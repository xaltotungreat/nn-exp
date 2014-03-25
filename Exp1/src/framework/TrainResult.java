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

	public List<Double> getExpectedResults() {
		return expectedResults;
	}

	public void setExpectedResults(List<Double> expectedResults) {
		this.expectedResults = expectedResults;
	}

	public List<Double> getActualResults() {
		return actualResults;
	}

	public void setActualResults(List<Double> actualResults) {
		this.actualResults = actualResults;
	}
	

}
