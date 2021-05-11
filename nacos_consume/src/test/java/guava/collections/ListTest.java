package guava.collections;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.nacosDemo.bean.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @Description: Lists 测试类
 * @Author LeeYoung
 */
public class ListTest {

    /**
     * 初始化方法：
     *          1、带数据初始化
     *          2、不带数据初始化
     *          3、带默认空间大小初始化  ** 常用 **
     *          4、数组初始化 注：必须带一个first,否则数组为空时报错
     *          5、copyOnWrite 初始化
     *          6、linkedList 初始化
     * @Author LeeYoung
     **/
    public void createTest(){
        //1
        ArrayList<Integer> integers = Lists.newArrayList(1, 2, 3);

        //2
        ArrayList<Object> objects = Lists.newArrayList();

        //3
        ArrayList<Object> objects1 = Lists.newArrayListWithCapacity(10);

        //4
        Integer[] Integer = {2,3};
        List<java.lang.Integer> list = Lists.asList(1, Integer);

        //5
        CopyOnWriteArrayList<Object> objects2 = Lists.newCopyOnWriteArrayList();

        //6
        LinkedList<Object> objects3 = Lists.newLinkedList();

    }

    /**
     * Lists.cartesianProduct :
     *                        1、笛卡尔积
     *                        2、形参为两个list
     * @Author LeeYoung
     **/
    @Test
    public void CartesianProduct(){
        List<List<String>> lists = Lists.cartesianProduct(
                Lists.newArrayList("1", "A"),
                Lists.newArrayList("2", "B")
        );

        System.out.println(lists);
    }

    /**
     * transform -> 生成一个新的list
     * @Author LeeYoung
     **/
    @Test
    public void transform(){
        ArrayList<String> strings = org.assertj.core.util.Lists.newArrayList("commons", "guava", "hutool","lee");
        List<Integer> transform = Lists.transform(strings, e -> e.length());
        System.out.println(transform);
    }

    /**
     * reverse :
     *         list 数据倒转
     * @Author LeeYoung
     **/
    @Test
    public void reverseTest(){
        ArrayList<String> strings = Lists.newArrayList("1", "2", "3");
        List<String> reverse = Lists.reverse(strings);
        assertThat(Joiner.on(",").join(reverse),equalTo("3,2,1"));
    }

    /**
     * 分区 ：
     *      1、返回相同大小的分区，最后一个区为剩余个数  形参 1 为 分区的list  2位分区后的大小
     *      2、如果分区后的大小 大于总个数 则返回一个分区
     * @Author LeeYoung
     **/
    @Test
    public void partitionTest(){
        ArrayList<String> strings = Lists.newArrayList("1", "2", "3","4");
        List<List<String>> partition = Lists.partition(strings, 3);
        System.out.println(partition);

        List<User> userList = Lists.newArrayList(new User(1L,"lee",20),
                new User(2L,"lee 1",30),
                new User(3L,"lee2",40),
                new User(4L,"lee3",50));
        List<List<User>> partition1 = Lists.partition(userList, 3);
        System.out.println(partition1);
    }


}
