package framework;

import java.util.ArrayList;
import java.util.List;

public class BackPropNeuronNetwork extends BaseNeuronNetworkImpl {

	protected INeuronLayer inputLayer;
	protected INeuronLayer hiddenLayer;
	protected INeuronLayer outputLayer;
	protected double gamma = 1;
	
	public class OutputDeltaWrapper {
		protected Double deltaValue;
		protected INeuron outputNeuron;
		public OutputDeltaWrapper() {
			
		}
		
		public OutputDeltaWrapper(Double val, INeuron nr) {
			deltaValue = val;
			outputNeuron = nr;
		}

		public Double getDeltaValue() {
			return deltaValue;
		}

		public void setDeltaValue(Double deltaValue) {
			this.deltaValue = deltaValue;
		}

		public INeuron getOutputNeuron() {
			return outputNeuron;
		}

		public void setOutputNeuron(INeuron outputNeuron) {
			this.outputNeuron = outputNeuron;
		}
	}
	
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
		if (expectedResults.size() != outputLayer.getNeuronList().size()) {
			System.out.println("Incorrect expected results size " + expectedResults.size() 
					+ " output neurons " + outputLayer.getNeuronList().size());
			return null;
		}
		CalculationResult calcRes = calculate(inputs);
		recalculateWeights(expectedResults, calcRes.getResult());
		
		return new TrainResult(expectedResults, calcRes.getResult());
	}
	
	protected void recalculateWeights(List<Double> expectedResults, List<Double> actualResults) {
		List<OutputDeltaWrapper> outputDelta = new ArrayList<>();
		// recalculate for the output neurons
		for (int i = 0; i < outputLayer.getNeuronList().size(); i++) {
			INeuron nr = outputLayer.getNeuronList().get(i);
			double otpDelta = (expectedResults.get(i) - actualResults.get(i))
					*nr.getActivationFunction().getDerivativeValueByS(1, nr.getCurrentInput());
			outputDelta.add(new OutputDeltaWrapper(otpDelta, nr));
			for (INeuronLink lnk : nr.getInputLinks()) {
				System.out.println("Output Src " + lnk.getSource().getId() + " Dst " + lnk.getDestination().getId() + " old weight " + lnk.getLinkWeight());
				double deltaW = gamma*otpDelta*lnk.getSource().getCurrentActivation();
				lnk.setLinkWeight(lnk.getLinkWeight() + deltaW);
				System.out.println("Output Src " + lnk.getSource().getId() + " Dst " + lnk.getDestination().getId() + " new weight " + lnk.getLinkWeight());
			}
		}
		// recalculate for the hidden layer
		for (int i = 0; i < hiddenLayer.getNeuronList().size(); i++) {
			INeuron nr = hiddenLayer.getNeuronList().get(i);
			double hdnDelta = 0;
			for (INeuronLink lnk : nr.getOutputLinks()) {
				double tmpOtpDelta = 0;
				for (OutputDeltaWrapper wrp : outputDelta) {
					if (wrp.getOutputNeuron().equals(lnk.getDestination())) {
						tmpOtpDelta = wrp.getDeltaValue();
						break;
					}
				}
				hdnDelta += tmpOtpDelta*lnk.getLinkWeight();
			}
			hdnDelta = hdnDelta*nr.getActivationFunction().getDerivativeValueByS(1, nr.getCurrentInput());
			for (INeuronLink lnk : nr.getInputLinks()) {
				System.out.println("Hidden Src " + lnk.getSource().getId() + " Dst " + lnk.getDestination().getId() + " old weight " + lnk.getLinkWeight());
				double deltaW = gamma*hdnDelta*lnk.getSource().getCurrentActivation();
				lnk.setLinkWeight(lnk.getLinkWeight() + deltaW);
				System.out.println("Hidden Src " + lnk.getSource().getId() + " Dst " + lnk.getDestination().getId() + " new weight " + lnk.getLinkWeight());
			}
		}
	}

	@Override
	public TestResult test(List<Double> inputs, List<Double> expectedResults) {
		CalculationResult calcRes = calculate(inputs);
		TestResult tst = new TestResult(inputs, calcRes.getResult());
		return tst;
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
