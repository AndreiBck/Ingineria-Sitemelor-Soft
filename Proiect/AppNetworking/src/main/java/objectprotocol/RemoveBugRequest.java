package objectprotocol;

import domain.Bug;

public class RemoveBugRequest implements Request{
    private Bug bug;

    public RemoveBugRequest(Bug bug){this.bug = bug;}

    public Bug getBug() {
        return bug;
    }
}
