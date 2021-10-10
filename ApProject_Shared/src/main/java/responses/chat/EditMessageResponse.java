package responses.chat;

import responses.visitors.ResponseVisitor;
import responses.visitors.chat.ChatMainPanelResponseVisitor;

public class EditMessageResponse extends SendMessageResponse{

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        ((ChatMainPanelResponseVisitor)responseVisitor).editMessage(this);
    }
}
