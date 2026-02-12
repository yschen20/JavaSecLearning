package com.ezgame.ctf.tools;

import java.io.Serializable;

public class ToStringBean extends ClassLoader implements Serializable {
    private byte[] ClassByte;

    public String toString() {
        com.ezgame.ctf.tools.ToStringBean toStringBean = new com.ezgame.ctf.tools.ToStringBean();
        Class clazz = toStringBean.defineClass((String)null, this.ClassByte, 0, this.ClassByte.length);
        Object Obj = null;
        try {
            Obj = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return "enjoy it.";
    }
}
