package com.example.chenshiyuan.geoquiz;

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    private int mScore;//得分
    private boolean mIsCheater;//是否作弊

    public Question(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mScore=0;
        mIsCheater=false;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int mTextResId) {
        this.mTextResId = mTextResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean mAnswerTrue) {
        this.mAnswerTrue = mAnswerTrue;
    }
    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }

    public boolean isCheater() {
        return mIsCheater;
    }

    public void setCheater(boolean cheater) {
        mIsCheater = cheater;
    }
}