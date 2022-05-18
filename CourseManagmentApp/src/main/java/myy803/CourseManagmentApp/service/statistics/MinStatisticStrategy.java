package myy803.CourseManagmentApp.service.statistics;

import org.springframework.stereotype.Service;

@Service("Min")
public class MinStatisticStrategy extends TemplateStatisticStrategy {

	public MinStatisticStrategy() {

		super();

	}

	@Override
	protected double doActualCalculation() {
		return super.descriptiveStatistics.getMin();
	}
	

}
