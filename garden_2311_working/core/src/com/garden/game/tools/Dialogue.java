package com.garden.game.tools;

import java.util.ArrayList;
import java.util.List;

public class Dialogue {


    public static String BOSS = "Boss: \n";
    public static String MainCharacter = "You: \n";
    private static int readingSpeed = 12;

    /* -------- Intro ---------- */
    public static String dia_intro_00 = "";
    public static String dia_intro_0 = BOSS + "Good morning Johnny boy.";

    public static String dia_intro_1 = MainCharacter + "Johnny boy..?!\n Good morning to you too boss... lady!";

    public static String dia_intro_3 = BOSS + "So you are our new park employee.... \n And you are of course aware of what you need to do?";

    public static String dia_intro_5 = MainCharacter + "No, not at all!";

    public static String dia_intro_6 = BOSS + "Yeah okay. It is your job to plant as many plants as possible and to maintain them.";
    public static String dia_intro_7 = BOSS + "I will of course give you some specific tasks that you need to perform.";
    public static String dia_intro_8 = BOSS + "And once a week I will come and look at your progress. If you have performed my tasks I will give you a reward.";

    public static String dia_intro_9  = MainCharacter + "What will happen if I do not perform all the tasks during a week?";

    public static String dia_intro_10 = BOSS + "Try to keep the deadlines. You will only receive rewards for the tasks you perform.";
    public static String dia_intro_11 = BOSS + "And the ones you do not have time to complete you have to do the weeks after.";

    public static String dia_intro_12 = MainCharacter + "Fantastic! Well I think I'm ready to make the park to sparkle like a gay parade in mid June.";

    public static String dia_intro_13 = BOSS + "I think that's an interesting choice of words, but whatever tickles your fancy...";
    public static String dia_intro_14 = BOSS + "And one more thing.";
    public static String dia_intro_15 = BOSS + "Here in the area we sometimes do not get rain for several days, which make that lake dry out.";
    public static String dia_intro_16 = BOSS + "So remember to keep an eye on the weather station so you do not run out of water.";

    public static String dia_intro_17 = MainCharacter + "What about the plants I have planted, will they also dry out?";

    public static String dia_intro_18 = BOSS + "Nope!" + " As long as you give them water once in a while, it should all work out";
    public static String dia_intro_20 = BOSS + "Bye bye!";


    public static final List<String> DIALOG_INTRO = new ArrayList<String>() {{
        add(dia_intro_00);
        add(dia_intro_0);
        add(dia_intro_1);
        //add(dia_intro_2);
        add(dia_intro_3);
        //add(dia_intro_4);
        add(dia_intro_5);
        add(dia_intro_6);
        add(dia_intro_7);
        add(dia_intro_8);
        add(dia_intro_9);
        add(dia_intro_10);
        add(dia_intro_11);
        add(dia_intro_12);
        add(dia_intro_13);
        add(dia_intro_14);
        add(dia_intro_15);
        add(dia_intro_16);
        add(dia_intro_17);
        add(dia_intro_18);
        //add(dia_intro_19);
        add(dia_intro_20);
    }};

    public static int getReadingSpeed() {
        return readingSpeed;
    }
    public static void setReadingSpeed(int val) {
        readingSpeed = val;
    }
}
