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
 * 定义一个交给线程/线程池处理的任务【文件上传】
 * 
 * @author wtao
 *
 */
public class FileUploadTask implements Runnable {

	/**
	 * 套接字
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
		// 使用文件的摘要值（散列值 - SHA-256）做文件名
		String fileName = "";

		// 具体的上传操作

		// 长度可变的内存数组
		ByteArrayOutputStream dataUpload = new ByteArrayOutputStream();

		// 接收数据
		try (InputStream in = socket.getInputStream()) {
			byte[] buf = new byte[1024 * 8];
			int size;
			while (-1 != (size = in.read(buf))) {
				dataUpload.write(buf, 0, size);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获得了所有数据
		byte[] data = dataUpload.toByteArray();

		// 获得文件的散列值
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
			System.out.println("接收完成：" + fileName);
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

		// 保存到数据库

		// 获得会话工厂（数据库连接池）
		SqlSessionFactory factory;
		try {
			factory = new SqlSessionFactoryBuilder().build(new FileReader("config.xml"));
			// 获得一个会话（连接),设置为自动提交事务
			SqlSession session = factory.openSession(true);

			// 获得一个mapper（反射）
			@SuppressWarnings("unused")
			FileMapper mapper = session.getMapper(FileMapper.class);

			for (File2 f : filelist) {
				try {
					mapper.add(f);
				} catch (PersistenceException e) {
					// TODO 自动生成的 catch 块
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