package Cli;


import Models.Request;


public class Announcement extends Father implements CommonActions{

    @Override
    public String GetCommand() {
        while(true){
            String a= scanner.next();
            switch (a) {
                case "0":
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                    return a;
                default:
                    this.invalidCommand();
                    break;
            }
        }
    }

    @Override
    public void showingOptions() {
        System.out.println(ConsoleColors.CYAN_BOLD+"Exit this Page(0)"+
                ", watching System Messages(1)"+
                ", watching others request(2)"+", watching myRequest(3)");
        System.out.println(ConsoleColors.CYAN+
                "write number of the action which you want to do"
        +ConsoleColors.RESET);
    }
    public void ShowingMyRequestGuide(){
        System.out.println(ConsoleColors.CYAN+"going to next request(1)" +
                ", going to previous request(2), Exit this list(0)"
        +ConsoleColors.RESET);
    }
    public void ShowingOthersRequestGuide(){
        System.out.println(ConsoleColors.CYAN+"going to next request(1)" +
                ", going to previous request(2), Exit this list(0)");
        System.out.println("accept request(3)" +
                ", reject request by system message to them(4), " +
                "reject request without system message to them(5)");
        System.out.print(ConsoleColors.RESET);
    }
    public void showMyRequest(Request request){
        System.out.println(request.getText());
        if(request.isAnswered()){
            if(request.isAccepted()){
                System.out.println(": request accepted");
            }
            else if(request.isSystemTellUser()){
                System.out.println(": request rejected");
            }
        }
    }
    public void showOtherRequest(Request request){
        System.out.println(request.getText());
        System.out.println("this request is from: "+request.getSourceUser()
        .getAccount().getUsername());
    }
    public void showSystemMessage(String text){
        System.out.println(ConsoleColors.RED_BOLD+
                text+ConsoleColors.RESET);
    }

    public void noMessage(){
        System.out.println("there is no message");
    }

    public void noRequest(){
        System.out.println("there is no request");
    }
}