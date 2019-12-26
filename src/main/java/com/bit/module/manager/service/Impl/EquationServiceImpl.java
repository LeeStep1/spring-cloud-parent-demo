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
import com.bit.module.miniapp.bean.Options;
import org.apache.commons.lang.StringUtils;
import org.mvel2.MVEL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;


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


    /**
     * 计算单个电梯
     * @param map
     */
    public void executeCount(Map map) {
        ProjectEleOrder projectEleOrder = projectEleOrderDao.selectById(Long.parseLong(map.get("orderId").toString()));
        List<ProjectEleOrderBaseInfo> baseInfos =
                projectEleOrderBaseInfoDao.selectList(new QueryWrapper<ProjectEleOrderBaseInfo>()
                        .eq("order_id", map.get("orderId").toString()));
        executeCountItem(baseInfos, map);
    }
    /**
     * 计算整个项目
     * @param map
     */
    public void executeCountProjectPrice(Map map) {
        ProjectPrice projectPrice = projectPriceDao.selectOne(new QueryWrapper<ProjectPrice>()
        .eq("project_id",map.get("projectId"))
        .eq("version",map.get("version")));
        List<ProjectEleOrder> projectEleOrder = projectEleOrderDao.selectList(new QueryWrapper<ProjectEleOrder>()
                .eq("project_id", projectPrice.getProjectId())
                .eq("version_id", projectPrice.getVersion()));
        for (ProjectEleOrder eleOrder : projectEleOrder) {
            Map input = new HashMap();
            input.put("orderId",eleOrder.getId() );
            executeCount(input);
        }
        BigDecimal bd = new BigDecimal("0");
        List<ProjectEleOrder> projectEleOrderNew = projectEleOrderDao.selectList(new QueryWrapper<ProjectEleOrder>()
                .eq("project_id", projectPrice.getProjectId())
                .eq("version_id", projectPrice.getVersion()));
        for (ProjectEleOrder eleOrder : projectEleOrderNew) {
            bd = NumberUtil.add(bd.toString(),eleOrder.getTotalPrice());
        }
        projectPrice.setTotalPrice(bd.toString());
        if (map.get("stage") != null) {
            projectPrice.setStage(Integer.parseInt(map.get("stage").toString()));
        }
        projectPriceDao.updateById(projectPrice);
    }

    public void executeCountItem(List<ProjectEleOrderBaseInfo> list, Map vars) {
        for (ProjectEleOrderBaseInfo baseInfo : list) {
            if (NumberUtil.isInteger(baseInfo.getInfoValue())) {
                vars.put(baseInfo.getParamKey(), Integer.parseInt(baseInfo.getInfoValue()));
            } else if (NumberUtil.isDouble(baseInfo.getInfoValue())) {
                vars.put(baseInfo.getParamKey(), Double.parseDouble(baseInfo.getInfoValue()));
            } else {
                vars.put(baseInfo.getParamKey(), baseInfo.getInfoValue());
            }
        }
        executeEquations(vars);//计算基价
        updateOrder(vars);
        if (Boolean.TRUE.equals(vars.get("包括运费"))) {
            executeTransportEquations(vars);
        }
        if (Boolean.TRUE.equals(vars.get("包括安装"))) {
            executeInstallEquations(vars);
        }
    }

    public void test(Map vars) {
        vars = new HashMap();
        // test
        vars.put("载重", new Integer(630));
        vars.put("速度", new Float(1.0));
        vars.put("层站", new Integer(3));
        vars.put("梯型", "直梯");
        vars.put("系列", "GEE");
        vars.put("角度", new Integer(10));
        vars.put("宽度", new Integer(10));
        vars.put("台量", new Integer(60));
        vars.put("省", "120");
        vars.put("市", "120000");
        vars.put("区", "120000000");
        vars.put("下浮", new Double(0.55));
        vars.put("实际提升高度", new Integer(60));
        vars.put("实际顶层高度", 4600);
        vars.put("实际底坑深度", 1500);
        //vars.put("标准提升高度", new Integer(6));
        vars.put("project_id", 123);//项目id
        vars.put("elevator_id", 1500);//项目id

        executeEquations(vars);
        executeTransportEquations(vars);
        executeInstallEquations(vars);
    }

    public void executeInstallEquations(Map vars) {
        String type = vars.get("系列").toString();
        vars.put("安装_基价", getEqInteger(type, vars, "安装基价"));
        vars.put("安装_台量系数", getEqDouble(null, vars, "安装_台量系数"));
        vars.put("安装_地区系数", getEqDouble(null, vars, "安装_地区系数"));
        vars.put("小计_安装费用", (Double) simpleEquation("安装_基价*安装_台量系数*安装_地区系数", vars));//安装费用
    }

    public void executeTransportEquations(Map vars) {
        vars.put("运输_吨位单价", getEqInteger(null, vars, "运输_吨位单价"));
        vars.put("运输_吨位", getEqInteger(null, vars, "运输_吨位"));
        vars.put("运输_分段系数", getEqDouble(null, vars, "运输_分段系数"));
        vars.put("小计_运费", simpleEquation("运输_吨位单价*运输_吨位*运输_分段系数", vars));//运输
    }

    public static void main(String[] args) {
        System.out.println(NumberUtil.isDouble("10.0"));
        Map vars = new HashMap();
        vars.put("小计_设备基价", 132);
        vars.put("下浮", 0.55);
        vars.put("小计_非标加价", 0.0);
        Object eval = MVEL.eval("小计_设备基价*(1-下浮)+小计_非标加价", vars);

        System.out.println(eval);
    }


    /**
     * 返回符合条件的option
     *
     * @param vars
     * @return
     */
    public List<Options> executeEquationsForOption(Map vars, List<Options> list) {
        List<Options> res = new ArrayList<>();
        for (Options options : list) {
            int i = getEqInteger(options.getId() + "", vars, "选项触发公式");
            if (i > 0) {
                res.add(options);
            }
        }
        return res;
    }

    public Map executeEquations(Map vars) {

        checkMap(vars);
        String type = vars.get("系列").toString();
        vars.put("标准提升高度", getHeightForBasePrice(vars, "基价"));
        boolean heightStandard = getEqBoolean(type, "顶层底坑是否超标", vars);//是否超标
        double heightPrice = 0;
        if (heightStandard) {
            vars.put("高度单价", getEqInteger(type, vars, "高度单价"));
            vars.put("标准顶层高度", getNoEquationOut(vars, "标准顶层高度"));
            vars.put("标准底坑深度", getNoEquationOut(vars, "标准底坑深度"));
            int temp = (Integer) simpleEquation("实际提升高度-标准提升高度", vars);
            if (temp > 0) {
                heightPrice += (Integer) simpleEquation("(实际提升高度-标准提升高度)*高度单价", vars);
            }
            int temp1 = (Integer) simpleEquation("实际顶层高度-标准顶层高度", vars);
            if (temp1 > 0) {
                heightPrice += (double) simpleEquation("(实际顶层高度-标准顶层高度)/1000*高度单价", vars);
            }
            int temp2 = (Integer) simpleEquation("实际底坑深度-标准底坑深度", vars);
            if (temp2 > 0) {
                heightPrice += (double) simpleEquation("(实际底坑深度-标准底坑深度)/1000*高度单价", vars);
            }
        } else {
            vars.put("是否为非标", true);
        }
        vars.put("小计_设备基价", getNoEquationOut(vars, "基价"));//设备基价
        double optionPrice = getOptionPrice(vars);
        optionPrice += heightPrice;
        vars.put("小计_设备可选项价格", optionPrice);//设备可选项价格


        vars.put("小计_非标加价", optionPrice);
        vars.put("小计_设备单价", simpleEquation("小计_设备基价*(1-下浮)+小计_非标加价", vars)); //单价
        if (vars.get("小计_安装费用") == null) {
            vars.put("小计_安装费用", 0);
        }
        vars.put("小计_单台总价", simpleEquation("小计_设备单价+小计_安装费用", vars)); //单价
        vars.put("小计_合价", simpleEquation("小计_单台总价*台量", vars)); //单价

        String service = " 价格*系数*维保价格";//维保
        return vars;
    }

    private void checkMap(Map vars) {
        if (vars.get("下浮") == null) {
            throw new RuntimeException("下浮不能为空");
        }
        if (vars.get("台量") == null) {
            throw new RuntimeException("台量不能为空");
        }
    }

    /**
     * 更新订单表
     */
    private void updateOrder(Map vars) {
        ProjectEleOrder projectEleOrder = projectEleOrderDao.selectById(Long.parseLong(vars.get("orderId").toString()));
        projectEleOrder.setUnitPrice(NumberUtil.roundStr(vars.get("小计_设备单价").toString(),2));
        projectEleOrder.setSingleTotalPrice(NumberUtil.roundStr(vars.get("小计_单台总价").toString(),2));
        projectEleOrder.setInstallPrice(NumberUtil.roundStr(vars.get("小计_安装费用").toString(),2));
        projectEleOrder.setTotalPrice(NumberUtil.roundStr(vars.get("小计_合价").toString(),2));
        projectEleOrderDao.updateById(projectEleOrder);
    }

    public int getOptionPrice(Map vars) {
        String type = vars.get("系列").toString();
        int res = 0;
        List<ProjectEleOptions> projectEleOptions = projectEleOptionsDao.selectList(new QueryWrapper<>());
        for (ProjectEleOptions projectEleOption : projectEleOptions) {
            vars.put("数量", projectEleOption.getNums());
            res = getEqInteger(projectEleOption.getId() + "", vars, "可选项");
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
            query.eq(basePriceEquationRel.getVal(), vars.get(basePriceEquationRel.getParams()));
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
        String all = getObject(type, category);
        return (Integer) MVEL.eval(all, vars);
    }

    //TODO 五方对讲平摊
    public void beforeExecuteEquations(List<Map> list) {

        int numSum = 0;
        int commonPrice = 0;
        for (Map map : list) {
            int num = (int) map.get("台量");
            String type = (String) map.get("梯型");
            if (type != "直梯") {
                continue;
            }
            numSum += num;
            for (Map option : (List<Map>) map.get("options")) {
                boolean isCommon = (boolean) option.get("是否为通用组件");
                if (isCommon) {
                    int price = (int) option.get("price");
                    commonPrice += price;
                }
            }
        }
        if (commonPrice == 0) {
            return;
        }
        int perPrice = commonPrice / numSum;
        for (Map map : list) {
            int num = (int) map.get("台量");
            String type = (String) map.get("梯型");
            if (type != "直梯") {
                continue;
            }
            map.put("平摊费用", perPrice * num);
        }
    }

    private Double getEqDouble(String type, Map vars, String category) {
        String all = getObject(type, category);
        all = all.replaceAll("res=0;", "res=0.0;");
        Object res = MVEL.eval(all, vars);
        System.out.println("计算结果:" + res);
        return (Double) res;
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
            expression += StrUtil.format(s, equationStr, entity.getPrice());
        }
        String all = "res=false; \n {} \n res;";
        all = StrUtil.format(all, expression);
        Boolean res = (Boolean) MVEL.eval(all, vars);
        return res;
    }

    private String getObject(String type, String category) {
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
            expression += StrUtil.format(s, equationStr, entity.getPrice());
        }
        String all = "res=0; \n {} \n res;";
        all = StrUtil.format(all, expression);
        return all;
    }

}
