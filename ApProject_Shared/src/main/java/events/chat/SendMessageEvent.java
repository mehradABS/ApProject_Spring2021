package events.chat;

import events.Event;
import events.visitors.EventVisitor;
import events.visitors.chat.ChatMainPanelEventVisitor;
import responses.Response;

import java.time.LocalDateTime;

public class SendMessageEvent extends Event {

    protected String text;
    protected int chatId;
    private String encodedImage;
    private LocalDateTime localDateTime;
    private boolean offline = false;

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((ChatMainPanelEventVisitor)eventVisitor).newMessage(this);
    }

    @Override
    public String getVisitorType() {
        return "ChatMainPanelEventVisitor";
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int id) {
        this.chatId = id;
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public boolean isOffline() {
        return offline;
    }

    public void setOffline(boolean offline) {
        this.offline = offline;
    }
}