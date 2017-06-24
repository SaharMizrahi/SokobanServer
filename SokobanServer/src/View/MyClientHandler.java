package View;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import Model.LevelCompressorAndGenerator;
import Model.SokobanSolution;
import Model.SokobanSolver;
import Model.Data.Item;
import Model.Data.Level;
import Model.Data.Level2D;
import Model.Data.LevelLoaderFactory;
/** A Class that implements ClientHandler interface
 * it has String that refers to the client command, one string that refers the message to the user
 * 
 * 
 * @author Sahar Mizrahi and Gal Ezra
 *
 */
public class MyClientHandler extends Observable implements ClientHandler {

	LevelCompressorAndGenerator cg;

	private String solveLevel(Level level)
	{
		SokobanSolver solver=new SokobanSolver();
		SokobanSolution solution=null;
		if(level instanceof Level2D)
		{
			solution=solver.solve2DLevel((Level2D) level);
			return solution.getCompresedSolution();
		}

		return null;
	}
	private String getSolutionFromDB(String levelName)
	{
		return "unDone";
		
	}
	/**
	 * The codes of the method from ClientHandler interface
	 */
	@Override
	public void handleClient(InputStream in, OutputStream out) {
		// TODO Auto-generated method stub
		Level lev=null;
		BufferedReader br=new BufferedReader(new InputStreamReader(in));
		PrintWriter pr=new PrintWriter(out);
		String s="",temp="";
		try {
			s=br.readLine();
			lev=cg.generate(s);
			pr.println(solveLevel(lev));
			pr.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}



	
		


	/**
	 * Default constructor
	 */
	public MyClientHandler() {
		super();
		// TODO Auto-generated constructor stub
		cg=new LevelCompressorAndGenerator();
	}







}
