package app.auth;

import events.BotEvent;
import models.auth.User;
import controller.ClientHandler;
import controller.MainController;
import controller.log.Log;
import events.auth.RegistrationEvent;
import events.visitors.auth.RegistrationEventVisitor;

import models.auth.Bot;
import resources.Texts;
import responses.BotResponse;
import responses.Response;
import responses.auth.RegistrationResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class RegisterController extends MainController implements
        RegistrationEventVisitor {

    private final ClientHandler clientHandler;

    public RegisterController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response checkEvent(RegistrationEvent registrationEvent) {
        synchronized (User.LOCK) {
            try {
                RegistrationResponse response = new RegistrationResponse();
                List<User> users = context.getUsers().getAll();
                String firstname = registrationEvent.getFirstname();
                String lastname = registrationEvent.getLastname();
                String username = registrationEvent.getUsername();
                String password = registrationEvent.getPassword();
                String email = registrationEvent.getEmail();
                String phone = registrationEvent.getPhone();
                //
                int j = email.length() - 1;
                for (; j >= 0; j--) {
                    if (email.charAt(j) != ' ') {
                        break;
                    }
                }
                email = email.substring(0, j + 1);
                int k = phone.length() - 1;
                for (; k >= 0; k--) {
                    if (phone.charAt(k) != ' ') {
                        break;
                    }
                }
                phone = phone.substring(0, k + 1);
                //
                if (firstname.equals("")) {
                    response.setAnswer(Texts.NULL);
                    return response;
                }
                if (lastname.equals("")) {
                    response.setAnswer(Texts.NULL);
                    return response;
                }
                if (username.equals("")) {
                    response.setAnswer(Texts.NULL);
                    return response;
                }
                if (password.equals("")) {
                    response.setAnswer(Texts.NULL);
                    return response;
                }
                if (email.equals("")) {
                    response.setAnswer(Texts.NULL);
                    return response;
                }
                //
                if (!checkNewUsername(username, users)) {
                    response.setAnswer(Texts.REPEATED_USERNAME);
                    return response;
                }
                if (!checkNewPassword(password, users)) {
                    response.setAnswer(Texts.REPEATED_PASSWORD);
                    return response;
                }
                if (!(email.toLowerCase().endsWith("@gmail.com") ||
                        email.toLowerCase().endsWith("@yahoo.com"))) {
                    response.setAnswer(Texts.INVALID_EMAIL);
                    return response;
                }
                if (!checkNewEmail(email, users)) {
                    response.setAnswer(Texts.REPEATED_EMAIL);
                    return response;
                }
                boolean number = true;
                if (phone.equals("")) {
                    phone = "---";
                } else if (checkNewPhone(phone, users)) {
                    for (int i = 0; i < phone.length(); i++) {
                        if (!(phone.charAt(i) - '0' < 10 &&
                                phone.charAt(i) - '0' > -1)) {
                            number = false;
                            break;
                        }
                    }
                    if (!number) {
                        response.setAnswer(Texts.INVALID_PHONE);
                        return response;
                    }
                } else {
                    response.setAnswer(Texts.REPEATED_PHONE);
                    return response;
                }
                String biography = "null";
                User newUser;
                User.setId_counter(context.getUsers().getIDCounter());
                newUser = new User(firstname, lastname,
                        registrationEvent.getBirth().getYear(),
                        registrationEvent.getBirth().getMonthValue(),
                        registrationEvent.getBirth().getDayOfMonth(),
                        email, phone, biography, username, password);
                context.getUsers().setIDCounter(User.getId_counter());
                context.getUsers().set(newUser);
                clientHandler.getAuthToken().setAuthToken(addClient(newUser.getId()));
                clientHandler.setId(newUser.getId());
                Log log = new Log("new account created", LocalDateTime.now(),
                        1, newUser.getId());
                Log.log(log);
                response.setAnswer("ok" + clientHandler.getAuthToken().getAuthToken());
                response.setId(newUser.getId());
                return response;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public Response createNewBot(BotEvent botEvent) {
        try {
            synchronized (User.LOCK) {
                BotResponse response = new BotResponse();
                if (botEvent.getBotName().equals("") || botEvent.getUrl().equals("")) {
                    response.setAnswer("enter robot's info    ");
                    return response;
                }
                if (!checkNewUsername(botEvent.getBotName(), context.getUsers().getAll())) {
                    response.setAnswer("invalid name    ");
                    return response;
                }
                response.setAnswer("ok");
                User.setId_counter(context.getUsers().getIDCounter());
                Bot bot = new Bot("", "", 2020, 1, 1, "",
                        "", "I am a bot", botEvent.getBotName(), ""
                        , botEvent.getUrl());
                context.getUsers().setIDCounter(User.getId_counter());
                context.getUsers().set(bot);
                response.setBot(bot);
                return response;
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkNewUsername(String username, List<User> users) {
        for (User user: users) {
            if(user.getAccount().getUsername().equals(username)){
                return false;
            }
        }
        return true;
    }

    public boolean checkNewPassword(String password, List<User> users) {
        for (User user: users) {
            if(user.getAccount().getPassword().equals(password)){
                return false;
            }
        }
        return true;
    }

    public boolean checkNewEmail(String email, List<User> users) {
        for (User user: users) {
            if(user.getEmailAddress().equals(email)){
                return false;
            }
        }
        return true;
    }

    public boolean checkNewPhone(String phone, List<User> users) {
        for (User user: users) {
            if(user.getPhoneNumber().equals(phone)){
                return false;
            }
        }
        return true;
    }
}