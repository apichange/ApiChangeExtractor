package apiChangeExtractor.evaluation;

public class ETimer {
	private long totalTime = 0;
	private long lastStartTime = -1;
	public void startTimer(){
		lastStartTime = System.currentTimeMillis();
	}
	public void endTimer(){
		if(lastStartTime<0){
			System.out.println("Timer has not started.");
			return;
		}
		totalTime = totalTime + (System.currentTimeMillis()-lastStartTime);
		lastStartTime = -1;
	}
	public long getTotalTime(){
		return totalTime;
	}
}
