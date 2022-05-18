package dto;

import java.io.Serializable;

public class UserDTO implements Serializable {
    private Integer id;
    private String firstName;
    private String lastName;
    private String eMail;
    private String password;
    private String type;

    public UserDTO(Integer id) {
        this(id,"","","","","");
    }

    public UserDTO(Integer id, String firstName, String lastName, String eMail, String password, String type) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.password = password;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
