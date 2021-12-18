package com.garden.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;


public class Assets extends AssetManager {
    public TextureAtlas textureAtlas, styleAtlas, textureCrops;
    public Label.LabelStyle largeTextStyle;
    public TextureRegionDrawable btnNextTurn, btnSetting, btnTalent,  goldIcon, waterIcon , dirtIcon, inGameBorder, buttonBorder;
    public TiledMapTileSet tileSet, tileSetNew;
    public TiledMapTileLayer.Cell dirtCell, grassCell;
    public ArrayList<Animation<TextureRegion>> walkAnimations, stopAnimations, bucketAnimations,
            wateringAnimations, journalistWalkAnimations, journalistStopAnimations;

    public Music menuMusic, inGameMusic, ambientSound_Bird, soundButtonPress, soundEnd, soundGameOver,
            soundGetWater, soundUseWater, soundUseGold, soundNewDay, soundTestEffektBeat;

    //public Sound soundButtonPress, soundEnd, soundGameOver, soundGetWater, soundUseWater, soundUseGold, soundNewDay;
    public float musicVolume = 1.0f;
    public TextureRegion[][] plantTextures;
    public TextureRegion[][] bucketTextures;

    
    public Assets() {
        loadFiles();
        generateFonts();
        loadSound();
        textureAtlas = this.get("pack_171221.atlas");
        styleAtlas = this.get("uiskin.atlas");
        initWalkAnimations();
        initStopAnimations();
        initPlantSprites();
        initWateringAnimations();
        initJournalist();

    }

    // Close files
    public void unloadAll() {
        this.unload("pack_171221.atlas");
        this.unload("uiskin.atlas");
        //this.unload("NewDesign/.png");
    }
    public void loadSound(){

        /* --------- InGameMusic setup  ---------  */
        inGameMusic = Gdx.audio.newMusic(Gdx.files.internal("music/PetParkMusic.mp3"));
        inGameMusic.setLooping(true);
        inGameMusic.setVolume(musicVolume);

        /* --------- InGameMusic setup  ---------  */
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Cooking-mania-short.mp3"));
        menuMusic.setLooping(true);
        menuMusic.setVolume(musicVolume);

        /* --------- Sound Effect setup  ---------  */
        ambientSound_Bird = Gdx.audio.newMusic(Gdx.files.internal("soundEffect/POL-morning-birds.mp3"));
        ambientSound_Bird.setVolume(0.5f); //1.0f max
        ambientSound_Bird.setLooping(true);

        //soundButtonPress = Gdx.audio.newSound(Gdx.files.internal("soundEffect/ButtonPressSound_mixkit-flute-alert-2307.mp3"));
        soundButtonPress = Gdx.audio.newMusic(Gdx.files.internal("soundEffect/ButtonPressSound_mixkit-game-ball-tap-2073.mp3"));
        soundEnd = Gdx.audio.newMusic(Gdx.files.internal("soundEffect/EndSound_mixkit-medieval-show-fanfare.mp3"));
        soundGameOver = Gdx.audio.newMusic(Gdx.files.internal("soundEffect/GameOver_mixkit-game-over-trombone-1940.mp3"));

        soundTestEffektBeat = Gdx.audio.newMusic(Gdx.files.internal("soundEffect/josefpres_bass-loops-063-with-drums-short-loop-120-bpm.mp3"));

        /* --------- Sound Effect InGame playing Sound  ---------  */
        soundGetWater = Gdx.audio.newMusic(Gdx.files.internal("soundEffect/inGame/getWater_water_pouring.mp3"));
        soundUseWater = Gdx.audio.newMusic(Gdx.files.internal("soundEffect/inGame/watering_plant.mp3"));
        soundUseGold = Gdx.audio.newMusic(Gdx.files.internal("soundEffect/inGame/use_gold.mp3"));
        soundNewDay = Gdx.audio.newMusic(Gdx.files.internal("soundEffect/inGame/newDay_cock_hahn.mp3"));





    }

    // Setup walking animations. Get region of spritesheet. Split into individual images.
    // Create animations corresponding to walking down, right, up and left.
    private void initWalkAnimations() {
        this.walkAnimations = new ArrayList<>();
        TextureAtlas.AtlasRegion atlasRegion = textureAtlas.findRegion("M");
        TextureRegion[][] tmpFrames = atlasRegion.split(16,17);

        int index = 0;
        for (int i = 0; i < 4; i++) {
            TextureRegion[] animationFrames = new TextureRegion[3];
            for (int j = 0; j < 3; j++) {
                animationFrames[index++] = tmpFrames[j][i];
            }
            index = 0;
            // First argument frame duration, how long does a frame last.
            //this.walkAnimations.add(new Animation<TextureRegion>(1f/5f, animationFrames));

            Animation<TextureRegion> animation = new Animation<TextureRegion>(1f/5f, animationFrames);
            animation.setPlayMode(Animation.PlayMode.LOOP);
            this.walkAnimations.add(animation);
        }

    }

    private void initStopAnimations() {
        this.stopAnimations = new ArrayList<>();
        TextureAtlas.AtlasRegion atlasRegion = textureAtlas.findRegion("M");
        TextureRegion[][] tmpFrames = atlasRegion.split(16,17);

        int index = 0;
        for (int i = 0; i < 4; i++) {  // 0 = down, 1 = right, 2 = up, 3 = left
            TextureRegion[] animationFrames = new TextureRegion[3];
            for (int j = 0; j < 3; j++) {  // 0 = stop, 1 and 2 = walk
                animationFrames[index++] = tmpFrames[0][i];
            }
            index = 0;
            //this.stopAnimations.add(new Animation<TextureRegion>(1f/5f, animationFrames));
            Animation<TextureRegion> animation = new Animation<TextureRegion>(1f/5f, animationFrames);
            animation.setPlayMode(Animation.PlayMode.LOOP);
            this.stopAnimations.add(animation);
        }

    }

    private void initWateringAnimations() {
        this.bucketAnimations = new ArrayList<>();
        this.wateringAnimations = new ArrayList<>();
        TextureAtlas.AtlasRegion atlasRegion = textureAtlas.findRegion("M_1_Water");
        TextureRegion[][] tmpFrames = atlasRegion.split(16,17);

        int index = 0;
        for (int i = 0; i < 4; i++) {
            TextureRegion[] animationFrames = new TextureRegion[2];
            for (int j = 0; j < 2; j++) {
                animationFrames[index++] = tmpFrames[j][i];
            }
            index = 0;
            Animation<TextureRegion> animation = new Animation<TextureRegion>(1f/4f, animationFrames);
            animation.setPlayMode(Animation.PlayMode.NORMAL);
            this.bucketAnimations.add(animation);


        }

        index = 0;
        for (int i = 0; i < 4; i++) {
            TextureRegion[] animationFrames = new TextureRegion[3];
            for (int j = 2; j < 5; j++) {
                animationFrames[index++] = tmpFrames[j][i];
            }
            index = 0;
            this.wateringAnimations.add(new Animation<TextureRegion>(1f/5f, animationFrames));
        }

        Animation<TextureRegion> animation = this.wateringAnimations.get(0);
        this.wateringAnimations.add(animation);

    }

    public void initJournalist() {
        journalistWalkAnimations = new ArrayList<>();
        journalistStopAnimations = new ArrayList<>();
        TextureAtlas.AtlasRegion atlasRegion = textureAtlas.findRegion("journalist");
        TextureRegion[][] tmpFrames = atlasRegion.split(16,17);

        int index = 0;
        for (int i = 0; i < 4; i++) {
            TextureRegion[] animationFrames = new TextureRegion[3];
            for (int j = 0; j < 3; j++) {
                animationFrames[index++] = tmpFrames[j][i];
            }
            index = 0;
            // First argument frame duration, how long does a frame last.
            //this.walkAnimations.add(new Animation<TextureRegion>(1f/5f, animationFrames));

            Animation<TextureRegion> animation = new Animation<TextureRegion>(1f/5f, animationFrames);
            animation.setPlayMode(Animation.PlayMode.LOOP);
            this.journalistWalkAnimations.add(animation);
        }

        index = 0;
        for (int i = 0; i < 4; i++) {  // 0 = down, 1 = right, 2 = up, 3 = left
            TextureRegion[] animationFrames = new TextureRegion[3];
            for (int j = 0; j < 3; j++) {  // 0 = stop, 1 and 2 = walk
                animationFrames[index++] = tmpFrames[0][i];
            }
            index = 0;
            //this.stopAnimations.add(new Animation<TextureRegion>(1f/5f, animationFrames));
            Animation<TextureRegion> animation = new Animation<TextureRegion>(1f/5f, animationFrames);
            animation.setPlayMode(Animation.PlayMode.LOOP);
            this.journalistStopAnimations.add(animation);
        }

    }

    // Initialize textures used for plants.
    private void initPlantSprites() {
        // Change to this size!!
        plantTextures = new TextureRegion[20][6];
        TextureAtlas.AtlasRegion atlasRegion = textureAtlas.findRegion("Crop_Spritesheet");
        TextureRegion[][] tmp = atlasRegion.split(16,16);
        //plantTextures = atlasRegion.split(32,32);
        int row = 0;
        int column = 0;
        for(int i = 0; i < 10; i++) {
            for (int j = 0; j < 12; j++) {
                plantTextures[row][column] = tmp[i][j];
                column = (column + 1) % 6;
                if(column == 0) {
                    row++;
                }
            }
        }

    }


    // Load map, textures, sprites
    public void loadFiles(){
        this.load("pack_171221.atlas", TextureAtlas.class);
        this.load("uiskin.atlas", TextureAtlas.class);

        this.load("inGameDesign/GameBorderNew.png", Texture.class);
        this.load("inGameDesign/ButtonBorder.png", Texture.class);

        this.load("inGameDesign/ButtonNextTurn.png", Texture.class);
        this.load("inGameDesign/ButtonSettings.png", Texture.class);
        this.load("inGameDesign/ButtonTalent.png", Texture.class);

        this.load("arrows/DownArrow.png", Texture.class);
        this.load("arrows/RightArrow.png", Texture.class);
        this.load("arrows/LeftArrow.png", Texture.class);

        /* --------- Keyboard Controls img  ---------  */
        this.load("KeyboardControls.png", Texture.class);


        this.load("black_screen.png", Texture.class);


        setLoader(TiledMap.class, new TmxMapLoader());
        load("World.tmx", TiledMap.class);

        finishLoading();

        tileSet = get("World.tmx", TiledMap.class).getTileSets().getTileSet("Terrain");
        dirtCell = new TiledMapTileLayer.Cell();
        dirtCell.setTile(this.tileSet.getTile(0x244));

        grassCell = new TiledMapTileLayer.Cell();
        grassCell.setTile(this.tileSet.getTile(0x125));

    }


    private void generateFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Chunk Five Print.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.incremental = true;


        parameter.size = 23;
        largeTextStyle = new Label.LabelStyle(generator.generateFont(parameter), Color.BROWN);


        generator.dispose();
    }
}
