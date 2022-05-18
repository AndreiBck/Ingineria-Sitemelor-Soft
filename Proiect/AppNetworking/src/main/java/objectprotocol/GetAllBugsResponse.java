package objectprotocol;

import domain.Bug;

public class GetAllBugsResponse implements Response{
    private Bug[] events;

    public GetAllBugsResponse(Bug[] events){this.events = events;}

    public Bug[] getEvents(){return events;}

}
