$(function() {

	// 1.初始化Table
	var oTable = new TableInit();
	oTable.Init();

	// 2.初始化Button的点击事件
	var oButtonInit = new ButtonInit();
	oButtonInit.Init();
});

var TableInit = function() {
	var oTableInit = new Object();
	// 初始化Table
	oTableInit.Init = function() {
		$('#tb_departments').bootstrapTable({
			url : 'ResultFileRequest.do', // 请求后台的URL（*）
			method : 'get', // 请求方式（*）
			toolbar : '#toolbar', // 工具按钮用哪个容器
			striped : true, // 是否显示行间隔色
			cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : true, // 是否显示分页（*）
			sortable : false, // 是否启用排序
			sortOrder : "asc", // 排序方式
			queryParams : oTableInit.queryParams,// 传递参数（*）
			sidePagination : "client", // 分页方式：client客户端分页，server服务端分页（*）
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pageSize : 10, // 每页的记录行数（*）
			pageList : [ 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
			search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
			strictSearch : true,
			showColumns : false, // 是否显示所有的列
			showRefresh : false, // 是否显示刷新按钮
			minimumCountColumns : 2, // 最少允许的列数
			clickToSelect : true, // 是否启用点击选中行
			// height : 500, // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId : "ID", // 每一行的唯一标识，一般为主键列
			showToggle : false, // 是否显示详细视图和列表视图的切换按钮
			cardView : false, // 是否显示详细视图
			detailView : false, // 是否显示父子表
			columns : [ {
				checkbox : true
			}, 
			{
				field : 'taskName',
				align: 'center',
				title : '任务名称'
			}, {
				field : 'ctime',
				align: 'center',
				title : '创建时间'
			}, {
				field : 'resultType',
				align : 'center',
				title : '结果类型'
			},{
				field : 'operate1',
				align: 'center',
				title : '操作',
				width : '80px',
				events : 'operateEvents1',
				formatter : 'operateFormatter1'
			} ]
		});
	};

	// 得到查询的参数
	oTableInit.queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, // 页面大小
			offset : params.offset, // 页码
		// departmentname: $("").val(),
		// statu: $("")
		};
		return temp;
	};
	return oTableInit;
};

var ButtonInit = function() {
	var oInit = new Object();
	var postdata = {};

	oInit.Init = function() {
		$("#select").click(function() {
			var sub = $("#sub_class").find("option:selected").val()
			$.ajax({
				"url" : "fileresult.do",
				"async" : false,
				"dataType" : "json",
				"data" : {
					"sub_class" : sub
				},
				"success" : function(data) {
					$('#tb_departments').bootstrapTable('load', data);
				}
			});
		});

		$("#btn_allexport").click(function () {
			var arrselections = $("#tb_departments").bootstrapTable(
			'getSelections');
			var sub = new Array();
			for (var i = 0; i < arrselections.length; i++) {
				sub[i] = arrselections[i].path;
			}
			var paths = sub.join(',');
			paths = paths.replace(/\\/g, '/');
			var form = document.createElement('form');
			form.method = 'post';
			form.action = 'Compressed.do'
			var urlFiled = document.createElement('input');
			urlFiled.setAttribute('hidden', 'hidden');
			urlFiled.name = 'path';
			urlFiled.value = sub;
			form.appendChild(urlFiled);
			document.body.appendChild(form);
			form.submit();
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

		$("#btn_query").click(function() {
			$("#tb_departments").bootstrapTable('refresh');

		});
	};
	return oInit
};