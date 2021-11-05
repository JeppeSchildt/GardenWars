package com.garden.game.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import jdk.internal.vm.compiler.collections.Pair;

import java.util.Map;

public class Plant extends Actor {
    int water;
    String typeID;
    TiledMapTileLayer.Cell cell;
    Sprite sprite;
    int price;

    // Move to constants?
    enum PlantState {
        SEED {
            @Override
            public PlantState prevState() {
                return DEAD;
            }

            @Override
            public PlantState nextState() {
                return SMALL;
            }
        },
        SMALL {
            @Override
            public PlantState prevState() {
                return DEAD;
            }

            @Override
            public PlantState nextState() {
                return THRIVING;
            }
        },
        THRIVING {
            @Override
            public PlantState prevState() {
                return WITHERING;
            }

            @Override
            public PlantState nextState() {
                return THRIVING;
            }
        },
        WITHERING {
            @Override
            public PlantState prevState() {
                return DEAD;
            }

            @Override
            public PlantState nextState() {
                return THRIVING;
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
        };

        public abstract PlantState prevState();
        public abstract PlantState nextState();
    }
    PlantState state;

     public Map<PlantState, Vector2> waterStateMap;
     //public Pair<Integer, Integer> p;
    // Two different constructors. For convenience. Maybe it's not necessary.
    public Plant(int x, int y) {
        setPosition(x, y);
        state = PlantState.SEED;
        //p = new Pair<>(1,1);
    }

    Plant(int x, int y, String assetName) {
        setPosition(x, y);
        state = PlantState.SEED;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x*32, y*32); // 32 is length of a tile...
    }

    public void changeState() {
        if(state == PlantState.DEAD) {
             sprite = null;
             cell = null;

        }
    }

    public PlantState getState() {
        return state;
    }

    public void nextTurn() {}

    public String getTypeID() {
        return typeID;
    }

    public TiledMapTileLayer.Cell getCell() {
        return cell;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getPrice() {
        return price;
    }

    public int getWater() { return water; }


}
