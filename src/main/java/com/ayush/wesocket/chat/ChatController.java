package com.ayush.websocket.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/general.sendMessage") //url to invoke the message
    @SendTo("/topic/general")
    public ChatMessage sendMessageGeneral(
            @Payload ChatMessage chatMessage
    ) {
        return chatMessage;
    }

    @MessageMapping("/general.addUser")
    @SendTo("/topic/general")
    public ChatMessage addUserGeneral(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Add username in web socket session
    	headerAccessor.getSessionAttributes().put("room", "general");
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
    
    @MessageMapping("/first.sendMessage") //url to invoke the message
    @SendTo("/topic/firstYear")
    public ChatMessage sendMessageFirst(
            @Payload ChatMessage chatMessage
    ) {
        return chatMessage;
    }

    @MessageMapping("/first.addUser")
    @SendTo("/topic/firstYear")
    public ChatMessage addUserFirst(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Add username in web socket session
    	headerAccessor.getSessionAttributes().put("room", "firstYear");
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
    
    @MessageMapping("/second.sendMessage") //url to invoke the message
    @SendTo("/topic/secondYear")
    public ChatMessage sendMessageSecond(
            @Payload ChatMessage chatMessage
    ) {
        return chatMessage;
    }

    @MessageMapping("/second.addUser")
    @SendTo("/topic/secondYear")
    public ChatMessage addUserSecond(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Add username in web socket session
    	headerAccessor.getSessionAttributes().put("room", "secondYear");
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
    
    @MessageMapping("/third.sendMessage") //url to invoke the message
    @SendTo("/topic/thirdYear")
    public ChatMessage sendMessageThird(
            @Payload ChatMessage chatMessage
    ) {
        return chatMessage;
    }

    @MessageMapping("/third.addUser")
    @SendTo("/topic/thirdYear")
    public ChatMessage addUserThirds(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Add username in web socket session
    	headerAccessor.getSessionAttributes().put("room", "thirdYear");
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
    
    @MessageMapping("/fourth.sendMessage") //url to invoke the message
    @SendTo("/topic/fourthYear")
    public ChatMessage sendMessageFourth(
            @Payload ChatMessage chatMessage
    ) {
        return chatMessage;
    }

    @MessageMapping("/fourth.addUser")
    @SendTo("/topic/fourthYear")
    public ChatMessage addUserFourth(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Add username in web socket session
    	headerAccessor.getSessionAttributes().put("room", "fourthYear");
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}
