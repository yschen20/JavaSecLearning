package demo;

import com.alibaba.fastjson.JSON;

public class StudentUnserialize02 {
    public static void main(String[] args) {
        String jsonString ="{\"@type\":\"demo.Student\",\"age\":20, \"name\":\"Suzen\",\"address\":\"China\",\"properties\":{}}";
        /// 不带指定类型的参数
//        Object obj = JSON.parseObject(jsonString);
        /// 带指定类型的参数
        Object obj = JSON.parseObject(jsonString,Student.class);
        System.out.println(obj);
        System.out.println(obj.getClass().getName());
    }
}
