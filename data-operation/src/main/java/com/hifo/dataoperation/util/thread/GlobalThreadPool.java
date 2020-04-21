package com.hifo.dataoperation.util.thread;

/**
 * @author weisibin
 * @date 2020年4月20日12:16:33
 * 公共线程池
 */
class GlobalThreadPool extends MultipleServiceThreadPool {

	/**
	 * 线程池
	 */
	private static ThreadPool pool;

	/**
	 * 构建一个根据 核心数，最大数，队列数得公共线程池
	 * @param coreThreadSize 核心数
	 * @param maxThreadSize 最大数
	 * @param maxQueueSize 队列数
	 */
	private GlobalThreadPool(int coreThreadSize, int maxThreadSize, int maxQueueSize) {
		super(coreThreadSize,maxThreadSize,maxQueueSize);
	}

	/**
	 * 获取线程实例
	 * @return ThreadPool 线程实例
	 */
	static synchronized ThreadPool getInstance() {
		if (pool == null) {
			int core = 20;
			int max = 200;
			int queue = 500;
			pool = new GlobalThreadPool(core, max, queue);
		}
		return pool;
	}
}
