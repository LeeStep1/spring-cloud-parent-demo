package com.bit.module.manager.service.Impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bit.module.equation.bean.BasePriceEquation;
import com.bit.module.equation.bean.BasePriceEquationRel;
import com.bit.module.equation.bean.Equation;
import com.bit.module.equation.dao.BasePriceEquationDao;
import com.bit.module.equation.dao.BasePriceEquationRelDao;
import com.bit.module.equation.dao.EquationDao;
import com.bit.module.manager.bean.*;
import com.bit.module.manager.dao.*;
import com.bit.module.miniapp.bean.Area;
import com.bit.module.miniapp.bean.ElevatorType;
import com.bit.module.miniapp.bean.Options;
import org.apache.commons.lang.StringUtils;
import org.mvel2.MVEL;
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
    private EquationDao equationDao;
    @Autowired
    private BasePriceEquationDao basePriceEquationDao;
    @Autowired
    private BasePriceEquationRelDao basePriceEquationRelDao;
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


    /**
     * 计算单个电梯
     *
     * @param vars
     */
    public void executeCount(Map vars) {
        getElevatorInfo(vars);
        if (Boolean.TRUE.equals(vars.get("包括运费"))) {
            executeTransportEquations(vars);
        }
        if (Boolean.TRUE.equals(vars.get("包括安装"))) {
            executeInstallEquations(vars);
        }
        executeEquations(vars);//计算设备单价
        updateOrder(vars);
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
        vars.put("下浮", projectEleOrder.getRate());
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
        vars.put("开门宽度", getNoEquationOut(vars, "开门宽度"));
        if (vars.get("平摊费用") == null) {
            vars.put("平摊费用", 0);
        }
    }

    /**
     * 计算整个项目
     *
     * @param map
     */
    public List<Map> executeCountProjectPrice(Map map) {
        ProjectPrice projectPrice = projectPriceDao.selectOne(new QueryWrapper<ProjectPrice>()
                .eq("project_id", map.get("projectId"))
                .eq("version", map.get("version")));
        List<ProjectEleOrder> projectEleOrder = projectEleOrderDao.selectList(new QueryWrapper<ProjectEleOrder>()
                .eq("project_id", projectPrice.getProjectId())
                .eq("version_id", projectPrice.getId()));

        //事前计算平摊费用
        List<Map> eleInputs = new ArrayList(projectEleOrder.size());
        for (ProjectEleOrder eleOrder : projectEleOrder) {
            Map input = new HashMap(3);
            input.put("orderId", eleOrder.getId());
            input.put("包括运费", map.get("包括运费"));
            input.put("包括安装", map.get("包括安装"));
            eleInputs.add(input);
        }
        beforeExecuteEquations(eleInputs);

        //开始计算
        for (Map vars : eleInputs) {
            executeCount(vars);
        }
        BigDecimal bd = new BigDecimal("0");
        List<ProjectEleOrder> projectEleOrderNew = projectEleOrderDao.selectList(new QueryWrapper<ProjectEleOrder>()
                .eq("project_id", projectPrice.getProjectId())
                .eq("version_id", projectPrice.getId()));
        for (ProjectEleOrder eleOrder : projectEleOrderNew) {
            bd = NumberUtil.add(bd.toString(), eleOrder.getTotalPrice());
        }
        projectPrice.setTotalPrice(bd.toString());
        if (map.get("stage") != null) {
            projectPrice.setStage(Integer.parseInt(map.get("stage").toString()));
        }
        projectPriceDao.updateById(projectPrice);
        return eleInputs;
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
        vars.put("开门宽度", getNoEquationOut(vars, "开门宽度"));
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
    public boolean checkHeightStandard(Map vars) {
        String type = vars.get("系列").toString();
        vars.put("标准提升高度", getHeightForBasePrice(vars, "基价"));
        boolean heightStandard = getEqBoolean(type, "顶层底坑是否超标", vars);//是否超标
        return heightStandard;
    }

    public Map executeEquations(Map vars) {
        checkMap(vars);
        boolean heightStandard = checkHeightStandard(vars);//是否超标
        double heightPrice = 0;
        if (heightStandard) {
            vars.put("高度单价", getNoEquationOut(vars, "高度单价"));
            vars.put("标准顶层高度", getNoEquationOut(vars, "标准顶层高度"));
            vars.put("标准底坑深度", getNoEquationOut(vars, "标准底坑深度"));
            int temp = (Integer) simpleEquation("实际提升高度-标准提升高度", vars);
            if (temp > 0) {
                heightPrice += (double) simpleEquation("(实际提升高度-标准提升高度)/1000*高度单价", vars);
            }
            int temp1 = (Integer) simpleEquation("实际顶层高度-标准顶层高度", vars);
            if (temp1 > 0) {
                heightPrice += (double) simpleEquation("(实际顶层高度-标准顶层高度)/1000*高度单价", vars);
            }
            int temp2 = (Integer) simpleEquation("实际底坑深度-标准底坑深度", vars);
            if (temp2 > 0) {
                heightPrice += (double) simpleEquation("(实际底坑深度-标准底坑深度)/1000*高度单价", vars);
            }
            vars.put("是否为非标", false);
        } else {
            vars.put("是否为非标", true);
        }
        vars.put("小计_设备基价", getNoEquationOut(vars, "基价"));//设备基价
        double optionPrice = countOptionPrice(vars);
        optionPrice += heightPrice;
        vars.put("小计_高度价格", heightPrice);
        vars.put("小计_设备可选项价格", optionPrice);//设备可选项价格

        //vars.put("小计_非标加价", optionPrice);
        vars.put("小计_设备单价", simpleEquation("小计_设备基价*(1-下浮)+小计_设备可选项价格+平摊费用", vars)); //单价
        if (vars.get("小计_安装费用") == null) {
            vars.put("小计_安装费用", 0);
        }
        if (vars.get("小计_运费") == null) {
            vars.put("小计_运费", 0);
        }
        vars.put("小计_单台总价", simpleEquation("小计_设备单价+小计_安装费用+小计_运费", vars)); //单价
        vars.put("小计_合价", simpleEquation("小计_单台总价*台量", vars)); //单价

        String service = "价格*系数*维保价格";//维保
        return vars;
    }

    /**
     * 更新订单表
     */
    private void updateOrder(Map vars) {
        ProjectEleOrder projectEleOrder = projectEleOrderDao.selectById(Long.parseLong(vars.get("orderId").toString()));
        projectEleOrder.setUnitPrice(NumberUtil.roundStr(vars.get("小计_设备单价").toString(), 2));
        projectEleOrder.setSingleTotalPrice(NumberUtil.roundStr(vars.get("小计_单台总价").toString(), 2));
        projectEleOrder.setInstallPrice(NumberUtil.roundStr(vars.get("小计_安装费用").toString(), 2));
        projectEleOrder.setTotalPrice(NumberUtil.roundStr(vars.get("小计_合价").toString(), 2));
        projectEleOrder.setTransportPrice(NumberUtil.roundStr(vars.get("小计_运费").toString(), 2));
        projectEleOrderDao.updateById(projectEleOrder);
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

    public int getHeightForBasePrice(Map vars, String category) {
        String type = vars.get("系列").toString();
        List<BasePriceEquationRel> basePriceRel = basePriceEquationRelDao.selectList(
                new QueryWrapper<BasePriceEquationRel>()
                        .eq("type", type)
                        .eq("category", category)
        );

        QueryWrapper<BasePriceEquation> query = new QueryWrapper<BasePriceEquation>()
                .eq("category", category)
                .eq("type", vars.get("系列"))
                .eq("val3", vars.get("层站")).last(" limit 1");
        BasePriceEquation basePriceEquation = basePriceEquationDao.selectOne(query);
        if (basePriceEquation == null) {
            return 0;
        }
        return Integer.parseInt(basePriceEquation.getVal4());
    }

    public int getNoEquationOut(Map vars, String category) {
        String type = vars.get("系列").toString();
        List<BasePriceEquationRel> basePriceRel = basePriceEquationRelDao.selectList(
                new QueryWrapper<BasePriceEquationRel>()
                        .eq("type", type)
                        .eq("category", category)
        );

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

        return Integer.parseInt(basePriceEquation.getOutput());
    }

    private Object simpleEquation(String equation, Map vars) {
        Object object = MVEL.eval(equation, vars);
        System.out.println(object);
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
        System.out.println("计算结果:" + res);
        return Double.parseDouble(res.toString());
    }

    private Boolean getEqBoolean(String type, String category, Map vars) {
        List<Equation> testEntities = equationDao.selectList(new QueryWrapper<Equation>()
                .eq(StringUtils.isNotEmpty(type), "type", type)
                .eq("category", category));
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
        List<Equation> testEntities = equationDao.selectList(new QueryWrapper<Equation>()
                .eq(StringUtils.isNotEmpty(type), "type", type)
                .eq("category", category));

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
