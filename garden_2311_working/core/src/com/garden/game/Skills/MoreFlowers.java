package com.garden.game.Skills;

import com.garden.game.player.Player;
import com.garden.game.tools.Constants;

public class MoreFlowers  extends Skill{
    public MoreFlowers(int turns, Player player) {
        super(turns, player);
        name = "More Flowers";
        adjacent.add(Constants.FERTILIZER_2);
    }

    @Override
    public void skillLearned(){
        super.skillLearned();
        player.makePlantAvailable(Constants.SUNFLOWER);
        player.makePlantAvailable(Constants.ROSE);
        player.makePlantAvailable(Constants.TULIP);
    }
    @Override
    public void nextTurn() {
        super.nextTurn();
        if(turns == 0) {
            skillLearned();
        }
    }
}
