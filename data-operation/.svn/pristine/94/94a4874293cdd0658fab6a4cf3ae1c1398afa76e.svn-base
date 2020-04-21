var dashboardIndex_obj = {

	url:'/dashboard',	
		
	init : function() {
		var _this=this;
		layui.ajax(_this.url,{},function(data){
			$.each(data,function(key,value){
				$('#dashboardIndex_wrapper #'+key).text(value);
			});
		});
	}
	
};

$(function() {
	dashboardIndex_obj.init();
});