package framework;

public interface INeuronLink {
	public INeuron getSource();
	public void setSource(INeuron source);
	public INeuron getDestination();
	public void setDestination(INeuron destination);
	public Double getLinkWeight();
	public void setLinkWeight(Double linkWeight);
	public Double getOldLinkWeight(); 
	public void setOldLinkWeight(Double linkWeight);
}
