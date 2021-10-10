package responses;

import responses.visitors.ConnectionResponseVisitor;
import responses.visitors.ResponseVisitor;

public class ConnectionResponse extends Response{

    private final long token;

    public ConnectionResponse(long token) {
        this.token = token;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        ((ConnectionResponseVisitor)responseVisitor).getInfo(token);
    }

    @Override
    public String getVisitorType() {
        return "ConnectionResponseVisitor";
    }

    public long getToken() {
        return token;
    }
}
