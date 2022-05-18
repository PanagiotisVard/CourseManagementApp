package myy803.CourseManagmentApp.service.statistics;

import org.springframework.stereotype.Service;

@Service("Kurtosis")
public class KurtosisStatisticStrategy extends TemplateStatisticStrategy {

	public KurtosisStatisticStrategy() {
		super();
	}

	@Override
	protected double doActualCalculation() {
		return super.descriptiveStatistics.getKurtosis();
	}

}
