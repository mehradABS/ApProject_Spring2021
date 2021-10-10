package responses.search;

import responses.Response;
import responses.visitors.ResponseVisitor;
import responses.visitors.search.SearchResponseVisitor;

public class SearchResponse extends Response {

    private int id;

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        ((SearchResponseVisitor)responseVisitor).gotoSearchedUserPanel(id);
    }

    @Override
    public String getVisitorType() {
        return "SearchResponseVisitor";
    }

    public void setId(int id) {
        this.id = id;
    }
}