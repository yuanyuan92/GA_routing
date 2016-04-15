package ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import boardAndFlow.Dataflow;
import boardAndFlow.DataflowList;

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
		Collections.sort(nextGeneration.getIndividuals());
		// cross
		nextGeneration.setIndividuals(crossover(nextGeneration.getIndividuals(), dataflowList));
		// mutate
		nextGeneration.setIndividuals(mutation(nextGeneration.getIndividuals(), dataflowList, 0.5));
		// mark after evolution
		mark(nextGeneration.getIndividuals(),dataflowList);
		Collections.sort(nextGeneration.getIndividuals());
		// select scale
		nextGeneration.setIndividuals(select(nextGeneration.getIndividuals(), oldGeneration.getScale()));
		return nextGeneration;
	}
	
	public static void mark(List<Individual> individuals, DataflowList dataflowList) {
		for (Individual individual : individuals) {
			int overlap = Fitness.overlap(individual, dataflowList);
			int energy 	= Fitness.energyCost(individual, dataflowList);
			int score = overlap + 0 * energy;
			individual.setScore(score);
			individual.setEnergyCost(energy);
			individual.setOverlapCost(overlap);
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
			if (Util.getPosibility(individuals.size(), idx)) {
				dancingPool.add(individual);
			}
		}
		// crossover begin
		for (Individual husband : dancingPool) {
			Individual wife = dancingPool.get((int) (Math.random() * dancingPool.size()));
			Individual baby = husband;
			baby.setPhaseMap((getBabyPhase(husband, wife, dataflowList)));
			individuals.add(baby);
		}
		return individuals;
	}
	
	private static Map<Integer, Integer> getBabyPhase(Individual husband,Individual wife, DataflowList dataflowList) {
		Map<Integer, Integer> chldPhase = new HashMap<>();
		Map<Integer, Integer> malePhase = husband.getPhaseMap();
		Map<Integer, Integer> femaPhase = wife.getPhaseMap();
		List<Dataflow> dataflows = dataflowList.getDataflows();
		// get the phase of baby
		for (Dataflow dataflow : dataflows) {
			int id = dataflow.getflowId();
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
				Map<Integer, Integer> phaseMap = individual.getPhaseMap();
				// select the mutation gene
				int mutationGene =  (int) (Math.random() * flowNum);
				int mutationLocation = phaseMap.get(mutationGene);
				// remove the current gene for mutation
				phaseMap.remove(mutationGene);
				// mutation strategy
				long deadline = dataflows.get(mutationGene).getDeadline();
				int newLocation =  (int) ((mutationLocation + Math.random() * deadline) % deadline);
				phaseMap.put(mutationGene, newLocation);
				individual.setPhaseMap(phaseMap);
			}
		}
		return individuals;
	}
	
	@SuppressWarnings("unused")
	private static Individual search(Individual individual, DataflowList dataflowList) {
		List<Dataflow> dataflows = dataflowList.getDataflows();
		Map<Integer, Integer> phaseMap = individual.getPhaseMap();
		int flowNum = dataflows.size();
		int id = (int) Math.random() * flowNum;
		int phase = phaseMap.get(id);
		int deadline = (int) (dataflows.get(id).getPeriod() - dataflows.get(id).getRunTime());
		while (phase <= deadline) {
			
			
		}
//		changeChromosome(phaseMap, dataflows, hyperPeriod, runTimeUnit);
		return individual;
	}
	

}
