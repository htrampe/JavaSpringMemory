package gymhum.memory.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import gymhum.memory.model.Game;
import gymhum.memory.model.MemoryCard;
import gymhum.memory.model.Player;

public class DatabaseController {

	/**
	 * BASIC DATABASE CONNECTION FUNCTIONS
	 * 
	 * createTable - Creates the Tables in the Database
	 * testConnection - Test the connection to the Database
	 * connect - returns a Connection-Object for interaction
	 * closeConnection - ends DB-Connection
	 */
	public void createTable() throws SQLException{
		Connection connection = connect();
		if(connection != null){
			Statement statement = connection.createStatement();
			// TABLE PLAYERS
			statement.execute("CREATE TABLE IF NOT EXISTS players(id INTEGER PRIMARY KEY, playername TEXT, playerkey TEXT)");
			// TABLE GAMES
			statement.execute("CREATE TABLE IF NOT EXISTS games(id INTEGER PRIMARY KEY, player1 TEXT, player2 TEXT, start TEXT, end TEXT, activePlayer TEXT, points1 INT, points2 INT)");
			// TABLE MEMORYCARDS
			statement.execute("CREATE TABLE IF NOT EXISTS memorycards(id INTEGER PRIMARY KEY, picUrl TEXT, slot TEXT, status TEXT, pairKey TEXT, gameId TEXT)");
			
			//statement.execute("ALTER TABLE games ADD p2 INT;");

			closeConnection(connection);
		}
	}

	public void testConnection(){
		Connection connection = null;
		try {
            String url = "jdbc:sqlite:memory.db";
            connection = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            closeConnection(connection);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}

	public Connection connect(){
		Connection connection = null;
		try {
            String url = "jdbc:sqlite:memory.db";
            connection = DriverManager.getConnection(url);
            return connection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return connection;
	}

	public void closeConnection(Connection connection) throws SQLException{
		connection.close();
	}

	/**
	 * 
	 * 	PLAYER FUNCTIONS
	 *  addPlayer
	 *  removePlayer
	 *  getPlayer
	 *  getAllPlayers
	 */
	public void addPlayer(Player player) throws SQLException{
		Connection connection = connect();
		if(connection != null){
			Statement statement = connection.createStatement();
			statement.execute("INSERT INTO players (playername, playerkey) values ('"+player.getPlayerName()+"','"+player.getKey()+"')");
			closeConnection(connection);
		}
	}

	public void removePlayer(int id) throws SQLException{
		Connection connection = connect();
		if(connection != null){
			Statement statement = connection.createStatement();
			statement.execute("DELETE FROM players WHERE id='"+id+"'");
			closeConnection(connection);
		}
	}

	public Player getPlayer(int id) throws SQLException{
		Connection connection = connect();
		Player player = null;
		if(connection != null){
			Statement statement = connection.createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM players WHERE id='"+id+"'");
			while(res.next()){
				player = new Player(res.getInt(1), res.getString(2), res.getInt(3));
			}
			closeConnection(connection);
		}
		return player;
	}

	public ArrayList<Player> getAllPlayers()  throws SQLException{
		ArrayList<Player> players = new ArrayList<>();

		Connection connection = connect();
		if(connection != null){
			Statement statement = connection.createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM PLAYERS ORDER BY playername ASC");
			while(res.next()){
				players.add(new Player(res.getInt(1), res.getString(2), res.getInt(3)));
			}
			closeConnection(connection);
		}
		
		return players;
	}

	/*
	 * MEMORY CARD FUNCTIONS
	 * 
	 * saveNewCard
	 */
	public int saveNewMemorCard(String picUrl, String slot, String status, String pairKey, String gameId) throws SQLException{
		Connection connection = connect();
		int lastId = -1;
		if(connection != null){
			Statement statement = connection.createStatement();
			statement.execute("INSERT INTO memorycards (picUrl, slot, status, pairKey, gameId) values ('"+picUrl+"','"+slot+"', '"+status+"', '"+pairKey+"', '"+gameId+"')");
			ResultSet rs = connection.prepareStatement("select last_insert_rowid();").executeQuery();
			lastId = rs.getInt("last_insert_rowid()");
			closeConnection(connection);
		}
		return lastId;
	}

	// Reset MemoryCards
	/*
	 * Visible Cards will be change to invisible if two cards opened!
	 */
	public void resetMemoryCardsInGame(int gameid) throws SQLException {
		Connection connection = connect();
		if(connection != null){
			Statement statement = connection.createStatement();
			statement.execute("UPDATE memorycards SET status=0 WHERE gameId='"+gameid+"' AND NOT status=2");
			closeConnection(connection);
		}
	}

	public void updateMemoryCard(int cardid, int gameid) throws SQLException{
		Connection connection = connect();

		if(connection != null){
			Statement statement = connection.createStatement();
			ResultSet res = statement.executeQuery("SELECT status FROM memorycards WHERE id='"+cardid+"' AND gameId='"+gameid+"' AND NOT status=2");
			while(res.next()){
				if(res.getInt(1) == 0){
					statement.execute("UPDATE memorycards SET status=1 WHERE id='"+cardid+"'");
				}
				else if(res.getInt(1) == 1){
					statement.execute("UPDATE memorycards SET status=0 WHERE id='"+cardid+"'");
				}
			}
			closeConnection(connection);
		}
	}

	public void updateMemoryCardStatus(int cardid, int status) throws SQLException{
		Connection connection = connect();

		if(connection != null){
			Statement statement = connection.createStatement();
			statement.execute("UPDATE memorycards SET status='"+status+"' WHERE id='"+cardid+"'");
			closeConnection(connection);
		}
	}


	/*
	 * GAME FUNCTIONS
	 * 
	 * getGame
	 * saveNewGame
	 * getGamesByPlayersIds
	 * 
	 */
	public Game getGame(int id) throws SQLException {
		Connection connection = connect();
		Game game = null;
		if(connection != null){
			Statement statement = connection.createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM players WHERE id='"+id+"'");
			while(res.next()){
				game = new Game(res.getInt(2), res.getInt(3), res.getInt(6));
			}
			closeConnection(connection);
		}
		return game;
	}

	public void alterActivePlayer(int game, int newPlayerId) throws SQLException {
		Connection connection = connect();

		if(connection != null){
			Statement statement = connection.createStatement();
			statement.execute("UPDATE games SET activePlayer="+newPlayerId+" WHERE id='"+game+"'");
			closeConnection(connection);
		}
	}

	public void addPoint(int player, int gameid) throws SQLException {
		Connection connection = connect();
		if(connection != null){
			Statement statement = connection.createStatement();
			if(player == 1){
				statement.execute("UPDATE games SET p1=p1+1 WHERE id='"+gameid+"'");
			}
			if(player == 2){
				statement.execute("UPDATE games SET p2=p2+1 WHERE id='"+gameid+"'");
			}
			closeConnection(connection);
		}
	}

	public Game getGameForGame(int id) throws SQLException {
		Connection connection = connect();
		Game game = null;
		if(connection != null){
			Statement statement = connection.createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM games WHERE id='"+id+"'");
			while(res.next()){
				game = new Game(res.getInt(2), res.getInt(3), res.getInt(6), res.getInt(9), res.getInt(10));
			}
			closeConnection(connection);
		}
		return game;
	}

	public int saveNewGame(Game game, int player1, int player2) throws SQLException{
		Connection connection = connect();
		int lastId = -1;
		if(connection != null){
			Statement statement = connection.createStatement();
			statement.execute("INSERT INTO games (player1, player2, start, activePlayer, p1, p2) values ('"+player1+"','"+player2+"', '"+game.getStart()+"', '0', 0, 0)");
			ResultSet rs = connection.prepareStatement("select last_insert_rowid();").executeQuery();
			lastId = rs.getInt("last_insert_rowid()");
			closeConnection(connection);
		}
		return lastId;
	}

	public ArrayList<Game> getGamesByPlayerIds(int player1, int player2) throws SQLException, ParseException{
		ArrayList<Game> games = new ArrayList<>();
		Connection connection = connect();

		if(connection != null){
			Statement statement = connection.createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM GAMES WHERE (player1='"+player1+"' AND player2='"+player2+"') OR (player1='"+player2+"' AND player2='"+player1+"') ORDER BY start ASC ");
			while(res.next()){
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
				Date parsedDate = (Date) dateFormat.parse(res.getString(4));
				Timestamp timestamp = new Timestamp(parsedDate.getTime());
				games.add(new Game(res.getInt(1), res.getInt(2), res.getInt(3), timestamp, res.getInt(9), res.getInt(10)));
			}
		}
		closeConnection(connection);
		return games;
	}

	public ArrayList<MemoryCard> getGameMemoryCards(int gameId) throws SQLException{
		ArrayList<MemoryCard> memoryCards = new ArrayList<>();
		Connection connection = connect();

		if(connection != null){
			Statement statement = connection.createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM memorycards WHERE gameId = '"+gameId+"'");
			while(res.next()){
				memoryCards.add(new MemoryCard(gameId, res.getInt("pairKey"), res.getString("picUrl"), res.getInt("slot"), res.getInt(1), res.getInt("status")));
			}
		}
		closeConnection(connection);
		return memoryCards;
	}
	
	public void removeGame(int id) throws SQLException{
		Connection connection = connect();
		if(connection != null){
			Statement statement = connection.createStatement();
			statement.execute("DELETE FROM games WHERE id='"+id+"'");
			closeConnection(connection);
		}
	}
    
}