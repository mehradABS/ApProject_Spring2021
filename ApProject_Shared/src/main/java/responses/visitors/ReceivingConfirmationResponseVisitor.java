package responses.visitors;


public interface ReceivingConfirmationResponseVisitor extends ResponseVisitor {
    void gotoAnotherPanel();
}
