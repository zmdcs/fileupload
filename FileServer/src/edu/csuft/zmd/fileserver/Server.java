package edu.csuft.zmd.fileserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ���̷�����
 * 
 * @author wtao
 *
 */
public class Server {

	/**
	 * TCP ������׽���
	 */
	ServerSocket serverSocket;
	
	/**
	 * �˿�
	 */
	int port = 9000;
	
	/**
	 * �̳߳�
	 */
	ExecutorService pool;
	
	
	public void start() {
		// �����̳߳أ�ͬʱ��Ӧ����ͻ��˵����󡾲�����̡�
		pool = Executors.newCachedThreadPool();
//		pool = Executors.newFixedThreadPool(9);
//		pool = Executors.newSingleThreadExecutor();
		
		// ����������׽���
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("������������...");
			
			// ���տͻ��˵�����
			while (true) {
				Socket socket = serverSocket.accept();
				
				pool.execute(new FileUploadTask(socket));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
