package responses.chat;

import models.OChat;
import models.auth.OUser;
import models.messages.OMessage;
import responses.Response;
import responses.visitors.ResponseVisitor;
import responses.visitors.chat.ChatMainPanelResponseVisitor;

public class SendMessageResponse extends Response {

    protected OMessage message;
    protected OChat chat;
    protected OUser user;
    protected String[] info;

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        ((ChatMainPanelResponseVisitor)responseVisitor).newMessage(this);
    }

    @Override
    public String getVisitorType() {
        return "ChatMainPanelResponseVisitor";
    }

    public OMessage getMessage() {
        return message;
    }

    public void setMessage(OMessage message) {
        this.message = message;
    }

    public OChat getChat() {
        return chat;
    }

    public void setChat(OChat chat) {
        this.chat = chat;
    }

    public OUser getUser() {
        return user;
    }

    public void setUser(OUser user) {
        this.user = user;
    }

    public String[] getInfo() {
        return info;
    }

    public void setInfo(String[] info) {
        this.info = info;
    }
}