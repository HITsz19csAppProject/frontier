package com.example.chatting;

import java.util.ArrayList;

import Tools.AddLabels;

public class LabelOnClicked{
    ArrayList<String> _grade = new ArrayList<>();        //年级
    ArrayList<String> _class = new ArrayList<>();        //班级
    ArrayList<String> _academy = new ArrayList<>();      //学院
    ArrayList<String> _speciality = new ArrayList<>();   //专业

    public void LabelOnClicked()
    {
        AddLabels addLabels = new AddLabels();
        addLabels.grade = _grade;
        addLabels.Class = _class;
        addLabels.academy = _academy;
        addLabels.speciality = _speciality;

        addLabels.addLabels();
    }
}
