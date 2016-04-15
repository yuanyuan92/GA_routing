package flowGenerate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.Util;

public class DataflowList {
	private int 	flowNum = 0;
	private long 	hyperPeriod = 0;
	private long 	runtimeUnit = 0;
	private List<Dataflow> dataflows = new ArrayList<>();
	private Map<Integer, Set<Integer>> id2ConfilctId; 

	public DataflowList(int ary, int flowNum, int maxPeriod, int payloadPercent) {
		this.flowNum = flowNum;
		for (int i = 0; i < flowNum; i++) {
			dataflows.add(new Dataflow(ary, maxPeriod, payloadPercent));
		}
		List<Long> 	periodList = new ArrayList<>();
		List<Long >	runtimeList= new ArrayList<>();
		for (Dataflow dataflow : dataflows) {
			dataflow.flowId = (dataflows.indexOf(dataflow));
			periodList.add(dataflow.getPeriod());
			runtimeList.add(dataflow.getRunTime());
		}
		hyperPeriod = getHyperPeriod(periodList);
		runtimeUnit = getRunTimeUnit(runtimeList);
		Map<String, Set<Integer>> flow2Id= getConflictMap(dataflows);
//		System.out.println("----------------------conflict map-----------------------");
//		System.out.println(flow2Id);
		id2ConfilctId = getConflictFlowId(flow2Id, flowNum);
//		System.out.println("----------------------conflict id-------------------------");
//		System.out.println(id2ConfilctId);
	}
	

	@Override
	public String toString() {
		System.out.println("----------------------dataflow list info---------------------------");
		for (Dataflow dataflow : dataflows) {
			System.out.println(dataflow);
		}
		System.out.println(id2ConfilctId);
		return "DataflowList [flowNum=" + flowNum + ", hyperPeriod=" + hyperPeriod + ", runtimeUnit=" + runtimeUnit
				+ "]";
	}


	public int getFlowNum() {
		return flowNum;
	}
	public Map<Integer, Set<Integer>> getid2ConfilctId() {
		return id2ConfilctId;
	}
	public long getHyperPeriod() {
		return hyperPeriod;
	}
	public long getRuntimeUnit() {
		return runtimeUnit;
	}
	public List<Dataflow> getDataflows() {
		return dataflows;
	}
	
	private static long getHyperPeriod(List<Long> periodList) {
		long result = 1;
		for (Iterator<Long> iterator = periodList.iterator(); iterator.hasNext();) {
			Long integer = (Long) iterator.next();
			result = Util.lcm(result, integer);
		}
		return result;
	}
	private static int getRunTimeUnit(List<Long> runtimeUnit) {
		long result = runtimeUnit.get(0);
		for (Iterator<Long> iterator = runtimeUnit.iterator(); iterator.hasNext();) {
			Long long1 = (Long) iterator.next();
			result = Util.gcd(result, long1);
		}
		return (int)result;
	}	
	/**
	 * get the unit link and the relevant flow id
	 * @param dataflows 
	 * @return
	 */
	private static Map<String, Set<Integer>> getConflictMap(List<Dataflow> dataflows) {
		Map<String, Set<Integer>> pathAndFlowId = new HashMap<>();
		for (int i = 0; i < dataflows.size(); i++) {
			List<Integer> routeNode = dataflows.get(i).getRouteNode();
			Integer flowId = dataflows.get(i).flowId;
			//get joint points' path
			for (int j = 0; j < routeNode.size()-1; j++) {
				String path = routeNode.get(j)+"->"+routeNode.get(j+1);
				if (!pathAndFlowId.containsKey(path)) {
					Set<Integer> set = new HashSet<>();
					set.add(flowId);
					pathAndFlowId.put(path, set);
				}else {
					pathAndFlowId.get(path).add(flowId);
				}
			}
		}
		return pathAndFlowId;
	}
	/**
	 * it can get conflict flows by giving a flow id. example:    
	 * id : [conflict id set]
	 * @param pathAndFlowId
	 * @param flowNum
	 * @return
	 */
	private static Map<Integer, Set<Integer>> getConflictFlowId(Map<String, Set<Integer>> pathAndFlowId,int flowNum) {
		Map<Integer, Set<Integer>> idAndConflictId = new HashMap<>();		
		Set<String> pathSet = pathAndFlowId.keySet();
        for (Iterator<String> iterator = pathSet.iterator(); iterator.hasNext();) {  
        	String perPath = iterator.next();
            Set<Integer> flowId = pathAndFlowId.get(perPath);
            for (Integer id : flowId) {
            	if (!idAndConflictId.containsKey(id)) {
            		idAndConflictId.put(id, flowId);
				}else{
					idAndConflictId.get(id).addAll(flowId);
				}
            }

        }		
        // remove the conflict id of the key. example: id 0's conflict id set can not include id 0;
        for (Integer id : idAndConflictId.keySet()) {
			idAndConflictId.get(id).remove(id);
		}
		return idAndConflictId;
	}	
}
