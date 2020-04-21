
package com.hifo.dataoperation.util.thread;

/**
 * @author weisibin
 * @date 2020年4月20日12:16:33
 * 线程池工厂
 */
public class ThreadPoolFactory {

	/**
	 * 获取全局线程池
	 * @return 线程池
	 */
	public static ThreadPool getGlobalThreadPool(){
		return GlobalThreadPool.getInstance();
	}

	/**
	 * 新建固定线程池
	 * @return 线程池
	 */
	public static ThreadPool newFixedThreadPool(){
		return new FixedThreadPool();
	}

	/**
	 * 新建固定线程池
	 * @param coreSize 核心线程数
	 * @param maxSize 最大线程数
	 * @param queueSize 队列大小
	 * @return 线程池
	 */
	public static ThreadPool newFixedThreadPool(int coreSize, int maxSize, int queueSize){
		return new FixedThreadPool(coreSize,maxSize,queueSize);
	}

	/**
	 * 新建多服务线程池
	 * @return 线程池
	 */
	public static ThreadPool newMultipleServiceThreadPool(){
		return new MultipleServiceThreadPool();
	}

	/**
	 * 新建多服务线程池
	 * @param coreThreadSize 核心线程数
	 * @param maxThreadSize 最大线程数
	 * @param maxQueueSize 队列大小
	 * @return 线程池
	 */
	public static ThreadPool newMultipleServiceThreadPool(int coreThreadSize, int maxThreadSize, int maxQueueSize){
		return new MultipleServiceThreadPool(coreThreadSize,maxThreadSize,maxQueueSize);
	}
}
