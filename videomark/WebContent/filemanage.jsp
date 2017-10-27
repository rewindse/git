<%@page import="vm.util.Const"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<TITLE> ZTREE DEMO - Standard Data </TITLE>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<link href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/assets/yangxiao/css/fileinput.css" media="all" rel="stylesheet" type="text/css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/ztree/zTreeStyle.css" type="text/css"/>
	<script src="${pageContext.request.contextPath}/assets/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/ztree/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/ztree/jquery.ztree.exedit-3.5.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/ztree/jquery.ztree.exhide.js"></script>
	<script src="${pageContext.request.contextPath}/assets/yangxiao/js/fileinput.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/assets/yangxiao/js/locales/zh.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>

	<style type="text/css">
		   .row{
		    width:800px;
		    height: 500px;
            border: 2px solid #D4D4D4;
            margin-left: 40px;
            margin-top: 10px;
            margin-bottom:40px;
            border-radius: 10px;
		   }
		   .col-sm-3{
		    height: 100%;
            border-right: 2px solid #D4D4D4;
            padding:10px
		   }
		    .col-sm-9{
		    height: 100%;
            padding:10px;
		   }
		    #menu {
		    text-align: left;
            height: 100px;
            width: 130px;
            border: 1px solid #333;
            background: #FEFEFE;
            padding: 5px;
            display: none;
            border-radius:10px;
            cursor: pointer;
             }
           #menu_list{
           height:100%;
           width:100%;
           }
           .menu{
           padding-left:10px;
           line-height: 30px;
           margin-left:5px;
           }
           .fmenu{
           padding-left:0px;
           line-height: 30px;
           margin-left:5px;
           }
           .fmenu li{
           text-align:left;
           font-size:16px;
           list-style-type:none;
           padding-left:20px
           }
           .menu li{
           font-size:16px;
           list-style-type:none;
           padding-left:10px
           }
           div#rMenu {
            text-align: left;
            width: 130px;
            border: 1px solid #333;
            background: #FEFEFE;
            padding: 5px;
            visibility:hidden;
            position: absolute;
            border-radius:10px;
            cursor: pointer;
           }
           div#rMenu ul{
           padding-left:10px;
           line-height: 30px;
           }
           div#rMenu ul li{
           font-size:16px;
           list-style-type:none;
           padding-left:10px
           }
	 </style>  
</head>

<body>
  <!-- 目录树搜索框 -->
  <div class="input-group" style="margin-top:10px;margin-left:45px;width:199px">
      <input id="key" type="text" class="form-control" type="text" placeholder="Search for..." >
      <span class="input-group-btn">
      <button  class="btn btn-default" type="button" onclick="searchNode()">搜索</button>
      </span>
 </div> 

<div class="row" id="allfile">  
    <!-- 目录树 -->
    <div class="col-sm-3" id="treelist">
    <ul id="tree" class="ztree" style="overflow-x:auto;height:100%;width:100%;overflow-y:auto" ></ul>
    </div>
    <!-- 文件列表 -->
 	<div class="col-sm-9" id="filemanage" style="overflow-y:auto;">
 	    <div id='file_list' style="width:90%;margin-top:5px">
 	    </div>
 	     <div id="menu">
	     <ul class="menu">
	     <li data-id="create" data-toggle = 'modal' data-target = '#myModal' data-title="新建文件夹" data-label="文件夹名称" data-submit="创建">新建文件夹</li>
	     <li data-id="upfile" data-toggle = 'modal' data-target = '#myModal' data-title="上传文件" data-label="" data-submit="上传">上传文件</li>
	     <li data-id="updir" data-toggle = 'modal' data-target = '#myModal'  data-title="上传文件夹" data-label="" data-submit="上传">上传文件夹</li>
     	</ul>
     	</div>
 	</div> 
 </div>
 <!-- 模态框 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel"></h4>
      </div>
      <div class="modal-body">
       <form>
          <div class="form-group">
            <label for="information" class="control-label"></label>
            <input class="form-control" id="information" > 
          </div>
        </form>
      </div>
      <div class="modal-footer">      
        <button type="submit" class="btn btn-primary" id="submit"></button>
      </div>
    </div>
  </div>
</div>
<!-- 树的右键菜单 -->
 <div id="rMenu">
	<ul>
		<li id="m_add" onclick="addTreeNode();">新建</li>
		<li id="m_del" onclick="removeTreeNode();">删除</li>
		<li id="m_rename" onclick="renameTreeNode();">重命名</li>	
		<li id="m_upfile" onclick="upfileTreeNode();">上传文件</li>
	    <li id="m_updir" onclick="updirTreeNode();">上传文件夹</li>	
	    <li id="m_preview" onclick="previewFile();">预览文件</li>
	    </ul>
</div>

   <script type="text/javascript">
    context_path = "${pageContext.request.contextPath}"
    id = '0';
    //树的设置
	var setting = {
			async: {
				enable: true,
				url:"file/get_tree",
				autoParam:["id"],
				dataFilter: filter
			},
			callback: {
			onClick: onClick,
			onRightClick: OnRightClick,
			beforeRename:beforeRename,
		}
		};
    //点击树节点显示文件的方法 
	function onClick(event, treeId, treeNode, clickFlag) {
		id = treeNode.id;
		var isParent = treeNode.isParent;
    	var curPage = 1;
	 	$.ajax({
			url: 'file/get_src',
			async: false,
			dataType: 'json',
			type:"GET",
			data: {"id":id,"isParent":isParent},
	        success: function(result) {
	        	$("#file_list").empty();
	        	if(result!=null&&result.length>0){
			        getData(result);
		        }

	        },error:function(){
	        	alert("error");
	        } 
		}); 
		}
    
 	var hiddenNodes=[]; //用于存储被隐藏的结点
 	//过滤节点，根据节点名后缀显示不同的图标
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
	//右键事件，判断选中节点的类型，执行showRMenu方法
	function OnRightClick(event, treeId, treeNode) {
		if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
			zTree.cancelSelectedNode();
			showRMenu("blank", event.pageX, event.pageY);
		} else if (treeNode && treeNode.isParent && treeNode.id!=0) {
			zTree.selectNode(treeNode);
			showRMenu("pNode", event.pageX, event.pageY);
		} else if(treeNode && !treeNode.isParent){
			zTree.selectNode(treeNode);
			showRMenu("sNode", event.pageX, event.pageY);
		} else if (treeNode && treeNode.isParent && treeNode.id==0){
			zTree.selectNode(treeNode);
			showRMenu("root", event.pageX, event.pageY);
		}
	}
	function hideRMenu() {
		if (rMenu) rMenu.css({"visibility": "hidden"});
		$("body").unbind("mousedown", onBodyMouseDown);
	}
	//定义不同的节点类型，展示和隐藏的菜单项
	function showRMenu(type, x, y) {
		$("#rMenu ul").show();
		if (type=="root") {
			$("#m_add").show();
 			$("#m_upfile").show();
			$("#m_updir").show(); 
			$("#m_del").hide();
			$("#m_rename").hide();
			$("#m_preview").hide();
	        rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});
			$("body").bind("mousedown", onBodyMouseDown);
		} else if(type=="pNode"){
			$("#m_add").show();
			$("#m_del").show();
			$("#m_rename").show();
 			$("#m_upfile").show();
			$("#m_updir").show();
			$("#m_preview").hide();
	        rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});
			$("body").bind("mousedown", onBodyMouseDown);
		} else if(type=="sNode"){
			$("#m_add").hide();
			$("#m_preview").show();
			$("#m_del").show();
			$("#m_rename").show();
 			$("#m_upfile").hide();
			$("#m_updir").hide(); 
	        rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});
			$("body").bind("mousedown", onBodyMouseDown);
		}else if(type=="blank"){
			rMenu.css({"visibility" : "hidden"});
		}

	}
	//隐藏菜单
	
	function onBodyMouseDown(event){
		if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
			rMenu.css({"visibility" : "hidden"});
		}
	}
	var addCount = 1;
	//新建树节点
	function addTreeNode() {
		hideRMenu();
		var treeaddnode = document.getElementById("m_add");
		treeaddnode.dataset.id = "m_mkdir";
		treeaddnode.dataset.toggle = "modal";
		treeaddnode.dataset.target = "#myModal";
		treeaddnode.dataset.title = "新建文件夹";
		treeaddnode.dataset.submit = "确定";
		return true;
	}
	//生成随机字符串
	function uuid() {
		    var s = [];
		    var hexDigits = "0123456789abcdef";
		    for (var i = 0; i < 36; i++) {
		        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
		    }
		    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
		    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
		    s[8] = s[13] = s[18] = s[23] = "-";
		    var uuid = s.join("");
		    return uuid;
		}
	//删除树节点
	function removeTreeNode() {
		hideRMenu();
		var nodes = zTree.getSelectedNodes();
		if (nodes && nodes.length>0) {
			var pid = nodes[0].pid;
			var id = nodes[0].id;
			if (nodes[0].children && nodes[0].children.length > 0) {
				var msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉。\n\n请确认！";
				if (confirm(msg)==true){
					delNode(pid,id);
					zTree.removeNode(nodes[0]);
				}
			} else {
				var msg = "确定要删除该文件吗！";
				if(confirm(msg)==true){
					delNode(pid,id);
					zTree.removeNode(nodes[0]);
				}
			}
		}
	}
	//树节点重命名
	function beforeRename(treeId,treeNode,newName){
		var nodes  = zTree.getNodeByParam("id",treeNode.pid);
		var children = nodes.children;
		if(newName==0){
			alert("名字不能为空！");
		}
		for(var i = 0;i<children.length;i++){
			if(newName==children[i].name){
				alert("文件已存在，请刷新页面");
			}
		}
			$.ajax({
				url:"file/rename",
				type:"get",
				dataType:"json",
				data:{
					"newname":newName,
					"oriID":treeNode.id
				},
				success:function(data){
                    getSrc(treeNode.pid,true);
				}
			});
	}
	
	function renameTreeNode() {
		hideRMenu();
		var nodes = zTree.getSelectedNodes();
		zTree.editName(nodes[0]);
	}
	
	function checkTreeNode(checked) {
		var nodes = zTree.getSelectedNodes();
		if (nodes && nodes.length>0) {
			zTree.checkNode(nodes[0], checked, true);
		}
		hideRMenu();
	}
	function upfileTreeNode(){
		var nodes = zTree.getSelectedNodes();
		if (nodes && nodes.length>0) {
			nodes_id = nodes[0].id;
			var treeupfile = document.getElementById("m_upfile");
   			treeupfile.dataset.id = "m_upfile";
   			treeupfile.dataset.toggle = "modal";
   			treeupfile.dataset.target = "#myModal";
   			treeupfile.dataset.title = "文件上传";
   			treeupfile.dataset.submit = "确定";
		}
		hideRMenu();
	}
	function updirTreeNode(){
		var nodes = zTree.getSelectedNodes();
		if (nodes && nodes.length>0) {
			nodes_id = nodes[0].id;
			var treeupfile = document.getElementById("m_updir");
   			treeupfile.dataset.id = "m_updir";
   			treeupfile.dataset.toggle = "modal";
   			treeupfile.dataset.target = "#myModal";
   			treeupfile.dataset.title = "文件夹上传";
   			treeupfile.dataset.submit = "确定";
		}
		hideRMenu();
	}
	
	//把删除节点的id提交到后台
	function delNode(pid,oriID){
	    	$.ajax({
    			url: 'file/delete',
    			dataType: 'json',
    			type:"GET",
    			data: {"oriID":oriID},
                success: function(result) {
              	  console.log("删除成功");
      			  getSrc(pid,true);
                },error:function(){
              	  console.log("error");
                }
      	  }); 
	}

 	//树的模糊查询	
	function searchNode(){
  		var setting1 = {
  				data:{
  					simpleData:{
  						enable:true,
  						idKey:"id",
  						pIdKey:"pid",
  						rootPId:"",
  					}
  				},
				callback: {
				onClick: onClick,
				onRightClick: OnRightClick,
				beforeRename:beforeRename,
			},
			async:{
				dataFilter:filter
			}
			};
  		var zNodes = [];
  		var zNodesflag = false;
        $.ajax({
      	  url: 'file/get_alltree',
      	  dataType: 'json',
          type:"GET",
          success: function(result) {
      		zNodes = result;
      		zNodesflag = true;
            },error:function(){
          	  console.log("error");
            }
         }); 
        setTimeout(function(){
        		$.fn.zTree.init($("#tree"), setting1,zNodes);
                zTree = $.fn.zTree.getZTreeObj("tree");
        	 	var allNode = zTree.transformToArray(zTree.getNodes());
        		var value = $('#key').val(); 	
        	    nodeList = zTree.getNodesByParamFuzzy("name", value);
        	    nodeList = zTree.transformToArray(nodeList);
        	    for(var i in nodeList){
        	        findParent(zTree,nodeList[i]);
        	    }
        	    if(nodeList.length>0){
        	    	zTree.hideNodes(allNode);
            	    zTree.showNodes(nodeList);
        	    }else{
        	    	zTree.showNodes(allNode);
        	    }       		
		},200);
	}
	//找到匹配节点的父节点，将其展开
    function findParent(zTree,node){
           zTree.expandNode(node,true,false,false);
           var pNode = node.getParentNode();
           if(pNode != null){
            nodeList.push(pNode);
            findParent(zTree,pNode);
           }
    } 
    $('#treelist')[0].oncontextmenu = function(ev) {
    	hideFmenu();
        hideMenu();
    }
    $('#tree')[0].oncontextmenu = function(ev) {
    	hideFmenu();
    	hideMenu();     
    }
    function hideFmenu(){
 	   var file_list = document.getElementById("file_list").children;
	   for(var i =0;i<file_list.length;i++){
		   var id = "fmenu"+i;
		   var fmenu = document.getElementById(id);
		   if(fmenu){
			   fmenu.style.display = "none";
		   }
	   }
    }
    function hideMenu(){
		   if(document.getElementById("menu")){
			   menu.style.display = "none";
		   }
    }
     $('#filemanage')[0].oncontextmenu = function(ev) {
         hideFmenu();
         var filemanage = document.getElementById("filemanage");
         var oEvent = ev || event;
         menu.style.position='absolute';
         menu.style.left = oEvent.pageX - filemanage.offsetLeft + "px";
         menu.style.top = oEvent.pageY - filemanage.offsetTop + filemanage.scrollTop + "px";
         menu.style.display = "block";
         return false;
     }
       $('#filemanage')[0].onclick=function(){
    	   hideMenu();
    	   hideFmenu();
       }
       $('#tree')[0].onclick=function(){
    	   hideMenu();
    	   hideFmenu();
       }
       $('#treelist')[0].onclick=function(){
    	   hideMenu();
    	   hideFmenu();
       }
       //显示模态框
       $('#myModal').on('show.bs.modal', function (event) {
    	   var li = $(event.relatedTarget) 
           var li_id = li.data('id');
    	   var title = li.data('title');
    	   var label = li.data('label');
    	   var submit = li.data('submit');
    	   var modal = $(this);
    	   var button = document.getElementById('submit');
    	   var information = document.getElementById('information');
           if(li_id == 'upfile' || li_id == 'm_upfile'){ 
        	   information.type = "file";
        	   information.multiple = "multiple";
        	   information.name = "files";
           }else if(li_id == 'updir' || li_id == 'm_updir'){
        	   information.type = "file";
        	   information.webkitdirectory = "webkitdirectory";
           }else{
        	   information.type = "text";
           }
           button.name = li_id; 
    	   modal.find('.modal-title').text(title);
           modal.find('.control-label').text(label);
           modal.find('#submit').text(submit);
    	});
       //模态框里按钮的点击事件
      $('#submit').on("click",function(){
	        var submit = document.getElementById('submit');
	        var sub_name = submit.name;
	        if(sub_name == 'create'){
	        	var name = $('#information').val();
	        	createDir(name);
	        	submit.dataset.dismiss="modal"; 
	        }
	        if(sub_name == 'upfile' || sub_name =='updir'){
                uploadFile();
            	submit.dataset.dismiss="modal"; 
	        }
	        if(sub_name == 'rename'){
	        	var newname = $('#information').val();
				renameFile(newname);
	        	submit.dataset.dismiss="modal"; 
	        }
	        if(sub_name == 'm_upfile' || sub_name =='m_updir'){
                uploadTreeFile();
                submit.dataset.dismiss="modal";
	        }
	        if(sub_name == 'm_mkdir'){
	        	var dirName = $('#information').val();
                addNode(dirName);
                submit.dataset.dismiss="modal";
	        }
       });

        //删除文件   
      function delFile(){ 
		  var msg = "确定要删除该文件吗！";
		  if(confirm(msg)==true){
	    	  var container = document.getElementById(container_id);
	    	  oriID = container.name;
	          var pid = id;
	          var node = zTree.getNodeByParam("id",oriID);
	          zTree.removeNode(node);
	          delNode(pid,oriID);
		  }
      }
    //创建文件夹
      function dirPost(id,dirName,orgId){
          $.ajax({
        	  url: 'file/mkdir',
        	  dataType: 'json',
              type:"GET",
        	  data: {"id":id,"dirName":dirName,"orgId":orgId},
              success: function(result) {
            	  if(result[0].a=="false"){
            		  alert("该文件已存在");
            	  }else{
            	      if(zTree.getSelectedNodes()[0]) {
            	             var node = zTree.getSelectedNodes()[0];
            	             if(node.isParent){
                	             var newNode = {id:orgId,pid:id,name:dirName,isParent:true};
                       		     zTree.addNodes(node, newNode,true); 
                                 getSrc(id,true);
            	             }else{
            	            	 alert("该文件下不能创建文件夹");
            	             }
            	      }else{
            	    	  alert("文件夹创建失败");
            	      }

            	  }
              },error:function(){
            	 console.log("error");
              }
           });
      }
      function createDir(value){
		  var orgId = uuid();
    	  var dirName = value;
    	  if(dirName!=""){
                  dirPost(id,dirName,orgId); 
    	  }else{
    		  alert("文件名不能为空");
    	  }
    }
      function addNode(dirName){
    	  var addnode = 0;
    	  if(zTree.getSelectedNodes()[0]) {
    		  addnode = zTree.getSelectedNodes()[0].id;
    	  }
    	  var orgId = uuid();
    	  if(dirName!=""){    		  
              dirPost(addnode,dirName,orgId); 
    	  }else{
    		  alert("文件名不能为空");
    	  }
      }
      
    //上传文件或文件夹
      function uploadFile(){
       	var formdata = new FormData();
        var files = document.getElementById("information");
       	var filelist = files.files;
       	for(var i =0;i<filelist.length;i++){
       	   formdata.append("files",filelist[i]);  
       	}    
       	formdata.append("id",id);
          $.ajax({
          	url:'file/upload',
          	type:'POST',
          	dataType:'json',
          	data:formdata,
          	cache:false,
            processData: false,
            contentType: false, 
            success : function(data) {  
                getSrc(id,true);
                $.fn.zTree.init($("#tree"), setting);
              },  
            error : function() {  
                  console.log("失败");
              }  
          });
      }
      function uploadTreeFile(){
   		var formdata = new FormData();
        var files = document.getElementById("information");
        var filelist = files.files;
        for(var i =0;i<filelist.length;i++){
         	formdata.append("files",filelist[i]);  
        }    
        formdata.append("id",nodes_id);
            $.ajax({
            	url:'file/upload',
            	type:'POST',
            	dataType:'json',
            	data:formdata,
            	cache:false,
              processData: false,
              contentType: false, 
              success : function(data) {  
                 getSrc(nodes_id,true);  
                 $.fn.zTree.init($("#tree"), setting);
                },  
              error : function() {  
                    console.log("失败");
                }  
            }); 
    }
    function getSrc(id,isParent){
	 	$.ajax({
		url: 'file/get_src',
		async: false,
		dataType: 'json',
		type:"GET",
		data: {"id":id,"isParent":isParent},
        success: function(result) {
        	$("#file_list").empty();
        	if(result!=null&&result.length>0){
		        getData(result);
	        }

        },error:function(){
        	alert("error");
        } 
	});
    }
    //文件重命名
      function renameFile(newname){
      	var container = document.getElementById(container_id);
     	var input=container.childNodes[1];  
		var oriID = container.name;
		var pid = id;
    	  $.ajax({
   			url: 'file/rename',
  			dataType: 'json',
  			type:"GET",
  			data: {"newname":newname,"oriID":oriID},
              success: function(result) {
            	  flag = result[0].flag;
            	  if(flag=="true"){
                   	  getSrc(pid,true);
                      $.fn.zTree.init($("#tree"), setting);
            	  }
            	  else{
            		  alert("已存在该文件，不能重命名");
            	  }
              },error:function(){
            	  console.log("error");
              }
    	  });
      }     
	   //设置文件列表格式，自定义右键菜单
      function getData(data){
   		   var size = 100;
           for(var i=0;i<data.length;i++){  
       			var oriID = data[i].id;
           		var path = data[i].path; 
           		var tmp = path.substring(path.indexOf('store')).replace(/\\\\/g, '/');
           		var path1 = context_path + '/' + tmp ;
           		console.log(path1);
           		var name = tmp.substring(tmp.lastIndexOf('/')+1);
           		var container = document.createElement('div');
       			container.style.setProperty('height', size+10+'px');
       			container.style.setProperty('width', size+10+'px');
       			container.style.setProperty('margin', '15px');
       			container.style.setProperty('float', 'left');
       			container.className = "container";
                container.id = "container"+i; 
                container.name = oriID;
                    
                var img = document.createElement('img');
           	    container.style.setProperty('text-align', 'center');
           		img.style.setProperty('width', size+'px');
           		img.style.setProperty('height', size+'px');
           		img.style.setProperty('align', 'center');
           		img.style.setProperty('margin', '5px');
           		if(data[i].isParent){
           		    img.src = context_path + '/assets/yangxiao/css/img/folder.png';
           		}else{
           			if(path1.indexOf('mp4') >= 0){
           				img.src = context_path + '/assets/yangxiao/css/img/play19.png';
           			}else if(path1.indexOf('jpg') >= 0){
           				img.src = path1;
           			}else{
         				img.src = context_path + '/assets/yangxiao/css/img/file.png';
           			}
           		}
           		var input = document.createElement('input');
           		input.style.setProperty('width','110px');
           		input.style.setProperty('height','20px');
           		input.style.setProperty('text-align','center');
           		if(name.length>10){
           			name = name.substring(0,10)+"...";
           		}
           		input.value = name;
           		input.readOnly = "true";
           		input.style.setProperty('border','0px');
           		container.appendChild(img);
           		container.appendChild(input);
      				
   	 	 	//自定义菜单栏
            var fmenu = document.createElement("div");
    	 	fmenu.id = "fmenu"+i;
   			fmenu.style.setProperty('width', '150px');
   			fmenu.style.setProperty('height', '80px');
   			fmenu.style.setProperty('border', '1px solid #333');
   			fmenu.style.setProperty('background', '#FEFEFE');
   			fmenu.style.setProperty('position', 'absolute');
   			fmenu.style.setProperty('display', 'none');
   			fmenu.style.setProperty('border-radius', '10px');
    	 	container.appendChild(fmenu);
    	 	    
   			var filemenu = document.createElement("ul");
   			filemenu.className = "fmenu";
   			fmenu.appendChild(filemenu);
   			
   			var delfile = document.createElement("li");
   			var textnode1=document.createTextNode("删除文件");   //创建一个文本节点内容
   			delfile.appendChild(textnode1);
   			delfile.onclick = delFile;
   			var rename = document.createElement("li");
   			var textnode2=document.createTextNode("重命名");   //创建一个文本节点内容
   			rename.appendChild(textnode2);
   			rename.dataset.id = "rename";
   			rename.dataset.toggle = "modal";
   			rename.dataset.target = "#myModal";
   			rename.dataset.title = "重命名";
   			rename.dataset.label = "新名称";
   			rename.dataset.submit = "确定";
   			filemenu.appendChild(delfile);
   			filemenu.appendChild(rename); 
   		    
   			document.getElementById('file_list').appendChild(container);
   	    }
	       //指定文件的右键事件，和点击事件
		   var filemanage = document.getElementById("filemanage");
		   $('.container').on('contextmenu' , function(ev){
			   var oEvent = ev || event;
			   hideMenu();
		 	   var file_list = document.getElementById("file_list").children;
			   for(var i =0;i<file_list.length;i++){
				   var id = "fmenu"+i;
				   container_id = 'container' + $(this).index();
				   var fmenu = document.getElementById(container_id).lastChild;
				   if(fmenu.id!=id){
					   var fmenu1 = document.getElementById(id);
					   if(fmenu1){
						   fmenu1.style.display = "none";
					   }
				   }else{
			           fmenu.style.left = oEvent.pageX - filemanage.offsetLeft + "px";
			           fmenu.style.top = oEvent.pageY - filemanage.offsetTop + filemanage.scrollTop + "px";
			           fmenu.style.display = "block";
					   fmenu.onmouseover = function() {
					   fmenu.style.cursor="pointer";
						};
				   }
			   }
		           return false;
		     }); 
		   $('.container').on('click' , function(){
                hideFmenu();
			});
    } 
       function previewFile(){
    	   var file_list = document.getElementById("file_list");
    	   file_list.innerHTML = "";
    	   var node = zTree.getSelectedNodes();
    	   var path = node[0].path.replace(/\\/g, '/');
    	   if(path.indexOf('mp4') >= 0){
    		   file_list.innerHTML = '<video controls src ="'+path+'" style="width:450px;height:450px;margin-left:50px"></video>';
    	   }else if(path.indexOf('jpg') >= 0){
    		   file_list.innerHTML = '<img src ="'+path+'" style="width:450px;height:450px;margin-left:50px"></img>';
    	   }else{
    		   alert("该文件不支持预览");
    	   }
       }
   	//页面加载，初始化树，给树绑定右键事件
	$(document).ready(function(){
		$.fn.zTree.init($("#tree"), setting);
		zTree = $.fn.zTree.getZTreeObj("tree");
		var allNode = zTree.transformToArray(zTree.getNodes());
		rMenu = $("#rMenu");
		$('#tree').bind("contextmenu",
			    function(){
			        return false;
			    }
		);
	});
   	  document.onmousedown=function(event){
   		   var oEvent = event;
		   var allfile = document.getElementById("allfile");
		   var offsetRight = allfile.offsetLeft+allfile.offsetWidth;
		   var offsetBottom = allfile.offsetTop+allfile.offsetHeight;
		   if(oEvent.pageX<allfile.offsetLeft||oEvent.pageX>offsetRight||oEvent.pageY>offsetBottom||oEvent.pageY<allfile.offsetTop){
			   hideFmenu();
               hideMenu();
		   }
   	   }
 </script>
</body>
</html>