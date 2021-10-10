package responses.auth;

public class LoginResponse extends RegistrationResponse {

    public String getVisitorType(){
        return "LoginResponseVisitor";
    }
}