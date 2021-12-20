package com.garden.game.world;

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
    public float profit;
    public Map<PlantState, Vector2> waterStateMap;
    public Map<PlantState, Float> waterStateMap_;
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

    // Is simpler constructor with setters better?
    public Plant(int id, int x, int y, TextureRegion[] textureRegions, Map<PlantState, Float> waterStateMap, TiledMapTileLayer.Cell cell) {
        this.typeID = id;
        this.waterLoss = Constants.idWaterLossMap.get(id);
        this.profit = Constants.idProfitMap.get(id);
        this.price = Constants.idPriceMap.get(id);
        this.waterStateMap_ = waterStateMap;
        this.textureRegions = textureRegions;
        state = PlantState.SEED;
        this.cell = cell;
        setPosition((float) (x+ Constants.PLANT_OFFSET_X), (float) (y + Constants.PLANT_OFFSET_Y));
        setName(Constants.idNameMap.get(id));
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
            profit -= profit;
        } else if (water > waterStateMap.get(state).y) {
            state = state.nextState();
            profit += profit;
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

    public void changeState_() {
        if(water <= waterStateMap_.get(state.prevState())) {
            state = state.prevState();
        } else if (water > waterStateMap_.get(state)) {
            state = state.nextState();
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
        changeState_();
    }

    /* Is this plant a flower? */
    public boolean isFlower() {
        if(Constants.idIsFlowerMap.get(typeID)) {
            return true;
        }
        return false;
    }

    /* Harvest plant. Get some gold and set plant to the small state. */
    public float harvest() {
        // Can only harvest healthy plants.
        if(getState() != PlantState.HEALTHY) {
            return 0;
        }

        //float profit_ = profit;
        //profit = 2*Constants.idProfitMap.get(typeID);
        water = water/2;
        setState(PlantState.SMALL);
        setActiveAnimation();

        return profit;
    }

    public int getTypeID() {
        return typeID;
    }

    public TiledMapTileLayer.Cell getCell() {
        return cell;
    }


    public int getPrice() {
        return price;
    }

    public float getWater() { return water; }

    public void water(int amount) {
        water += amount;
    };

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
