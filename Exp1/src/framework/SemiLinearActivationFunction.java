package framework;

import org.apache.log4j.Logger;

public class SemiLinearActivationFunction implements IActivationFunction {

	public static final Logger LOG = Logger.getLogger(SemiLinearActivationFunction.class);
	
	protected Double coef = 1d;
	protected Double lowLimitX = -1d;
	protected Double upperLimitX = 1d;
	protected Double lowLimitY = -1d;
	protected Double upperLimitY = 1d;
	
	public SemiLinearActivationFunction() { }
	
	public SemiLinearActivationFunction(double lowLX, double lowLY, double upperLX, double upperLY) {
		lowLimitX = lowLX;
		lowLimitY = lowLY;
		upperLimitX = upperLX;
		upperLimitY = upperLY;
		coef = (upperLimitY - lowLimitY)/(upperLimitX - lowLimitX);
	}
	
	@Override
	public Double getActivationFunctionValue(Double input) {
		Double res = 0d;
		if ((lowLimitX <= input) && (input <= upperLimitX)) {
			res = input*coef;
		} else if (lowLimitX > input) {
			res = lowLimitY;
		} else if (input > upperLimitX) {
			res = upperLimitY;
		} else {
			LOG.error("getActivationFunctionValue incorrect value " + res);
		}
		return res;
	}

	@Override
	public Double getDerivativeValueByS(int level, Double input) {
		Double res = 0d;
		if (level != 1) {
			LOG.error("getDerivativeValueByS incorrect derivative level " + level);
			return null;
		} else {
			if ((lowLimitX <= input) && (input <= upperLimitX)) {
				res = coef;
			} else if (lowLimitX > input) {
				res = 0d;
			} else if (input > upperLimitX) {
				res = 0d;
			} else {
				LOG.error("getActivationFunctionValue incorrect value " + res);
			}
		}
		return res;
	}

}
