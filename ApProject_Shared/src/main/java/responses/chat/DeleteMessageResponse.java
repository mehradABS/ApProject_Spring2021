package responses.chat;

import models.OChat;
import models.messages.OMessage;
import responses.Response;
import responses.visitors.ResponseVisitor;
import responses.visitors.chat.ChatMainPanelResponseVisitor;

public class DeleteMessageResponse extends Response {

    private OChat chat;
    private OMessage message;
    private int messageId;

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        ((ChatMainPanelResponseVisitor)responseVisitor).deleteMessageResponse(this);
    }

    @Override
    public String getVisitorType() {
        return "ChatMainPanelResponseVisitor";
    }

    public OChat getChat() {
        return chat;
    }

    public void setChat(OChat chat) {
        this.chat = chat;
    }

    public OMessage getMessage() {
        return message;
    }

    public void setMessage(OMessage message) {
        this.message = message;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
}
