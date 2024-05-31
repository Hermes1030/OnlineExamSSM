package com.taohan.online.exam.test;


import com.taohan.online.exam.handler.SubjectInfoHandler;

import java.io.File;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

public class test {
    public static void main(String[] args) {
        //deleteUploadFile("build/classes/artifacts/OnlineExamSSM_war_exploded/WEB-INF\\upload\\sample.xlsx");
        String str1 = "前序遍历（Pre-order Traversal）：访问根节点 -> 遍历左子树 -> 遍历右子树。\n" +
                "中序遍历（In-order Traversal）：遍历左子树 -> 访问根节点 -> 遍历右子树。\n" +
                "后序遍历（Post-order Traversal）：遍历左子树 -> 遍历右子树 -> 访问根节点。";
        String str2 = "这是我的一个简答题答案，前序遍历（Preorder Traversal）";
//        System.out.println(getSimilarityRatio(str1,str2));
        int socore1 = (int)getSimilarityRatio(str1,str2);
        double socor2 = calculateJaccardSimilarity(str1,str2);

        System.out.println("（getSimilarityRatio）字符串匹配相关度为："+socore1);
        System.out.println("（calculateJaccardSimilarity）字符串匹配相关度为："+socor2);
    }

    private static void deleteUploadFile(String filePath) {
        File file = new File(filePath);
        filePath = filePath.replace("/WEB-INF/upload/", "\\WEB-INF\\upload\\");
        if (file.exists()) {
            file.delete();
            System.out.println("上传文件已被删除 " + filePath);
        }
    }

    /**
     * 计算两个字符串的相似度比率
     *
     * @param str 源字符串
     * @param target 目标字符串
     * @return 两个字符串的相似度比率，范围在0到100之间
     */
    public static float getSimilarityRatio(String str, String target) {

        int d[][]; // 用于存储动态规划过程中的中间结果的矩阵
        int n = str.length(); // 源字符串长度
        int m = target.length(); // 目标字符串长度
        int i; // 遍历源字符串的索引
        int j; // 遍历目标字符串的索引
        char ch1; // 源字符串的当前字符
        char ch2; // 目标字符串的当前字符
        int temp; // 用于记录两个字符是否相等的临时变量，相等则为0，不相等则为1
        // 如果任一字符串为空，则相似度为0
        if (n == 0 || m == 0) {
            return 0;
        }
        d = new int[n + 1][m + 1]; // 初始化动态规划矩阵
        // 初始化矩阵的第一列
        for (i = 0; i <= n; i++) {
            d[i][0] = i;
        }

        // 初始化矩阵的第一行
        for (j = 0; j <= m; j++) {
            d[0][j] = j;
        }

        // 动态规划计算相似度
        for (i = 1; i <= n; i++) { // 遍历源字符串
            ch1 = str.charAt(i - 1);
            // 去匹配目标字符串
            for (j = 1; j <= m; j++) {
                ch2 = target.charAt(j - 1);
                // 判断两个字符是否相等或大小写不同
                if (ch1 == ch2 || ch1 == ch2 + 32 || ch1 + 32 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                // 计算当前位置的最小编辑距离，取左边+1, 上边+1, 左上角+temp的最小值
                d[i][j] = Math.min(Math.min(d[i - 1][j] + 1, d[i][j - 1] + 1), d[i - 1][j - 1] + temp);
            }
        }

        // 计算并返回相似度比率
        return (1 - (float) d[n][m] / Math.max(str.length(), target.length())) * 100F;
    }
    public static double calculateJaccardSimilarity(String strA, String strB) {
        // 将字符串转换为字符集合
//        System.out.println("原字符串："+strA);
        Set<Character> setA = new HashSet<>();
        for (char c : strA.toCharArray()) {
            setA.add(c);
//            System.out.println("拆分的字符串：" + c);
        }

        Set<Character> setB = new HashSet<>();
        for (char c : strB.toCharArray()) {
            setB.add(c);
        }

        // 计算交集和并集的大小
        Set<Character> intersection = new HashSet<>(setA);
        intersection.retainAll(setB);

        Set<Character> union = new HashSet<>(setA);
        union.addAll(setB);

        // 计算Jaccard相似度
        double result = (double) intersection.size() / union.size();

        // 格式化返回值为8位小数点
        DecimalFormat decimalFormat = new DecimalFormat("#.########");
        String formattedResult = decimalFormat.format(result);
        return Double.parseDouble(formattedResult);
    }

}
