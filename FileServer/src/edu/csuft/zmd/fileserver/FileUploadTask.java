package edu.csuft.zmd.fileserver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.Socket;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * ����һ�������߳�/�̳߳ش���������ļ��ϴ���
 * 
 * @author wtao
 *
 */
public class FileUploadTask implements Runnable {

	/**
	 * �׽���
	 */
	Socket socket;
	List<File2> filelist;

	public FileUploadTask(Socket socket) {
		this.socket = socket;
		filelist = new ArrayList<>();
	}

	@Override
	public void run() {
		String path = "d:/files";
		// ʹ���ļ���ժҪֵ��ɢ��ֵ - SHA-256�����ļ���
		String fileName = "";

		// ������ϴ�����

		// ���ȿɱ���ڴ�����
		ByteArrayOutputStream dataUpload = new ByteArrayOutputStream();

		// ��������
		try (InputStream in = socket.getInputStream()) {
			byte[] buf = new byte[1024 * 8];
			int size;
			while (-1 != (size = in.read(buf))) {
				dataUpload.write(buf, 0, size);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// �������������
		byte[] data = dataUpload.toByteArray();

		// ����ļ���ɢ��ֵ
		try {
			byte[] hash = MessageDigest.getInstance("SHA-256").digest(data);

			fileName = new BigInteger(1, hash).toString(16);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		File file = new File(path, fileName);

		try (FileOutputStream out = new FileOutputStream(file)) {

			out.write(data);
			out.close();
			System.out.println("������ɣ�" + fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		File2 file2 = new File2();
		file2.hashcode = fileName;
		file2.name = fileName;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(file.lastModified());
		file2.time = sdf.format(cal.getTime());
		filelist.add(file2);

		// ���浽���ݿ�

		// ��ûỰ���������ݿ����ӳأ�
		SqlSessionFactory factory;
		try {
			factory = new SqlSessionFactoryBuilder().build(new FileReader("config.xml"));
			// ���һ���Ự������),����Ϊ�Զ��ύ����
			SqlSession session = factory.openSession(true);

			// ���һ��mapper�����䣩
			@SuppressWarnings("unused")
			FileMapper mapper = session.getMapper(FileMapper.class);

			for (File2 f : filelist) {
				try {
					mapper.add(f);
				} catch (PersistenceException e) {
					// TODO �Զ����ɵ� catch ��
				}

			}
			session.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class FilegetName implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Socket socket;
		
	}

}