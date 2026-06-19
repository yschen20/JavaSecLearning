package demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

public class StudentUnserialize04 {
    public static void main(String[] args) {
        /// 非嵌套
        String jsonString = "{\"@type\":\"demo.Student\",\"age\":20,\"name\":\"Suzen\"}";
        /// 嵌套
//        String jsonString = "{\"user\":{\"@type\":\"demo.Student\",\"age\":20,\"name\":\"Suzen\",\"address\":\"China\",\"properties\":{}}}";
        Object obj = JSON.parseObject(jsonString, Student.class, Feature.SupportNonPublicField);
        System.out.println(obj);
        System.out.println(obj.getClass().getName());
    }
}
