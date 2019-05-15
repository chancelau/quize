package com.itheima.demo02TCP通信;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
服务端开发
SeverSocket: 服务端得类
创建对象：
    public ServerSocket(int port): 监听本地得端口号

常用方法：
    public Socket accept(): 接收客端端得连接，如果客端没有连接过来，该方法会处于阻塞状态【一直等待客户端得连接】
    public void close();


步骤：
1）创建服务单ServerSocket对象，监听指定端口
2）接收客端得连接，获取对应得Socket对象【accept】
3）获取socket对象得输入流，获取客户端写出得数据
4）获取socket对象得输出流，写出数据到客户端
5）告诉客端写出数据完毕【shutdownOutput】
6）关闭socket对象连接
7）把服务器关掉


 */
public class Server {
    public static void main(String[] args) throws IOException {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        //1）创建服务单ServerSocket对象，监听指定端口
        ServerSocket serverSocket = new ServerSocket(8888);
        while (true) {

            //2）接收客端得连接，获取对应得Socket对象【accept】
            System.out.println("等待客户端得连接....");
            Socket socket = serverSocket.accept();//阻塞
            System.out.println("有一个客户端连接成功....");

            threadPool.submit(() -> {
                try {
                    while (true) {
                        //3）获取socket对象得输入流，获取客户端写出得数据
                        InputStream netIn = socket.getInputStream();
                        int len;
                        byte[] bytes = new byte[1024];
                        while ((len = netIn.read(bytes)) != -1) {
                            System.out.println(new String(bytes, 0, len));
                        }
                        socket.shutdownInput();
                        System.out.println("读完数据");

                        //4）获取socket对象得输出流，写出数据到客户端
                        OutputStream netOut = socket.getOutputStream();
                        netOut.write("好，请你看 复联4".getBytes());
                        System.out.println("写完数据");

                        //5）告诉客端写出数据完毕【shutdownOutput】
                        socket.shutdownOutput();
                        //6）关闭socket对象连接
                        //socket.close();
                    }
                } catch (IOException e) {

                }
            });


        }
        //7）把服务器关掉
       // serverSocket.close();



    }
}
