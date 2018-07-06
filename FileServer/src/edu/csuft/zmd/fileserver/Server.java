package edu.csuft.zmd.fileserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 网盘服务器
 * 
 * @author wtao
 *
 */
public class Server {

	/**
	 * TCP 服务端套接字
	 */
	ServerSocket serverSocket;
	
	/**
	 * 端口
	 */
	int port = 9000;
	
	/**
	 * 线程池
	 */
	ExecutorService pool;
	
	
	public void start() {
		// 创建线程池：同时响应多个客户端的请求【并发编程】
		pool = Executors.newCachedThreadPool();
//		pool = Executors.newFixedThreadPool(9);
//		pool = Executors.newSingleThreadExecutor();
		
		// 创建服务端套接字
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("服务器启动了...");
			
			// 接收客户端的链接
			while (true) {
				Socket socket = serverSocket.accept();
				
				pool.execute(new FileUploadTask(socket));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
