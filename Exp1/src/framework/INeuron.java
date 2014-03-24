package framework;

import java.util.List;
import java.util.UUID;

public interface INeuron {

	public UUID getId();
	public List<INeuronLink> getInputLinks();
	public void setInputLinks(List<INeuronLink> inputLinks);
	public List<INeuronLink> getOutputLinks();
	public void setOutputLinks(List<INeuronLink> outputLinks);
	
	public Double getCurrentOutput();
	public void setCurrentOutput(Double currentOutput);
	public void resetCurrentOutput();
	public Double getBias();
	public void setBias(Double bias);
	
	public Double calculateActivation();
}
