package responses.visitors.list;

import responses.list.ListPanelResponse;
import responses.visitors.ResponseVisitor;

public interface ListPanelResponseVisitor extends ResponseVisitor {

    void getResponse(ListPanelResponse response);
}
