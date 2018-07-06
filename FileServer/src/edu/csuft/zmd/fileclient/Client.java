package edu.csuft.zmd.fileclient;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * �ͻ��˶���
 * 
 * @author wtao��wtao.jyg@qq.com��
 *
 */
public class Client {
	
	/**
	 * �׽��֣���Դ������ͨ���׽��ֽ�����������
	 * ���ͣ����׽��ֻ���������ִ��д����
	 * ���գ����׽��ֻ����������ִ�ж�����
	 */
	Socket socket;
	
	/**
	 * �������ĵ�ַ
	 */
	String serverAddress = "127.0.0.1";
	
	/**
	 * �������Ķ˿ڣ�0 <= port <= 65535, ���� 2^16 ��
	 * 1024 ���ڵı���
	 */
	int serverPort = 9000;
	
	/**
	 * �ͻ�������
	 */
	public void start(String string,String file) {
		try {
			socket = new Socket(serverAddress, serverPort);
			System.out.println("�ͻ��˳ɹ������ӵ��˷�����");
			// ѡ��һ���ļ���ִ���ļ����ϴ�
			
			// GUI\������
//			Scanner sc = new Scanner(System.in);
//			System.out.print("������Ҫ�ϴ����ļ�����");
//			String string=sc.next();
//			System.out.println("�������ļ�·����");
//			String file = sc.next()+string;
//			sc.close();
			
			// ����: ���׽��ֻ�á����������������
			OutputStream out = socket.getOutputStream();
//			
			// ���ļ���ȡ����
//			FileInputStream in = new FileInputStream(string);
			@SuppressWarnings("resource")
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
			
			byte[] buf = new byte[1024 * 8];
			int size;
			
			while (-1 != (size = in.read(buf))) {
				// ����
				out.write(buf, 0, size);
				// ˢ�»�����
				out.flush();
			}
			
			// �ѳɹ�����
			System.out.println("�������");
			
			socket.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// ʼ��ִ��
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
