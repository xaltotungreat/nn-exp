package train;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.nnet.comp.layer.InputLayer;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.ConnectionFactory;
import org.neuroph.util.NeuronProperties;
import org.neuroph.util.TransferFunctionType;

import framework.gui.GraphFrame;
import framework.util.AbstractFunction;

public class NeurophTest1 {

	public static void main(String[] args) {
		boolean drawGraph = true;
		DataSet tempTrainingSet = DataSet.createFromFile("nnLogTrain1.tst", 1, 1, ",", false);
		final NeuralNetwork<BackPropagation> nn1 = new NeuralNetwork<>();
		nn1.setLearningRule(new BackPropagation());
		Layer inputLayer = new InputLayer(1);

		NeuronProperties hiddenProp = new NeuronProperties(Neuron.class, TransferFunctionType.SIGMOID);
		Layer hiddenLayer = new Layer(10, hiddenProp);
		ConnectionFactory.fullConnect(inputLayer, hiddenLayer);

		NeuronProperties outputProp = new NeuronProperties(Neuron.class, TransferFunctionType.SIGMOID);
		Layer outputLayer = new Layer(1, outputProp);
		ConnectionFactory.fullConnect(hiddenLayer, outputLayer);

		//nn1.addLayer(inputLayer);
		nn1.setInputNeurons(inputLayer.getNeurons());
		nn1.addLayer(hiddenLayer);
		//nn1.addLayer(outputLayer);
		nn1.setOutputNeurons(outputLayer.getNeurons());

		nn1.randomizeWeights();
		nn1.getLearningRule().setMaxError(0.1);
		nn1.getLearningRule().setLearningRate(0.01);

		LearningEventListener listen1 = new LearningEventListener() {

			@Override
			public void handleLearningEvent(LearningEvent event) {
				BackPropagation rule = (BackPropagation)event.getSource();
				System.out.println("Iteration " + rule.getCurrentIteration());
				System.out.println("Curr Error " + rule.getTotalNetworkError());

			}
		};
		nn1.getLearningRule().addListener(listen1);
		nn1.learn(tempTrainingSet);

		if (drawGraph) {
			Double graphLowX = 1d;
			Double graphHighX = 3d;
			AbstractFunction<Double, Double> currFunc = new AbstractFunction<Double, Double>() {
				@Override
				public Double apply(Double arg) {
					//return Math.sin(2*arg)*Math.sin(arg);
					return Math.log(arg);
				}
			};
			Map<List<Double>,List<Double>> origFunc1 = getResultsInOrder(100, graphLowX, graphHighX, currFunc);
			//nn.logCalculation = false;
			Map<List<Double>,List<Double>> nnFunc1 = getNNResultsOrder(100,
					Collections.singletonList(graphLowX), Collections.singletonList(graphHighX), nn1);
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
			List<Double> highBound, NeuralNetwork nn) {
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
			nn.setInput(currInputValues.stream().mapToDouble(a -> a.doubleValue()).toArray());
			nn.calculate();
            double[] networkOutput = nn.getOutput();

			//TestResult tr = nn.test(currInputValues, null);
            List<Double> outputValues = new ArrayList<Double>();
            for (double d1 : networkOutput) {
            	outputValues.add(d1);
			}
			res.put(new ArrayList<>(currInputValues), outputValues);
		}
		return res;
	}

	public static Map<Double,Double> convertResultsKeysOrder(Map<List<Double>, List<Double>> init, int num) {
		Map<Double,Double> res = new TreeMap<>();
		if (num >= 0) {
			for (Map.Entry<List<Double>, List<Double>> currEntry : init.entrySet()) {
				if (res.containsKey(currEntry.getKey().get(num))) {
					System.out.println("convertResults duplicate key " + currEntry.getKey().get(num));
				}
				res.put(currEntry.getKey().get(num), currEntry.getValue().get(num));
			}
		}
		return res;
	}

}
