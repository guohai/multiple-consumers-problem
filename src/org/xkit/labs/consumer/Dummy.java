package org.xkit.labs.consumer;

import java.util.ArrayList;
import java.util.List;

import org.xkit.labs.consumer.utils.RandomGen;

public class Dummy extends Thread {
	List<Task> queue;

	@Override
	public void run() {
		String[] cwns = new String[] { "love", "evol", "ovel", "vole", "leov",
				"elvo" }; // 假设具体的工作分5类，到后面会有相应的线程去执行这些工作
		queue = new ArrayList<Task>();
		for (int i = 0; i < 10; i++) {
			Task task = new Task(i, "task" + i);
			int cws = RandomGen.from1To10();
			List<Task.ConcreteWork> wks = new ArrayList<Task.ConcreteWork>(cws);
			for (int j = 0; j < cws; j++) {
				wks.add(task.new ConcreteWork("shan$"
						+ cwns[RandomGen.from1To10() / 2]));
			}
			task.setWorkers(wks);

			queue.add(task);
		}

		// 制造Queue数据

		analyzeQueue(queue);
		// 分析Queue数据

		new WatchDog(queue).start(); // WatchDog需不需要多线程？
		
		while (true) {
			System.err.println("beginning to acquire task...");
			new Consumer(queue).start();
			// 很多线程去抢队列，一个队列被其他线程拿到的话，其他线程就必须退出
//			try {
//				sleep(10 * 1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
	}

	public static void analyzeQueue(List<Task> q) {
		System.err.println("queue has " + q.size() + " tasks");
		int allWks = 0;
		for (Task task : q) {
			System.err.println("  " + task.getName() + " has "
					+ task.getWorkers().size() + " wks ");
			System.err.print("    they are ");
			for (Task.ConcreteWork cw : task.getWorkers()) {
				System.err.print(cw.getWork() + "[" + cw.isDone() + "] ");
				allWks++;
			}
			System.err.println();
		}
		System.out.println("queue has " + allWks + " wks");
	}
}
