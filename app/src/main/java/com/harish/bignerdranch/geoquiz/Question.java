package com.harish.bignerdranch.geoquiz;

/**
 * Created by DigitalStorm on 7/15/2017.
 */

public class Question {
    private int mTextResId;
    private boolean mAnswer;

    public Question(int textId, boolean answer){
        mTextResId = textId;
        mAnswer = answer;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public boolean isAnswer() {
        return mAnswer;
    }

    public void setTextResId(int textId) {
        mTextResId = textId;
    }

    public void setAnswer(boolean answer) {
        mAnswer = answer;
    }
}
