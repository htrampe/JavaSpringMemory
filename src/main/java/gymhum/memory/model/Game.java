package gymhum.memory.model;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Game {

    int player1;
    int player2;
    Timestamp start;
    Timestamp end;
    ArrayList<MemoryCard> cards;
    int dbId;


    public Game(int player1, int player2){
        setStart(new Timestamp(System.currentTimeMillis()));
        setPlayer1(player1);
        setPlayer2(player2);
    }

    public Game(int dbId, int player1, int player2, Timestamp timestamp){
        setPlayer1(player1);
        setPlayer2(player1);
        setStart(timestamp);
        setDbId(dbId);
    }
    public void setDbId(int dbId) {
        this.dbId = dbId;
    }
    public int getdbId() {
        return dbId;
    }
    public Timestamp getEnd() {
        return end;
    }
    public Timestamp getStart() {
        return start;
    }
    public void setEnd(Timestamp end) {
        this.end = end;
    }
    public void setStart(Timestamp start) {
        this.start = start;
    }
    public void setPlayer1(int player1) {
        this.player1 = player1;
    }
    public void setPlayer2(int player2) {
        this.player2 = player2;
    }
    public int getPlayer1() {
        return player1;
    }
    public int getPlayer2() {
        return player2;
    }

    
}
