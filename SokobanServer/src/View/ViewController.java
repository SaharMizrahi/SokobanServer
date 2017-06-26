package View;

import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import ViewModel.ViewModelInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ViewController implements Initializable, ViewInterface,Observer {
	private ViewModelInterface viewModel;
	private MyClientHandler ch;
	private int port;
	private boolean stop=true;
	private ThreadPoolExecutor pool;
	private ServerSocket server;
	private Socket aClient=null;
	private Thread serverthread;
	/***GUI Arguments*****/
	@FXML
	private Button serverButton;
	@FXML
	private Button shutDownButton;
	@FXML Circle stateFlag;
	
	/*******************/
	/***GUI Methods*****/
	/*******************/
	@FXML
	public void runServer()
	{

		System.out.println("server is running");
		stop=false;
		stateFlag.setFill(Color.LIMEGREEN);
		serverButton.setDisable(true);
		shutDownButton.setDisable(false);
		try {
			if(server!=null)
			{
				if(server.isClosed())
					server=new ServerSocket(port);
			}
			else
			{
				server=new ServerSocket(port);

			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		serverthread=new Thread(()->{
			
			
			while(!stop)
			{
				try {
					aClient=server.accept();
					System.out.println("client connected");
					pool.execute(()->{
						try {
							ch=new MyClientHandler(aClient.getInputStream(),aClient.getOutputStream());
							viewModel.addClientHandler(ch);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		serverthread.start();
		/*stop=false;
		serverButton.setDisable(true);
		shutDownButton.setDisable(false);

		
		
		System.out.println("server is running");
		stateFlag.setFill(Color.LIMEGREEN);

		Thread serverThread=new Thread(()->{
			while(!stop)
			{
				pool.execute(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub

						try {
							if(server==null)
							{
								server = new ServerSocket(port);

							}
							aClient=new Socket();
							aClient=server.accept();

							ch.handleClient(aClient.getInputStream(),aClient.getOutputStream());
							
							aClient.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
			}
		});

		System.out.println(this.stop);
		serverThread.start();*/
		
	

	}
	@FXML
	public void shutDownServer()
	{
		this.stop=true;
		serverthread.interrupt();
		System.out.println("server is closed");
		serverButton.setDisable(false);
		shutDownButton.setDisable(true);
		stateFlag.setFill(Color.RED);
		if(pool!=null)
			pool.shutdown();
		
			


		
	}
	
	/******************************/
	/*****getters and setters******/
	/******************************/
	public void setViewModel(ViewModelInterface vm)
	{
		this.viewModel=vm;
	}
	
	/*****************************/
	/*******Private Methods*******/
	/*****************************/

	private void restart()
	{
		BlockingQueue<Runnable> bq=new ArrayBlockingQueue<>(10);
		this.port=8888;
		pool=new ThreadPoolExecutor(1, 5, 2, TimeUnit.SECONDS, bq);
		serverButton.setDisable(false);
		shutDownButton.setDisable(true);
		stateFlag.setFill(Color.RED);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		restart();

		
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	

	


	
}
