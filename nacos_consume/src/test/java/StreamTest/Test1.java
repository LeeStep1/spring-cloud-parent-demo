package StreamTest;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test1 {

    List<People> people = Arrays.asList(new People(1L,"小张"),new People(2L,"小李"));
    @Test
    public void demo1(){
        Map<Long,String> map = people.stream().collect(Collectors.toMap(People::getId,people1 -> people1.getName()));
    }
}
