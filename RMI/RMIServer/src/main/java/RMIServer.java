import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    public static void main(String[] args) throws Exception {
        // 创建远程对象
        RemoteObj remoteObj = new RemoteObjImpl();
        // 创建RMI注册中心
        Registry registry = LocateRegistry.createRegistry(1099);
        // 绑定远程对象
        registry.bind("RemoteObj", remoteObj);
    }
}
