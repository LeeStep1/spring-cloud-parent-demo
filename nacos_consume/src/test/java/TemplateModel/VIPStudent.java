package TemplateModel;

public class VIPStudent extends AbstrcatSend {

    boolean b;
    @Override
    boolean isPullEs() {
        return this.b;
    }

    @Override
    void initUserList() {
        System.out.println("初始化VIP学生");
    }

    public VIPStudent( boolean b) {
        this.b = b;
    }
}
