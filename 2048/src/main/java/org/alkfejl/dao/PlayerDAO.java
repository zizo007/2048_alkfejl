package org.alkfejl.dao;

import org.alkfejl.model.Player;

import java.util.List;

public interface PlayerDAO {

    List<Player> findall();
    Player save(Player player);

}
