package responses.chat;

import models.OChat;
import models.auth.OUser;
import models.messages.OMessage;
import responses.Response;
import responses.visitors.ResponseVisitor;
import responses.visitors.chat.ChatMainPanelResponseVisitor;

import java.util.List;

public class LoadMessagesResponse extends Response {

    private final List<String[]> info;
    private final List<OMessage> chatMessages;
    private final OChat chat;
    private final OUser user;

    public LoadMessagesResponse(List<String[]> info,
                                List<OMessage> chatMessages, OChat chat, OUser user) {
        this.info = info;
        this.chatMessages = chatMessages;
        this.chat = chat;
        this.user = user;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        ((ChatMainPanelResponseVisitor)responseVisitor).getMessages(this);
    }

    @Override
    public String getVisitorType() {
        return "ChatMainPanelResponseVisitor";
    }

    public List<String[]> getInfo() {
        return info;
    }

    public List<OMessage> getChatMessages() {
        return chatMessages;
    }

    public OChat getChat() {
        return chat;
    }

    public OUser getUser() {
        return user;
    }
}