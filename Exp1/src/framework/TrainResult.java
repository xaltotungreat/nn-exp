package framework;

import java.util.List;

public class TrainResult {

	protected List<Double> expectedResults;
	protected List<Double> actualResults;
	
	public TrainResult() {
		// TODO Auto-generated constructor stub
	}
	
	public TrainResult(List<Double> exp, List<Double> act) {
		expectedResults = exp;
		actualResults = act;
	}
	
	public List<Double> getInputs() {
		return expectedResults;
	}

	public void setInputs(List<Double> inputs) {
		this.expectedResults = inputs;
	}

	public List<Double> getOutputs() {
		return actualResults;
	}

	public void setOutputs(List<Double> outputs) {
		this.actualResults = outputs;
	}

}
