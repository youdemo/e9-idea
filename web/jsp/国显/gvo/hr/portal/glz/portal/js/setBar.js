var chart;
$(function() {
    var jsondata={}
    $.ajax({
        type: "GET",
        url: "/api/gvo/hr/portal/glz/sy/getbasedata",
        data: {},
        dataType: "text",
        async:false,//同步   true异步
        success: function(data){
            data=data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
            jsondata=JSON.parse(data)
        }
    });
    jQuery("#gender-male").text(jsondata.gender.male);
    jQuery("#gender-female").text(jsondata.gender.female);

    jQuery("#zjdylzrs").text(jsondata.ryldsj.zjdylzrs);
    jQuery("#zjdydrzrs").text(jsondata.ryldsj.zjdydrzrs);
    jQuery("#zjdydczrs").text(jsondata.ryldsj.zjdydczrs);
    jQuery("#jjdylzrs").text(jsondata.ryldsj.jjdylzrs);
    jQuery("#zjdyrzrs").text(jsondata.ryldsj.zjdyrzrs);
    jQuery("#jjdyrzrs").text(jsondata.ryldsj.jjdyrzrs);
    jQuery("#jjdydrzrs").text(jsondata.ryldsj.jjdydrzrs);
    jQuery("#jjsymrs").text(jsondata.ryldsj.jjsymrs);
    jQuery("#jjdydczrs").text(jsondata.ryldsj.jjdydczrs);
    jQuery("#zjsymrs").text(jsondata.ryldsj.zjsymrs);
    //出勤率
    jQuery("#cqlbb").text(jsondata.cql.rate+'%');
    jQuery("#cqlyb").text(jsondata.cql.rate1+'%');
    jQuery("#cqgsjj").text(jsondata.cql.ave+'时');
    jQuery("#cqgszj").text(jsondata.cql.ave1+'时');
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
    var zdfb = "[{type: 'column',name: '柱形',data:["+jsondata.xlfb.leve1+","+jsondata.xlfb.leve2+","+jsondata.xlfb.leve3+","+jsondata.xlfb.leve4+"]},{type: 'spline',name: '折线',data:["+jsondata.xlfb.leve1+","+jsondata.xlfb.leve2+","+jsondata.xlfb.leve3+","+jsondata.xlfb.leve4+"]}]";
    eval("xlSeries=" + zdfb);
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
        xAxis: { //x轴
            categories: ['大专及以下', '本科', '硕士', '博士'],
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
    var jsondatahz={}
    $.ajax({
        type: "POST",
        url: "/api/gvo/hr/portal/glz/sy/getjbqkhzdata",
        data: {},
        dataType: "text",
        async:false,//同步   true异步
        success: function(data){
            data=data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
            jsondatahz=JSON.parse(data)
        }
    });

    var hzSeries;
    var hzdata = "[{type: 'column',name: '柱形',data:["+jsondatahz.leve1+","+jsondatahz.leve2+","+jsondatahz.leve3+","+jsondatahz.leve4+","+jsondatahz.leve5+","+jsondatahz.leve6+"]},{type: 'spline',name: '折线',data:["+jsondatahz.leve1+","+jsondatahz.leve2+","+jsondatahz.leve3+","+jsondatahz.leve4+","+jsondatahz.leve5+","+jsondatahz.leve6+"]}]";
    eval("hzSeries=" + hzdata);
    chart = new Highcharts.Chart({
        chart: {
            renderTo: 'attend' //关联页面元素div#id
        },
        title: { //图表标题
            text: ' '
        },
        legend: {
            enabled: false
        },
        xAxis: { //x轴
            categories: ['0~30', '31~50', '51~60', '61~80', '81~110', '110+'],
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
                    s = '<b>' + this.point.name + '</b>: <br>' + this.y + '小时';
                } else {
                    s = '' + this.x + ': ' + this.y + '小时';
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
        series: hzSeries
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
    var ratesumjjdata;
    var ratesumjj = "[{value: "+jsondata.lzl.rate_sum_jj+",name: '总离职率'}]";
    eval("ratesumjjdata=" + ratesumjj);
    //间接  总离职率 
    var myChart = echarts.init(document.getElementById('indirect_quit_all'));
    var indirect_quit_all = {
        toolbox: {
            show: true,
            feature: {
                mark: {
                    show: false
                },
                restore: {
                    show: false
                },
                saveAsImage: {
                    show: false
                }
            }
        },
        series: [{
            name: '仪表盘',
            type: 'gauge',
            min: 0,
            max: 100,
            radius: "100%",
            center: ["50%", "55%"],
            startAngle: 180,
            // 仪表盘起始角度,默认 225。圆心 正右手侧为0度，正上方为90度，正左手侧为180度。
            endAngle: 0,
            splitNumber: 4,
            // 分割段数，默认为1
            axisLine: { // 坐标轴线
                lineStyle: { // 属性lineStyle控制线条样式
                    color: [[0.2, '#1fc8a5'], [0.8, '#0e92f4'], [1, '#fd5771']],
                    width: 3,
                    shadowBlur: 5,
                    //(发光效果)图形阴影的模糊大小。该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果。 
                    shadowColor: "#fff",
                }
            },

            axisTick: { // 坐标轴小标记
                splitNumber: 5,
                // 每份split细分多少段
                length: 8,
                // 属性length控制线长
                lineStyle: { // 属性lineStyle控制线条样式
                    color: 'auto'
                }
            },

            axisLabel: { // 坐标轴文本标签，详见axis.axisLabel
                textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    color: 'auto',
                    fontSize: '11'
                }
            },
            splitLine: { // 分隔线
                show: true,
                // 默认显示，属性show控制显示与否
                length: 12,
                // 属性length控制线长
                lineStyle: { // 属性lineStyle（详见lineStyle）控制线条样式
                    color: 'auto',

                }
            },

            pointer: {
                width: 5
            },

            title: {
                show: true,
                offsetCenter: [0, '35%'],
                // x, y，单位px
                textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    fontWeight: '300',
                    fontSize: '12'

                },
                padding: 5
            },
            detail: {
                show: false,
                formatter: '{value}%',
                offsetCenter: [0, '70%'],
                // x, y，单位px
                textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    color: 'auto',
                    fontWeight: '300',
                    fontSize: '20',
                }
            },

            data:ratesumjjdata
        }]
    };

    // 为echarts对象加载数据 
    myChart.setOption(indirect_quit_all);
    var ratejjdata;
    var ratejj = "[{value: "+jsondata.lzl.rate_jj+",name: '计离职率'}]";
    eval("ratejjdata=" + ratejj);
    //间接 计离职率
    var myChart = echarts.init(document.getElementById('indirect_quit_single'));
    var indirect_quit_single = {

        toolbox: {
            show: true,
            feature: {
                mark: {
                    show: false
                },
                restore: {
                    show: false
                },
                saveAsImage: {
                    show: false
                }
            }
        },
        series: [{
            name: '仪表盘',
            type: 'gauge',
            min: 0,
            max: 100,
            radius: "100%",
            center: ["50%", "55%"],
            startAngle: 180,
            // 仪表盘起始角度,默认 225。圆心 正右手侧为0度，正上方为90度，正左手侧为180度。
            endAngle: 0,
            splitNumber: 4,
            // 分割段数，默认为1
            axisLine: { // 坐标轴线
                lineStyle: { // 属性lineStyle控制线条样式
                    color: [[0.2, '#1fc8a5'], [0.8, '#0e92f4'], [1, '#fd5771']],
                    width: 3,
                    shadowBlur: 20,
                    //(发光效果)图形阴影的模糊大小。该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果。 
                    shadowColor: "#fff",
                }
            },

            axisTick: { // 坐标轴小标记
                splitNumber: 5,
                // 每份split细分多少段
                length: 8,
                // 属性length控制线长
                lineStyle: { // 属性lineStyle控制线条样式
                    color: 'auto'
                }
            },

            axisLabel: { // 坐标轴文本标签，详见axis.axisLabel
                textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    color: 'auto',
                    fontSize: '11'
                }
            },
            splitLine: { // 分隔线
                show: true,
                // 默认显示，属性show控制显示与否
                length: 12,
                // 属性length控制线长
                lineStyle: { // 属性lineStyle（详见lineStyle）控制线条样式
                    color: 'auto',

                }
            },

            pointer: {
                width: 5
            },

            title: {
                show: true,
                offsetCenter: [0, '30%'],
                // x, y，单位px
                textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    fontWeight: '300',
                    fontSize: '12'

                },
                padding: 5
            },
            detail: {
                show: false,
                formatter: '{value}%',
                offsetCenter: [0, '70%'],
                // x, y，单位px
                textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    color: 'auto',
                    fontWeight: '300',
                    fontSize: '20',
                }
            },

            data: ratejjdata
        }]
    };

    // 为echarts对象加载数据 
    myChart.setOption(indirect_quit_single);
    var ratesumzjdata;
    var ratesumzj = "[{value: "+jsondata.lzl.rate_sum_zj+",name: '总离职率'}]";
    eval("ratesumzjdata=" + ratesumzj);
    //直接 总离职率
    var myChart = echarts.init(document.getElementById('direct_quit_all'));
    var direct_quit_all = {

        toolbox: {
            show: true,
            feature: {
                mark: {
                    show: false
                },
                restore: {
                    show: false
                },
                saveAsImage: {
                    show: false
                }
            }
        },
        series: [{
            name: '仪表盘',
            type: 'gauge',
            min: 0,
            max: 100,
            radius: "100%",
            center: ["50%", "55%"],
            startAngle: 180,
            // 仪表盘起始角度,默认 225。圆心 正右手侧为0度，正上方为90度，正左手侧为180度。
            endAngle: 0,
            splitNumber: 4,
            // 分割段数，默认为1
            axisLine: { // 坐标轴线
                lineStyle: { // 属性lineStyle控制线条样式
                    color: [[0.2, '#1fc8a5'], [0.8, '#0e92f4'], [1, '#fd5771']],
                    width: 3,
                    shadowBlur: 20,
                    //(发光效果)图形阴影的模糊大小。该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果。 
                    shadowColor: "#fff",
                }
            },

            axisTick: { // 坐标轴小标记
                splitNumber: 5,
                // 每份split细分多少段
                length: 8,
                // 属性length控制线长
                lineStyle: { // 属性lineStyle控制线条样式
                    color: 'auto'
                }
            },

            axisLabel: { // 坐标轴文本标签，详见axis.axisLabel
                textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    color: 'auto',
                    fontSize: '12'
                }
            },
            splitLine: { // 分隔线
                show: true,
                // 默认显示，属性show控制显示与否
                length: 12,
                // 属性length控制线长
                lineStyle: { // 属性lineStyle（详见lineStyle）控制线条样式
                    color: 'auto',

                }
            },

            pointer: {
                width: 5
            },

            title: {
                show: true,
                offsetCenter: [0, '30%'],
                // x, y，单位px
                textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    fontWeight: '300',
                    fontSize: '12'

                },
                padding: 5
            },
            detail: {
                show: false,
                formatter: '{value}%',
                offsetCenter: [0, '70%'],
                // x, y，单位px
                textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    color: 'auto',
                    fontWeight: '300',
                    fontSize: '20',
                }
            },

            data: ratesumzjdata
        }]
    };

    // 为echarts对象加载数据 
    myChart.setOption(direct_quit_all);
    var ratezjdata;
    var ratezj = "[{value: "+jsondata.lzl.rate_zj+",name: '计离职率'}]";
    eval("ratezjdata=" + ratezj);
    //直接 计离职率
    var myChart = echarts.init(document.getElementById('direct_quit_single'));
    var direct_quit_single = {

        toolbox: {
            show: true,
            feature: {
                mark: {
                    show: false
                },
                restore: {
                    show: false
                },
                saveAsImage: {
                    show: false
                }
            }
        },
        series: [{
            name: '仪表盘',
            type: 'gauge',
            min: 0,
            max: 100,
            radius: "100%",
            center: ["50%", "55%"],
            startAngle: 180,
            // 仪表盘起始角度,默认 225。圆心 正右手侧为0度，正上方为90度，正左手侧为180度。
            endAngle: 0,
            splitNumber: 4,
            // 分割段数，默认为1
            axisLine: { // 坐标轴线
                lineStyle: { // 属性lineStyle控制线条样式
                    color: [[0.2, '#1fc8a5'], [0.8, '#0e92f4'], [1, '#fd5771']],
                    width: 3,
                    shadowBlur: 20,
                    //(发光效果)图形阴影的模糊大小。该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果。 
                    shadowColor: "#fff",
                }
            },

            axisTick: { // 坐标轴小标记
                splitNumber: 5,
                // 每份split细分多少段
                length: 8,
                // 属性length控制线长
                lineStyle: { // 属性lineStyle控制线条样式
                    color: 'auto'
                }
            },

            axisLabel: { // 坐标轴文本标签，详见axis.axisLabel
                textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    color: 'auto',
                    fontSize: '12'
                }
            },
            splitLine: { // 分隔线
                show: true,
                // 默认显示，属性show控制显示与否
                length: 12,
                // 属性length控制线长
                lineStyle: { // 属性lineStyle（详见lineStyle）控制线条样式
                    color: 'auto',

                }
            },

            pointer: {
                width: 5
            },

            title: {
                show: true,
                offsetCenter: [0, '30%'],
                // x, y，单位px
                textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    fontWeight: '300',
                    fontSize: '12'

                },
                padding: 5
            },
            detail: {
                show: false,
                formatter: '{value}%',
                offsetCenter: [0, '70%'],
                // x, y，单位px
                textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    color: 'auto',
                    fontWeight: '300',
                    fontSize: '20',
                }
            },

            data: ratezjdata
        }]
    };

    // 为echarts对象加载数据 
    myChart.setOption(direct_quit_single);

    //加班额度表
    var jbeddata;
    var dbedzb = "[{value: "+jsondatahz.yjbzsszb+",name: '加班额度比'}]";
    eval("jbeddata=" + dbedzb);
    var myChart = echarts.init(document.getElementById('overtime_than'));
    var overtime_than = {
        toolbox: {
            show: true,
            feature: {
                mark: {
                    show: false
                },
                restore: {
                    show: false
                },
                saveAsImage: {
                    show: false
                }
            }
        },
        series: [{
            name: '仪表盘',
            type: 'gauge',
            min: 0,
            max: 100,
            radius: "100%",
            center: ["50%", "55%"],
            startAngle: 180,
            // 仪表盘起始角度,默认 225。圆心 正右手侧为0度，正上方为90度，正左手侧为180度。
            endAngle: 0,
            splitNumber: 4,
            // 分割段数，默认为1
            axisLine: { // 坐标轴线
                lineStyle: { // 属性lineStyle控制线条样式
                    color: [[0.2, '#1fc8a5'], [0.8, '#0e92f4'], [1, '#fd5771']],
                    width: 5,
                    shadowBlur: 20,
                    //(发光效果)图形阴影的模糊大小。该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果。 
                    shadowColor: "#fff",
                }
            },

            axisTick: { // 坐标轴小标记
                splitNumber: 5,
                // 每份split细分多少段
                length: 12,
                // 属性length控制线长
                lineStyle: { // 属性lineStyle控制线条样式
                    color: 'auto'
                }
            },

            axisLabel: { // 坐标轴文本标签，详见axis.axisLabel
                textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    color: 'auto',
                    fontSize: '12'
                }
            },
            splitLine: { // 分隔线
                show: true,
                // 默认显示，属性show控制显示与否
                length: 12,
                // 属性length控制线长
                lineStyle: { // 属性lineStyle（详见lineStyle）控制线条样式
                    color: 'auto',

                }
            },

            pointer: {
                width: 5
            },

            title: {
                show: true,
                offsetCenter: [0, '30%'],
                // x, y，单位px
                textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    fontWeight: '300',
                    fontSize: '12'

                },
                padding: 5
            },
            detail: {
                show: false,
                formatter: '{value}%',
                offsetCenter: [0, '70%'],
                // x, y，单位px
                textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    color: 'auto',
                    fontWeight: '300',
                    fontSize: '20',
                }
            },

            data: jbeddata
        }]
    };

    // 为echarts对象加载数据 
    myChart.setOption(overtime_than);
    var jqedb="0";
    $.ajax({
        type: "POST",
        url: "/api/gvo/hr/portal/glz/sy/getjqedbdata",
        data: {},
        dataType: "text",
        async:false,//同步   true异步
        success: function(data){
            data=data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
            jqedb=data;
        }
    });
    var jqeddata;
    var jqedzb = "[{value: "+jqedb+",name: '假期额度比'}]";
    eval("jqeddata=" + jqedzb);
    //假期额度表
    var myChart = echarts.init(document.getElementById('vacation_than'));
    var vacation_than = {
        toolbox: {
            show: true,
            feature: {
                mark: {
                    show: false
                },
                restore: {
                    show: false
                },
                saveAsImage: {
                    show: false
                }
            }
        },
        series: [{
            name: '仪表盘',
            type: 'gauge',
            min: 0,
            max: 100,
            radius: "100%",
            center: ["50%", "55%"],
            startAngle: 180,
            // 仪表盘起始角度,默认 225。圆心 正右手侧为0度，正上方为90度，正左手侧为180度。
            endAngle: 0,
            splitNumber: 4,
            // 分割段数，默认为1
            axisLine: { // 坐标轴线
                lineStyle: { // 属性lineStyle控制线条样式
                    color: [[0.2, '#1fc8a5'], [0.8, '#0e92f4'], [1, '#fd5771']],
                    width: 5,
                    shadowBlur: 20,
                    //(发光效果)图形阴影的模糊大小。该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果。 
                    shadowColor: "#fff",
                }
            },

            axisTick: { // 坐标轴小标记
                splitNumber: 5,
                // 每份split细分多少段
                length: 12,
                // 属性length控制线长
                lineStyle: { // 属性lineStyle控制线条样式
                    color: 'auto'
                }
            },

            axisLabel: { // 坐标轴文本标签，详见axis.axisLabel
                textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    color: 'auto',
                    fontSize: '12'
                }
            },
            splitLine: { // 分隔线
                show: true,
                // 默认显示，属性show控制显示与否
                length: 12,
                // 属性length控制线长
                lineStyle: { // 属性lineStyle（详见lineStyle）控制线条样式
                    color: 'auto',

                }
            },

            pointer: {
                width: 5
            },

            title: {
                show: true,
                offsetCenter: [0, '30%'],
                // x, y，单位px
                textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    fontWeight: '300',
                    fontSize: '12'

                },
                padding: 5
            },
            detail: {
                show: false,
                formatter: '{value}%',
                offsetCenter: [0, '70%'],
                // x, y，单位px
                textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    color: 'auto',
                    fontWeight: '300',
                    fontSize: '20',
                }
            },

            data: jqeddata
        }]
    };

    // 为echarts对象加载数据 
    myChart.setOption(vacation_than);
});