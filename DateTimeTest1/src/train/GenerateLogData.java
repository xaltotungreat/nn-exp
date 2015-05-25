package train;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class GenerateLogData {

	private static final String DELIMITER = ",";

	private static final int VALUES_NUMBER = 2000;

	private static final double ARGUMENT_LOW_BOUND = 1;
	private static final double ARGUMENT_HIGH_BOUND = 3;

	public static void main(String[] args) {
		List<String> sb = new ArrayList<>();
		Random yearRand = new Random();
		for(int i = 0; i < VALUES_NUMBER; i++) {
			double currArg = getNextValue(yearRand, ARGUMENT_LOW_BOUND, ARGUMENT_HIGH_BOUND);
			sb.add(String.format(Locale.US, "%1$2.2f", currArg) + DELIMITER
					+ String.format(Locale.US, "%1$2.2f", getNextFunctionValue(currArg)));
		}

		try {
			Files.write(Paths.get("nnLogTrain1.tst"), sb, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static double getNextFunctionValue(double argValue) {
		return Math.log(argValue);
	}

	public static double getNextValue(Random yearRandom, double lowBound, double highBound) {
		double val1 = yearRandom.nextDouble();
		return ((highBound - lowBound)*val1 + lowBound);
	}

}
