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
        data: {'type':'0','orgcode':orgcode,'sfbhxjzz':sfbhxjzz},
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



//职等分布
    var zdSeries;
    var zdfb = "[{type: 'column',name: '柱形',data:["+jsondata.zdfb.leve1+","+jsondata.zdfb.leve2+","+jsondata.zdfb.leve3+","+jsondata.zdfb.leve4+"]},{type: 'spline',name: '折线',data:["+jsondata.zdfb.leve1+","+jsondata.zdfb.leve2+","+jsondata.zdfb.leve3+","+jsondata.zdfb.leve4+"]}]";
    eval("zdSeries=" + zdfb);
    chart = new Highcharts.Chart({
        chart: {
            renderTo: 'level' //关联页面元素div#id
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
            categories: ['1~3等', '4~6等', '7~12等', '13等以上'],
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
        series: zdSeries
    });


//学历分布
    var xlSeries;
    var xlfb = "[{type: 'column',name: '柱形',data:["+jsondata.xlfn.leve1+","+jsondata.xlfn.leve2+","+jsondata.xlfn.leve3+","+jsondata.xlfn.leve4+"]},{type: 'spline',name: '折线',data:["+jsondata.xlfn.leve1+","+jsondata.xlfn.leve2+","+jsondata.xlfn.leve3+","+jsondata.xlfn.leve4+"]}]";
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
            categories: ['大专及以下', '本科', '硕士', '博士'],
            //X轴类别

            labels: {
                style: {fontSize: '10px'},
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
    var jtglfb = "[{type: 'column',name: '柱形',data:["+jsondata.jtglfb.leve1+","+jsondata.jtglfb.leve2+","+jsondata.jtglfb.leve3+","+jsondata.jtglfb.leve4+"]},{type: 'spline',name: '折线',data:["+jsondata.jtglfb.leve1+","+jsondata.jtglfb.leve2+","+jsondata.jtglfb.leve3+","+jsondata.jtglfb.leve4+"]}]";
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
            categories: ['1年以下', '1-3年', '3-5年', '5年以上'],
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
        series: jtglSeries
    });
    //社会工龄分部
    //集团工龄分布
    var shglSeries;
    var shglfb = "[{type: 'column',name: '柱形',data:["+jsondata.shglfb.leve1+","+jsondata.shglfb.leve2+","+jsondata.shglfb.leve3+","+jsondata.shglfb.leve4+"]},{type: 'spline',name: '折线',data:["+jsondata.shglfb.leve1+","+jsondata.shglfb.leve2+","+jsondata.shglfb.leve3+","+jsondata.shglfb.leve4+"]}]";
    eval("shglSeries=" + shglfb);
    chart = new Highcharts.Chart({
        chart: {
            renderTo: 'shglfb' //关联页面元素div#id
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
            categories: ['1年以下', '1-3年', '3-5年', '5年以上'],
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
        series: shglSeries
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