package Cli;



public class Menu extends Father implements CommonActions{

    public  void showingOptions(){
        System.out.println(ConsoleColors.RED_BOLD+
                "Logout(0)"+"     PersonalPage(1)"+"     " +
                "TimeLine(2)    "+
                " ExplorePage(3)"+"     "+
                "Chatroom(4)"+"     "+
                "Setting(5)"+
                ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN+"write the number " +
                "of the page which you want to go"
                +ConsoleColors.RESET);
    }
    public  String GetCommand(){
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

    public String logout(){
        System.out.println(ConsoleColors.RED+
                "are you sure(y/n)?"+ConsoleColors.RESET);
        while(true){
            String a= scanner.next();
            switch (a) {
                case "y":
                case "n":
                    return a;
                default:
                    this.invalidCommand();
                    break;
            }
        }
    }
}