package events.auth;

import events.Event;
import events.visitors.EventVisitor;
import events.visitors.auth.LoginEventVisitor;
import responses.Response;

public class LoginEvent extends Event {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((LoginEventVisitor)eventVisitor).checkLogin(this);
    }

    @Override
    public String getVisitorType() {
        return "LoginEventVisitor";
    }
}
