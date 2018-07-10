package com.example.knightcube.rockpaperscissors;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button resetBtn;
    private Button rockBtn;
    private Button scissorBtn;
    private Button paperBtn;
    private Button submitButton;
    private ImageView opponentInputImageView;
    private TextView opponentInputTextView;
    private TextView userInputTextView;
    private TextView resultTextView;
    private static final int GAME_STATE_LOSE = 0;
    private static final int GAME_STATE_DRAW = 1;
    private static final int GAME_STATE_WIN = 2;
    private int[] images = {R.drawable.rock,R.drawable.paper,R.drawable.scissor};
    private String TAG="MainActivity";
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        score = 0;
        rockBtn = findViewById(R.id.rock_btn);
        scissorBtn = findViewById(R.id.scissors_btn);
        paperBtn = findViewById(R.id.paper_btn);
        resetBtn = findViewById(R.id.reset_btn);
        opponentInputImageView = findViewById(R.id.opponent_img);
        opponentInputTextView = findViewById(R.id.opponent_played_txt);
        userInputTextView = findViewById(R.id.user_played_txt);
        resultTextView = findViewById(R.id.result_txt);
        submitButton = findViewById(R.id.submit_btn);
        rockBtn.setOnClickListener(this);
        scissorBtn.setOnClickListener(this);
        paperBtn.setOnClickListener(this);
        resetBtn.setOnClickListener(this);
        submitButton.setOnClickListener(this);

    }

    private int generateRandomInputForOpponent() {
        //0 means rock
        //1 means paper
        //2 means scissor
        Random random = new Random();
        int x = random.nextInt(3);
        opponentInputImageView.setImageResource(images[x]);
        if(x==0){
            opponentInputTextView.setText("Opponent Played:Rock");
        }else if(x==1){
            opponentInputTextView.setText("Opponent Played:Paper");
        }else if(x==2){
            opponentInputTextView.setText("Opponent Played:Scissor");
        }
        return x;
    }

    public void onClick(View v) {
        int id = v.getId();
        Log.i(TAG, "onClick:called ");
        switch (id) {
            case R.id.rock_btn:
                Log.i(TAG, "onClick:called Rock ");
                int userInput = 0;
                int opponentInput = generateRandomInputForOpponent();
                userInputTextView.setText("You played: Rock");
                disableOtherBtns();
                checkResults(userInput, opponentInput);
                break;
            case R.id.paper_btn:
                Log.i(TAG, "onClick:called Paper ");
                userInput = 1;
                userInputTextView.setText("You played: Paper");
                opponentInput = generateRandomInputForOpponent();
                disableOtherBtns();
                checkResults(userInput, opponentInput);
                break;
            case R.id.scissors_btn:
                Log.i(TAG, "onClick:called Scissor ");
                userInput = 2;
                userInputTextView.setText("You played: Scissor");
                opponentInput = generateRandomInputForOpponent();
                disableOtherBtns();
                checkResults(userInput, opponentInput);
                break;
            case R.id.reset_btn:
                Log.i(TAG, "onClick:called Reset ");
                enableBtns();
                break;
            case R.id.submit_btn:
                openDetailsActitity();

        }
    }

    private void openDetailsActitity() {
        Log.i(TAG, "openDetailsActitity: "+score);
        Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
        intent.putExtra("user_score",score);
        startActivity(intent);
    }

    private void checkResults(int userInput, int opponentInput) {
        if (userInput == 0 && opponentInput == 0) {     //User chooses Rock,Computer chooses Rock
            showresult(GAME_STATE_DRAW);
        } else if (userInput == 0 && opponentInput == 1) { //User chooses Rock,Computer chooses Paper
            showresult(GAME_STATE_LOSE);
        } else if (userInput == 0 && opponentInput == 2) { //User chooses Rock,Computer chooses Scissors
            showresult(GAME_STATE_WIN);
        } else if (userInput == 1 && opponentInput == 0) { //User chooses Paper,Computer chooses Rock
            showresult(GAME_STATE_WIN);
        } else if (userInput == 1 && opponentInput == 1) { //User chooses Paper,Computer chooses Paper
            showresult(GAME_STATE_DRAW);
        } else if (userInput == 1 && opponentInput == 2) { //User chooses Paper,Computer chooses Scissors
            showresult(GAME_STATE_LOSE);
        } else if (userInput == 2 && opponentInput == 0) {//User chooses Scissors,Computer chooses Rock
            showresult(GAME_STATE_LOSE);
        } else if (userInput == 2 && opponentInput== 1) { //User chooses Scissors,Computer chooses Paper
            showresult(GAME_STATE_WIN);
        } else if (userInput == 2 && opponentInput == 2) { //User chooses Scissors,Computer chooses Scissors
            showresult(GAME_STATE_DRAW);
        }
    }

    private void showresult(int gameState) {
        if(gameState == GAME_STATE_DRAW){
            Toast.makeText(this, "DRAW", Toast.LENGTH_SHORT).show();
            resultTextView.setText("Draw");
        }else if(gameState == GAME_STATE_LOSE){
            Toast.makeText(this, "YOU LOST", Toast.LENGTH_SHORT).show();
            resultTextView.setText("You Lost");

        }else if(gameState == GAME_STATE_WIN){
            Toast.makeText(this, "YOU WON", Toast.LENGTH_SHORT).show();
            resultTextView.setText("You Won");
            score=score+5;
        }
        resultTextView.append("\nYour Score:"+score);
    }

    private void disableOtherBtns() {
        scissorBtn.setEnabled(false);
        paperBtn.setEnabled(false);
        rockBtn.setEnabled(false);
    }

    private void enableBtns() {
        scissorBtn.setEnabled(true);
        paperBtn.setEnabled(true);
        rockBtn.setEnabled(true);
    }

}
