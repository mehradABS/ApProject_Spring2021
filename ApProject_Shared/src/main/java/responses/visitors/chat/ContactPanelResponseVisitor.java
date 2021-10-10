package responses.visitors.chat;

import responses.chat.LoadChatResponse;
import responses.chat.LoadGroupChatResponse;
import responses.chat.LoadMemberOfChatResponse;
import responses.visitors.ResponseVisitor;

import java.util.List;

public interface ContactPanelResponseVisitor extends ResponseVisitor {

     void getChats(LoadChatResponse response);
     void setGroupChatsInGroupSettingPanel(LoadGroupChatResponse response);
     void setMemberOfChatInGroupSettingPanel(LoadMemberOfChatResponse response);
     void setFollowingsInGroupSettingPanel(List<String[]> info);
     void setNewGroupPanelInfo(List<String[]> info);
     void deleteChat(int chatId);
}
