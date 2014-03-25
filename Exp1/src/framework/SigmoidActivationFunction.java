package framework;

public class SigmoidActivationFunction implements IActivationFunction {

	protected double coeff = 1;
	
	public SigmoidActivationFunction() {
		
	}
	
	public SigmoidActivationFunction(double coef) {
		coeff = coef;
	}
	@Override
	public Double getActivationFunctionValue(Double input) {
		return coeff/(1 + Math.exp(-input));
	}

	@Override
	public Double getDerivativeValueByS(int level, Double input) {
		return (coeff/(1 + Math.exp(-input)))*(Math.exp(-input)/(1 + Math.exp(-input)));
	}

}
