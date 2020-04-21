layui.define(function(exports){
  exports('ajax',function(url,data,cb){
	  $.ajax({  
			url:url,  
			type: 'POST',  
			datType: 'json',  
			contentType: 'application/json',  
			data:JSON.stringify(data),  
			success: function (back) {
				if(back.code==0){
					cb(back.data);
				}else{
					layui.layer.msg(back.msg,{icon:2});   
				}
			}  
		}); 
  });
});    