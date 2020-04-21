
package com.hifo.dataoperation.util.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
/**
 * @author weisibin
 * @date 2020年4月20日12:16:33
 *线程池抽象类，线程抽池象类可以创建各类型线程池实例。
 * <p>
 *    {@link GlobalThreadPool }是单例的，池大小和队列大小可通过配置文件或环境变量设置，
 *    默认情况 {@link GlobalThreadPool}核心池大小是20，最大是200，队列是无限的。
 * </p>
 * <p>
 *     {@link MultipleServiceThreadPool }为线程进行了分类，不同的线程类属于不同的分类，
 *     在需要对各分类统计时可使用此线程池，当线程池和队列满时，线程会阻塞，
 *     默认情况 {@link MultipleServiceThreadPool}核心池大小是20，最大是200，队列最大为500。
 * </p>
 * <p>
 *     {@link FixedThreadPool }是固定大小池，当线程池和队列满时，线程会阻塞，
 *     默认情况 {@link FixedThreadPool}核心池大小是20，最大是200，队列最大为500。
 * </p>
 */
public abstract class ThreadPool extends ThreadPoolExecutor {

	/**
	 * 等待时间
	 */
	private final static int PUT_WAIT_INTERVAL = 1000 ;
	/**
	 * 最大空闲存在时间
	 */
	private final static int KEEP_ALIVE_TIME = 60 ;
	/**
	 * 默认线程核心数
	 */
	private final static int DEFAULT_POOL_CORE = 20;
	/**
	 * 默认线程最大数
	 */
	private final static int DEFAULT_POOL_MAX = 200;
	/**
	 * 默认线程队列数
	 */
	private final static int DEFAULT_QUEUE_MAX = 500;

	/**
	 * 线程核心数
	 */
	private int coreThreadSize ;
	/**
	 * 线程最大数
	 */
	private int maxThreadSize ;
	/**
	 * 线程队列数
	 */
	private int maxQueueSize ;

	/**
	 * 构建一个指定核心数，最大数，队列数线程池
	 * @param coreThreadSize 核心数
	 * @param maxThreadSize 最大数
	 * @param maxQueueSize 队列数
	 */
	ThreadPool(int coreThreadSize, int maxThreadSize, int maxQueueSize) {
		super(coreThreadSize, maxThreadSize, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<>(maxQueueSize));
		this.coreThreadSize = coreThreadSize;
		this.maxQueueSize = maxQueueSize;
		this.maxThreadSize = maxThreadSize;
	}

	/**
	 * 构建一个使用默认参数线程池
	 */
	ThreadPool() {
		super(DEFAULT_POOL_CORE, DEFAULT_POOL_MAX, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<>(DEFAULT_QUEUE_MAX));
	}


	/**
	 * 获取分类服务线程数
	 * @param key 服务名
	 * @return Integer 服务数
	 */
	public abstract Integer getServiceSize(String key);

	/**
	 * 设置线程池大小
	 */
	synchronized void autoSetupSize() {
		int activeSize = getActiveCount();
		int coreSize = getPoolSize(); // 当前线程池当前设置最小线程数
		int queueSize = getQueue().size();
		int setSize = 0;

		if (queueSize > 0) {
			// 增大当前线程数
			setSize = (coreSize + queueSize > maxThreadSize) ? maxThreadSize: coreSize + queueSize;
		} else if (activeSize < coreSize) {
			// 减小线程数
			setSize = (activeSize < coreThreadSize) ? coreThreadSize: activeSize;
		}

		if (setSize > 0) {
			if (setSize > coreSize) {
				setMaximumPoolSize(setSize);
				setCorePoolSize(setSize);
			} else {
				setCorePoolSize(setSize);
				setMaximumPoolSize(setSize);
			}
		}
	}

	/**
	 * 执行线程
	 * @param runnable 被执行线程
	 */
	public void execute(Runnable runnable) {
			int poolSize ;
			int poolMaxThreadSize ;
			int currentQueueSize ;
			autoSetupSize();
			while (true) {
				poolSize = getPoolSize();
				poolMaxThreadSize = getMaximumPoolSize();
				currentQueueSize = getQueue().size();
//				System.out.println("PoolSize:"+poolSize + "  MaxSize:" + poolMaxThreadSize +" queueSize:" + currentQueueSize);
				if (poolSize >= poolMaxThreadSize && currentQueueSize >= maxQueueSize) {
					waitTime();
				} else {
					super.execute(runnable);
					break;
				}
			}
	}

	/**
	 * 等待时间
	 */
	private void waitTime(){
		try {
			Thread.sleep(PUT_WAIT_INTERVAL);
		} catch (InterruptedException e) {
			throw new RuntimeException ("线程等待异常", e);
		}
	}
}
