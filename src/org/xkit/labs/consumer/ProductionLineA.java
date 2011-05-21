package org.xkit.labs.consumer;

import java.math.BigDecimal;

import org.xkit.labs.consumer.utils.Pi;

public class ProductionLineA extends Thread {

	private Task task;
	private Task.ConcreteWork cw;

	public ProductionLineA(Task task, Task.ConcreteWork cw) {
		System.err.println("ProductionLineA " + System.currentTimeMillis());
		this.task = task;
		this.cw = cw;
	}

	@Override
	public void run() {
		System.out.println(cw.getWork() + " in task[" + task.getId()
				+ "] is executing by ProductionLineA...");
		doA(); // CONSUME TIME

		cw.setDone(true);// FLAG DONE
	}

	public BigDecimal doA() {
		return new Pi(2000).execute();
	}
}
