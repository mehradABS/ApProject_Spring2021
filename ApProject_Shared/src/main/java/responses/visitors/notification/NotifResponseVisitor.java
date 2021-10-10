package responses.visitors.notification;

import responses.notification.NotifResponse;
import responses.visitors.ResponseVisitor;

public interface NotifResponseVisitor extends ResponseVisitor {

    void getAnswer(NotifResponse response);
}
