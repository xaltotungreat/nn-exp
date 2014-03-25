package framework;

import java.util.ArrayList;
import java.util.List;

public class BackPropNeuronNetwork extends BaseNeuronNetworkImpl {

	protected INeuronLayer inputLayer;
	protected INeuronLayer hiddenLayer;
	protected INeuronLayer outputLayer;
	protected double gamma = 1;
	
	public BackPropNeuronNetwork() {
		// TODO Auto-generated constructor stub
	}
	
	public BackPropNeuronNetwork(double gm) {
		gamma = gm;
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
		CalculationResult calcRes = calculate(inputs);
		recalculateWeights(expectedResults, calcRes.getResult());
		
		return new TrainResult();
	}
	
	protected void recalculateWeights(List<Double> expectedResults, List<Double> actualResults) {
		//double smallDelta = 
	}

	@Override
	public TestResult test(List<Double> inputs, List<Double> expectedResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CalculationResult calculate(List<Double> inputs) {
		CalculationResult res = new CalculationResult();
		for (int i = 0; i < inputLayer.getNeuronList().size(); i++) {
			INeuron inputNeuron = inputLayer.getNeuronList().get(i);
			inputNeuron.setCurrentActivation(inputs.get(i));
		}
		for (INeuron nr : hiddenLayer.getNeuronList()) {
			nr.calculateActivation();
		}
		List<Double> outputs = new ArrayList<>();
		for (INeuron nrOutp : outputLayer.getNeuronList()) {
			outputs.add(nrOutp.calculateActivation());
		}
		res.setResult(outputs);
		return res;
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
