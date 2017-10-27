<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>修改任务</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/login_jsp/css/common.css" type="text/css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/zhangyihe/css/addtaskmessage.css" type="text/css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/ztree/zTreeStyle.css" type="text/css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/assets/jquery-3.2.1.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/assets/ztree/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/assets/ztree/jquery.ztree.excheck.js"></script>
	</head>

	<body>
		<div class="container">
			<form id="contact" action="addorupdatetask.do?taskID=${taskID}"
				method="post">
				<c:if test="${taskID != null}">
					<h3>正在修改任务</h3>
					<h4>任务名:${taskname}</h4>
				</c:if>
				<c:if test="${taskID == null}">
					<h3>正在创建新任务</h3>
					<h4></h4>
				</c:if>
				<fieldset>
					<font>任务名称:</font><br/>
					<input placeholder="请填写任务名称" name="taskName" type="text" tabindex="1" value="${taskname}" required autofocus />
				</fieldset>
				<fieldset></fieldset>
				<fieldset>
					<font>标注员:</font><br/> 
					<select name="userID" style="width: 80%; height: 35px">
						<c:forEach items="${userList}" var="user" varStatus="vs">
							<c:choose>
								<c:when test="${user.userID == userID}">
									<option value="${user.userID}" selected="selected">${user.realname}</option>
								</c:when>
								<c:otherwise>
									<option value="${user.userID}">${user.realname}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</fieldset>
				<input type="hidden" name="originalfile" id="originalfile"/>
				<fieldset>
					<font>任务备注:</font><br/>
					<textarea name="note" placeholder="描述任务的备注" tabindex="5" style="vertical-align:middle;">${note}</textarea>
				</fieldset>
				<fieldset>
					<button name="submit" type="submit" id="contact-submit"
						onclick="GetCheckedAll();">提交</button>
				</fieldset>
			</form>
		</div>
		<div class="show_con">
			<ul id="tree" class="ztree"
				style="width: 100%; height: 100%; overflow-x: scroll; overflow-y: auto"></ul>
		</div>
		<div class="show_pic" id="video_list" align="center">
			<div id="show_item" class="show_item" style="width:60px;height:25px;background-color:White;float:right;">
				<a href="javascript:ListData()"><img width="40%" height="90%" src="${pageContext.request.contextPath}/assets/zhangyihe/imgs/list.png"></a>
				<a href="javascript:SquaredData()"><img width="40%" height="80%" src="${pageContext.request.contextPath}/assets/zhangyihe/imgs/squared.png"></a>
			</div>
		</div>
		
		<script type="text/javascript" language="javascript">
		var original_file_id = new Array();
		var task_id = "<%=request.getParameter("taskID")%>";
		var result;
		$.ajax({
			"url" : "gettaskoriginal.do",
			"type" : "get",
			"dataType" : "json",
			"async" : "false",
			"data" : {
				"task_id" : task_id
			},
			"success" : function(data) {
				for(var i = 0; i < data.length; i++) {
					original_file_id[i] = data[i].id;
				}
			},
			"error" : function(jqXHR, textStatus, errorThrown) {
				alert(textStatus);
			}	
		});
		
		var setting = {
				async : {
					enable : true,
					url : "getTaskTree.do?alertTaskId=" +task_id,
					autoParam : [ "id" ],
					dataFilter : filter
				},
				callback : {
					onClick : onClick
				},
				check : {
					enable : true
				},
				data : {
					simpleData : {
						enable : true
					}
				}
			};

			var isList = false;
			function onClick(event, treeId, treeNode, clickFlag) {
				var id = treeNode.id;
				var isParent = treeNode.isParent;
				$.ajax({
					url : 'getTaskSrc.do',
					async : false,
					dataType : 'json',
					type : "GET",
					data : {
						"id" : id,
						"isParent" : isParent
					},
					success : function(results) {
						result = results;
						if (isList == true) {
							ListData();
						} else {
							SquaredData();
						}
					},
					error : function() {
						alert("error");
					}
				});
			}
	
			function filter(treeId, parentNode, childNodes) {
				var context_path = "${pageContext.request.contextPath}";
				if (!childNodes)
					return null;
				for (var i = 0, l = childNodes.length; i < l; i++) {
					childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
					//
					 for(var j = 0; j<original_file_id.length;j++){
						if(childNodes[i].id==original_file_id[j]){
							childNodes[i].checked=true;
						}
					}
	 			var type = childNodes[i].name.substring(childNodes[i].name
							.lastIndexOf('.') + 1);
					if (type.toLowerCase() == "mp4") {
						childNodes[i].icon = context_path
								+ '/assets/zhangyihe/imgs/video_icon.png';
					}
					if (type.toLowerCase() == "jpg") {
						childNodes[i].icon = context_path
								+ '/assets/zhangyihe/imgs/image_icon.png';
					}
				}
				return childNodes;
			}
			function addDiyDom(treeId, treeNode) {
				var spaceWidth = 5;
				var switchObj = $("#" + treeNode.tId + "_switch"), icoObj = $("#"
						+ treeNode.tId + "_ico");
				switchObj.remove();
				icoObj.parent().before(switchObj);
				var spantxt = $("#" + treeNode.tId + "_span").html();
				if (spantxt.length > 10) {
					spantxt = spantxt.substring(0, 10) + "...";
					$("#" + treeNode.tId + "_span").html(spantxt);
				}
			}
			function GetCheckedAll() {
				var treeObj = $.fn.zTree.getZTreeObj("tree");
				var nodes = treeObj.getCheckedNodes(true);
				var msg = "";
				for (var i = 0; i < nodes.length; i++) {
					if (!nodes[i].isParent)
						msg += nodes[i].id + ";&";
				}
				$("#originalfile").val(msg);
			}
			$(document).ready(function() {
				$.fn.zTree.init($("#tree"), setting);
				
				$("#bt_b").click(GetCheckedAll);
			});
			
			function SquaredData() {
				isList = false;
				$("#video_list").empty();
				AddButton();
				var size = 100;
				var context_path = "${pageContext.request.contextPath}";
				for (var i = 0; i < result.length; i++) {
					var tmp = result[i].path;
					var path1 = context_path + '/' + tmp;
					var name = result[i].name;
					var container = document.createElement('div');
					container.style.setProperty('height', size + 10 + 'px');
					container.style.setProperty('width', size + 10 + 'px');
					container.style.setProperty('margin', '15px');
					container.style.setProperty('float', 'left');
					if (path1.indexOf('mp4') >= 0) {
						//截取视频第一帧图像做海报
						var video = document.createElement('video');
						video.style.setProperty('width', size + 'px');
						video.style.setProperty('height', size + 'px');
						video.style.setProperty('margin', '5px');
						video.controls = 'controls';
						video.preload = 'none';
						video.poster = context_path + '/store/poster/' + result[i].id + '.jpg';
						video.src = path1;

						var input = document.createElement('input');
						$(input).attr('readonly', 'true');
						input.style.setProperty('width', '110px');
						input.style.setProperty('height', '20px');
						input.style.setProperty('text-align',
								'center');
						if (name.length > 6) {
							name = name.substring(0, 6) + "...";
						}
						input.value = name;
						input.style.setProperty('border', '0px');
						container.appendChild(video);
						container.appendChild(input);
					} else if (path1.indexOf('jpg') >= 0) {
						var img = document.createElement('img');
						container.style.setProperty('text-align',
								'center');
						img.style.setProperty('width', '80%');
						img.style
								.setProperty('height', size + 'px');
						img.style.setProperty('align', 'center');
						img.style.setProperty('margin', '5px');
						img.src = path1;

						var input = document.createElement('input');
						$(input).attr('readonly', 'true');
						input.style.setProperty('width', '110px');
						input.style.setProperty('height', '20px');
						input.style.setProperty('text-align',
								'center');
						if (name.length > 6) {
							name = name.substring(0, 6) + "...";
						}
						input.value = name;
						input.style.setProperty('border', '0px');
						container.appendChild(img);
						container.appendChild(input);
					}else {
						var img = document.createElement('img');
						container.style.setProperty('text-align',
								'center');
						img.style.setProperty('width', '80%');
						img.style.setProperty('height', size + 'px');
						img.style.setProperty('align', 'center');
						img.style.setProperty('margin', '5px');
						img.src = context_path + "/assets/zhangyihe/imgs/timg.jpg";

						var input = document.createElement('input');
						$(input).attr('readonly', 'true');
						input.style.setProperty('width', '110px');
						input.style.setProperty('height', '20px');
						input.style.setProperty('text-align',
								'center');
						if (name.length > 6) {
							name = name.substring(0, 6) + "...";
						}
						input.value = name;
						input.style.setProperty('border', '0px');
						container.appendChild(img);
						container.appendChild(input);
					}
					document.getElementById('video_list')
							.appendChild(container);
				}
			} 
			
			function ListData() {
				isList = true;
				$("#video_list").empty();
				AddButton();
				var context_path = "${pageContext.request.contextPath}";
				for(var i = 0; i < result.length; i++) {
					var tmp = result[i].path;
					var name = result[i].name;
					var container = document.createElement('div');
					container.style.setProperty('height', 25 + 'px');
					container.style.setProperty('width', '90%');
					container.style.setProperty('margin-left', '30px');
					container.style.setProperty('float', 'left');
					var img = document.createElement('img');
					container.style.setProperty('text-align', 'center');
					img.style.setProperty('width', '20px');
					img.style.setProperty('height',  '80%');
					img.style.setProperty('float', 'left');
					if(tmp.indexOf('mp4') >= 0) {
						img.src = context_path + "/assets/zhangyihe/imgs/video_icon.png";	
					}else if(tmp.indexOf('jpg') >= 0) {
						img.src = context_path + "/assets/zhangyihe/imgs/image_icon.png";
					}else {
						img.src = context_path + "/assets/zhangyihe/imgs/folder_icon.jpg";
					}
					var input = document.createElement('input');
					$(input).attr('readonly', 'true');
					input.style.setProperty('width', '94%');
					input.style.setProperty('height', '80%');
					input.style.setProperty('text-align', 'left');
					input.value = name;
					input.style.setProperty('border', '0px');
					container.appendChild(img);
					container.appendChild(input);
					
					document.getElementById('video_list').appendChild(container);
				}
			}
			
			function AddButton() {
				var div = "<div id='show_item' class='show_item' style='width:60px;height:25px;background-color:White;float:right;'>"
					+ "<a href='javascript:ListData()'><img width='40%' height='90%' src='${pageContext.request.contextPath}/assets/zhangyihe/imgs/list.png'></a>&nbsp;"
					+ "<a href='javascript:SquaredData()'><img width='40%' height='80%' src='${pageContext.request.contextPath}/assets/zhangyihe/imgs/squared.png'></a>"
			    	+ "</div>";
				$("#video_list").append(div);
			}
		</script>
	</body>
</html>