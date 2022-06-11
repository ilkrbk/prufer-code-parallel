package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class ParallelPruferCode {
    private final Matrix matrix;
    private final Matrix removal;
    private final Matrix edgesList;
    private final Boolean parallel;
    private final Matrix maxNodesValues;
    private final int[][] chains;
    public int[] code;

    public ParallelPruferCode(int [][] edgesList, int maxNode, Boolean parallel){
        long startTime = System.currentTimeMillis();
        this.parallel = parallel;
        this.edgesList = new Matrix(edgesList);
        this.matrix = new Matrix(edgesList, maxNode, false);
        this.maxNodesValues = getMaxNodesValues(matrix.matrix); // todo Parallel
        this.maxNodesValues.sortByColumn(0, this.parallel);// todo Parallel
        this.chains = getChains(); // todo Parallel
        this.removal = getRemoval(); // todo Parallel
        this.code = new int[matrix.size - 2];

        buildPruferCode(); // todo Parallel
        System.out.print("Час виконання у мілісекундах -> " + (System.currentTimeMillis() - startTime) + "\n");
    }

    public void printCode(){
        System.out.print("Prufer code:" + "\t");
        for (int i : this.code) {
            System.out.print(i + "\t");
        }
        System.out.print("\n");
    }

    private void buildPruferCode() {
        if (this.parallel) {
            Arrays.parallelSetAll(this.code, n -> findParent(this.removal.matrix[n][1]));
        } else {
            for (int i = 0; i < this.removal.size - 2; i++)
                this.code[i] = findParent(this.removal.matrix[i][1]);
        }
    }

    private Matrix getRemoval() {

        if (this.parallel) {
            Arrays.parallelSetAll(this.chains, n -> {
                int index = indexOfByFirstItem(this.maxNodesValues.matrix, this.chains[n][0]);
                if (index != -1) {
                    for (int j = 0; j < this.chains[n].length; j++) {
                        this.maxNodesValues.matrix[index + j][1] = this.chains[n][j];
                    }
                }
                return this.chains[n];
            });
        } else {
            for (int i = 0; i < this.chains.length; i++) {
                int index = indexOfByFirstItem(this.maxNodesValues.matrix, this.chains[i][0]);
                if (index != -1) {
                    for (int j = 0; j < this.chains[i].length; j++) {
                        this.maxNodesValues.matrix[index + j][1] = this.chains[i][j];
                    }
                }
            }
        }

        return this.maxNodesValues;
    }

    private int indexOfByFirstItem(int[][] list, int firstItem) {
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null && list[i][0] == firstItem) {
                return i;
            }
        }
        return -1;
    }

    private int[][] getChains() {
        int[] maxNodes = maxNodesValues.getMatrixColumn(0);
        int[][] repeats = findRepeat(maxNodes, this.matrix.size);
        int[][] chains = new int[repeats.length][];

        if (this.parallel) {
            Arrays.parallelSetAll(repeats, n -> {
                int[] chain = new int[repeats[n][1]];
                chain[0] = repeats[n][0];

                for (int i = 0; i < chain.length - 1; i++)
                    chain[i + 1] = findParent(chain[i]);

                chains[n] = chain;
                return repeats[n];
            });
        } else {
            for (int n = 0; n < repeats.length; n++) {
                int[] chain = new int[repeats[n][1]];
                chain[0] = repeats[n][0];

                for (int i = 0; i < chain.length - 1; i++)
                    chain[i + 1] = findParent(chain[i]);

                chains[n] = chain;
            }
        }

        return chains;
    }

    static int[][] findRepeat(int[] array, int maxNode) {
        ArrayList<int[]> repeats = new ArrayList<>();

        for (int i = 1; i <= maxNode; i++) {
            int count = 0;

            for (int j : array)
                if (i == j) count++;
            if (count > 1) repeats.add(new int[] {i, count});
        }
        int[][] repeatsMain = new int[repeats.size()][];
        for (int i = 0; i < repeats.size(); i++) {
            repeatsMain[i] = repeats.get(i);
        }
        return repeatsMain;
    }

    private int findParent(int value) {
        for (int[] pair: this.edgesList.matrix) {
            if (pair[1] == value)
                return pair[0];
        }
        return -1;
    }

    private Matrix getMaxNodesValues(int[][] matrix) {
        Matrix list = new Matrix(new int[matrix.length][2]);

        if (this.parallel) {
            Arrays.parallelSetAll(list.matrix, i -> {
                list.matrix[i][0] = Math.max(findMaxChild(0, matrix, i), i + 1);
                list.matrix[i][1] = i + 1;
                return list.matrix[i];
            });
        } else {
            for (int i = 0; i < list.matrix.length; i++){
                list.matrix[i][0] = Math.max(findMaxChild(0, matrix, i), i + 1);
                list.matrix[i][1] = i + 1;
            }
        }

        return list;
    }

    private int findMaxChild(int currentMax, int[][] matrix, int child) {
        for (int i = 0; i < matrix[child].length; i++){
            if (matrix[child][i] == 1) {
                currentMax = findMaxChild(Math.max(currentMax, i + 1), matrix, i);
            }
        }
        return currentMax;
    }
}
