package boardAndFlow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ga.Util;

public class DataflowList {
	private int 	flowNum = 0;
	private long 	hyperPeriod = 0;
	private long 	runtimeUnit = 0;
	private List<Dataflow> dataflows = new ArrayList<>();
	private Map<Integer, Set<Integer>> id2ConfilctId; 
/*
	Dataflow [] dataflows = new Dataflow[flowNum];
	for (int i = 0; i < dataflows.length; i++) {
		dataflows[i] = new Dataflow(ary, maxPeriod, payloadPercent);
		dataflows[i].setflowId(i+"");
	}
	Map<String, String> conflictMap= CheckConflict.getConflictMap(dataflows);
	Set<String>[] conflictTable = CheckConflict.getConflictFlowId(conflictMap, flowNum);
*/
	public DataflowList(int ary, int flowNum, int maxPeriod, int payloadPercent) {
		this.flowNum = flowNum;
		for (int i = 0; i < flowNum; i++) {
			dataflows.add(new Dataflow(ary, maxPeriod, payloadPercent));
		}
		List<Long> 	periodList = new ArrayList<>();
		List<Long >	runtimeList= new ArrayList<>();
		for (Dataflow dataflow : dataflows) {
			dataflow.setflowId(dataflows.indexOf(dataflow));
			periodList.add(dataflow.getPeriod());
			runtimeList.add(dataflow.getRunTime());
		}
		hyperPeriod = getHyperPeriod(periodList);
		runtimeUnit = getRunTimeUnit(runtimeList);
		Map<String, Set<Integer>> flow2Id= CheckConflict.getConflictMap(dataflows);
//		System.out.println("----------------------conflict map-----------------------");
//		System.out.println(flow2Id);
		id2ConfilctId = CheckConflict.getConflictFlowId(flow2Id, flowNum);
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
	
}
