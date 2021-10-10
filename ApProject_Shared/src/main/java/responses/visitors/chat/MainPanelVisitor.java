package responses.visitors.chat;

import models.OChat;
import responses.visitors.ResponseVisitor;

public interface MainPanelVisitor extends ResponseVisitor {

    void chatPanelHandling(OChat chat, boolean isNewChat);
}
