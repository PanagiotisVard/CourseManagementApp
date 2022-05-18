package myy803.CourseManagmentApp.service.statistics;

import org.springframework.stereotype.Service;

@Service("Mean")
public class MeanStatisticStrategy extends TemplateStatisticStrategy {

	public MeanStatisticStrategy() {

		super();

	}

	@Override
	protected double doActualCalculation() {
		return super.descriptiveStatistics.getMean();
	}

}
