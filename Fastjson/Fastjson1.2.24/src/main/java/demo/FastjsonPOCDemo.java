package demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

public class FastjsonPOCDemo {
    public static void main(String[] args) {
        String jsonString ="{\"@type\":\"demo.Student\",\"age\":20, \"name\":\"Suzen\",\"address\":\"China\",\"properties\":{}}";
        /// 场景一
        Object obj = JSON.parse(jsonString);
//        Object obj = JSON.parseObject(jsonString, Object.class);
        /// 场景二
//        Student obj = JSON.parseObject(jsonString, Student.class);
        System.out.println(obj);
        System.out.println(obj.getClass().getName());
    }
}
