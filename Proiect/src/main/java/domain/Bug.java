package domain;

public class Bug implements Identifiable<Integer>{
    private int ID;
    private String name;
    private String description;

    public Bug(String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    public Bug()
    {
        this.name = "";
        this.description = "";
    }

    @Override
    public Integer getID() {
        return this.ID;
    }

    @Override
    public void setID(Integer id) {
        this.ID = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
