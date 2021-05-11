package guava.collections;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.nacosDemo.bean.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @Description: Lists 测试类
 * @Author LeeYoung
 */
public class ListTest {

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
                new User(2L,"lee1",30),
                new User(3L,"lee2",40),
                new User(4L,"lee3",50));
        List<List<User>> partition1 = Lists.partition(userList, 3);
        System.out.println(partition1);
    }

}
