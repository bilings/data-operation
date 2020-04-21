package com.hifo.dataoperation.util.thread;


/**
 * @author weisibin
 * @date 2020年4月20日12:16:33
 * 固定线程池
 */
public class FixedThreadPool extends ThreadPool {

	FixedThreadPool(int coreThreadSize, int maxThreadSize, int maxQueueSize) {
		super(coreThreadSize, maxThreadSize, maxQueueSize);
	}

	FixedThreadPool() {
		super();
	}

	/**
	 * 获取正在运行的服务线程数
	 * @param key 服务名
	 * @return 服务数
	 */
	public Integer getServiceSize(String key) {
		return getPoolSize();
	}
}
