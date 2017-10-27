<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="java.io.File"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width" />
	<title>结果文件管理</title>
	<link href="${pageContext.request.contextPath}/assets/dongliang/select.css" rel="stylesheet" />
	<link href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.css" rel="stylesheet" />
	<link href="${pageContext.request.contextPath}/assets/bootstrap-table/bootstrap-table.css" rel="stylesheet" />
	
	<script src="${pageContext.request.contextPath}/assets/jquery-3.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.js"></script>
	<script src="${pageContext.request.contextPath}/assets/bootstrap-table/bootstrap-table.js"></script>
	<script src="${pageContext.request.contextPath}/assets/bootstrap-table/bootstrap-table-zh-CN.js"></script>
	<script src="${pageContext.request.contextPath}/assets/dongliang/resultfile.js"></script>
	<script type="text/javascript">
		window.operateEvents1 = {
			'click .RoleOfA' : function(e, value, row, index) {
				var path = row["path"];
				var iframe = document.createElement('iframe');
				iframe.border = 0;
				iframe.width = 0;
				iframe.heigth = 0;
				iframe.style.border = 'none';
				var url = '${pageContext.request.contextPath}/export.do?path=' + path;
				iframe.src =  encodeURI(url);
				
				document.body.appendChild(iframe);
			}
		};

	function operateFormatter1(value, row, index) {
		return [
				'<button id="btn_export" type="button" class="RoleOfA btn-default bt-select">导出</button>', ]
				.join('');
	}
</script>
<style type="text/css">
.search-field .shadow-x {
	position: absolute;
	top: 0;
	left: 1px;
	width: 438px;
	height: 0;
	border-top: 1px solid #d0d0d0;
	border-bottom: 1px solid #f0f0f0;
	overflow: hidden;
	z-index: 1;
}

.search-field .shadow-y {
	position: absolute;
	top: 0;
	left: 0;
	width: 0;
	height: 30px;
	border-left: 1px solid #d0d0d0;
	border-right: 1px solid #f0f0f0;
	overflow: hidden;
}
</style>
</head>
<body>
	<div class="panel-body" style="padding-bottom: 0px;">
		<div id="toolbar" class="btn-group">
			<button id="btn_allexport" type="button" class="btn btn-default">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>全部导出
			</button>
			&nbsp;&nbsp;
			<select id="root_class" class="select"
				onchange="javascript:root_class_onchange();">
				<option value="weather">天气</option>
				<option value="road_type">道路类型</option>
				<option value="road_condition">路况</option>
				<option value="light">光线</option>
				<option value="video_details">视频细节</option>
				<option value="target">标注信息</option>
			</select> 
			&nbsp;&nbsp;
			<select id="sub_class" class="select" name="sub_class"></select>
			<script type="text/javascript">
				window.onload = function() {
					var obj = document.getElementById('sub_class');
					obj.options.length = 0;
					var root_class = document
							.getElementById('root_class');
					var sub_class = document
							.getElementById('sub_class');
					var sub_list = [ {
						"key" : "sunshine",
						"text" : "晴"
					}, {
						"key" : "overcast",
						"text" : "阴"
					}, {
						"key" : "cloudy",
						"text" : "多云"
					}, {
						"key" : "light_rain",
						"text" : "小雨"
					}, {
						"key" : "heavy_rain",
						"text" : "大雨"
					}, {
						"key" : "snow",
						"text" : "雪"
					}, {
						"key" : "fog",
						"text" : "雾"
					}

					];
					for ( var i in sub_list) {
						var sub = sub_list[i];
						var opt = document.createElement("option");
						opt.value = sub.key;
						opt.text = sub.text;
						sub_class.append(opt);
					}
				}

				function root_class_onchange() {
					var obj = document.getElementById('sub_class');
					obj.options.length = 0;
					var root_class = document
							.getElementById('root_class');
					var sub_class = document
							.getElementById('sub_class');

					var val = root_class.options[root_class.options.selectedIndex].value;
					if (val == 'weather') {
						var sub_list = [ {
							"key" : "sunshine",
							"text" : "晴"
						}, {
							"key" : "overcast",
							"text" : "阴"
						}, {
							"key" : "cloudy",
							"text" : "多云"
						}, {
							"key" : "light_rain",
							"text" : "小雨"
						}, {
							"key" : "heavy_rain",
							"text" : "大雨"
						}, {
							"key" : "snow",
							"text" : "雪"
						}, {
							"key" : "fog",
							"text" : "雾"
						}

						];
						for ( var i in sub_list) {
							var sub = sub_list[i];
							var opt = document.createElement("option");
							opt.value = sub.key;
							opt.text = sub.text;
							sub_class.append(opt);
						}
					}
					if (val == 'road_type') {
						var sub_list = [ {
							"key" : "urban_road",
							"text" : "城市道路"
						}, {
							"key" : "expressway",
							"text" : "高速公路"
						}, {
							"key" : "rural_road",
							"text" : "乡村公路"
						} ];
						for ( var i in sub_list) {
							var sub = sub_list[i];
							var opt = document.createElement("option");
							opt.value = sub.key;
							opt.text = sub.text;
							sub_class.append(opt);
						}
					}
					if (val == 'road_condition') {
						var sub_list = [ {
							"key" : "road_condition_normal",
							"text" : "正常路况"
						}, {
							"key" : "tunnel",
							"text" : "隧道"
						}, {
							"key" : "crossing",
							"text" : "路口"
						}, {
							"key" : "bend",
							"text" : "弯道"
						}, {
							"key" : "rampway",
							"text" : "坡道"
						} ];
						for ( var i in sub_list) {
							var sub = sub_list[i];
							var opt = document.createElement("option");
							opt.value = sub.key;
							opt.text = sub.text;
							sub_class.append(opt);
						}
					}
					if (val == 'light') {
						var sub_list = [ {
							"key" : "light_normal",
							"text" : "正常光线"
						}, {
							"key" : "frontlight",
							"text" : "顺光"
						}, {
							"key" : "sidelight",
							"text" : "测光"
						}, {
							"key" : "backlight",
							"text" : "逆光"
						}, {
							"key" : "infrared",
							"text" : "红外补光"
						} ];
						for ( var i in sub_list) {
							var sub = sub_list[i];
							var opt = document.createElement("option");
							opt.value = sub.key;
							opt.text = sub.text;
							sub_class.append(opt);
						}
					}
					if (val == 'video_details') {
						var sub_list = [ {
							"key" : "FCWS",
							"text" : "FCWS"
						}, {
							"key" : "PCWS",
							"text" : "PCWS"
						}, {
							"key" : "LDWS",
							"text" : "LDWS"
						}, {
							"key" : "TSR",
							"text" : "TSR"
						}, {
							"key" : "LAMP",
							"text" : "LAMP"
						} ];
						for ( var i in sub_list) {
							var sub = sub_list[i];
							var opt = document.createElement("option");
							opt.value = sub.key;
							opt.text = sub.text;
							sub_class.append(opt);
						}
					}
					if (val == 'target') {
						var sub_list = [ {
							"key" : "persion",
							"text" : "人"
						}, {
							"key" : "vehicle",
							"text" : "车"
						}, {
							"key" : "ride",
							"text" : "骑行"
						}, {
							"key" : "traffic_sign",
							"text" : "交通标志"
						} ];
						for ( var i in sub_list) {
							var sub = sub_list[i];
							var opt = document.createElement("option");
							opt.value = sub.key;
							opt.text = sub.text;
							sub_class.append(opt);
						}
					}
				}
			</script>
			&nbsp;&nbsp;
			<button id="select">搜索</button>
		</div>
		<table id="tb_departments"></table>
	</div>
</body>
</html>
