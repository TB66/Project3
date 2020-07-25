package com.s3.thread.d0725;

import java.util.Scanner;

public class Demo1 {
	public static void main(String[] args) {
		// 获取当前线程Thread.currentThread()
		System.out.println("main线程getId:" + Thread.currentThread().getId());
		System.out.println("main线程getName:" + Thread.currentThread().getName());
		// getPriority优先级
		System.out.println("main线程getPriority:" + Thread.currentThread().getPriority());
		System.out.println("main线程getState:" + Thread.currentThread().getState());
		A a = new A("a线程");
		B b = new B();
		Thread c = new Thread(b, "b线程");
		a.start();
		c.start();

	}

	public static void a() {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入");
		String str = sc.nextLine();
		System.out.println("输入：" + str);
	}

	public static void b() {
		System.out.println("hello");
	}

	// 静态内部类实现线程类
	// 通过继承Thread 实现多线程
	public static class A extends Thread {
		public A(String name) {
			super(name);
		}

		@Override
		public void run() {
			a();
			System.out.println("a线程getId:" + Thread.currentThread().getId());
			System.out.println("a线程getName:" + Thread.currentThread().getName());
			System.out.println("a线程getPriority:" + Thread.currentThread().getPriority());
			System.out.println("a线程getState:" + Thread.currentThread().getState());

		}
	}

	// 通过实现runnable接口实现多线程
	public static class B implements Runnable {

		@Override
		public void run() {
			System.out.println("线程B休眠10s");
			try {
				Thread.sleep(10 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			b();
			System.out.println("b线程getId:" + Thread.currentThread().getId());
			System.out.println("b线程getName:" + Thread.currentThread().getName());
			System.out.println("b线程getPriority:" + Thread.currentThread().getPriority());
			System.out.println("b线程getState:" + Thread.currentThread().getState());

		}

	}
}
