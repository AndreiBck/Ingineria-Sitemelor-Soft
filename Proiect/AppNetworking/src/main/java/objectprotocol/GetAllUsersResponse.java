package objectprotocol;

import domain.User;

public class GetAllUsersResponse implements Response{
    private User[] events;

    public GetAllUsersResponse(User[] events){this.events = events;}

    public User[] getEvents() {
        return events;
    }
}
