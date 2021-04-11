package org.alkfejl.dao;

import org.alkfejl.config.PlayerConfiguration;
import org.alkfejl.model.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAOImpl implements PlayerDAO{

    private static final String SELECT_ALL_PLAYER_SCORES = "SELECT * FROM scores";
    private static final String ADD_PLAYER_SCORE = "INSERT INTO scores (name, time, level, score, gridsize) VALUES (?,?,?,?,?)";
    private final String connectionURL;

    public PlayerDAOImpl() {
        connectionURL = PlayerConfiguration.getValue("db.url");
    }

    @Override
    public List<Player> findall() {

        List<Player> result = new ArrayList<>();

        try{
            Connection connection = DriverManager.getConnection(connectionURL);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_PLAYER_SCORES);


            while (resultSet.next()){
                Player player = new Player();
                player.setId(resultSet.getInt("id"));
                player.setName(resultSet.getString("name"));
                player.setScore(resultSet.getInt("score"));
                player.setLevel(resultSet.getInt("level"));
                player.setTime(resultSet.getDouble("time"));
                player.setGridsize(resultSet.getString("gridsize"));

                result.add(player);
            }
        } catch (SQLException exception){
           exception.printStackTrace();
        }

        return result;
    }

    @Override
    public void save(Player player) {
        try{
            Connection connection = DriverManager.getConnection(connectionURL);
            PreparedStatement statement = connection.prepareStatement(ADD_PLAYER_SCORE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,player.getName());
            statement.setDouble(2,player.getTime());
            statement.setInt(3,player.getLevel());
            statement.setInt(4,player.getScore());
            statement.setString(5,player.getGridsize());
            statement.executeUpdate();

            ResultSet genKeys = statement.getGeneratedKeys();
            if(genKeys.next()){
                player.setId(genKeys.getInt(1));
            }
        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }
}




























