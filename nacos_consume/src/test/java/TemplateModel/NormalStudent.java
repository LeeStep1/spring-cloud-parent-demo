package TemplateModel;

public class NormalStudent extends AbstrcatSend {
    boolean b;

    @Override
    boolean isPullEs() {
        return this.b;
    }

    @Override
    void initUserList() {
        System.out.println("初始化普通学生.....");
    }

    public NormalStudent(boolean b){
        this.b = b;
    }
}
