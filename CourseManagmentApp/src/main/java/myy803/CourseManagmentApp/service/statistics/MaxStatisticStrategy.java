package myy803.CourseManagmentApp.service.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("Max")
public class MaxStatisticStrategy extends TemplateStatisticStrategy {
	
	@Autowired
	public MaxStatisticStrategy() {
		super();
	}

	@Override
	protected double doActualCalculation() {
		return super.descriptiveStatistics.getMax();
	}

}
