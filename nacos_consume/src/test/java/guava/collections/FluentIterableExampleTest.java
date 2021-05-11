package guava.collections;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableListMultimap;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Functions.toStringFunction;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @Description: FluentIterable 测试类
 * @Author LeeYoung
 */
public class FluentIterableExampleTest {

    public FluentIterable<String> build(){
        ArrayList<String> strings = Lists.newArrayList("commons", "guava", "hutool","lee");
        return FluentIterable.from(strings);
    }

    /**
     * filter 过滤器 使用 类似于 Java8 Stream.filter
     * @Author LeeYoung
     **/
    @Test
    public void filterTest(){
        FluentIterable<String> fit = build();
        assertThat(fit.size(),equalTo(4));

        FluentIterable<String> filter = fit.filter(e -> e != null && e.length() > 3);
        assertThat(filter.size(),equalTo(3));

        //对比stream
        ArrayList<String> strings = Lists.newArrayList("commons", "guava", "hutool","lee");
        List<String> collect = strings.stream().filter(e -> e != null && e.length() > 3).collect(Collectors.toList());
        assertThat(collect.size(),equalTo(3));
    }

    /**
     * append 方法
     *          1、追加方法是生成一个新的FluentIterable,不会影响老的值
     * @Author LeeYoung
     **/
    @Test
    public void appendTest(){
        FluentIterable<String> fit = build();
        assertThat(fit.size(),equalTo(4));

        FluentIterable<String> append = fit.append("APPEND");
        assertThat(fit.size(),equalTo(4));
        assertThat(append.size(),equalTo(5));
        assertThat(append.contains("APPEND"),equalTo(true));

        FluentIterable<String> append2 = append.append("APPEND2");
        assertThat(append2.size(),equalTo(6));
        assertThat(append2.contains("APPEND"),equalTo(true));
        assertThat(append2.contains("APPEND2"),equalTo(true));
    }

    /**
     * match方法:
     *          1、anyMatch: 只要有任意一个满足条件，返回true
     *          2、allMatch: 所有的条件全部满足，返回true
     *          3、firstMatch: 只要有一个满足，立即停止，并返回该值  Optional防止空指针
     * @Author LeeYoung
     **/
    @Test
    public void match(){
        FluentIterable<String> fit = build();
        boolean b = fit.anyMatch(e -> e.length() > 6);
        assertThat(b,equalTo(true));

        boolean b1 = fit.allMatch(e -> e.length() > 6);
        assertThat(b1,equalTo(false));

        Optional<String> stringOptional = fit.firstMatch(e -> e.length() == 3);
        assertThat(stringOptional.isPresent(),equalTo(true));
        assertThat(stringOptional.get(),equalTo("lee"));
    }

    /**
     * 返回集合中的第一个和最后一个元素
     * @Author LeeYoung
     **/
    @Test
    public void firstAndLast(){
        FluentIterable<String> fit = build();
        Optional<String> first = fit.first();
        assertThat(first.get(),equalTo("commons"));

        Optional<String> last = fit.last();
        assertThat(last.get(),equalTo("lee"));
    }


    /**
     * 返回部分集合
     *           1、按照集合中顺序返回
     *           2、如果写入的数字大于集合长度，则返回集合长度
     * @Author LeeYoung
     **/
    @Test
    public void limit(){
        FluentIterable<String> fit = build();
        FluentIterable<String> limit = fit.limit(3);
        assertThat(limit.size(),equalTo(3));
        assertThat(limit.contains("lee"),equalTo(false));

        FluentIterable<String> limit2 = fit.limit(300);
        assertThat(limit2.size(),equalTo(4));
        assertThat(limit2.contains("lee"),equalTo(true));

    }

    /**
     * copyIn:
     *        1、将一个 fluentIterable 以追加的形式拷贝到 一个 Collection 中
     *        2、先将 FluentIterable 转换成 Iterable , 再将 Iterable 转换成Collection 再调用 addAll
     * @Author LeeYoung
     **/
    @Test
    public void copyInTest(){
        FluentIterable<String> fit = build();
        ArrayList<String> spring = Lists.newArrayList("spring");
        ArrayList<String> strings = fit.copyInto(spring);
        assertThat(strings.contains("spring"),equalTo(true));
        assertThat(strings.contains("guava"),equalTo(true));
        assertThat(strings.size(),equalTo(5));

        assertThat(fit.size(),equalTo(4));
        assertThat(fit.contains("guava"),equalTo(true));
        assertThat(fit.contains("spring"),equalTo(false));
    }

    /**
     * cycle
     *       1、无线循环一个集合
     *       2、结合limit使用，产生一个新的集合
     * @Author LeeYoung
     **/
    @Test
    public void cycleTest(){
        FluentIterable<String> fit = build();
        FluentIterable<String> cycle = fit.cycle().limit(30);
        cycle.forEach(System.out::println);
    }

    /**
     * tranform 生成新的集合
     * @Author LeeYoung
     **/
    @Test
    public void transformTest(){
        FluentIterable<String> fit = build();
        fit.transform(e->e.substring(0,3)).forEach(System.out::println);
    }


    /**
     * transformAndConcat:
     *                  1、根据 FluentIterable 批量执行 一个 Iterable的子类(集合)
     *                  2、例子中 FluentIterable 的 size是 4,则执行 4次 simulation()方法
     *                  3、simulation() 返回值必须为 Iterable 子类 即 返回值必须为集合
     *                  4、将4个集合进行合并 类似于union All 或者  Collection.addAll 以及上面列子中的 copyIn
     * @Author LeeYoung
     **/
    @Test
    public void transformAndConcatTest(){
        FluentIterable<String> fit = build();
        FluentIterable<String> strings = fit.transformAndConcat(e -> simulation(5));
        assertThat(strings.size(),equalTo(20));
    }

    public List<String> simulation(int num){
        ArrayList<String> list = Lists.newArrayList();
        for (int i = 0; i < num; i++) {
            String a = "spring" + i ;
            list.add(a);
        }
        return list;
    }

    /**
     * 将集合转换为string
     * @Author LeeYoung
     **/
    @Test
    public void joinTest(){
        FluentIterable<String> fit = build();
        String join = fit.join(Joiner.on(","));
        assertThat(join,equalTo("commons,guava,hutool,lee"));
    }

    /**
     * index:
     *      1、将一个list转成一个 不可变的 map
     *      2、入参是一个function 此function 作用为定义 key 的值
     * @Author LeeYoung
     **/
    @Test
    public void test(){
        FluentIterable<String> fit = build();
        ImmutableListMultimap<String, String> index = fit.index(e -> "key" + e);
        System.out.println(index);
    }
}
