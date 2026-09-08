package tools;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Base64;

public class Decode {
    public static void unserialize(String Filename) throws Exception{
        FileInputStream fis = new FileInputStream(Filename);
        ObjectInputStream ois = new ObjectInputStream(fis);
        ois.readObject();
        ois.close();
    }

    public static Object unserializeBase64Decode(String Filename) throws Exception{
        FileInputStream fis = new FileInputStream(Filename);
        byte[] data = new byte[fis.available()];
        fis.read(data);
        fis.close();
        byte[] decodedBytes = Base64.getDecoder().decode(new String(data));
        ByteArrayInputStream bais = new ByteArrayInputStream(decodedBytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object obj = ois.readObject();
        return obj;
    }
}
