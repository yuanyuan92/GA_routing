package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import flowGenerate.DataflowList;
import gaElements.Individual;
import gaElements.Population;
import gaStrategy.Evolution;

public class test {
	/**
	 * @flowNum 网络中数据流的数量 
	 * @param args
	 */
	public static void main(String[] args) {
		int ary = 7;
		int flowNum = 50;
		int maxPeriod = 10;
		int payloadPercent = 0;	//payload is the percentage from 0 to 100
		int scale = 1;
		int evolutionTimes = 1;
		//----------------------------------------------------------------------------------------------
		DataflowList dataflowList = new DataflowList(ary, flowNum, maxPeriod, payloadPercent);
		List<Individual> rank = new ArrayList<>();
		List<String> feasible = new ArrayList<>();
		double flow = 50;
		Population population = new Population(scale, dataflowList);
		Population next = population;
		for (int i = 0; i < evolutionTimes; i++) {
			Evolution evolution = new Evolution(next, dataflowList);
			next = evolution.newGeneration(next, dataflowList);
			Individual good = next.individuals.get(0);
			flow = good.overlapFlow<flow? good.overlapFlow :flow;
			if (good.overlapCost==0) {
				System.err.println(good);
				feasible.add(good.toString());
			}else {
				System.out.println(i+"\t"+good);
			}
		}
		
		System.out.println(dataflowList);
		System.out.println("best score is "+flow);
		System.out.println("---------feasible list--------------");
		for (String individual : feasible) {
			System.out.println(individual);
		}
	}
}
