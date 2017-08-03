package com.harish.bignerdranch.geoquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class QuizActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private ImageButton mBackButton;
    private TextView mQuestionTextView;
    private int mQuestionIndex = 0;
    private int mNumCorrect = 0;

    private static final String TAG = "QuizActivity";
    private static final String INDEX_KEY = "Index";
    private static final String ANSWERED_KEY = "Answered Array";

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_asia, true),
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true)};

    private boolean[] answeredArray = new boolean[mQuestionBank.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Log.d(TAG, "OnCreate()");

        if (savedInstanceState != null) {
            mQuestionIndex = savedInstanceState.getInt(INDEX_KEY, 0);
            answeredArray = savedInstanceState.getBooleanArray(ANSWERED_KEY);
        }

        mFalseButton = (Button) findViewById(R.id.false_button);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mQuestionTextView = (TextView) findViewById(R.id.question_textview);
        updateQuestion();
        updateLayout();

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                answeredArray[mQuestionIndex] = true;
                updateLayout();
            }
        });

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                answeredArray[mQuestionIndex] = true;
                updateLayout();
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestionIndex = (mQuestionIndex + 1) % mQuestionBank.length;
                updateQuestion();
                updateLayout();
            }
        });

        mBackButton = (ImageButton) findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mQuestionIndex == 0) mQuestionIndex = mQuestionBank.length - 1;
                else mQuestionIndex = mQuestionIndex - 1;
                updateQuestion();
                updateLayout();
            }
        });

    }

    private void updateLayout() {
        if (answeredArray[mQuestionIndex]) {
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        } else {
            mTrueButton.setEnabled(true);
            mFalseButton.setEnabled(true);
        }
        if (areAllTrue(answeredArray)) {
            double percentCorrect = mNumCorrect / mQuestionBank.length * 100;
            Toast.makeText(this, "You got " + mNumCorrect + " out of " + mQuestionBank.length + " correct!", Toast.LENGTH_LONG).show();
        }
    }

    private void updateQuestion() {
        int question = mQuestionBank[mQuestionIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private boolean areAllTrue(boolean[] booleanArray) {
        for (boolean answered : booleanArray) {
            if (answered == false) {
                return false;
            }
        }
        return true;
    }

    private void checkAnswer(boolean userPress) {
        boolean answer = mQuestionBank[mQuestionIndex].isAnswer();
        if (userPress == answer) {
            Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
            mNumCorrect++;
        } else {
            Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle onSaveInstanceState) {
        super.onSaveInstanceState(onSaveInstanceState);
        onSaveInstanceState.putInt(INDEX_KEY, mQuestionIndex);
        onSaveInstanceState.putBooleanArray(ANSWERED_KEY, answeredArray);
        Log.d(TAG, "onSaveInstanceState()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }
}