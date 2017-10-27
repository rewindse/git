<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width" />
    <title>用户管理</title>
    <link href="${pageContext.request.contextPath}/assets/login_jsp/css/common.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/assets/bootstrap-table/bootstrap-table.css" rel="stylesheet" />
    
    <script src="${pageContext.request.contextPath}/assets/jquery-3.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/assets/bootstrap-table/bootstrap-table.js"></script>
    <script src="${pageContext.request.contextPath}/assets/bootstrap-table/bootstrap-table-zh-CN.js"></script>
    <script src="${pageContext.request.contextPath}/assets/web-context-path.js"></script>
    <script src="${pageContext.request.contextPath}/assets/dongliang/Index.js"></script>
    <script type="text/javascript">
    var w,h,className;
    function getSrceenWH(){
    	w = $(window).width();
    	h = $(window).height();
    	$('#dialogBg').width(w).height(h);
    }

    window.onresize = function(){  
    	getSrceenWH();
    }  
    $(window).resize();  

    $(function(){
    	getSrceenWH();
    	
    	$("#btn_add").click(function(){
    		className = $(this).attr('class');
    		$('#dialogBg').fadeIn(100);
    		$('#dialog').removeAttr('class').addClass('animated '+className+'').fadeIn();
    		$('#add_user').show();
    		$('#update_user').hide();
    		$('#username').val("");
    		$('#password').val();
    		$('#email').val("");
    		$('#realname').val("");
    		$('#tel').val("");
    		$('#collage').val("");
    	});
    	
    	$('.claseDialogBtn').click(function(){
    		$('#dialogBg').fadeOut(100,function(){
    			$('#dialog').addClass('bounceOutUp').fadeOut();
    		});
    	});
    	
    });
    
	window.operateEvents1 = {
			'click .RoleOfA' : function(e, value, row, index) {
			var user = row["userid"];
			console.log(user);
			$.ajax({
				"url" : WEB_CONTEXT_PATH + "/user/unregister",
				"type" : "get",
				"dataType" : "json",
				"async" : "false",
				"data" : {
					"userID" : user
				},
				"success" : function(data) {
					if(data[0].flag) {
						$("#tb_departments").bootstrapTable('refresh');
					}else {
						alert("删除失败！");
					}	
				},
				"error" : function(jqXHR, textStatus, errorThrown) {
					console.log(jqXHR);
				}	
			});
		}
	};
	window.operateEvents2 = {
			'click .RoleOfA' : function(e, value, row, index) {
			
			var user = row["username"];
			$.ajax({
				"url" : WEB_CONTEXT_PATH + "/user/getUser",
				"type" : "get",
				"dataType" : "json",
				"async" : "false",
				"data" : {
					"user_name" : user
				},
				"success" : function(data) {
					console.log(data);
					getSrceenWH();
					className = $(this).attr('class');
		    		$('#dialogBg').fadeIn(100);
		    		$('#dialog').removeAttr('class').addClass('animated '+className+'').fadeIn();
		    		$('#add_user').hide();
		    		$('#update_user').show();
		    		$('#username').val(data[0].username);
		    		$('#username').attr("readonly","readonly")
		    		$('#password').val("");
		    		$('#email').val(data[0].email);
		    		$('#realname').val(data[0].realname);
		    		$('#tel').val(data[0].tel);
		    		$('#collage').val(data[0].collage);
				},
				"error" : function(jqXHR, textStatus, errorThrown) {
					alert(textStatus);
				}	
			});
		}
	};


	function operateFormatter1(value, row, index) {
		return [
				'<button id="btn_deluser" type="button" class="RoleOfA btn-default bt-select">删除</button>', ]
				.join('');
	}
	function operateFormatter2(value, row, index) {
		return [
				'<button id="btn_upduser" type="button" class="RoleOfA btn-default bt-select">更改</button>', ]
				.join('');
	}
	
</script>
</head>
<body>
    <div class="panel-body" style="padding-bottom:0px;">
        <div class="panel panel-default"> 
        <div id="toolbar" class="btn-group">
            <button id="btn_add" type="button" class="btn btn-default">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
            </button>
        </div>
        <table id="tb_departments"></table>
    </div>
    <div id="dialogBg"></div>
		<div id="dialog" class="animated">
			<img class="dialogIco" width="50" height="50" src="assets/login_jsp/ico.png" alt="" />
			<div class="dialogTop">
				<a href="javascript:;" class="claseDialogBtn">关闭</a>
			</div>
			<div>
				<ul class="editInfos">
					<li>
						<label>
							&nbsp;用&nbsp;户&nbsp;名:
							<input placeholder="请输入添加用户的用户名" type="text" name="username" id="username" class="ipt"/>
							<font color="#ff0000">* </font>
						</label>
					</li>
					<li>
						<label>
							密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码:
							<input placeholder="请输入添加用户的密码" type="password" name="password" id="password" class="ipt"/>
							<font color="#ff0000">* </font>
						</label>
					</li>
					<li>
						<label>
							邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:
							<input placeholder="请输入添加用户的邮箱" type="text" name="email" id="email" class="ipt"/>
							<font color="#ff0000">&nbsp;</font>
						</label>
					</li>
					<li>
						<label>
							真实姓名:
							<input placeholder="请输入添加用户的姓名" type="text" name="realname" id="realname" class="ipt"/>
							<font color="#ff0000">&nbsp;</font>
						</label>
					</li>
					<li>
						<label>
							电话号码:
							<input placeholder="请输入添加用户的电话" type="text" name="tel" id="tel" class="ipt"/>
							<font color="#ff0000">&nbsp;</font>
						</label>
					</li>
					<li>
						<label>
							单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位:
							<input placeholder="请输入添加用户的学院" type="text" name="collage" id="collage" class="ipt"/>
							<font color="#ff0000">&nbsp;</font>
						</label>
					</li>
					<li>
						<button type="button" class="submitbutton blue" id="add_user">确定提交</button>
						<button type="button" class="submitbutton blue" id="update_user">确定提交</button>
					</li>
				</ul>
			</div>
		</div>
    </div>
</body>
</html>





