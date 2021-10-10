package app.auth;

import models.auth.User;
import controller.ClientHandler;
import controller.MainController;
import controller.log.Log;
import events.auth.LoginEvent;
import events.visitors.auth.LoginEventVisitor;
import resources.Texts;
import responses.Response;
import responses.auth.LoginResponse;

import java.io.IOException;
import java.time.LocalDateTime;

public class LoginController extends MainController implements LoginEventVisitor {

    private final ClientHandler clientHandler;

    public LoginController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response checkLogin(LoginEvent loginEvent){
        try {
            LoginResponse loginResponse = new LoginResponse();
            if(loginEvent.getPassword().equals("") ||
                    loginEvent.getUsername().equals("")){
                loginResponse.setAnswer("           "+Texts.NULL);
                return loginResponse;
            }
            int id = checkValidationLogin(loginEvent.getUsername(),
                    loginEvent.getPassword());
            if (id == -1) {
                loginResponse.setAnswer("   "+Texts.INVALID_LOGIN);
                return loginResponse;
            }
            else if(isOnline(id)){
                loginResponse.setAnswer("  "+Texts.YOU_ARE_ONLINE);
                return loginResponse;
            }
            else{
                clientHandler.getAuthToken().setAuthToken(addClient(id));
                clientHandler.setId(id);
                loginResponse.setAnswer("ok" + clientHandler.getAuthToken().getAuthToken());
                loginResponse.setId(id);
                Log log = new Log("login successful", LocalDateTime.now(),
                        1,id);
                Log.log(log);
                return loginResponse;
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public int checkValidationLogin(String myUsername, String password)
            throws IOException {
        for (User user: context.getUsers().getAll()) {
            if(user.getAccount().getUsername().equals(myUsername)){
                if(user.getAccount().getPassword().equals(password)){
                    return user.getId();
                }
                return -1;
            }
        }
        return -1;
    }
}