package eu.bioimage.imagej.plugins.contractilityanalyzer;

import java.util.ArrayList;
import java.util.List;

public class DataPoint {
	List<Double> data;
	public List<Double> getData() {
		return data;
	}

	Double mean;
	Double sem;
	Double sd;

	public DataPoint(double value) {
		data = new ArrayList<Double>();
		data.add(value);
	}

	public DataPoint(List<? extends Number> data) {

		if (data.get(0) instanceof Double)
			this.data = (List<Double>) data;
		else if (data.get(0) instanceof Integer) {
			this.data = new ArrayList<Double>(data.size());
			for (Number number : data) {
				this.data.add(number.doubleValue());
			}
		}
	}

	public void addData(double value) {
		data.add(value);
	}

	public int getN() {
		return data.size();
	}

	public Double getMean() {
		if (mean == null)
			mean = Statistic.mean(data);
		return mean;
	}

	public Double getSem() {
		if (sem == null)
			sem = Statistic.sem(data);
		return sem;
	}

	public Double getSd() {
		if (sd == null)
			sd  = Statistic.sd(data);
		return sd;
	}

	@Override
	public String toString() {
		return getMean() + " " + getSd() + " " + getSem() + " " + getN();
	}
}
