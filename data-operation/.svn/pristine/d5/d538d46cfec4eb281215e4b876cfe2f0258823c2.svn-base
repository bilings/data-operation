package com.hifo.dataoperation.timetask.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.hifo.dataoperation.timetask.config.QueryConfig;
import com.hifo.dataoperation.timetask.service.SimilarityService;


/**
 * 批处理楼盘信息定时任务
 * @author weisibin
 * @date 2020年4月17日13:40:33
 */
@Component
public class SimilarityProcessor implements SchedulingConfigurer {

	@Autowired
	private QueryConfig qc;
	
	@Autowired
	private SimilarityService similarityService;
	/**
	 * 任务开始主方法
	 * (non-Javadoc)
	 * @see org.springframework.scheduling.annotation.SchedulingConfigurer#configureTasks(org.springframework.scheduling.config.ScheduledTaskRegistrar)
	 */
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		//数据对比任务
		taskRegistrar.addTriggerTask(()->{
			similarityService.processor();
		}, triggerContext->{
			return new CronTrigger(qc.getCron()).nextExecutionTime(triggerContext);
		});
	}
}
