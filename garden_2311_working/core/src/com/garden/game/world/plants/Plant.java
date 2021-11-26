package com.garden.game.world.plants;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.garden.game.tools.Constants;

import java.util.ArrayList;
import java.util.Map;

public class Plant extends Actor {
    public float water;
    int price;
    Integer typeID;
    TiledMapTileLayer.Cell cell;
    Sprite activeSprite;
    TextureRegion[] textureRegions;
    public int profit;
    public Map<PlantState, Vector2> waterStateMap;
    private ArrayList<Sprite> sprites;
    private float waterLoss;

    // Stuff used for learned skills
    public boolean isFertilizer1, isFertilizer2, isGeneral;



    // Reconsider this... what happens if water increases a lot one round watering many times fx ??
    // Make simpler maybe, state machine by doing switch(water) somewhere ....
    // Right it would go through all states, which is perfectly fine maybe.
    public enum PlantState {
        SEED {
            @Override
            public PlantState prevState() {
                return DEAD;
            }

            @Override
            public PlantState nextState() {
                return SMALL;
            }

            @Override
            public int getStateSpriteInt() {
                return Constants.SPRITE_SEED;
            }

            @Override
            public String getStateName() {
                return Constants.SEED_STATE;
            }
        },
        SMALL {
            @Override
            public PlantState prevState() {
                return WITHERING;
            }

            @Override
            public PlantState nextState() {
                return HEALTHY;
            }

            @Override
            public int getStateSpriteInt() {
                return Constants.SPRITE_SMALL;
            }

            @Override
            public String getStateName() {
                return Constants.SMALL_STATE;
            }
        },
        HEALTHY {
            @Override
            public PlantState prevState() { return SMALL; }

            @Override
            public PlantState nextState() {
                return HEALTHY;
            }

            @Override
            public int getStateSpriteInt() {
                return Constants.SPRITE_HEALTHY;
            }

            @Override
            public String getStateName() {
                return Constants.HEALTHY_STATE;
            }
        },
        WITHERING {
            @Override
            public PlantState prevState() {
                return DEAD;
            }

            @Override
            public PlantState nextState() {
                return SMALL;
            }

            @Override
            public int getStateSpriteInt() {
                return Constants.SPRITE_WITHERING;
            }

            @Override
            public String getStateName() {
                return Constants.WITHERING_STATE;
            }
        },
        DEAD {
            @Override
            public PlantState prevState() {
                return DEAD;
            }

            @Override
            public PlantState nextState() {
                return DEAD;
            }

            @Override
            public int getStateSpriteInt() {
                return Constants.SPRITE_DEAD;
            }

            @Override
            public String getStateName() {
                return Constants.DEAD_STATE;
            }

        };

        // Not exactly previous and next, but instead next bad and next good state.
        public abstract PlantState prevState();
        public abstract PlantState nextState();
        public abstract int getStateSpriteInt();
        public abstract String getStateName();
    }
    PlantState state;

    // Two different constructors. For convenience. Maybe it's not necessary.
    public Plant(int x, int y) {
        setPosition(x, y);
        state = PlantState.SEED;
    }

   public Plant(int x, int y, TextureRegion[] textureRegions) {
        setPosition(x, y);
        state = PlantState.SEED;
        this.textureRegions = textureRegions;
        initSprites();
    }

    // Is simpler constructor with setters better?
    public Plant(int x, int y, String name, int waterLoss, int profit, int price, TextureRegion[] textureRegions, Map<PlantState, Vector2> waterStateMap) {
        this.waterLoss = waterLoss;
        this.profit = profit;
        this.price = price;
        this.waterStateMap = waterStateMap;
        this.textureRegions = textureRegions;
        state = PlantState.SEED;

        setPosition(x, y);
        setName(name);
        initSprites();

    }


    // Initialize ArrayList of sprites. Only call when we have a TextureRegion.
    private void initSprites() {
        sprites = new ArrayList<>(6);
        for(int i = 0; i < 6; i++) {
            sprites.add(i, new Sprite(textureRegions[i]));
        }
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x*Constants.TILE_WIDTH, y*Constants.TILE_HEIGHT); // 32 is length of a tile...
    }

    public void changeState() {
        if(water <= waterStateMap.get(state).x) {
            state = state.prevState();
            profit -= 2;
        } else if (water > waterStateMap.get(state).y) {
            state = state.nextState();
            profit += 2;
        }

        if(state == PlantState.DEAD) {
            activeSprite = null;
            cell = null;
            sprites = null;
            return;
            // Something is wrong here.... null pointer exception :O !!!
        }
        setActiveSprite();

    }

    public void setState(PlantState newState) {
        this.state = newState;
    }

    private void setActiveSprite() {
        activeSprite = sprites.get(state.getStateSpriteInt());
    }

    public PlantState getState() {
        return state;
    }

    public void nextTurn() {
        water = water-waterLoss;
        changeState();
    }

    public int getTypeID() {
        return typeID;
    }

    public TiledMapTileLayer.Cell getCell() {
        return cell;
    }

    public Sprite getActiveSprite() {
        return activeSprite;
    }

    public int getPrice() {
        return price;
    }

    public float getWater() { return water; }

    public void water(int amount) {
        water += amount;
    };


    public TextureRegion[] getTextureRegions() {
        return textureRegions;
    }

    public void setTextureRegions(TextureRegion[] textureRegions) {
        this.textureRegions = textureRegions;
    }


    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public Map<PlantState, Vector2> getWaterStateMap() {
        return waterStateMap;
    }

    public void setWaterStateMap(Map<PlantState, Vector2> waterStateMap) {
        this.waterStateMap = waterStateMap;
    }

    public ArrayList<Sprite> getSprites() {
        return sprites;
    }

    public float getWaterLoss() {
        return waterLoss;
    }

    public void setWaterLoss(float waterLoss) {
        this.waterLoss = waterLoss;
    }

    public void setActiveAnimation() {
        activeSprite = sprites.get(state.getStateSpriteInt());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(activeSprite != null) {
            activeSprite.setPosition(getX(), getY());
            activeSprite.draw(batch);
        }
    }
}
