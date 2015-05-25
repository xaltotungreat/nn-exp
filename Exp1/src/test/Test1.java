package test;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import framework.BackPropNeuronNetwork;
import framework.IActivationFunction;
import framework.INeuronLayer;
import framework.INeuronNetwork;
import framework.INeuronValuesFactory;
import framework.ISummInput;
import framework.NeuronNetworkUtil;
import framework.SigmaInput;
import framework.SinActivationFunction;
import framework.TestResult;
import framework.TrainResult;
import framework.gui.GraphFrame;
import framework.util.AbstractFunction;

public class Test1 {

	public static final Logger LOG = Logger.getLogger(Test1.class);

	public static void main(String[] args) {
		boolean drawGraph = true;

		PropertyConfigurator.configure("log4j.properties");
		BackPropNeuronNetwork nn = new BackPropNeuronNetwork(0.1);
		INeuronValuesFactory defFactory = new INeuronValuesFactory() {

			@Override
			public ISummInput getSummInput() {
				return new SigmaInput();
			}

			@Override
			public IActivationFunction getActivationFunction() {
				//return new SigmoidActivationFunction();
				//return new LinearActivationFunction(1);
				//return new SemiLinearActivationFunction(-1000, -1000, 1000, 1000);
				//return new AtanActivationFunction();
				return new SinActivationFunction();
			}
		};

		INeuronLayer inpLayer = NeuronNetworkUtil.generateNeuronLayer(1, defFactory);
		INeuronLayer hidLayer = NeuronNetworkUtil.generateNeuronLayer(10, defFactory);
		INeuronLayer outpLayer = NeuronNetworkUtil.generateNeuronLayer(1, defFactory);
		NeuronNetworkUtil.interconnectAllRandomWeights(inpLayer, hidLayer, -1, 1);
		NeuronNetworkUtil.interconnectAllRandomWeights(hidLayer, outpLayer, -1, 1);
		nn.setInputLayer(inpLayer);
		nn.setHiddenLayer(hidLayer);
		nn.setOutputLayer(outpLayer);
		AbstractFunction<Double, Double> currFunc = new AbstractFunction<Double, Double>() {
			@Override
			public Double apply(Double arg) {
				//return Math.sin(2*arg)*Math.sin(arg);
				return Math.log(arg);
			}
		};
		double lowBoundX = 1;
		double highBoundX = 3;

		Map<List<Double>,List<Double>> trainValues = getInputResultsRandom(2000, lowBoundX, highBoundX, currFunc);
		for (Map.Entry<List<Double>,List<Double>> currEntry : trainValues.entrySet()) {
			TrainResult currRes = nn.train(currEntry.getKey(), currEntry.getValue());
			//System.out.println("Diff " + (currRes.getExpectedResults().get(0) - currRes.getActualResults().get(0)));
			//LOG.debug("Init Values " + currEntry.getKey().get(0) + " " + currEntry.getValue().get(0));
			LOG.info("Expected " + currRes.getExpectedResults().get(0) + " Actual " + currRes.getActualResults().get(0)
					+ " Diff " + (currRes.getExpectedResults().get(0) - currRes.getActualResults().get(0)));
		}
		if (drawGraph) {
			Double graphLowX = lowBoundX;
			Double graphHighX = highBoundX;
			Map<List<Double>,List<Double>> origFunc1 = getResultsInOrder(100, graphLowX, graphHighX, currFunc);
			nn.logCalculation = false;
			Map<List<Double>,List<Double>> nnFunc1 = getNNResultsOrder(100,
					Collections.singletonList(graphLowX), Collections.singletonList(graphHighX), nn);
			final Map<Double,Double> origFunc = convertResultsKeysOrder(origFunc1, 0);
			final Map<Double,Double> nnFunc = convertResultsKeysOrder(nnFunc1, 0);
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					try {
						GraphFrame frame = new GraphFrame(origFunc, Color.BLUE, nnFunc, Color.MAGENTA);
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	public static Map<List<Double>,List<Double>> getInputResultsRandom(int count, double lowBound, double highBound,
			AbstractFunction<Double, Double> f) {
		Map<List<Double>,List<Double>> res = new HashMap<List<Double>,List<Double>>();
		for (int i = 0; i < count; i++) {
			double randNum = Math.random();
			double var = randNum*(highBound - lowBound) + lowBound;
			double value = f.apply(var);
			res.put(Collections.singletonList(var), Collections.singletonList(value));
		}
		return res;
	}

	public static Map<List<Double>,List<Double>> getResultsInOrder(int count, double lowBound, double highBound,
			AbstractFunction<Double, Double> f) {
		Map<List<Double>,List<Double>> res = new HashMap<List<Double>,List<Double>>();
		double step = (highBound - lowBound)/count;
		for (int i = 0; i <= count; i++) {
			Double xVal = lowBound + step*i;
			Double yVal = f.apply(xVal);
			res.put(Collections.singletonList(xVal), Collections.singletonList(yVal));
		}
		return res;
	}

	public static Map<List<Double>,List<Double>> getNNResultsOrder(int count, List<Double> lowBound,
			List<Double> highBound, INeuronNetwork nn) {
		Map<List<Double>,List<Double>> res = new HashMap<List<Double>,List<Double>>();
		List<Double> steps = new ArrayList<>();
		for (int i = 0; i < lowBound.size(); i++) {
			steps.add((highBound.get(i) - lowBound.get(i))/count);
		}
		List<Double> currInputValues = new ArrayList<>();
		for (int i = 0; i <= count; i++) {
			currInputValues.clear();
			for (int j = 0; j < steps.size(); j++) {
				currInputValues.add(lowBound.get(j) + steps.get(j)*i);
			}
			TestResult tr = nn.test(currInputValues, null);
			res.put(new ArrayList<>(currInputValues), tr.getOutputs());
		}
		return res;
	}

	public static Map<Double,Double> convertResultsKeysOrder(Map<List<Double>, List<Double>> init, int num) {
		Map<Double,Double> res = new TreeMap<>();
		if (num >= 0) {
			for (Map.Entry<List<Double>, List<Double>> currEntry : init.entrySet()) {
				if (res.containsKey(currEntry.getKey().get(num))) {
					LOG.error("convertResults duplicate key " + currEntry.getKey().get(num));
				}
				res.put(currEntry.getKey().get(num), currEntry.getValue().get(num));
			}
		}
		return res;
	}

}
