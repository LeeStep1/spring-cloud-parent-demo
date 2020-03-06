package TemplateModel;

public class NormalStudent extends AbstrcatSend {
    private boolean b;

    private final Integer type = 1;


    @Override
    boolean isPullEs() {
        return this.b;
    }

    @Override
    void initUserList() {
        System.out.println("初始化普通学生.....");
    }

    @Override
    int type() {
        return this.type;
    }

    public NormalStudent(boolean b){
        this.b = b;
    }


}
