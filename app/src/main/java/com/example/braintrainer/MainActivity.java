package com.example.braintrainer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout gameLayout;

    private int a;
    private int b;
    private int score;
    private int totalQuestions;
    private int index;

    private ArrayList<Integer> answers;

    private CountDownTimer countDownTimer;

    private Button goButton, button0, button1, button2, button3, restartButton;
    private TextView sumTextView, scoreTextView, timeTextView, resultTextView;

    public void start(View view) {
        goButton.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
        loadGameState();
        countDownTimer.start();
    }

    public void restart(View view) {
        restartButton.setVisibility(View.INVISIBLE);
        score = 0;
        totalQuestions = 0;
        loadGameState();
        timeTextView.setText("30s");
        countDownTimer.start();
    }

    public void chooseAnswer(View view) {

        String tag = (String) view.getTag();

        if (tag.equals(Integer.toString(index))) {
            score++;
            resultTextView.setText("CORRECT!");
        } else {
            resultTextView.setText("WRONG!");
        }

        totalQuestions++;

        loadGameState();
    }

    private void loadGameState() {

        Random random = new Random();

        a = random.nextInt(21);
        b = random.nextInt(21);

        int answer = a + b;
        String answerStr = Integer.toString(a) + " + " + Integer.toString(b);
        sumTextView.setText(answerStr);

        String sumStr = Integer.toString(score) + "/" + Integer.toString(totalQuestions);
        scoreTextView.setText(sumStr);

        answers.clear();

        index = random.nextInt(4);

        for (int i = 0; i < 4; i++) {

            if (i == index)
                answers.add(answer);
            else {
                int randomAnswer = random.nextInt(41);

                while (randomAnswer == answer)
                    randomAnswer = random.nextInt(41);

                answers.add(randomAnswer);
            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameLayout = findViewById(R.id.gameLayout);

        score = 0;
        totalQuestions = 0;

        goButton = findViewById(R.id.button);
        restartButton = findViewById(R.id.restartButton);

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        sumTextView = findViewById(R.id.sumTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        timeTextView = findViewById(R.id.timeTextView);
        resultTextView = findViewById(R.id.resultTextView);

        answers = new ArrayList<>();

        countDownTimer = new CountDownTimer(30100, 1000) {

            /**
             * Callback fired on regular interval.
             *
             * @param millisUntilFinished The amount of time until finished.
             */
            @Override
            public void onTick(long millisUntilFinished) {
                String timeStr = millisUntilFinished / 1000 + "s";
                timeTextView.setText(timeStr);
            }

            /**
             * Callback fired when the time is up.
             */
            @Override
            public void onFinish() {
                resultTextView.setText("Time's up!");
                restartButton.setVisibility(View.VISIBLE);
            }
        };
    }
}
