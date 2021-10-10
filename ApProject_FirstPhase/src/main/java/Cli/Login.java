package Cli;

 
public class Login extends Father {

    public Login(){
        System.out.println("                                 "+
                ConsoleColors.BLUE_BOLD+"Welcome to MinabCity"+
                ConsoleColors.RESET);
    }
    public  String IsHavingAccount(){
        System.out.println(ConsoleColors.CYAN+"Login(l)"+
                " or Create new account(c)"+ConsoleColors.RESET);
        return scanner.next();
    }
    public String[] login(){
     String[] information=new String[2];
     //information keeps user's answers.
     System.out.println(ConsoleColors.CYAN+"Enter your username(Exit(0))"
             +ConsoleColors.RESET);
     information[0]=this.scanner.next();
     if(information[0].equals("0")){
         return information;
     }
     System.out.println(ConsoleColors.CYAN+"Enter your password"
             +ConsoleColors.RESET);
     information[1]=this.scanner.next();
     return information;
    }
    public void creatingAccount() {
        System.out.println(ConsoleColors.CYAN + "Well,please enter " +
                "the information that you'll be asked" + "\n" +
                "(you can ignore any item that dose not have * by writing 'i')" +
                "\n(you can write -1 every where to comeback)"+ConsoleColors.RESET);
    }
    public String getFirstName() {
        System.out.println(ConsoleColors.CYAN + "Enter your FirstName(*)" +
                ConsoleColors.RESET);
        return this.scanner.next();
    }
    public String getLastName() {
        System.out.println(ConsoleColors.CYAN + "Enter your LastName(*)" +
                ConsoleColors.RESET);
        return this.scanner.next();
    }
    public String getUsername() {
        System.out.println(ConsoleColors.CYAN + "Enter your Username(*)" +
                ConsoleColors.RESET);
       return this.scanner.next();
    }
    public String getPassword() {
        System.out.println(ConsoleColors.CYAN + "Enter your Password(*)" +
                ConsoleColors.RESET);
        return this.scanner.next();
    }
    public String getEmail() {
        System.out.println(ConsoleColors.CYAN + "Enter your EmailAddress(*)" +
                ConsoleColors.RESET);
        return this.scanner.next();
    }
    public String[] getBirth() {
        String[] Birth;
        System.out.println(ConsoleColors.CYAN + "Enter your Birth day , " +
                "month , year like this: 10,08,1990" +
                ConsoleColors.RESET);
        String b=scanner.next();
        b=b+',';
        Birth=b.split(",");
        return Birth;
    }
    public String getPhoneNumber() {
        System.out.println(ConsoleColors.CYAN + "Enter your PhoneNumber" +
                ConsoleColors.RESET);
        return this.scanner.next();
    }
    public String getBiography() {
        System.out.println(ConsoleColors.CYAN + "you can write a biography" +
                " for yourself");
        System.out.println("please write your text like this:"+"\n"
        +"......\n...../\n(/ is necessary)"+ConsoleColors.RESET);
        StringBuilder Biography=new StringBuilder();
        String first=scanner.next();
        Biography.append(first);
        if(!Biography.toString().equals("i")) {
            while (Biography.length() < 3 || Biography.charAt(Biography.length() - 1) != '/') {
                Biography.append("\n").append(scanner.nextLine());
                while (Biography.charAt(Biography.length() - 1) == ' ') {
                    Biography.delete(Biography.length() - 1, Biography.length());
                }
            }
            for (int i = 0; i < Biography.length(); i++) {
                if (Biography.charAt(i) == '\n') {
                    Biography.delete(i, i + 1);
                    i--;
                } else {
                    break;
                }
            }
            Biography.delete(Biography.length() - 1, Biography.length());
        }
        return Biography.toString();
    }
    public void invalidLogin(){
        System.out.println(ConsoleColors.RED_BOLD+
                "invalid username or password");
    }
   public void successful(char what){
       if(what=='l'){
           System.out.println(ConsoleColors.YELLOW+
                   "login successful");
       }
       else{
           System.out.println(ConsoleColors.YELLOW+
                   "your account created");
       }
       System.out.print(ConsoleColors.RESET);
    }

   public void invalidNewInfo(String infoName){
       System.out.println(ConsoleColors.RED+
               "your " +infoName +" has already been chosen"
       +ConsoleColors.RESET);
   }
   public void invalidBirth(){
       System.out.println(ConsoleColors.RED+"invalid Birth"
       +ConsoleColors.RESET);
   }
   public void invalidNumber(){
       System.out.println(ConsoleColors.RED+
               "invalid phone number"+ConsoleColors.RESET);
   }
}