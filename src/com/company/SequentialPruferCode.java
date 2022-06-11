package com.company;

import java.util.ArrayList;

public class SequentialPruferCode {
    public int[] code;

    public SequentialPruferCode(int[][] edgesList, int maxNode) {
        long startTime = System.currentTimeMillis();
        this.code = buildCode(edgesList, maxNode);
        System.out.print("Час виконання у мілісекундах -> " + (System.currentTimeMillis() - startTime) + "\n");
    }

    public void printCode(){
        System.out.print("Prufer code:" + "\t");
        for (int i : this.code) {
            System.out.print(i + "\t");
        }
        System.out.print("\n");
    }

    static int[] buildCode(int[][] edgesList, int maxNode) {
        int[] code = new int[maxNode - 2];

        for (int i = 0; i < code.length; i++) {
            int[] searchLeaflet = SearchInDepth(edgesList, maxNode);
            int min = MinInList(searchLeaflet);
            for (int j = 0; j < edgesList.length; j++)
            {
                if (min == edgesList[j][0]) {
                    code[i] = edgesList[j][1];
                    edgesList = remove(edgesList, j);
                    break;
                }
                if (min == edgesList[j][1]) {
                    code[i] = edgesList[j][0];
                    edgesList = remove(edgesList, j);
                    break;
                }
            }
        }
        return code;
    }

    static int[][] remove(int[][] list, int index){
        int[][] copyArray = new int[list.length - 1][2];

        System.arraycopy(list, 0, copyArray, 0, index);

        System.arraycopy(list, index + 1, copyArray, index, list.length - index - 1);

        return copyArray;
    }

    static int[] SearchInDepth(int[][] edgesList, int maxNode){
        int start = MaxInDuoList(edgesList);
        Matrix matrix = new Matrix(edgesList, maxNode, true);

        ArrayList<Integer> result = new ArrayList<Integer>();
        ArrayList<Integer> turn = new ArrayList<Integer>();
        ArrayList<Integer> visited = new ArrayList<Integer>();
        int varCheck = 0;
        turn.add(start);
        visited.add(start);
        while (turn.size() != 0)
            for (int i = 0; i < maxNode; i++)
                if (i + 1 == start)
                    for (int j = 0; j < maxNode; j++) {
                        if (matrix.matrix[i][j] == 1 && SearchInList(visited, (j + 1))) {
                            varCheck = 0;
                            start = j + 1;
                            turn.add(start);
                            visited.add(start);
                            break;
                        }
                        if (j == maxNode - 1) {
                            if (varCheck == 0)
                                result.add(turn.get(turn.size() - 1));
                            varCheck++;
                            turn.remove(turn.size() - 1);
                            if (turn.size() == 0)
                                break;
                            start = turn.get(turn.size() - 1);
                            break;
                        }
                    }

        int[] resultInArray = new int[result.size()];
        for (int i = 0; i < resultInArray.length; i++) {
            resultInArray[i] = result.get(i);
        }

        return resultInArray;
    }

    static Boolean SearchInList(ArrayList<Integer> list, int n) {
        for (int item : list)
            if (item == n)
                return false;

        return true;
    }

    static int MaxInDuoList(int[][] duoList) {
        int max = duoList[0][0];
        for (int[] item : duoList) {
            if (max < item[0])
                max = item[0];
            if (max < item[1])
                max = item[1];
        }
        return max;
    }

    static int MinInList(int[] list) {
        int min = list[0];
        for (int i = 0; i < list.length; i++)
            if (min > list[i])
                min = list[i];
        return min;
    }
}
