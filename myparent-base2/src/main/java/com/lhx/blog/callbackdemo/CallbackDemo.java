package com.lhx.blog.callbackdemo;

/**
 * java回调机制 http://blog.csdn.net/xiaanming/article/details/8703708 Class
 * A实现接口CallBack callback——背景1
 * class A中包含一个class B的引用b ——背景2
 * class B有一个参数为callback的方法f(CallBack callback) ——背景3
 * A的对象a调用B的方法 f(CallBack callback)
 * A类调用B类的某个方法f 然后b就可以在f(CallBack callback)方法中调用A的方法 ——B类调用A类的某个方法
 */
public class CallbackDemo {
	public static void main(String[] args) {
		Answerer answerer = new Answerer();
		Questioner questioner = new Questioner(answerer);
		questioner.ask();
	}

	/**
	 * 回调接口
	 */
	static interface Callback {
		void getResult(String result);
	}

	/**
	 * 实现了一个回调接口CallBack，相当于----->背景一
	 */
	static class Questioner implements Callback {
		/**
		 * 对象的引用 相当于----->背景二
		 */
		private Answerer answerer;

		public void ask() {
			// 异步回调
			Questioner me = this;
			new Thread(new Runnable() {
				@Override
				public void run() {
					/**
					 * 这就相当于A类调用B的方法
					 */
					// answerer.dealProblem(Questioner.this,"1+1=?");
					answerer.dealProblem(me, "1+1=?");
				}
			}, "提问者线程").start();
			// 同步回调
			// answerer.dealProblem(this,"1+1=?");

			doSomeThing();
		}

		@Override
		public void getResult(String result) {
			System.out.println("提问者得到答案：" + result);
		}

		public void doSomeThing() {
			System.out.println("提问者做其他事去啦，知道答案后同我讲声");
		}

		public Questioner(Answerer answerer) {
			this.answerer = answerer;
		}
	}

	static class Answerer {
		public void dealProblem(Questioner questioner, String question) {
			System.out.println(this+"收到的问题是:" + question);
			System.out.println(this+"容我想一下");
			// 需要时间，模拟业务处理
			for (int i = 0; i < 60000; i++) {

			}
			String result = "2";
			// 通知提问者获取结果
            //B类调用A类的某个方法
			questioner.getResult(result);
		}
	}
}
