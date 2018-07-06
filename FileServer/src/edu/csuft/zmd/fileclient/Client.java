package edu.csuft.zmd.fileclient;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * 客户端定义
 * 
 * @author wtao（wtao.jyg@qq.com）
 *
 */
public class Client {
	
	/**
	 * 套接字：电源插座，通过套接字建立网络链接
	 * 发送：从套接字获得输出流，执行写操作
	 * 接收：从套接字获得输入流，执行读操作
	 */
	Socket socket;
	
	/**
	 * 服务器的地址
	 */
	String serverAddress = "127.0.0.1";
	
	/**
	 * 服务器的端口：0 <= port <= 65535, 共计 2^16 个
	 * 1024 以内的保留
	 */
	int serverPort = 9000;
	
	/**
	 * 客户端启动
	 */
	public void start(String string,String file) {
		try {
			socket = new Socket(serverAddress, serverPort);
			System.out.println("客户端成功的链接到了服务器");
			// 选择一个文件，执行文件的上传
			
			// GUI\命令行
//			Scanner sc = new Scanner(System.in);
//			System.out.print("请输入要上传的文件名：");
//			String string=sc.next();
//			System.out.println("请输入文件路径：");
//			String file = sc.next()+string;
//			sc.close();
			
			// 发送: 从套接字获得【输出流】发送数据
			OutputStream out = socket.getOutputStream();
//			
			// 从文件读取数据
//			FileInputStream in = new FileInputStream(string);
			@SuppressWarnings("resource")
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
			
			byte[] buf = new byte[1024 * 8];
			int size;
			
			while (-1 != (size = in.read(buf))) {
				// 发送
				out.write(buf, 0, size);
				// 刷新缓冲区
				out.flush();
			}
			
			// 已成功发送
			System.out.println("发送完成");
			
			socket.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 始终执行
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	
	
	
	

}
