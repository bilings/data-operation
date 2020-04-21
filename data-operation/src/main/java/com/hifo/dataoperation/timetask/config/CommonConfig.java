package com.hifo.dataoperation.timetask.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;

import com.hifo.dataoperation.util.thread.ThreadPoolFactory;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 任务配置公共类
 * @author weisibin
 */
@Slf4j
@Data
public class CommonConfig {
	
	/**
	 * 核心数量
	 */
	@Value("20")
	private Integer coreSize;
	
	/**
	 * 最大数
	 */
	@Value("200")
	private Integer maxSize;
	
	/**
	 * 队列数
	 */
	@Value("500")
	private Integer queueSize;
	/**
	 * 线程池
	 */
	private volatile ExecutorService threadPool;
	
	/**
	 * 初始化线程池
	 */
	@PostConstruct
	private void init(){
		threadPool = ThreadPoolFactory.newFixedThreadPool(coreSize, maxSize, queueSize);
		log.debug("初始化线程池成功,{}",this.getClass().getSimpleName());
	}
	
	/**
	 * 关闭线程池
	 */
	@PreDestroy
	public void close() {
		try {
			threadPool.shutdown();
			threadPool.awaitTermination(30, TimeUnit.SECONDS);
			log.debug("关闭任务线程池！");
		} catch (InterruptedException e) {
			log.error("异常关闭任务线程池！", e);
		}
	}
	
	/**
	 * 执行线程
	 * @param run
	 */
	public void exec(Runnable run){
		threadPool.execute(run);
	}
}
