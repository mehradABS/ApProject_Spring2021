package app.explore.search;

import models.auth.User;
import controller.ClientHandler;
import controller.MainController;
import events.visitors.search.SearchEventVisitor;
import responses.Response;
import responses.search.SearchResponse;

import java.io.IOException;
import java.util.List;

public class SearchController extends MainController implements
        SearchEventVisitor {


    private final ClientHandler clientHandler;

    public SearchController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response findUser(String username){
        SearchResponse response = new SearchResponse();
        try {
            User current = context.getUsers().get(clientHandler.getClientId());
            List<User> users = context.getUsers().getAll();
            User user = null;
            for (User allUser : users) {
                if(allUser.getAccount().isActive()) {
                    if (allUser.getAccount().getUsername().equals(username)
                            && !username.equals(current.getAccount().getUsername())) {
                        user = allUser;
                        break;
                    }
                }
            }
            if (user != null) {
                response.setId(user.getId());
            } else {
                response.setId(-1);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return response;
    }
}