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

    int dbId;

    /*
     * STANDARD-Bilddaten
     */
    String picDone = "https://images.pexels.com/photos/255379/pexels-photo-255379.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2";
    String hiddenPic = "https://images.pexels.com/photos/1939485/pexels-photo-1939485.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2";

    public MemoryCard(int gameId, int pairKey, String picUrl, int slot, int dbId, int status){
        setStatus(status);
        setGameId(gameId);
        setSlot(slot);
        setPairKey(pairKey);
        setPicUrl(picUrl);
        setDbId(dbId);
    }

    public MemoryCard(int gameId, int pairKey, String picUrl, int slot){
        setStatus(0);
        setGameId(gameId);
        setSlot(slot);
        setPairKey(pairKey);
        setPicUrl(picUrl);
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }
    public int getdbId() {
        return dbId;
    }

    public String getPic(){
        if(getStatus() == 0){
            return hiddenPic;
        }
        else if(getStatus() == 1){
            return getPicUrl();
        }

        return picDone;
        
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
