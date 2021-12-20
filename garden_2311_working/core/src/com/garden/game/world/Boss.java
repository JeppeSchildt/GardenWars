package com.garden.game.world;

import com.garden.game.GardenGame;
import com.garden.game.player.Quest;
import com.garden.game.tools.Constants;
import com.garden.game.tools.Dialogue;

import java.io.DataInput;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Journalist class
public class Boss extends Unit {
    public List<String> introDialog;
    public List<String> currentDialog;
    Random random;

    public Boss(GardenGame app) {
        super(app);
        this.walkAnimations = app.assets.journalistWalkAnimations;
        this.stopAnimations = app.assets.journalistStopAnimations;
        activeAnimation = stopAnimations.get(0);
        introDialog = Dialogue.DIALOG_INTRO;
        currentDialog = new ArrayList<>();
        random = new Random();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        drawThis = activeAnimation.getKeyFrame(elapsedTime);
    }

    public void setInitialPosition() {
        direc = Constants.LEFT;
        setX(31*Constants.TILE_WIDTH);
        setY(22*Constants.TILE_HEIGHT);
    }

    public void enterBoss(float x, float y) {
        setInitialPosition();
        setPosition(x+50, y);
    }

    public void intro(float x, float y) {
        direc = Constants.LEFT;
        setX(31*Constants.TILE_WIDTH);
        setY(22*Constants.TILE_HEIGHT);
        setPosition(x+30, y-10);
    }

    public void gameWon(float x, float y) {
        gameWonDialog();
        setInitialPosition();
        setPosition(x+50, y);
    }

    public void leave() {
        goSomewhereRemove(30*Constants.TILE_WIDTH, 22*Constants.TILE_HEIGHT);
    }

    public void weeklyCheck() {
        currentDialog.clear();
        for(Quest q : app.gameScreen.world.player.quests) {
            checkQuest(q);
        }
        if(currentDialog.isEmpty()) {
            negativeDialog();
        }
    }

    private void negativeDialog() {
        currentDialog.add(negativeEmployer());
        currentDialog.add(replyNegativeMain());
    }

    public void checkQuest(Quest q) {
        if(!q.isCompleted) {
            return;
        }

        switch (q.questID) {
            case Constants.KEEP_HEALTHY_QUEST_ID:
                currentDialog.add(Dialogue.BOSS + "Great job keeping the plants healthy!");
                break;
            case Constants.FLOWER_QUEST_ID:
                currentDialog.add(Dialogue.BOSS + "What a beautiful flower arrangement. The board really likes what you are doing.");
                break;
            case Constants.HARVEST_QUEST_ID:
                currentDialog.add(Dialogue.BOSS + "The city appreciates the profit you generate from harvesting plants.");
                break;
        }

        currentDialog.add(replyPositiveMain());
    }

    public String replyPositiveMain() {
        String replies[] = {"Thank you!", "I'm just doing my job.", "I try my best."};
        return Dialogue.MainCharacter + replies[random.nextInt(replies.length)];
    }

    public String replyNegativeMain() {
        String replies[] = {"Ok. I'm trying, but I will try HARDER", "I hear what you are saying its just that..."};
        return Dialogue.MainCharacter + replies[random.nextInt(replies.length)];
    }

    public String negativeEmployer() {
        String replies[] = {"You work leaves significant room for improvement. Consider learning some more things about horticulture.",
                "What is this supposed to look like a garden? Please do better.",
                "I think you are not doing your best."};
        return Dialogue.BOSS + replies[random.nextInt(replies.length)];
    }

    public void gameWonDialog() {
        currentDialog.clear();
        currentDialog.add(Dialogue.BOSS + "The park has never looked better. People from near and far are talking about this place. All due to your dedication and hard work");
        currentDialog.add(Dialogue.MainCharacter + "It's been a pleasure and an honor");
        currentDialog.add(Dialogue.BOSS + "If you want you can keep this job for life. The city thanks you and the mayor wants to give you the keys to the city!");
        currentDialog.add(Dialogue.MainCharacter + "*blushes*");
    }
}
