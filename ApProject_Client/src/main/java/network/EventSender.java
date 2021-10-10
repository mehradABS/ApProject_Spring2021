package network;

import events.Event;
import responses.Response;

public interface EventSender {

    Response sendEvent(Event event);

    void close();
}