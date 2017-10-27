<%@page import="vm.util.Const"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<HTML>
<HEAD>
	<TITLE> ZTREE DEMO - Standard Data </TITLE>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<link href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/assets/yangxiao/css/fileinput.css" media="all" rel="stylesheet" type="text/css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/ztree/zTreeStyle.css" type="text/css"/>
	<script src="${pageContext.request.contextPath}/assets/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/ztree/jquery.ztree.core-3.5.js"></script>
	<script src="${pageContext.request.contextPath}/assets/yangxiao/js/fileinput.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/assets/yangxiao/js/locales/zh.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<SCRIPT type="text/javascript">

	var setting = {
			async: {
				enable: true,
				url:"getTree.do",
				autoParam:["id"],
				dataFilter: filter
			},
			callback: {
			onClick: onClick
		}
		};
        
 	function onClick(event, treeId, treeNode, clickFlag) {
		var id = treeNode.id;
		$.ajax({
			url: 'getSrc.do',
			async: false,
			dataType: 'json',
			type:"GET",
			data: {"id":id},
            success: function(result) {
            	var path = result[0].path;  
        		var tmp = path.substring(path.indexOf('store')).replace(/\\/g, '/');
        		var path1 ="${pageContext.request.contextPath}" + '/' + tmp ;
        		var type = path1.substring(path1.lastIndexOf(".") + 1, path1.length);
        		if(type.toLowerCase()=="jpg"){
					document.getElementById('preview').innerHTML = '<img src="' + path1 + '" width=80% height=80% style="margin-top:30px"></img>';
        		} else if (type.toLowerCase()=="mp4") {
					document.getElementById('preview').innerHTML = '<video controls src="' + path1 + '" width=90% height=90% style="margin-top:20px"></video>';
        		} else {
        			alert("该文件格式不支持预览");
        		} 
            },error:function(){
            	alert("error");
            }
		});
	}
	
	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			var context_path = "${pageContext.request.contextPath}"
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			var type = childNodes[i].name.substring(childNodes[i].name.lastIndexOf(".")+1);
			if(type.toLowerCase()=="mp4"){
				childNodes[i].icon = context_path+'/assets/yangxiao/css/img/4.png';
			}
			if(type.toLowerCase()=="jpg"){
				childNodes[i].icon = context_path+'/assets/yangxiao/css/img/2.png'
			}
		}
		return childNodes;
	}
		$(document).ready(function(){
			$.fn.zTree.init($("#tree"), setting);
			var treeObj = $.fn.zTree.getZTreeObj("tree");
		});

	</SCRIPT>
			<style type="text/css">
			.round_rectangle {
				border-radius: 10px;
			}
	 		#treedemo {
	 		    border-radius: 10px;
	 			float: left;
	 			width: 30%;
	 			height: 420px;
	 			margin-left: 15px;
	 			margin-top:50px;
	 			background:#F5F5F5;
	 		}
	 		
	 		 #preview {
	 		    border-radius: 10px;
	 		    border: 5px solid #D4D4D4;
	 			width: 60%;
	 		    margin-right: 30px;
	 			margin-top:50px;
	 			height: 420px;
	 			float: right;
	 			clear: both;
	 		}
	 		#upload_file {
	 			width: 30%;
	 			margin: 10px;
	 			float: left;
	 		}
	 		</style>
</HEAD>

<BODY>
    <div id="upload_file" class="round_rectangle">
			<form enctype="multipart/form-data" action="upload.do" method="POST">
				<input id="file" class="file" type="file" multiple data-show-preview="false" name="files">
		    </form>
		</div>
<div >  
     <div id="preview"  align="center">
    <h2 align="center" style="margin-top:150px;color:#D4D4D4">请选择预览文件</h2>
    </div>  
 <!-- 	<div class="show_pic" id="video_list"  align="center"></div>  -->
    <div id="treedemo">
    <ul id="tree" class="ztree" style="overflow-x:scroll;height:420px;width:100%;overflow-y:auto" ></ul>
    </div>
 </div>
</BODY>
</HTML>