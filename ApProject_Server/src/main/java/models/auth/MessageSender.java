package models.auth;

import events.chat.SendMessageEvent;

public interface MessageSender {

    void sendMessage(SendMessageEvent event, int botId);
}
