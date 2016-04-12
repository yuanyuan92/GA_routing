package ga;

 import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import boardAndFlow.Dataflow;
import boardAndFlow.DataflowList;

public class Individual implements Comparable<Individual>{
	private Map<Integer ,Set<Integer>>  chromosome = new HashMap<>();
	private Map<Integer ,Integer>  phaseMap = new HashMap<>();
	@SuppressWarnings("unused")
	private int geneLength = 0;
	private double score = 0;
	@SuppressWarnings("unused")
	private double varCost = 0;	
	private int overlapCost = 0;
	private int energyCost = 0;
	
	
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
				Integer id = dataflow.getflowId();
				long runTime	= (dataflow.getRunTime() / runTimeUnit);
				long period		= (dataflow.getPeriod() / runTimeUnit);
				//deadline constrain and runtime supposed to be less than period
				long phase = (long) ((period - runTime + 1) * Math.random());
				phaseMap.put(id, (int)phase);
			}
		}


	public Map<Integer, Set<Integer>> getChromosome() {
		return chromosome;
	}

	public void setChromosome(Map<Integer, Set<Integer>> chromosome) {
		this.chromosome = chromosome;
	}


	public Map<Integer, Integer> getPhaseMap() {
		return phaseMap;
	}


	public void setPhaseMap(Map<Integer, Integer> phaseMap) {
		this.phaseMap = phaseMap;
	}


	public int getEnergyCost() {
		return energyCost;
	}

	public void setEnergyCost(int energyCost) {
		this.energyCost = energyCost;
	}

	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Individual [phaseMap=" + phaseMap + ", score=" + score + "]";
	}
	public String showChromo() {
		return "Individual [Chromo=" + chromosome;
	}
	
	public String showCost() {
		return "energyCost="+ energyCost + ", overlapCost=" + overlapCost+ ", score=" + score;
	}
	@Override
	public int compareTo(Individual individual) {
		return overlapCost > individual.score ? 1 : (score < individual.score ? -1 : 0);
	}
	
}
