package framework;

public class SimpleActivationFunction implements IActivationFunction {

	public SimpleActivationFunction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Double getActivationFunctionValue(Double input) {
		return input;
	}

}
