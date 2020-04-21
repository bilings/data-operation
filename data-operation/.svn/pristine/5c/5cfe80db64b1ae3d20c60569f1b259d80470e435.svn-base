package com.hifo.dataoperation.util.thread;

import java.util.HashMap;
import java.util.Map;

/**
 * @author weisibin
 * @date 2020年4月20日12:16:33
 */
public class MultipleServiceThreadPool extends ThreadPool {

	/**
	 * 分类计数器
	 */
	private Map<String, Integer> serviceMap = new HashMap<>();


	/**
	 * 构建一个默认多服务线程池
	 */
	MultipleServiceThreadPool() {
		super();
	}

	/**
	 * 构建一个指定核心数，最大数，队列数得线程池
	 * @param coreThreadSize 核心数
	 * @param maxThreadSize 最大数
	 * @param maxQueueSize 队列数
	 */
	MultipleServiceThreadPool(int coreThreadSize, int maxThreadSize, int maxQueueSize) {
		super(coreThreadSize,maxThreadSize,maxQueueSize);
	}

	/**
	 * 线程结束后执行线程池数据清理
	 * @param r 线程
	 * @param t 异常
	 */
	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
		if (r == null)
			return;
		String key = r.getClass().getSimpleName();
		keyPlus(key, -1);
		autoSetupSize();
	}

	/**
	 * 线程开始前执行线程池数据处理
	 * @param t 异常
	 * @param r 线程
	 */
	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		if (r == null)
			return;
		String key = r.getClass().getSimpleName();
		keyPlus(key, 1);
		super.beforeExecute(t, r);
	}

	/**
	 * 线程池多服务数据计算
	 * @param key 服务关键字
	 * @param plus 服务数量
	 */
	private synchronized void keyPlus(String key, int plus) {
		serviceMap.put(key, getServiceSize(key) + plus);
	}

	/**
	 * 获取正在运行的服务线程数
	 * @param key  服务名
	 * @return Integer 服务数量
	 */
	public Integer getServiceSize(String key) {
		Integer size = serviceMap.get(key);
		if (size == null) {
			size = 0;
		}
		return size;
	}

}
