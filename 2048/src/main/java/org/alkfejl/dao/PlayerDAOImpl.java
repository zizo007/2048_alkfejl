package org.alkfejl.dao;

import org.alkfejl.config.PlayerConfiguration;
import org.alkfejl.model.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAOImpl implements PlayerDAO{

    private static final String SELECT_ALL_PLAYER_SCORES = "SELECT * FROM scores";
    private static final String ADD_PLAYER_SCORE = "INSERT INTO scores (name, time, level, score) VALUES (?,?,?,?)";
    private String connectionURL;

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


                result.add(player);
            }




        } catch (SQLException exception){
           exception.printStackTrace();
        }

        return result;
    }

    @Override
    public Player save(Player player) {
        return null;
    }
}