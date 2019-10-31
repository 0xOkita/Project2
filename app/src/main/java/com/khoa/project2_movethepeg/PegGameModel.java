package com.khoa.project2_movethepeg;

import java.util.ArrayList;
import java.util.Random;


public class PegGameModel {
    public static final int NUM_ROWS = 5;
    public static final int NUM_COLS = 5;


    private int lastRow = 0;
    private int lastCol = 0;
    private boolean[][] mPegs;
    private int countPegLeft;
    private ArrayList<Boolean> list;

    public PegGameModel() {
        mPegs = new boolean[NUM_ROWS][NUM_COLS];
    }

    public void newGame() {
        Random randomNumGenerator = new Random();
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                mPegs[row][col] = true;
            }
        }
        int Row = randomNumGenerator.nextInt(5);
        int Col = randomNumGenerator.nextInt(5);
        mPegs[Row][Col] = false;
       // mPegs[0][1] = true;
       // mPegs[1][1] = true;
        //mPegs[0][3] = true;
        //mPegs[1][2] = true;
       // //mPegs[4][4] = true;
    }

    // Return peg value: True or False / Show or Hide
    public boolean IsPegShow(int row, int col) {
        return mPegs[row][col];
    }

    public void OffPeg(int row, int col) {
        mPegs[row][col] = !mPegs[row][col];
    }

    public void setLastSelectedPeg(int row, int col) {
        lastRow = row;
        lastCol = col;
    }

    public boolean ValidMove(int row, int col) {
        if (lastCol < col) { // Move peg from left to right
            if (IsPegShow(row, col - 1) == true && IsPegShow(row, col) == false) {
                return true;
            } else {
                return false;
            }
        } else if (lastCol > col) // Move peg from right to left
        {
            if (IsPegShow(row, col + 1) == true && IsPegShow(row, col) == false) {
                return true;
            } else {
                return false;
            }
        } else if (lastRow < row) { // Move peg UP-Down
            if (IsPegShow(row - 1, col) == true && IsPegShow(row, col) == false) {
                return true;
            } else {
                return false;
            }
        } else if (lastRow > row) { //Move peg Down-UP
            if (IsPegShow(row + 1, col) == true && IsPegShow(row, col) == false) {
                return true;
            } else {
                return false;
            }
        }
        return false;

    }

    public void ClearPeg(int row, int col, String key) {
        if (key == "left") {
            OffPeg(row, col - 1);
            OffPeg(row, col - 2);
            OffPeg(row, col);

        }
        if (key == "right") {
            OffPeg(row, col);
            OffPeg(row, col + 1);
            OffPeg(row, col + 2);
        }

        if (key == "up") {
            OffPeg(row - 1, col);
            OffPeg(row - 2, col);
            OffPeg(row, col);
        }

        if (key == "down") {
            OffPeg(row + 1, col);
            OffPeg(row + 2, col);
            OffPeg(row, col);
        }

    }

    public boolean CheckValidPegMove(boolean Move1, boolean Move2) {
        if (Move1 || Move2) { //
            return true;
        } else {
            return false;
        }
    }

    public boolean CheckValidPegMove(boolean Move1, boolean Move2, boolean Move3) {
        if (Move1 || Move2 || Move3) { //
            return true;
        } else {
            return false;
        }
    }

    public boolean CheckValidPegMove(boolean Move1, boolean Move2, boolean Move3, boolean Move4) {
        if (Move1 || Move2 || Move3 || Move4) { //
            return true;
        } else {
            return false;
        }
    }

    public boolean CanPegMove(int row, int col) {
        boolean toRight2;
        boolean toDown2;
        boolean toRight;
        boolean toDown;

        if (row < NUM_COLS - 2) {
            toDown = mPegs[row+1][col];

        } else {
            toDown = false;
        }
        if (col < NUM_COLS - 2) {
            toRight = mPegs[row][col + 1];

        } else {
            toRight = false;
        }

        if (col < NUM_COLS - 2) {
            toRight2 = mPegs[row][col + 2];

        } else {
            toRight2 = false;
        }

        if (row < NUM_ROWS - 2) {
            toDown2 = mPegs[row+2][col];

        } else {
            toDown2 = false;
        }

        boolean toLeft = mPegs[row][Math.abs(col - 1)];
        boolean toUp = mPegs[Math.abs(row - 1)][col];
        boolean toLeft2 = mPegs[row][Math.abs(col - 2)];
        boolean toUp2 = mPegs[Math.abs(row - 2)][col];
        boolean DownMove = (toDown == true && toDown2 == false);
        boolean RightMove = (toRight == true && toRight2 == false);
        boolean LeftMove = (toLeft == true && toLeft2 == false);
        boolean UpMove = (toUp == true && toUp2 == false);


        // start here
        // Check 4 corner first
        // Check Horizontal
        if (IsPegShow(row, col)) {
            if (row == 0) { // Check row 0
                if (col < 2) { // check col 0 1
                    if (CheckValidPegMove(RightMove, DownMove)) {
                        return true;
                    }
                }
                if (col >= NUM_COLS - 2) { // check 2 last block if can move
                    if (CheckValidPegMove(LeftMove, DownMove)) {
                        return true;
                    }
                }
                if (1 < col && col < NUM_COLS - 2) {
                    if (CheckValidPegMove(LeftMove, RightMove, DownMove)) {
                        return true;
                    }
                }


            }
            //Check last row
            if (row >= NUM_ROWS - 2) {

                if (col < 2) { // check col 0 and 1
                    if (CheckValidPegMove(RightMove, UpMove)) {
                        return true;
                    }
                }
                if (col >= NUM_COLS - 2) { // check 2 last block if can move
                    if (CheckValidPegMove(LeftMove, UpMove)) {
                        return true;
                    }
                }
                if (1 < col && col < NUM_COLS - 2) {
                    if (CheckValidPegMove(LeftMove, RightMove, UpMove)) {
                        return true;
                    }
                }

            }

            // Check Vertical
            // Start with 0 then last col
            if (col == 0) {
                if (row < 2) {
                    if (CheckValidPegMove(RightMove, DownMove)) {
                        return true;
                    }
                }
                if (2 < row && row < NUM_ROWS - 2) {
                    if (CheckValidPegMove(RightMove, UpMove, DownMove)) {
                        return true;
                    }
                }
                if (row >= NUM_ROWS - 2) {
                    if (CheckValidPegMove(UpMove, RightMove)) {
                        return true;
                    }
                }
            }
            //Lats col
            if (col >= NUM_COLS - 2) {
                if (row < 2) {
                    if (CheckValidPegMove(LeftMove, DownMove)) {
                        return true;
                    }
                }
                if (2 < row && row < NUM_ROWS - 2) {
                    if (CheckValidPegMove(LeftMove, UpMove, DownMove)) {
                        return true;
                    }
                }
                if (row >= NUM_ROWS - 2) {
                    if (CheckValidPegMove(UpMove, LeftMove)) {
                        return true;
                    }
                }
            }
            if(1< row && row < NUM_ROWS-2 && 1< col && col < NUM_COLS){
                if(CheckValidPegMove(UpMove,RightMove,DownMove,LeftMove)){
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public boolean isGameLost() {
        list = new ArrayList<Boolean>();
        boolean NoMoreMove= true;
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                if(mPegs[row][col]){
                    list.add(CanPegMove(row, col));
                }

            }
        }
        for (boolean value : list) {
            if (value == true) {
                NoMoreMove = false;
            }

        }

        if(NoMoreMove){
            return true;
        }
        return false;


    }

    public boolean isGameWon() {
        countPegLeft = 0;
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                if (mPegs[row][col] == true) {
                    countPegLeft += 1;
                }
            }
        }
        if (countPegLeft == 1) {
            return true;
        } else {
            return false;
        }
    }
}








