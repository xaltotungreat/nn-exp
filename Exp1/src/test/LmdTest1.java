package test;

import java.util.Arrays;
import java.util.List;

import com.sun.istack.internal.NotNull;

public class LmdTest1 {

	@NotNull String aa = "a";
	public static void main(String args[]) {
		List<AAA> testLst = Arrays.asList(new AAA(2d, 2d), new AAA(3d, 3d));
		/*double res = 0;
		for (AAA a : testLst) {
			res += a.getVal1()*a.getVal2();
		}*/
		//double res = testLst.stream().reduce(0d, (Double z, AAA aa) -> z + aa.getVal1()*aa.getVal2());
		//double res = testLst.stream().mapToDouble(aa -> aa.getVal1()*aa.getVal2()).sum();
		double res = testLst.stream().map(aa -> aa.getVal1()*aa.getVal2()).reduce(0d, Double::sum);
		System.out.println("Res A " + res);
	}
	
	private static class AAA {
		protected Double val1;
		protected Double val2;
		
		public AAA(double a1, double a2) {
			val1 = a1;
			val2 = a2;
		}

		public Double getVal1() {
			return val1;
		}

		public void setVal1(Double val1) {
			this.val1 = val1;
		}

		public Double getVal2() {
			return val2;
		}

		public void setVal2(Double val2) {
			this.val2 = val2;
		}
	}
}
