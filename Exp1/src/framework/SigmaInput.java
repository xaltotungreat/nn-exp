package framework;

import java.util.List;

public class SigmaInput implements ISummInput {

	public SigmaInput() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Double getSummInputValue(List<INeuronLink> inputLinks) {
		double res = 0;
		if (inputLinks != null) {
			for (INeuronLink nl : inputLinks) {
				res += nl.getLinkWeight()*nl.getSource().getCurrentActivation();
			}
		}
		return res;
	}

}
