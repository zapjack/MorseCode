package com.kiowok.morsecode;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends Activity {
    Button dot, dash, done;
    TextView ques, status;
    EditText answer;
    Model model = new Model();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dot = findViewById(R.id.dot);
        dash = findViewById(R.id.dash);
        done = findViewById(R.id.done);
        ques = findViewById(R.id.ques);
        status = findViewById(R.id.status);
        answer = findViewById(R.id.answer);

        model.start(1);
        refresh();
    }

    private void refresh() {
        ques.setText("What is the code for: " + model.getQuestionText());

        String s = (model.isLastQuestionCorrect() ? "Correct!!!" : "No...");

        if (model.justStarting())
            status.setText("Starting...");
        else if (model.done())
            status.setText(String.format("Last guess: %s\n%d of %d", s, model.getCorrect(), model.getTotal()));
        else
            status.setText(String.format("Last guess: %s", s));
    }

    public void onDot(View view) {
        model.addToGuessText(".");
    }

    public void onDash(View view) {
        model.addToGuessText("-");
    }

    public void onDone(View view) {
        model.eval();
        refresh();
        /*
        if (current >= Data.data.size()) {
            String s = status.getText().toString();
            s += String.format("\n%d of %d", correct, current);
            status.setText(s);
            startGame();
        }
        else {
            Data d = Data.data.get(current);
            answerText = d.getMorse();
            questionText = d.getAlpha();
            ques.setText("What is the code for: " + questionText);
        }
        */
    }

    public void onLevel1(View view) {
        model.start(1);
        refresh();
    }

    public void onLevel2(View view) {
        model.start(2);
        refresh();
    }
}
