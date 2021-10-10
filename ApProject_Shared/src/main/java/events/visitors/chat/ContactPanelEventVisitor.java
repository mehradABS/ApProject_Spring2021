package events.visitors.chat;

import events.visitors.EventVisitor;
import responses.Response;

import java.util.List;

public interface ContactPanelEventVisitor extends EventVisitor {

    Response loadChats();
    Response makeNewChat(int userId);
    Response loadGroupChat();
    Response loadMembersOfChat(int chatId, boolean f);
    Response removePersonFromChat(int chatId, List<Integer> userIds);
    Response addPersonToChat(int chatId, List<Integer> userIds);
    Response loadFollowings(int chatId);
    Response makeNewGroupChat(List<Integer> ids, String chatName);
}
