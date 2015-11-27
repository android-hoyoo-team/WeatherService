<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
        <style>
      .col{
          width:40%;
          float:left;
          
      }
        .center{
            text-align: center;
        }
        .col input{
            width: 40%;
        }
        .tips{
            font-size: 12px;
            color: red;
        }
        #addItem{
            width: 100px;
            height: 40px;
        }
    </style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title></title>
    <script  src="../res/js/load.js"></script>
    <script  type="text/javascript">

   	//<!-- bootstrap -->
    	//loadJsCssFileRelToLoadJs("bootstrap-3.3.0-dist/dist/css/bootstrap.css");
    	//loadJsCssFileRelToLoadJs("bootstrap-3.3.0-dist/dist/js/bootstrap.min.js");
    	loadJsCssFileRelToLoadJs("ajaxfileupload/ajaxfileupload.js");
		loadJsCssFileRelToLoadJs("DataTables/media/js/jquery.dataTables.js","js");
		loadJsCssFileRelToLoadJs("DataTables/media/css/jquery.dataTables.min.css");
		loadJsCssFileRelToLoadJs("jquery-ui-1.11.2/jquery-ui.min.js","js");
		loadJsCssFileRelToLoadJs("jquery-ui-1.11.2/jquery-ui.min.css");
		//loadJsCssFileRelToLoadJs("jquery-ui-1.11.2/user-default.css");


    	loadJsCssFileRelToLoadJs("_per/ex/DataTablesUtils.js");
    	//<!-- lightbox --> 
    	loadJsCssFileRelToLoadJs("lightbox/css/lightbox.css");
    	loadJsCssFileRelToLoadJs("lightbox/lightbox-2.6.min.js");
        loadJsCssFileRelToLoadJs("colpick/js/colpick.js");
        loadJsCssFileRelToLoadJs("colpick/css/colpick.css");

    	
    </script>
 
    <script>
	var base_url="";
        function set_user_status(id,status)
        {
             $.ajax({
                 type:"GET",
                 url:"/update_user_status",
                 data:{
                    user_id:id,
                    status:status
                 },
                 datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
                 beforeSend:function(){
                 },
                 success:function(data){
                     if(data.status=="success")
                     {
                        $('#table_all_users').dataTable().fnClearTable(true);
                         show_message("设置成功",3);
                     }
                     else
                     {
                         show_message(data.message,10);
                     }
                    
                 },
                 complete: function(XMLHttpRequest, textStatus){
                 },
                 error: function(){
                 }         
              });
        }
        function update_user_images_status(id,status)
        {
             $.ajax({
                 type:"GET",
                 url:"/update_user_images_status",
                 data:{
                    id:id,
                    status:status
                 },
                 datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
                 beforeSend:function(){
                 },
                 success:function(data){
                     if(data.status=="success")
                     {
                        $('#table_user_images').dataTable().fnClearTable(true);
                         show_message("设置成功",3);
                     }
                     else
                     {
                         show_message(data.message,10);
                     }
                 },
                 complete: function(XMLHttpRequest, textStatus){
                 },
                 error: function(){
                 }         
              });
        }
        function showCurrentApkCode()
        {
        	$.ajax({
                type: "GET",
                url: "/get_sys_key_value",
                dataType: 'json',//"xml", "html", "script", "json", "jsonp", "text".
                data:{key:"m_apk_version"},
                success: function (data) {    
                    if(data.result&&data.status=="success")
                    {
                    	var v=JSON.parse(data.result.value);
                         // $("#current_apk_name").html(v.verName);
                          $("#current_apk_code").html(v.version);
                    }                        
                    
                    
                }
        	});
        }
        $(function () {
            getAllSysKeyValue();
            showCurrentApkCode();
            $("#tabs").tabs();
           
        
        t3_user_name=null;
        $("input[name='t3_user_name']").keyup(function(){
        	//do something
        	if(t3_user_name==$(this).val())
	        {
	        	//$(this).prop("checked",false); 
	        }else
	        {
	        	t3_user_name=$(this).val();
	        	//if(t3_user_name==null||t3_user_name.trim().length>0)
	        	$('#table_all_awards').dataTable().fnClearTable(true);
	        }
        });
        t3_serial_number=null;
        $("input[name='t3_serial_number']").blur(function(){
        	//do something
        	if(t3_serial_number==$(this).val())
	        {
	        	//$(this).prop("checked",false); 
	        }else
	        {
	        	t3_serial_number=$(this).val();
	        	//if(t3_serial_number==null||t3_serial_number.trim().length>0)
        	 	$('#table_all_awards').dataTable().fnClearTable(true);
	        }
        });
        t3_out_of_date="false";
        $("input[name='t3_out_of_date']").click(function(){
	        if(t3_out_of_date==$(this).val())
	        {
	        	t3_out_of_date=null;
	        	$(this).prop("checked",false); 
	        }else
	        {
	        	t3_out_of_date=$(this).val();
	        }
        	 $('#table_all_awards').dataTable().fnClearTable(true);
        });
        t3_exchange="false";
        $("input[name='t3_exchange']").click(function(){
	        if(t3_exchange==$(this).val())
	        {
	        	t3_exchange=null;
	        	$(this).prop("checked",false); 
	        }else
	        {
	        	t3_exchange=$(this).val();
	        }
        	 $('#table_all_awards').dataTable().fnClearTable(true);
        });
            $('#table_all_awards').DataTable(Util.extend(DataTablesUtils.getDefaultOption(), {
            "aoColumns": [
                { "sTitle": "序号", "mData": "id", "sClass": "center" },
                { "sTitle": "获奖名称", "mData": "awards", "sClass": "center" },
                { "sTitle": "用户名", "mData": "name", "sClass": "center" },
                { "sTitle": "手机号", "mData": "phoneNum", "sClass": "center" },
                { "sTitle": "获奖时间", "mData": "addTime", "sClass": "center" },
                { "sTitle": "获奖序号", "mData": "serialNumber", "sClass": "center" },
                { "sTitle": "是否兑换", "mData": "exchange", "sClass": "center" },
                { "sTitle": "过期时间", "mData": "expirationTime", "sClass": "center" }
                //{ "sTitle": "操作", "mData": "id", "sClass": "center" }

                      
            ],
            "order": [[ 0, "desc" ]],
            searching: false,
            //"aaData": [],
            "bPaginate": true,//翻页功能
            "sPaginationType": "full_numbers",
            sAjaxDataProp: 'aaData',
            //"bRetrieve": true,
            //"bDestroy": true,
            "bStateSave": true,
            "bProcessing": true,
            "bServerSide": true,
            "bSort": true,
            "sAjaxSource": base_url+"/get_user_awards",
            // "aaData": [],
            xhr: function () {
                console.info("XXXX");
                console.info(arguments);
            },
            "fnServerData": function (sSource, aoData, fnCallback) {
                console.info(sSource);
				var start=aoData.getElementByAttr({ name: "iDisplayStart" })[0].value;
				var length=aoData.getElementByAttr({ name: "iDisplayLength" })[0].value;
                var iSortCol_0=aoData.getElementByAttr({ name: "iSortCol_0" })[0].value;
                var mDataProp=aoData.getElementByAttr({ name: "mDataProp_"+iSortCol_0 })[0].value;
                var sSortDir_0=aoData.getElementByAttr({ name: "sSortDir_0" })[0].value;
                //
                aoData.push({ name: 'page', value: parseInt(start/length)+1 });
                aoData.push({ name: 'size', value: length });
                aoData.push({ name: 'order', value: sSortDir_0 });
                aoData.push({ name: 'sort', value: mDataProp });
                if(t3_exchange!=null)
                {
                	aoData.push({ name: 'exchange', value: t3_exchange });
                }
                if(t3_out_of_date!=null)
                {
                	aoData.push({ name: 'out_of_date', value: t3_out_of_date});
                }
                if(t3_user_name!=null)
                {
                	aoData.push({ name: 'user_name', value: t3_user_name});
                }
                if(t3_serial_number!=null)
                {
                	aoData.push({ name: 'serial_number', value: t3_serial_number});
                }
				var target=this;
                $.ajax( {
					"dataType": 'json', 
					"type": "get", 
					"url": sSource, 
					"data": aoData, 
					"success":function(data){
						if(data.status=="success")
						{
							var res_d=data.result; 
							var res={
			// 	                		"sEcho": 1,
										"iTotalRecords": res_d.total,
										"iTotalDisplayRecords":  res_d.total,
										"aaData":res_d.result
									};
							arguments[0]=res;
							fnCallback.apply(target,arguments);
						}
                        else 
                        {
                            if(data&&data.message)
                                show_message(data.message,10);
                        }
						
					 }
				  });
                ///fnCallback.apply(target, arg);
            },
            // "sDom" : "<'box-content-top'>rt<'box-content2'<'col-sm-3'l><'col-sm-3'i><'col-sm-6 text-right'p><'clearfix'>>",
            "aoColumnDefs": [
					{
						 width: "30px",
                       aTargets: [0]
                   },
				   {
						 width: "150px",
                       aTargets: [1]
                   },
					{
				       width: "150px",
                       aTargets: [2]
                   },
                    {
                       width: "150px",
                       aTargets: [3]
                   },
                    {
                        mRender: function (data, type, full, cell) {
                            //console.info(arguments);
                            return new Date(full.addTime).date2String("yyyy-MM-dd HH:mm:ss");
                        },
                        width: "150px",
                        aTargets: [4]
                    },
//                    {
//                       mRender: function (data, type, full, cell) {
//                            if(full.status==0)
//                            {
//                                 return "<a href=\"javascript:set_user_status('" + full.id + "',1)\">禁用</a>";
//                            }
//                           else
//                           {
//                                return "已禁用|<a href=\"javascript:set_user_status('" + full.id + "',0)\">解禁</a>";
//                           }
//                       },
//                       aTargets: [5]
//                   },
					{
                       width: "150px",
                       aTargets: [5]
                   },
					{
                	   mRender: function (data, type, full, cell) {
                           //console.info(arguments);
                           if(data=="true")
                        	{
                        	   if(full.expirationTime<=new Date().getTime())
                        		   return "已兑"
                        	   return "<a href=\"javascript:exchange('" + full.name+ "','"+full.serialNumber+"',false)\">兑换取消</a>";
                        	}
                           else  if(full.expirationTime<=new Date().getTime()) 
                        	{return "过期未兑";}
                           else 
                           {
                        	   return "<a href=\"javascript:exchange('" + full.name+ "','"+full.serialNumber+"',true)\">兑换</a>";
                        	}
                           //return new Date(full.addTime).date2String("yyyy-MM-dd hh:mm:ss");
                       },
                       width: "150px",
                       aTargets: [6]
                   },
                   {
                	   mRender: function (data, type, full, cell) {
                           //console.info(arguments);
                           if(data<=new Date().getTime())
                        	{
                        	   return new Date(data).date2String("yyyy-MM-dd HH:mm:ss");
                        	}
                           else
                        	{
                        	   return new Date(data).date2String("yyyy-MM-dd HH:mm:ss");
                        	}
                           //return new Date(full.addTime).date2String("yyyy-MM-dd hh:mm:ss");
                       },
                       width: "150px",
                       aTargets: [7]
                   },
                   {
                       sDefaultContent: '',
                       aTargets: ['_all']
                   }
            ]

        }));
            
           
    });
        $("#awards_color").val("#ff8800");
        function  exchange(name,num,exchange)
        {
        	 $.ajax({
                 type:"GET",
                 url:"/exchange",
                 data:{
                	 user_name:name,
                	 serial_num:num,
                	 exchange:exchange
                 },
                 datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
                 beforeSend:function(){
                 },
                 success:function(data){
                     if(data.status=="success")
                     {
                    	 show_message("兑奖成功",3);
                    	 $('#table_all_awards').dataTable().fnClearTable(true);
                     }
                     else
                    {
                    	 show_message(data.message,5);
                     }
//                     
//                     console.info(data);
                 },
                 complete: function(XMLHttpRequest, textStatus){
                 },
                 error: function(){
                 }         
              });
        }
    function show_message(d,time)
    {
        if (time == null)
            time = 3600 * 24;
         Messenger().post(function (data) {
            return {
                message: d+"[" + new Date().date2String("hh:mm:ss")+"]",
                type: 'info',
                hideAfter: time,
                actions: {
                    cancel: {
                        label: '确定',
                        action: function () {
                               this.hide();
                            //return msg.update({
                            //    message: '已取消',
                            //    type: 'success',
                            //    actions: false
                            //});
                        }
                    }
                }
            };
        }(d));
    }
        function getAllSysKeyValue()
        {
             $.ajax({
                 type:"GET",
                 url:"/get_sys_key_value",
                 data:{
                    key:null
                 },
                 datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
                 beforeSend:function(){
                 },
                 success:function(data){
                     if(data.status=="success")
                     {
                         for(var k in  data.result)
                         {
                             if(data.result[k].type==="sys")
                             {
                                 updatePage(data.result[k].key,data.result[k].value);
                             }
                             
                         }
//                        var m_start_images = data.result.getElementByAttr({ key: "m_start_image" ,type:'sys'});
//                         if(m_start_images&&m_start_images.length>0)
//                         {
//                              $("#m_start_image").attr("src","/"+m_start_images[0].value);
//                         }
                       
                     }
//                     
//                     console.info(data);
                 },
                 complete: function(XMLHttpRequest, textStatus){
                 },
                 error: function(){
                 }         
              });
        }
        var m_awards_info={
                    awards:
                           [
                           {res:"10闪币",color:"rgba(255,241,0,0.3)",no_aw:true,ran:0.1,img:""},
                           {res:"11闪币",color:"rgba(255,241,0,0.3)",no_aw:true,ran:0.1,img:""},
                           {res:"12闪币",color:"rgba(255,241,0,0.3)",no_aw:true,ran:0.1,img:""}
                          ]
                      };
        var m_awards_info1={
            restaraunts:["10闪币", "谢谢参与", "5闪币", "10M免费流量包", "20M免费流量包", "20闪币 ", "30M免费流量包", "100M免费流量包", "2闪币"],
            colors:["rgba(255,241,0,0.3)", "rgba(255,255,255,0.3)", "rgba(0,186,255,0.3)", "rgba(255,241,0,0.3)", "rgba(255,255,255,0.3)", "rgba(0,186,255,0.3)", "rgba(255,241,0,0.3)", "rgba(255,255,255,0.3)", "rgba(0,186,255,0.3)"]
        };
        var table_jiang_xiang_dt;
        var m_awards_info_object_list=[];
        function updatePage(key,value)
        {
             if(key==="m_start_image")
             {
                $("#m_start_image").attr("src","/"+value);
             }
             else if(key==="m_background_image")
             {
                $("#m_background_image").attr("src","/"+value);
             }
             else if(key==="m_awards_info")
             {
                if(value)
                {
                    try{
                        m_awards_info=JSON.parse(value);
                        m_awards_info_object_list=[];
                        for(var i =0;i< m_awards_info.awards.length;i++)
                        {
                            m_awards_info.awards[i].id=i;
                            m_awards_info_object_list.push(m_awards_info.awards[i]);
                        }
                        
                         if(table_jiang_xiang_dt)
                         {
                             //table_jiang_xiang_dt.fnClearTable(true);
                             //table_jiang_xiang_dt.fnDraw(false);
                             table_jiang_xiang_dt.fnDestroy(false);
                         }
                        //else
                        {
                             table_jiang_xiang_dt=$('#table_jiang_xiang').dataTable( {
                            "aaData": m_awards_info_object_list,
                            "aoColumns": [
                              { "sTitle": "序号",   "mData": "id"  ,sClass:"center"},
                              { "sTitle": "奖品名称",   "mData": "res" ,sClass:"center"},
                              { "sTitle": "背景",  "mData": "color" },
                              { "sTitle": "是否有奖",  "mData": "no_aw",sClass:"center" },
                              { "sTitle": "概率",  "mData": "ran",sClass:"center" },
                              { "sTitle": "操作", "mData": "id" }
                            ],
                            "aoColumnDefs": [
                                    {
                                       width: "30px",
                                       aTargets: [0]
                                   },
                                    {
                                    width: "260px",
                                       aTargets: [1]
                                   },
                                   {
                                        mRender: function (data, type, full, cell) {
                                            var s=$('<div class="color-box" style="width:30px;height:30px;margin:5px;border: 1px solid black;"></div>');
                                            s.css('background-color', data);
                                            return s[0].outerHTML;
                                            //$(el).css('background-color', '#'+hex);
                                           //return full.no_aw?"没奖品":"";
                                            
                                       },
                                       
                                         width: "50px",
                                       aTargets: [2]
                                   },
                                   {
                                       mRender: function (data, type, full, cell) {
                                           return full.no_aw?"没奖品":"";
                                            
                                       },
                                       width: "200px",
                                       aTargets: [3]
                                   },
                                    {
                                         width: "200px",
                                       aTargets: [4]
                                   },
                                    {
                                       mRender: function (data, type, full, cell) {
                                           return "<a href=\"javascript:delete_awards(" + full.id + ")\">删除</a>";
                                            
                                       },
                                       width: "120px",
                                       aTargets: [5]
                                   },
                                   {
                                       sDefaultContent: '',
                                       aTargets: ['_all']
                                   }
                            ]
                          });
                        }
                        
                       
                    }catch(ex)
                    {
                        show_message("执行错误:"+ex.message);
                    }
                }
             }
        }
        function delete_awards(id)
        {
            for(var i =0;i<m_awards_info_object_list.length;i++)
            {
                if(m_awards_info_object_list[i].id==id)
                {
                    m_awards_info.awards.splice(i,1);
                    m_awards_info_object_list.splice(i,1);
                    saveSysKeyValue("m_awards_info",JSON.stringify(m_awards_info));
                    break;
                }
            }
           
           // oTable.fnDeleteRow( id );
            
            
        }
        function edit_awards(id,data)
        {
            for(var i =0;i<m_awards_info_object_list.length;i++)
            {
                if(m_awards_info_object_list[i].id==id)
                {
                    m_awards_info.awards[i]=data;
                    m_awards_info_object_list[i]=data;
                    saveSysKeyValue("m_awards_info",JSON.stringify(m_awards_info));
                    break;
                }
            }
           
           // oTable.fnDeleteRow( id );
            
            
        }
        function saveSysKeyValue(key,value)
        {
             $.ajax({
                 type:"GET",
                 url:"/save_sys_key_value",
                 data:{
                    key:key,
                    value:value
                 },
                 datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
                 beforeSend:function(){
                 },
                 success:function(data){
                     if(data.status=="success")
                     {
                         updatePage(key,data.result.value); 
                         show_message("设置成功",3);
                     }
                     else
                     {
                         show_message(data.message,10);
                     }
                 },
                 complete: function(XMLHttpRequest, textStatus){
                 },
                 error: function(){
                 }         
              });
        }
        function ajaxApkFileUpload() {
        	//var apk_name=$("#apk_name").val();
        	var apk_code=$("#apk_code").val();
        	if(!apk_code)
        	{
        		 show_message("版本号不能空",5);
        	}
            var file=$("#apk_file_upload_src").val();
            if(!file)
            {
            	
             	show_message("请选择文件",10);
             	return;
            }
        	$.ajaxFileUpload({
        				url : "/apk/apk_upload",
        				secureuri : false,
        				fileElementId : "apk_file_upload_src",
        				dataType : 'json',
        				data:{code:apk_code},
        				success : function(data) {
                            //console.info(data);
                            if(data.status=="success")
                            {
                            	show_message("版本更新成功",10);
                            	$("#apk_file_upload_src").val(null);
                            	$("#apk_path").html("");
                            	showCurrentApkCode();
                                //saveSysKeyValue(key,data.result[0]);
                            }
                            else{
                                 show_message(data.message,10);
                            	$("#apk_path").html("");
                            }
//        						$("#text").show();
        				}
        	
        			});
        }
        function ajaxFileUpload(key,fileElementId) {
        	$.ajaxFileUpload({
        				url : "/file_upload",
        				secureuri : false,
        				fileElementId : fileElementId,
        				dataType : 'json',
        				success : function(data) {
                            //console.info(data);
                            if(data.status=="success")
                            {
                                saveSysKeyValue(key,data.result[0]);
                            }
                            else{
                                 show_message(data.message,10);
                            }
//        						$("#text").show();
        				}
        	
        			});
        }
        function init_awards()
        {
        	var init0={
                    awards:
                           [
                           {res:"10闪币",color:"rgba(255,241,0,0.3)",no_aw:true,ran:0.1,img:""},
                           {res:"11闪币",color:"rgba(255,241,0,0.3)",no_aw:true,ran:0.1,img:""},
                           {res:"12闪币",color:"rgba(255,241,0,0.3)",no_aw:true,ran:0.1,img:""}
                          ]
                      };
            var init={
                restaraunts:["10闪币", "谢谢参与", "5闪币", "10M免费流量包", "20M免费流量包", "20闪币 ", "30M免费流量包", "100M免费流量包", "2闪币"],
                colors:["rgba(255,241,0,0.3)", "rgba(255,255,255,0.3)", "rgba(0,186,255,0.3)", "rgba(255,241,0,0.3)", "rgba(255,255,255,0.3)", "rgba(0,186,255,0.3)", "rgba(255,241,0,0.3)", "rgba(255,255,255,0.3)", "rgba(0,186,255,0.3)"]
            }
             saveSysKeyValue("m_awards_info",JSON.stringify(init0));
        }
        function addItem_awards()
        {
            var awards_name =$("#awards_name").val();
            var awards_color =$("#awards_color").val();
            
            var awards_has_no =$("#awards_has_no").is(':checked')==true?true:false;
            var awards_ran =$("#awards_ran").val();

            
            if(awards_name==null||awards_name.trim().length<=0)
            {
                show_message("奖品名称不能为空",5);
                return;
            }
            if(awards_color==null||awards_color.trim().length<=0)
            {
                show_message("奖品区对应背景不能为空",5);
                return ;
            }
            if(awards_ran==null||awards_ran.trim().length<=0)
            {
                show_message("获奖概率不能为空",5);
                return ;
            }
            m_awards_info.awards.push({res:awards_name,color:awards_color,no_aw:awards_has_no,ran:awards_ran,img:""});
            saveSysKeyValue("m_awards_info",JSON.stringify(m_awards_info));
        }
        function fa_jiang_init(user_id)
        {
        	$("#fa_jiang_user_id").val(user_id);
        	if(m_awards_info&&m_awards_info.awards&&m_awards_info.awards.length>0)
        	{
        		 $("#fa_jiang_awards").empty();
        		for(var k in m_awards_info.awards)
        		{
	        		var d=m_awards_info.awards[k];
        			if(d.no_aw==false)
	        		 $("#fa_jiang_awards").append("<option value='" + d.res + "' >" + d.res + "</option>");
        			
        		}
        	}
        	 var dialog = $("#fa_jiang_dialog").dialog({
                 autoOpen: false,
                 width: 450,
                 //height:400,
                 buttons: {
                     "确认": function () {
                         var fa_jiang_awards = $("#fa_jiang_awards").val();
                         if(fa_jiang_awards==null||fa_jiang_awards.trim().length<=0)
                         {
                              show_message("奖项不能为空",3);
                             return;
                         }
                         var fa_jiang_expiration_time = $("#fa_jiang_expiration_time").val();
                         $.ajax({
                             type:"GET",
                             url:"/save_user_awards",
                             data:{
                                res:fa_jiang_awards,
                                user_id:user_id,
                                expiration_time:fa_jiang_expiration_time==null||fa_jiang_expiration_time.trim().length<=0?null:Number(fa_jiang_expiration_time.trim())*24*60*60*1000
                             },
                             datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
                             beforeSend:function(){
                             },
                             success:function(data){
                                 if(data.status=="success")
                                 {
                                     //updatePage(key,data.result.value); 
                                     show_message("奖品发放成功",3);
                                     dialog.dialog("close");
                                 }
                                 else
                                 {
                                     show_message(data.message,10);
                                 }
                             },
                             complete: function(XMLHttpRequest, textStatus){
                             },
                             error: function(){
                             }         
                          });
                     },
                     "取消": function () {
                         dialog.dialog("close");
                     }
                 }
             });
             $("#fa_jiang_dialog").dialog("open");
        }
        function change_password()
        {
        	var old_password = $("#old_pwd").val();
            if(old_password==null||old_password.trim().length<=0)
            {
                show_message("原始密码不能为空",3);
                return;
            }
            var password = $("#new_pwd").val();
            if(password==null||password.trim().length<=0)
            {
                show_message("密码不能为空",3);
                return;
            }
            $.ajax({
                type:"GET",
                url:"/admin/change_password",
                data:{
                	old_password:old_password,
                	password:password
                },
                datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
                beforeSend:function(){
                },
                success:function(data){
                    if(data.status=="success")
                    {
                        //updatePage(key,data.result.value); 
                        show_message("修改成功",3);
                    }
                    else
                    {
                        show_message(data.message,10);
                    }
                },
                complete: function(XMLHttpRequest, textStatus){
                },
                error: function(){
                }         
             });
        }
    </script>
    <style>
    </style>
</head>
<body>
    <div id="tabs">
        <ul>
            <li><a href="#tabs-3">用户获奖</a></li>
            <li><a href="#tabs-7">修改密码</a></li>
        </ul>
        <div id="tabs-3">   
        	<label><input name="t3_exchange" type="radio" value="true" />已兑 </label>
			<label><input name="t3_exchange" type="radio" value="false" checked />未兑 </label>
			<label><input name="t3_out_of_date" type="radio" value="true" />过期 </label>
			<label><input name="t3_out_of_date" type="radio" value="false" checked/>未过期 </label>
			用户名: <input type="text" name="t3_user_name" />
			获奖序号: <input type="text" name="t3_serial_number" />
            <table id="table_all_awards" class="display" cellspacing="0" width="100%">
            </table>
            
        </div>
        <div id="tabs-7">
                  <div style="text-align:center;">
                <div>
                    <label for="" style="display:inline-block;width:150px;text-align:right;line-height:40px;">请输入旧密码：</label>
                    <input type="password" id="old_pwd" style="height:25px;:200px;">
                </div>
                <div>
                    <label for="" style="display:inline-block;width:150px;text-align:right;line-height:40px;">请输入新密码：</label>
                    <input type="password" id="new_pwd" style="height:25px;:200px;">
                </div>
                <div style="margin-top:10px;">
                    <input type="button" id="change_password" onclick="change_password();" value="确认修改密码" style="height:30px;width:100px;">
                </div>
             </div>
         </div>
    </div>
    </div>
	<div id="fa_jiang_dialog" title="发奖" style="display: none;">
		<form>
			<fieldset>
				<input type="hidden" style="width: 100%;" readonly name="user_id"
					id="fa_jiang_user_id" class="text ui-widget-content ui-corner-all">
				<label style="width: 100%;">奖品</label> 
				<select name="fa_jiang_awards" id="fa_jiang_awards" style="width: 90px;">
				</select>
				<label style="width: 100%;clear:both;">有效期/天</label> 
				<input	style="width: 100%;" type="text" name="expiration_time"
					id="fa_jiang_expiration_time"
					class="text ui-widget-content ui-corner-all"> 
				 <label id="bang_ding_message" style="color: red;"></label>
			</fieldset>
		</form>
	</div>
</body>
    <script>
        $(function () {
           // $('#table_jiang_xiang').DataTable();    

        });
    </script>
</html>
