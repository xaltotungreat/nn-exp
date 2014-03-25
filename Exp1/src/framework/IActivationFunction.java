package framework;

public interface IActivationFunction {

	public Double getActivationFunctionValue(Double input);
	public Double getDerivativeValueByS(int level, Double input);
}
