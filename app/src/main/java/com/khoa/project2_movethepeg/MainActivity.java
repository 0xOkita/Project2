package com.khoa.project2_movethepeg;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Build;
import android.view.View;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {

    private PegGameModel mPegGame;
    private ImageButton[][] mButton;
    private ImageButton[][] mSelectedButton;
    private int mOnColor;
    private int mOffColor;
    private int mColor;
    private int mColor2;
    private int mSelectedColor;
    private int lastRow=0;
    private int lastCol=0;
    private String ResIdPegImg = "img2";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);


        mOnColor = ContextCompat.getColor(this, R.color.colorOn);
        mOffColor = ContextCompat.getColor(this, R.color.colorOff);
        mSelectedColor = ContextCompat.getColor(this, R.color.color_empty_button);
        mButton = new ImageButton[PegGameModel.NUM_ROWS+2][PegGameModel.NUM_COLS+2];
        mSelectedButton = new ImageButton[PegGameModel.NUM_ROWS+2][PegGameModel.NUM_COLS+2];

        GridLayout gridLayout = findViewById(R.id.light_grid);
        int ChildIndex = 0;
        for (int row = 0; row < PegGameModel.NUM_ROWS; row++) {
            for (int col = 0; col < PegGameModel.NUM_COLS; col++) {
                mButton[row][col] = (ImageButton) gridLayout.getChildAt(ChildIndex);
                ChildIndex++;
            }
        }

        mPegGame = new PegGameModel();
        GameStart();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void GameStart(){
        mPegGame.newGame();
        setButtonColors();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onLightButtonClick(View view) {
        // Find the row and col selected

        setButtonColors();
        boolean buttonFound = false;
        for (int row = 0; row < PegGameModel.NUM_ROWS && !buttonFound; row++) {
            for (int col = 0; col < PegGameModel.NUM_COLS && !buttonFound; col++) {
                if (view == mButton[row][col]) {
                    //mPegGame.SelectingPeg(row, col);  //true or false
                    //setButtonColors(); // then change color
                    if(mPegGame.IsPegShow(row,col)){
                        mButton[lastRow][lastCol].setBackgroundTintList(ColorStateList.valueOf(mOffColor));
                        mButton[row][col].setBackgroundTintList(ColorStateList.valueOf(mOnColor));
                        //mButton[row][col].setBackgroundColor(mOnColor);
                        setButtonColors();

                    }
                    buttonFound = true;
                    // Save the last selected peg before move
                    mSelectedButton[row][col] = mButton[row][col];

                    // mButton[lastRow][lastCol] is the second move
                    // use with lastrow and lastCol variable because substract cause crash

                    if(mButton[lastRow][lastCol] == mSelectedButton[row][col+2] && mPegGame.ValidMove(row,col)){
                        mPegGame.ClearPeg(row,col,"right");
                        setButtonColors();

                    }
                    if(mButton[lastRow][lastCol+2] == mSelectedButton[row][col] && mPegGame.ValidMove(row,col)){
                        mPegGame.ClearPeg(row,col,"left");
                        setButtonColors();

                    }
                    if(mButton[lastRow+2][lastCol] == mSelectedButton[row][col] && mPegGame.ValidMove(row,col)){
                        mPegGame.ClearPeg(row,col,"up");
                        setButtonColors();

                    }
                    if(mButton[lastRow][lastCol] == mSelectedButton[row+2][col] && mPegGame.ValidMove(row,col)){
                        mPegGame.ClearPeg(row,col,"down");
                        setButtonColors();

                    }

                    // Get the previous move to compare with the new one
                    // Must be assign at last
                    lastRow = row;
                    lastCol = col;
                    mPegGame.setLastSelectedPeg(lastRow,lastCol);
                    checkGameStatus();

                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setButtonColors() {
        // Set all buttons' background color
        for (int row = 0; row < PegGameModel.NUM_ROWS; row++) {
            for (int col = 0; col < PegGameModel.NUM_COLS; col++) {
                if (mPegGame.IsPegShow(row, col)) {
                    mButton[row][col].setBackgroundColor(mOnColor);
                    mButton[row][col].setBackgroundResource(R.drawable.img1);

                } else {
                    mButton[row][col].setBackgroundColor(mColor);

                }
            }
        }
    }

    private void ClearArrayLastSelectedPeg(){
        for (int row = 0; row < PegGameModel.NUM_ROWS; row++) {
            for (int col = 0; col < PegGameModel.NUM_COLS; col++) {
                mSelectedButton[row][col] = null;
            }
        }
        lastCol = 0;
        lastRow = 0;
    }
    private void checkGameStatus(){
        if(mPegGame.isGameWon() == true){
            String win_message = getString(R.string.win_message);
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle(R.string.win_title);
            alertDialog.setMessage(win_message);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
        if(mPegGame.isGameLost() == true){
            String lost_message = getString(R.string.lost_message);
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle(R.string.lost_title);
            alertDialog.setMessage(lost_message);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    public void ChangePegImage(){
        int idPegImg = getResources().getIdentifier(ResIdPegImg, "drawable", getPackageName());
        for (int row = 0; row < PegGameModel.NUM_ROWS; row++) {
            for (int col = 0; col < PegGameModel.NUM_COLS; col++) {
                if (mPegGame.IsPegShow(row, col)) {
                    mButton[row][col].setBackgroundColor(mOnColor);
                    mButton[row][col].setBackgroundResource(idPegImg);

                } else {
                    mButton[row][col].setBackgroundColor(mColor);

                }
            }
        }
    }
}
