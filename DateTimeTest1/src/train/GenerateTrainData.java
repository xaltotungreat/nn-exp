package train;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;


public class GenerateTrainData {

	private static final int YEAR_LOW_BOUND = 1900;
	private static final int YEAR_HIGH_BOUND = 2100;

	private static final int MONTH_LOW_BOUND = 1;
	private static final int MONTH_HIGH_BOUND = 12;

	private static final int DAY_LOW_BOUND = 1;
	private static final int DAY_HIGH_BOUND = 31;

	private static final int HOUR_LOW_BOUND = 0;
	private static final int HOUR_HIGH_BOUND = 23;

	private static final int MINUTE_LOW_BOUND = 0;
	private static final int MINUTE_HIGH_BOUND = 59;

	private static final int SECOND_LOW_BOUND = 0;
	private static final int SECOND_HIGH_BOUND = 59;

	private static final int NANOSECOND_LOW_BOUND = 0;
	private static final int NANOSECOND_HIGH_BOUND = 999999999;

	private static final String DELIMITER = ",";

	private static final int VALUES_NUMBER = 1000;

	public static class FormatWrapper {
		String formatString;
		List<Integer> posArray;
		DateTimeFormatter fmt;

		public FormatWrapper(String str, List<Integer> array) {
			formatString = str;
			posArray = array;
			fmt = DateTimeFormatter.ofPattern(formatString, Locale.US);
		}

		public String getDateTimeAsTrainStr(LocalDateTime dt, String delim, String endStr) {
			StringBuilder sb = new StringBuilder();
			sb.append(fmt.format(dt) + delim);
			String commaSeparatedNumbers = posArray.stream()
					.map(i -> i.toString()).
					collect(Collectors.joining(delim));
			sb.append(commaSeparatedNumbers);
			return sb.toString();
		}
	}

	public static void main(String[] args) {
		Random yearRand = new Random();
		Random monthRand = new Random();
		Random dayRand = new Random();
		Random hourRand = new Random();
		Random minuteRand = new Random();
		Random secondRand = new Random();
		Random nanosecRand = new Random();

		List<FormatWrapper> allFormats = new ArrayList<>();
		/*allFormats.add(new FormatWrapper("yyyy,MM,dd,HH,mm,ss,SSS", Arrays.asList(1,2,3,4,5,6,7)));
		allFormats.add(new FormatWrapper("MM,dd,yyyy,HH,mm,ss,SSS", Arrays.asList(3,1,2,4,5,6,7)));
		allFormats.add(new FormatWrapper("dd,MM,HH,mm,ss,SSS", Arrays.asList(0,2,1,4,5,6,7)));
		allFormats.add(new FormatWrapper("dd,MM,yyyy,HH,mm,ss,SSS", Arrays.asList(3,2,1,4,5,6,7)));*/
		allFormats.add(new FormatWrapper("yyyy,MM,dd,HH,mm,ss,SSS", Arrays.asList(1,2,3,4,5,6,7)));
		allFormats.add(new FormatWrapper("MM,dd,yyyy,HH,mm,ss,SSS", Arrays.asList(3,1,2,4,5,6,7)));
		allFormats.add(new FormatWrapper("dd,MM,HH,mm,ss,SSS,0", Arrays.asList(0,2,1,4,5,6,7)));
		allFormats.add(new FormatWrapper("dd,MM,yyyy,HH,mm,ss,SSS", Arrays.asList(3,2,1,4,5,6,7)));

		List<String> sb = new ArrayList<>();

		for(int i = 0; i < VALUES_NUMBER; i++) {
			try {
				LocalDateTime time = LocalDateTime.of(
					getNextValue(yearRand, YEAR_LOW_BOUND, YEAR_HIGH_BOUND),
					getNextValue(monthRand, MONTH_LOW_BOUND, MONTH_HIGH_BOUND),
					getNextValue(dayRand, DAY_LOW_BOUND, DAY_HIGH_BOUND),
					getNextValue(hourRand, HOUR_LOW_BOUND, HOUR_HIGH_BOUND),
					getNextValue(minuteRand, MINUTE_LOW_BOUND, MINUTE_HIGH_BOUND),
					getNextValue(secondRand, SECOND_LOW_BOUND, SECOND_HIGH_BOUND),
					getNextValue(nanosecRand, NANOSECOND_LOW_BOUND, NANOSECOND_HIGH_BOUND));
				FormatWrapper currFmt = allFormats.get(i%allFormats.size());
				sb.add(currFmt.getDateTimeAsTrainStr(time, DELIMITER, "\n"));
			} catch (DateTimeException e) {

			}
		}

		try {
			Files.write(Paths.get("nnTest1.tst"), sb, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int getNextValue(Random yearRandom, int lowBound, int highBound) {
		double val1 = yearRandom.nextDouble();
		return (int)((highBound - lowBound)*val1 + lowBound);
	}

}
