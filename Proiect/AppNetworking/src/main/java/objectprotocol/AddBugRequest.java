package objectprotocol;

public class AddBugRequest implements Request{
    private String name;
    private String description;

    public AddBugRequest(String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
