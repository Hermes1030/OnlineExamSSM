package com.taohan.online.exam.charts;

import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.*;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Line;
import com.taohan.online.exam.po.ClassInfo;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;


@Repository
public class StudentCount {

    /**
     * 生成并返回一个关于班级学生数量统计的GsonOption对象的JSON字符串。
     * 该函数配置了GsonOption的各种属性，包括标题、工具箱、提示框、图例、坐标轴和系列数据等，
     * 用于展示班级学生数量的折线图。
     *
     * @param data 包含班级信息和对应学生数量的数据映射表
     * @return 返回配置好的GsonOption对象的JSON字符串
     */
    public static String createBarJson(Map<String, Object> data) {
        // 获取数据集中的所有班级名称
        Set<String> set = data.keySet();

        GsonOption option = new GsonOption();
        // 设置标题和样式
        option.title().text("Statistics on the number of students in a class").x(X.center).y(Y.top).borderWidth(1).textStyle().color("#438EB9");
        // 配置工具箱，包括标记、恢复、魔法类型（柱状图和折线图）和保存为图片的功能
        option.toolbox().show(true).feature(Tool.mark, Tool.restore, new MagicType(Magic.bar, Magic.line), Tool.saveAsImage).x(X.right).y(Y.top);
        //数据默认触发， 鼠标移入显示竖线  trigger(Trigger.axis)
        option.tooltip().formatter("{b} {c}人").trigger(Trigger.axis);

        option.legend().data("Total class size").x(X.center).y(Y.bottom).borderWidth(1);

        Line line = new Line("Total class size");

        //值轴
        ValueAxis valueAxis = new ValueAxis();
        valueAxis.axisLabel().formatter("{value}人").textStyle().color("#438EB9");
        //valueAxis.min(0);
        option.yAxis(valueAxis);

        //类目轴
        CategoryAxis categoryAxis = new CategoryAxis();
        //interval(0)：设置横轴信息全部显示
        //rotate(-30)：设置 -30 度角倾斜显示
        categoryAxis.axisLabel().interval(0).rotate(-30).textStyle().color("#438EB9");

        for (String className : set) {
            categoryAxis.data(className);
            ClassInfo classInfo = (ClassInfo) data.get(className);
            line.data(classInfo.getClassId());
        }

        option.xAxis(categoryAxis);
        line.smooth(true);
        option.series(line);
        option.grid().x(100);
        System.out.println(option);
        return option.toString();
    }
}
