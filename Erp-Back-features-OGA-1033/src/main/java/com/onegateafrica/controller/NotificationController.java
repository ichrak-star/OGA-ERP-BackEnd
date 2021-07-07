package com.onegateafrica.controller;

import com.onegateafrica.Utile.Notifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;

public class NotificationController {

    @Autowired
    private SimpMessagingTemplate template;

    private Notifications notification = new Notifications(0);

    @GetMapping("notify")
    public String getnotification(){

        notification.increment();

        template.convertAndSend("topic/notification",notification);

        return "Notification successfully sent to Angular";
    }
}
