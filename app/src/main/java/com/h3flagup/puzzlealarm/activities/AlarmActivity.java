package com.h3flagup.puzzlealarm.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.h3flagup.puzzlealarm.R;
import com.h3flagup.puzzlealarm.entities.Operation;
import com.h3flagup.puzzlealarm.entities.Question;

public class AlarmActivity extends AppCompatActivity {

    private Question question;
    private EditText answerBox;
    private TextView op1;
    private TextView op2;
    private TextView operation;
    private int answeredQuestions = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        answerBox = findViewById(R.id.answerBox);
        op1 = findViewById(R.id.op1);
        op2 = findViewById(R.id.op2);
        operation = findViewById(R.id.operation);
        setNewQuestion();
        answerBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    int typedAnswer = Integer.parseInt(answerBox.getText().toString());
                    if (typedAnswer == question.getAnswer())
                    {
                        answeredQuestions++;
                        if (answeredQuestions == 3)
                            finish();
                        setNewQuestion();
                        handled = true;
                    }
                }
                return handled;
            }
        });
    }

    private void setNewQuestion() {
        question = Question.random();
        op1.setText(String.valueOf(question.getOperand1()));
        op2.setText(String.valueOf(question.getOperand2()));
        Operation o = question.getOperation();
        switch (o)
        {
            case Add:
                operation.setText("+");
                break;
            case Multiply:
                operation.setText("x");
                break;
            case Subtract:
                operation.setText("-");
                break;
        }
    }
}