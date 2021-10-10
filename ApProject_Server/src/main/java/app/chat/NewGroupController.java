package app.chat;

import controller.ClientHandler;
import events.visitors.chat.NewGroupPanelEventVisitor;
import models.auth.User;
import controller.MainController;
import responses.Response;
import responses.chat.LoadFriendsResponseForNewGroupPanel;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class NewGroupController extends MainController implements
        NewGroupPanelEventVisitor {

    private final ClientHandler clientHandler;
    private final ContactPanelController contactPanelController;

    public NewGroupController(ClientHandler clientHandler,
                              ContactPanelController contactPanelController) {
        this.clientHandler = clientHandler;
        this.contactPanelController = contactPanelController;
    }

    public Response loadFriends() {
        try {
            User current = context.getUsers().get(clientHandler.getClientId());
            List<String[]> info = new LinkedList<>();
            for (Integer id: current.getFollowingUsername()) {
                User user = context.getUsers().get(id);
                if(!user.getBlackUsername().contains(clientHandler.getClientId())
                        && user.getAccount().isActive()){
                    String[] fo = new String[5];
                    fo[0] = String.valueOf(id);
                    fo[1] = user.getAccount().getUsername();
                    fo[2] = contactPanelController.loadImage(id);
                    fo[4] = String.valueOf(id);
                    info.add(fo);
                }
            }
            return new LoadFriendsResponseForNewGroupPanel(info);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}