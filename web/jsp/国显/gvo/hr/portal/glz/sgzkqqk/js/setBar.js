var charttb;
var charttb2;
var charttb3;
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
        url: "/api/gvo/hr/portal/glz/sgzqk/getsgzfbqk",
        data: {'orgcode':orgcode,'sfbhxjzz':sfbhxjzz},
        dataType: "text",
        async:false,//同步   true异步
        success: function(data){
            data=data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
            jsondata=JSON.parse(data)
        }
    });

    var charttbSeries;
    var charttbfb = "[{type: 'pie',name:'占比',innerSize: '70%',data:[{name:'Online岗认证',y:"+jsondata.online+"},{name:'Offline岗认证',y:"+jsondata.offline+"},{name:'未取得',y:"+jsondata.nothave+"}]}]";
    eval("charttbSeries=" + charttbfb);
    //性别分布1
    charttb = new Highcharts.Chart({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            renderTo: 'sexfb', //关联页面元素div#id
            spacing : [0, 0 , 0, 0]
        },
        credits: {
            enabled: false,
        },
        title: {
            floating:true,
            text: '上岗证获取情况分布',
            style:{
                "fontSize": "12px"
            }
        },

        tooltip: {
            enabled: true,
            pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                showInLegend: true,
                cursor: 'pointer',
                colors: [ //圆环颜色，如果数据多于颜色的个数，则颜色会重复出现
                    "#5791df", "#f3ba5b", "#e9e17e", "#90d880",
                    "#dfa4c8", "#a6e4ff", "#a4aa76", "#b6acf0"
                ],
                dataLabels: {
                    enabled: false,
                    format: '<b>{point.name}</b>: {point.percentage:.2f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    },

                },

                point: {
                    events: {
                        mouseOver: function(e) {  // 鼠标滑过时动态更新标题
                            // 标题更新函数，API 地址：https://api.hcharts.cn/highcharts#Chart.setTitle
                            var centerY = charttb.series[0].center[1],
                                titleHeight = 12;
                            charttb.setTitle({
                                y:centerY + titleHeight/2,
                                text: e.target.name+ '\t'+ e.target.y + ' %'
                            });
                        },

                        click: function(e) { // 同样的可以在点击事件里处理
                            var pointname=e.point.name;
                            if(pointname == '未取得'){
                                return;
                            }
                            updatetb2data(pointname,orgcode,sfbhxjzz);
                        }

                    }
                },
            }
        },
        legend: { //图例
           layout: "horizontal", //图例布局： 垂直排列,
           verticalAlign: "bottom",
           align: "center",
           y: 0,
           x: 0,
            labelFormatter: function() {
                return  '<span style="display:inline-block;font-size:12px;color:#666666;font-weight:100;">' + this.name.replace("岗认证","") + '</span>';
            },
           useHTML: true,
            itemWidth: 80 ,
            symbolWidth: 10 ,
            symbolHeight: 10 ,
           rtl: true //图表是否在右边
        },
        series: charttbSeries
    }, function(c) { // 图表初始化完毕后的会掉函数
        // 环形图圆心
        var centerY = c.series[0].center[1],
            titleHeight = 12;
        // 动态设置标题位置
        c.setTitle({
            y:centerY + titleHeight/2
        });
    });

    $.ajax({
        type: "GET",
        url: "/api/gvo/hr/portal/glz/sgzqk/getsgzdjfb",
        data: {'type':'online','orgcode':orgcode,'sfbhxjzz':sfbhxjzz},
        dataType: "text",
        async:false,//同步   true异步
        success: function(data){
            data=data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
            jsondata=JSON.parse(data)
        }
    });

    var charttb2Series;
    var charttb2fb = "[{type: 'pie',name:'占比',innerSize: '70%',data:[";
    var tb2flag="";
    for(var key in jsondata){
        var value= jsondata[key];
        charttb2fb +=tb2flag+"{name:'"+key+"',y:"+value+"}";
        tb2flag = ",";
    }
    charttb2fb += "]}]";
    eval("charttb2Series=" + charttb2fb);
     charttb2 = new Highcharts.Chart({
        chart: {
            renderTo: 'age-title', //关联页面元素div#id
            spacing : [0, 0 , 0, 0]
        },
        credits: {
            enabled: false,
        },
        title: {
            floating:true,
            text: '各岗位等级分布',
            style:{
                "fontSize": "12px"
            }
        },

        tooltip: {
            enabled: true,
            pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: false,
                showInLegend: true,
                cursor: 'pointer',
                colors: [ //圆环颜色，如果数据多于颜色的个数，则颜色会重复出现
                    "#5791df", "#f3ba5b", "#e9e17e", "#90d880",
                    "#dfa4c8", "#a6e4ff", "#a4aa76", "#b6acf0"
                ],
                dataLabels: {
                    enabled: false,
                    format: '<b>{point.name}</b>: {point.percentage:.2f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    },

                },

                point: {
                    events: {
                        mouseOver: function(e) {  // 鼠标滑过时动态更新标题
                            // 标题更新函数，API 地址：https://api.hcharts.cn/highcharts#Chart.setTitle
                            var centerY = charttb2.series[0].center[1],
                                titleHeight = 12;
                            charttb2.setTitle({
                                y:centerY + titleHeight/2,
                                text: e.target.name+ '\t'+ e.target.y + ' %'
                            });
                        }
                    }
                },
            }
        },
        legend: { //图例
            layout: "horizontal", //图例布局： 垂直排列,
            verticalAlign: "bottom",
            align: "center",
            y: 0,
            x: 0,
            labelFormatter: function() {
                return  '<span style="display:inline-block;font-size:12px;color:#666666;font-weight:100;">' + this.name.replace("岗认证","") + '</span>';
            },
            useHTML: true,
            itemWidth: 50 ,
            symbolWidth: 10 ,
            symbolHeight: 10 ,
            rtl: true //图表是否在右边
        },
        series: charttb2Series
    }, function(c) { // 图表初始化完毕后的会掉函数
        // 环形图圆心
        var centerY = c.series[0].center[1],
            titleHeight = 12;
        // 动态设置标题位置
        c.setTitle({
            y:centerY + titleHeight/2
        });
    });
    $.ajax({
        type: "GET",
        url: "/api/gvo/hr/portal/glz/sgzqk/getjnhqqkfb",
        data: {'orgcode':orgcode,'sfbhxjzz':sfbhxjzz},
        dataType: "text",
        async:false,//同步   true异步
        success: function(data){
            data=data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
            jsondata=JSON.parse(data)
        }
    });

    var charttb3Series;
    var charttb3fb = "[{type: 'pie',name:'占比',innerSize: '70%',data:[{name:'0个',y:"+jsondata.zero+"},{name:'1个',y:"+jsondata.one+"},{name:'2个',y:"+jsondata.two+"}]}]";
    eval("charttb3Series=" + charttb3fb);
     charttb3 = new Highcharts.Chart({
        chart: {
            renderTo: 'jnzb', //关联页面元素div#id
            spacing : [0, 0 , 0, 0]
        },
        credits: {
            enabled: false,
        },
        title: {
            floating:true,
            text: '技能获取情况分布',
            style:{
                "fontSize": "12px"
            }
        },

        tooltip: {
            enabled: true,
            pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: false,
                showInLegend: true,
                cursor: 'pointer',
                colors: [ //圆环颜色，如果数据多于颜色的个数，则颜色会重复出现
                    "#5791df", "#f3ba5b", "#e9e17e", "#90d880",
                    "#dfa4c8", "#a6e4ff", "#a4aa76", "#b6acf0"
                ],
                dataLabels: {
                    enabled: false,
                    format: '<b>{point.name}</b>: {point.percentage:.2f} %',


                },

                point: {
                    events: {
                        mouseOver: function(e) {  // 鼠标滑过时动态更新标题
                            // 标题更新函数，API 地址：https://api.hcharts.cn/highcharts#Chart.setTitle
                            var centerY = charttb3.series[0].center[1],
                                titleHeight = 12;
                            charttb3.setTitle({
                                y:centerY + titleHeight/2,
                                text: e.target.name+ '\t'+ e.target.y + ' %'
                            });
                        }
                        //,

                    }
                },
            }
        },
        legend: { //图例
            layout: "horizontal", //图例布局： 垂直排列,
            verticalAlign: "bottom",
            align: "center",
            y: 0,
            x: 0,
            labelFormatter: function() {
                return  '<span style="display:inline-block;font-size:12px;color:#666666;font-weight:100;">' + this.name.replace("岗认证","") + '</span>';
            },
            useHTML: true,
            itemWidth: 80 ,
            symbolWidth: 10 ,
            symbolHeight: 10 ,
            rtl: true //图表是否在右边
        },
        series: charttb3Series
    }, function(c) { // 图表初始化完毕后的会掉函数
        // 环形图圆心
        var centerY = c.series[0].center[1],
            titleHeight = 12;
        // 动态设置标题位置
        c.setTitle({
            y:centerY + titleHeight/2
        });
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

function updatetb2data(pointname,orgcode,sfbhxjzz) {
    var type = "";
    if(pointname == 'Online岗认证'){
        type='online';
        jQuery("#tb2name").text("各岗位等级分布（Online）");
    }else if(pointname == 'Offline岗认证'){
        type='offline';
        jQuery("#tb2name").text("各岗位等级分布（Offline）");
    }
    var jsondata={};
    $.ajax({
        type: "GET",
        url: "/api/gvo/hr/portal/glz/sgzqk/getsgzdjfb",
        data: {'type':type,'orgcode':orgcode,'sfbhxjzz':sfbhxjzz},
        dataType: "text",
        async:false,//同步   true异步
        success: function(data){
            data=data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
            jsondata=JSON.parse(data)
        }
    });

    var charttb2Series;
    var charttb2fb = "[";
    var tb2flag="";
    for(var key in jsondata){
        var value= jsondata[key];
        charttb2fb +=tb2flag+"{name:'"+key+"',y:"+value+"}";
        tb2flag = ",";
    }
    charttb2fb += "]";
    eval("charttb2Series=" + charttb2fb);
    charttb2.series[0].setData(charttb2Series)

}