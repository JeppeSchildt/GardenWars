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
    public TextureAtlas textureAtlas, styleAtlas, textureCrops;
    public Label.LabelStyle largeTextStyle;
    public TiledMapTileSet tileSet;
    public TiledMapTileLayer.Cell grassCell;
    public ArrayList<Animation<TextureRegion>> walkAnimations, stopAnimations;

    public Music menuMusic, inGameMusic, ambientSound_Bird;
    public Sound soundButtonPress, soundEnd, soundGameOver;
    public float musicVolume = 1.0f;
    public TextureRegion[][] plantTextures;
    
    public Assets() {
        loadFiles();
        generateFonts();
        loadSound();
        textureAtlas = this.get("pack_character_crops.atlas");
        styleAtlas = this.get("uiskin.atlas");
        initWalkAnimations();
        initStopAnimations();
        initPlantSprites();

    }

    // Close files
    public void unloadAll() {
        this.unload("pack_character_crops.atlas");
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
        soundButtonPress = Gdx.audio.newSound(Gdx.files.internal("soundEffect/ButtonPressSound_mixkit-game-ball-tap-2073.mp3"));
        soundEnd = Gdx.audio.newSound(Gdx.files.internal("soundEffect/EndSound_mixkit-medieval-show-fanfare.mp3"));
        soundGameOver = Gdx.audio.newSound(Gdx.files.internal("soundEffect/GameOver_mixkit-game-over-trombone-1940.mp3"));
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
            this.walkAnimations.add(new Animation<TextureRegion>(1f/5f, animationFrames));
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
            this.stopAnimations.add(new Animation<TextureRegion>(1f/5f, animationFrames));
        }

    }

    // Initialize textures used for plants.
    private void initPlantSprites() {
        // Change to this size!!
        plantTextures = new TextureRegion[20][6];
        TextureAtlas.AtlasRegion atlasRegion = textureAtlas.findRegion("Crop_Spritesheet_32");
        TextureRegion[][] tmp = atlasRegion.split(32,32);
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
        this.load("pack_character_crops.atlas", TextureAtlas.class);
        this.load("uiskin.atlas", TextureAtlas.class);
        this.load("NewDesign/InGameButtons.png", Texture.class);
        this.load("well_00.png", Texture.class);

        setLoader(TiledMap.class, new TmxMapLoader());
        load("map6.tmx", TiledMap.class);

        finishLoading();

        tileSet = get("map6.tmx", TiledMap.class).getTileSets().getTileSet("wood_tileset");
        grassCell = new TiledMapTileLayer.Cell();
        grassCell.setTile(this.tileSet.getTile(0x1));

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
