package framework;

import java.util.List;

public interface INeuronNetwork {

	public TrainResult train(List<Double> inputs, List<Double> expectedResults);
	public TestResult test(List<Double> inputs, List<Double> expectedResults);
	public CalculationResult calculate(List<Double> inputs);
}
