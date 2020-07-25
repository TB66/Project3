package com.s3.thread.d0725;

import java.util.ArrayList;
import java.util.List;

public class Demo4 {
	public static void main(String[] args) {
		ProductConsumer pc = new ProductConsumer();
		new Thread() {
			public void run() {
				try {
					pc.producer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
		new Thread() {
			public void run() {
				try {
					pc.consumer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();

	}
}

class ProductConsumer {
	private List<Integer> list = new ArrayList<Integer>();

	/**
	 * 生产方法
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void producer() throws InterruptedException {
		while (true) {
			if (list.isEmpty()) {
				for (int i = 0; i < 10; i++) {
					list.add(i);
					System.out.println("-----生产了一个产品：" + i);
					Thread.sleep(200);
				}
			} else {
				/**
				 * list 不为空 通知消费线程notifyAll() 当前线程进入等待状态wait()
				 */
				notifyAll();
				wait();
			}
		}
	}

	/**
	 * 消费方法
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void consumer() throws InterruptedException {
		while (true) {
			if (list.isEmpty() == false) {
				for (int i = 0; i < 10; i++) {
					list.remove(0);
					System.out.println(Thread.currentThread().getId() + "消费了一个产品:" + i);
					Thread.sleep(100);
				}
			} else {
				/**
				 * list 为空 通知生产线程notifyAll() 当前线程进入等待状态wait()
				 */
				notifyAll();
				wait();
			}
		}
	}
}
