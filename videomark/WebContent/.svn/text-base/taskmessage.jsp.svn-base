<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>任务详情</title>
		<link href="${pageContext.request.contextPath}/assets/zhangyihe/css/taskmessage.css" rel="stylesheet">
		<style type="text/css">
			.button {
				display: inline-block;
				zoom: 1; /* zoom and *display = ie7 hack for display:inline-block */
				*display: inline;
				vertical-align: baseline;
				margin: 0 2px;
				outline: none;
				cursor: pointer;
				text-align: center;
				text-decoration: none;
				font: 14px/100% Arial, Helvetica, sans-serif;
				padding: .5em 2em .55em;
				text-shadow: 0 1px 1px rgba(0, 0, 0, .3);
				-webkit-border-radius: .5em;
				-moz-border-radius: .5em;
				border-radius: .5em;
				-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .2);
				-moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .2);
				box-shadow: 0 1px 2px rgba(0, 0, 0, .2);
			}
			
			.white {
				color: #606060;
				border: solid 1px #b7b7b7;
				background: #fff;
				background: -webkit-gradient(linear, left top, left bottom, from(#fff),
					to(#ededed));
				background: -moz-linear-gradient(top, #fff, #ededed);
				filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffff',
					endColorstr='#ededed');
			}
		</style>
	</head>
<body>
	<p class="rw">任务名称:${taskName}</p>
	<table class="bordered" align="center">
		<thead>
			<tr>
				<th width="10%" align="center">编号</th>
				<th width="35%" align="center">文件名称</th>
				<th width="20%" align="center">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${originalFileList != null}">
				<c:forEach items="${originalFileList}" var="originalFile"
					varStatus="vs">
					<tr>
						<td align="center">${ vs.index + 1}</td>
						<td>${originalFile.note}</td>
						<c:choose>
							<c:when test="${resultFileList != null}">
								<c:set var="exit_id" value="f"></c:set>
								<c:forEach items="${resultFileList}" var="result_file_id"
									varStatus="vs">
									<c:if
										test="${originalFile.originalFileID == result_file_id.note && exit_id == 'f'}">
										<td><a
											href="videomark.do?originalFileID=${originalFile.originalFileID}">已完成</a></td>
										<c:set var="exit_id" value="t"></c:set>
									</c:if>
									<c:if
										test="${(fn:length(resultFileList) - 1) == vs.index && exit_id == 'f'}">
										<td><a
											href="videomark.do?originalFileID=${originalFile.originalFileID}">开始任务</a>
										</td>
									</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<td><a
									href="videomark.do?originalFileID=${originalFile.originalFileID}">开始任务</a>
								</td>
							</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${originalFileList == null}">
				<tr>
					<td colspan="4" align="center"><span style="color: red;">未查询到结果</span></td>
				</tr>
			</c:if>
			<tr>
				<td colspan="2" align="right">完成所有任务:<a
					href="updatestatus.do?taskID=${taskID}" class="button white">提交任务</a>
				<p style="color: red; display: inline;">*完成的任务一旦提交就不能修改</p>
				</td>
				<td colspan="2">
					<p class="mess">${mess}</p>
				</td>
			</tr>
		</tbody>
	</table>
	<script type="text/javascript">
		
	</script>
</body>
</html>