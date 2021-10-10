package responses.visitors.chat;

import responses.chat.DeleteMessageResponse;
import responses.chat.LoadMessagesResponse;
import responses.chat.SendMessageResponse;
import responses.visitors.ResponseVisitor;


public interface ChatMainPanelResponseVisitor extends ResponseVisitor {

    void getMessages(LoadMessagesResponse response);
    void newMessage(SendMessageResponse response);
    void editMessage(SendMessageResponse response);
    void deleteMessageResponse(DeleteMessageResponse response);
}
