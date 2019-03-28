package com.example.android.puzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PuzzleManager {

    public static final int DOWN = 0;
    public static final int UP = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    private int n;
    private int m;
    private int[][] puzzle;

    public PuzzleManager(int n, int m) {
        this.n = n;
        this.m = m;
        puzzle = new int[n][m];
        fillPuzzle();
    }

    public PuzzleManager(int n, int m, long memory) {
            this.n = n;
        this.m = m;
        puzzle = new int[n][m];
        loadFromMemory(memory);
    }

    private void fillPuzzle() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                puzzle[i][j] = i * n + j;
            }
        }
    }

    public void shufflePuzzle() {
        List<Integer> random = new ArrayList<>(n * m);
        for (int i = 0; i < n * m; i++) {
            random.add(i);
        }
        Collections.shuffle(random);
        int[][] newPuzzle = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int iOrigin = random.get(i * n + j) / n;
                int jOrigin = random.get(i * n + j) % n;
                newPuzzle[i][j] = puzzle[iOrigin][jOrigin];
            }
        }
        puzzle = newPuzzle;
    }

    public int getPuzzleElementId(int point) {
        return puzzle[point / n][point % n];
    }

    public int swipe(int id, int direction) {
        int i = id / n;
        int j = id % n;
        int iNew;
        int jNew;
        switch (direction){
            case DOWN:
                iNew = i + 1;
                jNew = j;
                break;
            case UP:
                iNew = i - 1;
                jNew = j;
                break;
            case LEFT:
                iNew = i;
                jNew = j - 1;
                break;
            default:
                iNew = i;
                jNew = j + 1;
                break;
        }
        if (iNew >= 0 && iNew < n && j >= 0 && j < m && puzzle[iNew][jNew] == n * m - 1) {
            int temp = puzzle[i][j];
            puzzle[i][j] = puzzle[iNew][jNew];
            puzzle[iNew][jNew] = temp;
            return iNew * n + jNew;
        }
        else {
            return -1;
        }
    }

    /*
    works only for 4x4 puzzle
     */
    public long memorize() {
        long res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int id = puzzle[i][j];
                res = (res << n);
                res += id;
            }
        }
        return res;
    }

    /*
    works only for 4x4 puzzle
     */
    private void loadFromMemory(long memory) {
        int mask = 0b1111;
        for (int i = n - 1; i >=0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                int id = (int) (memory & mask);
                memory = memory >> n;
                puzzle[i][j] = id;
            }
        }
    }
}
