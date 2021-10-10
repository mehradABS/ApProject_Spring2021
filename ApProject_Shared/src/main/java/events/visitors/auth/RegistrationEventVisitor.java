package events.visitors.auth;

import events.BotEvent;
import events.auth.RegistrationEvent;
import events.visitors.EventVisitor;
import responses.Response;

public interface RegistrationEventVisitor extends EventVisitor {

    Response checkEvent(RegistrationEvent event);
    Response createNewBot(BotEvent event);
}
