package tools;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

public class Encode {
    public static void serialize(Object obj) throws Exception{
        FileOutputStream fos = new FileOutputStream("ser.bin");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(obj);
        oos.close();
    }

    public static void serializeBase64Encode(Object obj) throws Exception{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        oos.close();
        String base64Str = Base64.getEncoder().encodeToString(baos.toByteArray());
        System.out.println(base64Str);
        FileOutputStream fos = new FileOutputStream("ser.bin");
        fos.write(base64Str.getBytes());
        fos.close();
    }
}
