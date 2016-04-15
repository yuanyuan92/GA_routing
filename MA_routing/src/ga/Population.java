package ga;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import boardAndFlow.DataflowList;

public class Population {
	int scale;
	public List<Individual> individuals;
	public void setIndividuals(List<Individual> individuals) {
		this.individuals = individuals;
	}
	public List<Individual> getIndividuals() {
		return individuals;
	}
	public int getScale() {
		return scale;
	}
	/**
	 * @param scale
	 */
	public Population(int scale, DataflowList dataflowList) {
		super();
		this.scale = scale;
		individuals = new ArrayList<>();
		for (int i = 0; i < scale; i++) {
			individuals.add(new Individual(dataflowList));
		}
		Evolution.mark(individuals, dataflowList);
	}
	@Override
	public String toString() {
		System.out.println("------------------population score info-----------------------");
		for (Individual individual : individuals) {
			System.out.println(individual.getScore());
		}
		return "Population [scale=" + scale + "]";
	}

	public static long getHyperPeriod(List<Long> periodList) {
		long result = 1;
		for (Iterator<Long> iterator = periodList.iterator(); iterator.hasNext();) {
			Long integer = (Long) iterator.next();
			result = Util.lcm(result, integer);
		}
		return result;
	}
	public static int getRunTimeUnit(List<Long> runTimeList) {
		long result = runTimeList.get(0);
		for (Iterator<Long> iterator = runTimeList.iterator(); iterator.hasNext();) {
			Long long1 = (Long) iterator.next();
			result = Util.gcd(result, long1);
		}
		return (int)result;
	}

}
