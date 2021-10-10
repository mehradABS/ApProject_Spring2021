package events.visitors.chat;

import events.chat.DeleteMessageEvent;
import events.chat.SendMessageEvent;
import events.visitors.EventVisitor;
import responses.Response;

public interface ChatMainPanelEventVisitor extends EventVisitor {

    Response loadMessages(int chatId);
    Response newMessage(SendMessageEvent event);
    Response editMessage(SendMessageEvent event);
    Response deleteMessage(DeleteMessageEvent event);
}
