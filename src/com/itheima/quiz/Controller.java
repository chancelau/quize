package com.itheima.quiz;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    public static final int state_begin = 100;//开始
    public static final int state_choose_quiz = 103; //选题
    public static final int state_begin_answer = 104; //开始答题
    public static final int state_time_stop = 101;//计时结束
    public static final int state_show_answer = 102;//显示答案，答题结束
    public static final int state_game_over = 105;//游戏结束
    private static ArrayList<String> QA = new ArrayList<>();
    @FXML
    Button button;
    @FXML
    Label label_question, label_answer, label_time, label_left_question;
    int current_state = state_begin;
    @FXML
    ImageView img_haibao;
    /**
     * 当前题目
     */
    Quiz currentQuiz;
    //计时器
    int time = 60_000;
    Timer timer = new Timer();
    TimerTask task = null;
    private long period = 1000;

    private static void initialQA() {
        try {
            InputStreamReader isr = new InputStreamReader(Controller.class.getClassLoader().getResourceAsStream("QALibs.txt"), "utf8");
            BufferedReader br = new BufferedReader(isr);

            String qa;
            while ((qa = br.readLine()) != null) {
                QA.add(qa);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.shuffle(QA);
    }

    @FXML
    protected void click() throws Exception {
        // showQuestion();
        if (current_state == state_begin) {
            initialQA();
            refresh(state_choose_quiz);
        } else if (current_state == state_choose_quiz) {
            //选题
            chooseQuiz();
        } else if (current_state == state_begin_answer) {//
            // 答题结束
            answerOver();
        }
    }

    private void updateLeftQuestionNumber() {
        label_left_question.setVisible(true);
        label_left_question.setText("剩余题数：" + QA.size());
    }

    /**
     * 答题结束
     */
    private void answerOver() {
        stopTimeCounting();//停止计时
        refresh(state_choose_quiz);
        label_answer.setText(currentQuiz.getAnswer());

    }

    public void beginGame() {
        refresh(state_begin);
        img_haibao.setVisible(false);
    }

    public void chooseQuiz() {
        label_answer.setText("稍后呈上答案！！");
        if (QA.size() > 0) {
            String qa = QA.remove(0);
            String[] q_a = qa.split("\t");
            currentQuiz = new Quiz(q_a[0], q_a[1]);
            label_question.setText(currentQuiz.getQuestion());
            startTimeCounting();//开始计时
            refresh(state_begin_answer);
            updateLeftQuestionNumber();
        } else {
            label_question.setText("比赛结束");
            label_answer.setText("快来领奖吧！");
            label_time.setText("");
            button.setVisible(false);
            label_left_question.setVisible(false);
        }

    }

    public void refresh(int state) {
        current_state = state;
        switch (state) {
            case state_begin:
                button.setText("开始");
                break;
            case state_choose_quiz:
                button.setText("选题");
                break;
            case state_begin_answer:
                button.setText("结束答题");
                break;
        }
    }

    /*
    开始计时
     */
    public void startTimeCounting() {
        if (task != null) {
            task.cancel();
        }
//        time = 30_000;
        time = 30;
        task = new TimerTask() {
            @Override
            public void run() {
                if (time < 1) {
                    cancel();
                    //答题结束
                    Platform.runLater(() -> answerOver());
                    return;
                }
                /*String strMill = "";
                int milli = time % 1000;
                if (milli < 10) {
                    strMill = "00" + milli;
                } else if (milli > 9 && milli < 100) {
                    strMill = "0" + milli;
                } else {
                    strMill = "" + milli;
                }
                String timeStr = --time/1000 + ":"+ strMill;*/
                Platform.runLater(() -> {
                    label_time.setText("您还剩下时间：" + --time + "秒");
                });
            }
        };
        timer.schedule(task, 0, period);
    }

    /**
     * 结束计时
     */
    public void stopTimeCounting() {
        if (task != null) {
            task.cancel();
        }
    }
}
