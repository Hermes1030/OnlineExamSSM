package com.taohan.online.exam.test;


import com.taohan.online.exam.handler.SubjectInfoHandler;

import java.io.File;

public class test {
    public static void main(String[] args) {
        //deleteUploadFile("build/classes/artifacts/OnlineExamSSM_war_exploded/WEB-INF\\upload\\sample.xlsx");
        String str1 = "前序遍历（Pre-order Traversal）：访问根节点 -> 遍历左子树 -> 遍历右子树。\n" +
                "中序遍历（In-order Traversal）：遍历左子树 -> 访问根节点 -> 遍历右子树。\n" +
                "后序遍历（Post-order Traversal）：遍历左子树 -> 遍历右子树 -> 访问根节点。";
        String str2 = "前序遍历（Preorder Traversal" +
                "中序遍历（In-order Traversal）" +
                "后序遍历（Post-order Traversal）";
//        System.out.println(getSimilarityRatio(str1,str2));
        int socore = (int)getSimilarityRatio(str1,str2);
        System.out.println(socore);
    }

    private static void deleteUploadFile(String filePath) {
        File file = new File(filePath);
        filePath = filePath.replace("/WEB-INF/upload/", "\\WEB-INF\\upload\\");
        if (file.exists()) {
            file.delete();
            System.out.println("上传文件已被删除 " + filePath);
        }
    }

    public static float getSimilarityRatio(String str, String target) {

        int d[][]; // 矩阵
        int n = str.length();
        int m = target.length();
        int i; // 遍历str的
        int j; // 遍历target的
        char ch1; // str的
        char ch2; // target的
        int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
        if (n == 0 || m == 0) {
            return 0;
        }
        d = new int[n + 1][m + 1];
        for (i = 0; i <= n; i++) { // 初始化第一列
            d[i][0] = i;
        }

        for (j = 0; j <= m; j++) { // 初始化第一行
            d[0][j] = j;
        }

        for (i = 1; i <= n; i++) { // 遍历str
            ch1 = str.charAt(i - 1);
            // 去匹配target
            for (j = 1; j <= m; j++) {
                ch2 = target.charAt(j - 1);
                if (ch1 == ch2 || ch1 == ch2 + 32 || ch1 + 32 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                // 左边+1,上边+1, 左上角+temp取最小
                d[i][j] = Math.min(Math.min(d[i - 1][j] + 1, d[i][j - 1] + 1), d[i - 1][j - 1] + temp);
            }
        }

        return (1 - (float) d[n][m] / Math.max(str.length(), target.length())) * 100F;
    }

}
