<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<script src="${pageContext.request.contextPath}/assets/dangyuchao/jquery.min.js" type="text/javascript"></script>

		<script
			src="${pageContext.request.contextPath}/assets/dangyuchao/Photomark.js"
			type="text/javascript"></script>
		<script
			src="${pageContext.request.contextPath}/assets/dangyuchao/markUtils.js"
			type="text/javascript"></script>
			<script
			src="${pageContext.request.contextPath}/assets/dangyuchao/markAction.js"
			type="text/javascript"></script>
		<script
			src="${pageContext.request.contextPath}/assets/dangyuchao/bootstrap.min.js"
			type="text/javascript"></script>
		<style type="text/css">
			body {height:1200px;}
			.container {width:100%;}
			.photo {float:left;}
		</style>
	</head>
	
	<body>
		<input id="taskId" name="taskId" type="hidden" value="${taskId}" />
		<input id="pic" name="pic" type="hidden"  value="${file}" />
		<input id="original_file_id" type="hidden" name="original_file_id" value="${original_file_id}"/>
		<div class="container">
			<div class="photo">
				<div id="showpanel">
					<img src="${file}" name="bigimg"  id="Photo_0_" />
				</div>
				<div class="row">
					<div style="border:2px black solid">
						<div id="masker" class="col-md-2">
							<p>标注类别 :</p>
							<img id="shot_man" name="man" class="masker" 
							alt="行人" src="assets/dangyuchao/maskerIcon/man.png">
							<img id="shot_car" name="car" class="masker" 
							alt="车辆" src="assets/dangyuchao/maskerIcon/car.png">
							<img id="shot_sign" name="sign" class="masker" 
							alt="交通标志" src="assets/dangyuchao/maskerIcon/sign.png">
							<img id="shot_light" name="light" class="masker" 
							alt="交通灯" src="assets/dangyuchao/maskerIcon/light.png">
							<img id="shot_line" name="line" class="masker" 
							alt="车道线" src="assets/dangyuchao/maskerIcon/line.png">
							
					<!-- 		<input id="shot_man" class="masker btn btn-primary" type="button"
									name="man" value="行人"></input>
							<input id="shot_car" class="masker btn btn-primary" type="button"
									name="car" value="车辆"></input>
							<input id="shot_sign" class="masker btn btn-primary" type="button"
									name="sign" value="交通标志"></input>
							<input id="shot_light" class="masker btn btn-primary" type="button"
									name="light" value="交通灯"></input>
							<input id="shot_line" class="masker btn btn-primary" type="button"
									name="line" value="车道线"></input> -->
						</div>
					</div>
					<div style="border:2px black solid">
						<div id="mark" class="col-md-2">
							<p>标注形式   :</p>
							<img id="rect_mark" name="rect_mark" class="mark REC" title="矩形" alt="矩形" src="assets/dangyuchao/maskerIcon/rect_mark.png"/>
						<!--<input id="mark_rec" class="mark REC btn btn-primary" type="button"
									name="rec" value="矩形"></input>
 							<input id="mark_circle" class="mark CIRCLE btn btn-primary" type="button"
									name="circle" value="圆"></input><br>
							<input id="mark_line" class="mark LINE btn btn-primary" type="button"
									name="line" value="直线"></input><br>
							<input id="mark_multiline" class="mark MULTILINE btn btn-primary" type="button"
									name="multiline" value="多边形"></input><span>点击若干个点形成多边形</span><br>  -->
						</div>
					</div>
					<div style="border:2px black solid">
						<div id="mark" class="col-md-2">
							<p>操作</p>
							<img id="move" name="move" class="slide" title="移动" alt="移动" src="assets/dangyuchao/maskerIcon/move.jpg"/>
							<img id="cleanMasker" name="cleanMasker" title="清除当前类别选框" alt="清除当前类别选框" src="assets/dangyuchao/maskerIcon/eraser_32.png"/>
						
							<!-- <input id="dragTomove" class="slide btn btn-primary" type="button"
									name="slide" value="操作"></input>
							<input id="drawRec" class="btn btn-primary" type="button"
									name="drawREC" value="画矩形"></input> -->
						</div>
					</div>	
					<div style="border:2px black solid">
						<div id="mark" class="col-md-2">
							<p></p>			
							<input id="submitbtn" class="btn btn-primary" type="button" value="提交"></input>
						</div>
					</div>
				</div>
				
			</div>
		</div>
		
		<script>
		var subdata;
	 	var getdata='${video_info_json}';
 		if(getdata=="[]"){
			console.log("no input");
			subdata={
					taskid:null,//taskid
					videolist:[],
					photolist:[]
			}
		}else{
			console.log("has input");
			subdata=${video_info_json}; 
		}
		var act= new action();
		var markPen;
 		

		var sub_button=document.getElementById("submitbtn");
		var input_img= document.getElementById("Photo_0_");
		var realWidth;
		var realHeight;
		
		var timer=self.setInterval(precessPic,50);
		function precessPic(){
			 if(input_img.complete){
				console.log("清除计时");
				clearInterval(timer); 
				 
				realWidth=input_img.width;
				realHeight=input_img.height;
				input_img.width=document.body.clientWidth*0.9;//如果直接设置width用数字格式,不加px
				input_img.height=input_img.width/realWidth*realHeight;
				var showpanel= document.getElementById("showpanel");
				showpanel.style.width=input_img.width+"px";
				showpanel.style.height=input_img.height+"px";
		
				markPen= new photoInput("Photo_0_",subdata,"masker","mark","slide","cleanMasker",(input_img.width/realWidth));
				markPen.processPhoto(0);
			}
			
		}

		sub_button.onclick=function(){
			subdata.taskid=$("#taskId").val();
			subdata.photolist[0].original_file_id=$("#original_file_id").val();
			subdata.photolist[0].mark="yes";
			markPen.PhotoDC.prepareCoords(markPen.viewRatio);
			console.log(markPen.PhotoDC.subdata);
 			$.ajax({
					url : "video.do",
					type : "post",
					async : false,
					contentType : "application/x-www-form-urlencoded;charset=utf-8",
					data : {
						"subdata":JSON.stringify(markPen.PhotoDC.subdata),
					},
					dataType : "json",
					success : function(result) {
						window.location.href = result.backurl;
					}
			});
		}
				
		
		</script>
	</body>
</html>