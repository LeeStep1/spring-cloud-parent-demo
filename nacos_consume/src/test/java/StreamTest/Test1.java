package StreamTest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test1 {

    List<People> people = Arrays.asList(new People(1L,"小张"),new People(2L,"小李"));
    @Test
    public void demo1(){
        Map<Long,List<People>> map = people.stream().collect(Collectors.toMap(People::getId,aaa -> people));

        for (Map.Entry m : map.entrySet()){
            System.out.println("key  " +m.getKey() + " ,value  " + m.getValue());
        }



    }

    @Test
    public void demo2(){
        List list = new ArrayList();

        Map<Long,List<String>> map2 = people.stream().collect(Collectors.toMap(People::getId, aaa->{
            people.forEach(a->{
                list.add(a.getName());
            });
            return list;
        }));

        for (Map.Entry m : map2.entrySet()){
            System.out.println("key  " +m.getKey() + " ,value  " + m.getValue());
        }
    }

    @Test
    public void demo3(){
        List<String> list = new ArrayList<>();
        Map<Long,List<String>> map = people.stream().collect(Collectors.toMap(a->a.getId(),b->{
            String tempName = b.getName();
            list.add(tempName);
            return list;
        }));

        for (Map.Entry m :map.entrySet()){
            System.out.println("key  " +m.getKey() + " ,value  " + m.getValue());
        }
    }


    @Test
    public void demo4(){
        List<String> list = new ArrayList<>();
        Map<Long,List<String>> map =people.stream().collect(Collectors.toMap(a->{
            a.getId();
            System.out.println(" a.getId() 是 ： " +  a.getId());
            System.out.println("           ");
            System.out.println("==================================");
            System.out.println("           ");
            return a.getId();
        },b->{
            System.out.println("b 是 :" + b);
            System.out.println("bname 是 :" + b.getName());
            String tempName = b.getName();
            list.add(tempName);
            System.out.println("list 是 :" + list);
            System.out.println("           ");
            System.out.println("==================================");
            System.out.println("           ");
            return list;
        }));

//        System.out.println(map);
    }

    @Test
    public void demo5(){
        Map<Long,List<String>> map = people.stream().collect(Collectors.toMap(People::getId,aaa->{
            List list = new ArrayList();
            people.forEach(t->{
                list.add(t.getName());
            });
            return list;
        }));

        for (Map.Entry m :map.entrySet()){
            System.out.println("key  " +m.getKey() + " ,value  " + m.getValue());
        }
    }
}
