package app.personalPage.info;

import events.BotEvent;
import models.auth.User;
import app.auth.RegisterController;
import controller.ClientHandler;
import controller.MainController;
import controller.log.Log;
import events.auth.RegistrationEvent;
import events.info.ChangeInfoEvent;
import events.visitors.auth.RegistrationEventVisitor;
import resources.Texts;
import responses.Response;
import responses.info.ChangeInfoResponse;

import java.io.IOException;
import java.time.LocalDateTime;

public class ChangeInfoController extends MainController implements
        RegistrationEventVisitor {
    private final RegisterController registerController;

    private final ClientHandler clientHandler;

    public ChangeInfoController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        registerController = new RegisterController(clientHandler);
    }

    public Response checkEvent(RegistrationEvent registrationEvent) {
        synchronized (User.LOCK) {
            try {
                User user = context.getUsers().get(clientHandler.getClientId());
                ChangeInfoResponse response = new ChangeInfoResponse();
                switch (((ChangeInfoEvent) registrationEvent).getType()) {
                    case Texts.FIRSTNAME_INFO_PANEL -> {
                        String firstname = registrationEvent.getFirstname();
                        if (firstname.equals("")) {
                            response.setAnswer(Texts.NULL);
                            return response;
                        }
                        user.setFirstname(firstname);
                        Log log = new Log("firstname changed", LocalDateTime.now(),
                                1, clientHandler.getClientId());
                        Log.log(log);
                    }
                    case Texts.LASTNAME_INFO_PANEL -> {
                        String lastname = registrationEvent.getLastname();
                        if (lastname.equals("")) {
                            response.setAnswer(Texts.NULL);
                            return response;
                        }
                        user.setLastname(lastname);
                        Log log = new Log("lastname changed", LocalDateTime.now(),
                                1, clientHandler.getClientId());
                        Log.log(log);
                    }
                    case Texts.USERNAME_INFO_PANEL -> {
                        String username = registrationEvent.getUsername();
                        if (username.equals("")) {
                            response.setAnswer(Texts.NULL);
                            return response;
                        }
                        if (registerController.checkNewUsername(username,
                                context.getUsers().getAll())) {
                            user.getAccount().setUsername(username);
                            Log log = new Log("username changed", LocalDateTime.now(),
                                    1, clientHandler.getClientId());
                            Log.log(log);
                        } else {
                            response.setAnswer(Texts.REPEATED_USERNAME);
                            return response;
                        }
                    }
                    case Texts.PASSWORD_INFO_PANEL -> {
                        String password = registrationEvent.getPassword();
                        if (password.equals("")) {
                            response.setAnswer(Texts.NULL);
                            return response;
                        }
                        if (registerController.checkNewPassword(password,
                                context.getUsers().getAll())) {
                            user.getAccount().setPassword(password);
                            Log log = new Log("password changed", LocalDateTime.now(),
                                    1, clientHandler.getClientId());
                            Log.log(log);
                        } else {
                            response.setAnswer(Texts.REPEATED_PASSWORD);
                            return response;
                        }
                    }
                    case Texts.EMAIL_INFO_PANEL -> {
                        String email = registrationEvent.getEmail();
                        int j = email.length() - 1;
                        for (; j >= 0; j--) {
                            if (email.charAt(j) != ' ') {
                                break;
                            }
                        }
                        email = email.substring(0, j + 1);
                        if (!(email.toLowerCase().endsWith("@gmail.com") ||
                                email.toLowerCase().endsWith("@yahoo.com"))) {
                            response.setAnswer(Texts.INVALID_EMAIL);
                            return response;
                        }
                        if (registerController.checkNewEmail(email,
                                context.getUsers().getAll())) {
                            user.setEmailAddress(email);
                            Log log = new Log("email changed", LocalDateTime.now(),
                                    1, clientHandler.getClientId());
                            Log.log(log);
                        } else {
                            response.setAnswer(Texts.REPEATED_EMAIL);
                            return response;
                        }
                    }
                    case Texts.PHONE_INFO_PANEL -> {
                        String phone = registrationEvent.getPhone();
                        int k = phone.length() - 1;
                        for (; k >= 0; k--) {
                            if (phone.charAt(k) != ' ') {
                                break;
                            }
                        }
                        phone = phone.substring(0, k + 1);
                        boolean number = true;
                        if (phone.equals("")) {
                            phone = "---";
                        } else if (registerController.checkNewPhone(phone,
                                context.getUsers().getAll())) {
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
                        user.setPhoneNumber(phone);
                        Log log = new Log("phone changed", LocalDateTime.now(),
                                1, clientHandler.getClientId());
                        Log.log(log);
                    }
                    case Texts.BIRTH -> {
                        user.setBirth(registrationEvent.getBirth());
                        Log log = new Log("birth changed", LocalDateTime.now(),
                                1, clientHandler.getClientId());
                        Log.log(log);
                    }
                    case Texts.BIOGRAPHY_INFO_PANEL -> {
                        String biography = registrationEvent.getEmail();
                        if (biography.equals("")) {
                            biography = "null";
                        }
                        user.setBiography(biography);
                        Log log = new Log("biography changed", LocalDateTime.now(),
                                1, clientHandler.getClientId());
                        Log.log(log);
                    }
                }
                context.getUsers().set(user);
                response.setAnswer("ok");
                return response;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public Response createNewBot(BotEvent botEvent) {
        return null;
    }
}
