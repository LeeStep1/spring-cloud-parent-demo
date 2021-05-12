package guava.collections;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * maps
 * @Author LeeYoung
 **/
public class MapsTest {

    /**
     * map 初始化
     *          1、初始化一个可变的 空map
     *          2、由List转化为一个 长度不可变的 map  ** function 定义key **
     *          3、toMap 入参1 是Iterable类型 即list 和set 都可以传  入参2的function是定义value的 返回的是可变的map
     *          4、由set 转换一个 可变的map ** function 定义value **
     *          5、通过 ImmutableMap 也可以初始化
     * @Author LeeYoung
     **/
    @Test
    public void createTest(){
        //1
        HashMap<Object, Object> objectObjectHashMap = Maps.newHashMap();
        System.out.println(objectObjectHashMap);

        //2
        ImmutableMap<String, Integer> stringIntegerImmutableMap = Maps.uniqueIndex(Lists.newArrayList(1, 2, 3), key -> "key_" + key);
        System.out.println(stringIntegerImmutableMap);

        //3
        Map<Integer, String> integerStringMap1 = Maps.toMap(Lists.newArrayList(1, 2, 3), value -> "value_" + value);
        System.out.println(integerStringMap1);

        //4
        Map<Integer, String> integerStringMap = Maps.asMap(Sets.newHashSet(1, 2, 3), value -> "value_" + value);
        System.out.println(integerStringMap);

        //5
        Map<String, Boolean> options = ImmutableMap.of("verbose", true, "sort", false);
        System.out.println(options);

    }

    /**
     * transform
     *          1、transformEntries: 可获取entry的 function 可根据key值对value做出修改 形成新的map
     *          2、transformValues: 相当于对对map 的 key 循环修改value
     * @Author LeeYoung
     **/
    @Test
    public void transformTest(){
        Map<String, Boolean> map = ImmutableMap.of("commons", true, "guava", false,"hutool",false);
        Map<String, ? extends Serializable> stringMap = Maps.transformEntries(map, (k, v) -> v ? v : "no_" + k);
        Maps.transformEntries(map, (k, v) -> v ? v : "no_" + k);
        System.out.println(stringMap);

        Map<String, String> map2 = Maps.toMap(Lists.newArrayList("commons", "guava", "hutool","lee"), value -> value);
        Map<String, String> stringStringMap1 = Maps.transformEntries(map2, (k, v) -> k.length() > 3 ? v : k.length() + "_" + k);
        System.out.println(stringStringMap1);

        Map<String, Boolean> map3 = ImmutableMap.of("commons", true, "guava", false,"hutool",false);
        Map<String, String> stringStringMap = Maps.transformValues(map3, v -> "string");
        System.out.println(stringStringMap);
    }

    /**
     * filter过滤器：
     *            1、filterKeys 根据key 进行过滤
     *            2、filterValues 根据value 进行过滤
     *            3、filterEntries 根据 key 或者 value 都可以进行过滤
     *            4、filter 入参都为 Predicate 类型的 function
     * @Author LeeYoung
     **/
    @Test
    public void filterTest(){
        Map<String, Boolean> map = ImmutableMap.of("commons", true, "guava", false,"hutool",false,"lee",true);
        Map<String, Boolean> stringBooleanMap = Maps.filterKeys(map, k -> k.length() > 3);
        System.out.println(stringBooleanMap);

        Map<String, String> map2 = Maps.toMap(Lists.newArrayList("commons", "guava", "hutool","lee"), value -> value);
        Map<String, String> stringStringMap = Maps.filterValues(map2, v -> v.length() <= 3);
        System.out.println(stringStringMap);

        Map<String, Boolean> map3 = ImmutableMap.of("commons", true, "guava", false,"hutool",false,"lee",true);
        Map<String, Boolean> stringStringMap1 = Maps.filterEntries(map3, entry -> entry.getKey().length() > 5 && entry.getValue() == true);
        assertThat(stringStringMap1.containsKey("commons"),equalTo(true));
        assertThat(stringStringMap1.containsKey("hutool"),equalTo(false));
    }


}
