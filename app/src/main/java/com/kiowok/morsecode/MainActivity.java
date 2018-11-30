package com.kiowok.morsecode;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    Button dot, dash, done;
    TextView ques, status;
    EditText guess;
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
        guess = findViewById(R.id.guess);

        model.start(1);
        refresh();
    }

    private void refresh() {
        ques.setText("What is the code for: " + model.getQuestionText());

        String s = (model.isLastQuestionCorrect() ? "Correct!!!" : "No...");

        if (model.justStarting()) {
            guess.setText("");
            status.setText("Starting...");
        }
        else if (model.done())
            status.setText(String.format("Last guess: %s\n%d of %d", s, model.getCorrect(), model.getTotal()));
        else
            status.setText(String.format("Last guess: %s", s));
    }

    public void onDot(View view) {
        String s = guess.getText().toString();
        s += ".";
        guess.setText(s);
        model.addToGuessText(".");
    }

    public void onDash(View view) {
        String s = guess.getText().toString();
        s += "-";
        guess.setText(s);
        model.addToGuessText("-");
    }

    public void onDone(View view) {
        guess.setText("");
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
