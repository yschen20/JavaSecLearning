package demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class StudentSerialize {
    public static void main(String[] args) {
        Student student = new Student();
        student.setName("Suzen");
//        student.setAge(20);
        /// 未设置SerializerFeature.WriteClassName
        String jsonString1 = JSON.toJSONString(student);
        System.out.println(jsonString1);
        /// 设置了SerializerFeature.WriteClassName
        String jsonString2 = JSON.toJSONString(student, SerializerFeature.WriteClassName);
        System.out.println(jsonString2);
    }
}
