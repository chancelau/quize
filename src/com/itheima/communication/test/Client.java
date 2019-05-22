package com.itheima.communication.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;


public class Client {

    public static void main(String[] args) throws Exception {
        test();
    }

    public static void test() throws Exception {
        Socket socket = new Socket("localhost", 9999);

        // 发送请求
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("Class");
        Element type = root.addElement("Types");
        Element classes = root.addElement("Classes");
        Element function = classes.addElement("FunctionLanding");
        type.addAttribute("type", "String").addText("LandConn");
        classes.addAttribute("name", "SQLParse");
        function.addAttribute("name", "LandDemo");
        function.addElement("IPparam").addAttribute("type", "String").addText("ClientIP");
        function.addElement("LNparam").addAttribute("type", "String").addText("ClientName");
        function.addElement("ANparam").addAttribute("type", "String").addText("ClientAccountNumber");
        function.addElement("PWparam").addAttribute("type", "String").addText("ClientPassWord");
        function.addElement("Fparam").addAttribute("type", "String").addText("Lflag");
        // 是否有缩进
        String indent = "   ";
        // 是否产生新行（即一行一个元素）
        boolean newline = true;
        XMLWriter writer = new XMLWriter(socket.getOutputStream(), new OutputFormat(indent, newline, "gb2312"));
        writer.write(doc);
        writer.flush();
        System.out.println("发送完毕");

        // 接收响应
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        // 接收数据，直至 socket 被对方 close（或者 shutdownOutput）
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        System.out.println("接收到的内容: " + sb.toString());

        socket.close();
    }
}
