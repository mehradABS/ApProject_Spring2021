package responses.auth;

import responses.Response;
import responses.visitors.ResponseVisitor;
import responses.visitors.ReceivingConfirmationResponseVisitor;

public class ReceivingImageConfirmationResponse extends Response {

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        ((ReceivingConfirmationResponseVisitor)responseVisitor).gotoAnotherPanel();
    }

    @Override
    public String getVisitorType() {
        return "ReceivingImageConfirmationResponseVisitor";
    }
}
