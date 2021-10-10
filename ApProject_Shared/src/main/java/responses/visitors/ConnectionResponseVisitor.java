package responses.visitors;


public interface ConnectionResponseVisitor extends ResponseVisitor{
    void getInfo(long token);
}
