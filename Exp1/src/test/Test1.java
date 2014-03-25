package test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import framework.BackPropNeuronNetwork;
import framework.IActivationFunction;
import framework.INeuronLayer;
import framework.INeuronValuesFactory;
import framework.ISummInput;
import framework.LinearActivationFunction;
import framework.NeuronNetworkUtil;
import framework.SigmaInput;
import framework.SigmoidActivationFunction;
import framework.TrainResult;

public class Test1 {

	public static final Logger LOG = Logger.getLogger(Test1.class);
	
	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");
		BackPropNeuronNetwork nn = new BackPropNeuronNetwork(0.001);
		INeuronValuesFactory defFactory = new INeuronValuesFactory() {
			
			@Override
			public ISummInput getSummInput() {
				return new SigmaInput();
			}
			
			@Override
			public IActivationFunction getActivationFunction() {
				return new SigmoidActivationFunction(30);
				//return new LinearActivationFunction(1);
			}
		};
		
		INeuronLayer inpLayer = NeuronNetworkUtil.generateNeuronLayer(1, defFactory);
		INeuronLayer hidLayer = NeuronNetworkUtil.generateNeuronLayer(5, defFactory);
		INeuronLayer outpLayer = NeuronNetworkUtil.generateNeuronLayer(1, defFactory);
		NeuronNetworkUtil.interconnectAllRandomWeights(inpLayer, hidLayer, -10, 10);
		NeuronNetworkUtil.interconnectAllRandomWeights(hidLayer, outpLayer, -10, 10);
		nn.setInputLayer(inpLayer);
		nn.setHiddenLayer(hidLayer);
		nn.setOutputLayer(outpLayer);
		
		Map<List<Double>,List<Double>> trainValues = getInputsResultsX(100, -180, 180);
		for (Map.Entry<List<Double>,List<Double>> currEntry : trainValues.entrySet()) {
			TrainResult currRes = nn.train(currEntry.getKey(), currEntry.getValue());
			//System.out.println("Diff " + (currRes.getExpectedResults().get(0) - currRes.getActualResults().get(0)));
			LOG.debug("Init Values " + currEntry.getKey().get(0) + " " + currEntry.getValue().get(0));
			LOG.info("Expected " + currRes.getExpectedResults().get(0) + " Actual " + currRes.getActualResults().get(0));
		}
	}
	
	public static Map<List<Double>,List<Double>> getInputsResultsSin(int count, double lowBound, double highBound) {
		Map<List<Double>,List<Double>> res = new HashMap<List<Double>,List<Double>>();
		for (int i = 0; i < count; i++) {
			double randNum = Math.random();
			double var = randNum*(highBound - lowBound) + lowBound;
			double value = Math.sin(var);
			res.put(Collections.singletonList(var), Collections.singletonList(value));
		}
		return res;
	}
	
	public static Map<List<Double>,List<Double>> getInputsResultsX(int count, double lowBound, double highBound) {
		Map<List<Double>,List<Double>> res = new HashMap<List<Double>,List<Double>>();
		for (int i = 0; i < count; i++) {
			double randNum = Math.random();
			double var = randNum*(highBound - lowBound) + lowBound;
			double value = var;
			res.put(Collections.singletonList(var), Collections.singletonList(value));
		}
		return res;
	}

}
