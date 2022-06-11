package com.company;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

import java.util.ArrayList;

public class Tree {
    private final int count;
    public int[][] edgesList;
    private final Graph graph;

    public Tree(int count) {
        System.setProperty("org.graphstream.ui", "swing");
        this.count = count;
        this.edgesList = generateEdgesListForTree(count);
        this.graph = new SingleGraph("Tutorial 1");
        buildTree();
        this.graph.display();
    }

    private void buildTree() {
        for (int i = 1; i <= this.count; i++){
            this.graph.addNode(Integer.toString(i));
            this.graph.attributeKeys();
        }

        for (int[] ints : this.edgesList)
            this.graph.addEdge("" + Integer.toString(ints[0]) + Integer.toString(ints[1]), Integer.toString(ints[0]), Integer.toString(ints[1]));
    }

    static int[][] generateEdgesListForTree(int vertexCount) {
        ArrayList<Integer> vertexesList = new ArrayList<>();
        for (int i = 1; i <= vertexCount; i++)
            vertexesList.add(i);

        ArrayList<int[]> edgesList = new ArrayList<>();

        int parentIndex = (int)(Math.random() * (vertexesList.size() + 1));
        int parent = vertexesList.get(parentIndex);
        vertexesList.remove(parentIndex);
        generateTreeChildren(vertexesList, edgesList, parent);

        int[][] edgesListMain = new int[edgesList.size()][2];
        for (int i = 0; i < edgesList.size(); i++) {
            edgesListMain[i] = edgesList.get(i);
        }
        return edgesListMain;
    }

    static void generateTreeChildren(ArrayList<Integer> vertexesList, ArrayList<int[]> edgesList, int parent) {
        int childrenCount = 1 + (int) (Math.random() * ((4 - 1) + 1));
        int[] foundChildren = new int[childrenCount];

        for (int i = 1; i <= childrenCount; i++) {
            if (vertexesList.size() != 0) {
                int childIndex = (int) (Math.random() * (vertexesList.size()));
                int child = vertexesList.get(childIndex);
                vertexesList.remove(childIndex);
                edgesList.add(new int[]{parent, child});
                foundChildren[i - 1] = child;
            } else {
                return;
            }
        }

        for (int foundChild : foundChildren) {
            generateTreeChildren(vertexesList, edgesList, foundChild);
        }

    }

//    static int[][] generateEdgesListForTree(int vertexCount) {
//        ArrayList<Integer> vertexesList = new ArrayList<>();
//        for (int i = 1; i <= vertexCount; i++)
//            vertexesList.add(i);
//
//        ArrayList<int[]> edgesList = new ArrayList<>();
//
//        int parentIndex = (int)(Math.random() * (vertexesList.size() + 1));
//        int parent = vertexesList.get(parentIndex);
//        vertexesList.remove(parentIndex);
//        generateTreeChildren(vertexesList, edgesList, parent);
//
//        int[][] edgesListMain = new int[edgesList.size()][2];
//        for (int i = 0; i < edgesList.size(); i++) {
//            edgesListMain[i] = edgesList.get(i);
//        }
//        return edgesListMain;
//    }
//
//    static void generateTreeChildren(ArrayList<Integer> vertexesList, ArrayList<int[]> edgesList, int parent) {
//        int childrenCount = 1 + (int) (Math.random() * ((4 - 1) + 1));
//        int[] foundChildren = new int[childrenCount];
//
//        for (int i = 1; i <= childrenCount; i++) {
//            if (vertexesList.size() != 0) {
//                int childIndex = (int) (Math.random() * (vertexesList.size()));
//                int child = vertexesList.get(childIndex);
//                vertexesList.remove(childIndex);
//                edgesList.add(new int[]{parent, child});
//                foundChildren[i - 1] = child;
//            } else {
//                return;
//            }
//        }
//
//        for (int foundChild : foundChildren) {
//            generateTreeChildren(vertexesList, edgesList, foundChild);
//        }
//
//    }
}