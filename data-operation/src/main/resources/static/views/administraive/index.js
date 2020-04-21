var administraiveIndex_obj = {

    urlConfig: {
        // 获取行政区划数据
        administrativeList: '/administrative/list',
        // 修改行政区划
        administrativeModify: '/administrative/modify'	
    },

    tableIns: '',

    /**
	 * 页面初始化入口
	 */
    init: function () {
        this._displayTable();
    },

    /**
	 * 初始化表格信息
	 * 
	 * @private
	 */
    _displayTable: function () {
        var _this = this;
        // 表格初始化
        _this.tableIns = layui.table.render({
            elem: '#administraiveIndex_wrapper #administraiveTable',
            url: _this.urlConfig.administrativeList,
            method: 'get',
            where: {
					// 临时数据，验证逻辑用
				  mongoId: '2'	
            },
            title: '基础配置—行政区划配置',
            cols: [[
				{field: 'id', hide: 'true'},
				{field: 'name',title: '名称', edit: 'text'},
				{field: 'type',title:'类型'},
				{field: 'parentName',title:'父级名称'},
				{field: 'otherName',title:'曾用名', edit: 'text'},
				{field: 'simpleName',title:'简称', edit: 'text'},
				{field: 'comment',title:'备注', edit: 'text'}
				
		
            ]],
            page: false,
            id: 'administraiveTable'
        });
        
        
        var table = layui.table;
        // 监听单元格编辑
        table.on('edit(administraiveTable)', function(obj){
          var value = obj.value // 得到修改后的值
          ,data = obj.data // 得到所在行所有键值
          ,field = obj.field; // 得到字段
          layer.msg('[ID: '+ data.id +'] ' + field + ' 字段更改为：'+ value);
          
          $.post({
              url: _this.urlConfig.administrativeModify,
              data: {
            	id: data.id,
            	name: data.name,
            	type:data.type,
            	parentName:data.parentName,
            	otherName:data.otherName,
            	simpleName:data.simpleName,
            	comment:data.comment
             
            		  
              },
              success: function (res) {
//                 alert (res.data);
              }
          });
          
          
          
        });
    } 
};

administraiveIndex_obj.init();