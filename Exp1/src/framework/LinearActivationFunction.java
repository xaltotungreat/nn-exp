package framework;

public class LinearActivationFunction implements IActivationFunction {

	protected double coeff = 1;
	
	public LinearActivationFunction() {
		// TODO Auto-generated constructor stub
	}
	
	public LinearActivationFunction(double coef) {
		coeff = coef;
	}

	@Override
	public Double getActivationFunctionValue(Double input) {
		return coeff*input;
	}

	@Override
	public Double getDerivativeValueByS(int level, Double input) {
		double res = coeff;
		if (level >= 1) {
			res = 0;
		}
		return res;
	}

}
