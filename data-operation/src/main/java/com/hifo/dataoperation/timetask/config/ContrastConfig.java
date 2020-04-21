package com.hifo.dataoperation.timetask.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=true)
@Configuration
@ConfigurationProperties(prefix = "task.contrast")
public class ContrastConfig extends CommonConfig{
	
	/**
	 * 线程运行间隔
	 */
	@Value("100")
	private Long spaceTime;
	
	@Value("100")
	private Long contrastSpaceTime;
	
	/**
	 * 需要保存的对比name\名称结果边界分数，
	 */
	@Value("60")
	private Double nameScoreMix;
	
	@Value("")
	private String strPattern;
	
	/**
	 * 需要保存的对比结address\地址果边界分数，也就是最小的分数
	 */
	@Value("60")
	private Double addrScoreMix;
}
