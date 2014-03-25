package framework;

public class SigmoidActivationFunction implements IActivationFunction {

	@Override
	public Double getActivationFunctionValue(Double input) {
		return 1/(1 + Math.exp(-input));
	}

	@Override
	public Double getDerivativeValueByS(int level, Double input) {
		return (1/(1 + Math.exp(-input)))*(Math.exp(-input)/(1 + Math.exp(-input)));
	}

}
