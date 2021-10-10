package events;

import events.visitors.EventVisitor;
import events.visitors.auth.RegistrationEventVisitor;
import responses.Response;

public class BotEvent extends Event{

    private String botName;
    private String url;

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((RegistrationEventVisitor)eventVisitor).createNewBot(this);
    }

    @Override
    public String getVisitorType() {
        return "RegistrationEventVisitor";
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}