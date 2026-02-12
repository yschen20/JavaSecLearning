package tools;

public class Encode {
    public static void serialize(Object obj) throws Exception{
        java.io.FileOutputStream fos = new java.io.FileOutputStream("ser.bin");
        java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(fos);
        oos.writeObject(obj);
        oos.close();
    }

    public static void serializeBase64Encode(Object obj) throws Exception{
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(baos);
        oos.writeObject(obj);
        oos.close();
        String base64Str = java.util.Base64.getEncoder().encodeToString(baos.toByteArray());
        System.out.println(base64Str);
        java.io.FileOutputStream fos = new java.io.FileOutputStream("ser.bin");
        fos.write(base64Str.getBytes());
        fos.close();
    }
}
