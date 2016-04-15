package boardAndFlow;

import java.util.List;

public class Dataflow {
	private int flowId;
	private int source;
	private int dst;
	//unit：ns
	private long period;
	private long runTime;
	private long deadline;
	private List<Integer> routeNode;
	/**
	 * 
	 */
//	public Dataflow(int ary) {
//		super();
//		int nodeNum = (int) Math.pow(ary, 2);
//		//起止点不能一样
//		do {
//			source = (int) (Math.random()*nodeNum);
//			dst = (int) (Math.random()*nodeNum);
//		} while (source==dst);
//		//周期设为1、2、4
//		period = (int) (Math.pow(2, (int)(Math.random()* 5)));//period baseline
//		runTime= (int) (Math.random()*period)/4+1;//supposed can not be zero and utilization lower than 25%
//		setDeadline(period - runTime);
//		routeNode = XYRouting.getRouteNode(source, dst, ary);
//	}
	public Dataflow(int ary, int maxPeriod, int payloadPercent) {
		super();
		int nodeNum = (int) Math.pow(ary, 2);
		//起止点不能一样
		do {
			source = (int) (Math.random()*nodeNum);
			dst = (int) (Math.random()*nodeNum);
		} while (source==dst);
		//周期设为1、2、4
		period = (int) (Math.pow(2, 1+(int)(Math.random()* maxPeriod)));//period baseline
		runTime= ((int) (Math.random() * period) * payloadPercent / 100) + 1;//supposed can not be zero and utilization lower than 25%
		deadline = period - runTime;
		routeNode = XYRouting.getRouteNode(source, dst, ary);
	}
	
	@Override
	public String toString() {
		return "[Id=" + flowId + ", source=" + source + ", dst=" + dst + ", period=" + period + ", runTime="
				+ runTime + ", routeNode=" + routeNode + "]";
	}
	
	public void setflowId(int flowId) {
		this.flowId = flowId;
	}
	public int getflowId() {
		return flowId;
	}
	public List<Integer> getRouteNode() {
		return routeNode;
	}
	public int getSource() {
		return source;
	}
	public int getDst() {
		return dst;
	}
	public long getPeriod() {
		return period;
	}
	public long getRunTime() {
		return runTime;
	}
	public long getDeadline() {
		return deadline;
	}

}
