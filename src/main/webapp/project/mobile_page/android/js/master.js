setTimeout(function() {
    document.getElementById("guanggao").style.display = "none";
    // document.getElementById("footerMenu").style.display="block";               
}, 3000);
function alertMessage(ele,msg){
    var param={
        message: msg, 
        fadeIn: 700, 
        fadeOut: 700, 
        timeout: 2000, 
        showOverlay: false, 
        // centerY: true, 
        // centerX:true,
        css: { 
            width: '60%', 
            top: '40%', 
            left: '20%', 
            border: 'none', 
            padding: '10px', 
            backgroundColor: '#000', 
            '-webkit-border-radius': '10px', 
            '-moz-border-radius': '10px', 
            opacity: .8, 
            color: '#fff',
            fontSize:"14px"
        } 
    };
    if(ele==null){        
        $.blockUI(param);
    }else{
        ele.block(param);
    }
}
var os_type="";
var localhost_weather = store.get('localhost_weather');
var stations_int = null;
var global_pm25 = null; //pm25
var global_ion = null; //负氧离子
var lineChartLoaded=false;//标记天气预报图标是否已经加载过
var global_address={
};
var global_info = {
    st: {
        global_ready: false, //DOM加载成功
        o2_ready: false, //负氧离子仪表盘渲染完成
        get_lastest_probe_oxy_per_hour_success: false, //获取了首页仪表盘的负氧离子和天气预报页面的负氧离子
        get_latest_o2_per_hour_by_stations_success: false, //获取了西湖、西溪湿地的负氧离子
        // get_avg_province_lastest_probe_oxy_per_hour_success:false,//负氧离子全省平均

        pm25_ready: false, //PM25仪表盘渲染完成
        get_avg_lastest_probe_envi_per_hour_success: false, //首页仪表盘PM25=天气预报页面PM25
        // get_avg_station_num_probe_envi_per_hour_success:false,//杭州某站点
        // get_lastest_probe_envi_per_hour_success:false,//天气预报页面PM25
        get_avg_province_probe_envi_per_hour_success: false, //PM25全省平均
        // get_avg_latest_probe_envi_per_hour_by_citys_success:false,//杭州平均

        get_chinese_calendar_success: false, //日历

        get_lastest_probe_aws_per_hour_success: false, //风速、温度
        get_fcst_fine_city_by_stationnum_success: false, //未来三天天气预报                            
        broadcast_ready: false,
        get_town_info_success: false, //旅游景点
        get_fcst_live_index_by_qxzs_success: false,
        get_fcst_live_index_by_sszs_success: false,
        // get_lastest_area_probe_aws_per_hour_success:false,
        get_lastest_station_probe_aws_per_hour_success: false,
        get_sys_key_value_success: false, //奖项设置
        get_sys_key_value_awards_intr_success: false, //有奖活动说明文字
        get_sys_key_value_awards_open_success: false, //有奖活动页面是否对外开放
        get_user_awards_success: false,
        save_user_awards_success: false,
        get_weather_desc_type_success: false,
        get_wind_s_type_success: false,
        get_wind_d_type_success: false,
        weather_is_ready: false
    },
    d: {
        device_ready:false,
        o2: null, //天气预报页面的负氧离子
        o2_avg: null, //首页仪表盘显示的负氧离子
        o2_stations: null, //西湖、西溪湿地负氧离子
        // o2_province_avg:null,//负氧离子全省平均
        // o2_xixi:null,
        // o2_xihu:null,

        pm25_avg: null, //首页仪表盘PM25=天气预报页面PM25
        // pm25_hangzhou2:null,//杭州某站点PM25
        // pm25:null,//天气预报页面pm25
        pm25_province_avg: null, //PM25全省平均
        // pm25_hangzhou:null,

        chineseDate: null,

        temprature: null, //天气预报页面温度
        speed: null, //天气预报页面风速
        weather: null,
        weatherJson: null,
        glable_weaDesc48: null, //在未来三天天气预报中获取到，并用于旅游景点
        glable_ming_weaDesc: null, //在未来三天天气预报中获取到，并用于旅游景点
        jing_dian: null,
        jingdianData: null,
        qingxin_text: null,
        shushi_grade: null,
        shushi_text: null,
        wendu: null,
        shidu: null,
        qiya: null,
        awards: null,
        awards_intro: null, //有奖活动说明文字
        awards_open: null, //有奖活动对外开放
        user_awards: null,
        awards_txt: null //用户所中奖项
    }
};
var csatGauge, csatGauge3; //两个仪表盘
var lineChart=null;
var tableHtml="";
var turnplate = {
    restaraunts: [], //大转盘奖品名称
    colors: [], //大转盘奖品区块对应背景颜色
    outsideRadius: 170, //大转盘外圆的半径
    textRadius: 155, //大转盘奖品位置距离圆心的距离
    insideRadius: 62, //大转盘内圆的半径
    startAngle: 0, //开始角度
    bRotate: false //false:停止;ture:旋转
};
//获取并设置地理位置
function onDeviceReady() {    
    var useragent = navigator.userAgent;
    if(useragent.indexOf('Android') > -1 || useragent.indexOf('Linux') > -1){
        os_type="android";
    }else if(!!useragent.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/)){
        os_type="ios";
    }
    getAddressAndUpdate();
    setInterval(getAddressAndUpdate,600000);    
}
document.addEventListener("deviceready", onDeviceReady, false);
function getAddressAndUpdate(){
    var locationOBJ={};
    $("body").bind("current_address_ready",function(event,param){
        locationOBJ.longitude=param.locationOBJ.longitude;
        locationOBJ.latitude=param.locationOBJ.latitude;
        locationOBJ.city=param.locationOBJ.city;
        locationOBJ.district=param.locationOBJ.district;
        locationOBJ.street=param.locationOBJ.street;
        locationOBJ.errMsg=param.locationOBJ.errMsg;
        if(global_address.longitude!=null){
            if(locationOBJ.errMsg!=null){
                $("#box0_address").html(locationOBJ.errMsg);
                $("body").unbind("current_address_ready");
                return;
            }else{                
                if(locationOBJ.street==global_address.street || locationOBJ.district==global_address.district || locationOBJ.city==global_address.city){
                    // $("#box0_address").html(locationOBJ.errMsg);
                    $("body").unbind("current_address_ready");
                    return;
                }
            }
        }
        global_address.longitude=locationOBJ.longitude;
        global_address.latitude=locationOBJ.latitude;
        global_address.city=locationOBJ.city;
        global_address.district=locationOBJ.district;
        global_address.street=locationOBJ.street;
        global_address.errMsg=locationOBJ.errMsg;
        if(global_address.errMsg==null){        
            // alert(global_address.city); 
            $("#box0_address").html(global_address.city + "<font style='font-size:16px;'>" + global_address.district + "</font>" + global_address.street);
        }else{
            $("#box0_address").html(global_address.errMsg);
        }
        //触发更新数据事件
        $("body").trigger("shouye_o2_get");
        $("body").trigger("tianqiyubao_wind_and_temp_get");     
        $("body").unbind("current_address_ready");
    });
    $("body").trigger("get_current_address",{os_type:os_type});   
}
//                    getMainData();
getMainData2();
//                    getTianqiData();
getTianqiData2();
getEnvirData();
configureAward();
getUserAward();
//绑定获取当前位置get_current_address事件，位置信息存入locationOBJ，并触发current_address_ready事件
$("body").bind("get_current_address",function(event,param){
    var locationOBJ={};
    if(param.os_type=="android"){
        var noop = function() {};
        window.locationService.getCurrentPosition(function(pos) {
            var point = new BMap.Point(pos.coords.longitude, pos.coords.latitude); // 创建点坐标
            var gc = new BMap.Geocoder();
            gc.getLocation(point, function(rs) {
                if(rs && rs.addressComponents){
                    // alert(pos.coords.longitude +":"+pos.coords.latitude);
                    locationOBJ.longitude=pos.coords.longitude;
                    locationOBJ.latitude=pos.coords.latitude;
                    locationOBJ.city=rs.addressComponents.city;
                    locationOBJ.district=rs.addressComponents.district;
                    locationOBJ.street=rs.addressComponents.street;
                    locationOBJ.errMsg=null;
                    // alert(locationOBJ.city+":"+locationOBJ.district+":"+locationOBJ.street+"hhhhhhhh");
                    $("body").trigger("current_address_ready",{locationOBJ:locationOBJ});
                }else{ 
                    locationOBJ.longitude=120.4;
                    locationOBJ.latitude=29.0;
                    locationOBJ.city=null;
                    locationOBJ.district=null;
                    locationOBJ.street=null;               
                    locationOBJ.errMsg="定位失败！";
                    $("body").trigger("current_address_ready",{locationOBJ:locationOBJ});
                }
            // $("#box0_address").html(addComp.city + "<font style='font-size:16px;'>" + addComp.district + "</font>" + addComp.street);
            });
            window.locationService.stop(noop, noop);
        }, function(e) {
            locationOBJ.longitude=120.4;
            locationOBJ.latitude=29.0;
            locationOBJ.city=null;
            locationOBJ.district=null;
            locationOBJ.street=null;
            locationOBJ.errMsg="位置获取失败";
            $("body").trigger("current_address_ready",{locationOBJ:locationOBJ});
            // $("#box0_address").html("位置获取失败");
            window.locationService.stop(noop, noop);
        });
    }else if(param.os_type=="ios"){
        if(navigator.geolocation){
            navigator.geolocation.getCurrentPosition(function (pos) {
                var point = new BMap.Point(pos.coords.longitude, pos.coords.latitude);
                var gc = new BMap.Geocoder();
                gc.getLocation(point, function(rs) {
                    if(rs && rs.addressComponents){
                        locationOBJ.longitude=pos.coords.longitude;
                        locationOBJ.latitude=pos.coords.latitude;
                        locationOBJ.city=rs.addressComponents.city;
                        locationOBJ.district=rs.addressComponents.district;
                        locationOBJ.street=rs.addressComponents.street;
                        locationOBJ.errMsg=null;
                        $("body").trigger("current_address_ready",{locationOBJ:locationOBJ});
                    }else{
                        locationOBJ.longitude=120.4;
                        locationOBJ.latitude=29.0;
                        locationOBJ.city=null;
                        locationOBJ.district=null;
                        locationOBJ.street=null; 
                        locationOBJ.errMsg="定位失败！";
                        $("body").trigger("current_address_ready",{locationOBJ:locationOBJ});
                    }
                    // var addComp = rs.addressComponents;
                    // $("#box0_address").html(addComp.city + "<font style='font-size:16px;'>" + addComp.district + "</font>" + addComp.street);
                });
            }, function(error){
                locationOBJ.longitude=120.4;
                locationOBJ.latitude=29.0;
                locationOBJ.city=null;
                locationOBJ.district=null;
                locationOBJ.street=null;
                switch (error.code) {
                    case error.TIMEOUT:
                        locationOBJ.errorMsg="定位超时！";
                        break;
                    case error.POSITION_UNAVAILABLE:
                        locationOBJ.errorMsg="地理位置不可用！";
                        break;
                    case error.PERMISSION_DENIED:
                        locationOBJ.errorMsg="用户拒绝提供地理位置!";
                        break;
                    case error.UNKNOWN_ERROR:
                        locationOBJ.errorMsg="定位失败！";
                        break;
                }
                $("body").trigger("current_address_ready",{locationOBJ:locationOBJ});
            }, {
                //是否使用高精度设备，入GPS。默认是true
                enableHighAcuracy: true,
                timeout: 10000,
                //使用设置时间内的缓存数据，单位毫秒
                //默认为0，即时钟使用缓存数据
                //如设置为Infinity，则始终使用缓存数据
                // maximumAge:3000
            });
        }else{
            locationOBJ.longitude=120.4;
            locationOBJ.latitude=29.0;
            locationOBJ.city=null;
            locationOBJ.district=null;
            locationOBJ.street=null;
            locationOBJ.errorMsg="定位失败！";
            $("body").trigger("current_address_ready",{locationOBJ:locationOBJ});
        }
    }
});
//绑定获取负氧离子事件
$("body").bind("shouye_o2_get",function(event,param){
    $.ajax({
        type: "GET",
        url: globle_url + "/oxy/get_lastest_probe_oxy_per_hour",
        //                            data: { longitude: localhost_weather.longitude, latitude: localhost_weather.latitude },
        data: {
            /*longitude: global_position.longitude,
            latitude: global_position.latitude*/
            longitude: global_address.longitude,
            latitude: global_address.latitude
        },
        dataType: 'json', //"xml", "html", "script", "json", "jsonp", "text".
        beforeSend: function() {},
        success: function(data) {
            if (data.status == "success") {
                var o2 = null;
                var o2_avg = null;
                var res = null;
                var res_area = null;
                var res_avg = null;
                var localhost_weather_data = store.get('localhost_weather').data;
                if (data.result) {
                    if (data.result.latest_distance) {
                        res = data.result.latest_distance;
                        if (res.o2) {
                            o2 = res.o2;
                        }
                    } else if (data.result.latest_area) {
                        res_area = data.result.latest_area;
                        if (res_area.o2) {
                            o2 = res_area.o2;
                        }
                    }
                    if (data.result.latest_area_avg) {
                        res_avg = data.result.latest_area_avg;
                        if (res_avg.O2) {
                            o2_avg = res_avg.O2;
                        }
                    }
                }
                $("body").trigger("get_lastest_probe_oxy_per_hour_success", {
                    o2: o2,
                    o2_avg: o2_avg
                });
            }
            console.info(data);
        },
        complete: function(XMLHttpRequest, textStatus) {},
        error: function() {}
    });    
})

function getMainData2() {
    //获取负氧离子全省平均
    // $.ajax({
    //     type: "GET",
    //     url: globle_url + "/oxy/get_avg_province_lastest_probe_oxy_per_hour",
    //     dataType: 'json',//"xml", "html", "script", "json", "jsonp", "text".
    //     beforeSend: function () {
    //     },
    //     success: function (data) {                               
    //         if (data.status == "success") {                                 
    //             var res = data.result;                                   
    //             var o2 = (res.O2).toFixed(0);
    //             $("body").trigger("get_avg_province_lastest_probe_oxy_per_hour_success", {o2_province_avg:o2});
    //         }
    //     },
    //     complete: function (XMLHttpRequest, textStatus) {
    //     },
    //     error: function () {
    //     }
    // }); 
    //获取西湖、西溪湿地负氧离子
    $.ajax({
        type: "GET",
        url: globle_url + "/oxy/get_latest_o2_per_hour_by_stations",
        dataType: 'json', //"xml", "html", "script", "json", "jsonp", "text".
        beforeSend: function() {},
        success: function(data) {
            if (data.status == "success") {
                var res = null;
                if (data.result) {
                    res = data.result;
                }
                $("body").trigger("get_latest_o2_per_hour_by_stations_success", {
                    o2_stations: res
                });
            }
        },
        complete: function(XMLHttpRequest, textStatus) {},
        error: function() {}
    });
    //获取首页仪表盘PM25数值=天气预报页面PM25
    $.ajax({
        type: "GET",
        url: globle_url + "/get_avg_lastest_probe_envi_per_hour",
        dataType: 'json', //"xml", "html", "script", "json", "jsonp", "text".
        beforeSend: function() {},
        success: function(data) {
            if (data.status == "success") {
                var res = null;
                var pm25 = null;
                if (data.result) {
                    res = data.result;
                    if (res.pm25) {
                        pm25 = res.pm25.toFixed(1); //保留一位小数点，后续要分开处理
                    }
                }
                $("body").trigger("get_avg_lastest_probe_envi_per_hour_success", {
                    pm25_avg: pm25
                });
            }
        },
        complete: function(XMLHttpRequest, textStatus) {},
        error: function() {}
    });
    //杭州某站点PM25
    // $.ajax({
    //     type: "GET",
    //     url: globle_url + "/get_avg_station_num_probe_envi_per_hour",
    //     data: { station_num: 58457},
    //     dataType: 'json',//"xml", "html", "script", "json", "jsonp", "text".
    //     beforeSend: function () {
    //     },
    //     success: function (data) {                               
    //         if (data.status == "success") {     
    //             var pm25=null;
    //             var res = data.result;
    //             if(res && res.pm25!=null){
    //                 pm25=(res.pm25).toFixed(1);
    //             }                            
    //             $("body").trigger("get_avg_station_num_probe_envi_per_hour_success", {pm25_hangzhou2:pm25});                           
    //         }
    //     },
    //     complete: function (XMLHttpRequest, textStatus) {
    //     },
    //     error: function () {
    //     }
    // }); 

    // 获取PM25全省平均
    $.ajax({
        type: "GET",
        url: globle_url + "/get_avg_province_probe_envi_per_hour",
        dataType: 'json', //"xml", "html", "script", "json", "jsonp", "text".
        beforeSend: function() {},
        success: function(data) {
            if (data.status == "success") {
                var res = null;
                var pm25 = null;
                if (data.result) {
                    res = data.result;
                    pm25 = res.pm25;
                    // pm25=(res.pm25).toFixed(1);
                }
                /*if(res.pm25!=null){                                        
                    pm25 = (res.pm25).toFixed(1);
                }*/
                $("body").trigger("get_avg_province_probe_envi_per_hour_success", {
                    pm25_province_avg: pm25
                });
            }
        },
        complete: function(XMLHttpRequest, textStatus) {},
        error: function() {}
    });

    // 杭州平均PM25
    //  $.ajax({
    //     type: "GET",
    //     url: globle_url + "/get_avg_latest_probe_envi_per_hour_by_citys",
    //     dataType: 'json',//"xml", "html", "script", "json", "jsonp", "text".
    //     beforeSend: function () {
    //     },
    //     success: function (data) {                               
    //         if (data.status == "success") {                                 
    //             var res = data.result;
    //             var pm25_hangzhou=null;
    //             if(res!=null && res.length>0){
    //                 pm25_hangzhou=(res[0].pm25).toFixed(1);
    //             }
    //             $("body").trigger("get_avg_latest_probe_envi_per_hour_by_citys_success", {pm25_hangzhou:pm25_hangzhou});
    //         }
    //     },
    //     complete:function (XMLHttpRequest, textStatus) {
    //     },
    //     error: function () {
    //     }
    // });


    //获取日期数据
    $.ajax({
        type: "GET",
        url: globle_url + "/get_chinese_calendar",
        data: {},
        dataType: 'json', //"xml", "html", "script", "json", "jsonp", "text".
        beforeSend: function() {},
        success: function(data) {
            console.info(data);
            var chineseDate = null;
            if (data) {
                chineseDate = data.simple_gregorian_date + " " + data.day_of_week + " " + " 农历" + data.chinese_year + " " + data.chinese_month + data.chinese_date;
            }
            $("body").trigger("get_chinese_calendar_success", {
                chineseDate: chineseDate
            });
        },
        complete: function(XMLHttpRequest, textStatus) {},
        error: function() {}
    });
}
//天气预报页面获取温度和风速事件
$("body").bind("tianqiyubao_wind_and_temp_get",function(event,param){
    $.ajax({
        type: "GET",
        url: globle_url + "/get_lastest_probe_aws_per_hour",
        //                            data: { longitude: localhost_weather.longitude, latitude: localhost_weather.latitude },
        data: {
            longitude: global_address.longitude,
            latitude: global_address.latitude
        },
        dataType: 'json', //"xml", "html", "script", "json", "jsonp", "text".
        beforeSend: function() {},
        success: function(data) {
            if (data.status == "success") {
                //var localhost_weather_data = store.get('localhost_weather').data;
                var t = null;
                var ff = null;
                var res, res_area;
                if (data.result) {
                    res = data.result.latest_distance;
                    res_area = data.result.latest_area;
                    if (res) {
                        t = res.t;
                        ff = res.ff;
                    }
                }
                $("body").trigger("get_lastest_probe_aws_per_hour_success", {
                    temprature: t,
                    speed: ff
                });
            }
            console.info(data);
        },
        complete: function(XMLHttpRequest, textStatus) {},
        error: function() {}
    });
});
$("body").bind("tianqiyubao_duanqiyubao_get",function(event,param){
    $.ajax({
        type: "GET",
        // async: false,
        url: globle_url + "/get_fcst_fine_city_by_stationnum",
        data: {
            longitude: global_address.longitude,
            latitude: global_address.latitude
        },
        //                            data: { longitude: global_position.longitude, latitude: global_position.latitude },
        dataType: 'json', //"xml", "html", "script", "json", "jsonp", "text".
        success: function(data) {
            if (data.status == "success") {
                //var localhost_weather_data= store.get('localhost_weather').data;
                var res = null;
                if (!data || !data.result || data.result.length <= 0) {
                    return;
                }
                if (data.result && data.result.length > 0) {
                    res = data.result[0];
                    var temp24H = parseFloat(res.temp24H).toFixed(0);
                    var temp24L = parseFloat(res.temp24L).toFixed(0);
                    var temp48H = parseFloat(res.temp48H).toFixed(0);
                    var temp48L = parseFloat(res.temp48L).toFixed(0);
                    var temp72H = parseFloat(res.temp72H).toFixed(0);
                    var temp72L = parseFloat(res.temp72L).toFixed(0);
                    var weaDesc24 = res.weaDesc24;
                    var weaDesc48 = res.weaDesc48;
                    var weaDesc72 = res.weaDesc72;
                    var weaDesc96 = res.weaDesc96;
                    var windD24 = res.windD24;
                    var windD48 = res.windD48;
                    var windD72 = res.windD72;
                    var windD24_ = localhost_weather.wind_d_type[windD24];
                    var windD48_ = localhost_weather.wind_d_type[windD48];
                    var windD72_ = localhost_weather.wind_d_type[windD72];
                    var windS24 = res.windS24;
                    var windS48 = res.windS48;
                    var windS72 = res.windS72;
                    var windS24_ = localhost_weather.wind_s_type[windS24];
                    var windS48_ = localhost_weather.wind_s_type[windS48];
                    var windS72_ = localhost_weather.wind_s_type[windS72];
                    var weaDesc24_ = localhost_weather.weather_desc_type[weaDesc24];
                    var weaDesc48_ = localhost_weather.weather_desc_type[weaDesc48];
                    var weaDesc72_ = localhost_weather.weather_desc_type[weaDesc72];
                    var weaDesc96_ = localhost_weather.weather_desc_type[weaDesc96];
                    var glable_weaDesc48, glable_ming_weaDesc;
                    glable_weaDesc48 = weaDesc48;
                    if (weaDesc48_ == weaDesc72_) {
                        glable_ming_weaDesc = weaDesc48_;
                    } else {
                        glable_ming_weaDesc = weaDesc48_ + "转" + weaDesc72_;
                    }
                    var weatherJson = {
                        "imgs": {
                            "img1": "img/weather/" + weaDesc24 + ".png",
                            "img2": "img/weather/" + weaDesc48 + ".png",
                            "img3": "img/weather/" + weaDesc72 + ".png"
                        },
                        "values": {
                            "date1": [temp24H, temp24L],
                            "date2": [temp48H, temp48L],
                            "date3": [temp72H, temp72L]
                        },
                        "windDir": [windD24_, windD48_, windD72_],
                        "windPower": [windS24_, windS48_, windS72_]
                    };
                    console.info(weatherJson);
                    $("body").trigger("get_fcst_fine_city_by_stationnum_success", {
                        weather: weaDesc24_,
                        weatherJson: weatherJson,
                        glable_weaDesc48: glable_weaDesc48,
                        glable_ming_weaDesc: glable_ming_weaDesc
                    });
                }
            }
        }
    });
});
function getTianqiData2() {
    $("body").trigger("tianqiyubao_duanqiyubao_get");
    var jing_dian = [{
        stid: "58560",
        name: ""
    }, {
        stid: "K6079",
        name: ""
    }, {
        stid: "K6083",
        name: ""
    }, {
        stid: "K6099",
        name: ""
    }, {
        stid: "K6100",
        name: ""
    }, {
        stid: "K6101",
        name: ""
    }, {
        stid: "K6102",
        name: ""
    }, {
        stid: "K6122",
        name: ""
    }, {
        stid: "K6125",
        name: ""
    }, {
        stid: "K6159",
        name: ""
    }];
    $.ajax({
        type: "GET",
        url: globle_url + "/get_town_tour",
        data: {
            fh: 12
        },
        dataType: 'json', //"xml", "html", "script", "json", "jsonp", "text".
        success: function(data) {
            if (data.status == "success") {
                var res = data.result;
                if (res) {
                    $("body").trigger("get_town_info_success", {
                        jingdianData: res,
                        jing_dian: jing_dian
                    });
                }
            }
        }
    });
}

function getEnvirData() {
    $.ajax({
        type: "GET",
        url: globle_url + "/get_fcst_live_index_by_type",
        data: {
            type: "清新指数"
        },
        dataType: 'json',
        success: function(data) {
            if (data && data.status == "success" && data.result) {
                var qingxin_text = data.result.fcst_text;
                $("body").trigger("get_fcst_live_index_by_qxzs_success", {
                    qingxin_text: qingxin_text
                });
            }

        }
    });
    $.ajax({
        type: "GET",
        url: globle_url + "/get_fcst_live_index_by_type",
        data: {
            type: "舒适度指数"
        },
        dataType: 'json',
        success: function(data) {
            if (data && data.status == "success" && data.result) {
                var shushi_text = "---";
                var shushi_grade = "---";
                if (data.result.fcst_text != null) {
                    shushi_text = data.result.fcst_text;
                }
                if (data.result.fcst_grade != null) {
                    shushi_grade = data.result.fcst_grade;
                }
                $("body").trigger("get_fcst_live_index_by_sszs_success", {
                    shushi_text: shushi_text,
                    shushi_grade: shushi_grade
                });
            }
        }
    });
    /*$.ajax({
        type:"GET",
        url:globle_url+"/get_lastest_area_probe_aws_per_hour",
        dataType:'json',
        success:function(data){
            if(data&&data.status=="success"&&data.result)
            {
                var wendu="---";
                var shidu="---";
                var qiya="---";
                if(data.result.t!=null){
                    wendu=data.result.t.toFixed(0);
                }
                if(data.result.rh!=null){
                    shidu=data.result.rh;                                       
                }
                if(data.result.p!=null){
                    qiya=data.result.p;                                        
                }
                $("body").trigger("get_lastest_area_probe_aws_per_hour_success",{wendu:wendu,shidu:shidu,qiya:qiya});
            }
        }
    });*/
    $.ajax({
        type: "GET",
        url: globle_url + "/get_lastest_station_probe_aws_per_hour",
        dataType: 'json',
        success: function(data) {
            if (data && data.status == "success" && data.result) {
                var wendu = "---";
                var shidu = "---";
                var qiya = "---";
                if (data.result.t != null) {
                    wendu = data.result.t.toFixed(0);
                }
                if (data.result.rh != null) {
                    shidu = data.result.rh;
                }
                if (data.result.p != null) {
                    qiya = (data.result.p).toFixed(0);
                }
                $("body").trigger("get_lastest_station_probe_aws_per_hour_success", {
                    wendu: wendu,
                    shidu: shidu,
                    qiya: qiya
                });
            }
        }
    });
}
/*  function configureParam(){
   //获取背景图片，登录图片
   $.ajax({
           type: "GET",
           url: globle_url + "/get_sys_key_value",
           dataType: 'json',//"xml", "html", "script", "json", "jsonp", "text".
           success: function (data) {
               console.info("kljdfi",data.result.length);
               for(var i=0;i<data.result.length;i++){
                   if(data.result[i].key=="m_background_image"){
                       localhost_weather.bg_image_url=data.result[i].value;
                   }                                      
               }
               store.set("localhost_weather", localhost_weather);
               if(localhost_weather.bg_image_url&&localhost_weather.bg_image_url!=""){
                   $("#all").css({
                       "backgroundImage": "url("+globle_url+"/"+localhost_weather.bg_image_url+")"
                   });
               }
               else{
                   $("#all").css({
                       "backgroundImage": "url(img/all_bg.jpg)"
                   });
               }                                    
           },
           error: function () {                                   
               $("#all").css({
                       "backgroundImage": "url(img/all_bg.jpg)"
               });
           }
   });
}  */   
function configureAward() {
    //获取奖项信息
    $.ajax({
        type: "GET",
        url: globle_url + "/get_sys_key_value",
        dataType: 'json', //"xml", "html", "script", "json", "jsonp", "text".
        data: {
            key: "m_awards_info"
        },
        success: function(data) {
            if (data.result && data.status == "success") {
                $("body").trigger("get_sys_key_value_success", {
                    awards: data.result.value
                });
            }


        }
    });
    //获取有奖页面的说明文字
    $.ajax({
        type: "GET",
        url: globle_url + "/get_sys_key_value",
        dataType: 'json', //"xml", "html", "script", "json", "jsonp", "text".
        data: {
            key: "m_awards_intr"
        },
        success: function(data) {
            if (data.result && data.status == "success") {
                $("body").trigger("get_sys_key_value_awards_intr_success", {
                    awards_intro: data.result.value
                });
            }
        }
    });
    //有奖页面是否开放
    $.ajax({
        type: "GET",
        url: globle_url + "/get_sys_key_value",
        dataType: 'json',
        data: {
            key: "m_awards_open"
        },
        success: function(data) {
            if (data.status == "success" && data.result) {
                $("body").trigger("get_sys_key_value_awards_open_success", {
                    awards_open: data.result.value
                });
            }
        }
    });
}

function getUserAward() {
    $.ajax({
        type: "GET",
        url: globle_url + "/get_user_awards",
        data: {
            page: 1,
            size: 10
        },
        dataType: 'json', //"xml", "html", "script", "json", "jsonp", "text".
        success: function(data) {
            var user_award = data;
            if (data.result == null) {
                return;
            }
            $("body").trigger("get_user_awards_success", {
                user_awards: data.result.result
            });
        }
    });
}

/*function saveUserAward(awards, user_id) {
    $.ajax({
        type: "GET",
        url: globle_url + "/save_user_awards",
        data: {
            awards: awards,
            user_id: user_id
        },
        dataType: 'json', //"xml", "html", "script", "json", "jsonp", "text".
        success: function(data) {
            $("body").trigger("save_user_awards_success", {
                awards_txt: awards
            });
        }
    });
}*/

function saveSuggestion(content, contact) {
    $.ajax({
        type: "GET",
        url: globle_url + "/save_suggestion",
        data: {
            content: content,
            contact: contact
        },
        dataType: 'json', //"xml", "html", "script", "json", "jsonp", "text".
        success: function(data) {
            if (data.message != null) {
                alertMessage($("#fankuiDialog"),data.message + "<br/>提交建议不成功！");
            } else {
                $("#suggestion_txt")[0].value = "";
                $("#contact_txt")[0].value = "";
                alertMessage(null,"您的建议已提交！");
            }
        }
    });
}

function getWeek(num) {
    var weekday;
    switch (num) {
        case 0:
            weekday = "周日";
            break;
        case 1:
            weekday = "周一";
            break;
        case 2:
            weekday = "周二";
            break;
        case 3:
            weekday = "周三";
            break;
        case 4:
            weekday = "周四";
            break;
        case 5:
            weekday = "周五";
            break;
        case 6:
            weekday = "周六";
            break;
    }
    return weekday;
}

function getDateFormat(date) {
    return date.getDate() + "/" + (date.getMonth() + 1);
}
function getLChartDatasource(weatherdata){
    var _max = Math.max(weatherdata.values.date1[0], weatherdata.values.date2[0], weatherdata.values.date3[0]);
    var _min = Math.min(weatherdata.values.date1[1], weatherdata.values.date2[1], weatherdata.values.date3[1]);
    return {
        "chart": {
            "bgAlpha": "0",
            "canvasBgAlpha": "0",
            "showValues": "1",
            "showBorder": "0",
            "xAxisNamePadding": "50",
            "chartLeftMargin": "0",
            "chartRightMargin": "0",
            "chartTopMargin": "0",
            "chartBottomMargin": "0",
            //是否显示y轴刻度
            "showYAxisValues": "0",
            "showCanvasBorder": "0",
            "usePlotGradientColor": "0",
            "showAxisLines": "0",
            "showYAxisLines": "0",
            "showAlternateHGridColor": "0",
            "divlineThickness": "1",
            "divLineDashed": "0",
            "divLineDashLen": "0",
            "divLineGapLen": "0",
            "yAxisMaxValue": _max + 8,
            "yAxisMinValue": _min - 8,
            //"numVDivLines": "2",
            "divLineColor": "#5c8aa0",
            "divLineAlpha": "0",
            "vDivLineAlpha": "15",
            "vdivLineColor": "#ffffff",
            "showAlternateVGridColor": "0",
            "canvasBgAlpha": "0",
            "showLegend": "0",
            "baseFontColor": "#FFFFFF",
            "anchorRadius": "5",
            "showToolTip": "1",
            "numDivLines": "1",
            "toolTipColor": "#000000"
        },
        "categories": [{

            "category": [{}, {
                    "vline": "true",
                    "lineposition": "0",
                    "color": "#ffffff",
                    "alpha": "15"
                }, {}, {
                    "vline": "true",
                    "lineposition": "0",
                    "color": "#ffffff",
                    "alpha": "15"
                }, {}, {
                    "vline": "true",
                    "lineposition": "0",
                    "color": "#ffffff",
                    "alpha": "15"
                }

            ]
        }],
        "dataset": [{
            "color": "#fe7506",
            "valuePosition": "above",
            "anchorBgColor": "#fe7506",
            "baseFontColor": "#fe7506",

            "data": [{
                "value": weatherdata.values.date1[0],
            }, {
                "value": weatherdata.values.date2[0],
            }, {
                "value": weatherdata.values.date3[0]
            }]
        }, {
            "color": "#95d44c",
            "valuePosition": "below",
            "anchorBgColor": "#95d44c",
            "valueFontColor": "#95d44c",
            "data": [{
                "value": weatherdata.values.date1[1]
            }, {
                "value": weatherdata.values.date2[1]
            }, {
                "value": weatherdata.values.date3[1]
            }]
        }]
    };
}

function setChartDatas(weatherdata){
    var Date0 = new Date();
    var Date1 = new Date();
    var Date2 = new Date();
    var Date3 = new Date();
    Date1.setDate(Date0.getDate() + 1);
    Date2.setDate(Date0.getDate() + 2);
    Date3.setDate(Date0.getDate() + 3);
    $("#lchart_week1").html(getWeek(Date1.getDay()));
    $("#lchart_week2").html(getWeek(Date2.getDay())); 
    $("#lchart_week3").html(getWeek(Date3.getDay())); 
    $("#lchart_date1").html(getDateFormat(Date1)); 
    $("#lchart_date2").html(getDateFormat(Date2)); 
    $("#lchart_date3").html(getDateFormat(Date3)); 
    $("#lchart_img1").attr({src:weatherdata.imgs.img1}); 
    $("#lchart_img2").attr({src:weatherdata.imgs.img2}); 
    $("#lchart_img3").attr({src:weatherdata.imgs.img3}); 
    $("#lchart_wind1").html(weatherdata.windDir[0]); 
    $("#lchart_wind2").html(weatherdata.windDir[1]);  
    $("#lchart_wind3").html(weatherdata.windDir[2]); 
    $("#lchart_windpower1").html(weatherdata.windPower[0]); 
    $("#lchart_windpower2").html(weatherdata.windPower[1]); 
    $("#lchart_windpower3").html(weatherdata.windPower[2]);
}
var today_date=new Date();
function initTable(weatherJson) {    
    
    // var _max = Math.max(weatherJson.values.date1[0], weatherJson.values.date2[0], weatherJson.values.date3[0]);
    // var _min = Math.min(weatherJson.values.date1[1], weatherJson.values.date2[1], weatherJson.values.date3[1]);
    
    var new_weather_data=getLChartDatasource(weatherJson);
    if(lineChart==null){        
        tableHtml = "<tr><td style='text-align:left;' id='lchart_week1'></td><td id='lchart_week2'></td><td style='text-align:right;' id='lchart_week3'></td></tr>"+
        "<tr><td style='text-align:left;' id='lchart_date1'></td><td id='lchart_date2'></td><td style='text-align:right;' id='lchart_date3'></td></tr>"+
        "<tr><td style='text-align:left;'><img id='lchart_img1'/></td><td><img id='lchart_img2'/></td><td style='text-align:right;'><img id='lchart_img3'/></td></tr>"+
        "<tr><td colspan='3'><div id='chartContainer'></div></td></tr>" +
        "<tr><td style='text-align:left;' id='lchart_wind1'></td><td id='lchart_wind2'></td><td style='text-align:right;' id='lchart_wind3'></td></tr>"+
        "<tr><td style='text-align:left;' id='lchart_windpower1'></td><td id='lchart_windpower2'></td><td style='text-align:right;' id='lchart_windpower3'></td></tr>";
        $(".box6-table").html(tableHtml);
        setChartDatas(weatherJson);  
        lineChart = new FusionCharts({
            events: {
                loaded: function() {
                    var text = document.getElementsByTagName("tspan");
                    for (var i = 0; i < text.length; i++) {
                        if (text[i].textContent == "FusionCharts XT Trial") {
                            text[i].style.display = "none";
                        }
                    }

                },
                resized: function() {
                    setTimeout(function() {
                        var text = document.getElementsByTagName("tspan");
                        for (var i = 0; i < text.length; i++) {
                            if (text[i].textContent == "FusionCharts XT Trial") {
                                text[i].style.display = "none";
                            }
                        }
                    }, 1);
                }
            },
            "type": "msline",
            "renderAt": "chartContainer",
            "width": "100%",
            "height": 120,
            "dataFormat": "json",
            "dataSource":new_weather_data
        });
        lineChart.setTransparent(true);
        lineChart.render();
    }else{
        if(getDateFormat(new Date())!=getDateFormat(today_date)){
            today_date=new Date();
            setChartDatas(weatherJson); 
            lineChart.setJSONData(new_weather_data);
            lineChart.render();
        }
    }   
}

//绑定ondeviceready
$("body").bind("device_has_ready",function(){
    global_info.d.device_ready=true;
});
//$("body").bind("cehuaBtn_gloable_trigger zhongjiangBtn_trigger",function(event,param){
$("body").bind("zhongjiangBtn_trigger", function(event, param) {
    init_zhongjiang_list();
});
$("body").bind("get_weather_desc_type_success get_wind_s_type_success get_wind_d_type_success", function(event, param) {
    if (event.type == "get_weather_desc_type_success") {
        global_info.st.get_weather_desc_type_success = true;
    } else if (event.type == "get_wind_s_type_success") {
        global_info.st.get_wind_s_type_success = true;
    } else if (event.type == "get_wind_d_type_success") {
        global_info.st.get_wind_d_type_success = true;
    }
    if (global_info.st.get_weather_desc_type_success && global_info.st.get_wind_s_type_success && global_info.st.get_wind_d_type_success) {

        $("body").trigger("weather_is_ready");
    }
});

//main页面 天气预报页面 日期
$("body").bind("get_chinese_calendar_success global_ready", function(event, param) {
    if (event.type == "get_chinese_calendar_success") {
        console.info("get_chinese_calendar_success");
        global_info.st.get_chinese_calendar_success = true;
        global_info.d.chineseDate = param.chineseDate;
    } else if (event.type == "global_ready") {
        console.info("global_ready");
        global_info.st.global_ready = true;
    }
    if (global_info.st.get_chinese_calendar_success && global_info.st.global_ready) {
        if (global_info.d.chineseDate) {
            $("#currentDate").html(global_info.d.chineseDate);
            $("#b_currentDate").html(global_info.d.chineseDate);
        } else {
            $("#currentDate").html("未获取到日期数据");
            $("#b_currentDate").html("未获取到日期数据");
        }
    }
});

//main页面仪表盘负氧离子、 天气预报页面负氧离子
$("body").bind("get_lastest_probe_oxy_per_hour_success o2_ready", function(event, param) {
    if (event.type == "get_lastest_probe_oxy_per_hour_success") {
        console.info("get_lastest_probe_oxy_per_hour_success");
        global_info.st.get_lastest_probe_oxy_per_hour_success = true;
        global_info.d.o2 = param.o2;
        global_info.d.o2_avg = param.o2_avg;
    } else if (event.type == "o2_ready") {
        console.info("o2_ready");
        global_info.st.o2_ready = true;
    } 
    if (global_info.st.o2_ready && global_info.st.get_lastest_probe_oxy_per_hour_success) {
        var _o2_avg = global_info.d.o2_avg;
        var _o2 = global_info.d.o2;
        if (_o2_avg) {
            $("#main_box4_ion1").html(_o2_avg); //首页仪表盘的负氧离子
            if (_o2_avg > 3000) {
                _o2_avg = 3000;
            }
            csatGauge3.setDataForId("dials_id", _o2_avg);
        } else {
            $("#main_box4_ion1").html("---");
            csatGauge3.setDataForId("dials_id", 0);
        }
        if (_o2) {
            $("#main_box3_ion").html(_o2); //天气预报页面负氧离子
        } else {
            $("#main_box3_ion").html("---");
        }
    }
});

//西湖、西溪湿地负氧离子
$("body").bind("get_latest_o2_per_hour_by_stations_success global_ready", function(event, param) {
    if (event.type == "get_latest_o2_per_hour_by_stations_success") {
        global_info.st.get_latest_o2_per_hour_by_stations_success = true;
        global_info.d.o2_stations = param.o2_stations;
    } else if (event.type == "global_ready") {
        global_info.st.global_ready = true;
    }
    if (global_info.st.get_latest_o2_per_hour_by_stations_success && global_info.st.global_ready) {
        var o2_station_html = "";
        /*$("#main_box4_ion2_xixi").html("西溪湿地&nbsp;"+global_info.d.o2_xixi);
        $("#main_box4_ion2_xihu").html("西湖&nbsp;"+global_info.d.o2_xihu);*/
        if (global_info.d.o2_stations && global_info.d.o2_stations.length > 0) {
            for (var i = 0; i < global_info.d.o2_stations.length; i++) {
                if (global_info.d.o2_stations[i].O2 != null) {
                    if (i == 0) {
                        if (global_info.d.o2_stations[i].O2 > 10000 || global_info.d.o2_stations[i].O2 == 0) {
                            o2_station_html += "<p class='box4-ion-p2' id='main_box4_ion2_" + i + "'>" + global_info.d.o2_stations[i].stationName + "&nbsp;" +
                                "---</p>";
                        } else {
                            o2_station_html += "<p class='box4-ion-p2' id='main_box4_ion2_" + i + "'>" + global_info.d.o2_stations[i].stationName + "&nbsp;" +
                                global_info.d.o2_stations[i].O2 + "</p>";
                        }
                    } else {
                        if (global_info.d.o2_stations[i].O2 > 10000 || global_info.d.o2_stations[i].O2 == 0) {
                            o2_station_html += "<p class='box4-ion-p2' style='display:none;' id='main_box4_ion2_" + i + "'>" + global_info.d.o2_stations[i].stationName + "&nbsp;" +
                                "---</p>";
                        } else {
                            o2_station_html += "<p class='box4-ion-p2' style='display:none;' id='main_box4_ion2_" + i + "'>" + global_info.d.o2_stations[i].stationName + "&nbsp;" +
                                global_info.d.o2_stations[i].O2 + "</p>";
                        }
                    }

                } else {
                    if (i == 0) {
                        o2_station_html += "<p class='box4-ion-p2' style='display:none;' id='main_box4_ion2_" + i + "'>" + global_info.d.o2_stations[i].stationName + "&nbsp;---</p>";
                    } else {
                        o2_station_html += "<p class='box4-ion-p2' id='main_box4_ion2_" + i + "'>" + global_info.d.o2_stations[i].stationName + "&nbsp;---</p>";
                    }
                }
            }
            $("#o2_station_div").html(o2_station_html);
            clearInterval(stations_int);
            var show_ion = 0;
            stations_int = setInterval(function() {
                $("#main_box4_ion2_" + show_ion % global_info.d.o2_stations.length).hide();
                $("#main_box4_ion2_" + (show_ion + 1) % global_info.d.o2_stations.length).show();
                show_ion++;
            }, 5000);
        } else {
            $("#o2_station_div").html("---");
        }
    }
});

//负氧离子全省平均
/*$("body").bind("get_avg_province_lastest_probe_oxy_per_hour_success global_ready",function(event,param){
    if(event.type=="get_avg_province_lastest_probe_oxy_per_hour_success"){
        global_info.st.get_avg_province_lastest_probe_oxy_per_hour_success=true;
        global_info.d.o2_province_avg=param.o2_province_avg;
    }
    else if(event.type=="global_ready"){
        global_info.st.global_ready=true;
    }
    if(global_info.st.get_avg_province_lastest_probe_oxy_per_hour_success && global_info.st.global_ready){
        // $("#main_box4_ion2").html("全省均值&nbsp;"+global_info.d.o2_province_avg);
    }
});*/

//main页面仪表盘PM25=天气预报页面 PM2.5
$("body").bind("get_avg_lastest_probe_envi_per_hour_success pm25_ready", function(event, param) {
    if (event.type == "get_avg_lastest_probe_envi_per_hour_success") {
        console.info("get_avg_lastest_probe_envi_per_hour_success");
        global_info.st.get_avg_lastest_probe_envi_per_hour_success = true;
        global_info.d.pm25_avg = param.pm25_avg;
    } else if (event.type == "pm25_ready") {
        console.info("pm25_ready");
        global_info.st.pm25_ready = true;
    }
    if (global_info.st.get_avg_lastest_probe_envi_per_hour_success && global_info.st.pm25_ready) {
        var _pm25 = null;
        var pm25_array = null; //存储_pm25的整数部分和小数部分
        if (global_info.d.pm25_avg) {
            _pm25 = global_info.d.pm25_avg;
            $("#main_box3_pm25").html(_pm25); //天气预报页面的PM25
            pm25_array = (_pm25 + "").split("."); //分为整数部分和小数部分，分开显示
            if (_pm25 <= 150) {
                if (pm25_array.length == 1) {
                    $("#main_box5_quality1").html(_pm25);
                } else if (pm25_array.length == 2) {
                    var pm25_1 = pm25_array[0];
                    var pm25_2 = pm25_array[1];
                    $("#main_box5_quality1").html(pm25_1 + "<font style='font-size:30px;'>." + pm25_2 + "</font>");
                }
            } else {
                _pm25 = 150;
            }
            csatGauge.setDataForId("dials_id1", _pm25);
        } else {
            $("#main_box3_pm25").html("---");
            $("#main_box5_quality1").html("---");
            csatGauge.setDataForId("dials_id1", 0);
        }
    }
});

//main页面杭州PM25
/*$("body").bind("get_avg_station_num_probe_envi_per_hour_success global_ready",function(event,param){
    if(event.type=="get_avg_station_num_probe_envi_per_hour_success"){
        global_info.st.get_avg_station_num_probe_envi_per_hour_success=true;
        global_info.d.pm25_hangzhou2=param.pm25_hangzhou2;
    }else if(event.type=="global_ready"){
        global_info.st.global_ready=true;
    }
    if(global_info.st.get_avg_station_num_probe_envi_per_hour_success && global_info.st.global_ready){
        if(global_info.d.pm25_hangzhou2!=null){
            $("#main_box5_quality2").html("杭州&nbsp;"+global_info.d.pm25_hangzhou2);
        }else{
            $("#main_box5_quality2").html("杭州&nbsp;---");
        }  
    }
});*/

// PM25全省平均
$("body").bind("get_avg_province_probe_envi_per_hour_success global_ready", function(event, param) {
    if (event.type == "get_avg_province_probe_envi_per_hour_success") {
        global_info.st.get_avg_province_probe_envi_per_hour_success = true;
        global_info.d.pm25_province_avg = param.pm25_province_avg;
    } else if (event.type == "global_ready") {
        global_info.st.global_ready = true;
    }
    if (global_info.st.get_avg_province_probe_envi_per_hour_success && global_info.st.global_ready) {
        if (global_info.d.pm25_province_avg != null) {
            $("#main_box5_quality2").html("全省均值&nbsp;" + global_info.d.pm25_province_avg.toFixed(1));
        } else {
            $("#main_box5_quality2").html("全省均值&nbsp;---");
        }
    }
});

//杭州平均 PM25
/*$("body").bind("get_avg_latest_probe_envi_per_hour_by_citys_success global_ready",function(event,param){
    if(event.type=="get_avg_latest_probe_envi_per_hour_by_citys_success"){
        global_info.st.get_avg_latest_probe_envi_per_hour_by_citys_success=true;
        global_info.d.pm25_hangzhou=param.pm25_hangzhou;
    }
    else if(event.type=="global_ready"){
        global_info.st.global_ready=true;
    }
    if(global_info.st.get_avg_latest_probe_envi_per_hour_by_citys_success && global_info.st.global_ready){ 
        if(global_info.d.pm25_hangzhou!=null){
            $("#main_box5_quality2").html("杭州&nbsp;"+global_info.d.pm25_hangzhou);
        }else{
            $("#main_box5_quality2").html("杭州&nbsp;---");
        }                    
    }
});*/

//天气预报页  PM2.5
/*$("body").bind("get_lastest_probe_envi_per_hour_success global_ready",function(event,param){
    if(event.type=="get_lastest_probe_envi_per_hour_success"){
        global_info.st.get_lastest_probe_envi_per_hour_success=true;
        global_info.d.pm25=param.pm25;
    }
    else if(event.type=="global_ready"){
        global_info.st.global_ready=true;
    }
    if(global_info.st.get_lastest_probe_envi_per_hour_success && global_info.st.global_ready){
        var _pm25=global_info.d.pm25;
        $("#main_box3_pm25").html(_pm25);
    }
});*/

//天气预报页  温度，风速
$("body").bind("get_lastest_probe_aws_per_hour_success global_ready", function(event, param) {
    if (event.type == "get_lastest_probe_aws_per_hour_success") {
        console.info("get_lastest_probe_aws_per_hour_success");
        global_info.st.get_lastest_probe_aws_per_hour_success = true;
        global_info.d.speed = param.speed;
        global_info.d.temprature = param.temprature;

    } else if (event.type == "global_ready") {
        console.info("global_ready");
        global_info.st.global_ready = true;
    }
    if (global_info.st.global_ready && global_info.st.get_lastest_probe_aws_per_hour_success) {
        if (global_info.d.speed) {
            $("#main_box3_wind2").html(global_info.d.speed + "米/秒"); //风速                                
        } else {
            $("#main_box3_wind2").html("---"); //风速
        }
        if (global_info.d.temprature) {
            $("#main_box3_temp2").html(global_info.d.temprature + "℃"); //温度                                
        } else {
            $("#main_box3_temp2").html("---"); //温度
        }
    }
});

//天气预报页 未来三天天气预报 旅游景点预报
$("body").bind("get_fcst_fine_city_by_stationnum_success get_town_info_success global_ready weather_is_ready", function(event, param) {
    //alert(event.type);
    if (event.type == "get_fcst_fine_city_by_stationnum_success") {
        console.info("get_fcst_fine_city_by_stationnum_success");
        global_info.st.get_fcst_fine_city_by_stationnum_success = true;
        global_info.d.weather = param.weather;
        global_info.d.weatherJson = param.weatherJson;
        global_info.d.glable_weaDesc48 = param.glable_weaDesc48;
        global_info.d.glable_ming_weaDesc = param.glable_ming_weaDesc;
    } else if (event.type == "get_town_info_success") {
        global_info.st.get_town_info_success = true;
        global_info.d.jing_dian = param.jing_dian;
        global_info.d.jingdianData = param.jingdianData;
    } else if (event.type == "global_ready") {
        console.info("global_ready");
        global_info.st.global_ready = true;
    } else if (event.type == "weather_is_ready") {
        global_info.st.weather_is_ready = true;
    }
    if (global_info.st.get_fcst_fine_city_by_stationnum_success && global_info.st.global_ready && global_info.st.get_town_info_success && global_info.st.weather_is_ready) {
        global_info.st.get_fcst_fine_city_by_stationnum_success=false;
        global_info.st.get_town_info_success=false;
        $("#main_box3_weather").html(global_info.d.weather);
        initTable(global_info.d.weatherJson);
        var all_res = global_info.d.jingdianData;
        var jing_dian = global_info.d.jing_dian;
        var glable_weaDesc48 = global_info.d.glable_weaDesc48;
        var glable_ming_weaDesc = global_info.d.glable_ming_weaDesc;
        for (var kj = 0; kj < all_res.length; kj++) {
            var res_town = all_res[kj];
            if (res_town) {
                var tH = parseFloat(res_town.tmax12 / 10).toFixed(0);
                var tL = parseFloat(res_town.tmin12 / 10).toFixed(0);
                var name = jing_dian[kj].name == null || jing_dian[kj].name.length <= 0 ? res_town.stname : jing_dian[kj].name;
                $("#jing_" + (kj + 1) + "_name").html(name);
                $("#jing_" + (kj + 1) + "_t").html(tL + "到" + tH + "℃");

                if (glable_weaDesc48 != null)
                    $("#jing_" + (kj + 1) + "_icon").attr("src", "img/weather/" + res_town.ww12 + ".png");

                //if (glable_ming_weaDesc)
                $("#jing_" + (kj + 1) + "_w").html(localhost_weather.weather_desc_type[res_town.ww12]);

            }

        }
    }

});
//清新指数页面 获取清新指数
$("body").bind("get_fcst_live_index_by_qxzs_success global_ready ", function(event, param) {
    if (event.type == "get_fcst_live_index_by_qxzs_success") {
        global_info.st.get_fcst_live_index_by_qxzs_success = true;
        global_info.d.qingxin_text = param.qingxin_text;
    } else if (event.type = "global_ready") {
        global_info.st.global_ready = true;
    }
    if (global_info.st.get_fcst_live_index_by_qxzs_success && global_info.st.global_ready) {
        $("#qingxin_text").html(global_info.d.qingxin_text);
        qingxinzhishu_swiper.update(true);
    }
});
//舒适指数页面 获取舒适指数
$("body").bind("get_fcst_live_index_by_sszs_success get_lastest_station_probe_aws_per_hour_success global_ready", function(event, param) {
    if (event.type == "get_lastest_station_probe_aws_per_hour_success") {
        global_info.st.get_lastest_station_probe_aws_per_hour_success = true;
        global_info.d.wendu = param.wendu;
        global_info.d.shidu = param.shidu;
        global_info.d.qiya = param.qiya;
    } else if (event.type == "get_fcst_live_index_by_sszs_success") {
        global_info.st.get_fcst_live_index_by_sszs_success = true;
        global_info.d.shushi_text = param.shushi_text;
        global_info.d.shushi_grade = param.shushi_grade;
    } else if (event.type == "global_ready") {
        global_info.st.global_ready = true;
    }
    if (global_info.st.get_lastest_station_probe_aws_per_hour_success && global_info.st.get_fcst_live_index_by_sszs_success && global_info.st.global_ready) {
        $("#shushi_text").html(global_info.d.shushi_grade + "，温度" + global_info.d.wendu + "℃，湿度" + global_info.d.shidu + "%，气压" + global_info.d.qiya + "hPa，" + global_info.d.shushi_text);
        shushizhishu_swiper.update(true);
        /*var shushizhishu_swiper = new Swiper('.swiper-container-shushizhishu', {
            scrollbar: '.swiper-scrollbar-shushizhishu',
            direction: 'vertical',
            slidesPerView: 'auto',
            mousewheelControl: true,
            freeMode: true
        });*/
    }
});
//抽奖页面 绘制抽奖转盘，并绑定pointer按钮
var pointer_has_bound=false;
$("body").bind("get_sys_key_value_success global_ready", function(event, param) {
    console.info("xxxxxx", param);
    if (event.type == "get_sys_key_value_success") {
        global_info.st.get_sys_key_value_success = true;
        global_info.d.awards = JSON.parse(param.awards).awards;
    } else if (event.type == "global_ready") {
        global_info.st.global_ready = true;
    }
    if (global_info.st.get_sys_key_value_success && global_info.st.global_ready) {
        //                            alert(global_info.d.awards);
        var restaraunts = [];//奖项描述
        var colors = [];//奖项颜色
        for (var i = 0; i < global_info.d.awards.length; i++) {
            restaraunts.push(global_info.d.awards[i].res);
            colors.push(global_info.d.awards[i].color);

        }
        console.info(restaraunts);
        console.info(colors);
        turnplate.restaraunts = restaraunts;
        turnplate.colors = colors;
        drawRouletteWheel();
        if(pointer_has_bound==false){ 
            pointer_has_bound=true; 
            $('.pointer').bindtouch(function(event) {
                event.stopPropagation();
                if (!localhost_weather.user_info || !localhost_weather.user_info.oauthToken) {
                    alertMessage(null,"您还没有登录，登录后才能抽奖！");
                    return;
                }
                if (turnplate.bRotate) return;
                turnplate.bRotate = !turnplate.bRotate;

                //获取随机数(奖品个数范围内)
                rnd(function(item) {
                    rotateFn(item, turnplate.restaraunts[item - 1]);
                });               
            });
        }
    }
});
function drawRouletteWheel() {
    var canvas = document.getElementById("wheelcanvas");
    if (canvas.getContext) {
        //根据奖品个数计算圆周角度
        var arc = Math.PI / (turnplate.restaraunts.length / 2);
        var ctx = canvas.getContext("2d");
        //在给定矩形内清空一个矩形
        ctx.clearRect(0, 0, 422, 422);
        //strokeStyle 属性设置或返回用于笔触的颜色、渐变或模式
        //ctx.lineWidth = "none";
        ctx.strokeStyle = "rgba(0,0,0,0)";
        //font 属性设置或返回画布上文本内容的当前字体属性
        ctx.font = '16px Microsoft YaHei';
        for (var i = 0; i < turnplate.restaraunts.length; i++) {
            var angle = turnplate.startAngle + i * arc;
            ctx.fillStyle = turnplate.colors[i];
            ctx.beginPath();
            //arc(x,y,r,起始角,结束角,绘制方向) 方法创建弧/曲线（用于创建圆或部分圆）
            ctx.arc(211, 211, turnplate.outsideRadius, angle, angle + arc, false);
            ctx.arc(211, 211, turnplate.insideRadius, angle + arc, angle, true);
            ctx.stroke();
            ctx.fill();
            //锁画布(为了保存之前的画布状态)
            ctx.save();

            //----绘制奖品开始----
            ctx.fillStyle = "#fff";
            var text = turnplate.restaraunts[i];
            var line_height = 15;
            //translate方法重新映射画布上的 (0,0) 位置
            ctx.translate(211 + Math.cos(angle + arc / 2) * turnplate.textRadius, 211 + Math.sin(angle + arc / 2) * turnplate.textRadius);

            //rotate方法旋转当前的绘图
            ctx.rotate(angle + arc / 2 + Math.PI / 2);

            /** 下面代码根据奖品类型、奖品名称长度渲染不同效果，如字体、颜色、图片效果。(具体根据实际情况改变) **/
            if (text.indexOf("M") > 0) { //流量包
                var texts = text.split("M");
                for (var j = 0; j < texts.length; j++) {
                    ctx.font = j == 0 ? 'normal 16px Microsoft YaHei' : '16px Microsoft YaHei';
                    if (j == 0) {
                        ctx.fillText(texts[j] + "M", -ctx.measureText(texts[j] + "M").width / 2, j * line_height);
                    } else {
                        ctx.fillText(texts[j], -ctx.measureText(texts[j]).width / 2, j * line_height);
                    }
                }
            } else if (text.indexOf("M") == -1 && text.length > 6) { //奖品名称长度超过一定范围
                text = text.substring(0, 6) + "||" + text.substring(6);
                var texts = text.split("||");
                for (var j = 0; j < texts.length; j++) {
                    ctx.fillText(texts[j], -ctx.measureText(texts[j]).width / 2, j * line_height);
                }
            } else {
                //在画布上绘制填色的文本。文本的默认颜色是黑色
                //measureText()方法返回包含一个对象，该对象包含以像素计的指定字体宽度
                ctx.fillText(text, -ctx.measureText(text).width / 2, 0);
            }

            //添加对应图标
            if (text.indexOf("闪币") > 0) {
                var img = document.getElementById("shan-img");
                img.onload = function() {
                    ctx.drawImage(img, -15, 10);
                };
                ctx.drawImage(img, -15, 10);
            } else if (text.indexOf("谢谢参与") >= 0) {
                var img = document.getElementById("sorry-img");
                img.onload = function() {
                    ctx.drawImage(img, -15, 10);
                };
                ctx.drawImage(img, -15, 10);
            }
            //把当前画布返回（调整）到上一个save()状态之前
            ctx.restore();
            //----绘制奖品结束----
        }
    }
}
function rnd(fn) {
    var random;
    $.ajax({
        type: "GET",
        url: globle_url + "/draw_lottery",
        dataType: 'json', //"xml", "html", "script", "json", "jsonp", "text".
        data: {
            oauth_token: localhost_weather.user_info.oauthToken
        },

        beforeSend: function() {},
        success: function(data) {
            //console.info(JSON.stringify(data));
            if (data && data.status == "error") {
                alertMessage(null,data.message);
                turnplate.bRotate = !turnplate.bRotate;
            } else {
                fn(data.result.index + 1);
            }


        },
        complete: function(XMLHttpRequest, textStatus) {},
        error: function() {}
    });
}
//旋转转盘 item:奖品位置; txt：提示语;
var rotateFn = function(item, txt) {
    var angles = item * (360 / turnplate.restaraunts.length) - (360 / (turnplate.restaraunts.length * 2));
    if (angles < 270) {
        angles = 270 - angles;
    } else {
        angles = 360 - angles + 270;
    }
    $('#wheelcanvas').stopRotate();
    $('#wheelcanvas').rotate({
        angle: 0,
        animateTo: angles + 1800,
        duration: 8000,
        callback: function() {
            var awards = txt;
            var user_id = localhost_weather.user_info.user_id;
            $("body").trigger("save_user_awards_success", {
                awards_txt: awards
            });
            turnplate.bRotate = !turnplate.bRotate;
        }
    });
};
//抽奖页面 获取抽奖的活动说明文字
$("body").bind("get_sys_key_value_awards_intr_success global_ready", function(event, param) {
    if (event.type == "get_sys_key_value_awards_intr_success") {
        global_info.st.get_sys_key_value_awards_intr_success = true;
        global_info.d.awards_intro = param.awards_intro;
    } else if (event.type == "global_ready") {
        global_info.st.global_ready = true;
    }
    if (global_info.st.get_sys_key_value_awards_intr_success && global_info.st.global_ready) {
        $("#huodong_intro").html(global_info.d.awards_intro);
        youjianghuodong_swiper.update(true);
        /*var youjianghuodong_swiper = new Swiper('.swiper-container-youjianghuodong', {
            scrollbar: '.swiper-scrollbar-youjianghuodong',
            direction: 'vertical',
            slidesPerView: 'auto',
            mousewheelControl: true,
            freeMode: true
        });*/
    }
});
//抽奖页面 获取中奖滚动栏的中奖名单
$("body").bind("get_user_awards_success global_ready", function(event, param) {
    if (event.type == "get_user_awards_success") {
        global_info.st.get_user_awards_success = true;
        global_info.d.user_awards = param.user_awards;
    } else if (event.type == "global_ready") {
        global_info.st.global_ready = true;
    }
    if (global_info.st.get_user_awards_success && global_info.st.global_ready) {
        var user_awards = global_info.d.user_awards;
        var awardsListHtml = "";
        for (var i = 0; i < user_awards.length; i++) {
            awardsListHtml += "<li class='ui-grid-b'><div class='ui-block-a'><span>" + user_awards[i].phoneNum + "</span></div>" +
                "<div class='ui-block-b'><span>" + user_awards[i].awards + "</span></div>" +
                "<div class='ui-block-c'><span>" + new Date(user_awards[i].addTime).date2String("MM月dd日 hh:mm:ss") + "</span></div></li>";
        }
        $("#user_list_ul").html(awardsListHtml);
        //                            autoScroll(".mingdan-con");
        if (user_awards.length > 2) {
            setInterval('autoScroll(".mingdan-con")', 3000);
        }


    }
});
$("body").bind("save_user_awards_success", function(event, param) {
    global_info.st.save_user_awards_success = true;
    localhost_weather.user_has_awards = true;
    store.set("localhost_weather", localhost_weather);
    alertMessage(null,param.awards_txt);
});
//用户登录后的操作
$("body").bind("user_is_login", function(event, param) {
    $("#loginDialogBtn").css({
        display: "none"
    });
    $("#login_user_txt").html(localhost_weather.user_info.name);
    $("#login_user_txt").css({
        display: "block"
    });
});
//用户登出后的操作
$("body").bind("user_is_logout", function(event, param) {
    localhost_weather.user_info = {};
    store.set("localhost_weather", localhost_weather);
    $("#login_user_txt").css({
        display: "none"
    });
    $("#login_user_txt").html("");
    $("#loginDialogBtn").css({
        display: "block"
    });
});


if (!localhost_weather.weather_desc_type) {
    $.ajax({
        type: "GET",
        url: globle_url + "/get_weather_desc_type",
        dataType: 'json', //"xml", "html", "script", "json", "jsonp", "text".
        success: function(data) {
            localhost_weather.weather_desc_type = data;
            store.set("localhost_weather", localhost_weather);
            $("body").trigger("get_weather_desc_type_success");
        }
    });
} else {
    $("body").trigger("get_weather_desc_type_success");
}
if (!localhost_weather.wind_s_type) {
    $.ajax({
        type: "GET",
        url: globle_url + "/get_wind_s_type",
        dataType: 'json', //"xml", "html", "script", "json", "jsonp", "text".
        success: function(data) {
            localhost_weather.wind_s_type = data;
            store.set("localhost_weather", localhost_weather);
            $("body").trigger("get_wind_s_type_success");
        }
    });
} else {
    $("body").trigger("get_wind_s_type_success");
}
if (!localhost_weather.wind_d_type) {
    $.ajax({
        type: "GET",
        url: globle_url + "/get_wind_d_type",
        dataType: 'json', //"xml", "html", "script", "json", "jsonp", "text".
        success: function(data) {
            localhost_weather.wind_d_type = data;
            store.set("localhost_weather", localhost_weather);
            $("body").trigger("get_wind_d_type_success");
        }
    });
} else {
    $("body").trigger("get_wind_d_type_success");
}
var youjianghuodong_swiper=null;
var shushizhishu_swiper=null;
var qingxinzhishu_swiper=null;
var tianqiyubao_swiper=null;
$(document).ready(function() {

    
    $("body").trigger("global_ready");
    /*setTimeout(function(){
        $("#guanggao").css({display:"none"});
        $("#footerMenu").css({display:"block"});
    },3000);*/

    //                        configureParam(); 
    /*var guanggao_end=0;
    var guanggaoSwiper=new Swiper('.swiper-container-guanggao',{
        pagination:'.swiper-pagination-guanggao',
        autoplay:2000,
        loop:false,
        onReachEnd:function(swiper){
            swiper.stopAutoplay();
            
            var tag={t:true};
            if(swiper.touches.startX){
                tag.t=false;
                if(swiper.isEnd &&swiper.touches.startX > swiper.touches.currentX){
                    if(guanggao_end > 8){
                        $("#guanggao").css({display:"none"});
                        $("#footerMenu").css({display:"block"});                                            
                    }else{
                        guanggao_end++;
                    }
                }
            }else{
                setTimeout(function(){
                    if(tag.t)
                    {
                        $("#guanggao").css({display:"none"});
                        $("#footerMenu").css({display:"block"});
                    }
                    
                },2000);
            }
                                   
            
        }
    });  */
    // console.info("XXXXXXXXXXXXXXXX"+$(document).innerHeight());  
    // console.info($("#tianqiyubao .headerBar").height()); 
    $("#div_iframe_tianqiyubao").height($(document).innerHeight() - $("#tianqiyubao .headerBar").height());
    tianqiyubao_swiper = new Swiper('.swiper-container-tianqiyubao', {
        scrollbar: '.swiper-scrollbar-tianqiyubao',
        direction: 'vertical',
        slidesPerView: 'auto',
        mousewheelControl: true,
        freeMode: true
    });
    $("#div_iframe_youjianghuodong").height($(document).innerHeight() - $("#youjianghuodong .headerBar").height());
    youjianghuodong_swiper = new Swiper('.swiper-container-youjianghuodong', {
        scrollbar: '.swiper-scrollbar-youjianghuodong',
        direction: 'vertical',
        slidesPerView: 'auto',
        mousewheelControl: true,
        freeMode: true
    });
    $("#div_iframe_shushizhishu").height($(document).innerHeight() - $("#shushizhishu .headerBar").height());
    shushizhishu_swiper = new Swiper('.swiper-container-shushizhishu', {
        scrollbar: '.swiper-scrollbar-shushizhishu',
        direction: 'vertical',
        slidesPerView: 'auto',
        mousewheelControl: true,
        freeMode: true
    });
    $("#div_iframe_qingxinzhishu").height($(document).innerHeight() - $("#qingxinzhishu .headerBar").height());
    qingxinzhishu_swiper = new Swiper('.swiper-container-qingxinzhishu', {
        scrollbar: '.swiper-scrollbar-qingxinzhishu',
        direction: 'vertical',
        slidesPerView: 'auto',
        mousewheelControl: true,
        freeMode: true
    });
    
    //负氧离子仪表盘
    csatGauge3 = new FusionCharts({
        events: {
            loaded: function() {
                var tickEle = $("#chartContainer3 g text tspan");
                for (var i = 0; i < tickEle.length; i++) {
                    switch (tickEle[i].textContent) {
                        case '0':
                            continue;
                        case '300':
                            continue;
                        case '500':
                            continue;
                        case '1,000':
                            tickEle[i].textContent = 1000;
                            continue;
                        case '1,500':
                            tickEle[i].textContent = 1500;
                            continue;
                        case '2,000':
                            tickEle[i].textContent = 2000;
                            continue;
                        case '2,500':
                            tickEle[i].textContent = 2500;
                            continue;
                        case '3,000':
                            tickEle[i].textContent = "";
                            break;
                        default:
                            tickEle[i].style.display = "none";
                    }
                }
                var text = document.getElementsByTagName("tspan");
                for (var i = 0; i < text.length; i++) {
                    if (text[i].textContent == "FusionCharts XT Trial") {
                        text[i].style.display = "none";
                    }
                }
            },
            resized: function() {
                setTimeout(function() {
                    var tickEle = $("#chartContainer3 g text tspan");
                    for (var i = 0; i < tickEle.length; i++) {
                        switch (tickEle[i].textContent) {
                            case '0':
                                continue;
                            case '300':
                                continue;
                            case '500':
                                continue;
                            case '1,000':
                                tickEle[i].textContent = 1000;
                                continue;
                            case '1,500':
                                tickEle[i].textContent = 1500;
                                continue;
                            case '2,000':
                                tickEle[i].textContent = 2000;
                                continue;
                            case '2,500':
                                tickEle[i].textContent = 2500;
                                continue;
                            case '3,000':
                                tickEle[i].textContent = "";
                                break;
                            default:
                                tickEle[i].style.display = "none";
                        }
                    }
                    var text = document.getElementsByTagName("tspan");
                    for (var i = 0; i < text.length; i++) {
                        if (text[i].textContent == "FusionCharts XT Trial") {
                            text[i].style.display = "none";
                        }
                    }
                }, 1);
            }
        },
        "type": "angulargauge",
        "renderAt": "chartContainer3",
        "width": "100%",
        "height": "100",
        "dataFormat": "json",
        "dataSource": {
            "chart": {

                "bgAlpha": "0",
                "lowerLimit": "0",
                "upperLimit": "3000",
                "theme": "fint",
                "showLegend": "0",
                "majorTMColor": "#B1E253",
                "majortmnumber": "30", //大刻度
                "minorTMNumber": "0", //小刻度
                "majorTMThickness": "0", //大刻度线的粗细
                "majorTMHeight": "0",
                "showTickValues": "1", //不显示刻度
                "autoAlignTickValues": "0",
                "autoscale": "0",
                "baseFontColor": "FFF",
                "baseFontSize": "8",
                "showGaugeBorder": "0",
                "tickValueDistance": "3",
                "pivotRadius": "6",
                "canvaspadding": "0",
                "chartLeftMargin": "0",
                "chartTopMargin": "0",
                "chartRightMargin": "0",
                "chartLeftMargin": "0",
                "chartBottomMargin": "0",
            },
            "colorRange": {
                "color": [{
                    "minValue": "0",
                    "maxValue": "300",
                    "code": "#ff7f00",
                    "borderColor": "#B1E253",
                }, {

                    "minValue": "300",
                    "maxValue": "500",
                    "code": "#ffff01",
                    "borderColor": "#B1E253",
                }, {

                    "minValue": "500",
                    "borderColor": "#B1E253",
                    "maxValue": "1000",
                    "code": "#74c141"
                }, {
                    "minValue": "1000",
                    "maxValue": "1500",
                    "borderColor": "#B1E253",
                    "code": "#76c0ef"
                }, {
                    "minValue": "1500",
                    "maxValue": "2000",
                    "borderColor": "#B1E253",
                    "code": "#03b3ff"
                }, {
                    "minValue": "2000",
                    "maxValue": "2500",
                    "borderColor": "#B1E253",
                    "code": "#03ffea"
                }, {
                    "minValue": "2500",
                    "maxValue": "3000",
                    "borderColor": "#B1E253",
                    "code": "#03ffea"
                }, ]
            },
            "dials": {
                "dial": [{
                    "id": "dials_id",
                    "value": "0",
                    "bgcolor": "#FFFFFF",
                    "bordercolor": "#FFFFFF",
                    "editMode": "0",
                    "basewidth": "4",
                    "rearExtension": "0",
                }]
            }
        }
    });
    csatGauge3.render();
    csatGauge3.setTransparent(true);
    csatGauge3.addEventListener("rendered", function() {
        $("body").trigger("o2_ready");
    });

    //PM25仪表盘
    csatGauge = new FusionCharts({
        events: {
            loaded: function() {
                var tickEle = $("#chartContainer2 g text tspan");
                for (var i = 0; i < tickEle.length; i++) {
                    switch (tickEle[i].textContent) {
                        case '0':
                        case '50':
                        case '100':
                        case '150':
                            break;
                        default:
                            tickEle[i].style.display = "none";
                    }
                }
                var text = document.getElementsByTagName("tspan");
                for (var i = 0; i < text.length; i++) {
                    if (text[i].textContent == "FusionCharts XT Trial") {
                        text[i].style.display = "none";
                    }
                }

            },
            resized: function() {
                setTimeout(function() {
                    var tickEle = $("#chartContainer2 g text tspan");
                    for (var i = 0; i < tickEle.length; i++) {
                        switch (tickEle[i].textContent) {
                            case '0':
                            case '50':
                            case '100':
                            case '150':
                                break;
                            default:
                                tickEle[i].style.display = "none";
                        }
                    }
                    var text = document.getElementsByTagName("tspan");
                    for (var i = 0; i < text.length; i++) {
                        if (text[i].textContent == "FusionCharts XT Trial") {
                            text[i].style.display = "none";
                        }
                    }
                }, 1);
            }
        },
        "type": "angulargauge",
        "renderAt": "chartContainer2",
        "width": "100%",
        "height": "100",
        "dataFormat": "json",
        "dataSource": {
            "chart": {
                "bgAlpha": "0",
                "lowerLimit": "0",
                "upperLimit": "150",
                "theme": "fint",
                //"showLegend": "0",
                "majorTMColor": "#668ba4",
                "minortmnumber": "0",
                "majortmnumber": "8",
                "majorTMHeight": "0",
                "majorTMThickness": "0",
                "showLimits": "1",
                "showTickValues": "1", //不显示刻度
                "autoAlignTickValues": "0",
                "autoscale": "0",
                "baseFontColor": "FFF",
                //刻度值距离表盘的距离
                "tickValueDistance": "3",
                "showGaugeBorder": "0",
                "pivotRadius": "6",
                "chartTopMargin": "0",
                "canvaspadding": "0",
                "chartBottomMargin": "0",

            },
            "colorRange": {
                "color": [{
                        "minValue": "0",
                        "maxValue": "50",
                        "code": "#01e401",
                    }, {

                        "minValue": "50",
                        "maxValue": "100",
                        "code": "#ffff01",

                    }, {

                        "minValue": "100",
                        "maxValue": "150",
                        "code": "#ff7f00",

                    },

                ]
            },
            "dials": {
                "dial": [{
                    "id": "dials_id1",
                    "value": "0",
                    "bgcolor": "#FFFFFF",
                    "bordercolor": "#FFFFFF",
                    "editMode": "0",
                    "basewidth": "4",
                    "rearExtension": "0",
                }]
            }
        }
    });
    csatGauge.render();
    csatGauge.setTransparent(true);
    csatGauge.addEventListener("rendered", function() {
        $("body").trigger("pm25_ready");
    });

    //判断用户是否登录
    if (localhost_weather.user_info) {
        if (localhost_weather.user_info.oauthToken) {
            $("body").trigger("user_is_login");
        }
    } else {
        $("body").trigger("user_is_logout");
    }
    //绑定弹出框的后退按钮：回到首页并且关闭
    $(".backAndClose").bindtouch(function() {
        $.unblockUI();
    });
    //退出按钮
    $("#exitBtn").bindtouch(function() {
        $("body").trigger("user_is_logout");
        alertMessage(null,"您已退出登录！");
    });
    //点击登录按钮，弹出登录对话框
    $("#loginDialogBtn").bindtouch(function() {
        $("#name").val("");
        $("#password").val("");
        //注册后记住用户名密码
        if (localhost_weather.user_info) {
            if (localhost_weather.user_info.name) {
                $("#name").val(localhost_weather.user_info.name);
            }
            if (localhost_weather.user_info.password) {
                $("#password").val(localhost_weather.user_info.password);
            }
        }
        $.blockUI({
            message: $('#loginDialog'),
            css: {
                width: '100%',
                height: '100%',
                top: '0px',
                left: '0px',
                border: 'none',
                cursor: null,
            },
        });
    });
    //绑定登录页面的登录按钮
    $("#loginBtn").bindtouch(function() {
        var name = $("#name").val();
        var password = $("#password").val();
        if (name == null || name.length <= 0) {
            alertMessage($("#loginDialog"),"用户名不能为空！");
            return;
        }
        if (password == null || password.length <= 0) {
            alertMessage($("#loginDialog"),"密码不能为空！");
            return;
        }
        $.ajax({
            type: "GET",
            url: globle_url + "/login",
            data: {
                name: name,
                password: password
            },
            dataType: 'json', //"xml", "html", "script", "json", "jsonp", "text".
            beforeSend: function() {},
            success: function(data) {
                if (data && data.status == "success") {
                    if (data.result) {
                        var value = document.getElementById("checkValue").innerHTML;
                        localhost_weather.user_info.name = name;
                        localhost_weather.user_info.password = password;
                        localhost_weather.user_info.user_id = data.result.id;
                        localhost_weather.user_info.oauthToken = data.result.oauthToken;
                        if (value == "true") {
                            localhost_weather.user_info.auto_login = true;
                        }
                        store.set("localhost_weather", localhost_weather);
                        $("body").trigger("user_is_login");
                        alertMessage(null,"您已登录，可以去抽奖啦！");
                    }
                } else {
                    alertMessage($("#loginDialog"),data.message);
                }
            }
        });
    });
    //绑定登录页的注册按钮，点击弹出注册对话框
    $("#registerBtn").bindtouch(function() {
        $.blockUI({
            message: $('#zhuceDialog'),
            css: {
                width: '100%',
                height: '100%',
                top: '0px',
                left: '0px',
                border: 'none',
                cursor: null,
            },
        });
    });
    //绑定注册页的确认注册按钮
    $("#confirmRegister").bindtouch(function() {
        var name = $("#zhuce_name").val();
        var phoneNum = $("#phoneNum").val();
        var password = $("#zhuce_password").val();
        var password2 = $("#zhuce_password2").val();
        var phoneNum = $("#phoneNum").val();
        if (name == null || name.length <= 0) {
            alertMessage($("#zhuceDialog"),"用户名不能为空");
            return;
        }
        if (phoneNum == null || phoneNum.length <= 0) {
            alertMessage($("#zhuceDialog"),"手机号码不能为空！");
            return;
        }
        if (password == null || password.length <= 0) {
            alertMessage($("#zhuceDialog"),"密码不能为空！");
            return;
        }
        if (password2 == null || password2.length <= 0) {
            alertMessage($("#zhuceDialog"),"请再次输入密码！");
            return;
        }
        if (password2 != password) {
            alertMessage($("#zhuceDialog"),"两次密码不一致");
            return;
        }
        $.ajax({
            type: "GET",
            url: globle_url + "/save_user",
            data: {
                name: name,
                password: password,
                phone_num: phoneNum
            },
            dataType: 'json', //"xml", "html", "script", "json", "jsonp", "text".
            beforeSend: function() {},
            success: function(data) {
                if (data && data.status == "success") {
                    alertMessage(null,"注册成功，请登录！");
                    $("#zhuce_name").val("");
                    $("#phoneNum").val("");
                    $("#zhuce_password").val("");
                    $("#zhuce_password2").val("");
                    $("#phoneNum").val("");
                    //记录用户名密码，在登录对话框显示
                    localhost_weather.user_info.name = name;
                    localhost_weather.user_info.password = password;
                    store.set("localhost_weather", localhost_weather);
                } else {
                    alertMessage($("#zhuceDialog"),data.message);
                    return;
                }
            }
        });
    });
    //绑定注册页的取消按钮
    $("#cancleRegister").bindtouch(function() {
        $.unblockUI();
    });
    //绑定我的信箱按钮
    $("#zhongjiangBtn").bindtouch(function() {
        $("body").trigger("zhongjiangBtn_trigger");
        $.blockUI({
            message: $('#zhongjiangDialog'),
            css: {
                width: '100%',
                height: '100%',
                top: '0px',
                left: '0px',
                border: 'none',
                cursor: null,
            },
        });
    });
    //绑定意见反馈按钮
    $("#fankuiBtn").bindtouch(function() {
        $.blockUI({
            message: $('#fankuiDialog'),
            css: {
                width: '100%',
                height: '100%',
                top: '0px',
                left: '0px',
                border: 'none',
                cursor: null,
            },
        });
    });
    //绑定意见反馈页面的提交意见按钮
    $("#subbmit").bindtouch(function() {
        var content = $("#suggestion_txt").val();
        var contact = $("#contact_txt").val();
        if (!content || content == "") {
            alertMessage($("#fankuiDialog"),"建议不能为空！");
            return;
        }
        if (!contact || contact == "") {
            alertMessage($("#fankuiDialog"),"联系方式不能为空！");
            return;
        }
        saveSuggestion(content, contact);
    });
    $(".erweima a").attr({href:globle_url+"/project/manager_upload_imgs/download_icon.png"});
    $("#erweima_download").attr({src:globle_url+"/project/manager_upload_imgs/download_icon.png"});
    //绑定关于我们按钮
    $("#guanyuBtn").bindtouch(function() {
        $.blockUI({
            message: $('#guanyuDialog'),
            css: {
                width: '100%',
                height: '100%',
                top: '0px',
                left: '0px',
                border: 'none',
                cursor: null,
            },
        });
    });
    //绑定帮助按钮
    var _end = 0;
    var bangzhuSwiper = null;
    var changeTag = true;
    $("#bangzhuBtn").bindtouch(function() {
        _end = 0;
        changeTag = true;
        $.blockUI({
            message: $('#bangzhuDialog'),
            css: {
                width: '100%',
                height: '100%',
                top: '0px',
                left: '0px',
                border: 'none',
                cursor: null,
            },
        });
        if (bangzhuSwiper == null) {
            bangzhuSwiper = new Swiper('.swiper-container-bangzhu', {
                pagination: '.swiper-pagination-bangzhu',
                paginationClickable: true,
                onReachEnd: function(swiper) {
                    if (swiper.isEnd && swiper.touches.startX > swiper.touches.currentX) {
                        if (_end > 8) {
                            $.unblockUI();
                            if (changeTag) {
                                setTimeout(function() {
                                    swiper.slideTo(0, 1000);
                                }, 500);
                                changeTag = false;
                            }

                        } else
                            _end++;
                    }
                }
            });
        } else {
            ////bangzhuSwiper.slideTo(0, 1000, false);
        }
    });
    //绑定侧滑按钮
    var menuLeft = document.getElementById("cbp-spmenu-s1");
    var cehuaBody = document.getElementById("swiper_container_main");
    var cehuaBtn = document.getElementById("cehuaBtn");
    $("#cehuaBtn").bindtouch(function(event) {  
        event.stopPropagation();         
        classie.toggle(cehuaBody, 'cbp-spmenu-push-toright');
        classie.toggle(menuLeft, 'cbp-spmenu-open');
        
        if($("#cbp-spmenu-s1").hasClass("cbp-spmenu-open") && $("#swiper_container_main").hasClass("cbp-spmenu-push-toright")){
            $("#swiper_container_main").bindtouch(function(){                       
                $("#cbp-spmenu-s1").removeClass("cbp-spmenu-open");
                $("#swiper_container_main").removeClass("cbp-spmenu-push-toright");
                $("#swiper_container_main").unbind();
            });  
        }     
    }); 
    
    //绑定拍照按钮
    var tag = 0;
    $("#cameraBtn").bindtouch(function() {
        if (localhost_weather.user_info && localhost_weather.user_info.oauthToken) {
            $.blockUI({
                /*overlayCSS:{
                    backgroundColor:"red"
                },*/
                message: $('#cameraDialog'),
                css: {
                    /*width: '100%',
                    height: '100%',*/
                    width:"60%",
                    height:"auto",
                    top: '35%',
                    left: '20%',
                    border: 'none',
                    cursor: null,
                    background: 'transparent',
                },
            });
            $('.blockOverlay').bindtouch($.unblockUI);
            if (tag == 0) {
                tag++;
                $("#photoBtn1").bindtouch(function() {
                    $.unblockUI();
                    getCamera("capture")
                });
                $("#photoBtn2").bindtouch(function() {
                    $.unblockUI();
                    getCamera("getPicture")
                });
            }
        } else {
            alertMessage(null,"您还没有登录，登录后才能上传照片！");
        }
    });
    //绑定上传照片页面的确认和取消按钮
    // var yes_tag = false;
    $('#yes').bindtouch(function() {
        // if (yes_tag) {
        //     return;
        // }
        // yes_tag = true;
        var localhost_weather = store.get('localhost_weather');
        var u_file = $("#upload_success_image").attr("src_url");
        var comment = $("#upload_success_comment").val();
        if (comment == null || comment.trim() == "")
            comment = "没有任何评论";
        $("#upload_success_comment").val("");
        $.ajax({
            type: "GET",
            url: globle_url + "/save_user_images",
            dataType: 'json', //"xml", "html", "script", "json", "jsonp", "text".
            data: {
                comment: comment,
                url: u_file,
                oauth_token:localhost_weather.user_info.oauthToken,
                user_id: localhost_weather.user_info.user_id,
            },
            success: function(data) {
                // yes_tag = false;
                if (data.status == "success") {
                    // $.unblockUI();
                    alertMessage(null,"上传成功");
                } else {
                    alertMessage(null,data.message);
                }
            },
            error: function() {
                // yes_tag = false;
            }
        });
    });
    $('#no').bindtouch(function() {
        $.unblockUI();
        $("#upload_success_comment").val("");
        return false;
    });
    //绑定实况页的返回按钮
    $("#backToShikuang").bindtouch(function() {
        $("#_iframe").attr("src", "");
        $.unblockUI();
    });
    //绑定PM25实时数据按钮，显示PM25详细页面
    $("#enviroBtn").bindtouch(function() {
        $("#shikuang_title").html("PM2.5");
        $("#_iframe").attr("src", "http://220.191.230.13:8081/Wap/Map/58560/pm2_5?style=single");
        $.blockUI({
            message: $('#shikuangDialog'),
            css: {
                width: '100%',
                height: '100%',
                top: '0px',
                left: '0px',
                border: 'none',
                cursor: null,
            },
        });
    });
    //绑定负氧离子实时数据按钮，显示负氧离子详细页面
    $("#ionBtn").bindtouch(function() {
        $("#shikuang_title").html("负氧离子");
        $("#_iframe").attr("src", "http://220.191.230.13:8081/Wap/Map/58560/o2?style=single");
        $.blockUI({
            message: $('#shikuangDialog'),
            css: {
                width: '100%',
                height: '100%',
                top: '0px',
                left: '0px',
                border: 'none',
                cursor: null,
            },
        });
    });
    //绑定温度实时数据按钮，显示温度监测详细页面
    $("#tempBtn").bindtouch(function() {
        $("#shikuang_title").html("温度监测");
        $("#_iframe").attr("src", "http://220.191.230.13:8081/Wap/Map/58560/temp?style=single");
        $.blockUI({
            message: $('#shikuangDialog'),
            css: {
                width: '100%',
                height: '100%',
                top: '0px',
                left: '0px',
                border: 'none',
                cursor: null,
            },
        });
    });

    global_swiper = new Swiper('#all', {
        initialSlide: 0,
        mousewheelInvert: true, //?
        //                loop:true,
        //effect: 'fade',
        // effect: 'cube',
        // grabCursor: true,
        cube: {
            shadow: true,
            slideShadows: true,
            shadowOffset: 20,
            shadowScale: 0.94
        },
        hashnav: true, //?
        touchRatio: 1, //貌似作用是：值越大时鼠标或是手势移动时翻页的速度越大越快
        touchAngle: 45, //允许翻页的角度范围
        simulateTouch: true, //是否通过鼠标的drag和点击事件来模拟 touch事件（主要是电脑浏览器上没有touch事件），如果为false则电脑浏览器不具备翻页效果
        followFinger: false, //设置翻页效果是否跟随手势
        onInit: function(swiper) {
            //                    swiper.lockSwipes();
            var conHeight = ($(window).innerHeight() - $(".headerBar").height()) + "px";
            var conWidth = $(window).innerWidth() + "px";
            $(".div_iframe").css({
                height: conHeight,
                padding: "0px",
                width: conWidth
            });
            $(".backToMain").bindtouch(function(event) {
                event.stopPropagation();
                $("#footerMenu").toolbar({
                    tapToggle: false
                });
                if ($("#footerMenu").css("display") == "none") {
                    $("#footerMenu").toolbar("show");
                }
                //                        getMainData();
                getMainData2();

                setFootMenuStyle("");
                //                        swiper.unlockSwipes()
                swiper.slideTo(0, 500);                
                //                        swiper.lockSwipes();
            });
            $("#jrtqBtn").bindtouch(function() {
                // getMainData();
                $("body").trigger("shouye_o2_get");
                // getTianqiData();
                $("body").trigger("tianqiyubao_wind_and_temp_get");
                // $("body").trigger("tianqiyubao_duanqiyubao_get");
                getTianqiData2();
                setFootMenuStyle("jrtqBtn");
                //                        swiper.unlockSwipes()
                swiper.slideTo(1, 500);
                //                        swiper.lockSwipes();
            });
            $("#qxzsBtn").bindtouch(function() {
                getEnvirData();
                setFootMenuStyle("qxzsBtn");
                //                        swiper.unlockSwipes()
                swiper.slideTo(2, 500);
                //                        swiper.lockSwipes();
            });
            $("#sszsBtn").bindtouch(function() {
                getEnvirData();
                setFootMenuStyle("sszsBtn");
                //                        swiper.unlockSwipes()
                swiper.slideTo(3, 500);
                //                        swiper.lockSwipes();
            });
            $("#yjhdBtn").bindtouch(function() {
                // configureAward();
                getUserAward();
                setFootMenuStyle("yjhdBtn");
                //                        swiper.unlockSwipes()
                swiper.slideTo(4, 500);
                //                        swiper.lockSwipes();
            });

        },
        onSlideChangeStart: function(swiper) {

            $("#footerMenu").toolbar({
                tapToggle: false
            });
            // $( "#footerMenu" ).toolbar( "show" );
        },
        onSlideChangeEnd: function(swiper) {
            //                    $( "#footerMenu" ).toolbar( "show" );
            switch (swiper.activeIndex) {
                case 0:
                    $("#footerMenu").toolbar({
                        tapToggle: false
                    });
                    if ($("#footerMenu").css("display") == "none") {
                        $("#footerMenu").toolbar("show");
                    }
                    //                            alert($("#footerMenu").css("display"));  
                    setFootMenuStyle("");
                    // getMainData();
                    $("body").trigger("shouye_o2_get");
                    getMainData2();
                    break;
                case 1:
                    //                            $( "#footerMenu" ).toolbar( "show" );
                    $("#footerMenu").toolbar({
                        tapToggle: true
                    });
                    setFootMenuStyle("jrtqBtn");
                    // getMainData();
                    $("body").trigger("shouye_o2_get");
                    // getTianqiData();
                    $("body").trigger("tianqiyubao_wind_and_temp_get");
                    // $("body").trigger("tianqiyubao_duanqiyubao_get");
                    getTianqiData2();
                    break;
                case 2:
                    //                            $( "#footerMenu" ).toolbar( "show" );
                    $("#footerMenu").toolbar({
                        tapToggle: true
                    });
                    setFootMenuStyle("qxzsBtn");
                    break;
                case 3:
                    //                            $( "#footerMenu" ).toolbar( "show" );
                    $("#footerMenu").toolbar({
                        tapToggle: true
                    });
                    setFootMenuStyle("sszsBtn");
                    break;
                case 4:
                    //                            $( "#footerMenu" ).toolbar( "show" );
                    $("#footerMenu").toolbar({
                        tapToggle: true
                    });
                    setFootMenuStyle("yjhdBtn");
                    getUserAward();
                    break;

            }
        }
    });

});
function getCamera(type) {
    var myCamera = new MyCamera({
        uploadUrl: globle_url + "/file_upload",
        uploadParam: {},
        uploadSuccess: function(uploadData) {
            //alert(uploadData);
            //alert("uploadSuccess last:"+JSON.stringify(uploadData));

            if (uploadData.status == "success" && uploadData.result.length > 0) {
                $("#upload_success_image").attr("src", globle_url + "/" + uploadData.result[0]);
                $("#upload_success_image").attr("src_url", uploadData.result[0]);
                $.blockUI({
                    message: $('#uploadDialog'),
                    css: {
                        width: '100%',
                        height: '100%',
                        top: '0px',
                        left: '0px',
                        border: 'none',
                        cursor: null,
                    },
                });
            }else{
                alertMessage(null,uploadData.message);
            }

        },
        onUploadProgress:function(progressEvent){
            if (progressEvent.lengthComputable) {
                //alertMessage(null,new Number(100*progressEvent.loaded / progressEvent.total).toFixed(1)+"");
            } else {
                //alertMessage(null,"100");
            }
        },
        uploadFailed: function(error) {
            alertMessage(null,error.errorMessage);
        },
        uploadCantExe: function() {
            alertMessage(null,"取消");
        },
        onGetPictureSuccess: function(imageURI) {
            this.upload();
        },
        onGetPictureFail: function(message) {
            alertMessage(null,'获取图片失败' + message);
        }
    });
    if (type == "capture")
        myCamera.capture();
    else if (type == "getPicture")
        myCamera.getPicture();
}
//中奖名单自动滚动方法
function autoScroll(obj) {
    $(obj).find("ul").animate({
        marginTop: "-25px"
    }, 1000, function() {
        $(this).css({
            marginTop: "0px"
        }).find("li:first").appendTo(this);
    })
}

var ls = {};
function init_img_load(id) {
    console.info(id);
    if (!ls["id_" + id]) {
        ls["id_" + id] = true;
        //alert(id);
        // alert(swiperH / 3 + ":" + swiperW / 2);
        $('#' + id).fakecrop({
            wrapperSelector: null,
            wrapperWidth: swiperW / 2.0 - 3,
            wrapperHeight: swiperH / 3.0 - 30,
            center: true,
            fill: true,
            initClass: 'fc-init',
            doneEvent: 'fakedropdone',
            squareWidth: true
        });
    }
}
//设置页脚菜单的选中样式
var menuBtns = ["jrtqBtn", "qxzsBtn", "sszsBtn", "yjhdBtn"];
function setFootMenuStyle(activeBtn) {
    for (var i = 0; i < menuBtns.length; i++) {
        if (menuBtns[i] != activeBtn) {
            $("#" + menuBtns[i]).css({
                "backgroundColor": "transparent"
            });
        } else {
            $("#" + menuBtns[i]).css({
                "backgroundColor": "#ff8a00"
            });
        }
    }
}
function init_zhongjiang_list() { //http://192.168.1.108:9990/?user_id=1&page=0&size=0&exchange=false&out_of_date=false
    if (localhost_weather.user_info) {
        if (localhost_weather.user_info.user_id) {
            $.ajax({
                type: "GET",
                url: globle_url + "/get_user_awards",
                dataType: 'json', //"xml", "html", "script", "json", "jsonp", "text".
                data: {
                    user_id: localhost_weather.user_info.user_id,
                    page: 0,
                    size: 0,
                    exchange: false,
                    out_of_date: false
                },
                success: function(data) {
                    if (data.status == "success") {
                        if (data.result && data.result.result && data.result.result.length > 0) {
                            $("#zhongjiang_list").empty();
                            for (var k in data.result.result) {
                                var d = data.result.result[k];
                                //console.info(d);
                                //if(k%3==1)
                                //{
                                var img = "img/zhognjiang_bg" + (k % 3 + 1) + ".png";
                                //}
                                var append = '<li class="zhongjiang-list-item">' +
                                    ' <div class="zhongjiang-item-info">' +
                                    '<div class="zhongjiang-item-title-bg" style="background-image:url(' + img + ');">' + d.awards + '</div>' +
                                    '<div class="zhongjiang-item-text">' +
                                    '<span class="zhongjiang-text-span1">中奖码：' + d.serialNumber + '</span><br/>' +
                                    '<span class="zhongjiang-text-span2">有效期：' + new Date(d.expirationTime).date2String("yyyy年MM月dd HH:mm:ss"); + '</span>' +
                                '</div>' +
                                '</div>' +
                                '</li>';
                                $("#zhongjiang_list").append(append);
                            }
                        }

                        //init_img_load();
                        //addToSwiper({data:d},pos,6,swiperCon1);
                    }

                }
            });
        }
    }
}
/*function ajaxFileUpload() {

    $.ajaxFileUpload({
        url: globle_url + "/file_upload",
        secureuri: false,
        fileElementId: 'image_src',
        dataType: 'json',
        success: function(data) {
            console.info(data);
        }
    });
}*/


//照片墙模块
var swiperW, swiperH;
var imgData = {
    "data": []
};
var pos = {
    "imgPos": 5,
    "slidePos": -1
};
var _end = 0;
var _beginning = 0;
var swiperAll;
var swiperCon1;
var swiperCon2;
var flag_initial = false;
$(function() {
   //获取上传的图片12张
    $.ajax({
        type: "GET",
        url: globle_url + "/get_user_images",
        dataType: 'json', //"xml", "html", "script", "json", "jsonp", "text".
        data: {
            page: 1,
            size: 12
        },
        success: function(data) {
            if (data.status == "success") {
                var res = data.result.result;
                var d = [];
                for (var i = 0; i < res.length; i++) {
                    res[i].url = globle_url + "/" + res[i].url;
                }
                swiperAppend(res);
                //init_img_load();
                //console.info({data:d});
                //addToSwiper({data:d},pos,6,swiperCon1);
            }
        }
    });
    

    //main页面（照片墙和仪表盘页面）
    swiperAll = new Swiper('.swiper-container-main', {
        initialSlide: 1,
        //pagination: '.swiper-pagination',
        //paginationClickable: true,
        mousewheelInvert: true, //?
        hashnav: true, //?
        touchRatio: 1, //貌似作用是：值越大时鼠标或是手势移动时翻页的速度越大越快
        direction: 'vertical',
        touchAngle: 45, //允许翻页的角度范围
        simulateTouch: true, //是否通过鼠标的drag和点击事件来模拟 touch事件（主要是电脑浏览器上没有touch事件），如果为false则电脑浏览器不具备翻页效果
        followFinger: true, //设置翻页效果是否跟随手势
        onInit: function(swiper) {
            //仪表盘页面
            swiperCon2 = new Swiper('.swiper-container-shouye', {
                scrollbar: '.swiper-scrollbar-shouye',
                direction: 'vertical',
                slidesPerView: 'auto',
                mousewheelControl: true,
                //mousewheelReleaseOnEdges: true,
                //mousewheelInvert:ture,
                parallax: true,
                freeMode: true,
                onReachEnd: function(swiperCon2) {
                    if (!swiperCon2.isBeginning) {
                        if (swiperCon2.isEnd && _end > 8) {
                            swiperAll.slideNext(true, 1000);
                            _end = 0;
                            _beginning = 0;
                        } else
                            _end++;
                    }

                },
                onSliderMove: function(swiper, event) {
                    // console.info(swiper, event);
                    this["xxxx"] = true;
                },
                onReachBeginning: function(swiperCon2) {
                    //console.info(swiperCon2);
                    console.info(swiperCon2.touches);
                    if (swiperCon2.touches.startY < swiperCon2.touches.currentY) {
                        if (swiperCon2.isBeginning && _beginning > 8) {
                            console.info("XXX");
                            swiperAll.slidePrev(true, 1000);
                            _end = 0;
                            _beginning = 0;
                        } else {
                            _beginning++;
                        }
                    }

                }
            });
            setTimeout(function() {
                swiperCon2.update(true);
            }, 100);
        },
        onSlideChangeEnd: function(swiper) {
            if (swiper.activeIndex == 0) {
                // $("#footerMenu").css({"display":"none"});
                $("#footerMenu").toolbar("hide");
                $("#footerMenu").toolbar({
                    tapToggle: false
                });
            } else {
                $("#footerMenu").toolbar({
                    tapToggle: false
                });
                $("#footerMenu").toolbar("show");                                 
            }
        }
    });
    var g_swiperCon1_onReachBeginning = false;
    //照片墙页面
    swiperCon1 = new Swiper('.swiper-container-zhaopianqiang', {
        //slidesPerView: 3,
        centeredSlides: true,
        //paginationClickable: true,
        //spaceBetween: 30,
        scrollbar: '.swiper-scrollbar-zhaopianqiang',
        onReachBeginning: function(swiper) {
            console.info("onReachBeginning", swiper.isBeginning);
            if (swiper.isBeginning && !g_swiperCon1_onReachBeginning) {
                g_swiperCon1_onReachBeginning = true;
                $.ajax({
                    type: "GET",
                    url: globle_url + "/get_user_images",
                    dataType: 'json', //"xml", "html", "script", "json", "jsonp", "text".
                    data: {
                        page: 1,
                        size: 6
                    },
                    success: function(data) {
                        if (data.status == "success") {
                            g_swiperCon1_onReachBeginning = false;
                            var res = data.result.result;
                            if (res.length > 0) {
                                var d = [];
                                for (var i = 0; i < res.length; i++) {
                                    res[i].url = globle_url + "/" + res[i].url;
                                }
                                swiperAppend(res);
                                //init_img_load();
                                //addToSwiper({data:d},pos,6,swiperCon1);
                            }

                        }
                    }
                });
            }
        },
        //watchSlidesProgress: true,
        onProgress: function(swiper, progress) {
            for (var i = 0; i < swiper.slides.length; i++) {
                var slide = swiper.slides[i];
                var es = slide.style;
                es.webkitTransform = es.MsTransform = es.msTransform = es.MozTransform = es.OTransform = es.transform = 'rotate(' + 360 * slide.progress + 'deg)';
            }
        },
        onReachEnd: function(swiper) {
            console.info("onReachEnd");
            if (swiper.isEnd) {
                $.ajax({
                    type: "GET",
                    url: globle_url + "/get_user_images",
                    dataType: 'json', //"xml", "html", "script", "json", "jsonp", "text".
                    data: {
                        page: swiper.slides.length + 1,
                        size: 6
                    },
                    success: function(data) {
                        if (data.status == "success") {

                            var res = data.result.result;
                            if (res.length > 0) {
                                var d = [];
                                for (var i = 0; i < res.length; i++) {
                                    res[i].url = globle_url + "/" + res[i].url;
                                }
                                swiperAppend(res);
                                //init_img_load();
                                //addToSwiper({data:d},pos,6,swiperCon1);
                            }

                        }
                    }
                });
                // addToSwiper(queryImg,pos,6,swiper);
            }
        },
        onInit: function(swiper) {
            swiperH = $("#photoWall").innerHeight();
            swiperW = $("#photoWall").innerWidth();
        }
    });
    var t_image_swiper = {
        allImageData: {},
        slide: swiperCon1.slides.length,
        useImageData: [],
        slideSize: 6,
        swiper: swiperCon1,
        slideHtmlIdPrefix: 'slideHtmlIdPrefix_',
        slideHtml: "<div id='{0}' class='photo-wall-item'>" +
            "<div class='photo-box'><a tagName='tag_name'  href='{1}' style='display:inline-block' data-title='{7}' data-lightbox='tag_name'><img id='{5}'  onload='init_img_load(&#39;" + "{6}" + "&#39;)' src='{2}'/></a></div>" +
            "<div class='comment-box'><span style='font-weight:bolder;'>{3}：</span>{4}</div>" +
            "</div>"
    };
    function getFormatDiv(d) {
        return Util.formatString(t_image_swiper.slideHtml, t_image_swiper.slideHtmlIdPrefix + d.id, d.url, d.url, d.name, d.comment, t_image_swiper.slideHtmlIdPrefix + "img_" + d.id, t_image_swiper.slideHtmlIdPrefix + "img_" + d.id, d.comment); //,new Date(d.addTime).date2String("hh:mm:ss MM-dd"));
    }
    function swiperAppend(addData) {
        if (addData && addData.length > 0) {
            //if(addTag=="+")
            {
                a: for (var k in addData) {

                    if (t_image_swiper.allImageData["t_user_img_" + addData[k].id])
                        continue;
                    t_image_swiper.allImageData["t_user_img_" + addData[k].id] = addData[k];
                    var id = addData[k].id;
                    //var frontI=0,back=t_image_swiper.useImageData.length;
                    if (t_image_swiper.useImageData.length > 0) {
                        var lastUseSlide = (t_image_swiper.useImageData.length / t_image_swiper.slideSize) + 1;
                        for (var k1 = 0; k1 < t_image_swiper.useImageData.length; k1++) {
                            var img = t_image_swiper.useImageData[k1];
                            //if()
                            if (id > img.id) {
                                t_image_swiper.useImageData.splice(k1, 0, addData[k]);
                                _swiperAfter(addData[k], k1);
                                continue a;
                                //var add_=Util.formatString(t_image_swiper.slideHtml,t_image_swiper.slideHtmlIdPrefix+addData[k].id,addData[k].url);
                                //$("#"+t_image_swiper.slideHtmlIdPrefix+img.id).before(add_);
                            } else if (id < img.id && k1 == t_image_swiper.useImageData.length - 1) //当小于最后一个时
                            {
                                t_image_swiper.useImageData.splice(t_image_swiper.useImageData.length, 0, addData[k]);
                                _swiperAfter(addData[k], (t_image_swiper.useImageData.length - 1));
                                //var add_=Util.formatString(t_image_swiper.slideHtml,t_image_swiper.slideHtmlIdPrefix+addData[k].id,addData[k].url);
                                //$("#"+t_image_swiper.slideHtmlIdPrefix+img.id).after(add_);
                            }

                        }
                    } else {
                        if (t_image_swiper.slide <= 0) {
                            t_image_swiper.swiper.appendSlide('<div id="global_swiper' + 1 + '" class="swiper-slide"></div>');
                        }
                        t_image_swiper.swiper.slides[0].innerHTML = getFormatDiv(addData[k]); //Util.formatString(t_image_swiper.slideHtml, t_image_swiper.slideHtmlIdPrefix + addData[k].id, addData[k].url, addData[k].url);
                        t_image_swiper.useImageData.push(addData[k]);

                    }


                }

            }
        }
    }
    function _swiperAfter(add, index) {
        var useImageData = t_image_swiper.useImageData;

        var add_ = getFormatDiv(add); //Util.formatString(t_image_swiper.slideHtml, t_image_swiper.slideHtmlIdPrefix + add.id, add.url, add.url);
        //console.info("xxxxx:",(index+1)%t_image_swiper.slideSize);
        //console.info("yyyyy:",(parseInt(index/t_image_swiper.slideSize)+1)*t_image_swiper.slideSize+1);
        if (index == useImageData.length - 1 && (index + 1) % t_image_swiper.slideSize == 1) { //在最后页上第一个位置 ，检查 slide是否够用不够的话添加 ，然后再 添加
            //console.info("111");
            if (t_image_swiper.slide <= 0 || t_image_swiper.swiper.slides.length < index / t_image_swiper.slideSize + 1) {
                t_image_swiper.swiper.appendSlide('<div id="global_swiper' + (parseInt(index / t_image_swiper.slideSize) + 1) + '" class="swiper-slide"></div>');
            }
            t_image_swiper.swiper.slides[parseInt(index / t_image_swiper.slideSize)].innerHTML = getFormatDiv(add); //Util.formatString(t_image_swiper.slideHtml,t_image_swiper.slideHtmlIdPrefix+add.id,add.url,add.url);
            //t_image_swiper.useImageData.push(addData[k]);
            //$("#"+t_image_swiper.slideHtmlIdPrefix+useImageData[index-1].id).after(add_);
        } else if (index == useImageData.length - 1 && (index + 1) % t_image_swiper.slideSize != 1) //在 最后一页上的最后一个位置上，但不在当前页的第一个位置
        {
            $("#" + t_image_swiper.slideHtmlIdPrefix + useImageData[index - 1].id).after(add_);
            //var last=useImageData[(index/t_image_swiper.slideSize+1)*t_image_swiper.slideSize];
            //useImageData[]
        } else
        //if((index+1)%t_image_swiper.slideSize==0&&index>0)//不在最后页面&&不在最后页的第一个，先删除最后 然后添加当前，然后再 后面的页面添加 第一一个删除最后一个
        {
            //alert(index);
            //alert(index/t_image_swiper.slideSize);
            //console.info((index/t_image_swiper.slideSize+1)*t_image_swiper.slideSize);

            var last_index = (parseInt(index / t_image_swiper.slideSize) + 1) * t_image_swiper.slideSize; //获取当前页的最大序号
            console.info(last_index);
            var last = useImageData[last_index];
            if ((index + 1) % t_image_swiper.slideSize != 1) //如果位置不在第一个位置上
                $("#" + t_image_swiper.slideHtmlIdPrefix + useImageData[index - 1].id).after(add_); //然后添加当前
            else //如果在第一个位置上
                $("#" + t_image_swiper.slideHtmlIdPrefix + useImageData[last_index - t_image_swiper.slideSize + 1].id).before(add_);
            console.info(last);
            if (last) {
                $("#" + t_image_swiper.slideHtmlIdPrefix + last.id).remove(); //先删除最后
                ls["id_" + t_image_swiper.slideHtmlIdPrefix + "img_" + last.id] = false;
                _swiperAfter(last, last_index);
            } else {

            }
            //_swiperBefore(add,index);

            //$("#"+t_image_swiper.slideHtmlIdPrefix+font.id).after(add_);
        }
    }
});