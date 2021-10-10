package events.notofication;

import events.Event;
import events.visitors.EventVisitor;
import responses.Response;
import events.visitors.notification.NotifEventVisitor;

public class NotifEvent extends Event {

    private int reqId;
    private String event;

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((NotifEventVisitor)eventVisitor).getEvent(this);
    }

    @Override
    public String getVisitorType() {
        return "NotifEventVisitor";
    }

    public int getReqId() {
        return reqId;
    }

    public void setReqId(int reqId) {
        this.reqId = reqId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
