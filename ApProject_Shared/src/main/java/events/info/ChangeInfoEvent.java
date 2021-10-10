package events.info;

import events.auth.RegistrationEvent;

public class ChangeInfoEvent extends RegistrationEvent {

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getVisitorType() {
        return "ChangeInfoEventVisitor";
    }
}
