package View;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;



public class MyServer implements ViewInterface {
	
	private MyClientHandler ch;
	private int port;
	private boolean stop=false;
	private ThreadPoolExecutor pool;
	ServerSocket server;





	public void shutDownServer()
	{
		this.stop=true;
		pool.shutdown();

		
	}
	/**Starts the communication thread
	 * 
	 * @throws IOException
	 */
	@Override
	public boolean runServer() {
		// TODO Auto-generated method stub

			
			try {
				server = new ServerSocket(port);
				System.out.println("server is running");
				Thread serverThread=new Thread(()->{
					while(!stop)
					{
						try {
							Socket aClient=server.accept();
							pool.execute(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub

									try {
										ch.handleClient(aClient.getInputStream(),aClient.getOutputStream());
										aClient.close();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}
							});
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});


				serverThread.start();
				return true;

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return false;
			}
			
		


	}


	/**
	 * default constructor
	 */
	public MyServer() {
		super();
		// TODO Auto-generated constructor stub
		BlockingQueue<Runnable> bq=new ArrayBlockingQueue<>(10);
		ch=new MyClientHandler();
		this.port=8888;
		pool=new ThreadPoolExecutor(1, 5, 2, TimeUnit.SECONDS, bq);
	}

	

	/**
	 * 
	 * @return the server's port
	 */
	public int getPort() {
		return port;
	}
	
	
}



