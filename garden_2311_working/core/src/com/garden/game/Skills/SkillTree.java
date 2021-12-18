package com.garden.game.Skills;
//  graph implementation https://www.geeksforgeeks.org/graph-and-its-representations/
import com.garden.game.player.Player;
import com.garden.game.tools.Constants;

import java.util.ArrayList;
import java.util.*;

public class SkillTree {


    public ArrayList<ArrayList<Integer> > adj;
    public ArrayList<Skill> skills;
    public Player player;
    public boolean[] availableToLearn;
    public Skill currentlyLearning;
    public boolean locked;
    public int index;


    public SkillTree(Player player) {
        this.player = player;
        // Creating a graph with 12 vertices
        int V = 12;
        availableToLearn = new boolean[12];
        availableToLearn[Constants.BASIC_PLANTS] = true;
        availableToLearn[Constants.GENERAL] = true;
        skills = new ArrayList<>();

        skills.add(Constants.BASIC_PLANTS, new BasicPlants(3, player));
        skills.add(Constants.FERTILIZER_1, new Fertilizer(3, player));
        skills.add(Constants.MORE_FLOWERS, new MoreFlowers(3, player));
        skills.add(Constants.MORE_FRUITS, new MoreFruits(3, player));
        skills.add(Constants.FERTILIZER_2, new Fertilizer2(3, player));
        skills.add(Constants.GENERAL, new General(3, player));
        skills.add(Constants.CONSTRUCTION, new Construction(3, player));
        skills.add(Constants.COMMUNICATION, new Communication(3, player));
        skills.add(Constants.WATER_1, new Water(3, player));
        skills.add(Constants.WATER_2, new Water2(3, player));
        skills.add(Constants.IRRIGATION, new Irrigation(3, player));
        skills.add(Constants.AUTO_HARVEST, new AutoHarvest(3, player));



        adj = new ArrayList<ArrayList<Integer> >(V);

        for (int i = 0; i < V; i++)
            adj.add(new ArrayList<Integer>());

    }

    public void setCurrentlyLearning(int i) {


        if(!locked) {
            currentlyLearning = skills.get(i);
        }
        index = i;
    }


    public void nextTurn() {
        if(currentlyLearning != null) {
            currentlyLearning.nextTurn();
            if (currentlyLearning.learned) {
                locked = false;
                for (int i : currentlyLearning.adjacent) {
                    availableToLearn[i] = true;
                }
            }
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