package events;

import events.visitors.EventVisitor;
import events.visitors.OnlineEventVisitor;
import responses.Response;

import java.net.Socket;

public class OnlineEvent extends Event{

    transient private final Socket socket;
    private int clientId;

    public OnlineEvent(Socket socket) {
        this.socket = socket;
    }

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((OnlineEventVisitor)eventVisitor).checkConnection(clientId);
    }

    @Override
    public String getVisitorType() {
        return "OnlineEventVisitor";
    }

    public Socket getSocket() {
        return socket;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int id) {
        this.clientId = id;
    }
}