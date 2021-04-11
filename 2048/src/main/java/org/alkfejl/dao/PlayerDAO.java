package org.alkfejl.dao;

import org.alkfejl.model.Player;

import java.util.List;

public interface PlayerDAO {

    List<Player> findall();
    void save(Player player);

}
