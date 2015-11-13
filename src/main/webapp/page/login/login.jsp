<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>后台管理</title>
<style>
.ui-keyboard-button{height: 45px !important;min-width: 45px !important;}
body{background-color: rgb(105,186,197) !important;}
.login_panel{background-image: url(../../res/img/login/formbg.png); width:458px; height:310px; margin:200px auto; overflow:hidden}
.login_ul{ list-style:none; margin:40px 20px;padding:0}
.login_ul li{margin:10px}
.login_ul li span{ display:inline-block; width:100px; text-align:right; font: normal 16px/38px '微软雅黑';padding:0 10px 0 0;color:#5ca6b1}
.text{background: url(styles/image/1.png)  no-repeat; width: 260px; color: rgb(174, 174, 174); font-family: '微软雅黑','宋体','黑体'; border:none; height: 31px;padding: 9px 12px;}
.heading{background-image: url(../../res/img/login/clipboard.png); width:442px; height:67px;margin:10px 7px 7px }
</style>
<script  src="../../res/js/load.js"></script>
    <script  type="text/javascript">
    	loadJsCssFileRelToLoadJs("../css/login/signin.css");
    	loadJsCssFileRelToLoadJs("../css/chanpinliebiao.css");
		//bootstrap
    	loadJsCssFileRelToLoadJs("bootstrap-3.3.0-dist/dist/css/bootstrap.css");
    	loadJsCssFileRelToLoadJs("bootstrap-3.3.0-dist/dist/js/bootstrap.min.js");
    	
    	//masonry
    	loadJsCssFileRelToLoadJs("masonry/dist/masonry.pkgd.min.js");
    	//imagesloaded
    	loadJsCssFileRelToLoadJs("imagesloaded/imagesloaded.js");
    	
    	//lightbox
    	loadJsCssFileRelToLoadJs("lightbox/css/lightbox.css");
    	loadJsCssFileRelToLoadJs("lightbox/lightbox-2.6.min.js");
    	
    	//store.js
    	loadJsCssFileRelToLoadJs("store.js/store.js");
    	
    </script>
<script type="text/javascript">
      
$(document).ready(function() {
	$("#login").click(function(){
		if($("#login_id").val()==null||$("#login_id").val()=="")
		return ;
		$.ajax({
			"dataType" : 'json',
			"type" : "get",
			"url" : "login.jsp",
			"data" : {
				login_id: $("#login_id").val()
			},
			"success" : function(data) {
				console.info(data);
				if(data.status=='error')
				{
					$("#add_login_info").html(data.message);
				}
				if(data.status=='success')
				{
					location.href="patient_main.jsp?path=1";
				}
			}
		});
		$("#add_login_info").html('正在登陆......');
	});

});
</script>
</head>

<body style="-webkit-user-select:none;-moz-user-select:none;-ms-user-select:none;user-select:none;">
			<a class="btn btn-primary" href="/info_index/index.jsp">返回主页</a>
<div class="login_panel">
	<p class="heading"></p>
<!--     <ul class="login_ul"> -->
<!--     	<div class="container"> -->
			<form class="form-signin" role="form" id="loginForm" onsubmit="return false;" >
				<h4 class="form-signin-heading text-center">管理员登陆</h4>
				<div class="form-group form-group-lg">
					<input id="login_id" type="text" class="form-control" placeholder="用户名" required autofocus>
				</div>
				<input type="password" class="form-control" placeholder="Password" required>
	<!-- 			<div class="checkbox"> -->
	<!-- 				<label> <input type="checkbox" value="remember-me"> -->
	<!-- 					Remember me -->
	<!-- 				</label> -->
	<!-- 			</div> -->
				<button class="btn btn-lg btn-primary btn-block" id="login">登	陆</button>
				<div class="col-sm-8 col-sm-offset-2">
					<span id="add_login_info" class="label label-danger"></span>
				</div>
			</form>
<!-- 		</div> -->
<!--     <form action="login.action" method="post" name="Login" id="loginForm"> -->
<%--     <li><span>用户名:</span><input type="text" class="text" maxlength="30" id="login_id" placeholder="身份证/医保卡/IC卡"  name="account" /></li> --%>
<%--     <li><span></span><a href="javascript:login()"><img src="styles/image/btn.png"></a></li> --%>
<%-- 	<li class="row"><div class="col-sm-8 col-sm-offset-2 label label-danger">${loginMsg==null?'':loginMsg}</div></li> --%>
<!--     </form> -->
<!--     </ul> -->
</div>

</body>
</html>
