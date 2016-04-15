package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import boardAndFlow.DataflowList;
import ga.Evolution;
import ga.Individual;
import ga.Population;

public class test {
	/**
	 * @flowNum 网络中数据流的数量 
	 * @param args
	 */
	public static void main(String[] args) {
		int ary = 7;
		int flowNum = 25;
		int maxPeriod = 10;
		int payloadPercent = 0;	//payload is the percentage from 0 to 100
		int scale = 500;
		int evolutionTimes = 500;
		//----------------------------------------------------------------------------------------------
		DataflowList dataflowList = new DataflowList(ary, flowNum, maxPeriod, payloadPercent);
		List<Individual> rank = new ArrayList<>();
		List<String> feasible = new ArrayList<>();
		
		Population population = new Population(scale, dataflowList);
		Population next = population;
		for (int i = 0; i < evolutionTimes; i++) {
			Evolution evolution = new Evolution(next, dataflowList);
			next = evolution.newGeneration(next, dataflowList);
			Individual good = next.getIndividuals().get(0);
			if (good.getOverlapCost()==0) {
				System.err.println(good);
				feasible.add(good.toString());
			}else {
				System.out.println(i+"\t"+good);
			}
		}
		
		System.out.println(dataflowList);
		System.err.println("---------feasible list--------------");
		for (String individual : feasible) {
			System.out.println(individual);
		}
	}
}
