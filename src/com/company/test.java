//package com.company;
//
//import org.graphstream.graph.*;
//import org.graphstream.graph.implementations.*;
//
//import java.util.*;
//
//public class test {
//
//public static void main(String[] args) {
//        int maxNode = 2000; // 10 | 33
////        int[][] edgesList = { {8, 6}, {6, 5},{6, 2},{2, 1},{7, 8},{7, 4}, {7, 3}, {3, 9}, {3, 10} };
////        int[][] edgesList = {{8, 28},  {28, 15}, {15, 32},{33, 8}, {33, 7},{33, 30},{33, 21},{33, 20},{20, 31}, {20, 14}, {31, 5}, {31, 25}, {5, 12}, {12, 26}, {30, 24}, {24, 29}, {29, 17}, {17, 13}, {13, 16}, {13, 10}, {8, 3}, {8, 23},{23, 11}, {28, 22}, {28, 9}, {28, 27}, {27, 1}, {27, 6}, {32, 4}, {32, 18}, {32, 19}, {32, 2} };
//
//        int[][] edgesList = generateEdgesListForTree(maxNode);
//
//
//    long startTime = System.currentTimeMillis();
//        ArrayList<Integer> PruferCode = buildPruferCode(maxNode, edgesList);
//        System.out.print("Prufer code:" + "\t");
//        PruferCode.forEach(node -> System.out.print(node + "\t"));
//        double timeDiff = System.currentTimeMillis() - startTime;
//        System.out.print("\t" + " -> " + timeDiff + "\n");
//        }
//
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
//        int childrenCount =  1 + (int)(Math.random() * ((4 - 1) + 1));
//        int[] foundChildren = new int[childrenCount];
//
//        for (int i = 1; i <= childrenCount; i++){
//            if (vertexesList.size() != 0) {
//                int childIndex = (int)(Math.random() * (vertexesList.size()));
//                int child = vertexesList.get(childIndex);
//                vertexesList.remove(childIndex);
//                edgesList.add(new int[] {parent, child});
//                foundChildren[i - 1] = child;
//            } else {
//                return;
//            }
//        }
//
////        Arrays.parallelSetAll(foundChildren, childIndex -> {
////            generateTreeChildren(vertexesList, edgesList, foundChildren[childIndex]);
////            return  foundChildren[childIndex];
////        });
//        for (int foundChild : foundChildren) {
//            generateTreeChildren(vertexesList, edgesList, foundChild);
//        }
//    }
//
//    static int[] getMatrixColumn(int[][] matrix, int columnIndex) {
//        int[] column = new int[matrix.length];
//
//        for (int i = 0; i < column.length; i++)
//            column[i] = matrix[i][columnIndex];
//
//
//        return column;
//    }
//
//    static int[][] getRemoval(int[][] listOfMaxNodesValues, ArrayList<int[]> chains) {
//
//        for (int i = 0; i < listOfMaxNodesValues.length; i++) {
//            int index = indexOfByFirstItem(chains, listOfMaxNodesValues[i][0]);
//            if (index != -1){
//                int[] currentChain = chains.get(index);
//                for (int j = 0; j < currentChain.length; j++) {
//                    listOfMaxNodesValues[i + j][1] = currentChain[j];
//                }
//                i += currentChain.length - 1;
//            }
//        }
//
//        return listOfMaxNodesValues;
//    }
//
//    static ArrayList<Integer> buildPruferCode(int maxNode, int[][] edgesList) {
//        int[][] matrix = createAdjacencyMatrix(maxNode, edgesList);
//        int[][] listOfMaxNodesValues = findMaxNodesValueInSubtrees(matrix); // todo Parallel
//        Arrays.sort(listOfMaxNodesValues, Comparator.comparingDouble(o -> o[0]));
//        int[] maxOfNodes = getMatrixColumn(listOfMaxNodesValues, 0);
//        ArrayList<int[]> chains = getChains(edgesList, maxOfNodes, maxNode); // todo Parallel
//        int[][] removal = getRemoval(listOfMaxNodesValues, chains); // todo Parallel
//        ArrayList<Integer> PruferCode = new ArrayList<>();
//
//        for (int i = 0; i < removal.length - 2; i++)
//            PruferCode.add(findParent(edgesList, removal[i][1]));
//
//        return PruferCode;
//    }
//
//    static ArrayList<int[]> findRepeat(int[] array, int maxNode) {
//        ArrayList<int[]> repeats = new ArrayList<>();
//
//        for (int i = 1; i <= maxNode; i++) {
//            int count = 0;
//
//            for (int j : array)
//                if (i == j) count++;
//            if (count > 1) repeats.add(new int[] {i, count});
//        }
//
//        return repeats;
//    }
//
//    static ArrayList<int[]> getChains(int[][] edgesList, int[] maxOfNodes, int maxNode) {
//        ArrayList<int[]> chains = new ArrayList<>();
//        ArrayList<int[]> repeats = findRepeat(maxOfNodes, maxNode);
//
//        repeats.forEach((n) -> {
//            int[] chain = new int[n[1]];
//            chain[0] = n[0];
//
//            for (int i = 0; i < chain.length - 1; i++)
//                chain[i + 1] = findParent(edgesList, chain[i]);
//
//            chains.add(chain);
//        });
//
//        return chains;
//    }
//
//    static int indexOfByFirstItem(ArrayList<int[]> list, int firstItem) {
//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i)[0] == firstItem) {
//                return i;
//            }
//        }
//        return -1;
//    }
//
//    static int findParent(int[][] list, int value) {
//        for (int[] pair: list) {
//            if (pair[1] == value)
//                return pair[0];
//        }
//        return -1;
//    }
//
//    static int[][] findMaxNodesValueInSubtrees(int[][] matrix) {
//        int[][] list = new int[matrix.length][2];
//
//        for (int i = 0; i < list.length; i++){
//            list[i][0] = Math.max(findMaxChild(0, matrix, i), i + 1);
//            list[i][1] = i + 1;
//        }
//
//        return list;
//    }
//
//    static int findMaxChild(int currentMax, int[][] matrix, int child) {
//        for (int i = 0; i < matrix[child].length; i++){
//            if (matrix[child][i] == 1) {
//                currentMax = findMaxChild(Math.max(currentMax, i + 1), matrix, i);
//            }
//        }
//        return currentMax;
//    }
//
//    static void showMatrix(int[][] matrix) {
//        for (int[] ints : matrix) {
//            for (int anInt : ints) {
//                System.out.print(anInt + "\t");
//            }
//            System.out.println();
//        }
//        System.out.println();
//    }
//
//    static int[][] createAdjacencyMatrix(int sizeMatrix, int[][] edgesList) {
//        int[][] matrix = new int[sizeMatrix][sizeMatrix];
//        for (int i = 0; i < sizeMatrix; i++)
//            for (int j = 0; j < sizeMatrix; j++)
//                matrix[i][j] = 0;
//
//        for (int[] ints : edgesList) {
//            matrix[((ints[0]) - 1)][((ints[1]) - 1)] = 1;
//        }
//
//        return matrix;
//    }
//}
//
