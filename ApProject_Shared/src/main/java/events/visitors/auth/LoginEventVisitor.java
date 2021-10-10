package events.visitors.auth;

import events.auth.LoginEvent;
import events.visitors.EventVisitor;
import responses.Response;


public interface LoginEventVisitor extends EventVisitor {

   Response checkLogin(LoginEvent event);
}
