package TemplateModel;


import java.util.*;
import java.util.stream.Collectors;

public class Test {

    @org.junit.Test
    public void getAbstractTestList(){
        Map<Integer,AbstrcatSend> map = new HashMap<>();
        ServiceLoader<AbstrcatSend> loader = ServiceLoader.load(AbstrcatSend.class);
        Iterator<AbstrcatSend> iterator = loader.iterator();
        while (iterator.hasNext()){
            AbstrcatSend temp = iterator.next();
            map.put(temp.type(),temp);
        }
        System.out.println(map);
    }

    public void test(Object object){
        if(object instanceof AbstrcatSend){

            AbstrcatSend abstrcatSend = (AbstrcatSend)object;
            abstrcatSend.send();
        }

    }

    @org.junit.Test
    public void send(){
        NormalStudent student = new NormalStudent(false);
        this.test(student);

        VIPStudent vipStudent = new VIPStudent(true);
        this.test(vipStudent);
    }

}
