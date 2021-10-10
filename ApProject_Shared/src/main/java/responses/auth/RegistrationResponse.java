package responses.auth;

import responses.Response;
import responses.visitors.ResponseVisitor;
import responses.visitors.auth.RegistrationResponseVisitor;

public class RegistrationResponse extends Response {

    protected String answer;
    protected int id;

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        ((RegistrationResponseVisitor)responseVisitor).getAnswer(this);
    }

    @Override
    public String getVisitorType() {
        return "RegistrationResponseVisitor";
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer){
        this.answer = answer;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }
}