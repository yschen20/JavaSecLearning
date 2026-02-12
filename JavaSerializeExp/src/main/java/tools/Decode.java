package tools;

public class Decode {
    public static void unserialize(String Filename) throws Exception{
        java.io.FileInputStream fis = new java.io.FileInputStream(Filename);
        java.io.ObjectInputStream ois = new java.io.ObjectInputStream(fis);
        ois.readObject();
        ois.close();
    }

    public static Object unserializeBase64Decode(String Filename) throws Exception{
        java.io.FileInputStream fis = new java.io.FileInputStream(Filename);
        byte[] data = new byte[fis.available()];
        fis.read(data);
        fis.close();
        byte[] decodedBytes = java.util.Base64.getDecoder().decode(new String(data));
        java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(decodedBytes);
        java.io.ObjectInputStream ois = new java.io.ObjectInputStream(bais);
        Object obj = ois.readObject();
        return obj;
    }
}
