package myy803.CourseManagmentApp.service.statistics;

import org.springframework.stereotype.Service;

@Service("StandardDeviation")
public class StandardDeviationStatisticStrategy extends TemplateStatisticStrategy {

	public StandardDeviationStatisticStrategy() {
		super();
	}

	@Override
	protected double doActualCalculation() {
		return super.descriptiveStatistics.getStandardDeviation();
	}

}
