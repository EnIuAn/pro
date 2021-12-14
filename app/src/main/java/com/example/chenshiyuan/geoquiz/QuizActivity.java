package com.example.chenshiyuan.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_CHEAT_RESULT = "cheat_result";
    private static final String KEY_ANSWER_RESULT = "answer_result";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mLastButton;
    private Button mCheatButton;
    private boolean mIsCheater;
    private Button mScoreButton;
    private TextView mScoreTextView;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_peking, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
            new Question(R.string.question_yangtze, true)
    };
    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Log.d(TAG, "调用了onCreate方法！");

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            boolean[] cheatResult = savedInstanceState.getBooleanArray(KEY_CHEAT_RESULT);
            int[] answerResult = savedInstanceState.getIntArray(KEY_ANSWER_RESULT);
            try {
                for (int i = 0; i < mQuestionBank.length; i++) {
                    mQuestionBank[i].setScore(answerResult[i]);
                    mQuestionBank[i].setCheater(cheatResult[i]);
                }
            } catch (Exception ex) {
                Log.e(TAG, "恢复答题数据有误：", ex);
            }
        }
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                checkQuestion();
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                checkQuestion();
            }
        });
        updateQuestion();
        checkQuestion();

        final int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                checkQuestion();
                updateQuestion();
            }
        });
        mLastButton = (Button) findViewById(R.id.last_button);
        mLastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = mCurrentIndex - 1;
                if (mCurrentIndex < 0) {
                    mCurrentIndex = mQuestionBank.length - 1;
                }
                checkQuestion();
                updateQuestion();
            }
        });

        mCheatButton = findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this,
                        answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        mScoreTextView = findViewById(R.id.score_text_view);
        mScoreButton = findViewById(R.id.score_button);
        mScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalCorrectScore = 0;
                int totalInCorrectScore = 0;
                for (int i = 0; i < mQuestionBank.length; i++) {
                    if (mQuestionBank[i].getScore() == -1) {
                        totalInCorrectScore = totalInCorrectScore + 1;
                    }
                    if (mQuestionBank[i].getScore() == 1) {
                        totalCorrectScore = totalCorrectScore + 1;
                    }
                    mScoreTextView.setText("你答对了：" + totalCorrectScore +
                            "题；答错了：" + totalInCorrectScore + "题。");
                }

            }
        });

    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstaceState) {
        super.onSaveInstanceState(savedInstaceState);
        savedInstaceState.putInt(KEY_INDEX, mCurrentIndex);
        boolean[] cheatResult = new boolean[mQuestionBank.length];
        int[] answerResult = new int[mQuestionBank.length];
        for (int i = 0; i < mQuestionBank.length; i++) {
            cheatResult[i] = mQuestionBank[i].isCheater();
            answerResult[i] = mQuestionBank[i].getScore();
        }
        savedInstaceState.putBooleanArray(KEY_CHEAT_RESULT, cheatResult);
        savedInstaceState.putIntArray(KEY_ANSWER_RESULT, answerResult);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
            mQuestionBank[mCurrentIndex].setCheater(mIsCheater);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "调用了onStart方法！");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "调用了onResume方法！");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "调用了onPause方法！");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "调用了onStop方法！");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "调用了onDestroy方法！");
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;
        mIsCheater = mQuestionBank[mCurrentIndex].isCheater();
        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
                mQuestionBank[mCurrentIndex].setScore(1);
            } else {
                messageResId = R.string.incorrect_toast;
                mQuestionBank[mCurrentIndex].setScore(-1);
            }
            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
        }
    }
        private void checkQuestion()
        {
            mIsCheater = mQuestionBank[mCurrentIndex].isCheater();
            if (mQuestionBank[mCurrentIndex].getScore() == 0){
                mTrueButton.setEnabled(true);
                mFalseButton.setEnabled(true);
            } else{
                mTrueButton.setEnabled(false);
                mFalseButton.setEnabled(false);
            }
    }

}





