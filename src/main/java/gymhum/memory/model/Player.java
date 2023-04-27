package gymhum.memory.model;

public class Player {

    String name;

    public Player(String name){
        setName(name);
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
