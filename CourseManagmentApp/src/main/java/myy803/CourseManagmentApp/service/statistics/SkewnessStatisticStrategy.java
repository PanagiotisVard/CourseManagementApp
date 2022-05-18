package myy803.CourseManagmentApp.service.statistics;

import org.springframework.stereotype.Service;

@Service("Skewness")
public class SkewnessStatisticStrategy extends TemplateStatisticStrategy {

	public SkewnessStatisticStrategy() {
		super();
	}

	@Override
	protected double doActualCalculation() {
		return super.descriptiveStatistics.getSkewness();
		
	}

}
