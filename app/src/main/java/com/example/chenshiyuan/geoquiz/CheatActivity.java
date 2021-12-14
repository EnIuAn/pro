package com.example.chenshiyuan.geoquiz;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

public class CheatActivity extends AppCompatActivity {
    private static final String EXTRA_ANSWER_IS_TRUE = "GeoQuiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "GeoQuiz.answer_shown";
    private static final String IS_SHOWN_ANSWER = "is_shown_answer";
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;
    private boolean mAnswerIsTrue;
    private boolean mIsShownAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        if(savedInstanceState != null){
            mIsShownAnswer = savedInstanceState.getBoolean(IS_SHOWN_ANSWER,false);
            mAnswerIsTrue = savedInstanceState.getBoolean(EXTRA_ANSWER_IS_TRUE,false);
        } else
        {
            mIsShownAnswer = false;
        }
        setAnswerShownResult(mIsShownAnswer);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);
        mAnswerTextView = findViewById(R.id.answer_text_view);
        mShowAnswerButton = findViewById(R.id.show_answer_button);
        if (mIsShownAnswer)
        {
            showResult();
        }

        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResult();
                mIsShownAnswer = true;
                setAnswerShownResult(mIsShownAnswer);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(IS_SHOWN_ANSWER,mIsShownAnswer);
        //保留传过来的答案
        savedInstanceState.putBoolean(EXTRA_ANSWER_IS_TRUE,mAnswerIsTrue);
    }
    public static Intent newIntent(Context packageContext, boolean answerIsTrue)
    {
        Intent intent = new Intent(packageContext,CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue);
        return intent;
    }

    private void setAnswerShownResult(boolean isAnswerShown)
    {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown);
        setResult(RESULT_OK,data);
    }
    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);
    }
    private void showResult(){
        if(mAnswerIsTrue){
            mAnswerTextView.setText(R.string.true_button);
        } else
        {
            mAnswerTextView.setText(R.string.false_button);
        }
    }
}