package edu.dnaomi94utexas.doughnutracetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Instructions extends AppCompatActivity {

    private Button mMainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        setTitle("Instructions");

        mMainMenu = (Button) findViewById(R.id.main_menu);
        mMainMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Instructions.this, MainActivity.class);
                startActivity(intent);
            }

        });
    }
}
