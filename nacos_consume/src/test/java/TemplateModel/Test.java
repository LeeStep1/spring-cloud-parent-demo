package TemplateModel;


public class Test {

    public void test(Object object){
        SelfAnn annotation = object.getClass().getAnnotation(SelfAnn.class);
        int type = annotation.value();

        if(type == 5){
            AbstrcatSend normalStudent = new NormalStudent(true);
            normalStudent.send();
        }else {
            AbstrcatSend vipStudent = new VIPStudent(false);
            vipStudent.send();
        }

    }

    @SelfAnn(5)
    public void normalSend(){
    }

    @SelfAnn(6)
    public void vipSend(){

    }
}
