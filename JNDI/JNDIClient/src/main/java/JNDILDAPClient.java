import javax.naming.InitialContext;

public class JNDILDAPClient {
    public static void main(String[] args) throws Exception{
        InitialContext initialContext = new InitialContext();
        RemoteObj remoteObj = (RemoteObj) initialContext.lookup("ldap://localhost:1234/remoteObj");
        System.out.println(remoteObj.sayHello("hello"));
    }
}