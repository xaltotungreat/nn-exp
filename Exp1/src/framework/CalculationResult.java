package framework;

import java.util.List;

public class CalculationResult {

	protected List<Double> result;
	
	public CalculationResult() {
		// TODO Auto-generated constructor stub
	}
	
	public CalculationResult(List<Double> res) {
		result = res;
	}

	public List<Double> getResult() {
		return result;
	}

	public void setResult(List<Double> result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "CalculationResult [result=" + result + "]";
	}

}
