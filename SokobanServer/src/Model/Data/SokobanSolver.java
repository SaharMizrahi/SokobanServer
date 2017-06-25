package Model.Data;

import java.util.List;

import Model.Data.Level;
import Model.Data.Level2D;
import StripsLib.PlanAction;
import StripsLib.Strips;

public class SokobanSolver
{
	private SokobanSolution solution;
	public SokobanSolution solve2DLevel(Level level)
	{
		if(level instanceof Level2D)
		{
			PlannableSokoban ps=new PlannableSokoban((Level2D) level);
			Strips strips=new Strips();
			List<PlanAction> list=strips.plan(ps);
			solution=new SokobanSolution(list);
		}
		
		return solution;
		
	}

}
