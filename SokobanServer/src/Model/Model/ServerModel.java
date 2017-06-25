package Model.Model;

import java.util.Observable;

import Model.LevelCompressorAndGenerator;
import Model.Data.Level;
import Model.Data.SokobanSolution;
import Model.Data.SokobanSolver;

public class ServerModel  extends Observable implements ModelInterface   {
	private String solution;
	private LevelCompressorAndGenerator lcag;
	/***********************/
	/***implemnted methods**/
	/***********************/

	@Override
	public String getLevelSolution(String levelDescription) {
		// TODO Auto-generated method stub
		String solution="";
		solution=askForSolution(levelDescription);//ask from web service
		if(solution!=null)
			return solution;
		else
		{
			solution=searchSolution(levelDescription);//solve by himself
			if(solution!=null)
				return solution;
			else
				return "block";
		}

		
	}
	@Override
	public void postRecord(String record) {
		// TODO Auto-generated method stub
		
	}
	/***********************/
	/**private methids******/
	/***********************/

	private String askForSolution(String levelDescription) {
		// TODO Auto-generated method stub
		//open tcp socket with web service
		//send "get <levelDescription> "
		//return answer
		//close socket
		return "";

	}

	
	private String searchSolution(String levelDescription) {
		// TODO Auto-generated method stub
		SokobanSolver solver=new SokobanSolver();
		Level level=lcag.generate(levelDescription);
		SokobanSolution solution=solver.solve2DLevel(level);
		
		
		return solution.getCompresedSolution();
	}



	/***********************/
	/***getters and setters*/
	/***********************/


	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
		notifyObservers();
	}



}
