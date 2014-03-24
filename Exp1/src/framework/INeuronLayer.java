package framework;

import java.util.List;

public interface INeuronLayer {

	public String getName();
	public void setName(String name);
	public List<INeuron> getNeuronList();
	public void setNeuronList(List<INeuron> neuronList);
	public void calculateActivation();
}
