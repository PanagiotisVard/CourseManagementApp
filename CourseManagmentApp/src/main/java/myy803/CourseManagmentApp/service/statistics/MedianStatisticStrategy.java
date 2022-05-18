package myy803.CourseManagmentApp.service.statistics;

import org.springframework.stereotype.Service;

@Service("Median")
public class MedianStatisticStrategy extends TemplateStatisticStrategy {

	public MedianStatisticStrategy() {

		super();

	}

	@Override
	protected double doActualCalculation() {
		return super.descriptiveStatistics.getPercentile(50);
	}

}
