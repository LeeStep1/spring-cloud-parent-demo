package guava.collections;

import com.google.common.base.Joiner;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.junit.Test;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @Description: TableTest
 * @Author LeeYoung
 * @Date: 2021/5/12
 */
public class TableTest {

    /**
     * Table:
     *      1、Table 是一个 guava提供的接口，有多种实现类
     *      2、语法糖为 <R, C, V>  代表 行 列 值
     *      3、等同于 LinkedHashMap<R, Map<C, V>> / Map<String,Map<String,String>>
     *      4、插入相同的columnKey时，会覆盖之前的  即 row-column 为联合主键
     * @Author LeeYoung
     **/
    @Test
    public void putTest(){
        HashBasedTable<String, String, String> table = HashBasedTable.create();
        table.put("tools","commons","3.12.0");
        table.put("tools","guava","28.1");
        table.put("tools","guava","28.2");
        table.put("frame","spring","5.0");
        table.put("frame","springBoot","2.3.8");
        System.out.println(table);
    }

    /**
     * 获取内容：
     *        1、row: 可直接获取 一行数据,等到一个map
     *        2、get: 可根据 row 和 column 定位到value
     *        3、column: 可根据 column 获取到 row 和 value的 map 集合
     *        4、values: 获取所有value的集合
     * @Author LeeYoung
     **/
    @Test
    public void getTest(){
        HashBasedTable<String, String, String> table = HashBasedTable.create();
        table.put("tools","commons","3.12.0");
        table.put("tools","guava","28.1");
        table.put("frame","spring","5.0");
        table.put("frame","springBoot","2.3.8");

        Map<String, String> tools = table.row("tools");
        assertThat(tools.containsKey("guava"),is(true));
        assertThat(tools.containsKey("commons"),is(true));

        String s = table.get("tools", "commons");
        assertThat(s,equalTo("3.12.0"));

        Map<String, String> commons = table.column("commons");
        assertThat(commons.containsKey("tools"),is(true));

        Collection<String> values = table.values();
        assertThat(Joiner.on(",").join(values),equalTo("3.12.0,28.1,5.0,2.3.8"));
    }

    /**
     * cellSet :
     *          1、根据 row 和 column 进行拆解成不合并的集合 ，
     * @Author LeeYoung
     **/
    @Test
    public void cellTest(){
        HashBasedTable<String, String, String> table = HashBasedTable.create();
        table.put("tools","commons","3.12.0");
        table.put("tools","guava","28.1");
        table.put("frame","spring","5.0");
        table.put("frame","springBoot","2.3.8");

        Set<Table.Cell<String, String, String>> cells = table.cellSet();
        System.out.println(cells);
    }

    /**
     * contains :
     *          1、根据 row 和 column
     *          2、根据 row
     *          3、根据 column
     *          4、根据 value
     * @Author LeeYoung
     **/
    @Test
    public void containsTest(){
        HashBasedTable<String, String, String> table = HashBasedTable.create();
        table.put("tools","commons","3.12.0");
        table.put("tools","guava","28.1");
        table.put("frame","spring","5.0");
        table.put("frame","springBoot","2.3.8");

        boolean contains = table.contains("tools", "commons");
        assertThat(contains,is(true));

        boolean tools = table.containsRow("tools");
        assertThat(tools,is(true));

        boolean guava = table.containsColumn("guava");
        assertThat(guava,is(true));

        boolean b = table.containsValue("28.1");
        assertThat(b,is(true));
    }
}
