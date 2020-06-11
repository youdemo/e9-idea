var chart;
$(function() {
    //年龄分布
    chart = new Highcharts.Chart({
        chart: {
            renderTo: 'age-title' //关联页面元素div#id
        },
        title: { //图表标题
            text: ''
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
            //y轴标题
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
            text: 'sc.chinaz.com',
            href: 'http://sc.chinaz.com'
        },
        series: [{ //数据列
            type: 'column',
            name: '柱形',
            data: [380, 480, 520, 380]
        },
        {
            type: 'spline',
            name: '折线',
            data: [380, 480, 520, 380]
        }]
    });

    chart = new Highcharts.Chart({
        chart: {
            renderTo: 'level' //关联页面元素div#id
        },
        title: { //图表标题
            text: ''
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
            //y轴标题
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
            text: 'sc.chinaz.com',
            href: 'http://sc.chinaz.com'
        },
        series: [{ //数据列
            type: 'column',
            name: '柱形',
            data: [380, 480, 520, 380]
        },
        {
            type: 'spline',
            name: '折线',
            data: [380, 480, 520, 380]
        }]
    });

    chart = new Highcharts.Chart({
        chart: {
            renderTo: 'education' //关联页面元素div#id
        },
        title: { //图表标题
            text: ''
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
            //y轴标题
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
            text: 'sc.chinaz.com',
            href: 'http://sc.chinaz.com'
        },
        series: [{ //数据列
            type: 'column',
            name: '柱形',
            data: [380, 480, 520, 380]
        },
        {
            type: 'spline',
            name: '折线',
            data: [380, 480, 520, 380]
        }]
    });

    chart = new Highcharts.Chart({
        chart: {
            renderTo: 'attend' //关联页面元素div#id
        },
        title: { //图表标题
            text: ''
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
            //y轴标题
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
            text: 'sc.chinaz.com',
            href: 'http://sc.chinaz.com'
        },
        series: [{ //数据列
            type: 'column',
            name: '柱形',
            data: [380, 480, 520, 380, 520, 380]
        },
        {
            type: 'spline',
            name: '折线',
            data: [380, 480, 520, 380, 520, 380]
        }]
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

            data: [{
                value: 60,
                name: '总离职率'
            }]
        }]
    };

    // 为echarts对象加载数据 
    myChart.setOption(indirect_quit_all);

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

            data: [{
                value: 17,
                name: '计离职率'
            }]
        }]
    };

    // 为echarts对象加载数据 
    myChart.setOption(indirect_quit_single);

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

            data: [{
                value: 17,
                name: '总离职率'
            }]
        }]
    };

    // 为echarts对象加载数据 
    myChart.setOption(direct_quit_all);

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

            data: [{
                value: 17,
                name: '计离职率'
            }]
        }]
    };

    // 为echarts对象加载数据 
    myChart.setOption(direct_quit_single);

    //加班额度表
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

            data: [{
                value: 17,
                name: '加班额度比'
            }]
        }]
    };

    // 为echarts对象加载数据 
    myChart.setOption(overtime_than);

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

            data: [{
                value: 17,
                name: '假期额度比'
            }]
        }]
    };

    // 为echarts对象加载数据 
    myChart.setOption(vacation_than);
});