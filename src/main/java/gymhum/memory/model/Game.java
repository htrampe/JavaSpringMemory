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
    int activePlayer;
    int p1;
    int p2;

    public Game(int player1, int player2, int activePlayer){
        setStart(new Timestamp(System.currentTimeMillis()));
        setPlayer1(player1);
        setPlayer2(player2);
        setActivePlayer(activePlayer);
    }
    public Game(int player1, int player2, int activePlayer, int p1, int p2){
        setStart(new Timestamp(System.currentTimeMillis()));
        setPlayer1(player1);
        setPlayer2(player2);
        setActivePlayer(activePlayer);
        setP1(p1);
        setP2(p2);
    }
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
    public Game(int dbId, int player1, int player2, Timestamp timestamp, int p1, int p2){
        setPlayer1(player1);
        setPlayer2(player1);
        setStart(timestamp);
        setDbId(dbId);
        setP1(p1);
        setP2(p2);
    }

    public void setP1(int p1) {
        this.p1 = p1;
    }
    public void setP2(int p2) {
        this.p2 = p2;
    }
    public int getP1() {
        return p1;
    }
    public int getP2() {
        return p2;
    }

    public void setActivePlayer(int activePlayer) {
        this.activePlayer = activePlayer;
    }
    public int getActivePlayer() {
        return activePlayer;
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
