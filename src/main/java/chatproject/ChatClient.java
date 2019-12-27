package chatproject;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class ChatClient extends Frame {
	Logger logger = LogManager.getLogger(ChatClient.class);
	
	TextField tfTxt = new TextField();
	TextArea taContent = new TextArea();
	String hostIp;
	int port;
	Socket s;
	RecievThread rThread = null;
	DataOutputStream dos = null;
	DataInputStream dis = null;
	
	public static void main(String[] args) throws Exception {
		new ChatClient("127.0.0.1", 9910).launchFrame(); 
	}
	public ChatClient(String hostIp, int port) throws Exception {
		this.hostIp = hostIp;
		this.port = port;
		
	}
 
	public void launchFrame() throws Exception {
		setLocation(400, 300);
		this.setSize(300, 300);
		add(tfTxt, BorderLayout.SOUTH);
		add(taContent, BorderLayout.NORTH);
		pack();
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}
			
		});
		tfTxt.addActionListener(new TFListener());
		setVisible(true);
		connect();
		new Thread(rThread).start();
		
		
	}
	private void connect() throws Exception  {
		logger.info("begin to connect host!");
		s = new Socket(hostIp, port);
		dis = new DataInputStream(s.getInputStream());
		dos = new DataOutputStream(s.getOutputStream());
		
	}
	
	private class TFListener implements ActionListener  {

		public void actionPerformed(ActionEvent e) {
			String s = tfTxt.getText().trim();
			taContent.setText(s);
			tfTxt.setText("");
			try {
				dos.writeUTF(s);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	private class RecievThread implements Runnable {
		Logger logger = LogManager.getLogger(RecievThread.class);
		public void run() {
			try {
				String recv = dis.readUTF();
				logger.info("the client rev is " + recv);
				taContent.setText(taContent.getText() + recv + '\n');
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
