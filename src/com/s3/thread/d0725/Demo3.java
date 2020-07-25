package com.s3.thread.d0725;

import java.util.ArrayList;
import java.util.List;
/**
 * 修饰为同步synchronized的方法( N个)， 那么这些方法将会在同一时刻被-一个线程说执行
 * @author tanbe
 *
 */
public class Demo3 {
	//存放数字的集合
	private List<Integer> list=new ArrayList<Integer>();
	//synchronized 同步关键字 保证该方法能完整的执行
	public synchronized void add(Integer i) {
		list.add(i);
	}
	public synchronized Integer pop() {
		//同步代码块
		synchronized (this) {
			return list.remove(0);
		}
		
	}
	public synchronized int size() {
		return list.size();
	}
	
	public static void main(String[] args) {
		Demo3 d=new Demo3();
		Thread t1=new Thread("线程1") {
			@Override
			public void run() {
				while (true) {
					if(d.size()<10) {
						for (int i = 0; i < 10; i++) {
							System.out.println(Thread.currentThread()+""+i);
							d.add(i);
						}
					}
					
				}
			}
		};
		
		Thread t2=new Thread("线程2") {
			@Override
			public void run() {
				while (true) {
					if(d.size()>0) {
						System.out.println(Thread.currentThread()+":"+d.pop());
					}
					
				}
			}
		};
		
		Thread t3=new Thread("======线程3") {
			@Override
			public void run() {
				while (true) {
					if(d.size()>0) {
						System.out.println(Thread.currentThread()+":"+d.pop());
					}
					
				}
			}
		};
		
		
		
		t1.start();
		t2.start();
		t3.start();
		
		
		
	}
}
