package gymhum.memory.model;

public class Player {

    String playerName;
    int key;
    int id;

    public Player(String playerName, int key){
        setPlayerName(playerName);
        setKey(key);
    }
    public Player(int id, String playerName, int key){
        setPlayerName(playerName);
        setKey(key);
        setId(id);
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setKey(int key) {
        this.key = key;
    }
    public int getKey() {
        return key;
    }
    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }
    public String getPlayerName() {
        return playerName;
    }
    
    @Override
	public String toString() {
		return "TODO";
	}
}
