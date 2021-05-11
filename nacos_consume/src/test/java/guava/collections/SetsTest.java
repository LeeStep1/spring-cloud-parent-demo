package guava.collections;


import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Set;

/**
 * Sets
 * @Author LeeYoung
 **/
public class SetsTest {

    /**
     * combinations :
     *              1、作用：将一个set拆成N个 set集合
     *              2、入参1：要拆除的集合   入参2: 要拆成几个集合(这个值必须小于 set的size)
     * @Author LeeYoung
     **/
    @Test
    public void combinationsTest(){
        Set<Integer> set = Sets.newHashSet(1,2,3);
        Set<Set<Integer>> combinations = Sets.combinations(set, 2);
        combinations.forEach(System.out::println);
    }

    /**
     * 差集
     * @Author LeeYoung
     **/
    @Test
     public void differenceTest(){
         Set<Integer> set = Sets.newHashSet(1,2,3);
         Set<Integer> set2 = Sets.newHashSet(1,4,6);

         Sets.SetView<Integer> difference = Sets.difference(set, set2);
        System.out.println(difference);
     }

     /**
      * 交集
      * @Author LeeYoung
      **/
     @Test
     public void intersectionTest(){
         Set<Integer> set = Sets.newHashSet(1,2,3);
         Set<Integer> set2 = Sets.newHashSet(1,4,6);
         Sets.SetView<Integer> intersection = Sets.intersection(set, set2);
         System.out.println(intersection);
     }

     /**
      * 并集
      * @Author LeeYoung
      **/
     @Test
     public void unionTest(){
         Set<Integer> set = Sets.newHashSet(1,2,3);
         Set<Integer> set2 = Sets.newHashSet(1,4,6);
         Sets.SetView<Integer> union = Sets.union(set, set2);
         System.out.println(union);

     }
}
