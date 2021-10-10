package responses.chat;

import models.OChat;
import models.auth.OUser;
import responses.Response;
import responses.visitors.ResponseVisitor;
import responses.visitors.chat.ContactPanelResponseVisitor;

import java.util.LinkedList;
import java.util.List;

public class LoadChatResponse extends Response {

    private List<OChat> userChats;
    private OUser user;
    private List<String[]> chatsInfo;

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        ((ContactPanelResponseVisitor)responseVisitor).getChats(this);
    }

    @Override
    public String getVisitorType() {
        return "ContactPanelResponseVisitor";
    }

    public void setUser(OUser user) {
        this.user = user;
    }

    public List<OChat> getUserChats() {
        return userChats;
    }

    public OUser getUser() {
        return user;
    }

    public List<String[]> getChatsInfo() {
        return chatsInfo;
    }

    public void setChatsInfo(List<String[]> chatsInfo) {
        this.chatsInfo = chatsInfo;
    }

    public void setUserChats(List<OChat> userChats) {
        this.userChats = userChats;
    }
}