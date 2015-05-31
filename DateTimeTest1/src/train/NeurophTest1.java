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

	public static final String FILE_NAME = "nnSinxXTrain1.tst";

	public static final int INPUT_NEURONS = 1;

	public static final int HIDDEN_NEURONS1 = 10;

	public static final int HIDDEN_NEURONS2 = 10;

	public static final int HIDDEN_NEURONS3 = 10;

	public static final int OUTPUT_NEURONS = 1;

	public static final TransferFunctionType ACTIVATION_FUNCTION1 = TransferFunctionType.SIGMOID;

	public static final TransferFunctionType ACTIVATION_FUNCTION2 = TransferFunctionType.SIGMOID;

	public static final double MAX_ERROR = 0.01;

	public static final int MAX_ITERATIONS = 1000;

	public static final double LEARNING_RATE = 0.0001;

	public static final double GRAPH_LOW_BOUND_X = -5d;

	public static final double GRAPH_HIGH_BOUND_Y = 5d;

	public static void main(String[] args) {
		boolean drawGraph = true;
		DataSet tempTrainingSet = DataSet.createFromFile(FILE_NAME, 1,
				1, ",", false);
		final NeuralNetwork<BackPropagation> nn1 = new NeuralNetwork<>();
		//BackPropagation lRule = new BackPropagation();
		BackPropagation lRule = new BackPropagation();
		lRule.setMaxIterations(MAX_ITERATIONS);
		nn1.setLearningRule(lRule);

		Layer inputLayer = new InputLayer(INPUT_NEURONS);

		NeuronProperties hiddenProp1 = new NeuronProperties(Neuron.class,
				TransferFunctionType.SIGMOID);
		Layer hiddenLayer1 = new Layer(HIDDEN_NEURONS1, hiddenProp1);
		ConnectionFactory.fullConnect(inputLayer, hiddenLayer1);

		NeuronProperties hiddenProp2 = new NeuronProperties(Neuron.class,
				TransferFunctionType.SIGMOID);
		Layer hiddenLayer2 = new Layer(HIDDEN_NEURONS2, hiddenProp2);
		ConnectionFactory.fullConnect(hiddenLayer1, hiddenLayer2);

		/*NeuronProperties hiddenProp3 = new NeuronProperties(Neuron.class,
				TransferFunctionType.LOG);
		Layer hiddenLayer3 = new Layer(HIDDEN_NEURONS3, hiddenProp3);
		ConnectionFactory.fullConnect(hiddenLayer2, hiddenLayer3);*/

		NeuronProperties outputProp = new NeuronProperties(Neuron.class,
				TransferFunctionType.SIGMOID);
		Layer outputLayer = new Layer(OUTPUT_NEURONS, outputProp);
		ConnectionFactory.fullConnect(hiddenLayer2, outputLayer);

		nn1.addLayer(inputLayer);
		nn1.setInputNeurons(inputLayer.getNeurons());
		nn1.addLayer(hiddenLayer1);
		nn1.addLayer(hiddenLayer2);

		nn1.addLayer(outputLayer);
		nn1.setOutputNeurons(outputLayer.getNeurons());

		nn1.randomizeWeights(-2, 2);
		nn1.getLearningRule().setMaxError(MAX_ERROR);
		nn1.getLearningRule().setLearningRate(LEARNING_RATE);

		LearningEventListener listen1 = new LearningEventListener() {

			@Override
			public void handleLearningEvent(LearningEvent event) {
				BackPropagation rule = (BackPropagation) event.getSource();
				System.out.println("Iteration " + rule.getCurrentIteration());
				System.out.println("Curr Error " + rule.getTotalNetworkError());

			}
		};
		nn1.getLearningRule().addListener(listen1);
		nn1.learn(tempTrainingSet);

		if (drawGraph) {
			Double graphLowX = GRAPH_LOW_BOUND_X;
			Double graphHighX = GRAPH_HIGH_BOUND_Y;
			AbstractFunction<Double, Double> currFunc = new AbstractFunction<Double, Double>() {
				@Override
				public Double apply(Double arg) {
					// return Math.sin(2*arg)*Math.sin(arg);
					//return Math.log(arg);
					return Math.sin(arg)*arg;
				}
			};
			Map<List<Double>, List<Double>> origFunc1 = getResultsInOrder(100,
					graphLowX, graphHighX, currFunc);
			// nn.logCalculation = false;
			Map<List<Double>, List<Double>> nnFunc1 = getNNResultsOrder(100,
					Collections.singletonList(graphLowX),
					Collections.singletonList(graphHighX), nn1);
			final Map<Double, Double> origFunc = convertResultsKeysOrder(
					origFunc1, 0);
			final Map<Double, Double> nnFunc = convertResultsKeysOrder(nnFunc1,
					0);
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					try {
						GraphFrame frame = new GraphFrame(origFunc, Color.BLUE,
								nnFunc, Color.MAGENTA);
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	public static Map<List<Double>, List<Double>> getResultsInOrder(int count,
			double lowBound, double highBound,
			AbstractFunction<Double, Double> f) {
		Map<List<Double>, List<Double>> res = new HashMap<List<Double>, List<Double>>();
		double step = (highBound - lowBound) / count;
		for (int i = 0; i <= count; i++) {
			Double xVal = lowBound + step * i;
			Double yVal = f.apply(xVal);
			res.put(Collections.singletonList(xVal),
					Collections.singletonList(yVal));
		}
		return res;
	}

	public static Map<List<Double>, List<Double>> getNNResultsOrder(int count,
			List<Double> lowBound, List<Double> highBound, NeuralNetwork<?> nn) {
		Map<List<Double>, List<Double>> res = new HashMap<List<Double>, List<Double>>();
		List<Double> steps = new ArrayList<>();
		for (int i = 0; i < lowBound.size(); i++) {
			steps.add((highBound.get(i) - lowBound.get(i)) / count);
		}
		List<Double> currInputValues = new ArrayList<>();
		for (int i = 0; i <= count; i++) {
			currInputValues.clear();
			for (int j = 0; j < steps.size(); j++) {
				currInputValues.add(lowBound.get(j) + steps.get(j) * i);
			}
			nn.setInput(currInputValues.stream()
					.mapToDouble(a -> a.doubleValue()).toArray());
			nn.calculate();
			double[] networkOutput = nn.getOutput();

			List<Double> outputValues = new ArrayList<Double>();
			for (double d1 : networkOutput) {
				outputValues.add(d1);
			}
			res.put(new ArrayList<>(currInputValues), outputValues);
		}
		return res;
	}

	public static Map<Double, Double> convertResultsKeysOrder(
			Map<List<Double>, List<Double>> init, int num) {
		Map<Double, Double> res = new TreeMap<>();
		if (num >= 0) {
			for (Map.Entry<List<Double>, List<Double>> currEntry : init
					.entrySet()) {
				if (res.containsKey(currEntry.getKey().get(num))) {
					System.out.println("convertResults duplicate key "
							+ currEntry.getKey().get(num));
				}
				res.put(currEntry.getKey().get(num),
						currEntry.getValue().get(num));
			}
		}
		return res;
	}

}
