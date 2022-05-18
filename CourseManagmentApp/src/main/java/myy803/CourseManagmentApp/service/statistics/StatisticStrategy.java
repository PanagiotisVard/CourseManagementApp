package myy803.CourseManagmentApp.service.statistics;

public interface StatisticStrategy {
	
	public double calculateStatistic(int courseId);
	
	public void setPercentile(int percentile);

}
