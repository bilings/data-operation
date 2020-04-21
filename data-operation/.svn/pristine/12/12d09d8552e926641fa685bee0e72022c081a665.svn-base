package com.hifo.dataoperation.timetask.thread;

/**
 * 控制线程实现的逻辑规范
 * @author weisibin
 * @date 2020年4月17日14:06:47
 */
public interface CommonRunnable extends Runnable {

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	default void run() {
		try {
			running();
		} catch (Exception e) {
			exception(e);
		}
	}
	/**
	 * 线程运行方法
	 * @author weisibin
	 * @throws Exception
	 */
	void running() throws Exception;
	
	/**
	 * 线程运行异常返回
	 * @author weisibin
	 * @param e 
	 */
	void exception(Exception e);
}
