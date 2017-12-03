package edu.dnaomi94utexas.doughnutracetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HighScores extends AppCompatActivity {

    private Button mMainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        setTitle("Scores");

        mMainMenu = (Button) findViewById(R.id.main_menu);
        mMainMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HighScores.this, MainActivity.class);
                startActivity(intent);
                // getFragmentManager().popBackStack();
            }

        });
    }

}
