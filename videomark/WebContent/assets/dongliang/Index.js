$(function () {

    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

});


var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#tb_departments').bootstrapTable({
            url: WEB_CONTEXT_PATH + "/user/listAll",        //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            // height: 500,                      //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            columns: [
           {
                field: 'ctime',
                align: 'center',
                title: '创建时间'
            }, {
                field: 'username',
                align: 'center',
                title: '用户名'
            }, {
                field: 'email',
                align: 'center',
                title: '邮箱'
            }, {
                field: 'realname',
                align: 'center',
                title: '真实名称'
            }, {
                field: 'tel',
                align: 'center',
                title: '电话'
            },{
                field: 'collage',
                align: 'center',
                title: '学院'
            }, {
                field: 'roleid',
                align: 'center',
                title: '角色'
            },{
            	field:'operate1',
            	align: 'center',
            	width:'80px',
            	events:'operateEvents1',
            	formatter:'operateFormatter1'
            },{
            	field:'operate2',
            	align: 'center',
            	width:'80px',
            	events:'operateEvents2',
            	formatter:'operateFormatter2'
            }]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset: params.offset,  //页码
        };
        return temp;
    };
    return oTableInit;
};


var ButtonInit = function() {
	var oInit = new Object();
	var postdata = {};

	oInit.Init = function() {
		$("#add_user").click(function() {
			var username = $('#username').val();
			var password = $('#password').val();
			var email = $('#email').val();
			var realname = $('#realname').val();
			var tel = $('#tel').val();
			var collage = $('#collage').val();
			if(username == "" || password == ""){
				alert("用户名和密码不能为空!");
			}else {
				$.ajax({
					"url" : WEB_CONTEXT_PATH + "/user/register",
					"type" : "get",
					"dataType" : "json",
					"async" : "false",
					"data" : {
						"username" : username,
						"password" : password,
						"email" : email,
						"realname" : realname,
						"tel" : tel,
						"collage" : collage
					},
					"success" : function(data) {
						$("#password").val("");
						if(data[0].flag) {
							$("#tb_departments").bootstrapTable('refresh');
							$("#dialogBg").hide();
							$("#dialog").hide();
						}else {
							alert("该用户已经存在或添加失败！");
						}	
					},
					"error" : function(jqXHR, textStatus, errorThrown) {
						alert(textStatus);
					}	
				});
			}
		});
		
		$("#update_user").click(function() {
			var username = $('#username').val();
			var password = $('#password').val();
			var email = $('#email').val();
			var realname = $('#realname').val();
			var tel = $('#tel').val();
			var collage = $('#collage').val();
			if(username == "" || password == ""){
				alert("用户名和密码不能为空!");
			}else {
				$.ajax({
					"url" :WEB_CONTEXT_PATH + "/user/update",
					"type" : "get",
					"dataType" : "json",
					"async" : "false",
					"data" : {
						"username" : username,
						"password" : password,
						"email" : email,
						"realname" : realname,
						"tel" : tel,
						"collage" : collage
					},
					"success" : function(data) {
						$("#password").val("");
						if(data[0].flag) {
							$("#tb_departments").bootstrapTable('refresh');
							$("#dialogBg").hide();
							$("#dialog").hide();
						}else {
							alert("修改用户失败！");
						}	
					},
					"error" : function(jqXHR, textStatus, errorThrown) {
						alert(textStatus);
					}	
				});
			}
		});

		$("#btn_edit").click(
				function() {
					var arrselections = $("#tasktable").bootstrapTable(
							'getSelections');
					
					if (arrselections.length > 1) {
						toastr.warning('只能选择一行进行编辑');
						return;
					}
					if (arrselections.length <= 0) {
						toastr.warning('请选择有效数据');

						return;
					}
					$("#myModalLabel").text("编辑");
					$("#txt_departmentname").val(
							arrselections[0].DEPARTMENT_NAME);
					$("#txt_parentdepartment").val(arrselections[0].PARENT_ID);
					$("#txt_departmentlevel").val(
							arrselections[0].DEPARTMENT_LEVEL);
					$("#txt_statu").val(arrselections[0].STATUS);

					postdata.DEPARTMENT_ID = arrselections[0].DEPARTMENT_ID;
					$('#myModal').modal();
				});

		

		$("#btn_submit").click(function() {
			postdata.DEPARTMENT_NAME = $("#txt_departmentname").val();
			postdata.PARENT_ID = $("#txt_parentdepartment").val();
			postdata.DEPARTMENT_LEVEL = $("#txt_departmentlevel").val();
			postdata.STATUS = $("#txt_statu").val();
			$.ajax({
				type : "post",
				url : "/Home/GetEdit",
				data : {
					"" : JSON.stringify(postdata)
				},
				success : function(data, status) {
					if (status == "success") {
						toastr.success('提交数据成功');
						$("#tb_departments").bootstrapTable('refresh');
					}
				},
				error : function() {
					toastr.error('Error');
				},
				complete : function() {

				}

			});
		});

		$("#btn_query").click(function() {
			$("#tb_departments").bootstrapTable('refresh');
		});
	};

	return oInit;
};