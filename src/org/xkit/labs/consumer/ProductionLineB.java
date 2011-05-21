package org.xkit.labs.consumer;

import java.math.BigDecimal;
import java.util.List;

import org.xkit.labs.consumer.utils.Pi;

public class ProductionLineB extends Thread {

	private Task task;
	private List<Task.ConcreteWork> wks;
	private String series;

	public ProductionLineB(Task task, String series, List<Task.ConcreteWork> wks) {
		System.err.println("ProductionLineB " + System.currentTimeMillis());
		this.task = task;
		this.series = series;
		this.wks = wks;
	}

	@Override
	public void run() {
		System.err.println("going to " + series);
		for (Task.ConcreteWork work : wks) {
			System.out.println(work.getWork() + " in task[" + task.getId()
					+ "] is executing by ProductionLineB...");
			doB(); // CONSUME TIME

			work.setDone(true);// FLAG DONE
		}
	}

	public BigDecimal doB() {
		return new Pi(4000).execute();
	}
}
