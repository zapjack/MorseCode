package com.kiowok.morsecode;

import java.util.ArrayList;
import java.util.Collections;

public class Model {
    private int level = 1;
    private KState state = KState.NONE;
    private Guess guessing = Guess.MORSE;
    private int current, correct, total;
    private Data dCurrent = null;
    private String answerText = "", questionText = "";
    private String guessText = "";
    private boolean lastQuestionCorrect = false;
    private String lastAnswerText = "", lastQuestionText = "", lastGuessText = "";

    public String getLastAnswerText() {
        return lastAnswerText;
    }

    public String getLastQuestionText() {
        return lastQuestionText;
    }

    public String getLastGuessText() {
        return lastGuessText;
    }

    /**
     * Start game at the indicated level of difficulty.
     *
     * @param level Level of difficulty
     */
    public void start(int level) {
        this.level = level;
        state = KState.PLAYING;
        current = 0;
        correct = 0;

        if (level == 1)
            Data.buildData(Data.level1, (Data.data = new ArrayList<>()));
        else if (level == 2)
            Data.buildData(Data.level2, (Data.data = new ArrayList<>()));
        else if (level == 3)
            Data.buildData(Data.level3, (Data.data = new ArrayList<>()));
        else if (level == 4)
            Data.buildData(Data.level4, (Data.data = new ArrayList<>()));
        else
            Data.buildData(Data.alphas, (Data.data = new ArrayList<>()));

        Collections.shuffle(Data.data);

        dCurrent = Data.data.get(current);
        answerText = isGuessingMorse() ? dCurrent.getMorse() : dCurrent.getAlpha();
        questionText = isGuessingMorse() ? dCurrent.getAlpha() : dCurrent.getMorse();
        total = Data.data.size();
        guessText = "";
    }

    public int getLevel() {
        return level;
    }

    public boolean eval(String guessIn) {
        guessText = Model.normalize(guessIn);

        if (state == KState.DONE) return false;

        answerText = Model.normalize(answerText);
        questionText = Model.normalize(questionText);

        // answerText = answerText.replaceAll("\\s+", "");
        //questionText = questionText.replaceAll("\\s+", "");

        lastAnswerText = answerText;
        lastGuessText = guessText;
        lastQuestionText = questionText;

        if (guessText.equalsIgnoreCase(answerText)) {
            correct++;
            lastQuestionCorrect = true;
        } else
            lastQuestionCorrect = false;

        current++;
        if (current >= total) {
            state = KState.DONE;
        } else {
            dCurrent = Data.data.get(current);

            answerText = isGuessingMorse() ? dCurrent.getMorse() : dCurrent.getAlpha();
            questionText = isGuessingMorse() ? dCurrent.getAlpha() : dCurrent.getMorse();

            guessText = "";
        }

        return lastQuestionCorrect;
    }

    public void setGuessingMorse(boolean gsMorse) {
        if (gsMorse)
            guessing = Guess.MORSE;
        else
            guessing = Guess.ALPHA;
    }

    public boolean isGuessingMorse() {
        return guessing == Guess.MORSE;
    }

    public void setGuessText(String guessText) {
        this.guessText = guessText;
    }

    public void addToGuessText(String moreGuess) {
        this.guessText += moreGuess;
    }

    public String getAnswerText() {
        return answerText;
    }

    public String getQuestionText() {
        return questionText;
    }

    public boolean justStarting() {
        return current == 0;
    }

    public boolean done() {
        return state == KState.DONE;
    }

    public boolean isLastQuestionCorrect() {
        return lastQuestionCorrect;
    }

    public int getCorrect() {
        return correct;
    }

    public int getTotal() {
        return total;
    }

    public static String normalize(String in) {
        return in.trim().toLowerCase().replaceAll("\\s+", "");
    }
}
