package Cli.agent;

import Cli.Login;
import Cli.Menu;
import Logic.LogicalAgent;
import Models.Log;
import Models.User;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class LoginAgent {

    public void login(Login login, LogicalAgent logicalagent)
            throws IOException {
        while (true) {
            String ask = login.IsHavingAccount();
            if (ask .equals("l")) {
                String[] info;
                boolean exit=false;
                while (true) {
                    info = login.login();
                    if(info[0].equals("0") && info[1]==null){
                        exit=true;
                        break;
                    }
                    else if (logicalagent.checkValidationLogin(info[0], info[1])) {
                        break;
                    } else {
                        login.invalidLogin();
                    }
                }
                if(!exit) {
                    login.successful('l');
                    login.DrawingLine();
                    for (User user : logicalagent.getProgram().originalUsers()) {
                        if (user.getAccount().getUsername().equals(info[0])) {
                            logicalagent.getProgram().setCurrentUser(user);
                        }
                    }
                    Log log=new Log("login successful",LocalDateTime.now(),
                            1,logicalagent.getProgram().getCurrentUser()
                            .getAccount().getUsername());
                    Log.log(log);
                    break;
                }
            } else if (ask .equals("c")) {
                boolean exit=false;
                while(true) {
                    login.creatingAccount();
                    String firstname = login.getFirstName();
                    if(firstname.equals("-1")){
                        break;
                    }
                    String lastname = login.getLastName();
                    if(lastname.equals("-1")){
                        break;
                    }
                    String username;
                    boolean exit1=false;
                    while (true) {
                        username = login.getUsername();
                        if(username.equals("-1")){
                            exit1=true;
                            break;
                        }
                        if (logicalagent.validationNewUsername(username)) {
                            break;
                        }
                        login.invalidNewInfo("username");
                    }
                    if(exit1){
                        break;
                    }
                    String password;
                    while (true) {
                        password = login.getPassword();
                        if(password.equals("-1")){
                            exit1=true;
                            break;
                        }
                        if (logicalagent.validationNewPassword(password)) {
                            break;
                        }
                        login.invalidNewInfo("password");
                    }
                    if(exit1){
                        break;
                    }
                    String email;
                    while (true) {
                        email = login.getEmail();
                        if(email.equals("-1")){
                            exit1=true;
                            break;
                        }
                        if (logicalagent.validationNewEmail(email)) {
                            break;
                        }
                        login.invalidNewInfo("email");
                    }
                    if(exit1){
                        break;
                    }
                    String phone;
                    while (true) {
                        boolean number=true;
                        phone = login.getPhoneNumber();
                        if(phone.equals("-1")){
                            exit1=true;
                            break;
                        }
                        if (phone.equals("i")) {
                            phone = "---";
                            break;
                        } else if (logicalagent.validationNewPhone(phone)) {
                            for (int i = 0; i < phone.length(); i++) {
                                if(!(phone.charAt(i)-'0'<10 &&
                                        phone.charAt(i)-'0'>-1)){
                                    number=false;
                                    break;
                                }
                            }
                            if(number){
                                break;
                            }
                        }
                        if(!number){
                            login.invalidNumber();
                        }
                        else {
                            login.invalidNewInfo("phoneNumber");
                        }
                    }
                    if(exit1){
                        break;
                    }
                    String[] Birth;
                    while (true) {
                        Birth = login.getBirth();
                        if(Birth[0].equals("-1")){
                            exit1=true;
                            break;
                        }
                        if (Birth[0].equals("i")) {
                            Birth = new String[3];
                            Birth[0] = "1";
                            Birth[1] = "1";
                            Birth[2] = "2025";
                            break;
                        }
                        try {
                            LocalDate.of(Integer.parseInt(Birth[2])
                                    , Integer.parseInt(Birth[1]),
                                    Integer.parseInt(Birth[0]));
                            break;
                        } catch (Exception e) {
                            login.invalidBirth();
                        }
                    }
                    if(exit1){
                        break;
                    }
                    String biography = login.getBiography();
                    if(biography.equals("-1")){
                        break;
                    }
                    if (biography.equals("i")) {
                        biography = "null";
                    }
                    login.successful('c');
                    Log log=new Log("new account created",LocalDateTime.now(),
                            1,username);
                    Log.log(log);
                    login.DrawingLine();
                    User newUser = new User(firstname, lastname, Integer.parseInt(Birth[2]),
                            Integer.parseInt(Birth[1]), Integer.parseInt(Birth[0]),
                            email, phone, biography, username, password);
                    logicalagent.getModelLoader().setUserID_counter(User.getId_counter());
                    logicalagent.getModelLoader().setUserChanges(newUser);
                    logicalagent.getProgram().originalUsers().add(newUser);
                    logicalagent.getProgram().setCurrentUser(newUser);
                    exit=true;
                    break;
                }
                if(exit){
                    break;
                }
            } else {
                login.invalidCommand();
            }
        }
    }
    public String logout(LogicalAgent logicalagent,
                         Menu menu) throws IOException {
        logicalagent.getProgram().
                getCurrentUser().getAccount().
                setLastSeen(LocalDateTime.now());
        logicalagent.getModelLoader().setUserChanges(
                logicalagent.getProgram().getCurrentUser());
        Log log=new Log("logout",LocalDateTime.now(),1,
                logicalagent.getProgram().
                        getCurrentUser().getAccount().getUsername());
        Log.log(log);
        return menu.logout();
    }
}
