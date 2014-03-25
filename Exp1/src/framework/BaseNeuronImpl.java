package framework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class BaseNeuronImpl implements INeuron {

	protected UUID id;
	protected List<INeuronLink> inputLinks = Collections.synchronizedList(new ArrayList<INeuronLink>());
	protected List<INeuronLink> outputLinks = Collections.synchronizedList(new ArrayList<INeuronLink>());
	protected Double currentInput = 0d;
	protected Double currentActivation = 0d;
	protected Double bias = 0d;
	protected IActivationFunction activationFunction;
	protected ISummInput summInput;
	
	public BaseNeuronImpl() {
		id = UUID.randomUUID();
	}
	
	public BaseNeuronImpl(IActivationFunction actFunc, ISummInput inp) {
		this();
		activationFunction = actFunc;
		summInput = inp;
	}
	
	@Override
	public Double calculateActivation() {
		if ((activationFunction != null) && (summInput != null)) {
			currentActivation = activationFunction.getActivationFunctionValue(summInput.getSummInputValue(inputLinks) + bias);
		}
		return currentActivation;
	}
	
	@Override
	public UUID getId() {
		return id;
	}

	public List<INeuronLink> getInputLinks() {
		return inputLinks;
	}

	public void setInputLinks(List<INeuronLink> inputLinks) {
		this.inputLinks = inputLinks;
	}

	public List<INeuronLink> getOutputLinks() {
		return outputLinks;
	}

	public void setOutputLinks(List<INeuronLink> outputLinks) {
		this.outputLinks = outputLinks;
	}

	public Double getCurrentInput() {
		return currentInput;
	}

	public void setCurrentInput(Double currentInput) {
		this.currentInput = currentInput;
	}

	public Double getCurrentActivation() {
		return currentActivation;
	}

	public void setCurrentActivation(Double currentAct) {
		this.currentActivation = currentAct;
	}
	
	@Override
	public void resetCurrentOutput() {
		currentActivation = 0d;
	}

	public Double getBias() {
		return bias;
	}

	public void setBias(Double bias) {
		this.bias = bias;
	}
	

}
