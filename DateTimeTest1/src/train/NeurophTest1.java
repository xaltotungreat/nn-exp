package train;

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

public class NeurophTest1 {

	public static void main(String[] args) {
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
	}

}
