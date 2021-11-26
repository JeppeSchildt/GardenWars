package com.garden.game.Skills;

import com.garden.game.player.Player;
import com.garden.game.tools.Constants;

public class MoreFlowers  extends Skill{
    public MoreFlowers(int turns, Player player) {
        super(turns, player);
    }

    @Override
    public void skillLearned(){
        super.skillLearned();
        player.makePlantAvailable(Constants.TULIP);
        player.makePlantAvailable(Constants.ROSE);
        player.makePlantAvailable(Constants.SUNFLOWER);

    }
}
