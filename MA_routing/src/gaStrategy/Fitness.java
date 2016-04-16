package gaStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import flowGenerate.Dataflow;
import flowGenerate.DataflowList;
import gaElements.Individual;
/**
 * 
 * @author new
 * Fitness function set
 */
public class Fitness {
    //we supposed that the flow runtime cannot larger than the period
    public static Individual getAllScore (Individual individual , DataflowList dataflowList) {
    	Map<Integer, Set<Integer>> 	id2ConfilctId 	= dataflowList.getid2ConfilctId();
    	Map<Integer, Integer> 		phaseMap 		= individual.phaseMap;
    	Map<Integer, Set<Integer>> 	chromosome		= phase2Chromo(phaseMap, dataflowList);
    	Map<Integer, Map<Integer, Integer>> overlapInfo = new HashMap<>();
    	int score = 0;
    	for (Iterator<Integer> iterator = chromosome.keySet().iterator(); iterator.hasNext();) {  
        	Integer slot = iterator.next();
        	//get the current time's flow set
            Set<Integer> phaseSet = chromosome.get(slot);
			if (!phaseSet.isEmpty()) {
			//get confict flow
				for (Integer id : phaseSet) {
					Set<Integer> conflictSet = id2ConfilctId.get(id);
					// if the flow has conflict set
					if (!conflictSet.isEmpty()) {
						for (Integer coexistId : phaseSet) {
							// for every coexist id if coexist id is the conflict id
							if (conflictSet.contains(coexistId)) {
								score++;
								// if overlap info do not include the flow id, then add the id and go through
								if (!overlapInfo.containsKey(id)) {
									overlapInfo.put(id, new HashMap<>());
								}
								// if this overlap id map do not include this conflict id, then add the conflict and go through
								if (!overlapInfo.get(id).containsKey(coexistId)) {
									overlapInfo.get(id).put(coexistId, 1);
								}else{
									int times = overlapInfo.get(id).get(coexistId);
									overlapInfo.get(id).put(coexistId, times+1);
								}
							}
						}
						
					}
				}
			}
		}
    	int overlapFlowNum = overlapInfo.size();
    	int overlapTimes = 0;
    	for (Integer id : overlapInfo.keySet()) {
			Map<Integer, Integer> conflictIdAndTimes = overlapInfo.get(id);
			for (Integer conflictId : conflictIdAndTimes.keySet()) {
				overlapTimes += conflictIdAndTimes.get(conflictId);
			}
		}
    	individual.score = score;
    	individual.overlapFlow = overlapFlowNum;
    	individual.overlapCost = overlapTimes;
        return individual;
	}
	
    //we supposed that the flow runtime cannot larger than the period
//    private static int overlap (Individual individual , DataflowList dataflowList) {
//    	int score = 0;
//    	Map<Integer, Set<Integer>> 	conflictTable 	= dataflowList.getid2ConfilctId();
//    	Map<Integer, Integer> 		phaseMap 		= individual.phaseMap;
//    	Map<Integer, Set<Integer>> 	chromosome		= phase2Chromo(phaseMap, dataflowList);
//        for (Iterator<Integer> iterator = chromosome.keySet().iterator(); iterator.hasNext();) {  
//        	Integer slot = iterator.next();
//        	//get the current time's flow set
//            Set<Integer> phaseSet = chromosome.get(slot);
//			if (!phaseSet.isEmpty()) {
//				for (Integer id : phaseSet) {
//					Set<Integer> conflictSet = conflictTable.get(id);
//					if (!conflictSet.isEmpty()) {
//						for (Integer conflictId : phaseSet) {
//							if (conflictSet.contains(conflictId)) {
//								score++;
//							}
//						}
//					}
//				}
//			}
//		}
//		return score;
//	}
    
    //the utilization of NOC need to be minimum
    public static int energyCost(Individual individual, DataflowList dataflowList) {
    	individual.chromosome = (phase2Chromo(individual.phaseMap, dataflowList));
		int score = individual.chromosome.size();
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
			int id = dataflow.flowId;
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
