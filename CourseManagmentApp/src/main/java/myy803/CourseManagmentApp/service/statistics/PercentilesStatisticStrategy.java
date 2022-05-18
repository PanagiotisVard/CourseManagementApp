package myy803.CourseManagmentApp.service.statistics;

import org.springframework.stereotype.Service;

@Service("Percentile")
public class PercentilesStatisticStrategy extends TemplateStatisticStrategy {

	private int percentile;

	public PercentilesStatisticStrategy() {
		super();
		percentile = 20;
	}
	
	@Override
	public void setPercentile(int percentile) {
		this.percentile = percentile;
	}
	
	


	@Override
	protected double doActualCalculation() {
		return super.descriptiveStatistics.getPercentile(percentile);
	}

}
