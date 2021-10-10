package Cli.agent;

import Cli.EditPersonalPage;
import Cli.Login;
import Cli.Menu;
import Cli.Setting;
import Logic.LogicalAgent;
import Models.User;

import java.io.IOException;

public class SettingAgent {
    public String setting(Setting setting
    , LogicalAgent logicalagent,
                          Login login,
                          Menu menu,
                          EditPersonalPage editPersonalInfo) throws IOException {
        while(true){
            setting.showingOptions();
            String answer= setting.GetCommand();
            switch (answer) {
                case "0":
                    return "0";
                case "1":
                    setting.changePrivacy(logicalagent.getProgram().getCurrentUser()
                            .getAccount());
                    while (true) {
                        String answer1 = setting.GetCommand();
                        if (answer1.equals("0")) {
                            break;
                        } else if (answer1.equals("1")) {
                            if (logicalagent.getProgram().getCurrentUser()
                                    .getAccount().getPrivacy().equals("public")) {
                                logicalagent.getProgram().getCurrentUser()
                                        .getAccount().setPrivacy("private");
                            } else {
                                logicalagent.getProgram().getCurrentUser()
                                        .getAccount().setPrivacy("public");
                            }
                            logicalagent.getModelLoader().setUserChanges(
                                    logicalagent.getProgram().getCurrentUser());
                            setting.finishingProcess();
                        } else {
                            setting.invalidCommand();
                        }
                    }
                    setting.DrawingLine();
                    break;
                case "2":
                    setting.changeLastSeen(logicalagent.getProgram().getCurrentUser()
                            .getAccount());
                    while (true) {
                        String answer1 = setting.GetCommand();
                        if ("0".equals(answer1)) {
                            break;
                        } else if ("1".equals(answer1)) {
                            logicalagent.getProgram().getCurrentUser()
                                    .getAccount().setWhoCanSeeLastSeen("allUsers");
                            logicalagent.getModelLoader().setUserChanges(
                                    logicalagent.getProgram().getCurrentUser());
                            setting.finishingProcess();
                        } else if ("2".equals(answer1)) {
                            logicalagent.getProgram().getCurrentUser()
                                    .getAccount().setWhoCanSeeLastSeen("nobody");
                            logicalagent.getModelLoader().setUserChanges(
                                    logicalagent.getProgram().getCurrentUser());
                            setting.finishingProcess();
                        } else if ("3".equals(answer1)) {
                            logicalagent.getProgram().getCurrentUser()
                                    .getAccount().setWhoCanSeeLastSeen(
                                    "just yourFollowing");
                            logicalagent.getModelLoader().setUserChanges(
                                    logicalagent.getProgram().getCurrentUser());
                            setting.finishingProcess();
                        } else {
                            setting.invalidCommand();
                        }
                    }
                    setting.DrawingLine();
                    break;
                case "3":
                    User current = logicalagent.getProgram().getCurrentUser();
                    editPersonalInfo.change();
                    while (true) {
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
                                editPersonalInfo.finishingProcess();
                                break;
                            } else {
                                login.invalidNewInfo("password");
                            }
                        } else {
                            break;
                        }
                    }
                    setting.DrawingLine();
                    break;
                case "4":
                    boolean active = setting.
                            changeAccountActivate(logicalagent.getProgram()
                                    .getCurrentUser().getAccount());
                    while (true) {
                        String answer1 = setting.GetCommand();
                        if (answer1.equals("0")) {
                            break;
                        } else if (answer1.equals("1")) {
                            logicalagent.getProgram().getCurrentUser()
                                    .getAccount().setActive(!active);
                            logicalagent.getModelLoader().setUserChanges(
                                    logicalagent.getProgram().getCurrentUser());
                            setting.finishingProcess();
                        } else {
                            setting.invalidCommand();
                        }
                    }
                    setting.DrawingLine();
                    break;
                case "5":
                    String answer1 = menu.logout();
                    if (answer1.equals("y")) {
                        boolean f=logicalagent.getProgram().removeAccount(logicalagent
                                .getProgram().getCurrentUser(),
                                logicalagent.getModelLoader());
                        setting.deleteAccount(f);
                        setting.DrawingLine();
                        return "-1";
                    }
                    break;
            }
        }
    }
}
