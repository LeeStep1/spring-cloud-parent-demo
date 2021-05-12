package guava.collections;

import com.google.common.collect.*;
import org.junit.Test;

import java.util.NavigableMap;
import java.util.NavigableSet;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RangeTest {

    /**
     * closed:
     *      1、边界值 closed 代表包含关系   0<= x <=9
     *      2、当入参为integer时，直接比较数字大小
     *      3、当入参为String时，比较ASCII
     *      4、当入参为Object时，需要实现comparable 重写 compareTo 方法
     * @Author LeeYoung
     **/
    @Test
    public void closedTest(){
        Range<Integer> range = Range.closed(0,9);
        assertThat(range.contains(5),is(true));
        assertThat(range.contains(9),is(true));
        assertThat(range.contains(0),is(true));

        Range<String> closed = Range.closed("A", "C");
        assertThat(closed.contains("B"),is(true));
    }

    /**
     * open:
     *      1、边界值 open 代表不包含关系   0< x <9
     * @Author LeeYoung
     **/
    @Test
    public void openTest(){
        Range<Integer> range = Range.open(0,9);
        assertThat(range.contains(9),is(false));
        assertThat(range.contains(0),is(false));
    }

    /**
     * openClose:
     *      1、边界值 openClose 代表不包含关系   0< x <=9
     *      2、closedOpen 相反
     * @Author LeeYoung
     **/
    @Test
    public void openCloseTest(){
        Range<Integer> range = Range.openClosed(0,9);
        assertThat(range.contains(9),is(true));
        assertThat(range.contains(0),is(false));
    }

    /**
     * openClose:
     *      1、边界值 openClose 代表不包含关系   0< x <=9
     *      2、closedOpen 相反
     * @Author LeeYoung
     **/
    @Test
    public void greaterThanTest(){
        Range<Integer> range = Range.greaterThan(10);
        assertThat(range.contains(9),is(false));
        assertThat(range.contains(Integer.MAX_VALUE),is(true));
    }

    /**
     * subSetTest:
     *           1、将一个set集合按照 Range 拆分出一个范围内的子集
     *           2、入参1：NavigableSet 接口  入参2 ： Range
     * @Author LeeYoung
     **/
    @Test
    public void subSetTest(){
        NavigableSet<Integer> integers = Sets.subSet(ImmutableSortedSet.of(6,  7, 1, 4, 2, 3, 5, 8, 9), Range.closed(7, 9));
        assertThat(integers.contains(1),is(false));
        assertThat(integers.contains(7),is(true));
    }

    /**
     * subMap:
     *      1、将一个Map集合按照 Range 拆分出一个范围内的子集
     *      2、入参1：NavigableMap 接口  入参2 ： Range
     *      3、ImmutableSortedMap 已经实现了对key的排序
     *      4、Range入参为 key 的顺序
     * @Author LeeYoung
     **/
    @Test
    public void subMap(){
        NavigableMap<String, Boolean> stringBooleanNavigableMap = Maps.subMap(ImmutableSortedMap.of("guava", true, "commons", false, "java",true,"hutool", true), Range.closed("commons", "hutool"));
        assertThat(stringBooleanNavigableMap.containsKey("java"),is(false));
        assertThat(stringBooleanNavigableMap.containsKey("commons"),is(true));
        assertThat(stringBooleanNavigableMap.containsKey("guava"),is(true));
    }

    /**
     * rangeMap:
     *          1、根据规定的范围获取value
     * @Author LeeYoung
     **/
    @Test
    public void rangeMapTest(){
        TreeRangeMap<Comparable, Object> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(0,60),"D");
        rangeMap.put(Range.closed(61,80),"C");
        rangeMap.put(Range.closed(81,90),"B");
        rangeMap.put(Range.closed(91,100),"A");

        assertThat(rangeMap.get(80),equalTo("C"));
    }

}
