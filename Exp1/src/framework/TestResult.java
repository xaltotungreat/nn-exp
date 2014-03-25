package framework;

import java.util.List;

public class TestResult {

	protected List<Double> inputs;
	protected List<Double> outputs;
	
	public TestResult() {
		// TODO Auto-generated constructor stub
	}
	
	public TestResult(List<Double> inp, List<Double> outp) {
		inputs = inp;
		outputs = outp;
	}

	public List<Double> getInputs() {
		return inputs;
	}

	public void setInputs(List<Double> inputs) {
		this.inputs = inputs;
	}

	public List<Double> getOutputs() {
		return outputs;
	}

	public void setOutputs(List<Double> outputs) {
		this.outputs = outputs;
	}

}
