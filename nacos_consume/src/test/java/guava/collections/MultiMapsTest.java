package guava.collections;

import TemplateModel.User;
import com.google.common.collect.*;
import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @Description: MultiMapsTest
 * @Author LeeYoung
 */
public class MultiMapsTest {

    /**
     * Multimap:
     *         1、Google封装的map 接口
     *         2、可以存相同键值对
     *         3、存储相同键的时候会合并 变成 Map<Object,List<Object>>
     *         3、返回的格式为 List<value>
     * @Author LeeYoung
     **/
    @Test
    public void createTest(){
        HashMap<String, String> map = Maps.newHashMap();
        map.put("1","1");
        map.put("1","2");
        assertThat(map.size(),equalTo(1));

        Multimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("1","2");
        multimap.put("1","2");
        assertThat(multimap.size(),equalTo(2));

        ArrayListMultimap<String, User> multimap2 = ArrayListMultimap.create();
        User user = new User();
        user.setId(1L);
        user.setName("lee");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("lee2");

        multimap2.put("1",user);
        multimap2.put("2",user2);
        assertThat(multimap2.size(),equalTo(2));
        System.out.println(multimap2);
    }

    /**
     * containsEntry :同时查询 key 和 value 是否包含
     * @Author LeeYoung
     **/
    @Test
    public void containsEntryTest(){
        Multimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("1","2");
        multimap.put("1","2");
        multimap.put("1","6");
        multimap.put("3","3");
        multimap.put("4","4");
        assertThat(multimap.containsEntry("1","6"),is(true));
    }

    /**
     * entries : 将map 拆成多个集合形式
     * @Author LeeYoung
     **/
    @Test
    public void entriesTest(){
        Multimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("1","2");
        multimap.put("1","2");
        Collection<Map.Entry<String, String>> entries = multimap.entries();
        System.out.println(entries);
        System.out.println(multimap);
    }
}
