package com.s3.thread.d0725;

public class Demo2 {
	public static void main(String[] args) {
		// 匿名内部类 创建线程
		Thread t1 = new Thread("线程1") {
			public void run() {
				for (int i = 0; i < 1000; i++) {
					System.out.println(Thread.currentThread().getName() + ":" + i);
				}
			}
		};

		Thread t2 = new Thread("------线程2") {
			public void run() {
				for (int i = 0; i < 1000; i++) {
					System.out.println(Thread.currentThread().getName() + ":" + i);
					try {
						if (i == 500) {
							/**
							 * Join()方法: join方法用于等待其它线程结束 当前运行的线程
							 * 可以调用另一线程的join 方法，当前运行线程将转到阻塞状态，
							 * 直至另一线程执行结束，它才会恢复运行。
							 */
							t1.join();
							
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		};

		t1.start();
		t2.start();
	}
}
