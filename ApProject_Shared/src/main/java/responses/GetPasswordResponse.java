package responses;

import responses.visitors.GetPasswordResponseVisitor;
import responses.visitors.ResponseVisitor;

public class GetPasswordResponse extends Response{

    private final String password;

    public GetPasswordResponse(String password) {
        this.password = password;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        ((GetPasswordResponseVisitor)responseVisitor).setPassword(password);
    }

    @Override
    public String getVisitorType() {
        return "GetPasswordResponseVisitor";
    }
}
