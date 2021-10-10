package responses.chat;

import responses.Response;
import responses.visitors.ResponseVisitor;
import responses.visitors.chat.ContactPanelResponseVisitor;

public class DeleteChatResponse extends Response {

    private final int chatId;

    public DeleteChatResponse(int chatId) {
        this.chatId = chatId;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        ((ContactPanelResponseVisitor)responseVisitor).deleteChat(chatId);
    }

    @Override
    public String getVisitorType() {
        return "ContactPanelResponseVisitor";
    }
}
