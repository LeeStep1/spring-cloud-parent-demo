package com.lee.user.commonUtil;

import com.lee.user.bean.Order;
import com.lee.user.feign.OrderFeign;
import com.lee.user.service.UserService;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class TransactionListenerImpl implements TransactionListener {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderFeign orderFeign;

    /**
     * 消息处理是否成功
     */
    private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

    /**
     * 执行本地事务方法
     * @param msg 消息
     * @param arg
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        System.out.print("正在执行本地事务逻辑.");
        for (int i = 0; i < 30; i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print(".");
        }
//        localTrans.put(msg.getTransactionId(),1);
        try {
            Order order = new Order();
            order.setUserId(1L);
            order.setProductName("分布式测试1");
            orderFeign.addOne(order);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }

        localTrans.put(msg.getTransactionId(),3);

        //这里写unknown 表示这个值不起作用，最后消息发出与否 只与下面的check方法有关系，check方法说成功就是成功，说失败就失败
        //如果这里写成功，则不管下面的check返回什么 都会成功
        return LocalTransactionState.UNKNOW;
    }

    /**
     * 事务消息状态查询
     * @param msg
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        Integer status = localTrans.get(msg.getTransactionId());
        if(status == null){
            System.out.println("这么半天一个都没插入....直接回滚.....");
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }

        if(status == 3){
            System.out.println("检测到成功了....");
            return LocalTransactionState.COMMIT_MESSAGE;
        }else{
            System.out.println("还没执行完....");
            return LocalTransactionState.UNKNOW;
        }
    }
}
