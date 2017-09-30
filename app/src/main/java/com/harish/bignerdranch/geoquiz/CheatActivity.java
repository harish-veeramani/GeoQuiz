package com.harish.bignerdranch.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import static android.R.attr.animation;

public class CheatActivity extends AppCompatActivity {
    private boolean mAnswer;
    private boolean mHasCheated;
    private TextView mCheatTextView;
    private Button mShowAnswerButton;

    private static final String EXTRA_ANSWER_IS_TRUE = "com.harish.bignerdranch.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.harish.bignerdranch.geoquiz.answer_shown";
    private static final String HAS_CHEATED_KEY = "Has Cheated?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswer = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mCheatTextView = (TextView) findViewById(R.id.cheat_answer_textview);
        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);

        if (savedInstanceState != null) {
            mHasCheated = savedInstanceState.getBoolean(HAS_CHEATED_KEY, false);
            if (mHasCheated){
                mShowAnswerButton.setVisibility(View.INVISIBLE);
                if (mAnswer){
                    mCheatTextView.setText(R.string.true_button);
                }else{
                    mCheatTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(mHasCheated);
            }

        }

        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (mAnswer){
                    mCheatTextView.setText(R.string.true_button);
                }else{
                    mCheatTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);

                cheatButtonInvisible();

            }
        });
    }

    private void cheatButtonInvisible() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = mShowAnswerButton.getWidth() / 2;
            int cy = mShowAnswerButton.getHeight() / 2;
            float radius = mShowAnswerButton.getWidth();
            Animator animator = ViewAnimationUtils.createCircularReveal(mShowAnswerButton, cx, cy, radius, 0);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mShowAnswerButton.setVisibility(View.INVISIBLE);
                }
            });
            animator.start();
        }else{
            mShowAnswerButton.setVisibility(View.INVISIBLE);
        }
    }

    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    private void setAnswerShownResult (boolean answer){
        Intent returnIntent = new Intent();
        returnIntent.putExtra(EXTRA_ANSWER_SHOWN, answer);
        setResult(Activity.RESULT_OK, returnIntent);
    }

    public static Intent newIntent(Context packageContext, boolean answerIsTrue){
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    @Override
    protected void onSaveInstanceState(Bundle onSaveInstanceState) {
        super.onSaveInstanceState(onSaveInstanceState);
        onSaveInstanceState.putBoolean(HAS_CHEATED_KEY, mHasCheated);
    }
}