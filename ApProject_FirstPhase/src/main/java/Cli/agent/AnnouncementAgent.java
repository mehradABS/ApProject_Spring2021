package Cli.agent;

import Cli.Announcement;
import Logic.LogicalAgent;
import Models.Log;
import Models.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class AnnouncementAgent {
    public void Announcement(LogicalAgent logicalagent,
                             Announcement announcement) throws IOException {
        User current = logicalagent.getProgram().getCurrentUser();
        while (true) {
            announcement.showingOptions();
            String answer = announcement.GetCommand();
            switch (answer) {
                case "0":
                    return;
                case "1":
                    if (current.getSystemMessages().size() == 0) {
                        announcement.noMessage();
                    } else {
                        for (String text : current.getSystemMessages()) {
                            announcement.showSystemMessage(text);
                        }
                    }
                    break;
                case "2": {
                    announcement.DrawingLine();
                    announcement.ShowingOthersRequestGuide();
                    int length = current.getRequestsFromMe().size() - 1;
                    boolean show = true;
                    if (length == -1) {
                        announcement.noRequest();
                        show = false;
                    }
                    while (true) {
                        if (show && length!=-1) {
                            announcement.showOtherRequest(current.
                                    getRequestsFromMe().get(length));
                        }
                        String answer1 = announcement.GetCommand();
                        if (answer1.equals("0")) {
                            break;
                        } else if (answer1.equals("1")) {
                            if (length > 0) {
                                length--;
                                show = true;
                            } else {
                                announcement.noRequest();
                            }
                        } else if (answer1.equals("2")) {
                            if (length < current.getRequestsFromMe().size() - 1) {
                                show = true;
                                length++;
                            } else {
                                announcement.noRequest();
                            }
                        } else if (answer1.equals("3") && length != -1) {
                            current.getRequestsFromMe().get(length).
                                    setAnswered(true);
                            current.getRequestsFromMe().get(length).
                                    setAccepted(true);
                            current.getRequestsFromMe().get(length).setSystemTellUser(true);
                            logicalagent.getModelLoader().setRequestChanges(
                                    current.getRequestsFromMe().get(length));
                            current.getRequestsFromMe().get(length)
                                    .getSourceUser().getSystemMessages()
                                    .add(current.getAccount()
                                            .getUsername() + " accepted your request");
                            Log log=new Log("accept a request", LocalDateTime.now(),3,
                                    logicalagent.getProgram().
                                            getCurrentUser().getAccount().getUsername());
                            Log.log(log);
                            if(!current.getFollowers().contains(
                                    current.getRequestsFromMe().get(length)
                                    .getSourceUser())) {
                                current.getFollowers().add(current.getRequestsFromMe().get(length)
                                        .getSourceUser());
                                current.getFollowerUsername().add(current.getRequestsFromMe().get(length)
                                        .getSourceUser().getId());
                                current.getRequestsFromMe().get(length)
                                        .getSourceUser().getFollowings().add(current);
                                current.getRequestsFromMe().get(length)
                                        .getSourceUser().getFollowingUsername().add(
                                        current.getId());
                                logicalagent.getModelLoader().setUserChanges(current);
                                logicalagent.getModelLoader().setUserChanges(
                                        current.getRequestsFromMe().get(length)
                                                .getSourceUser());
                            }
                            File file=new File("src\\Save\\requests\\" +
                                    current.getRequestsFromMe().get(length)
                                            .getId()+".json");
                            while(true) {
                                if(file.delete()){
                                    break;
                                }
                            }
                            current.getRequestsFromMe().remove(length);
                            length--;
                            show=true;
                                announcement.finishingProcess();
                        } else if (answer1.equals("4") && length != -1) {
                            current.getRequestsFromMe().get(length).
                                    setAnswered(true);
                            current.getRequestsFromMe().get(length).
                                    setAccepted(false);
                            current.getRequestsFromMe().get(length).
                                    setSystemTellUser(true);
                            logicalagent.getModelLoader().setRequestChanges(
                                    current.getRequestsFromMe().get(length));
                            current.getRequestsFromMe().get(length)
                                    .getSourceUser().getSystemMessages()
                                    .add(current.getAccount()
                                            .getUsername() + "rejected your request");
                            Log log=new Log("reject a request",LocalDateTime.now(),3,
                                    logicalagent.getProgram().
                                            getCurrentUser().getAccount().getUsername());
                            Log.log(log);
                            logicalagent.getModelLoader().setUserChanges(
                                    current.getRequestsFromMe().get(length)
                                            .getSourceUser());
                            File file=new File("src\\Save\\requests\\" +
                                    current.getRequestsFromMe().get(length)
                                            .getId()+".json");
                            while(true) {
                                if(file.delete()){
                                    break;
                                }
                            }
                            current.getRequestsFromMe().remove(length);
                            logicalagent.getModelLoader().setUserChanges(current);
                            length--;
                            show=true;
                            announcement.finishingProcess();
                        } else if (answer1.equals("5") && length != -1) {
                            current.getRequestsFromMe().get(length).
                                    setAnswered(true);
                            current.getRequestsFromMe().get(length).
                                    setAccepted(false);
                            Log log=new Log("reject a request",LocalDateTime.now(),3,
                                    logicalagent.getProgram().
                                            getCurrentUser().getAccount().getUsername());
                            Log.log(log);
                            current.getRequestsFromMe().get(length).
                                    setSystemTellUser(false);
                            logicalagent.getModelLoader().setRequestChanges(
                                    current.getRequestsFromMe().get(length));
                            File file=new File("src\\Save\\requests\\" +
                                    current.getRequestsFromMe().get(length)
                                            .getId()+".json");
                            while(true) {
                                if(file.delete()){
                                    break;
                                }
                            }
                            current.getRequestsFromMe().remove(length);
                            length--;
                            show=true;
                            logicalagent.getModelLoader().setUserChanges(current);
                            announcement.finishingProcess();
                        }
                    }
                    announcement.DrawingLine();
                    break;
                }
                case "3": {
                    announcement.DrawingLine();
                    announcement.ShowingMyRequestGuide();
                    int length = current.getMyRequests().size() - 1;
                    boolean show = true;
                    if (length == -1) {
                        announcement.noRequest();
                        show = false;
                    }
                    label:
                    while (true) {
                        if (show) {
                            announcement.showMyRequest(current.
                                    getMyRequests().get(length));
                        }
                        String answer1 = announcement.GetCommand();
                        switch (answer1) {
                            case "0":
                                break label;
                            case "1":
                                if (length > 0) {
                                    length--;
                                    show = true;
                                } else {
                                    announcement.noRequest();
                                }
                                break;
                            case "2":
                                if (length < current.getMyRequests().size() - 1) {
                                    show = true;
                                    length++;
                                } else {
                                    announcement.noRequest();
                                }
                                break;
                        }
                    }
                    announcement.DrawingLine();
                    break;
                }
            }

        }
    }
}
