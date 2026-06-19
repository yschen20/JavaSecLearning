package demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

public class StudentUnserialize03 {
    public static void main(String[] args) {
        String jsonString ="{\"@type\":\"demo.Student\",\"age\":20,\"name\":\"Suzen\",\"address\":\"China\",\"properties\":{}}";
        Object obj = JSON.parse(jsonString, Feature.SupportNonPublicField);
        System.out.println(obj);
        System.out.println(obj.getClass().getName());
    }
}