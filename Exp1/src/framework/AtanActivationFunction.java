package framework;

import org.apache.log4j.Logger;

public class AtanActivationFunction implements IActivationFunction {

	private static final Logger LOG = Logger.getLogger(AtanActivationFunction.class);
	protected Double coef = 1d;
	
	public AtanActivationFunction() {
		
	}
	
	public AtanActivationFunction(double c) {
		coef = c;
	}
	
	@Override
	public Double getActivationFunctionValue(Double input) {
		return coef*Math.atan(input);
	}

	@Override
	public Double getDerivativeValueByS(int level, Double input) {
		if (level > 1) {
			LOG.error("getDerivativeValueByS incorrect derivative level " + level);
			return null;
		} else {
			return coef/(1 + input*input);
		}
	}

}
