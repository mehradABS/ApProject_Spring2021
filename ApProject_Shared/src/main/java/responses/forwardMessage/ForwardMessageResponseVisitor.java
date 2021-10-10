package responses.forwardMessage;

import responses.visitors.ResponseVisitor;

public interface ForwardMessageResponseVisitor extends ResponseVisitor {

    void getAnswer(ForwardMessageResponse response);
}
