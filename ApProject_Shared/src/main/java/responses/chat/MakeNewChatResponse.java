package responses.chat;

import models.OChat;
import responses.Response;
import responses.visitors.ResponseVisitor;
import responses.visitors.chat.MainPanelVisitor;

public class MakeNewChatResponse extends Response {

    private boolean isNewChat = false;
    private final OChat chat;

    public MakeNewChatResponse(boolean isNewChat, OChat chat) {
        this.chat = chat;
        this.isNewChat = isNewChat;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        ((MainPanelVisitor)responseVisitor).chatPanelHandling(chat, isNewChat);
    }

    @Override
    public String getVisitorType() {
        return "MainPanelVisitor";
    }

    public int getChatID() {
        return chat.getId();
    }
}
