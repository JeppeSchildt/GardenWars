package com.garden.game.Skills;

import com.badlogic.gdx.math.Vector2;
import com.garden.game.player.Player;
import com.garden.game.world.plants.Plant;

import java.util.Iterator;
import java.util.Map;

public class Communication extends Skill {
    public Communication(int turns, Player player) {
        super(turns, player);
        hasTurnWork = true;
    }

    @Override
    public void skillLearned() {
        super.skillLearned();
    }

}

