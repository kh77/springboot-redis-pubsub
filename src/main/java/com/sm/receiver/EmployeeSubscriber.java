package com.sm.receiver;


import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class EmployeeSubscriber implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] bytes) {
        System.out.println("Employee message received : " + message.toString());
    }
}
