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
	 * @flowNum ����������������� 
	 * @param args
	 */
	public static void main(String[] args) {
		int ary = 7;
		int flowNum = 25;
		int maxPeriod = 10;
		int payloadPercent = 0;	//payload is the percentage from 0 to 100
		int scale = 100;
		int evolutionTimes = 200;
		//----------------------------------------------------------------------------------------------
		DataflowList dataflowList = new DataflowList(ary, flowNum, maxPeriod, payloadPercent);
		List<Individual> rank = new ArrayList<>();
//		System.out.println(dataflowList);
		Population population = new Population(scale, dataflowList);
		Population next = population;
		Individual best = new Individual();
		best.setScore(10000);
		for (int i = 0; i < evolutionTimes; i++) {
			Evolution evolution = new Evolution(next, dataflowList);
			next = evolution.newGeneration(next, dataflowList);
			rank.add(next.individuals.get(0));
			if (next.individuals.get(0).getScore()==0) {
				System.out.println("*************");
				System.out.println(next.individuals.get(0));
				break;
			}
			System.out.println(next.individuals.get(0));
		}
		System.out.println(dataflowList);
//		for (Individual individual : rank) {
//			System.out.println(individual);
//		}
	}
}
