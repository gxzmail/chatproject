package chatproject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ChatServer {
	
	Logger logger = LogManager.getLogger(ChatServer.class);
	private List<Client> list = new ArrayList<>();
	private ServerSocket ss;
	 
	public static void main(String[] args) throws Exception {
		new ChatServer(9910).start(); 
		

	}
	
	public ChatServer(int port) throws Exception {
		ss = new ServerSocket(port);
	}
	public void start() throws Exception {
		while (true) {
			logger.info("server start");
			Socket s = ss.accept(); //走分支
			logger.info("a new socket is connected!");
			Client client = new Client(s);
			removeThread removeThread = new removeThread();
			new Thread(client).start();
			list.add(client);	
			new Thread(removeThread).start();
		}
		
	}
	
	private class Client implements Runnable {
		
		Logger logger = LogManager.getLogger(Client.class);
		DataOutputStream dos = null;
		private DataInputStream dis = null;
		private Socket s;
		public Client(Socket s) throws IOException {
			this.setS(s);
			dos = new DataOutputStream(s.getOutputStream());
			setDis(new DataInputStream(s.getInputStream()));
		}
		
		public void run() {
			
				try {
					String rev = dis.readUTF();
					logger.info("the rev string is :" + rev);
					send(rev);
				} catch (IOException e) {
					
				}
				

			
		}
		public void send(String rev) throws IOException {
			Iterator<Client> ite = list.iterator();
			while (ite.hasNext()) {
				ite.next().dos.writeUTF(rev);
				logger.info("the send string is " + rev);
				}
			
		}

		public Socket getS() {
			return s;
		}

		public void setS(Socket s) {
			this.s = s;
		}

		public DataInputStream getDis() {
			return dis;
		}

		public void setDis(DataInputStream dis) {
			this.dis = dis;
		}
		
	}
	
	private class removeThread implements Runnable {
		Logger logger = LogManager.getLogger(removeThread.class);
		public void run() {
			if (list.size() > 0 ) {
				Iterator<Client> ite = list.iterator();
				while (ite.hasNext()) {
					if (ite.next().getS().isClosed()) {
						ite.remove();
						logger.info("delete a socket");
					}
					
				}
			}
		}
	}

	
} 

