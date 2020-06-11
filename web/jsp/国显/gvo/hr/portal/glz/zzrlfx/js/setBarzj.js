var chart;
$(function() {
    var params=window.location.search;
    if(params.length>1){
        params=params.substr(1);
        var strs = params.split("&");
        var orgcode = decodeURI(strs[0].split("=")[1]);
        jQuery("#orgcode").val(orgcode);
        var sfbhxjzz = strs[1].split("=")[1];
        if(sfbhxjzz=='true'){
            jQuery("#sfbhxjzz").attr('checked',true);
            jQuery("#sfbhxjzz").next().addClass('layui-form-checked')

        }
    }
    var orgcode = encodeURI(jQuery("#orgcode").val());
    var sfbhxjzz = jQuery("#sfbhxjzz").is(":checked");
    var jsondata={}
    $.ajax({
        type: "GET",
        url: "/api/gvo/hr/portal/glz/zzrlfy/getbasedata",
        data: {'type':'1','orgcode':orgcode,'sfbhxjzz':sfbhxjzz},
        dataType: "text",
        async:false,//同步   true异步
        success: function(data){
            data=data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
            jsondata=JSON.parse(data)
        }
    });

    jQuery("#dylzrs").text(jsondata.ryldsj.dylzrs);
    jQuery("#dyrzrs").text(jsondata.ryldsj.dyrzrs);
    jQuery("#dydrzrs").text(jsondata.ryldsj.dydrzrs);
    jQuery("#symrs").text(jsondata.ryldsj.symrs);
    jQuery("#dydczrs").text(jsondata.ryldsj.dydczrs);
    var sexSeries;
    var sexfb = "[{type: 'column',name: '柱形',data:["+jsondata.sexfb.male+","+jsondata.sexfb.female+"]},{type: 'spline',name: '折线',data:["+jsondata.sexfb.male+","+jsondata.sexfb.female+"]}]";
    eval("JsonSeries=" + sexfb);
    //性别分布1
    chart = new Highcharts.Chart({
        chart: {
            renderTo: 'sexfb' //关联页面元素div#id
        },
        title: { //图表标题
            text: ' '
        },
        legend: {
            enabled: false
        },
        plotOptions: {
            series: {
                
                marker: {
                    radius: 1.5, //曲线点半径，默认是4
                    symbol: 'circle' //曲线点类型：”circle”, “square”, “diamond”, “triangle”,”triangle-down”，默认是”circle”
                }
            }},
        xAxis: { //x轴
            categories: ['男', '女'],
            //X轴类别
            labels: {
                y: 18
            } //x轴标签位置：距X轴下方18像素
        },
        yAxis: { //y轴
            title: {
                text: ''
            },
            labels: {
                formatter: function () {
                    return Highcharts.numberFormat(this.value,0);
                }
            },
            //y轴标题
            tickPixelInterval:20,
            lineWidth: 2 //基线宽度
        },
        tooltip: {
            formatter: function() { //格式化鼠标滑向图表数据点时显示的提示框
                var s;
                if (this.point.name) { // 饼状图
                    s = '<b>' + this.point.name + '</b>: <br>' + this.y ;
                } else {
                    s = '' + this.x + ': ' + this.y ;
                }
                return s;
            }
        },
        labels: { //图表标签
            items: [{
                html: '',
                style: {
                    left: '48px',
                    top: '8px'
                }
            }]
        },
        exporting: {
            enabled: false //设置导出按钮不可用
        },
        credits: {
            text: '',
            href: ''
        },
        series: JsonSeries
    });

    var ageSeries;
    var agefb = "[{type: 'column',name: '柱形',data:["+jsondata.agefb.leve1+","+jsondata.agefb.leve2+","+jsondata.agefb.leve3+","+jsondata.agefb.leve4+"]},{type: 'spline',name: '折线',data:["+jsondata.agefb.leve1+","+jsondata.agefb.leve2+","+jsondata.agefb.leve3+","+jsondata.agefb.leve4+"]}]";
    eval("JsonSeries=" + agefb);
    //年龄分布
    chart = new Highcharts.Chart({
        chart: {
            renderTo: 'age-title' //关联页面元素div#id
        },
        title: { //图表标题
            text: ' '
        },
        legend: {
            enabled: false
        },
        plotOptions: {
            series: {
                
                marker: {
                    radius: 1.5, //曲线点半径，默认是4
                    symbol: 'circle' //曲线点类型：”circle”, “square”, “diamond”, “triangle”,”triangle-down”，默认是”circle”
                }
            }},
        xAxis: { //x轴
            categories: ['25岁以下', '25~35岁', '35~45岁', '45岁以上'],
            //X轴类别
            labels: {
                y: 18
            } //x轴标签位置：距X轴下方18像素
        },
        yAxis: { //y轴
            title: {
                text: ''
            },
            labels: {
                formatter: function () {
                    return Highcharts.numberFormat(this.value,0);
                }
            },
            //y轴标题
            tickPixelInterval:20,
            lineWidth: 2 //基线宽度
        },
        plotOptions :{
            column: {
                pointPadding: 0.2,
                borderWidth: 0,
                dataLabels: {
                    enabled: false //设置显示对应y的值
                }
            }
        },
        tooltip: {
            formatter: function() { //格式化鼠标滑向图表数据点时显示的提示框
                var s;
                if (this.point.name) { // 饼状图
                    s = '<b>' + this.point.name + '</b>: <br>' + this.y ;
                } else {
                    s = '' + this.x + ': ' + this.y ;
                }
                return s;
            }
        },
        labels: { //图表标签
            items: [{
                html: '',
                style: {
                    left: '48px',
                    top: '8px'
                }
            }]
        },
        exporting: {
            enabled: false //设置导出按钮不可用
        },
        credits: {
            text: '',
            href: ''
        },
        series: JsonSeries
    });



//员工类别
    var yglbfbSeries;
    var yglbfb = "[{type: 'column',name: '柱形',data:["+jsondata.yglbfb.leve1+","+jsondata.yglbfb.leve2+","+jsondata.yglbfb.leve3+"]},{type: 'spline',name: '折线',data:["+jsondata.yglbfb.leve1+","+jsondata.yglbfb.leve2+","+jsondata.yglbfb.leve3+"]}]";
    eval("yglbfbSeries=" + yglbfb);
    chart = new Highcharts.Chart({
        chart: {
            renderTo: 'yglbfb' //关联页面元素div#id
        },
        title: { //图表标题
            text: ' '
        },
        legend: {
            enabled: false
        },
        plotOptions: {
            series: {
                
                marker: {
                    radius: 1.5, //曲线点半径，默认是4
                    symbol: 'circle' //曲线点类型：”circle”, “square”, “diamond”, “triangle”,”triangle-down”，默认是”circle”
                }
            }},
        xAxis: { //x轴
            categories: ['正式员工', '劳务人员', '实习员工'],
            //X轴类别
            labels: {
                y: 18
            } //x轴标签位置：距X轴下方18像素
        },
        yAxis: { //y轴
            title: {
                text: ''
            },
            labels: {
                formatter: function () {
                    return Highcharts.numberFormat(this.value,0);
                }
            },
            //y轴标题
            tickPixelInterval:20,
            lineWidth: 2 //基线宽度
        },
        tooltip: {
            formatter: function() { //格式化鼠标滑向图表数据点时显示的提示框
                var s;
                if (this.point.name) { // 饼状图
                    s = '<b>' + this.point.name + '</b>: <br>' + this.y ;
                } else {
                    s = '' + this.x + ': ' + this.y ;
                }
                return s;
            }
        },
        labels: { //图表标签
            items: [{
                html: '',
                style: {
                    left: '48px',
                    top: '8px'
                }
            }]
        },
        exporting: {
            enabled: false //设置导出按钮不可用
        },
        credits: {
            text: '',
            href: ''
        },
        series: yglbfbSeries
    });


//学历分布
    var xlSeries;
    var xlfb = "[{type: 'column',name: '柱形',data:["+jsondata.xlfn.leve1+","+jsondata.xlfn.leve2+","+jsondata.xlfn.leve3+"]},{type: 'spline',name: '折线',data:["+jsondata.xlfn.leve1+","+jsondata.xlfn.leve2+","+jsondata.xlfn.leve3+"]}]";
    eval("xlSeries=" + xlfb);
    chart = new Highcharts.Chart({
        chart: {
            renderTo: 'education' //关联页面元素div#id
        },
        title: { //图表标题
            text: ' '
        },
        legend: {
            enabled: false
        },
        plotOptions: {
            series: {
                
                marker: {
                    radius: 1.5, //曲线点半径，默认是4
                    symbol: 'circle' //曲线点类型：”circle”, “square”, “diamond”, “triangle”,”triangle-down”，默认是”circle”
                }
            }},
        xAxis: { //x轴
            categories: ['中专及以下', '高中', '大专及以上'],
            //X轴类别
            labels: {
                y: 18
            } //x轴标签位置：距X轴下方18像素
        },
        yAxis: { //y轴
            title: {
                text: ''
            },
            labels: {
                formatter: function () {
                    return Highcharts.numberFormat(this.value,0);
                }
            },
            //y轴标题
            tickPixelInterval:20,
            lineWidth: 2 //基线宽度
        },
        tooltip: {
            formatter: function() { //格式化鼠标滑向图表数据点时显示的提示框
                var s;
                if (this.point.name) { // 饼状图
                    s = '<b>' + this.point.name + '</b>: <br>' + this.y + '位';
                } else {
                    s = '' + this.x + ': ' + this.y + '位';
                }
                return s;
            }
        },
        labels: { //图表标签
            items: [{
                html: '',
                style: {
                    left: '48px',
                    top: '8px'
                }
            }]
        },
        exporting: {
            enabled: false //设置导出按钮不可用
        },
        credits: {
            text: '',
            href: ''
        },
        series: xlSeries
    });
    //集团工龄分布
    var jtglSeries;
    var jtglfb = "[{type: 'column',name: '柱形',data:["+jsondata.jtglfb.leve1+","+jsondata.jtglfb.leve2+","+jsondata.jtglfb.leve3+","+jsondata.jtglfb.leve4+","+jsondata.jtglfb.leve5+","+jsondata.jtglfb.leve6+","+jsondata.jtglfb.leve7+"]},{type: 'spline',name: '折线',data:["+jsondata.jtglfb.leve1+","+jsondata.jtglfb.leve2+","+jsondata.jtglfb.leve3+","+jsondata.jtglfb.leve4+","+jsondata.jtglfb.leve5+","+jsondata.jtglfb.leve6+","+jsondata.jtglfb.leve7+"]}]";
    eval("jtglSeries=" + jtglfb);
    chart = new Highcharts.Chart({
        chart: {
            renderTo: 'jtglfb' //关联页面元素div#id
        },
        title: { //图表标题
            text: ' '
        },
        legend: {
            enabled: false
        },
        plotOptions: {
            series: {
                
                marker: {
                    radius: 1.5, //曲线点半径，默认是4
                    symbol: 'circle' //曲线点类型：”circle”, “square”, “diamond”, “triangle”,”triangle-down”，默认是”circle”
                }
            }},
        xAxis: { //x轴
            categories: ['7天以下', '1个月以下', '1~6个月', '6个月~1年', '1~2年', '2~3年', '3年以上'],
            //X轴类别
            labels: {
                y: 18,
                style: {
                    fontSize: '9px'
                }
            } //x轴标签位置：距X轴下方18像素
        },
        yAxis: { //y轴
            title: {
                text: ''
            },
            labels: {
                formatter: function () {
                    return Highcharts.numberFormat(this.value,0);
                }
            },
            //y轴标题
            tickPixelInterval:20,
            lineWidth: 2 //基线宽度
        },
        tooltip: {
            formatter: function() { //格式化鼠标滑向图表数据点时显示的提示框
                var s;
                if (this.point.name) { // 饼状图
                    s = '<b>' + this.point.name + '</b>: <br>' + this.y ;
                } else {
                    s = '' + this.x + ': ' + this.y ;
                }
                return s;
            }
        },
        labels: { //图表标签
            items: [{
                html: '',
                style: {
                    left: '48px',
                    top: '8px'
                }
            }]
        },
        exporting: {
            enabled: false //设置导出按钮不可用
        },
        credits: {
            text: '',
            href: ''
        },
        series: jtglSeries
    });
    //籍贯分别

    var jgfbjson = jsondata.jhfb;
    var arr = Object.keys(jgfbjson);
    var jgfbvalues="";
    var jgfbkeys = "";
    var flagjg = "";
    for(var key in jgfbjson){
        var value= jgfbjson[key];
        jgfbkeys = jgfbkeys+flagjg+"'"+key+"'";
        jgfbvalues=jgfbvalues+flagjg+value;
        flagjg=",";
    }
    var jgfbxjson = "{categories: ["+jgfbkeys+"],labels: {";
    if(arr.length>6){
     jgfbxjson += "rotation:55,style: {fontSize: '9px'},";
    }
    jgfbxjson+="y: 18}}";
    var xAxisjgfb;
    eval("xAxisjgfb=" + jgfbxjson);
    var jgfbSeries;
    var jgfb = "[{type: 'column',name: '柱形',data:["+jgfbvalues+"]},{type: 'spline',name: '折线',data:["+jgfbvalues+"]}]";
    eval("jgfbSeries=" + jgfb);
    chart = new Highcharts.Chart({
        chart: {
            renderTo: 'jgfb' //关联页面元素div#id
        },
        title: { //图表标题
            text: ' '
        },
        legend: {
            enabled: false
        },
        plotOptions: {
            series: {
                
                marker: {
                    radius: 1.5, //曲线点半径，默认是4
                    symbol: 'circle' //曲线点类型：”circle”, “square”, “diamond”, “triangle”,”triangle-down”，默认是”circle”
                }
            }},
        xAxis: xAxisjgfb,
        yAxis: { //y轴
            title: {
                text: ''
            },
            labels: {
                formatter: function () {
                    return Highcharts.numberFormat(this.value,0);
                }
            },
            //y轴标题
            tickPixelInterval:20,
            lineWidth: 2 //基线宽度
        },
        tooltip: {
            formatter: function() { //格式化鼠标滑向图表数据点时显示的提示框
                var s;
                if (this.point.name) { // 饼状图
                    s = '<b>' + this.point.name + '</b>: <br>' + this.y + '位';
                } else {
                    s = '' + this.x + ': ' + this.y + '位';
                }
                return s;
            }
        },
        labels: { //图表标签
            items: [{
                html: '',
                style: {
                    left: '48px',
                    top: '8px'
                }
            }]
        },
        exporting: {
            enabled: false //设置导出按钮不可用
        },
        credits: {
            text: '',
            href: ''
        },
        series: jgfbSeries
    });



    //保留2位小数
    function twoDecimal(x) {
        var f_x = parseFloat(x);
        if (isNaN(f_x)) {
            //alert('错误的参数');
            return false;
        }
        var f_x = Math.round(x * 100) / 100;
        var s_x = f_x.toString();
        var pos_decimal = s_x.indexOf('.');
        if (pos_decimal < 0) {
            pos_decimal = s_x.length;
            s_x += '.';
        }
        while (s_x.length <= pos_decimal + 2) {
            s_x += '0';
        }
        return s_x;
    }


});