package tr2.client.series;

import tr2.server.common.entity.Interval;

public class Calculator {
	
	public Interval calculate(Interval interval) {
		if (interval == null)
			return null;

		double sum = 0;
		for (long i = interval.getFirstDenominator(); i < interval
				.getLastDenominator(); i++) {
			sum += 1.0d / i;
		}
		interval.setResult(sum);
		
		return interval;
	}
	
}
