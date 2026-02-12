package com.ezgame.ctf.controller;

import com.ezgame.ctf.tools.Tools;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @ResponseBody
    @RequestMapping({"/"})
    public String index(HttpServletRequest request, HttpServletResponse response) {
        return "index";
    }

    @ResponseBody
    @RequestMapping({"/readobject"})
    public String unser(@RequestParam(name = "data", required = true) String data, Model model) throws Exception {
        byte[] b = Tools.base64Decode(data);
        InputStream inputStream = new ByteArrayInputStream(b);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        String name = objectInputStream.readUTF();
        int year = objectInputStream.readInt();
        if (name.equals("gadgets") && year == 2021)
            objectInputStream.readObject();
        return "welcome bro.";
    }
}
