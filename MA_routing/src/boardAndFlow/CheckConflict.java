package boardAndFlow;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CheckConflict {
	/**
	 * get the unit link and the relevant flow id
	 * @param dataflows 
	 * @return
	 */
	public static Map<String, Set<Integer>> getConflictMap(List<Dataflow> dataflows) {
		Map<String, Set<Integer>> pathAndFlowId = new HashMap<>();
		for (int i = 0; i < dataflows.size(); i++) {
			List<Integer> routeNode = dataflows.get(i).getRouteNode();
			Integer flowId = dataflows.get(i).getflowId();
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
	public static Map<Integer, Set<Integer>> getConflictFlowId(Map<String, Set<Integer>> pathAndFlowId,int flowNum) {
		Map<Integer, Set<Integer>> conflictMap = new HashMap<>();		
		Set<String> pathSet = pathAndFlowId.keySet();
        for (Iterator<String> iterator = pathSet.iterator(); iterator.hasNext();) {  
        	String perPath = iterator.next();
            Set<Integer> flowId = pathAndFlowId.get(perPath);
            for (Integer id : flowId) {
            	if (!conflictMap.containsKey(id)) {
					conflictMap.put(id, flowId);
				}else{
					conflictMap.get(id).addAll(flowId);
				}
            }
        }		
//		Set<String> [] conflictSet = new HashSet[flowNum]; 
//		for (int i = 0; i < conflictSet.length; i++) {
//			conflictSet[i] = new HashSet<>();
//			conflictSet[i].add(i+"");
//		}
//        for (Iterator<String> iterator = pathSet.iterator(); iterator.hasNext();) {  
//            String perPath = iterator.next();
//            Set<String> flowId = pathAndFlowId.get(perPath);
//			if (flowId.contains("->")) {
//				
//				String[] oldConflictId = flowId.split(":");
//				for (int i = 0; i < oldConflictId.length; i++) {
//					int key = Integer.parseInt(oldConflictId[i]);
//					for (int j = 0; j < oldConflictId.length; j++) {
//						String conflictNode = oldConflictId[j];
//						conflictSet[key].add(conflictNode);
//					}
//				}
//			}
//        }
		return conflictMap;
	}
	
}
