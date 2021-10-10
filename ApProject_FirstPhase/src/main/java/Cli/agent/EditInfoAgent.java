package Cli.agent;

import Cli.EditPersonalPage;
import Cli.Login;
import Logic.LogicalAgent;
import Models.Log;
import Models.User;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class EditInfoAgent {
    public void EditPersonalInfo(LogicalAgent logicalagent,
                                 EditPersonalPage editPersonalInfo
    ,Login login) throws IOException {
        User current = logicalagent.getProgram().getCurrentUser();
        while (true) {
            editPersonalInfo.ShowInfo(current.getAccount());
            editPersonalInfo.showingOptions();
            String answer = editPersonalInfo.GetCommand();
            editPersonalInfo.DrawingLine();
            switch (answer) {
                case "0":
                    return;
                case "1": {
                    editPersonalInfo.change();
                    String change = editPersonalInfo.getChange();
                    if (!change.equals("0")) {
                        current.setFirstname(change);
                        Log log=new Log("firstname changed", LocalDateTime.now(),
                                1,logicalagent.getProgram().getCurrentUser().getAccount()
                                .getUsername());
                        Log.log(log);
                        editPersonalInfo.finishingProcess();
                    }
                    editPersonalInfo.DrawingLine();
                    break;
                }
                case "2": {
                    editPersonalInfo.change();
                    String change = editPersonalInfo.getChange();
                    if (!change.equals("0")) {
                        current.setLastname(change);
                        editPersonalInfo.finishingProcess();
                        Log log=new Log("lastname changed",LocalDateTime.now(),
                                1,logicalagent.getProgram().getCurrentUser().getAccount()
                                .getUsername());
                        Log.log(log);
                    }
                    editPersonalInfo.DrawingLine();
                    break;
                }
                case "3":
                    while (true) {
                        editPersonalInfo.change();
                        String change = editPersonalInfo.getChange();
                        if (!change.equals("0")) {
                            if (logicalagent.validationNewUsername(change)) {
                                logicalagent.getProgram().getAllInfo().remove(
                                        current.getAccount().getUsername());
                                current.getAccount().setUsername(change);
                                logicalagent.getProgram().getAllInfo().put(
                                        current.getAccount().getUsername(),
                                        current.getAccount().getPassword());
                                logicalagent.getModelLoader().setUserChanges(current);
                                Log log=new Log("username changed",LocalDateTime.now(),
                                        1,logicalagent.getProgram().getCurrentUser().getAccount()
                                        .getUsername());
                                Log.log(log);
                                editPersonalInfo.finishingProcess();
                                break;
                            } else {
                                login.invalidNewInfo("username");
                            }
                        } else {
                            break;
                        }
                    }
                    editPersonalInfo.DrawingLine();
                    break;
                case "4":
                    while (true) {
                        editPersonalInfo.change();
                        String change = editPersonalInfo.getChange();
                        if (!change.equals("0")) {
                            if (logicalagent.validationNewPassword(change)) {
                                logicalagent.getProgram().getAllInfo().remove(
                                        current.getAccount().getUsername());
                                current.getAccount().setPassword(change);
                                logicalagent.getProgram().getAllInfo().put(
                                        current.getAccount().getUsername(),
                                        current.getAccount().getPassword());
                                logicalagent.getModelLoader().setUserChanges(current);
                                Log log=new Log("password changed",LocalDateTime.now(),
                                        1,logicalagent.getProgram().getCurrentUser().getAccount()
                                        .getUsername());
                                Log.log(log);
                                editPersonalInfo.finishingProcess();
                                break;
                            } else {
                                login.invalidNewInfo("password");
                            }
                        } else {
                            break;
                        }
                    }
                    editPersonalInfo.DrawingLine();
                    break;
                case "5":
                    String[] Birth;
                    while (true) {
                        editPersonalInfo.Exit();
                        Birth = editPersonalInfo.Birth();
                        if (Birth[0].equals("0")) {
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
                    if (!Birth[0].equals("0")) {
                        current.setBirth(LocalDate.of(Integer.parseInt(Birth[2])
                                , Integer.parseInt(Birth[1]),
                                Integer.parseInt(Birth[0])));
                        Log log=new Log("birth changed",LocalDateTime.now(),
                                1,logicalagent.getProgram().getCurrentUser().getAccount()
                                .getUsername());
                        Log.log(log);
                        editPersonalInfo.finishingProcess();
                    }
                    editPersonalInfo.DrawingLine();
                    break;
                case "6":
                    while (true) {
                        editPersonalInfo.change();
                        String change = editPersonalInfo.getChange();
                        if (!change.equals("0")) {
                            if (logicalagent.validationNewEmail(change)) {
                                logicalagent.getProgram().getAllEmails()
                                        .remove(current.getEmailAddress());
                                current.setEmailAddress(change);
                                logicalagent.getProgram().getAllEmails()
                                        .add(current.getEmailAddress());
                                Log log=new Log("email changed",LocalDateTime.now(),
                                        1,logicalagent.getProgram().getCurrentUser().getAccount()
                                        .getUsername());
                                Log.log(log);
                                editPersonalInfo.finishingProcess();
                                break;
                            } else {
                                login.invalidNewInfo("Email");
                            }
                        } else {
                            break;
                        }
                    }
                    editPersonalInfo.DrawingLine();
                    break;
                case "7": {
                    editPersonalInfo.change();
                    while(true){
                        String change = editPersonalInfo.getChange();
                        if (!change.equals("0")) {
                            boolean valid=true;
                            for (int i = 0; i < change.length(); i++) {
                                if(!(change.charAt(i)-'0'>-1 &&
                                        change.charAt(i)-'0'<10)){
                                    valid=false;
                                    break;
                                }
                            }
                            if(valid) {
                                logicalagent.getProgram().getAllPhoneNumbers().
                                        remove(current.getPhoneNumber());
                                current.setPhoneNumber(change);
                                logicalagent.getProgram().getAllPhoneNumbers()
                                        .add(current.getPhoneNumber());
                                Log log=new Log("phone changed",LocalDateTime.now(),
                                        1,logicalagent.getProgram().getCurrentUser().getAccount()
                                        .getUsername());
                                Log.log(log);
                                editPersonalInfo.finishingProcess();
                            }
                            else{
                                login.invalidNumber();
                            }
                        }
                        else{
                            break;
                        }
                    }
                    editPersonalInfo.DrawingLine();
                    break;
                }
                case "8": {
                    editPersonalInfo.Exit();
                    String change = editPersonalInfo.Biography();
                    if (!change.equals("0")) {
                        current.setBiography(change);
                        Log log=new Log("biography changed",LocalDateTime.now(),
                                1,logicalagent.getProgram().getCurrentUser().getAccount()
                                .getUsername());
                        Log.log(log);
                        editPersonalInfo.finishingProcess();
                    }
                    editPersonalInfo.DrawingLine();
                    break;
                }
            }
            logicalagent.getModelLoader().setUserChanges(current);
        }
    }
}
