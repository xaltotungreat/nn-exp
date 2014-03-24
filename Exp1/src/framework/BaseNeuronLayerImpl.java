package framework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaseNeuronLayerImpl implements INeuronLayer {

	protected String name; 
	protected List<INeuron> neuronList = Collections.synchronizedList(new ArrayList<INeuron>());
	
	public BaseNeuronLayerImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public BaseNeuronLayerImpl(String aName) {
		name = aName;
	}
	
	public BaseNeuronLayerImpl(String aName, List<INeuron> allNeurons) {
		this(aName);
		neuronList = allNeurons;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<INeuron> getNeuronList() {
		return neuronList;
	}

	public void setNeuronList(List<INeuron> neuronList) {
		this.neuronList = neuronList;
	}

	@Override
	public void calculateActivation() {
		for (INeuron currNeuron : neuronList) {
			currNeuron.calculateActivation();
		}
		
	}

}
