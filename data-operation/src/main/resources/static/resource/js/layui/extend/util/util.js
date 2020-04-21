layui.define(function(exports) {

	var methods={
			
		/**
		 * 提取url参数	
		 * **/
		urlParam:function(name){
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
			var r = window.location.search.substr(1).match(reg);
			if (r != null) return unescape(r[2]); return null;
		},
        /**
         * 提取url参数,解决中文乱码的问题
         * **/
        getQueryString:function(name){
			var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
			var r = window.location.search.substr(1).match(reg);
			if (r != null) {
				return decodeURI(r[2]);
			}
			return null;
        },

		/**
		 * 快速生成table的html
		 */
		quickTable:function(options){
			var defaultOpt={
				fields:[],//字段配置,可选
				data:null,//json对象或数组,{name:'a',age:1}或[{name:'a',age:1},{name:'b',age:2}]
				tableCls:null,//额外的table的class,可选
				tableAttr:null,//额外的table的属性,如'k1=v1 k2=v2',可选
				id:Math.ceil(Math.random()*100000),//表格ID，如果需要渲染为数据表格会用到这个,可选
				numbers:true//JSON数组是否展示行号
			};
			var defaultFieldOpt={
				field:'',//字段名称
				title:'',//字段描述
				//格式化，value当前值，data该行的值
				format:function(value,data){
					return value==null?'':value;
				}
			};
			
			options=$.extend(defaultOpt,options);
			
			//无数据
			if(!options.data||options.data.length==0)
				return '<table class="layui-table '+(options.tableCls||'')+'" '+(options.tableAttr||'')+'>'+
						    '<tr><td align="center">无数据</td></tr>'+	   
						'</table>';
			
			//没有定义fieldMap,
			if(!options.fields||options.fields.length==0){
				options.fields=[];
				if(options.data.length){//json数组，取字段并集
					var keySet={};//字段去重
					$.each(options.data,function(index,item){
						$.each(item,function(k,v){
							if(!keySet[k]){
								keySet[k]=k;
								options.fields.push({
									field:k,
									title:k
								});
							}
						});
					});
				}else{//json数据，取key
					$.each(options.data,function(key,value){
						options.fields.push({
							field:key,
							title:key
						});
					});
				}
			}
			//字段配置合并
			$.each(options.fields,function(index,field){
				field=$.extend({},defaultFieldOpt,field);
				options.fields[index]=field;
			});
			
			//开始拼接
			var html=[];
			html.push('<table lay-filter="'+options.id+'"'+
						' class="layui-table '+(options.tableCls||'')+'" '+
						(options.tableAttr||'')+'>');
			html.push('<thead><tr>');
			
			//json数组表头
			if(options.data.length){
				options.numbers&&html.push('<th lay-data="{type:\'numbers\'}">#</th>');
				$.each(options.fields,function(index,field){
					html.push('<th lay-data="{field:\''+field.field+'\',sort:true}">'+field.title+'</th>');
				});
			}
			html.push('</tr></thead>');
			html.push('<tbody>');
			
			if(options.data.length){//json数组内容
				$.each(options.data,function(index,item){
					html.push('<tr>');
					options.numbers&&html.push('<td>'+(++index)+'</td>');
					$.each(options.fields,function(i,field){
						var value=item[field.field];
						var cellHtml=field.format(value,item);
						html.push('<td>'+cellHtml+'</td>');
					});
					html.push('</tr>');
				});
			}else{//json数据内容
				$.each(options.fields,function(i,field){
					var value=options.data[field.field];
					var cellHtml=field.format(value,options.data);
					html.push('<tr><td>'+field.title+'</td><td>'+cellHtml+'</td></tr>');
				}); 
			}
			html.push('</tbody>');
			html.push('</table>');
			return html.join('');
		},
		
		/**
		 * 快速生成form的html
		 **/
		quickForm:function(config){
			var defaultOpt={
				id:Math.ceil(Math.random()*100000),//id
				formClass:null,//额外的formClass，可选
				split:3,//一行几个表单项,可选
				withValue:true,//是否带初始值，true需要传data,false不需要传data
				btnText:'确定修改',//按钮文字,可选
				btnCls:'',//按钮class，可选
				data:{},//渲染的数据,{field:value}
				fields:[]//表单项
			};
			//fields表单项默认配置
			var defaultField={
				field:null,//字段名称，必填
				lable:null,//字段描述，必填
				editType:'text',//编辑控件类型，支持text select multiSelect
				editable:true,//是否可编辑，true
				config:null//控件为select multiSelect的下拉配置，如[{text:xx,value:xx}]
			}
			config=$.extend(defaultOpt,config);
			var html=[];
			html.push('<form lay-filter="'+config.id+'" class="layui-form '+(config.formClass||'')+'">');
			var totalEditable=false;
			$.each(config.fields,function(index,field){
				if(index%(config.split)==0){//换行
					if(index!=0)
						html.push('</div>');
					html.push('<div class="layui-form-item">');
				}
				field=$.extend({},defaultField,field);
				
				html.push('<div class="layui-inline">');
				html.push('   <label class="layui-form-label">'+field.label+'</label>');
				html.push('	  <div class="layui-input-inline">');
				if(field.editType=='select'){
					html.push('  <select name="'+field.field+'" lay-search '+ 
							  '    lay-filter="'+field.field+' '+(field.editable?'':'disabled')+'">');
					html.push('     <option value="">请选择</option>');
					$.each(field.config,function(i,option){
						html.push(' <option value="'+option.value+'" ');
						if(config.withValue){
							html.push(option.value==config.data[field.field]?'selected':'');
						}
						html.push(' >'+option.text+'</option>');
					});
					html.push('  </select>');
				}else if(field.editType=='multiSelect'){
					html.push('  <select  name="'+field.field+'" lay-search xm-select '+ 
							  '   lay-filter="'+field.field+' '+(field.editable?'':'disabled')+'">');
					html.push('     <option value="">请选择</option>');
					var selectValue=config.data[field.field];
					$.each(field.config,function(i,option){
						html.push(' <option value="'+option.value+'" ');
						if(config.withValue){
							html.push((','+selectValue+',').indexOf(','+option.value+',')>-1?'selected':'');
						}
						html.push(' >'+option.text+'</option>');
					});
					html.push('  </select>');
				}else{//默认text
					html.push('  <input '+(field.editable?'':'disabled')+' type="text" ');
					if(config.withValue){
						html.push('value="'+(config.data[field.field]||'')+'"');
					}
					html.push('   placeholder="请输入" name="'+field.field+'" autocomplete="off" class="layui-input" >');
				}
				html.push('</div>');//layui-input-inline
				html.push('</div>');//layui-inline
				
				//至少有一个表单项可编辑，否则不展示按钮
				!totalEditable&&field.editable&&(totalEditable=true);
			});
			html.push('</div>');//form-item收尾
			//至少有一个表单项可编辑，否则不展示按钮
			if(totalEditable){
				html.push('<div style="position: absolute;bottom: 0px;right: 15px;text-align: right;padding: 12px 0px;">'+
						      '<button class="layui-btn '+config.btnCls+'" lay-submit lay-filter="sub_'+config.id+'">'+config.btnText+'</button>'+
						  '</div>');
			}
			html.push('</form>');
			return html.join('');
		},
		
		//传入一个date类型，输出格式化字符串
		//默认格式为yyyy-MM-dd HH:mm:ss 或
	    //yyyy-M-d H:m:s
		//MM两位月份，M一位月份，其它类似
		//小时是24小时制
		date2str:function(date,formatter){
			formatter=formatter||'yyyy-MM-dd HH:mm:ss';
			if(typeof date!='object'||!date)
				return '';
			var year=date.getFullYear();
			var month=date.getMonth()+1;
			var day=date.getDate();
			var hour=date.getHours();
			var minute=date.getMinutes();
			var second=date.getSeconds();
			formatter=formatter.replace(/yyyy/g,year)
							.replace(/MM/g,(month+'').length==1?'0'+month:month)
							.replace(/dd/g,(day+'').length==1?'0'+day:day)
							.replace(/HH/g,(hour+'').length==1?'0'+hour:hour)
							.replace(/mm/g,(minute+'').length==1?'0'+minute:minute)
							.replace(/ss/g,(second+'').length==1?'0'+second:second);
			return formatter=formatter.replace(/M/g,month)
										.replace(/d/g,day)
										.replace(/H/g,hour)
										.replace(/m/g,minute)
										.replace(/s/g,second);
		},
		
		//传入一个string类型，按格式化输入date
		//年月日必须有，即yyyy M|MM d|dd 参数必须有
		//时分秒可以无，即HH|H m|mm s|ss 参数可以无
		str2date:function(str,formatter){
			if(!str)
				return null;
			formatter=formatter||'yyyy-MM-dd HH:mm:ss';
			//截取年
			var year=str.substr(formatter.indexOf('yyyy'),'yyyy'.length);
			//截取月
			var month='';
			var startIndex=formatter.indexOf('MM');
			if(startIndex==-1){
				startIndex=formatter.indexOf('M');
				month=str.substr(startIndex,'M'.length);
			}else{
				month=str.substr(startIndex,'MM'.length);
			}
			month=parseInt(month)-1;
			//截取日
			var day='';
			var startIndex=formatter.indexOf('dd');
			if(startIndex==-1){
				startIndex=formatter.indexOf('d');
				day=str.substr(startIndex,'d'.length);
			}else{
				day=str.substr(startIndex,'dd'.length);
			}
			//截取时
			var hour='0';
			var startIndex=formatter.indexOf('HH');
			if(startIndex==-1){
				startIndex=formatter.indexOf('H');
				startIndex!=-1&&(
					hour=str.substr(startIndex,'H'.length)
				);
			}else{
				hour=str.substr(startIndex,'HH'.length);
			}
			//截取分
			var minute='0';
			var startIndex=formatter.indexOf('mm');
			if(startIndex==-1){
				startIndex=formatter.indexOf('m');
				startIndex!=-1&&(
					minute=str.substr(startIndex,'m'.length)
				);
			}else{
				minute=str.substr(startIndex,'mm'.length);
			}
			//截取秒
			var second='0';
			var startIndex=formatter.indexOf('ss');
			if(startIndex==-1){
				startIndex=formatter.indexOf('s');
				startIndex!=-1&&(
					second=str.substr(startIndex,'s'.length)
				);
			}else{
				second=str.substr(startIndex,'ss'.length);
			}
			//bug fix 
			//若day=0 修复为1，否则为上月最后一天
			day=='0'&&(day='1');
			return new Date(year,month,day,hour,minute,second);
		}
	}
	
	exports('util',methods);
});