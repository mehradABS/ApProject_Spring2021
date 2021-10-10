package events.visitors;


import responses.Response;

public interface OnlineEventVisitor extends EventVisitor {

    Response checkConnection(int id);
    Response logout();
    Response sendPassword();
}
