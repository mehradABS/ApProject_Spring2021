package events.chat;

import events.Event;
import events.visitors.EventVisitor;
import events.visitors.chat.ChatMainPanelEventVisitor;
import responses.Response;

public class DeleteMessageEvent extends Event {

    private int chatId;
    private int messageId;

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((ChatMainPanelEventVisitor)eventVisitor).deleteMessage(this);
    }

    @Override
    public String getVisitorType() {
        return "ChatMainPanelEventVisitor";
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
}
