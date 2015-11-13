loadJsCssFile = function(filename, filetype) {
	var r=Math.random();
	if (filetype == "js") {
		document.write("<script src='" + filename +'?r=' + r+ "' type='text/javascript'></script>");
	} else if (filetype == "css") { 
		var fileref = document.createElement("link")
		fileref.setAttribute("rel", "stylesheet")
		fileref.setAttribute("type", "text/css")
		fileref.setAttribute("href", filename)
	}
	if (typeof fileref != "undefined") document.getElementsByTagName("head")[0].appendChild(fileref)
};
loadJsFile_async=function(path,callback)
{
	var script = document.createElement('script');
	script.src = path + '?r=' + Math.random();
	script.type = "text/javascript";
	document.getElementsByTagName("head")[0].appendChild(script);
	if( typeof callback=="function")
	{
		if( script.addEventListener ) {
			script.addEventListener("load", callback, false);
		} else if(script.attachEvent) {
			script.attachEvent("onreadystatechange", function(){
				if(script.readyState == 4
						|| script.readyState == 'complete'
							|| script.readyState == 'loaded') {
					callback();
				}
			});
		}
	}
}
scripLoction = (function() {
	var r = new RegExp("(^|(.*?\\/))(load.js)(\\?|$)"),
	s = document.getElementsByTagName('script'),
	src, m, l = "";
	for (var i = 0, len = s.length; i < len; i++) {
		src = s[i].getAttribute('src');
		if (src) {
			m = src.match(r);
			if (m) {
				l = m[1];
				break;
			}
		}
	}
	return (function() {
		return l;
	});
})();
loadJsCssFileRelToLoadJs = function(filename, filetype) {
	loadJsCssFile(scripLoction()+filename,filetype);
}
loadJsFileRelToLoadJs_async = function(path,callBack,check) {
	if(typeof check =="undefined"||check==null)
	{
		loadJsFile_async(scripLoction()+path,callBack);
	}
	else if (typeof check=="function" && check())
	{
		loadJsFile_async(scripLoction()+path,callBack);
	}
}
$.fn.bindtouch = function(cb) {
    attachEvent($(this), cb);
};
function attachEvent(src, cb) {
    // $(src).unbind();
    var isTouchDevice = 'ontouchstart' in window || navigator.msMaxTouchPoints;
    if (isTouchDevice) {
        $(src).bind("touchstart",
        function(event) {
            $(this).data("touchon", true);
            $(this).addClass("pressed");
        });
        $(src).bind("touchend",
        function() {
            $(this).removeClass("pressed");
            if ($(this).data("touchon")) {
            	cb.apply(this,arguments);
                //cb.bind(this)(arguments);
            }
            $(this).data("touchon", false);
        });
        $(src).bind("touchmove",
        function() {
            $(this).data("touchon", false);
            $(this).removeClass("pressed");
        });
    } else {
    	$(src).click(cb);
        // $(src).bind("mousedown",
        // function() {
        //     $(this).addClass("pressed");
        //     $(this).data("touchon", true);
        // });
        // $(src).bind("mouseup",
        // function() {
        //     $(this).removeClass("pressed");
        //     $(this).data("touchon", false);
        //     cb.apply(this,arguments);
        //     //cb.bind(this)(arguments);
        // });
    }
}
// loadJsCssFileRelToLoadJs("jquery-1.11.2/jquery.js","js");
// loadJsCssFileRelToLoadJs("per/ex/Class.js","js");
// loadJsCssFileRelToLoadJs("per/ex/Utils.js","js");
// loadJsCssFileRelToLoadJs("per/ex/ArrayUtils.js","js");
// loadJsCssFileRelToLoadJs("per/ex/DateUtils.js","js");
// loadJsCssFileRelToLoadJs("per/ex/StringUtils.js","js");
// loadJsCssFileRelToLoadJs("per/class/camera.js","js");

// loadJsCssFileRelToLoadJs("swiper/swiper.min.js", "js");
// loadJsCssFileRelToLoadJs("fusioncharts/fusioncharts.js", "js");
// loadJsCssFileRelToLoadJs("fusioncharts/fusioncharts.theme.fint.js", "js");
// loadJsCssFileRelToLoadJs("ajaxfileupload/ajaxfileupload.js","js");
//  loadJsCssFileRelToLoadJs("store.js/store.js","js");
//  loadJsCssFileRelToLoadJs("store.js/local_store.js","js");
//  loadJsCssFileRelToLoadJs("cordova.js", "js");
//var globle_url="http://220.191.230.13:8080";
// var globle_url="http://10.41.87.82:9990";
 var globle_url="http://192.168.1.108:9990";