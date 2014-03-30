package framework;

public class BaseNeuronLinkImpl implements INeuronLink {
	
	protected INeuron source;
	protected INeuron destination;
	protected Double linkWeight;
	protected Double oldLinkWeight;
	
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
		this.oldLinkWeight = this.linkWeight;
		this.linkWeight = linkWeight;
	}
	
	@Override
	public String toString() {
		return "BaseNeuronLinkImpl [linkWeight=" + linkWeight + ", source=" + source + ", destination="
				+ destination + "]";
	}

	@Override
	public Double getOldLinkWeight() {
		// TODO Auto-generated method stub
		return oldLinkWeight;
	}

	@Override
	public void setOldLinkWeight(Double linkWeight) {
		oldLinkWeight = linkWeight;
	}

}
