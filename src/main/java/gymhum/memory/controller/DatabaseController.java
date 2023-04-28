package gymhum.memory.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import gymhum.memory.model.Player;

public class DatabaseController {

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
			System.out.println("ID: " + id);
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

	public void createTable() throws SQLException{
		Connection connection = connect();
		if(connection != null){
			Statement statement = connection.createStatement();
			// TABLE PLAYERS
			statement.execute("CREATE TABLE IF NOT EXISTS players(id INTEGER PRIMARY KEY, playername TEXT, playerkey TEXT)");
			// TABLE GAMES
			statement.execute("CREATE TABLE IF NOT EXISTS games(id INTEGER PRIMARY KEY, player1 TEXT, player2 TEXT)");
			closeConnection(connection);
		}
	}
    
}