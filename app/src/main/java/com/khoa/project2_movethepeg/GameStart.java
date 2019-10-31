package com.khoa.project2_movethepeg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;

public class GameStart extends AppCompatActivity {
    private PegGameModel mPegGame;
    private Button[][] mButton;
    private int mOnColor;
    private int mOffColor;
    private int mSelectedColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);
        mOnColor = ContextCompat.getColor(this, R.color.colorOn);
        mOffColor = ContextCompat.getColor(this, R.color.colorOff);
        mSelectedColor = ContextCompat.getColor(this, R.color.color_empty_button);
        mButton = new Button[PegGameModel.NUM_ROWS][PegGameModel.NUM_COLS];
        GridLayout gridLayout = findViewById(R.id.light_grid);
        int ChildIndex = 0;
        for (int row = 0; row < mPegGame.NUM_ROWS; row++) {
            for (int col = 0; col < mPegGame.NUM_COLS; col++) {
                mButton[row][col] = (Button) gridLayout.getChildAt(ChildIndex);
                ChildIndex++;
            }
        }

        mPegGame = new PegGameModel();
        GameStart();
    }

    private void GameStart(){
        mPegGame.newGame();
        SetButtonColor();
    }

    private void SetButtonColor(){
        for (int row = 0; row < mPegGame.NUM_ROWS; row++) {
            for (int col = 0; col < mPegGame.NUM_COLS; col++) {
                if (mPegGame.IsPegShow(row, col)) {
                    mButton[row][col].setBackgroundColor(mOnColor);
                } else {
                    mButton[row][col].setBackgroundColor(mOffColor);
                }
            }
        }
    }



}
