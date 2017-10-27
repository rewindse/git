<%@page import="vm.db.po.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>任务管理</title>
	<link href="assets/bootstrap/css/bootstrap.css" rel="stylesheet" />
	<link href="assets/bootstrap-table/bootstrap-table.css" rel="stylesheet" />
	<link href="assets/zhangyihe/css/taskstyle.css" rel="stylesheet" />
	
	<script src="assets/jquery-3.2.1.min.js" type="text/javascript"></script>
	<script src="assets/web-context-path.js" type="text/javascript"></script>

	<script src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.js"></script>
	<script src="${pageContext.request.contextPath}/assets/bootstrap-table/bootstrap-table.js"></script>
	<script src="${pageContext.request.contextPath}/assets/bootstrap-table/bootstrap-table-zh-CN.js"></script>
	<script type="text/javascript">
		window.operateEvents1 = {
			'click .RoleOfA' : function(e, value, row, index) {
				var taskID = row["taskID"];
				window.location.href = 'taskmessage.do?taskID=' + taskID;
			}
		};

		window.operateEventsUpdate = {
			'click .RoleOfA' : function(e, value, row, index) {
				var taskID = row["taskID"];
				window.location.href = 'task.do?taskID=' + taskID;
			}
		};
	
		window.operateEventsDelet = {
			'click .RoleOfA' : function(e, value, row, index) {
				var taskID = row["taskID"];
				window.location.href = 'delettask.do?taskID=' + taskID;
			}
		};

		function operateFormatter(value, row, index) {
			return [
					'<button id="btn_detail" type="button" class="RoleOfA btn-default bt-select">详情</button>', ]
					.join('');
		}
	
		function operateFormatterUpdate(value, row, index) {
			return [
					'<button id="btn_detail" type="button" class="RoleOfA btn-default bt-select">修改</button>', ]
					.join('');
		}
	
		function operateFormatterDelet(value, row, index) {
			return [
					'<button id="btn_detail" type="button" class="RoleOfA btn-default bt-select">删除</button>', ]
					.join('');
		}
	</script>
</head>
<body>
	<div class="panel-body" style="padding-bottom: 0px;">
		<div id="toolbar" class="btn-group">
			<button id="btn_add" type="button" class="btn btn-default">
				<span class="glyphicon glyphicon-plus"></span>新增
			</button>
			&nbsp;&nbsp; 任务名:<input type="text" name="task_name" id="task_name"
				class="mytxt" /> 创建时间:<input type="date" name="stime" id="stime"
				class="mytxt" />to <input type="date" name="etime" id="etime"
				class="mytxt" /> <span id="person">标注员:</span><input type="text"
				name="user_name" id="user_name" class="mytxt" /> 状态:<input
				type="text" name="status" id="status" class="mytxt" /> <input
				type="button" value="搜索" onclick="search()" class="button">
		</div>
		<table id="tasktable"></table>
	</div>
	<script type="text/javascript">
		$.getJSON(WEB_CONTEXT_PATH + '/user/current_user', function (user) {
			$.getJSON(WEB_CONTEXT_PATH + '/role/list', {
				"role_id" : user.roleID
			}, function (role) {
				userRoleId = role.roleID;
				var script = document.createElement('script');
				script.src = WEB_CONTEXT_PATH + "/assets/zhangyihe/js/task_table.js";
				document.body.appendChild(script);
				if (role.roleID == "B") {
					$("#person").hide();
					$("#user_name").hide();
				}
				$.ajax({
					url : "checkRolePage.do",
					type : "get",
					dataType : "json",
					success : function(data) {
						if (!data.flag) {
							document.getElementById("btn_add").style.display = "none";
						}
					}
				});
			});
		});
	</script>
</body>
</html>