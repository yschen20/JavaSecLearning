import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {
    public static void main(String[] args) throws Exception{
        // 获取远程对象
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
        RemoteObj remoteObj = (RemoteObj) registry.lookup("RemoteObj");
        // 调用远程方法
        remoteObj.sayHello("Suzen");
    }
}
