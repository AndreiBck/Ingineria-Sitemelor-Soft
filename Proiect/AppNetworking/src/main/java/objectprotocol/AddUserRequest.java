package objectprotocol;

public class AddUserRequest implements Request{
    private String firstName;
    private String lastName;
    private String eMail;
    private String password;
    private String type;

    public AddUserRequest(String firstName, String lastName, String eMail, String password, String type)
    {
        this.firstName=firstName;
        this.lastName=lastName;
        this.eMail=eMail;
        this.password=password;
        this.type=type;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String geteMail() {
        return eMail;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }
}
