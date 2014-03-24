package framework;

public class BaseNeuronLinkImpl implements INeuronLink {

	protected INeuron source;
	protected INeuron destination;
	protected Double linkWeight;
	
	public BaseNeuronLinkImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public BaseNeuronLinkImpl(INeuron src, INeuron dst, double weight) {
		source = src;
		destination = dst;
		linkWeight = weight;
	}

	public INeuron getSource() {
		return source;
	}

	public void setSource(INeuron source) {
		this.source = source;
	}

	public INeuron getDestination() {
		return destination;
	}

	public void setDestination(INeuron destination) {
		this.destination = destination;
	}

	public Double getLinkWeight() {
		return linkWeight;
	}

	public void setLinkWeight(Double linkWeight) {
		this.linkWeight = linkWeight;
	}
	
	

}
