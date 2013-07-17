package tr2.client.series;

import tr2.server.common.entity.Interval;

public class Calculator {
	
	public Interval calculate(Interval interval) {
		if (interval == null)
			return null;

		double sum = 0.0;
		long i;
		for (i = interval.getFirstDenominator(); i < interval
				.getLastDenominator(); i++) {
			sum += 1.0 / (double) i;
		}
		interval.setResult(sum);
		
		return interval;
	}
	
}
