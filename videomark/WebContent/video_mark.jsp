<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>视频标注</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/dangyuchao/bootstrap.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/video_mark_jsp/mark.css" >
	<script src="${pageContext.request.contextPath}/assets/dangyuchao/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/dangyuchao/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/dangyuchao/Photomark.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/assets/dangyuchao/markUtils.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/assets/dangyuchao/markAction.js" type="text/javascript"></script>
	<style type="text/css">
			html, body, div{
				margin:auto;
			}
			.hand {cursor:pointer;}
			.container {width:100%;margin-right: auto;margin-left: auto;}
		</style>

</head>
<body style="margin: 0;overflow:inherit;">
	<div class="container">
		<div class="row">
			<div class="col-md-6">
				<div class="row text-center">
					<h3>视频标注</h3>
				</div>
				<div class="row">
					<div id="videoParent">
							<video id="playvideo_0_" src="${file}" playbackRate="1" loop="true"
								 controls="controls">
							</video>
					</div>
				</div>
				<div class="row">
						<div class="buttongroup">
							<div class="row">
								<div class="col-md-5">
									<div id="slowspeed" class="col-md-3 hand">
 										<img  alt="播放减速" src="assets/dangyuchao/icon/back.png">
										<!-- <button class="btn btn-primary">减速</button>	 -->
									</div>
									<div id="normspeed" class="col-md-3 hand">
										<img   onclick="changeSpeed(1);"alt="还原速度" src="assets/dangyuchao/icon/reset.png">
									</div>
									<div class="dropdown col-md-3 hand">
									   	<button class="btn btn-default dropdown-toggle" type="button" id="menu1" data-toggle="dropdown">播放速度
									   		<span class="caret"></span>
									  	</button>
									   	<ul class="dropdown-menu" role="menu" aria-labelledby="menu1">
										     <li role="presentation"><a role="menuitem" onclick="changeSpeed(0.1);">0.1</a></li>
										     <li role="presentation"><a role="menuitem" onclick="changeSpeed(0.5);">0.5</a></li>
										     <li role="presentation"><a role="menuitem" onclick="changeSpeed(1);">1</a></li>
										     <li role="presentation"><a role="menuitem" onclick="changeSpeed(1.5);">1.5</a></li>
										     <li role="presentation"><a role="menuitem" onclick="changeSpeed(2);">2</a></li>
										     <li role="presentation"><a role="menuitem" onclick="changeSpeed(4);">4</a></li>
										     <li role="presentation"><a role="menuitem" onclick="changeSpeed(8);">8</a></li>
										     <li role="presentation"><a role="menuitem" onclick="changeSpeed(16);">16</a></li>
										</ul>
									</div>
									<div id="fastspeed" class="col-md-2 col-md-offset-1 hand">
										<img  alt="播放加速" src="assets/dangyuchao/icon/fast.png">
									</div>								
								</div>
								<div class="col-md-2">
									<div id="playpause" class="col-md-12 hand">
										<img id="change_play" alt="播放" src="assets/dangyuchao/icon/pause.png">			
									</div>
																
								</div>
								<div id="shotbutton" class="col-md-2 hand">
									<img  alt="截图" src="assets/dangyuchao/icon/shot.png">						
								</div>
								<div class="col-md-3">	
									<p id="shothint"><span>你目前共截取了</span><span id="pic_totalcount">0</span><span>张图</span><p /><br/>
								</div>
							</div>
						</div>
				</div>
			</div>

			<div class="col-md-5  col-md-offset-1">
				<form id="subform" class="form-inline">		
				<input type="hidden" name="mark_type" value="video"></input> 
				<input id="original_file_id" type="hidden" name="original_file_id" value="${original_file_id}"></input>
						<input type="hidden" id="sub_img" name="sub_img" value=""></input><input
							type="hidden" name="file_name" value="${file}"></input> <input
							type="hidden" id="task_id" name="task_id" value="${taskId}"></input>
						<input type="hidden" name="device" value="${device}"></input> <input
							type="hidden" name="driver" value="${driver}"></input> <input
							type="hidden" name="place" value="${place}"></input> <input
							type="hidden" name="date" value="${date}"></input> <input
							type="hidden" name="time" value="${time}"></input>
				<div class="row">
					<div class="form-group card">
						<div class="col-md-4 text-center">
							<label>天气 :</label>
						</div>
						<div class="col-md-1 text-center">
							<input type="radio" name="weather" value="sunshine"></input> 晴
						</div>
						<div class="col-md-1 text-center">
						<input type="radio" name="weather"
								value="overcast"></input> 阴
							
						</div>
						<div class="col-md-1 text-center">

							<input type="radio" name="weather" value="cloudy"></input>
								多云
							

						</div>
						<div class="col-md-1 text-center">

							 <input type="radio" name="weather"
								value="light_rain"></input> 小雨
							

						</div>
						<div class="col-md-1 text-center">

							 <input type="radio" name="weather"
								value="heavy_rain"></input> 大雨
					

						</div>
						<div class="col-md-1 text-center">

							 <input type="radio" name="weather" value="snow"></input>
								雪
							

						</div>
						<div class="col-md-1 text-center">

							<input type="radio" name="weather" value="fog"></input>
								雾
							

						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group card">
						<div class="col-md-4 text-center text-nowrap">

							<label>道路类型:</label>

						</div>
						<div class="col-md-2  text-nowrap">

						 <input type="radio" name="road_type"
								value="urban_road"></input> 城市道路
							

						</div>
						<div class="col-md-2 col-md-offset-1 text-nowrap">

							 <input type="radio" name="road_type"
								value="expressway"></input> 高速公路
							

						</div>
						<div class="col-md-2 col-md-offset-1 text-nowrap">

							<input type="radio" name="road_type"
								value="rural_road"></input> 乡村公路
							

						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group card">
						<div class="col-md-4 text-center text-nowrap">
							<label>路况:</label>
						</div>
						<div class="col-md-8">
							<div class="row">
								<div class="col-md-4  text-nowrap">
										 <input type="checkbox" name="road_condition"
											value="road_condition_normal"></input> 正常
																
								</div>
								<div class="col-md-4  text-nowrap">
									 <input type="checkbox" name="road_condition"
										value="tunnel"></input> 隧道
									
								</div>
								<div class="col-md-4  text-nowrap">
									 <input type="checkbox" name="road_condition"
										value="crossing"></input> 路口
									
								</div>
							</div>
							<div class="row">
								<div class="col-md-4  text-nowrap">
									<input type="checkbox" name="road_condition"
										value="bend"></input> 弯道
									
								</div>
								<div class="col-md-4  text-nowrap">
									<input type="checkbox" name="road_condition"
										value="rampway"></input> 坡道
									
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group card">
						<div class="col-md-4 text-center text-nowrap">
							<label>光线:</label>
						</div>
						<div class="col-md-8">
							<div class="row">
								<div class="col-md-4  text-nowrap">
										 <input type="checkbox" name="light"
											value="road_condition_normal"></input> 正常
																
								</div>
								<div class="col-md-4  text-nowrap">
									 <input type="checkbox" name="light"
										value="tunnel"></input> 顺光
									
								</div>
								<div class="col-md-4  text-nowrap">
									 <input type="checkbox" name="light"
										value="crossing"></input> 侧光
									
								</div>
							</div>
							<div class="row">
								<div class="col-md-4  text-nowrap">
									<input type="checkbox" name="light"
										value="bend"></input> 逆光
									
								</div>
								<div class="col-md-4  text-nowrap">
									 <input type="checkbox" name="light"
										value="rampway"></input> 红外补光
									
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group card">
						<div class="col-md-4 text-center text-nowrap">
							<label>视频内容:</label>
						</div>
						<div class="col-md-8">
							<div class="row">
								<div class="col-md-4  text-nowrap">
									 <input type="checkbox" name="video_details"
											value="road_condition_normal"></input> FCWS
																	
								</div>
								<div class="col-md-4  text-nowrap">
									 <input type="checkbox" name="video_details"
										value="tunnel"></input> PCWS
									
								</div>
								<div class="col-md-4  text-nowrap">
									 <input type="checkbox" name="video_details"
										value="crossing"></input> LDWS
									
								</div>
							</div>
							<div class="row">
								<div class="col-md-4  text-nowrap">
									 <input type="checkbox" name="video_details"
										value="bend"></input> TSR
								
								</div>
								<div class="col-md-4  text-nowrap">
									<input type="checkbox" name="video_details"
										value="rampway"></input> LAMP
									
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group card">
						<div class="col-md-4 text-center text-nowrap">
							<label>标注信息:</label>
						</div>
						<div class="col-md-8">
							<div class="row">
								<div class="col-md-4  text-nowrap">
									<input type="checkbox" name="target"
											value="road_condition_normal"></input>人
																	
								</div>
								<div class="col-md-4  text-nowrap">
									 <input type="checkbox" name="target"
										value="tunnel"></input> 车
									
								</div>
								<div class="col-md-4  text-nowrap">
									<input type="checkbox" name="target"
										value="crossing"></input> 骑行
									
								</div>
							</div>
							<div class="row">
								<div class="col-md-4  text-nowrap">
										<input type="checkbox" name="target"
										value="bend"></input> 交通标志
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group card">
						<div class="col-md-4 text-center text-nowrap">
							<label> 视频备注:</label>
						</div>
						<div class="col-md-8">
							<textarea rows="3" name="video_note"></textarea>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group card">
						<div class="col-md-4 text-center text-nowrap">
						<label> 特殊场景:</label>
						</div>
						<div class="col-md-8">
							<textarea rows="3" name="special_case"></textarea>
							
						</div>
					</div>
				</div>
				</form>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="row text-center">
					<h3>图片标注</h3>
				</div>
				<div id="imageshow" ></div>			
			</div>
			<div class="col-md-1">
				<div class="row" style="margin-top:200px;">
					<div class="col-md-6 col-md-offset-6">
							<img id="deleteimg" alt="删除截图" src="assets/dangyuchao/icon/trash.png">
						<!-- <button class="btn btn-primary">减速</button>	 -->
					</div>
				</div>
				<div class="row" style="margin-top:20px;">
					<div class="col-md-6 col-md-offset-6">
							<img id="cleanMasker" alt="清空框选" src="assets/dangyuchao/icon/eraser.png">
						<!-- <button class="btn btn-primary">减速</button>	 -->
					</div>
				</div>							
			</div>
			<div class="col-md-5 text-center" style="margin-left:0px">	
				<div id="previewpanel"></div>
			</div>
		</div>
		<div class="row">
					<div>
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
						</div>
					</div>
					<div>
						<div id="mark" class="col-md-2">
							<p>标注形式</p>
							<img id="rect_mark" name="rect_mark" class="mark REC" title="矩形" alt="矩形" src="assets/dangyuchao/maskerIcon/rect_mark.png"/>
							
							<!-- <input id="mark_rec" class="mark REC btn btn-primary" type="button"
									name="rec" value="矩形"></input>
							 <input id="mark_circle" class="mark CIRCLE btn btn-primary" type="button"
									name="circle" value="圆"></input><br>
							<input id="mark_line" class="mark LINE btn btn-primary" type="button"
									name="line" value="直线"></input><br>
							<input id="mark_multiline" class="mark MULTILINE btn btn-primary" type="button"
									name="multiline" value="多边形"></input><span>点击若干个点形成多边形</span><br>  -->
						</div>
					</div>
					<div>
						<div id="mark_action" class="col-md-2">
							<p>操作</p>
							<img id="move" name="move" class="slide" title="移动" alt="移动" src="assets/dangyuchao/maskerIcon/move.jpg"/>		
						<!-- 	<input id="dragTomove" class="slide btn btn-primary" type="button"
									name="slide" value="操作"></input> -->
						</div>
					</div>	
		</div>
		<div class="row">
				
		</div>	
		<div class="row">
			<div class="text-center">
				<button id="submitdata" class="btn btn-primary">提交</button>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var subdata;
		var getdata='${video_info_json}';
		console.log(getdata);
		var videoSrc="${file}";
 		if(getdata=="[]"){
			console.log("no input");
			subdata={
					taskid:null,//taskid
					videolist:[],
					photolist:[]
			}
		}else{
			subdata=${video_info_json}; 
 			subdata.videolist[0].url=videoSrc;	
		}
		var videoElem = document.getElementById("playvideo_0_");
		var playrate=videoElem.playbackRate;
		var previewpanel = document.getElementById("previewpanel");
		var previewboard=document.createElement("div");
		previewboard.style.overflowY="scroll";
		

		var previescalewhint = document.createElement("p");
		previescalewhint.innerHTML = "<br/>预览缩略图:<br/>";
		previewpanel.appendChild(previescalewhint);
		previewpanel.appendChild(previewboard);
		
		var act= new action();
		
		var process=new VideoInput("subform",//页面天气，道路表单
				subdata,//输入的数据
				"shotbutton",//截图按键ID
				"imageshow",//展示截图区域的ID
				"masker",//用来识别蒙版的类名
				"mark",//用来识别框选符号的类名
				"slide",//用于拖动的类名
				"playvideo_0_",//视频的ID
				"pic_totalcount",//用于截图计数的页面中countID
				"deleteimg",//删除截图的按键ID
				"cleanMasker",//清除蒙版的按键ID
				previewboard,//创建的缩略图容器
				400);//缩略图容器高度
		process.processVideo(0); 

		$("#slowspeed")[0].onclick = function() {
			var playrate=videoElem.playbackRate;
			if(playrate>0.1){
				console.log(videoElem.playbackRate);
				videoElem.playbackRate=Math.round((playrate - 0.3) * 10) / 10;
				console.log(videoElem.playbackRate);
			}
		};
		$("#fastspeed")[0].onclick = function() {
			var playrate=videoElem.playbackRate;
			if(playrate<64){
				videoElem.playbackRate=Math.round((playrate + 0.3) * 10) / 10;
			}
		};
		$("#playpause")[0].onclick = function() {
			if(!videoElem.paused){
				$("#change_play")[0].src="assets/dangyuchao/icon/play.png";
				videoElem.pause();
			}else{
				$("#change_play")[0].src="assets/dangyuchao/icon/pause.png";
				videoElem.play();
			}
			
		};
		function changeSpeed(speed){
			videoElem.playbackRate=speed;
		}

		function serializeObject() {
			var formParam = $("#subform").serializeArray();//序列化表格内容为字符串 
			var paramlist = {};

			$.each(formParam, function() {
				if (paramlist[this.name]) {
					if (!paramlist[this.name].push) {
						paramlist[this.name] = [ paramlist[this.name] ];
					}
					paramlist[this.name].push(this.value || '');
				} else {
					paramlist[this.name] = this.value || '';
				}
			});
			return paramlist;
		}

		var sub_button=document.getElementById("submitdata");	
		sub_button.onclick=function(){			
				subdata.taskid=$("#task_id").val();
				process.shotVideo.updateFormData(serializeObject());
				process.shotVideo.updateOriFileID($("#original_file_id").val());
				process.shotVideo.updateCoords(process.shotVideo.viewRatio);

				console.log(process.videoDC.subdata);
  				$.ajax({
						url : "video.do",
						type : "post",
						async : false,
						contentType : "application/x-www-form-urlencoded;charset=utf-8",
						data : {
							"subdata":JSON.stringify(process.videoDC.subdata),
						},
						dataType : "json",
						success : function(result) {
							for(var i = 0 ; i <10000;i++){};
							window.location.href = '/videomark/' + result.backurl;
						}
				});
		}

	</script>
</body>

</html>