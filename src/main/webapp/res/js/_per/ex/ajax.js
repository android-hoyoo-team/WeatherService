//ajax重写
(function($){     
   //备份jquery的ajax方法     
   var _ajax=$.ajax;     
        
   //重写jquery的ajax方法     
   $.ajax=function(opt){     
       //备份opt中error和success方法     
       var fn = {     
           error:function(XMLHttpRequest, textStatus, errorThrown){},     
           success:function(data, textStatus){}     
        }     
        if(opt.error){     
            fn.error=opt.error;     
        }     
        if(opt.success){     
            fn.success=opt.success;     
        }     
             
        //扩展增强处理     
        var _opt = $.extend(opt,{     
            error:function(XMLHttpRequest, textStatus, errorThrown){     
                //错误方法增强处理     
            	var status=XMLHttpRequest.status;
            	if(401==status){
            		window.top.location="index.do";
            		return false;
            	}else if(403==status){
            		window.location.href="/";
            		return false;
            	}
            	layer.msg("系统错误请联系管理员");
//            	else if(404!=status){
//            		layer.msg("系统错误请联系管理员");
//            		return false;
//            	}
            	console.info(textStatus);
                fn.error(XMLHttpRequest, textStatus, errorThrown);     
            },     
            success:function(data, textStatus){     
            	/*console.info(data);*/
                //成功回调方法增强处理     
                var error = data.error;  
                if (error != undefined && error == true) {  
                    return;  
                }  
                fn.success(data, textStatus);     
            }     
        });     
        _ajax(_opt);     
    };     
})(jQuery);    