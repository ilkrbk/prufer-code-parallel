package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть кількість вузлів дерева: ");
        int maxNode = scanner.nextInt();
        System.out.print("Натисніть\n1 -> Для запуску паралельного алгоритму\n2 -> Для запуску кастомного послідовного алгоритму\n3 -> Для запуску класічного послідовного алгоритму\n4 -> Для запуску порівняння\n");
        int type = scanner.nextInt();
        int[][] edgesList = generateEdgesListForTree(maxNode);

        switch (type) {
            case 1 -> {
                ParallelPruferCode pruferCode = new ParallelPruferCode(edgesList, maxNode, true);
                pruferCode.printCode();
            }
            case 2 -> {
                ParallelPruferCode pruferCode = new ParallelPruferCode(edgesList, maxNode, false);
                pruferCode.printCode();
            }
            case 3 -> {
                SequentialPruferCode sequential = new SequentialPruferCode(edgesList, maxNode);
                sequential.printCode();
            }
            default -> analytics(edgesList, maxNode);
        }
    }

    static void analytics(int[][] edgesList, int maxNode){
        System.out.print("Кастомний послідовний алгоритм \n");
        ParallelPruferCode sequentialPruferCode = new ParallelPruferCode(edgesList, maxNode, false);
        sequentialPruferCode.printCode();
        System.out.print("\nКласічний послідовний алгоритм \n");
        SequentialPruferCode sequential = new SequentialPruferCode(edgesList, maxNode);
        sequential.printCode();
        System.out.print("\nПаралельний алгоритм \n");
        ParallelPruferCode parallelPruferCode = new ParallelPruferCode(edgesList, maxNode, true);
        parallelPruferCode.printCode();
    }

    static int[][] generateEdgesListForTree(int vertexCount) {
        ArrayList<Integer> vertexesList = new ArrayList<>();
        for (int i = 1; i <= vertexCount; i++)
            vertexesList.add(i);

        ArrayList<int[]> edgesList = new ArrayList<>();
        ArrayList<Integer> foundChildren = new ArrayList<>();


        int parent = vertexesList.get(vertexesList.size() - 1);
        vertexesList.remove(vertexesList.size() - 1);
        generateTreeChildren(vertexesList, edgesList, foundChildren, parent);

        int[][] edgesListMain = new int[edgesList.size()][2];
        for (int i = 0; i < edgesList.size(); i++) {
            edgesListMain[i] = edgesList.get(i);
        }
        return edgesListMain;
    }

    static void generateTreeChildren(ArrayList<Integer> vertexesList, ArrayList<int[]> edgesList, ArrayList<Integer> foundChildren, int parent) {
        int childrenCount =  1 + (int)(Math.random() * ((2 - 1) + 1));

        for (int i = 1; i <= childrenCount; i++){
            if (vertexesList.size() != 0) {
                int childIndex = (int)(Math.random() * (vertexesList.size()));
                int child = vertexesList.get(childIndex);
                vertexesList.remove(childIndex);
                edgesList.add(new int[] {parent, child});
                foundChildren.add(child);
            } else {
                return;
            }
        }
        double random = new Random().nextDouble();

        int randChildIndex = (int)(random * (foundChildren.size()));
        generateTreeChildren(vertexesList, edgesList, foundChildren, foundChildren.get(randChildIndex));
    }
}