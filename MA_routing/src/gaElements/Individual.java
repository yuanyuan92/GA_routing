package gaElements;

 import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import flowGenerate.Dataflow;
import flowGenerate.DataflowList;

public class Individual implements Comparable<Individual>{
	public double score = 0;
	public int energyCost = 0;
	public int overlapCost = 0;
	public int overlapFlow = 0;
	public double varCost = 0;	
	public Map<Integer ,Set<Integer>>			chromosome = new HashMap<>();
	public Map<Integer ,Integer> 	 			phaseMap = new HashMap<>();
	public int geneLength = 0;
	/**
	 * 
	 */
	public Individual() {
		super();
	}
	/**
		 * @param dataflows
		 */
		public Individual(DataflowList dataflowList) {
			super();
			long hyperPeriod = dataflowList.getHyperPeriod();
			long runTimeUnit = dataflowList.getRuntimeUnit();
			List<Dataflow> dataflows = dataflowList.getDataflows();
			this.geneLength = (int) (hyperPeriod/runTimeUnit);
			for (Dataflow dataflow : dataflows) {
				Integer id = dataflow.flowId;
				long runTime	= (dataflow.getRunTime()	/ runTimeUnit);
				long period		= (dataflow.getPeriod() 	/ runTimeUnit);
				//deadline constrain and runtime supposed to be less than period
				long phase = (long) ((period - runTime + 1) * Math.random());
				phaseMap.put(id, (int)phase);
			}
		}


	@Override
	public String toString() {
		return "Individual [score=" + score + ", energyCost=" + energyCost + ", overlapCost=" + overlapCost
				+ ", overlapFlow=" + overlapFlow + "]";
	}
	public String showChromo() {
		return "Individual [Chromo=" + chromosome;
	}
	
	public String showCost() {
		return "energyCost="+ energyCost + ", overlapCost=" + overlapCost+ ", score=" + score;
	}
	@Override
	public int compareTo(Individual individual) {
		return score > individual.score ? 1 : (score < individual.score ? -1 : 0);
	}	
}
