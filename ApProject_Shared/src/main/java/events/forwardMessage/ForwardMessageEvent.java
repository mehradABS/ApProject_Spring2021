package events.forwardMessage;

import events.Event;
import events.visitors.EventVisitor;
import responses.Response;

import java.util.List;

public class ForwardMessageEvent extends Event {

    private String event;
    private String messageType;
    private int messageId;
    private String responseVisitorType;
    private List<Integer> ids;

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((ForwardMessageEventVisitor)eventVisitor).getEvent(this);
    }

    @Override
    public String getVisitorType() {
        return "ForwardMessageEventVisitor";
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getResponseVisitorType() {
        return responseVisitorType;
    }

    public void setResponseVisitorType(String responseVisitorType) {
        this.responseVisitorType = responseVisitorType;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}