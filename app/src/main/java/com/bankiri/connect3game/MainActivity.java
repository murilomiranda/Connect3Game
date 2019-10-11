package com.bankiri.connect3game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // 0 = yellow, 1 = red and 2 = empty
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winPositions ={{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};
    int actualPlayer = 0;
    int countTurns = 0;
    boolean gameActive = true;

    public void playAgain(View view) {
        gameState = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2};
        actualPlayer = 0;
        gameActive = true;
        countTurns = 0;

        TextView winnerView = findViewById(R.id.winnerTextView);
        Button playAgain = findViewById(R.id.button);

        winnerView.setVisibility(View.INVISIBLE);
        playAgain.setVisibility(View.INVISIBLE);

        androidx.gridlayout.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);

        for(int i =0; i < gridLayout.getChildCount(); i++) {
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }
    }

    public void dropIn(View view){
        ImageView counter = (ImageView) view;
        int tagged = Integer.parseInt(counter.getTag().toString());
        countTurns++;

        if (gameState[tagged] == 2 && gameActive) {
            gameState[tagged] = actualPlayer;
            counter.setTranslationY(-1500);

            if (actualPlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                actualPlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                actualPlayer = 0;
            }

            counter.animate().translationYBy(1500).setDuration(300);

            for (int[] winPosition : winPositions) {
                if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                        gameState[winPosition[1]] == gameState[winPosition[2]] &&
                        gameState[winPosition[0]] != 2) {

                    // Someone won. actualPlayer is equal to 0, then red won
                    gameActive = false;

                    String winner;
                    if(actualPlayer == 0){
                        winner = "Red";
                    } else {
                        winner = "Yellow";
                    }

                    TextView winnerView = findViewById(R.id.winnerTextView);
                    Button playAgain = findViewById(R.id.button);

                    winnerView.setText(String.format("%s won!", winner));
                    winnerView.setVisibility(View.VISIBLE);
                    playAgain.setVisibility(View.VISIBLE);

                    //Toast.makeText(this, winner + " won!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if(countTurns == 9){
            TextView winnerView = findViewById(R.id.winnerTextView);
            Button playAgain = findViewById(R.id.button);

            winnerView.setText("Draw!");
            winnerView.setVisibility(View.VISIBLE);
            playAgain.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
