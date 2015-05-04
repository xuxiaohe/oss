<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="Date" uri="/WEB-INF/tld/datetag.tld"%>
<% 
	String contextPath = request.getContextPath();
%>
<html>
	<head lang="en">
	<meta charset="UTF-8">
		<title>干货统计图表</title>
		<link href="<%=contextPath%>/resources/echarts/css/font-awesome.min.css" rel="stylesheet">
	    <link href="<%=contextPath%>/resources/echarts/css/bootstrap.css" rel="stylesheet">
	    <link href="<%=contextPath%>/resources/echarts/css/carousel.css" rel="stylesheet">
	    <link href="<%=contextPath%>/resources/echarts/css/echartsHome.css" rel="stylesheet">
	    <link href="<%=contextPath%>/resources/echarts/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
	</head>
	<body>
		<div class="container-fluid">
		<ol class="breadcrumb">
			<li><a href="#">数据统计</a></li>
			<li><a href="<%=contextPath%>/table/index">图表列表</a></li>
			<li class="active">干货统计图<small> </small>
			</li>
		</ol>
		
		<!-- <div class="panel panel-default">
			<div class="panel-body"> -->
				<div class="row">
					<div class="col-xs-4">
						<label for="exampleInputEmail1">统计类型</label>
						<select class="form-control" id="countType" onchange="javascript:changeDateTag(this);">
								<option value="1">日</option>
								<option value="2">月</option>
						</select>
						
					</div>
				</div>
				<div class="row">	
					<div class="col-xs-4">
						<label for="exampleInputEmail1">日期选择</label>
						<select class="form-control" onchange="javascript:loadData();" id="dateSelect">
							<option value="201501">2015年1月</option>
							<option value="201502">2015年2月</option>
							<option value="201503">2015年3月</option>
							<option value="201504">2015年4月</option>
							<option value="201505">2015年5月</option>
							<option value="201506">2015年6月</option>
							<option value="201507">2015年7月</option>
							<option value="201508">2015年8月</option>
							<option value="201509">2015年9月</option>
							<option value="201510">2015年10月</option>
							<option value="201511">2015年11月</option>
							<option value="201512">2015年12月</option>
						</select>	
						<select class="form-control" style="display:none;" onchange="javascript:loadData();" id="yearSelect">
							<option value="2015">2015年</option>
						</select>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="main" id="main"></div> 
				</div>
				<!-- <div class="row-fluid">
					<table class="table">
						<thead>
							<tr>
								<th>时间</th>
								<th>干货总数</th>
								<th>今日新增干货数</th>
							</tr>
						</thead>
						<tbody id="table">
						</tbody>
					</table>
				</div> -->
			<!-- </div>
		</div> -->
	</div>
	<script type="text/javascript" src="<%=contextPath%>/resources/echarts/js/jquery.min.js"></script>
		<script type="text/javascript" src="<%=contextPath%>/resources/echarts/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="<%=contextPath%>/resources/echarts/js/echarts.js"></script>
		<script type="text/javascript" src="<%=contextPath%>/resources/echarts/js/bootstrap-datetimepicker.min.js"></script>
		<script type="text/javascript">
				$(function(){
					/* $("#datepicker").datetimepicker({
				        format: "yyyy-mm",
				        autoclose: true,
				        todayBtn: true,
				    }); */
				    loadData();
				});
				function changeDateTag(obj){
					if($(obj).val() == '1'){
						$("#dateSelect").show();
						$("#yearSelect").hide();
					}else{
						$("#yearSelect").show();
						$("#dateSelect").hide();
					}
					loadData();
				}
				function loadData(){
					var xAxisAry = new Array();
					var title = '';
					var dataParam = '';
					if($("#countType").val() == '1'){//设定X轴刻度 是日还是月
						xAxisAry = ['1日', '2日', '3日', '4日', '5日', '6日', '7日', '8日', '9日', '10日', '11日', '12日', '13日', '14日', '15日',
     		        	        '16日', '17日', '18日', '19日', '20日', '21日', '22日', '23日', '24日', '25日', '26日', '27日', '28日', '29日', '30日', '31日'];
						title = '干货日数据';
						dateParam = $("#dateSelect").val();
					}else{
						xAxisAry = ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'];
						title = '干货月数据';
						dateParam = $("#yearSelect").val();
					}
					var myChart;
					require.config({
			            paths: {
			                echarts: '<%=contextPath%>/resources/echarts/js'
			            }
			        });
					require(
				            [
				                'echarts',
				                'echarts/chart/line',   // 按需加载所需图表，如需动态类型切换功能，别忘了同时加载相应图表
				                'echarts/chart/bar'
				            ],
				            function (ec) {
							 	myChart = ec.init(document.getElementById('main'));
							 	myChart.showLoading({text: '正在努力的读取数据中...'  });
				            }
				     );
					$.ajax({
						url : '<%=contextPath%>/table/dry/loadData',
						data : {'type': $("#countType").val(), 'date' : dateParam},
						type : 'post',
						dataType : 'json',
						'_': new Date().getTime(),
						success : function(data){
							console.log(data);
							var ary = data.result;
							var count = new Array();//干货汇总
							var develop = new Array();//当月/日的发展量
							$.each( ary, function(index, content){
								count.push(content.sum);
								develop.push(content.numbers);
							});
							var option = {
			            		    title : {
			            		        text: title,
			            		    },
			            		    tooltip : {
			            		        trigger: 'axis'
			            		    },
			            		    legend: {
			            		        data:['干货数', '新增干货数']
			            		    },
			            		    toolbox: {
			            		        show : true,
			            		        feature : {
			            		            mark : {show: false},
			            		            magicType : {show: true, type: ['line', 'bar']},
			            		            restore : {show: true},
			            		            saveAsImage : {show: true}
			            		        }
			            		    },
			            		    calculable : true,
			            		    xAxis : [
			            		        {
			            		            type : 'category',
			            		            boundaryGap : false,
			            		            //data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
			            		        	data : xAxisAry
			            		        }
			            		    ],
			            		    yAxis : [
			            		        {
			            		            type : 'value',
			            		            axisLabel : {
			            		                formatter: '{value}'
			            		            }
			            		        }
			            		    ],
			            		    series : [
			            		        {
			            		            name:'干货数',
			            		            type:'line',
			            		            data: count,
			            		            markPoint : {
			            		                data : [
			            		                    {type : 'max', name: '最大值'},
			            		                    {type : 'min', name: '最小值'}
			            		                ]
			            		            },
			            		            markLine : {
			            		                data : [
			            		                    {type : 'average', name: '平均值'}
			            		                ]
			            		            }
			            		        },
			            		        {
			            		            name:'新增干货数',
			            		            type:'line',
			            		            data: develop,
			            		            markPoint : {
			            		                data : [
			            		                    {type : 'max', name: '最大值'},
			            		                    {type : 'min', name: '最小值'}
			            		                ]
			            		            },
			            		            markLine : {
			            		                data : [
			            		                    {type : 'average', name: '平均值'}
			            		                ]
			            		            }
			            		        }
			            		    ]
			            		};
							
							
							myChart.setOption(option); 
							myChart.hideLoading();
							// loadTable(ary);
						}
						
					});	
				}
				
		</script>
	</body>
</html>