package Cli;



public class ForwardMessage extends Father implements CommonActions{
    @Override
    public String GetCommand() {
        while(true){
            String a= scanner.next();
            switch (a) {
                case "0":
                case "1":
                case "2":
                case "3":
                    return a;
                default:
                    this.invalidCommand();
                    break;
            }
        }
    }
    @Override
    public void showingOptions() {
        System.out.println(ConsoleColors.CYAN+
                "Exit(0)");
        System.out.println("forward message to all of your following(1)(except people to Whom" +
                " you cant send message)");
        System.out.println("forward Message to your friend'sList(s)(2)");
        System.out.println("forward Message to your friend(s)(3)"+ConsoleColors.RESET);
    }
    public String[] getList(){
        StringBuilder ListName=new StringBuilder();
        ListName.append(scanner.next());
        if(!ListName.toString().equals("0")) {
            while (ListName.length() < 3 ||
                    ListName.charAt(ListName.length() - 1) != '/') {
                ListName.append("\n").append(scanner.nextLine());
                while (ListName.charAt(ListName.length() - 1) == ' ') {
                    ListName.delete(ListName.length() - 1, ListName.length());
                }
            }
            for (int i = 0; i < ListName.length(); i++) {
                if (ListName.charAt(i) == '\n') {
                    ListName.delete(i, i + 1);
                    i--;
                } else {
                    break;
                }
            }
            ListName.delete(ListName.length() - 1, ListName.length());
        }
        return ListName.toString().split("~~");
    }
    public void Lists(){
        System.out.println(ConsoleColors.CYAN+"please" +
                " write your List Like this"+"\n"
                +"listName~~listName~~....~~listName/");
        System.out.println("(~~) and (/) are necessary " +
                "(you can Exit by writing(0))"+ConsoleColors.RESET);
    }
    public void Usernames(){
        System.out.println(ConsoleColors.CYAN+"please" +
                " write your Username Like this"+"\n"
                +"userName~~userName~~....~~userName/");
        System.out.println("(~~) and (/) are necessary " +
                "(you can Exit by writing(0))"+ConsoleColors.RESET);
    }
    public String[] getUsername(){
        StringBuilder userName=new StringBuilder();
        userName.append(scanner.next());
        if(!userName.toString().equals("0")) {
            while (userName.length() < 3 ||
                    userName.charAt(userName.length() - 1) != '/') {
                userName.append("\n").append(scanner.nextLine());
                while (userName.charAt(userName.length() - 1) == ' ') {
                    userName.delete(userName.length() - 1, userName.length());
                }
            }
            for (int i = 0; i < userName.length(); i++) {
                if (userName.charAt(i) == '\n') {
                    userName.delete(i, i + 1);
                    i--;
                } else {
                    break;
                }
            }
            userName.delete(userName.length() - 1, userName.length());
        }
        return userName.toString().split("~~");
    }
    public void invalid(){
       System.out.println("invalid name(s)");
   }
    public void Block(){
        System.out.println("you block all of them or you have no following");
    }
    public void Block1(){
        System.out.println("you block all of them or your list(s) is empty");
    }
}