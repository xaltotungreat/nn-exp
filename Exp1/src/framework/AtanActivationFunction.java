package framework;

import org.apache.log4j.Logger;

public class AtanActivationFunction implements IActivationFunction {

	public static final Logger LOG = Logger.getLogger(AtanActivationFunction.class);
	
	@Override
	public Double getActivationFunctionValue(Double input) {
		return Math.atan(input);
	}

	@Override
	public Double getDerivativeValueByS(int level, Double input) {
		if (level > 1) {
			LOG.error("getDerivativeValueByS incorrect derivative level " + level);
			return null;
		} else {
			return 1/(1 + input*input);
		}
	}

}
