package ga;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import boardAndFlow.Dataflow;
import boardAndFlow.DataflowList;
/**
 * 
 * @author new
 * Fitness function set
 */
public class Fitness {
    //we supposed that the flow runtime cannot larger than the period
    public static int overlap (Individual individual , DataflowList dataflowList) {
    	int score = 0;
    	Map<Integer, Set<Integer>> 	conflictTable 	= dataflowList.getid2ConfilctId();
    	Map<Integer, Integer> 		phaseMap 		= individual.getPhaseMap();
    	Map<Integer, Set<Integer>> 	chromosome		= phase2Chromo(phaseMap, dataflowList);
        for (Iterator<Integer> iterator = chromosome.keySet().iterator(); iterator.hasNext();) {  
        	Integer key = iterator.next();
            Set<Integer> phaseSet = chromosome.get(key);
			if (phaseSet.size() > 1) {
				for (Integer flow : phaseSet) {
					Set<Integer> conflict = conflictTable.get(flow);
					if (!conflict.isEmpty()) {
						conflict.remove(flow);
						for (Integer id : phaseSet) {
							if (conflict.contains(id)) {
								score++;
							}
						}
					}
				}
			}
		}
		return score;
	}
    
    //the utilization of NOC need to be minimum
    public static int energyCost(Individual individual, DataflowList dataflowList) {
    	individual.setChromosome(phase2Chromo(individual.getPhaseMap(), dataflowList));
		int score = individual.getChromosome().size();
    	return score;
	}
    
  //the utilization of any time on the NOC can be stable
//    public static double varCost(Individual individual) {
//    	double avg = 0;
//    	int sum = 0;
//    	double score = 0;
//    	List<Integer> list = new ArrayList<>();
//    	Set<String> [] chromosome = individual.getChromosome();
//		for (Set<String>  string : chromosome) {
//			int temp = 0;
//			if (string==null) {
//				temp = 0;
//			}else if (string.contains(":")) {
//				temp = string.split(":").length;
//			}else {
//				temp = 1;
//			}
//			list.add(temp);
//		}
//		for (Integer integer : list) {
//			sum+=integer;
//		}
//		avg = sum / list.size();
//		for (Integer integer : list) {
//			score += Math.abs(avg-integer) * Math.abs(avg-integer);
//		}
//    	return score;
//	}
	private static Map<Integer, Set<Integer>> phase2Chromo (Map<Integer,Integer> phaseMap, DataflowList dataflowList) {
		List<Dataflow> dataflows = dataflowList.getDataflows();
		long hyperPeriod = dataflowList.getHyperPeriod();
		long runTimeUnit = dataflowList.getRuntimeUnit();
		Map<Integer, Set<Integer>> chromosome =  new HashMap<>();
		for (Dataflow dataflow : dataflows) {
			int runTime = (int) (dataflow.getRunTime() / runTimeUnit);
			int period = (int) (dataflow.getPeriod() / runTimeUnit);
			int times = (int) (hyperPeriod/dataflow.getPeriod() / runTimeUnit);
			int id = dataflow.getflowId();
			int phase = phaseMap.get(id);
			for (int i = 0; i < times; i++) {
				for (int j = 0; j < runTime; j++) {
					int cur = i * period + phase + j;
					if (!chromosome.containsKey(cur)) {
						chromosome.put(cur, new HashSet<>());
					}
					chromosome.get(cur).add(id);
				}
			}
		}		
		return chromosome;
	}

}
