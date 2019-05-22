package com.itheima.communication.demo02TCP通信;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/*
客户端程序Socket
类得使用：
1）创建对象
    public Socket(String host,int port): 可以连接指定地址，指定端口号得服务器
2）使用方法
    public InputStream getInputStream(): 获取输入流，从网络中读取数据
    public OutputStream getOutputStream():获取输出流，写出数据到网络
    public void shutdownOutput():关闭输出流，发送接收标记，不会关闭连接
    public void close():关闭Socket连接


步骤：
1）要创建Socket对象，连接指定得服务端
2）获取输出流，写出数据
3）关闭输出流
4）获取输入流，读取数据
5）关闭连接


 */
public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        //1）要创建Socket对象，连接指定得服务端
        Socket socket = new Socket("127.0.0.1", 8888);
        while (true) {
            Thread.sleep(2000);
            //2）获取输出流，写出数据
            OutputStream netOut = socket.getOutputStream();

            netOut.write("你好，我是客端，我想要看电影".getBytes());

            //3）关闭输出流
            //socket.shutdownOutput(); // 告诉服务端，我数据写出完毕
            System.out.println("写完数据");
            //4）获取输入流，读取数据
            InputStream netIn = socket.getInputStream();
            int len;
            byte[] bytes = new byte[1024];
            while ((len = netIn.read(bytes)) != -1) {
                System.out.println(new String(bytes, 0, len));
            }
            System.out.println("读完数据");

            //socket.shutdownInput();
        }
        //5）关闭连接
       // socket.close();
    }
}
