package com.nacosDemo.until;

/**
 * @description:
 * @author: liyang
 * @date: 2020-01-03
 **/
public class RequestThread {

    public static ThreadLocal<String> local = new ThreadLocal<>();

    public static void bindThread(String request){
        local.set(request);
    }

    public static String getThread(){
        return local.get();
    }
}
