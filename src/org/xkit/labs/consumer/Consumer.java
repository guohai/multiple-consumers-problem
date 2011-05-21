package org.xkit.labs.consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.xkit.labs.consumer.Task.ConcreteWork;

public class Consumer extends Thread {
	List<Task> queue;

	public Consumer(List<Task> q) {
		this.queue = q;
	}

	@Override
	public void run() {
		executeTask();
	}

	void executeTask() {
		// 预热
		for (int i = 0; i < 100000; i++) {
			int j = i;
		}

		for (Task task : queue) {
			Lock lock = new ReentrantLock();
			lock.tryLock();
			// critical area start
			if (null != task.getExclusive()
					&& task.getExclusive() != Thread.currentThread()) {
				// 如果有人占了，那么自动退出
				// 这里怎么实现比较好？
				continue;
			} else {
				if (null == task.getExclusive()) {
					task.setExclusive(Thread.currentThread());
				}
			}
			// critical area end
			lock.unlock();

			System.out.println("can only be executed " + queue.size()
					+ " times...");

			// 如果拿到了，按道理这段代码始终只会有一个线程来执行
			Map<String, List<ConcreteWork>> classification = new HashMap<String, List<ConcreteWork>>();
			List<ConcreteWork> cws = task.getWorkers();
			List<ConcreteWork> works = null;
			for (ConcreteWork cw : cws) {
				String[] awork = cw.getWork().split("\\$", -1);
				if (awork[1].equals("love")) {
					// 直接执行
					new ProductionLineA(task, cw).start();
				} else {
					if (!classification.containsKey(awork[1])) {
						works = new ArrayList<ConcreteWork>();
						works.add(cw);
						classification.put(awork[1], works);
					} else {
						classification.get(awork[1]).add(cw);
					}
				}
			}

			Iterator<String> it = classification.keySet().iterator();

			System.err.println("series amount " + classification.size());

			while (it.hasNext()) {
				String key = it.next();
				List<Task.ConcreteWork> cwwws = classification.get(key);
				// 分类执行
				new ProductionLineB(task, key, cwwws).start();
			}
		}

//		watchDog(); // 这个要不要synchronized，这块代码有问题
	}
}
