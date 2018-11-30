package com.kiowok.morsecode;

import java.util.Collections;

public class Model {
    private KState state = KState.NONE;
    private int current, correct, total;
    private Data dCurrent = null;
    private String answerText = "", questionText = "";
    private String guessText = "";
    private boolean lastQuestionCorrect = false;

    private String lastAnswerText = "", lastQuestionText = "", lastGuessText = "";



    /**
     * Start game at the indicated level of difficulty.
     *
     * @param level Level of difficulty
     */
    public void start(int level) {
        state = KState.PLAYING;
        current = 0;
        correct = 0;

        if (level == 1)
            Data.buildData(Data.level1);
        else
            Data.buildData(Data.level2);

        Collections.shuffle(Data.data);

        dCurrent = Data.data.get(current);
        answerText = dCurrent.getMorse();
        questionText = dCurrent.getAlpha();
        total = Data.data.size();
        guessText = "";
    }

    public boolean eval() {
        if (state == KState.DONE) return false;

        answerText = answerText.replaceAll("\\s+", "");
        lastAnswerText = answerText;
        lastGuessText = guessText;
        lastQuestionText = questionText;

        if (guessText.equalsIgnoreCase(answerText)) {
            correct++;
            lastQuestionCorrect = true;
        }
        else
            lastQuestionCorrect = false;

        current++;
        if (current >= total)
            state = KState.DONE;
        else {
            dCurrent = Data.data.get(current);
            answerText = dCurrent.getMorse();
            questionText = dCurrent.getAlpha();
            guessText = "";
        }

        return lastQuestionCorrect;
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
}
