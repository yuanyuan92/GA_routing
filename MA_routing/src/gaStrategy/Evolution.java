package gaStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flowGenerate.Dataflow;
import flowGenerate.DataflowList;
import gaElements.Individual;
import gaElements.Population;
import util.Util;

public class Evolution {
	/**
	 * @param dataflowList
	 * @param evolutionTimes
	 */
	public Evolution(Population population, DataflowList dataflowList) {
		super();
		newGeneration(population, dataflowList);
	}
	public Population newGeneration(Population oldGeneration , DataflowList dataflowList) {
		Population nextGeneration = oldGeneration;
		// initial marks
		mark(nextGeneration.individuals,dataflowList);
		Collections.sort(nextGeneration.individuals);
		// cross
		nextGeneration.individuals = (crossover(nextGeneration.individuals, dataflowList));
		// mutate
		nextGeneration.individuals = (mutation(nextGeneration.individuals, dataflowList, 0.5));
		// mark after evolution
		mark(nextGeneration.individuals,dataflowList);
		Collections.sort(nextGeneration.individuals);
		// select scale
		nextGeneration.individuals = (select(nextGeneration.individuals, oldGeneration.scale));
		return nextGeneration;
	}
	
	public static void mark(List<Individual> individuals, DataflowList dataflowList) {
		for (Individual individual : individuals) {
			individual = Fitness.getAllScore(individual, dataflowList);
			individual.score = individual.overlapFlow;

		}
		//sort for minimum overlap
	}
	
	private static List<Individual> select(List<Individual> individuals, int scale) {
		Collections.sort(individuals);
		List<Individual> next = new ArrayList<>();
		for (int i = 0; i < scale; i++) {
			next.add(individuals.get(i));
		}
		return next;
	}

	
	private static List<Individual> crossover(List<Individual> individuals, DataflowList dataflowList) {
		List<Individual> dancingPool = new ArrayList<>();
		for (Individual individual : individuals) {
			int idx = individuals.indexOf(individual);
			if (getPosibility(individuals.size(), idx)) {
				dancingPool.add(individual);
			}
		}
		// crossover begin
		for (Individual husband : dancingPool) {
			Individual wife = dancingPool.get((int) (Math.random() * dancingPool.size()));
			Individual baby = husband;
			baby.phaseMap = ((getBabyPhase(husband, wife, dataflowList)));
			individuals.add(baby);
		}
		return individuals;
	}
	
	private static Map<Integer, Integer> getBabyPhase(Individual husband,Individual wife, DataflowList dataflowList) {
		Map<Integer, Integer> chldPhase = new HashMap<>();
		Map<Integer, Integer> malePhase = husband.phaseMap;
		Map<Integer, Integer> femaPhase = wife.phaseMap;
		List<Dataflow> dataflows = dataflowList.getDataflows();
		// get the phase of baby
		for (Dataflow dataflow : dataflows) {
			int id = dataflow.flowId;
			int mLocation = malePhase.get(id);
			int fLocation = femaPhase.get(id);
			int cLocation = Math.random() < 0.5 ? mLocation : fLocation;
			chldPhase.put(id, cLocation);
		}
		return chldPhase;
	}
	
	private static List<Individual> mutation(List<Individual> individuals, DataflowList dataflowList,double percnt) {
		List<Dataflow> dataflows = dataflowList.getDataflows();
		for (Individual individual : individuals) {
			if (Math.random() <= percnt) {
				int flowNum = dataflows.size();
				Map<Integer, Integer> phaseMap = individual.phaseMap;
				// select the mutation gene
				int mutationGene =  (int) (Math.random() * flowNum);
				int mutationLocation = phaseMap.get(mutationGene);
				// remove the current gene for mutation
				phaseMap.remove(mutationGene);
				// mutation strategy
				long deadline = dataflows.get(mutationGene).getDeadline();
				int newLocation =  (int) ((mutationLocation + Math.random() * deadline) % deadline);
				phaseMap.put(mutationGene, newLocation);
				individual.phaseMap = (phaseMap);
			}
		}
		return individuals;
	}
	
	@SuppressWarnings("unused")
	private static Individual search(Individual individual, DataflowList dataflowList) {
		List<Dataflow> dataflows = dataflowList.getDataflows();
		Map<Integer, Integer> phaseMap = individual.phaseMap;
		int flowNum = dataflows.size();
		int id = (int) Math.random() * flowNum;
		int phase = phaseMap.get(id);
		int deadline = (int) (dataflows.get(id).getPeriod() - dataflows.get(id).getRunTime());
		while (phase <= deadline) {
			
			
		}
//		changeChromosome(phaseMap, dataflows, hyperPeriod, runTimeUnit);
		return individual;
	}
	public static boolean getPosibility( int scale , int idx) {
		// individual is good enough ? the better individual has more posibility to crossover
		double propablity = ((double)scale - idx) / scale;
		if (Math.random() < propablity) {
			return true;
		}else {
			return false;
		}		
	}	

}
