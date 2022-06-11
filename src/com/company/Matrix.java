package com.company;

import java.util.Arrays;
import java.util.Comparator;

public class Matrix {
    public final int[][] matrix;
    public final int size;

    public Matrix(int[][] matrix) {
        this.matrix = matrix;
        this.size = matrix.length;
    }

    public Matrix(int[][] edgesList, int size, boolean adjacency) {
        this.matrix = adjacency ? createAdjacencyMatrix(size, edgesList) : createIncidenceMatrix(size, edgesList);
        this.size = size;
    }

    private static int[][] initMatrix(int sizeMatrix){
        int[][] matrix = new int[sizeMatrix][sizeMatrix];
        for (int i = 0; i < sizeMatrix; i++)
            for (int j = 0; j < sizeMatrix; j++)
                matrix[i][j] = 0;

        return matrix;
    }

    private static int[][] createIncidenceMatrix(int sizeMatrix, int[][] edgesList) {
        int[][] matrix = initMatrix(sizeMatrix);

        for (int[] ints : edgesList) {
            matrix[((ints[0]) - 1)][((ints[1]) - 1)] = 1;
        }

        return matrix;
    }

    private static int[][] createAdjacencyMatrix(int sizeMatrix, int[][] edgesList) {
        int[][] matrix = initMatrix(sizeMatrix);

        for (int[] ints : edgesList) {
            matrix[((ints[0]) - 1)][((ints[1]) - 1)] = 1;
            matrix[((ints[1]) - 1)][((ints[0]) - 1)] = 1;
        }

        return matrix;
    }

    public void showMatrix() {
        for (int[] ints : this.matrix) {
            for (int anInt : ints) {
                System.out.print(anInt + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int[] getMatrixColumn(int columnIndex) {
        int[] column = new int[this.matrix.length];

        for (int i = 0; i < column.length; i++)
            column[i] = matrix[i][columnIndex];


        return column;
    }

    public void sortByColumn(int columnIndex, Boolean parallel) {
        if (parallel) {
            Arrays.parallelSort(this.matrix, Comparator.comparingDouble(o -> o[columnIndex])); // todo Parallel
        } else {
            Arrays.sort(this.matrix, Comparator.comparingDouble(o -> o[columnIndex]));
        }
    }

}
