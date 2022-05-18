package objectprotocol;

public class UpdateUserRequest implements Request{
    private String firstName;
    private String lastName;
    private String eMail;
    private String password;
    private String type;
    private int IdU;

    public UpdateUserRequest(String firstName, String lastName, String eMail, String password, String type, int Idu)
    {
        this.firstName=firstName;
        this.lastName=lastName;
        this.eMail=eMail;
        this.password=password;
        this.type=type;
        this.IdU = Idu;
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

    public int getIdU() {
        return IdU;
    }
}
