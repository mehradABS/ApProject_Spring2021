package Logic;

import Cli.agent.BigAgent.CliAgent;
import Models.Program;

import java.io.FileNotFoundException;
import java.io.IOException;


public class LogicalAgent {
    private final Program program;
    private final ModelLoader modelLoader;
    public Program getProgram() {
        return program;
    }
    public ModelLoader getModelLoader() {
        return modelLoader;
    }
    private final CliAgent cliAgent;
    public LogicalAgent() throws IOException {
        this.modelLoader=new ModelLoader();
        this.cliAgent=new CliAgent(this);
        this.program=modelLoader.loadProgram();
    }

    public String startingProgram() throws IOException {
        cliAgent.login();
        while(true) {
            cliAgent.father.DrawingLine();
            String function = cliAgent.Menu();
            switch (function) {
                case "0": {
                    cliAgent.father.DrawingLine();
                    String answer = cliAgent.logout();
                    if (answer.equals("y")) {
                        String answer1 = cliAgent.father.ExitProgram();
                        if (answer1.equals("0")) {
                            cliAgent.father.DrawingLine();
                            return "0";
                        } else {
                            return "1";
                        }
                    }
                    break;
                }
                case "1": {
                    cliAgent.father.DrawingLine();
                    String answer;
                    label:
                    while (true) {
                        answer= cliAgent.PersonalPage();
                        switch (answer) {
                            case "0":
                                break label;
                            case "1":
                                cliAgent.father.DrawingLine();
                                cliAgent.NewTweet();
                                cliAgent.father.DrawingLine();
                                break;
                            case "2":
                                cliAgent.father.DrawingLine();
                                cliAgent.TweetHistory();
                                cliAgent.father.DrawingLine();
                                break;
                            case "3":
                                cliAgent.father.DrawingLine();
                                cliAgent.EditPersonalInfo();
                                cliAgent.father.DrawingLine();
                                break;
                            case "4":
                                cliAgent.father.DrawingLine();
                                cliAgent.UserLists();
                                cliAgent.father.DrawingLine();
                                break;
                            case "5":
                                cliAgent.father.DrawingLine();
                                cliAgent.Announcement();
                                cliAgent.father.DrawingLine();
                                break;
                        }
                    }
                    break;
                }
                case "2":
                    cliAgent.father.DrawingLine();
                    cliAgent.TimeLine(program.getCurrentUser()
                            .TimelineTweets(), false);
                    break;
                case "3":
                    cliAgent.father.DrawingLine();
                    cliAgent.ExplorePage();
                    break;
                case "4":
                    cliAgent.father.DrawingLine();
                    cliAgent.ChatsPage();
                    break;
                case "5": {
                    cliAgent.father.DrawingLine();
                    String answer=cliAgent.Setting();
                    if(answer.equals("-1")){
                        String answer1 = cliAgent.father.ExitProgram();
                        if (answer1.equals("0")) {
                            cliAgent.father.DrawingLine();
                            return "0";
                        } else {
                            return "1";
                        }
                    }
                        break;
                }
            }
        }
    }


    public boolean checkValidationLogin(String username,String password){
        return program.isValidInfo(username,password);
    }
    public boolean validationNewUsername(String username){
        return program.checkNewUsername(username);
    }
    public boolean validationNewPassword(String password){
        return program.checkNewPassword(password);
    }
    public boolean validationNewEmail(String email){
        return program.checkNewEmail(email);
    }
    public boolean validationNewPhone(String phone){
        return program.checkNewPhoneNumber(phone);
    }
}