package guava.collections;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.junit.Test;

/**
 * @Description: BiMapTest
 * @Author LeeYoung
 * @Date: 2021/5/12
 */
public class BiMapTest {

    /**
     * BiMap：
     *      1、Google提供的map接口
     *      2、实现功能与MultiMap 相反， 键重复覆盖 ，值重复抛异常
     * @Author LeeYoung
     **/
    @Test
    public void createTest(){
        HashBiMap<String,String> biMap = HashBiMap.create();
        biMap.put("1","1");
        biMap.put("1","2");
        System.out.println(biMap);

        HashBiMap<String,String> biMap2 = HashBiMap.create();
        biMap2.put("1","2");
//        biMap2.put("2","2");
//        System.out.println(biMap);
    }

    /**
     * forcePut : 加重复值不报错了，但是会覆盖
     * @Author LeeYoung
     **/
    @Test
    public void forcePutTest(){
        HashBiMap<String,String> biMap = HashBiMap.create();
        biMap.forcePut("1","1");
        biMap.forcePut("2","1");
        System.out.println(biMap);
    }

    /**
     * inverse  key  value 反转
     * @Author LeeYoung
     **/
    @Test
    public void inverseTest(){
        HashBiMap<String,String> biMap = HashBiMap.create();
        biMap.forcePut("1","1");
        biMap.forcePut("2","3");
        BiMap<String, String> inverse = biMap.inverse();
        System.out.println(inverse);
    }
}
