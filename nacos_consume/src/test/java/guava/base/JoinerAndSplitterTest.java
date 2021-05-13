package guava.base;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;


public class JoinerAndSplitterTest {

    /**
     * Joiner:
     *        1、List 转 String Joiner.on 自定义分隔符
     *        2、List 转 String Joiner.on(";").skipNulls() 去掉空
     *        3、List 转 String Joiner.on(";").useForNull() 遇到空 赋值默认值
     */
    @Test
    public void JoinerTestSimple(){
        List<String> joinString1 = Lists.newArrayList("单位1","单位2","单位3","单位4");
        List<String> joinString2 = Lists.newArrayList("单位1","单位2","单位3",null,"单位4");

        String join = Joiner.on(";").join(joinString1);
        assertThat(join,equalTo("单位1;单位2;单位3;单位4"));

        String join2 = Joiner.on(";").skipNulls().join(joinString2);
        assertThat(join2,equalTo("单位1;单位2;单位3;单位4"));

        String join3 = Joiner.on(";").useForNull("default").join(joinString2);
        assertThat(join3,equalTo("单位1;单位2;单位3;default;单位4"));
    }

    /**
     * Map 转 String:
     *              1、Joiner.on(",") 元素间的分隔符  rose=6000,jack=5000
     *              1、Joiner.on(",").withKeyValueSeparator("！") key  value 连接符  rose=6000!jack=5000
     * @Author LeeYoung
     **/
    @Test
    public void JoinerMapToString(){
        Map<String, Integer> salary = Maps.newHashMap();
        salary.put("jack",5000);
        salary.put("rose",6000);

        String join = Joiner.on("#") // 元素间的连接符
                .withKeyValueSeparator("！") //key  value 连接符
                .join(salary);
        System.out.println(join);

    }

    /**
     * Splitter:
     *          1、string转List
     *          2、Splitter.on(",") 截取的分隔符
     *          3、Splitter.on(",").trimResults() 去除前后空格
     *          4、Splitter.on(",").trimResults().omitEmptyStrings() 去除空格元素
     */
    @Test
    public void StringToList(){
        String input = "one    ,   tow   ,  ,  three";
        Iterable<String> split = Splitter.on(",")
                .trimResults()  //去除前后空格
                .omitEmptyStrings() //去除空格元素
                .split(input);
        List<String> strings = Lists.newArrayList(split);
        System.out.println(strings);
    }

    /**
     * Splitter.fixedLength() : 按照元素个数截取拆分list
     * @Author LeeYoung
     **/
    @Test
    public void splitSameFixedStr(){
        String input = "aaabbbccc";
        List<String> strings = Lists.newArrayList(Splitter.fixedLength(2).split(input));
        assertThat(strings.get(0),equalTo("aaa"));
        assertThat(strings.get(1),equalTo("bbb"));
        assertThat(strings.size(),equalTo(3));
    }

    /**
     * limit ：
     *      1、根据limit个数 自定义拆分
     * @Author LeeYoung
     **/
    @Test
    public void splitLimit(){
        String input = "one,tow,three,four";
        List<String> strings = Lists.newArrayList(Splitter.on(",").limit(2).split(input));
        assertThat(strings.size(),equalTo(2));
        assertThat(strings.get(0),equalTo("one"));
        assertThat(strings.get(1),equalTo("tow,three,four"));
    }
}
