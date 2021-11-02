package com.garden.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;


public class Assets extends AssetManager {
    public TextureAtlas textureAtlas, styleAtlas;
    public Label.LabelStyle largeTextStyle;
    public TextureRegionDrawable nextturnIcon, goldIcon, waterIcon , dirtIcon;
    public TiledMapTileSet tileSet;
    public TiledMapTileLayer.Cell grassCell;
    public Drawable pixel;
    public ArrayList<Animation<TextureRegion>> walkAnimations;

    public Music menuMusic, inGameMusic, ambientSound_Bird;
    public Sound soundButtonPress, soundEnd, soundGameOver;
    public float musicVolume = 1.0f;
    
    public Assets() {
        loadFiles();
        generateFonts();
        loadSound();
        textureAtlas = this.get("pack5.atlas");
        styleAtlas = this.get("uiskin.atlas");
        walkAnimations = initWalkAnimations();
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
        soundButtonPress = Gdx.audio.newSound(Gdx.files.internal("soundEffect/ButtonPressSound_mixkit-game-ball-tap-2073.mp3"));
        soundEnd = Gdx.audio.newSound(Gdx.files.internal("soundEffect/EndSound_mixkit-medieval-show-fanfare.mp3"));
        soundGameOver = Gdx.audio.newSound(Gdx.files.internal("soundEffect/GameOver_mixkit-game-over-trombone-1940.mp3"));
    }

    // Setup walking animations. Load spritesheet. Split into individual images.
    // Create animations corresponding to walking down, right, up and left.
    private ArrayList<Animation<TextureRegion>> initWalkAnimations() {
        walkAnimations = new ArrayList<>();
        Texture img = this.get("M_01.png");
        TextureRegion[][] tmpFrames = TextureRegion.split(img, 16, 17);
        Animation<TextureRegion> animation;


        int index = 0;
        for (int i = 0; i < 4; i++) {
            TextureRegion[] animationFrames = new TextureRegion[3];
            for (int j = 0; j < 3; j++) {
                animationFrames[index++] = tmpFrames[j][i];
            }
            index = 0;
            walkAnimations.add(new Animation<TextureRegion>(1f/5f, animationFrames));
        }

        // First argument frame duration, how long does a frame last.

        return walkAnimations;
    }

    // Load map and textures
    public void loadFiles(){
        this.load("pack5.atlas", TextureAtlas.class);
        this.load("NextTurn_3.png", Texture.class);
        this.load("gold_icon.png", Texture.class);
        this.load("water_icon.png", Texture.class);
        this.load("dirt_icon.png", Texture.class);
        this.load("uiskin.atlas", TextureAtlas.class);
        this.load("M_01.png", Texture.class);

        this.load("pixel.png", Texture.class);
        setLoader(TiledMap.class, new TmxMapLoader());
        load("map6.tmx", TiledMap.class);


        finishLoading();

        tileSet = get("map6.tmx", TiledMap.class).getTileSets().getTileSet("wood_tileset");
        grassCell = new TiledMapTileLayer.Cell();
        grassCell.setTile(this.tileSet.getTile(0x1));

        final Texture buttonSheet = this.get("NextTurn_3.png", Texture.class);
        final TextureRegion button_ = new TextureRegion(buttonSheet, 0, 0, 88, 100);
        nextturnIcon = new TextureRegionDrawable(button_);

        loadIcon();


    }

    private void loadIcon(){
        final Texture goldSheet = this.get("gold_icon.png", Texture.class);
        final TextureRegion goldIcon_ = new TextureRegion(goldSheet, 0, 0, 100, 70);
        goldIcon = new TextureRegionDrawable(goldIcon_);

        final Texture waterSheet = this.get("water_icon.png", Texture.class);
        final TextureRegion waterIcon_ = new TextureRegion(waterSheet, 0, 0, 100, 70);
        waterIcon = new TextureRegionDrawable(waterIcon_);

        final Texture dirtSheet = this.get("dirt_icon.png", Texture.class);
        final TextureRegion dirtIcon_ = new TextureRegion(dirtSheet, 0, 0, 100, 70);
        dirtIcon = new TextureRegionDrawable(dirtIcon_);
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
