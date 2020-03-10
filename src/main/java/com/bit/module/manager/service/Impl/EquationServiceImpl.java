package com.bit.module.manager.service.Impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bit.base.exception.BusinessException;
import com.bit.common.businessEnum.CalculateFlagEnum;
import com.bit.common.informationEnum.StandardEnum;
import com.bit.module.equation.bean.BasePriceEquation;
import com.bit.module.equation.bean.BasePriceEquationRel;
import com.bit.module.equation.bean.Equation;
import com.bit.module.equation.dao.BasePriceEquationDao;
import com.bit.module.equation.dao.EquationDao;
import com.bit.module.manager.bean.*;
import com.bit.module.manager.dao.*;
import com.bit.module.manager.vo.ProjectPriceAndOrderVO;
import com.bit.module.miniapp.bean.Area;
import com.bit.module.miniapp.bean.ElevatorType;
import com.bit.module.miniapp.bean.Options;
import org.mvel2.MVEL;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


/**
 * @description:
 * @author: mifei
 * @create: 2019-05-06 15:44
 */
@Service
public class EquationServiceImpl extends ServiceImpl<EquationDao, Equation> {


    @Autowired
    private BasePriceEquationDao basePriceEquationDao;
    @Autowired
    private ProjectEleOptionsDao projectEleOptionsDao;
    @Autowired
    private ProjectEleOrderDao projectEleOrderDao;
    @Autowired
    private ProjectEleOrderBaseInfoDao projectEleOrderBaseInfoDao;
    @Autowired
    private ProjectPriceDao projectPriceDao;
    @Autowired
    private ElevatorTypeDao elevatorTypeDao;
    @Autowired
    private AreaDao areaDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ProjectEleNonstandardDao projectEleNonstandardDao;
    @Autowired
    private EquationCacheServiceImpl equationCacheService;


    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(EquationServiceImpl.class);
    /**
     * 计算单个电梯
     *
     * @param vars
     */
    public void executeCount(Map vars) {
        logger.info("计算单个电梯入参:"+vars);
        getElevatorInfo(vars);
        if (Boolean.TRUE.equals(vars.get("包括运费"))) {
            executeTransportEquations(vars);
        }
        if (Boolean.TRUE.equals(vars.get("包括安装"))) {
            executeInstallEquations(vars);
        }
        executeEquations(vars);//计算设备单价
        if (Boolean.TRUE.equals(vars.get("isUpdate")) || "true".equals(vars.get("isUpdate"))) {
            updateOrder(vars);
        }
    }

    /**
     * 计算整个项目
     * @param map
     */

    public List<Map> executeCountProjectPrice(Map map) {
        ProjectPriceAndOrderVO vo = executeCountProjectPrice(map, null);
        return vo.getEleInputs();
    }

    public ProjectPriceAndOrderVO executeCountProject(Map map) {
        return executeCountProjectPrice(map, null);
    }


    /**
     * 调整每个下浮率时重新计算总价
     * @param priceId
     * @param projectEleOrderInputs
     * @return
     */
    public ProjectPrice countProjectTotalPrice(Long priceId,List<ProjectEleOrder> projectEleOrderInputs) {
        ProjectPrice projectPrice = projectPriceDao.selectById(priceId);
        List<ProjectEleOrder> projectEleOrder = projectEleOrderDao.selectList(new QueryWrapper<ProjectEleOrder>()
                .eq("project_id", projectPrice.getProjectId())
                .eq("version_id", projectPrice.getId()));
        double total = 0;
        for (ProjectEleOrder eleOrder : projectEleOrder) {
            if (projectEleOrderInputs != null) {
                Optional<ProjectEleOrder> projectEleOrderInput = projectEleOrderInputs.stream().filter(item -> item.getId().equals(eleOrder.getId())).findFirst();
                if (projectEleOrderInput.isPresent()) {
                    ProjectEleOrder temp = projectEleOrderInput.get();
                    String basePrice = JSON.parseObject(eleOrder.getBasePrice()).get("price").toString();
                    // 原总价 - 基价 *（1-原下浮)+ 基价*(1-现下浮)*数量  简化为:(原总价+ (基价*（原下浮-现下浮)))*数量
                    total += (Double.parseDouble(eleOrder.getSingleTotalPrice()) + Double.parseDouble(basePrice) * (eleOrder.getRate()- temp.getRate()))*Double.parseDouble(eleOrder.getNum());
                }
            }
        }
        projectPrice.setTotalPrice(NumberUtil.round(total, 2).toString());
        return projectPrice;
    }
    /**
     * 计算整个项目
     * @param map
     * @param projectEleOrderInputs 应对手动调节下浮率的情况，如果用户手动调节就输入此参数
     * @return
     */
    public ProjectPriceAndOrderVO executeCountProjectPrice(Map map,List<ProjectEleOrder> projectEleOrderInputs) {
        logger.info("计算总价时入参:"+map.toString());
        ProjectPrice projectPrice = null;
        if (map.get("projectPriceId") != null) {
            projectPrice = projectPriceDao.selectById(Long.parseLong(map.get("projectPriceId").toString()));
        } else {
            projectPrice = projectPriceDao.selectOne(new QueryWrapper<ProjectPrice>()
                    .eq("project_id", map.get("projectId"))
                    .eq("version", map.get("version")));
        }
        List<ProjectEleOrder> projectEleOrder = projectEleOrderDao.selectList(new QueryWrapper<ProjectEleOrder>()
                .eq("project_id", projectPrice.getProjectId())
                .eq("version_id", projectPrice.getId()));

        //事前计算平摊费用
        List<Map> eleInputs = new ArrayList(projectEleOrder.size());
        for (ProjectEleOrder eleOrder : projectEleOrder) {
            Map input = new HashMap(4);
            //如果用户输入了下浮
            if (projectEleOrderInputs != null) {
                Optional<ProjectEleOrder> projectEleOrderInput = projectEleOrderInputs.stream().filter(item -> item.getId().equals(eleOrder.getId())).findFirst();
                if (projectEleOrderInput.isPresent()){
                    input.put("下浮", projectEleOrderInput.get().getRate());
                }
            }
            input.put("orderId", eleOrder.getId());
            if (map.get("包括运费") != null) {
                input.put("包括运费", map.get("包括运费"));
            } else {
                input.put("包括运费",getBooleanForInteger(projectPrice.getTransportFlag()));
            }
            if (map.get("包括安装") != null) {
                input.put("包括安装", map.get("包括安装"));
            } else {
                input.put("包括安装", getBooleanForInteger(projectPrice.getInstallFlag()));
            }
            input.put("isUpdate", map.get("isUpdate"));
            eleInputs.add(input);
        }
        beforeExecuteEquations(eleInputs);
        boolean isStandard = true;
        //开始计算项目总价
        double difference = 0;//设备基价与成本差
        double totalNoDiscount = 0;//设备总价无下浮
        Double costTotalPrice = 0d;//设备总价无下浮
        double totalPrice = 0;//总价
        for (Map vars : eleInputs) {
            double sum = 0;
            List<ProjectEleNonstandard> projectEleNonStandards = projectEleNonstandardDao.selectList(new QueryWrapper<ProjectEleNonstandard>()
                    .eq("order_id", vars.get("orderId").toString()));
            for (ProjectEleNonstandard projectEleNonstandard : projectEleNonStandards) {
                if (CalculateFlagEnum.YES.getCode() != projectEleNonstandard.getProductionFlag()) {
                    continue;
                }
                if (projectEleNonstandard.getSignalPrice() != null) {
                    sum += Double.parseDouble(projectEleNonstandard.getSignalPrice());
                }
            }
            vars.put("非标加价",sum );
            executeCount(vars);
            difference += (int)vars.get("小计_设备基价与成本差");
            totalNoDiscount += (int)vars.get("小计_设备总价无下浮");
            costTotalPrice += Double.parseDouble(vars.get("小计_成本总价").toString());
            totalPrice += Double.parseDouble(vars.get("小计_合价").toString());
            if (Boolean.TRUE.equals(vars.get("是否为非标"))){
                isStandard = false;
            }
        }
        //最大下浮率 = 设备基价与成本差之和 / 设备总价无下浮之和
        projectPrice.setMaxRate(NumberUtil.round(difference/totalNoDiscount, 2).doubleValue());
        projectPrice.setCostTotalPrice(NumberUtil.round(costTotalPrice,2).toString());// 总成本
        projectPrice.setTotalPrice(NumberUtil.round(totalPrice,2).toString());
//        BigDecimal bd = new BigDecimal("0");
//        List<ProjectEleOrder> projectEleOrderNew = projectEleOrderDao.selectList(new QueryWrapper<ProjectEleOrder>()
//                .eq("project_id", projectPrice.getProjectId())
//                .eq("version_id", projectPrice.getId()));
//        for (ProjectEleOrder eleOrder : projectEleOrderNew) {
//            bd = NumberUtil.add(bd.toString(), eleOrder.getTotalPrice());
//        }
//        projectPrice.setTotalPrice(bd.toString());
        if (map.get("stage") != null) {
            projectPrice.setStage(Integer.parseInt(map.get("stage").toString()));
        }
        if (Boolean.TRUE.equals(map.get("isUpdate"))) {
            if (isStandard == false){//如果是非标
                projectPrice.setStandard(StandardEnum.STANDARD_ZERO.getCode());
                projectPrice.setStandardName(StandardEnum.STANDARD_ZERO.getInfo());
            }
            projectPriceDao.updateById(projectPrice);
        }
        ProjectPriceAndOrderVO vo = new ProjectPriceAndOrderVO();
        vo.setEleInputs(eleInputs);
        vo.setProjectPrice(projectPrice);
        return vo;
    }

    private boolean getBooleanForInteger(Integer installFlag) {
        if (installFlag == 1) {
            return true;
        }
        return false;
    }

    /**
     * 计算平均下浮率
     * @param map
     * @return
     */
    public double countAvgDiscountRate (ProjectPrice projectPriceInput) {

        ProjectPrice projectPrice = projectPriceDao.selectOne(new QueryWrapper<ProjectPrice>()
                .eq("id", projectPriceInput.getId()));
        List<ProjectEleOrder> projectEleOrder = projectEleOrderDao.selectList(new QueryWrapper<ProjectEleOrder>()
                .eq("version_id", projectPrice.getId()));

        // 计算接口
        Map map = new HashMap();
        map.put("projectPriceId", projectPrice.getId());
        map.put("isUpdate", false);
        if (projectPrice.getTransportFlag() == 1){
            map.put("包括运费",true);
        }else if (projectPrice.getTransportFlag() == 0){
            map.put("包括运费",false);
        }

        if (projectPrice.getInstallFlag() == 1){
            map.put("包括安装",true);
        }else if (projectPrice.getInstallFlag() == 0){
            map.put("包括安装",false);
        }

        //事前计算平摊费用
        List<Map> eleInputs = new ArrayList(projectEleOrder.size());
        for (ProjectEleOrder eleOrder : projectEleOrder) {
            Map input = new HashMap(4);
            input.put("orderId", eleOrder.getId());
            input.put("包括运费", map.get("包括运费"));
            input.put("包括安装", map.get("包括安装"));
            input.put("isUpdate", map.get("isUpdate"));
            eleInputs.add(input);
        }
        beforeExecuteEquations(eleInputs);
        //开始计算项目总价
        double total = 0;
        double totalNoDiscount = 0;
        for (Map vars : eleInputs) {
            double sum = 0;
            List<ProjectEleNonstandard> projectEleNonStandards = projectEleNonstandardDao.selectList(new QueryWrapper<ProjectEleNonstandard>()
                    .eq("order_id", vars.get("orderId").toString()));
            for (ProjectEleNonstandard projectEleNonstandard : projectEleNonStandards) {
                if (CalculateFlagEnum.YES.getCode() != projectEleNonstandard.getProductionFlag()) {
                    continue;
                }
                if (projectEleNonstandard.getSignalPrice() != null) {
                    sum += Double.parseDouble(projectEleNonstandard.getSignalPrice());
                }
            }
            vars.put("非标加价",sum );
            vars.put("不计算下浮率",true );
            executeCount(vars);
            totalNoDiscount+= (Double)vars.get("小计_合价");
            total += Double.parseDouble(vars.get("小计_设备总价无下浮").toString());
        }
        double targetPrice = Double.parseDouble(projectPrice.getInquiryPrice());
        double avgDiscountRate = (totalNoDiscount - targetPrice) / total;
        //更新平均下浮率
        projectPrice.setAverageRate(avgDiscountRate);
        projectPriceDao.updateById(projectPrice);
        return NumberUtil.round(avgDiscountRate, 2).doubleValue();
    }
    /**
     * 拼装需要的参数
     *
     * @param projectEleOrder
     * @param baseInfos
     * @param vars
     */
    public void getElevatorInfo(Map vars) {
        ProjectEleOrder projectEleOrder = projectEleOrderDao.selectById(Long.parseLong(vars.get("orderId").toString()));
        List<ProjectEleOrderBaseInfo> baseInfos =
                projectEleOrderBaseInfoDao.selectList(new QueryWrapper<ProjectEleOrderBaseInfo>()
                        .eq("order_id", vars.get("orderId").toString()));
        if (baseInfos.size()==0) {
            throw new BusinessException("基础信息不能为空");
        }
        if (Boolean.TRUE.equals(vars.get("不计算下浮率"))) {
            vars.put("下浮", 0);
        } else {
            if (vars.get("下浮") == null) {//应对用户手动输入下浮率的情况
                vars.put("下浮", projectEleOrder.getRate());
            }
        }

        vars.put("台量", projectEleOrder.getNum());

        ElevatorType elevatorType = elevatorTypeDao.selectById(projectEleOrder.getElevatorTypeId());
        vars.put("梯型", elevatorType.getCategory());
        vars.put("系列", elevatorType.getParamsKey());
        Project project = projectDao.selectById(projectEleOrder.getProjectId());
        String[] split = project.getAddressId().split(",");

        List<Area> areas = areaDao.selectList(new QueryWrapper<Area>().in("ar_code", split));
        Collections.reverse(areas);
        float installCoefficient = 1.0f;
        int tonsPrice = 1;
        for (Area area : areas) {
            if (area.getInstallCoefficient() != null) {
                installCoefficient = area.getInstallCoefficient();
            }
        }
        for (Area area : areas) {
            if (area.getTonsPrice() != null) {
                tonsPrice = area.getTonsPrice();
                break;
            }
        }
        vars.put("运输_吨位单价", tonsPrice);
        vars.put("安装_地区系数", installCoefficient);

        for (ProjectEleOrderBaseInfo baseInfo : baseInfos) {
            if (NumberUtil.isInteger(baseInfo.getInfoValue())) {
                vars.put(baseInfo.getParamKey(), Integer.parseInt(baseInfo.getInfoValue()));
            } else if (NumberUtil.isDouble(baseInfo.getInfoValue())) {
                vars.put(baseInfo.getParamKey(), Double.parseDouble(baseInfo.getInfoValue()));
            } else {
                vars.put(baseInfo.getParamKey(), baseInfo.getInfoValue());
            }
        }
        vars.put("开门宽度", getBasePriceEquationOut(vars, "开门宽度"));
        if (vars.get("平摊费用") == null) {
            vars.put("平摊费用", 0);
        }
    }

    public void executeInstallEquations(Map vars) {
        String type = vars.get("系列").toString();
        vars.put("安装_基价", getEqInteger(type, vars, "安装_基价"));
        vars.put("安装_台量系数", getEqDouble(null, vars, "安装_台量系数"));
        //vars.put("安装_地区系数", getEqDouble(null, vars, "安装_地区系数"));
        vars.put("小计_安装费用", (Double) simpleEquation("安装_基价*安装_台量系数*安装_地区系数", vars));//安装费用
    }

    public void executeTransportEquations(Map vars) {
        //vars.put("运输_吨位单价", getEqInteger(null, vars, "运输_吨位单价"));
        vars.put("运输_吨位", getEqDouble(null, vars, "运输_吨位"));
        vars.put("运输_分段系数", getEqDouble(null, vars, "运输_分段系数", "1.0"));
        vars.put("小计_运费", simpleEquation("运输_吨位单价*运输_吨位*运输_分段系数", vars));//运输
    }

    public static void main(String[] args) {
        Map vars = new HashMap();
        vars.put("input", 5);
        vars.put("系列", "GEE_DJ");
        Object o = new EquationServiceImpl().simpleEquation("input<3 and input>1  ", vars);
        System.out.println(o.getClass());
    }


    /**
     * 返回符合条件的option
     *
     * @param vars
     * @return
     */
    public List<Options> executeEquationsForOption(Map vars, List<Options> list) {
        vars.put("开门宽度", getBasePriceEquationOut(vars, "开门宽度"));
        List<Options> res = new ArrayList<>();
        for (Options options : list) {
            vars.put("数量", 10000);//
            int i = getEqInteger(options.getId() + "", vars, "可选项价格");
            if (i != 0) {
                res.add(options);
            }
        }
        return res;
    }

    private void checkMap(Map vars) {
        if (vars.get("下浮") == null) {
            throw new RuntimeException("下浮不能为空");
        }
        if (vars.get("台量") == null) {
            throw new RuntimeException("台量不能为空");
        }
        if (vars.get("梯型") == null) {
            throw new RuntimeException("梯型不能为空");
        }

    }

    /**
     * 验证是否超标
     *
     * @param vars
     * @return
     */
    public boolean checkHeightStandard(Map vars,String category) {
        String type = vars.get("系列").toString();
        vars.put("标准提升高度", getHeightForBasePrice(vars, "基价"));
        boolean heightStandard = getEqBoolean(type, category, vars);//是否超标
        return heightStandard;
    }

    public Map executeEquations(Map vars) {
        checkMap(vars);
        vars.put("高度单价", getBasePriceEquationOut(vars, "高度单价"));
        vars.put("标准顶层高度", getBasePriceEquationOut(vars, "标准顶层高度"));
        vars.put("标准底坑深度", getBasePriceEquationOut(vars, "标准底坑深度"));

        double heightPrice = checkNostandardAndPrice(vars);//判断是否为非标
        int basePrice = getBasePriceEquationOut(vars, "基价");
        vars.put("小计_设备基价", basePrice);
        int baseCost = getBasePriceEquationCost(vars, "基价");
        vars.put("小计_设备基价成本", baseCost);  //成本
        vars.put("小计_设备基价与成本差", simpleEquation("(小计_设备基价-小计_设备基价成本)*台量", vars));//求最大下浮率的分母

        buildBasePriceJson(vars);
        double optionPrice = countOptionPrice(vars) + heightPrice;
        vars.put("小计_高度价格", heightPrice);
        vars.put("小计_设备可选项价格", optionPrice);//设备可选项价格
        if (vars.get("非标加价")==null){
            vars.put("非标加价", 0);
        }
        vars.put("小计_设备总价无下浮", simpleEquation("小计_设备基价*台量", vars));//计算平均下浮率用的 //单价
        if (vars.get("小计_安装费用") == null) {
            vars.put("小计_安装费用", 0);
        }
        if (vars.get("小计_运费") == null) {
            vars.put("小计_运费", 0);
        }
        vars.put("小计_设备单价", simpleEquation("小计_设备基价*(1-下浮)+小计_设备可选项价格+平摊费用+非标加价", vars));
        vars.put("小计_单台总价", simpleEquation("小计_设备单价+小计_安装费用+小计_运费", vars)); //单价
        vars.put("小计_合价", simpleEquation("小计_单台总价*台量", vars)); //单价
        vars.put("小计_成本总价", simpleEquation("(小计_设备基价成本+小计_设备可选项价格+平摊费用+非标加价+小计_安装费用+小计_运费)*台量", vars)); //单价
        String service = "价格*系数*维保价格";//维保
        return vars;
    }

    private void buildBasePriceJson(Map vars) {
        Map map = new HashMap();
        map.put("title", "规格参数基价");
        if ("客梯".equals(vars.get("梯型")) || "货梯".equals(vars.get("梯型"))){
            String content = StrUtil.format("{}KG,{}m/s,{}层", vars.get("载重"), vars.get("速度"), vars.get("层站"));
            map.put("content", content);
        }
        map.put("price", vars.get("小计_设备基价"));
        vars.put("基价后台显示JSON", JSON.toJSONString(map));
    }

    private double checkNostandardAndPrice(Map vars) {
        boolean noStandard = false;
        double heightPrice = 0;

        if ("扶梯".equals(vars.get("梯型")) || "人行道梯".equals(vars.get("梯型"))) {
            return 0;
        }
        List<String> noStandardDetail = new ArrayList<>(3);
        Map additionHeightInfo = new HashMap(3);
        List<String> title = new ArrayList<>(3);
        List<String> height = new ArrayList<>(3);
        List<String> prices = new ArrayList<>(3);

        if (checkHeightStandard(vars,"提升高度是否超标")) {
            int temp = (Integer) simpleEquation("实际提升高度-标准提升高度", vars);
            if (temp > 0) {
                heightPrice += (double) simpleEquation("(实际提升高度-标准提升高度)/1000*高度单价", vars);
                title.add("提升高度加价");
                if (vars.get("实际提升高度")!=null) {
                    height.add(vars.get("实际提升高度")+"mm");
                }
                prices.add(heightPrice +"");
            }
        }else {
            noStandard = true;
            noStandardDetail.add("提升高度超标");
        }
        if (checkHeightStandard(vars,"顶层高度是否超标")) {
            int temp1 = (Integer) simpleEquation("实际顶层高度-标准顶层高度", vars);
            if (temp1 > 0) {
                heightPrice += (double) simpleEquation("(实际顶层高度-标准顶层高度)/1000*高度单价", vars);
                title.add("顶层高度加价");
                if (vars.get("实际顶层高度")!=null) {
                    height.add(vars.get("实际顶层高度")+"mm");
                }
                prices.add(heightPrice +"");
            }
        }else {
            noStandard = true;
            noStandardDetail.add("顶层高度超标");
        }
        if (checkHeightStandard(vars,"底坑深度是否超标")) {
            int temp2 = (Integer) simpleEquation("实际底坑深度-标准底坑深度", vars);
            if (temp2 > 0) {
                heightPrice += (double) simpleEquation("(实际底坑深度-标准底坑深度)/1000*高度单价", vars);
                title.add("底坑深度加价");
                if (vars.get("实际底坑深度")!=null) {
                    height.add(vars.get("实际底坑深度")+"mm");
                }
                prices.add(heightPrice +"");
            }
        }else {
            noStandard = true;
            noStandardDetail.add("底坑深度超标");
        }
        if (!noStandard){
            additionHeightInfo.put("title", CollUtil.join(title, ","));
            additionHeightInfo.put("height", CollUtil.join(height, ","));
            additionHeightInfo.put("prices", CollUtil.join(prices, ","));
            vars.put("高度加价", additionHeightInfo);
        }
        vars.put("是否为非标", noStandard);
        vars.put("非标详情", CollUtil.join(noStandardDetail,","));
        return heightPrice;
    }

    /**
     * 更新订单表
     */
    private void updateOrder(Map vars) {
        Long orderId = Long.parseLong(vars.get("orderId").toString());
        ProjectEleOrder projectEleOrder = projectEleOrderDao.selectById(orderId);
        projectEleOrder.setUnitPrice(NumberUtil.roundStr(vars.get("小计_设备单价").toString(), 2));
        projectEleOrder.setSingleTotalPrice(NumberUtil.roundStr(vars.get("小计_单台总价").toString(), 2));
        projectEleOrder.setInstallPrice(NumberUtil.roundStr(vars.get("小计_安装费用").toString(), 2));
        projectEleOrder.setTotalPrice(NumberUtil.roundStr(vars.get("小计_合价").toString(), 2));
        projectEleOrder.setTransportPrice(NumberUtil.roundStr(vars.get("小计_运费").toString(), 2));
        projectEleOrder.setCostBasePrice(NumberUtil.roundStr(vars.get("小计_设备基价成本").toString(), 2));
        if (vars.get("高度加价") != null) {
            projectEleOrder.setAdditionPrice(JSON.toJSONString(vars.get("高度加价")));
        }
        if (vars.get("基价后台显示JSON") != null) {
            projectEleOrder.setBasePrice(vars.get("基价后台显示JSON")+"");
        }
//        if (Boolean.TRUE.equals(vars.get("是否为非标"))){
//            projectEleOrder.setStandard(StandardEnum.STANDARD_ZERO.getCode());
//            projectEleOrder.setStandardName(StandardEnum.STANDARD_ZERO.getInfo());
//        }
//        if (Boolean.FALSE.equals(vars.get("是否为非标"))){
//            projectEleOrder.setStandard(StandardEnum.STANDARD_ONE.getCode());
//            projectEleOrder.setStandardName(StandardEnum.STANDARD_ONE.getInfo());
//        }
        projectEleOrderDao.updateById(projectEleOrder);

        //插入非标记录
//        ProjectEleNonstandard nonstandard = projectEleNonstandardDao.selectOne(new QueryWrapper<ProjectEleNonstandard>()
//                .eq("order_id", orderId)
//                .eq("sys_type",NONSTANDARD_TYPE_SYS));
//        if (nonstandard == null && Boolean.TRUE.equals(vars.get("是否为非标"))) {
//            nonstandard = new ProjectEleNonstandard();
//            nonstandard.setOrderId(orderId);
//            nonstandard.setSysType(NONSTANDARD_TYPE_SYS);
//            nonstandard.setContent((String) vars.get("非标详情"));
//            projectEleNonstandardDao.insert(nonstandard);
//        }
    }

    public double countOptionPrice(Map vars) {
        Long orderId = Long.parseLong(vars.get("orderId").toString());
        double res = 0;
        List<ProjectEleOptions> projectEleOptions = projectEleOptionsDao.selectList(
                new QueryWrapper<ProjectEleOptions>().eq("order_id", orderId));
        for (ProjectEleOptions projectEleOption : projectEleOptions) {
            vars.put("数量", projectEleOption.getNums());
            Boolean isAverage = getEqBoolean(projectEleOption.getOptionId() + "", "是否参与平摊", vars);
            double price = getEqDouble(projectEleOption.getOptionId() + "", vars, "可选项价格");
            projectEleOption.setOptionPrice(price * projectEleOption.getNums());
            projectEleOptionsDao.updateById(projectEleOption);
            if (!isAverage) {
                res += projectEleOption.getOptionPrice();//参与平摊的在这里不再累加金额
            }
        }
        return res;
    }

    //特殊方法，写死只取val4,没有取output
    public int getHeightForBasePrice(Map vars, String category) {

        BasePriceEquation basePriceEquation = equationCacheService.getBasePriceEquation(
                vars.get("系列")+"",vars.get("层站")+"",category);
        if (basePriceEquation == null) {
            return 0;
        }
        return Integer.parseInt(basePriceEquation.getVal4());
    }

    // 返回基价表的成本
    public int getBasePriceEquationCost(Map vars, String category) {
        return getBasePriceEquationOut(vars, category, true);
    }
    // 返回基价表的价格
    public int getBasePriceEquationOut(Map vars, String category) {
        return getBasePriceEquationOut(vars, category, false);
    }

    /**
     * 返回基价表的输出（价格或成本）
     * @param vars
     * @param category
     * @param isCost
     * @return
     */
    public int getBasePriceEquationOut(Map vars, String category, boolean isCost) {
        String type = vars.get("系列").toString();

        List<BasePriceEquationRel> basePriceRel = equationCacheService.getBasePriceEquationRelList(type,category);
        QueryWrapper<BasePriceEquation> query = new QueryWrapper<BasePriceEquation>()
                .eq("category", category)
                .eq("type", vars.get("系列"));
        for (BasePriceEquationRel basePriceEquationRel : basePriceRel) {
            query.eq(vars.get(basePriceEquationRel.getParams()) != null, basePriceEquationRel.getVal(), vars.get(basePriceEquationRel.getParams()));
        }
        BasePriceEquation basePriceEquation = basePriceEquationDao.selectOne(query);
        if (basePriceEquation == null) {
            return 0;
        }
        if (isCost) {
            return Integer.parseInt(basePriceEquation.getCostPrice());
        } else {
            return Integer.parseInt(basePriceEquation.getOutput());
        }
    }

    private Object simpleEquation(String equation, Map vars) {
        Object object = MVEL.eval(equation, vars);
        return object;
    }

    private Integer getEqInteger(String type, Map vars, String category) {
        String all = getObject(type, category, "0");
        Object object = MVEL.eval(all, vars);
        return (Integer) object;
    }

    public void test() {
        Map vars = new HashMap();
        vars.put("提升高度", 15);
        vars.put("价格", 60);
        Integer test = getEqInteger(null, vars, "test");

    }

    //计算需要平摊的配件金额
    public double getCommonPrice(Map vars) {
        Long orderId = Long.parseLong(vars.get("orderId").toString());
        double res = 0;
        List<ProjectEleOptions> projectEleOptions = projectEleOptionsDao.selectList(
                new QueryWrapper<ProjectEleOptions>().eq("order_id", orderId));
        for (ProjectEleOptions projectEleOption : projectEleOptions) {
            Boolean isAverage = getEqBoolean(projectEleOption.getOptionId() + "", "是否参与平摊", vars);
            if (!isAverage) {
                continue;
            }
            vars.put("数量", projectEleOption.getNums());
            double price = getEqDouble(projectEleOption.getOptionId() + "", vars, "可选项价格");
            projectEleOption.setOptionPrice(price * projectEleOption.getNums());
            projectEleOptionsDao.updateById(projectEleOption);
            res += projectEleOption.getOptionPrice();
        }
        return res;
    }

    //将参与平摊的配件提前算出并平摊
    public void beforeExecuteEquations(List<Map> list) {
        double numSum = 0;
        double commonPrice = 0;
        for (Map map : list) {
            getElevatorInfo(map);
            int num = Integer.parseInt(map.get("台量").toString());
            Boolean isAverage = getEqBoolean(null, "此电梯是否参与平摊", map);
            if (!isAverage) {
                continue;
            }
            numSum += num;
            commonPrice += getCommonPrice(map);
        }
        if (commonPrice == 0) {
            return;
        }
        double perPrice = commonPrice / numSum;
        for (Map map : list) {
            Boolean isAverage = getEqBoolean(null, "此电梯是否参与平摊", map);
            if (isAverage) {
                map.put("平摊费用", perPrice);
            }
        }
    }

    private Double getEqDouble(String type, Map vars, String category, String... values) {
        String defaultValue = "0.0";
        if (values.length > 0) {
            defaultValue = values[0];
        }
        String all = getObject(type, category, defaultValue);
        Object res = MVEL.eval(all, vars);
        return Double.parseDouble(res.toString());
    }

    private Boolean getEqBoolean(String type, String category, Map vars) {
        List<Equation> testEntities = equationCacheService.getEquations(type, category);
        String s = "if ({}){ \n" +
                " res = true; \n" +
                "} \n";
        String expression = "";
        for (Equation entity : testEntities) {
            List<String> equations = JSON.parseArray(entity.getEquation(), String.class);
            String equationStr = CollUtil.join(equations, " and ");
            expression += StrUtil.format(s, equationStr, entity.getOutput());
        }
        String all = "res=false; \n {} \n res;";
        all = StrUtil.format(all, expression);
        Boolean res = (Boolean) MVEL.eval(all, vars);
        return res;
    }

    private String getObject(String type, String category, String result) {
        List<Equation> testEntities = equationCacheService.getEquations(type, category);

        String s = "if ({}){ \n" +
                " res = {}; \n" +
                "} \n";
        String expression = "";
        for (Equation entity : testEntities) {
            List<String> equations = JSON.parseArray(entity.getEquation(), String.class);
            String equationStr = CollUtil.join(equations, " and ");
            expression += StrUtil.format(s, equationStr, entity.getOutput());
        }
        String all = "res={}; \n {} \n res;";
        all = StrUtil.format(all, result, expression);
        return all;
    }


}
