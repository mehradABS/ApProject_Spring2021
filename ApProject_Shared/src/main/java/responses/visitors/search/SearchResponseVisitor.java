package responses.visitors.search;

import responses.visitors.ResponseVisitor;

public interface SearchResponseVisitor extends ResponseVisitor {

    void gotoSearchedUserPanel( int id);
}
