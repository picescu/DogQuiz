package com.example.android.dogquiz;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText name, lastQuestion;
    CheckBox c1, c2, c3;
    Button next, startQuiz, check, restart;
    RadioButton answer1, answer2, answer3;
    RadioGroup group;
    TextView question, title;
    int questionNumber = 1, result1 = 0, result2 = 0, result3 = 0;
    Resources res;
    String[] questions, answers1, answers2, answers3;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.name);
        lastQuestion = (EditText) findViewById(R.id.lastQuestion);

        c1 = (CheckBox) findViewById(R.id.c1);
        c2 = (CheckBox) findViewById(R.id.c2);
        c3 = (CheckBox) findViewById(R.id.c3);
        next = (Button) findViewById(R.id.next);
        startQuiz = (Button) findViewById(R.id.startQuiz);
        check = (Button) findViewById(R.id.check);
        restart = (Button) findViewById(R.id.restart);
        group = (RadioGroup) findViewById(R.id.group);
        answer1 = (RadioButton) findViewById(R.id.answer1);
        answer2 = (RadioButton) findViewById(R.id.answer2);
        answer3 = (RadioButton) findViewById(R.id.answer3);
        question = (TextView) findViewById(R.id.question);
        title = (TextView) findViewById(R.id.title);
        res = getResources();
        questions = res.getStringArray(R.array.question);
        answers1 = res.getStringArray(R.array.answer1);
        answers2 = res.getStringArray(R.array.answer2);
        answers3 = res.getStringArray(R.array.answer3);
        image = (ImageView) findViewById(R.id.image);
    }

    public void startQuiz(View v) {
        if (name.getText().toString().equals("")) {
            Toast message = Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT);
            message.show();
            return;
        }

        name.setVisibility(View.GONE);

        startQuiz.setVisibility(View.GONE);
        next.setVisibility(View.VISIBLE);
        question.setText(String.format(questions[0], name.getText().toString()));
        question.setVisibility(View.VISIBLE);
        group.setVisibility(View.VISIBLE);
        answer1.setText(answers1[0].toString());
        answer2.setText(answers2[0].toString());
        answer3.setText(answers3[0].toString());
        title.setText(res.getString(R.string.questionNumber, questionNumber));
        ScrollView mainLayout = (ScrollView) findViewById(R.id.ScrollView);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
    }


    public void next(View v) {
        if ((group.getCheckedRadioButtonId() == -1) && (questionNumber < 9)) {
            Toast message = Toast.makeText(getApplicationContext(), R.string.error2, Toast.LENGTH_SHORT);
            message.show();
            return;
        }
        if (questionNumber == 9) {
            if (!c1.isChecked() && !c2.isChecked() && !c3.isChecked()) {
                Toast message = Toast.makeText(getApplicationContext(), R.string.error2, Toast.LENGTH_SHORT);
                message.show();
                return;
            }
        }


        if (questionNumber < 8) {
            switch (res.getResourceEntryName(group.getCheckedRadioButtonId())) {
                case "answer1": {
                    result1++;
                    break;
                }
                case "answer2": {
                    result2++;
                    break;
                }
                case "answer3": {
                    result3++;
                    break;
                }
            }
            questionNumber++;
            answer1.setText((answers1[questionNumber - 1]).toString());
            answer2.setText((answers2[questionNumber - 1]).toString());
            answer3.setText((answers3[questionNumber - 1]).toString());
            group.clearCheck();
        } else if (questionNumber == 8) {
            switch (res.getResourceEntryName(group.getCheckedRadioButtonId())) {
                case "answer1": {
                    result1++;
                    break;
                }
                case "answer2": {
                    result2++;
                    break;
                }
                case "answer3": {
                    result3++;
                    break;
                }
            }
            questionNumber++;
            group.setVisibility(View.GONE);
            c1.setText((answers1[questionNumber - 1]).toString());
            c2.setText((answers2[questionNumber - 1]).toString());
            c3.setText((answers3[questionNumber - 1]).toString());
            c1.setVisibility(View.VISIBLE);
            c2.setVisibility(View.VISIBLE);
            c3.setVisibility(View.VISIBLE);

        } else if (questionNumber == 9) {
            if (c1.isChecked()) result1++;
            if (c2.isChecked()) result2++;
            if (c3.isChecked()) result3++;
            c1.setVisibility(View.GONE);
            c2.setVisibility(View.GONE);
            c3.setVisibility(View.GONE);
            lastQuestion.setVisibility(View.VISIBLE);
            questionNumber++;
            check.setVisibility(View.VISIBLE);
            next.setVisibility(View.GONE);
        } else {
            question.setVisibility(View.GONE);

            if (lastQuestion.equals("yes")) result1++;
            else if (lastQuestion.equals("no")) result2++;
            else result3++;

        }


        question.setText(String.format(questions[questionNumber - 1], name.getText().toString()));

        title.setText(res.getString(R.string.questionNumber, questionNumber));

    }

    public void endQuiz(View v) {
        Toast message;
        if ((result1 > result2) && (result1 > result3)) {
            image.setImageResource(R.drawable.ciuhuahua);
            lastQuestion.setVisibility(View.GONE);
            question.setText(res.getString(R.string.result3));
            title.setText(res.getString(R.string.finish));
            message = Toast.makeText(getApplicationContext(), R.string.result3, Toast.LENGTH_LONG);
        } else if ((result3 > result2) && (result3 > result1)) {
            image.setImageResource(R.drawable.labrador);
            lastQuestion.setVisibility(View.GONE);
            question.setText(res.getString(R.string.result1));
            title.setText(res.getString(R.string.finish));
            message = Toast.makeText(getApplicationContext(), R.string.result1, Toast.LENGTH_LONG);
        } else {
            image.setImageResource(R.drawable.cocker);
            lastQuestion.setVisibility(View.GONE);
            question.setText(res.getString(R.string.result2));
            title.setText(res.getString(R.string.finish));
            message = Toast.makeText(getApplicationContext(), R.string.result2, Toast.LENGTH_LONG);
        }

        check.setVisibility(View.GONE);
        restart.setVisibility(View.VISIBLE);
        message.show();
    }

    public void restart(View v) {
        result1 = 0;
        result2 = 0;
        result3 = 0;
        questionNumber = 1;
        startQuiz.setVisibility(View.VISIBLE);
        restart.setVisibility(View.GONE);
        title.setText(res.getString(R.string.title));
        image.setImageResource(R.drawable.alldogs);
        name.setVisibility(View.VISIBLE);
        name.setText("");
        lastQuestion.setText("");
        question.setVisibility(View.GONE);
        lastQuestion.setVisibility(View.GONE);
        group.clearCheck();
        c1.setChecked(false);
        c2.setChecked(false);
        c3.setChecked(false);

    }
}
