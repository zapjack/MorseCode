package com.kiowok.morsecode;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    Button dot, dash, done;
    TextView ques, status;
    EditText guess;
    Model model = new Model();
    RadioButton guessMorse;
    RadioGroup radioGroup;

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
        guessMorse = findViewById(R.id.guessMorse);
        radioGroup = findViewById(R.id.radioGroup);

        model.start(1);
        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_show:
                String s = Data.dataAll.toString();
                //s = s.replaceAll("\\\[", "");
                s = s.substring(1, s.length() - 1);
                //s = s.replaceAll("\\\]", "");
                s = s.replaceAll(",", "\n");
                Toast.makeText(this, s, Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void refresh() {
        ques.setText("" + model.getQuestionText());

        String s = (model.isLastQuestionCorrect() ? "Correct!!!" : "No...");

        if (model.justStarting()) {
            guess.setText("");
            status.setText("Starting...");
        }
        else if (model.done())
            status.setText(String.format("Last guess: %s\n%d of %d", s, model.getCorrect(), model.getTotal()));
        else {
            if (model.isLastQuestionCorrect())
                status.setText("YES");
            else {
                status.setText(String.format("NO>> asked: %s yours: %s correct: %s",
                    model.getLastQuestionText(), model.getLastGuessText(), model.getLastAnswerText()));
            }
        }
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
        model.eval(Model.normalize(guess.getText().toString()));
        guess.setText("");
        refresh();
    }

    public void onLevel(View view) {
        Button b = (Button) view;
        String s = b.getText().toString().trim();
        model.start(Integer.parseInt(s));
        refresh();
    }

    public void onGuessing(View view) {
        model.setGuessingMorse(guessMorse.isChecked());
        model.start(model.getLevel());
        refresh();
    }
}
