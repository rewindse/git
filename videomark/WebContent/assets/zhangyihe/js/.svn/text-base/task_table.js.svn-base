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
		if (userRoleId == "A") {
			$('#tasktable').bootstrapTable({
				url : 'selecttask.do',
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
				strictSearch : false,
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
					field : 'taskname',
					align : 'center',
					title : '任务名'
				}, {
					field : 'ctime',
					align : 'center',
					title : '创建时间'
				}, {
					field : 'utime',
					align : 'center',
					title : '更新时间'
				}, {
					field : 'userName',
					align : 'center',
					title : '标注员'
				}, {
					field : 'statusName',
					align : 'center',
					title : '任务状态'
				}, {
					field : 'operate',
					align : 'center',
					title : '操作',
					events : operateEvents1,
					formatter : operateFormatter
				}, {
					field : 'operateupdate',
					align : 'center',
					title : '操作',
					events : operateEventsUpdate,
					formatter : operateFormatterUpdate
				}, {
					field : 'operatedelet',
					align : 'center',
					title : '操作',
					events : operateEventsDelet,
					formatter : operateFormatterDelet
				} ]
			});
		} else {
			$('#tasktable').bootstrapTable({
				url : 'selecttask.do',
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
				strictSearch : false,
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
					field : 'taskname',
					align : 'center',
					title : '任务名'
				}, {
					field : 'ctime',
					align : 'center',
					title : '创建时间'
				}, {
					field : 'utime',
					align : 'center',
					title : '更新时间'
				}, {
					field : 'statusName',
					align : 'center',
					title : '任务状态'
				}, {
					field : 'operate',
					align : 'center',
					title : '操作',
					events : operateEvents1,
					formatter : operateFormatter
				} ]
			});
		}
	};

	// 得到查询的参数
	oTableInit.queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
		limit : params.limit, // 页面大小
		offset : params.offset, // 页码
		// taskID : $("#txt_search_taskID").val(),
		// status : $("#txt_search_status").val()
		};
		return temp;
	};
	return oTableInit;
};

var ButtonInit = function() {
	var oInit = new Object();
	var postdata = {};

	oInit.Init = function() {
		$("#btn_add").click(function() {
			window.location.href = 'task.do';
		});
	};

	return oInit;
};

function search() {
	var user_name = $("#user_name").val();
	var task_name = $("#task_name").val();
	var stime = $("#stime").val();
	var etime = $("#etime").val();
	var status = $("#status").val();

	$.ajax({
		url:"searchtask.do",
		type:"get",
		dataType:"json",
		data:{
			"user_name":user_name,
			"task_name":task_name,
			"stime":stime,
			"etime":etime,
			"status":status
		},
		success:function(data) {
			$("#tasktable").bootstrapTable('destroy');
			if (userRoleId == "A") {
				$('#tasktable').bootstrapTable({
					data : data,
					toolbar : '#toolbar', // 工具按钮用哪个容器
					striped : true, // 是否显示行间隔色
					cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
					pagination : true, // 是否显示分页（*）
					sortable : false, // 是否启用排序
					sortOrder : "asc", // 排序方式
					sidePagination : "client", // 分页方式：client客户端分页，server服务端分页（*）
					pageNumber : 1, // 初始化加载第一页，默认第一页
					pageSize : 10, // 每页的记录行数（*）
					pageList : [ 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
					search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
					strictSearch : false,
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
						field : 'taskname',
						align : 'center',
						title : '任务名'
					}, {
						field : 'ctime',
						align : 'center',
						title : '创建时间'
					}, {
						field : 'utime',
						align : 'center',
						title : '更新时间'
					}, {
						field : 'userName',
						align : 'center',
						title : '标注员'
					}, {
						field : 'statusName',
						align : 'center',
						title : '任务状态'
					}, {
						field : 'operate',
						align : 'center',
						title : '操作',
						events : operateEvents1,
						formatter : operateFormatter
					}, {
						field : 'operateupdate',
						align : 'center',
						title : '操作',
						events : operateEventsUpdate,
						formatter : operateFormatterUpdate
					}, {
						field : 'operatedelet',
						align : 'center',
						title : '操作',
						events : operateEventsDelet,
						formatter : operateFormatterDelet
					} ]
				});
			} else {
				$('#tasktable').bootstrapTable({
					data : data,
					toolbar : '#toolbar', // 工具按钮用哪个容器
					striped : true, // 是否显示行间隔色
					cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
					pagination : true, // 是否显示分页（*）
					sortable : false, // 是否启用排序
					sortOrder : "asc", // 排序方式
					sidePagination : "client", // 分页方式：client客户端分页，server服务端分页（*）
					pageNumber : 1, // 初始化加载第一页，默认第一页
					pageSize : 10, // 每页的记录行数（*）
					pageList : [ 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
					search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
					strictSearch : false,
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
						field : 'taskname',
						align : 'center',
						title : '任务名'
					}, {
						field : 'ctime',
						align : 'center',
						title : '创建时间'
					}, {
						field : 'utime',
						align : 'center',
						title : '更新时间'
					}, {
						field : 'statusName',
						align : 'center',
						title : '任务状态'
					}, {
						field : 'operate',
						align : 'center',
						title : '操作',
						events : operateEvents1,
						formatter : operateFormatter
					}]
				});
			}
		},
		error:function(jqXHR, textStatus, errorThrown) {
			alert(textStatus);
		}
	});
}
