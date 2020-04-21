/**
 * 默认模板(勿删勿改该文件)
 * 每新增一个Tableview，步骤如下
 * 1：在config目录下新增一个模板，js文件名称与全局变量名称一致，假设为tableObj,并配置好相应的参数
 * 2：菜单访问路径为../tableView/index.html?tableObj=tableObj
 *   还可以加queryParam参数，queryParam=base64(JSON.stringify(queryParamJsonArr))
 * **/
var tableDefaultObj = {
	//table名称
	table:'',
	//行的双击事件
	//如果为string，预设值有edit(编辑)
	//或者为function(data),data为该行的数据
	rowDbClick:'edit',
	//行toolbar点击事件
	toolEvent:function(obj){
		console.log(obj);
	},
	//首次table加载完成后的回调
	afterTableInit:function(){
		
	},
	//是否开启记录删除功能
	enableDel:true,
	//工具栏
	/**
	 * [{
	 * 	 text:'删除',
	 *   event:'del',
	 *   icon:'layui-icon-setting',
	 *   handler:function(){}
	 * }]
	 * **/
	toolbar:null,
	// 列配置
	cols : [ {
		// 字段名
		field : '',
		// 字段描述
		title : '',
		// 是否在table中展示,默认true展示
		inView : true,
		//是否在详情页面，如果为false,editable editType失效
		inDetail:true,
		// 是否可编辑,默认true可编辑
		editable : true,
		// 编辑控件类型，目前支持text,select,multiSelect,number,默认text
		editType : 'text',
		// 如果editType=select|multiSelect,下拉控件key&value配置,value如果为空则取key作为value
		//如[{key:'女',value:'woman'},{key:'男',value:'man'}]
		config : [],
		// 是否可搜索,默认为true
		search : true,
		// 搜索条件,equal精确匹配搜索,like模糊搜索，默认为equal
		searchCondition : 'equal',
		//表格列格式化,对编辑、搜索页面无效，参考layui,可选
		templet:null,
		//表格工具栏,对编辑、搜索页面无效，参考layui,可选
		toolbar:null
	} ]
}