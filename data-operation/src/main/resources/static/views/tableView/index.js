var tableViewIndex_obj={
	
	urlConfig:{
		query:'/tableView/query',
		update:'/tableView/update',
		remove:'/tableView/remove'
	},
	tableIns:null,//layui table对象
	tableConfig:null,//table配置文件
		
	init:function(){
		var _this=this;
		_this.tableObj=layui.util.urlParam('tableObj');
		if(!_this.tableObj) return;
		$.getScript('config/'+_this.tableObj+'.js',function(){
			$.getScript('config/_default.js',function(){
				//覆盖默认模板
				_this.tableConfig=$.extend({},window.tableDefaultObj,window[_this.tableObj]);
				var defaultCol=window.tableDefaultObj.cols[0];
				$.each(_this.tableConfig.cols,function(i,col){
					_this.tableConfig.cols[i]=$.extend({},defaultCol,col);
				});
				//url初始化查询参数
				var urlQueryParam=layui.util.urlParam('queryParam');
				if(urlQueryParam){
					var base64=new layui.base64();
					var str=base64.decode(urlQueryParam);
					var json=JSON.parse(str);
					$.each(json,function(index,item){
						_this.tableConfig.queryParam.push(item);
					});
				}
				//开始渲染
				_this._renderTable();
				_this._renderTableSearch();
			});
		});
	},
	
	//初始化表格
	_renderTable:function(){
		var _this=this;
		var query=[];
		if(_this.tableConfig.queryParam){
			$.each(_this.tableConfig.queryParam,function(i,q){
				query.push(q);
			});
		}
		//表格工具栏
		var $toolbar=$('#tableViewIndex_wrapper #ttToolbar');
		var toolbarHtml=[];
		toolbarHtml.push('<div>');
		toolbarHtml.push('<a class="layui-btn layui-btn-sm layui-btn-primary layui-mt-2 layui-fr" id="ttSearchBtn" lay-event="search">'+
							'<i class="layui-icon layui-icon-search"></i>搜索'+
						'</a>');
		if(_this.tableConfig.toolbar&&_this.tableConfig.toolbar.length>0){
			$.each(_this.tableConfig.toolbar,function(i,t){
				toolbarHtml.push('<a class="layui-btn layui-btn-sm layui-btn-primary layui-mr-5 layui-mt-2 layui-fr" lay-event="'+t.event+'">'+
									'<i class="layui-icon '+t.icon+'"></i>'+t.text+
								'</a>');
			});
		}
		toolbarHtml.push('</div>');
		$toolbar.html(toolbarHtml.join(''));
		
		var cols=$.map(_this.tableConfig.cols,function(col){
	    	if(col.inView){
	    		return {
	    			field:col.field,
	    			title:col.title,
	    			templet:col.templet,
	    			toolbar:col.toolbar,
	    			sort:true
	    		}
	    	}else{
	    		return null;
	    	}
	    });
		//加入行号
		cols.unshift({
			type:'numbers',
			title:'编号'
		});
		//加入删除功能
		if(_this.tableConfig.enableDel){
			cols.push({
				field:'_id',
				title:'删除',
				toolbar:'<div>'+
						  '<button class="layui-btn layui-btn-danger layui-btn-xs" lay-event="_del">删除</button>'+
						'</div>'
			});
		} 
		
		//初始化表格
		_this.tableIns=layui.table.render({
		    elem: '#tableViewIndex_wrapper #tt',
		    url: _this.urlConfig.query,
		    contentType:'application/json',
		    method:'post',
		    toolbar:_this.tableConfig.toolbar,
		    where:{
		    	_table:_this.tableConfig.table,
		    	query:query
		    },
		    page:true,
		    //改变请求page size命名
		    request:{limitName:'size'},
		    toolbar:'#ttToolbar',
		    cols:[cols]
		 });
		
		//行toolbar事件
		layui.table.on('tool(tt)', function(obj){
			var data=obj.data;
			if(obj.event=='_del'){
				layui.layer.confirm('是否删除该记录?', {icon: 3, title:'是否删除'}, function(index){
					layui.layer.close(index);
					if(data._id){
						layui.ajax(_this.urlConfig.remove,
						{
							_id:data._id,
							_table:_this.tableConfig.table
						},
						function(data){
							layui.layer.msg('删除成功');
							obj.del();
						});
					}else{
						layui.layer.msg('找不到_id字段，无法删除',{icon:2});
					}
				});
			}else{
				_this.tableConfig.toolEvent(obj);
			}
		}); 
		
		//行双击事件编辑数据
		layui.table.on('rowDouble(tt)', function(obj){
			if(_this.tableConfig.rowDbClick=='edit'){
				/**编辑数据 start**/
				var formId=Math.ceil(Math.random()*100000);
				var editHtml=layui.util.quickForm({
					id:formId,
					formClass:'layui-form-pane',
					split:10,
					withValue:true,
					data:obj.data,
					fields:$.map(_this.tableConfig.cols,function(col){
						if(col.inDetail){
							return {
					    		field:col.field,
					    		label:col.title,
					    		editable:col.editable,//是否可编辑
					    		editType:col.editType,//编辑类型text select multiSelect,
					    		config:col.config
					    	}
						}else{
							return null;
						}
				    })
				});
				//编辑弹窗
				layui.layer.open({
					title:'详情',
					content:editHtml,
					area:['90%','90%'],
					btn:null
				});
				layui.form.render(null,formId);
				layui.formSelects.render();
				//修改提交
				layui.form.on('submit(sub_'+formId+')', function(data){
					var newData=data.field;
					//找到变化的值，只提交变化的值
					$.each(newData,function(k,v){
						var oldValue=obj.data[k]||'';
						var v=v||'';
						if(v==oldValue)
							delete newData[k];
					});
					//无变化不提交
					if(Object.keys(newData).length==0){
						layui.layer.msg('没有任何修改');
						return false;
					}
					layui.ajax(_this.urlConfig.update,
								{
									_id:obj.data._id,
									_table:_this.tableConfig.table,
									data:newData
								},
								function(){
									layui.layer.msg('保存成功');
									obj.update(newData);
								}
					);
					return false;
				});
				/**编辑数据 end**/
			}else if(typeof _this.tableConfig.rowDbClick=='function'){
				_this.tableConfig.rowDbClick(obj.data);
			}
		});
		
		//工具栏点击事件
		$('#tableViewIndex_wrapper div.layui-table-tool a.layui-btn').on('click',function(){
			var event=$(this).attr('lay-event');
			$.each(_this.tableConfig.toolbar,function(i,t){
				if(t.event==event){
					t.handler&&t.handler();
				}
				return false;
			});
		});
		
		//加载完table后的回调
		_this.tableConfig.afterTableInit&&_this.tableConfig.afterTableInit();
	},
	
	//渲染表格的搜索功能
	_renderTableSearch:function(initValue){
		var _this=this;
		var formId=Math.ceil(Math.random()*100000);
		var searchHtml=layui.util.quickForm({
			id:formId,
			withValue:false,
			btnText:'搜索',
			btnCls:'layui-btn-danger',
			split:2,
			formClass:'layui-form-pane',
			fields:$.map(_this.tableConfig.cols,function(col){
				if(col.search){
					return {
			    		field:col.field,
			    		label:col.title,
			    		editable:true,
			    		editType:col.editType,
			    		config:col.config
			    	}
				}else{
					return null;
				}
		    })
		});
		$('#tableViewIndex_wrapper #ttSearchBtn').on('click',function(){
			//搜索弹窗
			var layerId=layui.layer.open({
				title:'条件搜索',
				content:searchHtml,
				area:['700px','90%'],
				btn:null
			});
			layui.form.render(null,formId);
			layui.formSelects.render();
			//初始化搜索值,当用户点击搜索关闭后，下次打开还能看到上一次的输入值
			if(initValue){
				layui.form.val(formId,initValue);
			}
			//搜索提交
			layui.form.on('submit(sub_'+formId+')', function(data){
				var queryData=$.map(_this.tableConfig.cols,function(col){
					if(col.search&&data.field[col.field]){
						return {
				    		key:col.field,
				    		value:data.field[col.field],
				    		searchCondition:col.searchCondition
				    	}
					}else{
						return null;
					}
			    });
				
				//加上默认查询
				if(_this.tableConfig.queryParam){
					$.each(_this.tableConfig.queryParam,function(i,q){
						queryData.push(q);
					});
				}
				
				//关闭弹窗
				layui.layer.close(layerId);
				_this.tableIns.reload({
					  where: {
					      _table:_this.tableConfig.table,
					      query:queryData
					  } 
				});
				//需要重新渲染搜索框(为了搜索初始值)
				_this._renderTableSearch(data.field);
				
				return false;
			});
		});
	}
		
};

$(function(){
	tableViewIndex_obj.init();
});