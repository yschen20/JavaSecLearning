package demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

public class StudentUnserialize01 {
    public static void main(String[] args) {
        String jsonString = "{\"@type\":\"demo.Student\",\"age\":20,\"name\":\"Suzen\"}";
        /// 不带Feature.SupportNonPublicField
//        Student student = JSON.parseObject(jsonString, Student.class);
        /// 带Feature.SupportNonPublicField
        Student student = JSON.parseObject(jsonString, Student.class, Feature.SupportNonPublicField);
        System.out.println(student);
        System.out.println(student.getClass().getName());
        System.out.println(student.getName());
        System.out.println(student.getAge());

    }
}