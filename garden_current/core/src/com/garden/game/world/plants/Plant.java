package com.garden.game.world.plants;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.garden.game.tools.Constants;

import java.util.Map;

public class Plant extends Actor {
    int water;
    Integer typeID;
    TiledMapTileLayer.Cell cell;
    Sprite activeSprite;
    TextureRegion[] textureRegions;
    int price;
    public int profit;

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
        },
        SMALL {
            @Override
            public PlantState prevState() {
                return DEAD;
            }

            @Override
            public PlantState nextState() {
                return HEALTHY;
            }

            @Override
            public int getStateSpriteInt() {
                return Constants.SPRITE_SMALL;
            }
        },
        HEALTHY {
            @Override
            public PlantState prevState() { return WITHERING; }

            @Override
            public PlantState nextState() {
                return HEALTHY;
            }

            @Override
            public int getStateSpriteInt() {
                return Constants.SPRITE_HEALTHY;
            }
        },
        WITHERING {
            @Override
            public PlantState prevState() {
                return DEAD;
            }

            @Override
            public PlantState nextState() {
                return HEALTHY;
            }

            @Override
            public int getStateSpriteInt() {
                return Constants.SPRITE_WITHERING;
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
        };

        // Not exactly previous and next, but instead next bad and next good state.
        public abstract PlantState prevState();
        public abstract PlantState nextState();
        public abstract int getStateSpriteInt();
    }
    PlantState state;

     public Map<PlantState, Vector2> waterStateMap;

    // Two different constructors. For convenience. Maybe it's not necessary.
    public Plant(int x, int y) {
        setPosition(x, y);
        state = PlantState.SEED;
    }

    Plant(int x, int y, TextureRegion[] textureRegions) {
        setPosition(x, y);
        state = PlantState.SEED;
        this.textureRegions = textureRegions;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x*32, y*32); // 32 is length of a tile...
    }

    public void changeState() {

    }

    public PlantState getState() {
        return state;
    }

    public void nextTurn() {}

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

    public int getWater() { return water; }

    public void water(int amount) {
        water += amount;
    };

    public void setActiveAnimation() {
        if(textureRegions != null) {
            activeSprite = new Sprite(textureRegions[state.getStateSpriteInt()]);
            activeSprite.setPosition(getX(), getY());
        }
    }
}
/*
    // Consider making sprites when creating plant instead?? And not new sprite when we change state. But ok because of GC?
    public void changeState() {
        activeSprite = new Sprite(textureRegions[state.getStateSpriteInt()]);
    }
 */