package edu.dnaomi94utexas.doughnutracetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button mHighScoresButton;
    private Button mInstructionsButton;
    private Button mSettingsButton;
    private Button mNewGameButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Doughnut Race");

        mHighScoresButton = (Button) findViewById(R.id.scores);
        mHighScoresButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HighScores.class);
                startActivity(intent);
            }

        });

        mInstructionsButton = (Button) findViewById(R.id.instructions);
        mInstructionsButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Instructions.class);
                startActivity(intent);
            }

        });

        mSettingsButton = (Button) findViewById(R.id.settings);
        mSettingsButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent

                );
            }

        });

        mNewGameButton = (Button) findViewById(R.id.new_game);
        mNewGameButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlayGame.class);
                startActivity(intent);
            }
        });
    }
}
