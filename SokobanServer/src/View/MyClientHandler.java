package View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Observable;

import Model.SokobanSolution;
import Model.SokobanSolver;
import Model.Data.Level2D;
import Model.Data.LevelLoaderFactory;
import SearchLib.Action;
/** A Class that implements ClientHandler interface
 * it has String that refers to the client command, one string that refers the message to the user
 * 
 * 
 * @author Sahar Mizrahi and Gal Ezra
 *
 */
public class MyClientHandler extends Observable implements ClientHandler {

	
	private String solveLevel(String level)
	{

		Level2D solveaAbleLevel=null;
		SokobanSolver solver=new SokobanSolver();
		SokobanSolution solution=null;
		LevelLoaderFactory llf=new LevelLoaderFactory();
		String[] levelName=level.split(".");
		try {
			solveaAbleLevel=(Level2D) llf.getLLHM().get(levelName[1]).create().loadLevel(new FileInputStream("./resources/Levels/"+level));
			solution=solver.solveLevel(solveaAbleLevel);
			if(solution!=null)
			{
				return solution.getCompresedSolution();
			}
			else
				return "block";
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "block";
		
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


		BufferedReader br=new BufferedReader(new InputStreamReader(in));
		PrintWriter pr=new PrintWriter(out);
		String s="",temp="";
		try {
			s=br.readLine();
			pr.println(s);
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

	}







}
