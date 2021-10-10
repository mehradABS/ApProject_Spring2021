package responses;

import responses.visitors.ResponseVisitor;

public abstract class Response{

    public abstract void visit(ResponseVisitor responseVisitor);
    public abstract String getVisitorType();
}
