package domain;

public class User implements Identifiable<Integer>{
    private int ID;
    private String username;
    private String password;
    private String type;

    public User(String username, String password, String type)
    {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public User()
    {
        this.username="";
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(String type) {
        this.type = type;
    }
}
