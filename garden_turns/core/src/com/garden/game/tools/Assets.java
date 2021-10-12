package com.garden.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class Assets extends AssetManager {
    public TextureAtlas textureAtlas;
    public Label.LabelStyle largeTextStyle;
    public TextureRegionDrawable button;
    //final Texture

    public Assets() {
        loadFiles();
        generateFonts();
        textureAtlas = this.get("pack5.atlas");
    }

    // Load map and textures
    public void loadFiles(){
        this.load("pack5.atlas", TextureAtlas.class);
        this.load("arrow_button.png", Texture.class);
        this.load("pixel.png", Texture.class);
        setLoader(TiledMap.class, new TmxMapLoader());
        load("map3.tmx", TiledMap.class);
        finishLoading();
        //button = this.get("arrow_button.png", TextureRegionDrawable.class);
        final Texture buttonSheet = this.get("arrow_button.png", Texture.class);
        final TextureRegion button_ = new TextureRegion(buttonSheet, 0, 0, 64, 64);
        button = new TextureRegionDrawable(button_);

    }

    private void generateFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Blazed.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.incremental = true;


        parameter.size = 14;
        largeTextStyle = new Label.LabelStyle(generator.generateFont(parameter), Color.BLACK);


        generator.dispose();
    }
}
