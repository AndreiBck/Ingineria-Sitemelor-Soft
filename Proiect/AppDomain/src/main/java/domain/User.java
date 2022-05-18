package domain;

import java.io.Serializable;

public class User implements Identifiable<Integer>, Serializable {
    private int ID;
    private String firstName;
    private String lastName;
    private String eMail;
    private String password;
    private String type;

    public User(String firstName, String lastName, String eMail, String password, String type)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.password = password;
        this.type = type;
    }

    public User()
    {
        this.firstName ="";
        this.lastName = "";
        this.eMail = "";
        this.password="";
        this.type="";
    }

    @Override
    public Integer getID() {
        return this.ID;
    }

    @Override
    public void setID(Integer id) {
        this.ID = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {return lastName;}

    public String geteMail() {return eMail;}

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {this.lastName = lastName;}

    public void seteMail(String eMail) {this.eMail = eMail;}

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(String type) {
        this.type = type;
    }
}
