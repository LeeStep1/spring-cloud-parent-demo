package com.lee.user.service.imp;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.user.bean.Order;
import com.lee.user.bean.User;
import com.lee.user.commonUtil.TransactionListenerImpl;
import com.lee.user.dao.UserDao;
import com.lee.user.service.UserService;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
@RefreshScope
public class UserServiceImpl extends ServiceImpl<UserDao,User> implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private DefaultMQProducer producer;

    @Autowired
    private TransactionMQProducer transactionProducer;


    @Override
    public User getUserById(Long id) {
        User user = userDao.selectUserById(id);
        return user;
    }

    @Override
    public String chainUpdateUser() {

        List<Map<String, Object>> maps = listMaps();

        if(maps.size()<=1) return "最少有2人";

        User user = new User();
        user.setId(1L);
        user.setAge(30);
        boolean b = updateById(user);

        Order order = new Order();
        order.setUserId(user.getId());
        order.setProductName("测试一条....");

        try {

            Message message = new Message("chainUpdate",JSON.toJSONBytes(order));

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

        Message message = new Message("chainUpdate",JSON.toJSONBytes(order));

        return "发送成功";
    }

    /**
     * 发送分布式事务消息
     * @return
     */
    @Override
    public String chainUpdateUserByTransaction() {

        List<Map<String, Object>> maps = listMaps();

        if(maps.size()<=1) return "最少有2人";

        User user = new User();
        user.setName("小黑");
        user.setAge(40);

//        Order order = new Order();
//        order.setUserId(user.getId());
//        order.setProductName("测试一条....");

        //开启线程池来处理消息状态回调
//        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
//            @Override
//            public Thread newThread(Runnable r) {
//                Thread thread = new Thread(r);
//                thread.setName("client-transaction-msg-check-thread");
//                return thread;
//            }
//        });
        try {
            Message message = new Message("updateUserByTransaction",JSON.toJSONBytes(user));
            TransactionSendResult transactionSendResult = transactionProducer.sendMessageInTransaction(message,null);
            System.out.printf("%s%n", transactionSendResult);
        } catch (MQClientException e) {
            e.printStackTrace();
        }
//        try {
//            Thread.sleep(30000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return null;
    }

}
