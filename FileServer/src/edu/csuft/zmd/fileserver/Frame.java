package edu.csuft.zmd.fileserver;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import edu.csuft.zmd.fileclient.Client;

public class Frame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Frame() {
		setTitle("客户端");
		setSize(500,300);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel filename=new JLabel("请输入文件名:");
		JTextField inputname=new JTextField(50);

		
		JLabel fileroad=new JLabel("请输入文件路径:");
		JTextField inputfileroad=new JTextField(50);
		JButton input=new JButton("上传");
		input.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				Client client=new Client();
				client.start(inputname.getText(),inputfileroad.getText()+inputname.getText());
				
			}
		});
		add(filename);
		add(inputname);
		add(fileroad);
		add(inputfileroad);
		add(input);
		
		
	
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Frame frame=new Frame();
	}
	
}
