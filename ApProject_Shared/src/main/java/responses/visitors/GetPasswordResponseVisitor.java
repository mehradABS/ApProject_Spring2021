package responses.visitors;

public interface GetPasswordResponseVisitor extends ResponseVisitor{

    void setPassword(String password);
}
