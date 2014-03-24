package framework;

import java.util.List;

public class SimpleNeuronNetwork extends BaseNeuronNetworkImpl {

	protected INeuronLayer inputLayer;
	protected INeuronLayer hiddenLayer;
	protected INeuronLayer outputLayer;
	
	public SimpleNeuronNetwork() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public TrainResult train(List<Double> inputs, List<Double> expectedResults) {
		if ((inputLayer == null) || (hiddenLayer == null) ||(outputLayer == null)) {
			System.out.println("One of layers is null - Existing");
			return null;
		}
		if ((inputs == null) || (expectedResults == null)) {
			System.out.println("One of result arrays is null - Existing");
			return null;
		}
		for (int i = 0; i < inputLayer.getNeuronList().size(); i++) {
			INeuron inputNeuron = inputLayer.getNeuronList().get(i);
			inputNeuron.setCurrentOutput(inputs.get(i));
		}
		
		return null;
	}
	
	protected void recalculateWeights(List<Double> expectedResults, List<Double> actualResults) {
		
	}

	@Override
	public TestResult test(List<Double> inputs, List<Double> expectedResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CalculationResult calculate(List<Double> inputs) {
		// TODO Auto-generated method stub
		return null;
	}

	public INeuronLayer getInputLayer() {
		return inputLayer;
	}

	public void setInputLayer(INeuronLayer inputLayer) {
		this.inputLayer = inputLayer;
	}

	public INeuronLayer getHiddenLayer() {
		return hiddenLayer;
	}

	public void setHiddenLayer(INeuronLayer hiddenLayer) {
		this.hiddenLayer = hiddenLayer;
	}

	public INeuronLayer getOutputLayer() {
		return outputLayer;
	}

	public void setOutputLayer(INeuronLayer outputLayer) {
		this.outputLayer = outputLayer;
	}

}
