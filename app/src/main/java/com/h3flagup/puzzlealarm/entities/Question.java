package com.h3flagup.puzzlealarm.entities;

import java.util.Random;

public class Question {
    private int operand1;
    private int operand2;
    private int answer;
    private static Random rand = new Random();
    Operation operation;

    private Question()
    {
    }

    public static Question random()
    {
        Question ret = new Question();
        ret.operand1 = rand.nextInt(20) + 1;
        ret.operand2 = rand.nextInt(20) + 1;
        int randOp = rand.nextInt(3);

        switch (randOp)
        {
            case 0:
                ret.operation = Operation.Add;
                ret.answer = ret.operand1 + ret.operand2;
                break;
            case 1:
                ret.operation = Operation.Subtract;
                ret.answer = ret.operand1 - ret.operand2;
                break;
            case 2:
                ret.operation = Operation.Multiply;
                ret.answer = ret.operand1 * ret.operand2;
                break;
        }
        if (ret.operation == Operation.Subtract && ret.operand1 < ret.operand2)
        {
            int tmp = ret.operand1;
            ret.operand1 = ret.operand2;
            ret.operand2 = tmp;
        }
        return ret;
    }

    public int getOperand1() {
        return operand1;
    }

    public int getOperand2() {
        return operand2;
    }

    public Operation getOperation() {
        return operation;
    }

    public int getAnswer() {
        return answer;
    }
}
