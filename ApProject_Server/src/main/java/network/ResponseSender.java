package network;

import events.Event;
import responses.Response;

public interface ResponseSender {

    Event getEvent();
    void sendResponse(Response response);
    void close();
}
