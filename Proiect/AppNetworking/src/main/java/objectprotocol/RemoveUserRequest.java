package objectprotocol;

import domain.User;

public class RemoveUserRequest implements Request{
    private User user;

    public RemoveUserRequest(User user){this.user = user;}

    public User getUser() {
        return user;
    }
}
