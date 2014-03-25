package framework;

public class NeuronNetworkUtil {

	private NeuronNetworkUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public static INeuronLayer generateNeuronLayer(int nCount, IActivationFunction actF, ISummInput inp) {
		INeuronLayer nLayer = new BaseNeuronLayerImpl();
		IActivationFunction actFunction = actF;
		if (actFunction == null) {
			actFunction = new LinearActivationFunction();
		}
		ISummInput summInp = inp;
		if (summInp == null) {
			summInp = new SigmaInput();
		}
		for (int i = 0; i < nCount; i++) {
			INeuron newNeuron = new BaseNeuronImpl(actFunction, summInp);
			nLayer.getNeuronList().add(newNeuron);
		}
		return nLayer;
	}
	
	public static INeuronLayer generateNeuronLayer(int nCount, INeuronValuesFactory factory) {
		INeuronLayer nLayer = new BaseNeuronLayerImpl();
		IActivationFunction actFunction = factory.getActivationFunction();
		if (actFunction == null) {
			System.err.println("Null Activation function - Setting Linear");
			actFunction = new LinearActivationFunction();
		}
		ISummInput summInp = factory.getSummInput();
		if (summInp == null) {
			System.err.println("Null SummInput function - Setting SigmaInput");
			summInp = new SigmaInput();
		}
		for (int i = 0; i < nCount; i++) {
			INeuron newNeuron = new BaseNeuronImpl(actFunction, summInp);
			nLayer.getNeuronList().add(newNeuron);
		}
		return nLayer;
	}
	
	public static void interconnectAllRandomWeights(INeuronLayer nl1, INeuronLayer nl2, double lowBound, double highBound) {
		if ((nl1 != null) && (nl2 != null)) {
			for (INeuron neuronSrc : nl1.getNeuronList()) {
				for (INeuron neuronDst : nl2.getNeuronList()) {
					boolean linkExists = false;
					for (INeuronLink lnk : neuronSrc.getOutputLinks()) {
						if (lnk.getDestination().equals(neuronDst)) {
							linkExists = true;
						}
					}
					for (INeuronLink lnk : neuronDst.getInputLinks()) {
						if (lnk.getSource().equals(neuronSrc)) {
							linkExists = true;
						}
					}
					if (!linkExists) {
						double randNumber = Math.random();
						double randWeight = randNumber*(highBound - lowBound) + lowBound;
						INeuronLink newLink = new BaseNeuronLinkImpl(neuronSrc, neuronDst, randWeight);
						neuronSrc.getOutputLinks().add(newLink);
						neuronDst.getInputLinks().add(newLink);
					} else {
						System.out.println("Link Exists N1 " + neuronSrc.getId() + " N2 " + neuronDst.getId());
					}
				}
			}
		}
	}
	
	public static void interconnectAllSpecifyWeight(INeuronLayer nl1, INeuronLayer nl2, double weight) {
		if ((nl1 != null) && (nl2 != null)) {
			for (INeuron neuronSrc : nl1.getNeuronList()) {
				for (INeuron neuronDst : nl2.getNeuronList()) {
					boolean linkExists = false;
					for (INeuronLink lnk : neuronSrc.getOutputLinks()) {
						if (lnk.getDestination().equals(neuronDst)) {
							linkExists = true;
						}
					}
					for (INeuronLink lnk : neuronDst.getInputLinks()) {
						if (lnk.getSource().equals(neuronSrc)) {
							linkExists = true;
						}
					}
					if (!linkExists) {
						INeuronLink newLink = new BaseNeuronLinkImpl(neuronSrc, neuronDst, weight);
						neuronSrc.getOutputLinks().add(newLink);
						neuronDst.getInputLinks().add(newLink);
					} else {
						System.out.println("Link Exists N1 " + neuronSrc.getId() + " N2 " + neuronDst.getId());
					}
				}
			}
		}
	}

}
