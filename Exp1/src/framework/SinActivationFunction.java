package framework;

import org.apache.log4j.Logger;

public class SinActivationFunction implements IActivationFunction {

	public static final Logger LOG = Logger.getLogger(SinActivationFunction.class);
	
	@Override
	public Double getActivationFunctionValue(Double input) {
		return Math.sin(input);
	}

	@Override
	public Double getDerivativeValueByS(int level, Double input) {
		if (level > 1) {
			LOG.error("getDerivativeValueByS incorrect derivative level " + level);
			return null;
		} else {
			return Math.cos(input);
		}
	}

}
