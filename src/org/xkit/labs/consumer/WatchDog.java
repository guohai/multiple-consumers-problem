package org.xkit.labs.consumer;

import java.util.Iterator;
import java.util.List;

public class WatchDog extends Thread {

	List<Task> queue;

	public WatchDog(List<Task> q) {
		this.queue = q;
	}

	@Override
	public void run() {
		sleepMe();
		watchDog();
	}

	private void watchDog() {
		System.err.println("watchDog in...");
		// 如果执行完了，线程自动退出，如果2分钟超时，线程自动退出
		while (true) {
			Iterator<Task> it = queue.iterator();

			while (it.hasNext()) {
				Task task = it.next();
				if (null != task && null != task.getExclusive()
						&& task.isTaskDone()) {
					System.out.println(task.getName() + " is done...");
//					Dummy.analyzeQueue(queue);
					it.remove();
					break;
				}
			}

			if (0 == queue.size()) {
				break;
			}
			sleepMe();
		}
		System.err.println("watchDog out...");
	}

	private void sleepMe() {
		try {
			sleep(10 * 5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
