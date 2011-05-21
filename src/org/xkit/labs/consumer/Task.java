package org.xkit.labs.consumer;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 一个Task，里面有很多具体的事情ConcreteWork
// 所有事情做完了才算一个Task完毕
public class Task {
	class ConcreteWork {

		private String work;

		private boolean done;

		public ConcreteWork(String work) {
			this(work, false);
		}

		public ConcreteWork(String work, boolean done) {
			this.work = work;
			this.done = done;
		}

		public String getWork() {
			return work;
		}

		public boolean isDone() {
			return done;
		}

		public void setDone(boolean done) {
			this.done = done;
		}

		public void setWork(String work) {
			this.work = work;
		}
	}

	private int id; // taskId

	private String name;

	private Thread hold; // FLAG THIS TASK IS HOLD BY SOME THREAD...

	public synchronized Thread getExclusive() {
		return hold;
	}

	public synchronized void setExclusive(Thread c) {
		Lock lock = new ReentrantLock();
		lock.tryLock();
		this.hold = c;
		lock.unlock();
	}

	private List<ConcreteWork> workers;

	public Task(int id, String name, List<ConcreteWork> wks) {
		this.id = id;
		this.name = name;
		this.workers = wks;
	}

	public Task(int id, String name) {
		this(id, name, null);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<ConcreteWork> getWorkers() {
		return workers;
	}

	public boolean isTaskDone() {
		boolean isDone = true;
		// 没有需要执行的事情
		if (null == workers)
			return true;
		for (ConcreteWork w : workers) {
			if (!w.done) {
				isDone = false;
				break;
			}
		}
		return isDone;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setWorkers(List<ConcreteWork> workers) {
		this.workers = workers;
	}
}
