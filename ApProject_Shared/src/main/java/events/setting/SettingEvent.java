package events.setting;

import events.Event;
import events.visitors.EventVisitor;
import events.visitors.setting.SettingPanelEventVisitor;
import responses.Response;

public class SettingEvent extends Event {

    private String lastSeen;
    private boolean activate;
    private String event;

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((SettingPanelEventVisitor) eventVisitor).getEvent(this);
    }

    @Override
    public String getVisitorType() {
        return "SettingPanelEventVisitor";
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public boolean isActivate() {
        return activate;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
