package com.bit.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-25
 **/
public class BeanReflectUtil {


    public static Map<String,Object> bean2map(Object bean,String key,String value) throws Exception{
        Map<String,Object> map = new HashMap<>();
        //获取JavaBean的描述器
        BeanInfo b = Introspector.getBeanInfo(bean.getClass(),Object.class);
        //获取属性描述器
        PropertyDescriptor[] pds = b.getPropertyDescriptors();
        //对属性迭代
        for (PropertyDescriptor pd : pds) {
            //属性名称
            String propertyName = pd.getName();
            //属性值,用getter方法获取
            Method m = pd.getReadMethod();
            Object properValue = m.invoke(bean);//用对象执行getter方法获得属性值

            //把属性名-属性值 存到Map中
            map.put(propertyName, properValue);
        }
        return map;
    }



    public static Map<String,Object> bean2map1(Object bean,String key,String value) throws Exception{
        Map<String,Object> map = new HashMap<>();
        int aa=-1;
        int bb=-1;
        //获取JavaBean的描述器
        BeanInfo b = Introspector.getBeanInfo(bean.getClass(),Object.class);
        //获取属性描述器
        PropertyDescriptor[] pds = b.getPropertyDescriptors();
        //对属性迭代
        for (int i=0;i<pds.length;i++) {
            String propertyName = pds[i].getName();
            //PropertyDescriptor pd : pds
            //属性名称
            if(propertyName.equals(key)){
               aa=i;
            }
            if(propertyName.equals(value)){
              bb=i;
            }
        }
        if(aa>0&&bb>0){
            Method m1 = pds[aa].getReadMethod();
            Object properValue1 = m1.invoke(bean);

            Method m = pds[bb].getReadMethod();
            Object properValue = m.invoke(bean);
            map.put((String) properValue1, properValue);
        }
        return map;
    }


    public static Map<String,Object> listmap(List beans, String key, String value) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Map<String,Object> map = new HashMap<>();
        if (beans.size()==0){
            throw new RuntimeException("參數無效");
        }

        for(Object o:beans){
            int aa=-1;
            int bb=-1;
            BeanInfo b = Introspector.getBeanInfo(o.getClass(),Object.class);
            PropertyDescriptor[] pds = b.getPropertyDescriptors();
            //对属性迭代
            for (int i=0;i<pds.length;i++) {
                String propertyName = pds[i].getName();
                //PropertyDescriptor pd : pds
                //属性名称
                if(propertyName.equals(key)){
                    aa=i;
                }
                if(propertyName.equals(value)){
                    bb=i;
                }
            }
            if(aa>0&&bb>0){
                Method m1 = pds[aa].getReadMethod();
                Object properValue1 = m1.invoke(o);
                Method m = pds[bb].getReadMethod();
                Object properValue = m.invoke(o);
                map.put((String) properValue1, properValue);
            }
        }
        return map;

    }

}
