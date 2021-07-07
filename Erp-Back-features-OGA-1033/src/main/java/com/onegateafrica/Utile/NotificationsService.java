package com.onegateafrica.Utile;

import com.onegateafrica.Entity.UserEntity;
import com.onegateafrica.services.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class NotificationsService {

    private SimpMessagingTemplate template;
    private UserService userService;

    public NotificationsService(SimpMessagingTemplate template,UserService userService) {

        this.template = template;
        this.userService= userService;
    }



    // Initialize Notifications
//    private Notifications notifications = new Notifications(0);


    public String sendNotification(long id ) {

        UserEntity user = userService.getUserByID(id);
        // Increment Notification by one
//        notifications.increment();

        // Push notifications to front-end
        if (user.getRole().equalsIgnoreCase("Admin")){
            template.convertAndSend("/topic/notification/admin", user);

            return "Notifications successfully sent to GRH !";
        }else{
            template.convertAndSend("/topic/notification", user);
            return "Notifications successfully sent to Employee !";
        }

    }
}
