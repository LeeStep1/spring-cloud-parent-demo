package TemplateModel;

import javax.servlet.annotation.HandlesTypes;

public abstract class AbstrcatSend {

    void send(){
        initUserList();

        if(isPullEs()){
            sendToEs();
        }

        sendToUser();

    }

    private void sendToUser() {
        System.out.println("根据人员推送.....");
    }

    private void sendToEs(){
        System.out.println("推送至ES....");
    };

    abstract boolean isPullEs();

    abstract void initUserList();



}
