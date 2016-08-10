package com.meidi.test;

//import com.meidi.util.mq.MessageSender;
import com.message.MQMessage;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by luanpeng on 16/3/21.
 */
public class ActiveMQTest {

    @Test
    public void testMQ() {
        try {


            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map<String, Object> msgMap = new HashMap<>();
            msgMap.put("time", simpleDateFormat.format(new Date()));
            MQMessage mqMessage = new MQMessage();
            mqMessage.setMsgMap(msgMap);
//            MessageSender messageSender = new MessageSender("TestMQ", mqMessage);
//            messageSender.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
