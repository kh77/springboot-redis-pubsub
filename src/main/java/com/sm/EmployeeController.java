package com.sm;

import com.sm.sender.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @Autowired
    MessagePublisher publisher;

    @Autowired
    @Qualifier("employeeTopic")
    private ChannelTopic employeeTopic;

    @Autowired
    @Qualifier("notificationTopic")
    private ChannelTopic notificationTopic;

    @PostMapping("/send")
    public void sendUpdate(@RequestParam("message") String message) {
        publisher.publish(employeeTopic.getTopic(), message);
        //publisher.publish(RedisConstant.EMPLOYEE_TOPIC, message);
    }

    @PostMapping("/notify")
    public void notify(@RequestParam("message") String message) {
        publisher.publish(notificationTopic.getTopic(), message);
        // publisher.publish(RedisConstant.NOTIFICATION_TOPIC, message);
    }
}
