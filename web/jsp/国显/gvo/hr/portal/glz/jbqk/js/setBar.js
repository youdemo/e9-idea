var chart;
$(function() {
    var params=window.location.search;
    if(params.length>1){
        params=params.substr(1);
        var strs = params.split("&");
        var orgcode = strs[0].split("=")[1];
        jQuery("#orgcode").val(orgcode);
    }
    var orgcode = jQuery("#orgcode").val();
    var jsondata={}
    $.ajax({
        type: "POST",
        url: "/api/gvo/hr/portal/glz/jbqk/getjbqkhzdata",
        data: {'identitytype':'0','orgcode':orgcode},
        dataType: "text",
        async:false,//同步   true异步
        success: function(data){
            data=data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
            jsondata=JSON.parse(data)
        }
    });

    jQuery("#bzgsrs").text(jsondata.zrs);
    jQuery("#bzjbsxzss").text(jsondata.bzjbsxzss);
    jQuery("#tzhjbsxzss").text(jsondata.dzhjbsxzss);
    jQuery("#dysqjbzss").text(jsondata.dysqjbzss);
    jQuery("#yjbzsszb").text(jsondata.yjbzsszb);
    jQuery("#rjss").text(jsondata.rjss);
    var hzSeries;
    var hzdata = "[{type: 'column',name: '柱形',data:["+jsondata.leve1+","+jsondata.leve2+","+jsondata.leve3+","+jsondata.leve4+","+jsondata.leve5+","+jsondata.leve6+"]}]";
    eval("hzSeries=" + hzdata);
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
            categories: ['0-30', '31-50', '51-60','61-80','81-110','110+'],
            //X轴类别
            labels: {
                y: 18
            } //x轴标签位置：距X轴下方18像素
        },
        yAxis: { //y轴
            title: {
                text: ''
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
        series:hzSeries
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

function updateData(){
    var orgcode = jQuery("#orgcode").val();
    var sfbhxjzz = jQuery("#sfbhxjzz").is(":checked");
    var identitytype = jQuery("#identitytype").val();
    var jsondata={}
    $.ajax({
        type: "POST",
        url: "/api/gvo/hr/portal/glz/jbqk/getjbqkhzdata",
        data: {'identitytype':identitytype,'orgcode':orgcode,'sfbhxjzz':sfbhxjzz},
        dataType: "text",
        async:false,//同步   true异步
        success: function(data){
            data=data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
            jsondata=JSON.parse(data)
        }
    });
    jQuery("#bzgsrs").text(jsondata.zrs);
    jQuery("#bzjbsxzss").text(jsondata.bzjbsxzss);
    jQuery("#tzhjbsxzss").text(jsondata.dzhjbsxzss);
    jQuery("#dysqjbzss").text(jsondata.dysqjbzss);
    jQuery("#yjbzsszb").text(jsondata.yjbzsszb);
    jQuery("#rjss").text(jsondata.rjss);
    var hzSeries;
    var hzdata = "["+jsondata.leve1+","+jsondata.leve2+","+jsondata.leve3+","+jsondata.leve4+","+jsondata.leve5+","+jsondata.leve6+"]";
    eval("hzSeries=" + hzdata);
    chart.series[0].setData(hzSeries)
}