package gymhum.memory.model;

public class MemoryCard {
    
    String picUrl;
    int slot;
    /*
     * 0 = card not visible
     * 1 = pic visible
     * 2 = pair done!
     */
    int status;
    // Key for pairing two cards, random int 999999
    int pairKey;
    int gameId;

    public MemoryCard(int gameId, int pairKey, String picUrl, int slot){
        setStatus(0);
        setGameId(gameId);
        setSlot(slot);
        setPairKey(pairKey);
        setPicUrl(picUrl);
    }

    public int getGameId() {
        return gameId;
    }
    public int getPairKey() {
        return pairKey;
    }
    public String getPicUrl() {
        return picUrl;
    }
    public int getSlot() {
        return slot;
    }
    public int getStatus() {
        return status;
    }
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
    public void setPairKey(int pairKey) {
        this.pairKey = pairKey;
    }
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
    public void setSlot(int slot) {
        this.slot = slot;
    }
    public void setStatus(int status) {
        this.status = status;
    }


}
