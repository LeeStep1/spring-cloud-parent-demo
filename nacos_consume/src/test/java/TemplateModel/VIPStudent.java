package TemplateModel;

public class VIPStudent extends AbstrcatSend {

    boolean b;

    private final int type = 2;
    @Override
    boolean isPullEs() {
        return this.b;
    }

    @Override
    void initUserList() {
        System.out.println("初始化VIP学生");
    }

    @Override
    int type() {
        return this.type;
    }

    public VIPStudent( boolean b) {
        this.b = b;
    }
}
