package com.itheima.quiz;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Controller {
    private static ArrayList<String> QA = new ArrayList<>();

    static {
        try {
            BufferedReader br = new BufferedReader(new FileReader("QALibs.txt"));
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
    Button button;

    @FXML
    Label label_question,label_answer;


    int index;
    String[] q_a;

    @FXML
    protected void click() throws Exception {
        showQuestion();
    }

    public void showQuestion() {
        if (index == 2) {
            label_answer.setText(q_a[index]);
            index = 0;
            button.setText("下一题");
            return;
        }

        if (QA.size() > 0) {
            String qa = QA.remove(0);
            q_a = qa.split("\t");
            System.out.println(Arrays.toString(q_a));
            index = 1;
            label_question.setText(q_a[index]);
            label_answer.setText("");
            index++;
            button.setText("参考答案");
        } else {
            label_question.setText("知识竞赛结束！！");
            label_answer.setText("");
            button.setVisible(false);
        }
    }


}
