package com.garden.game.Skills;
//  graph implementation https://www.geeksforgeeks.org/graph-and-its-representations/
import com.garden.game.player.Player;

import java.util.ArrayList;
import java.util.*;

public class SkillTree {

    static final int BASIC_PLANTS = 0;
    static final int FERTILIZER_1 = 1;
    static final int MORE_FLOWERS = 2;
    static final int MORE_FRUITS = 3;
    static final int FERTILIZER_2 = 4;
    static final int GENERAL = 5;
    static final int CONSTRUCTION = 6;
    static final int COMMUNICATION = 7;
    static final int WATER_1 = 8;
    static final int WATER_2 = 9;
    static final int IRRIGATION = 10;
    static final int AUTO_HARVEST = 11;

    ArrayList<ArrayList<Integer> > adj;
    public ArrayList<Skill> skills;
    public Player player;
    public boolean locked;
    public Skill currentlyLearning;

    public SkillTree(Player player) {
        this.player = player;
        // Creating a graph with 12 vertices
        int V = 12;

        skills = new ArrayList<>();

        skills.add(BASIC_PLANTS, new BasicPlants(3, player));
        skills.add(FERTILIZER_1, new BasicPlants(3, player));
        skills.add(MORE_FLOWERS, new BasicPlants(3, player));
        skills.add(MORE_FRUITS, new BasicPlants(3, player));
        skills.add(FERTILIZER_2, new BasicPlants(3, player));
        skills.add(GENERAL, new BasicPlants(3, player));
        skills.add(CONSTRUCTION, new BasicPlants(3, player));
        skills.add(COMMUNICATION, new BasicPlants(3, player));
        skills.add(WATER_1, new BasicPlants(3, player));
        skills.add(WATER_2, new BasicPlants(3, player));
        skills.add(IRRIGATION, new BasicPlants(3, player));
        skills.add(AUTO_HARVEST, new BasicPlants(3, player));




        adj = new ArrayList<ArrayList<Integer> >(V);

        for (int i = 0; i < V; i++)
            adj.add(new ArrayList<Integer>());

        // Adding edges one by one
        addEdge(adj, 0, 1);
        addEdge(adj, 1, 2);
        addEdge(adj, 1, 3);
        addEdge(adj, 2, 4);
        addEdge(adj, 3, 4);


        addEdge(adj, 4, 11);
        addEdge(adj, 5, 6);
        addEdge(adj, 5, 7);
        addEdge(adj, 5, 8);

        addEdge(adj, 6, 11);
        addEdge(adj, 8, 9);
        addEdge(adj, 9, 10);
        addEdge(adj, 10, 11);

    }

    // A utility function to add an edge in an
    // directed graph
    static void addEdge(ArrayList<ArrayList<Integer>> adj,
                        int u, int v)
    {
        adj.get(u).add(v);
        //adj.get(v).add(u);     // If this line is uncommented then it's a undirected graph
    }

    public void selectWhatToLearn(int i) {
        if(!locked) {
            currentlyLearning = skills.get(i);
        }
    }

    public void nextTurn() {
        currentlyLearning.nextTurn();
        if (currentlyLearning.learned) {
            locked = false;
        }
    }


}




/*
class Graph {

    // A utility function to add an edge in an
    // directed graph
    static void addEdge(ArrayList<ArrayList<Integer>> adj,
                        int u, int v)
    {
        adj.get(u).add(v);
        //adj.get(v).add(u);     // If this line is uncommented then it's a undirected graph
    }

    // A utility function to print the adjacency list
    // representation of graph
    static void printGraph(ArrayList<ArrayList<Integer> > adj)
    {
        for (int i = 0; i < adj.size(); i++) {
            System.out.println("\nAdjacency list of vertex" + i);
            System.out.print("head");
            for (int j = 0; j < adj.get(i).size(); j++) {
                System.out.print(" -> "+adj.get(i).get(j));
            }
            System.out.println();
        }
    }

    // Driver Code
    public static void main(String[] args)
    {
        // Creating a graph with 12 vertices
        int V = 12;
        ArrayList<ArrayList<Integer> > adj
                = new ArrayList<ArrayList<Integer> >(V);

        for (int i = 0; i < V; i++)
            adj.add(new ArrayList<Integer>());

        // Adding edges one by one
        addEdge(adj, 0, 1);
        addEdge(adj, 1, 2);
        addEdge(adj, 1, 3);
        addEdge(adj, 2, 4);
        addEdge(adj, 3, 4);


        addEdge(adj, 4, 11);
        addEdge(adj, 5, 6);
        addEdge(adj, 5, 7);
        addEdge(adj, 5, 8);

        addEdge(adj, 6, 11);
        addEdge(adj, 8, 9);
        addEdge(adj, 9, 10);
        addEdge(adj, 10, 11);


        printGraph(adj);
    }
}

 */