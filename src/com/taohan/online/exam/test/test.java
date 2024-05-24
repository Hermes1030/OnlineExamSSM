package com.taohan.online.exam.test;


import com.taohan.online.exam.handler.SubjectInfoHandler;

import java.io.File;

public class test {
    public static void main(String[] args) {
        deleteUploadFile("build/classes/artifacts/OnlineExamSSM_war_exploded/WEB-INF\\upload\\sample.xlsx");
    }

    private static void deleteUploadFile(String filePath) {
        File file = new File(filePath);
        filePath = filePath.replace("/WEB-INF/upload/", "\\WEB-INF\\upload\\");
        if (file.exists()) {
            file.delete();
            System.out.println("上传文件已被删除 " + filePath);
        }
    }
}
