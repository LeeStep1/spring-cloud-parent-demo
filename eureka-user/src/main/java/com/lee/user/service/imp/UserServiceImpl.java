package com.lee.user.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.user.bean.User;
import com.lee.user.dao.UserDao;
import com.lee.user.service.UserService;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RefreshScope
public class UserServiceImpl extends ServiceImpl<UserDao,User> implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private DefaultMQProducer producer;

    @Override
    public User getUserById(Long id) {
        User user = userDao.selectUserById(id);
        return user;
    }

    @Override
    public String chainUpdateUser() {

        List<Map<String, Object>> maps = listMaps();

        if(maps.size()<=1) return "最少有2人";

        try {
            producer.start();

            Message message = new Message("chainUpdate","我是第一个，我改完了.....".getBytes());

            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("发送成功......");
                }

                @Override
                public void onException(Throwable e) {
                    System.out.println("发送失败.....");
                }
            });
        }catch (Exception e){
            return "发送出错了,错误是：" + e.getMessage();
        }


        return "发送成功";
    }


}
