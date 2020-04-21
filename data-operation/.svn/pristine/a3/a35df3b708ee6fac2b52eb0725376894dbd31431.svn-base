package com.hifo.dataoperation.timetask.thread;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import com.hifo.dataoperation.dao.BuiltEstateDao;
import com.hifo.dataoperation.dao.BusEstateDao;
import com.hifo.dataoperation.dao.BusWaitMergeDao;
import com.hifo.dataoperation.entity.mongo.BuiltEstate;
import com.hifo.dataoperation.timetask.config.ContrastConfig;
import com.hifo.dataoperation.util.ExceptionMsgUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 比较数据主线程
 * @author weisibin
 * @date 2020年4月20日11:14:04
 */
@Slf4j
public class QueryThread implements CommonRunnable {

	private BuiltEstateDao bed;
	private ContrastConfig cc;
	private BusEstateDao ed;
	private Query query;
	private BusWaitMergeDao bwd;

	public QueryThread(BuiltEstateDao bed, BusEstateDao ed,BusWaitMergeDao bwd, ContrastConfig cc,
			Query query) {
		this.bed = bed;
		this.cc = cc;
		this.ed = ed;
		this.query = query;
		this.bwd = bwd;
	}

	@Override
	public void running() throws Exception {
		//查询此分页页数据
		List<BuiltEstate> bel = bed.selectByQuery(query, BuiltEstate.class);
		//处理数据
		for (BuiltEstate b : bel) {
			//判但此条数据是否已经跑过了
			if(bwd.existWId(b.getId())){
				break;
			}
			//子线程对比数据
			cc.exec(new ContrastThread(b,ed,bwd,cc));
			Thread.sleep(cc.getSpaceTime());
		}
	}

	@Override
	public void exception(Exception e) {
		log.error("对比任务主线程执行出现异常：{}",ExceptionMsgUtil.getStackMsg(e));
	}
}
