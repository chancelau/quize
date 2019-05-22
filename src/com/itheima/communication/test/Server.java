package com.itheima.communication.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class Server {

    public static void main(String[] args) throws Exception {
        test();
    }

    public static void test() throws IOException {
        ServerSocket ss = new ServerSocket(9999);
        Socket socket = ss.accept();

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line = null;
        // 接收数据，直至收到一行 "</Class>"
        while (!"</Class>".equals(line = br.readLine())) {
            sb.append(line);
        }
        sb.append("</Class>");
        System.out.println("接收到的内容: " + sb.toString());

        // 返回信息
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("Class");
        Element incept = root.addElement("incept");
        incept.addAttribute("type", "boolean").addText(String.valueOf(true));
        String lint = "   ";
        boolean newline = true;
        XMLWriter writer = new XMLWriter(socket.getOutputStream(), new OutputFormat(lint, newline, "gb2312"));
        writer.write(doc);
        writer.flush();
        System.out.println("发送完毕");

        socket.close();
        ss.close();
    }

}