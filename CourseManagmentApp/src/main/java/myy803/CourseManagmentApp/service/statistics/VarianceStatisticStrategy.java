package myy803.CourseManagmentApp.service.statistics;

import org.springframework.stereotype.Service;

@Service("Variance")
public class VarianceStatisticStrategy extends TemplateStatisticStrategy {

	public VarianceStatisticStrategy() {

		super();

	}

	@Override
	protected double doActualCalculation() {
		return super.descriptiveStatistics.getVariance();
	}

}
