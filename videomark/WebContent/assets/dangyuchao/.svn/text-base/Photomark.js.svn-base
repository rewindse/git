//给一个视频的id和截图占屏幕宽度的比例以及截图按钮的ID，返回截图列表;
ShotVideo = function(videoID, shotRatio, shotButtonID, photoParentID,
		maskerClassName,markClassName,moveBtnClassName,pictures,dataDC) {
	console.log("hello,正在加载视频截图模板");
	this.videoID = videoID;// 目前的工作视频id
	this.shotRatio = shotRatio;// 占面板的比例
	this.shotButtonID = shotButtonID;// 截图按钮的id
	this.photoParentID = photoParentID;// 添加到母版容器
	this.maskerClassName = maskerClassName;
	this.markClassName = markClassName;
	this.moveBtnClassName = moveBtnClassName;// 移动的按键类名
	this.picturesInfo=pictures;//如果有图片数据
	this.dataDC;

	this.photoParent;
	this.video;
	this.viewRatio;// 视频页面宽度与实际宽度之比，最后要用坐标除它
	this.wid_height_ratio;// 视频的实际高度/实际宽度
	this.photoList;// 截图列表
	this.photoCount;// 目前截图的计数---从零开始
	this.photoAbsCount;//截图绝对计数---从零开始，不减，用作唯一id
	this.currentPhoto;// 当前截图
	this.currentPhotoCount;// 当前截图
	this.currentMarkPhoto;// 当前截图的蒙版工具
	this.canvas;

	this.temp_canvas;
	this.temp_context;
	this.temp_shot;// 存储截图的暂时变量

	this.previewArea;
	this.previewRate;
	this.small_temp_canvas;// 缩略图的临时画布
	this.small_temp_context;
	this.currentPreview;// 当前的缩略图
	this.cover;// 缩略图的蒙版
	
	this.countHolder//页面用来计数的计数区域
	this.init(dataDC);
	this.loadVideo(pictures);// 视频需要等待加载以得到视频实际的宽度高度,如果有图片，加载第一张

	this.initButton();// 初始化截图键

	return this;
};
ShotVideo.prototype = {
	initDataCenter : function(dataDC) {
		this.dataDC = dataDC;
	},
	addVideo:function(){
		this.dataDC.insertVideo(this.video.id, null);
	},
	updateFormData : function(formdata) {// 插入视频注释信息
		if (this.dataDC != null) {
			this.dataDC.updateVideo(this.video.id, formdata);
		}
	},
	updateOriFileID : function(OriFileID) {// 插入视频注释信息
		if (this.dataDC != null) {
			this.dataDC.prepareOriginalFileID(OriFileID);
		}
	},
	updateCoords : function(viewRatio) {// 插入视频注释信息
		if (this.dataDC != null) {
			this.dataDC.prepareCoords(viewRatio);
		}
	},
	initCanvas : function() {
		this.canvas = document.createElement("canvas");
		this.canvas.width = this.video.width;
		this.canvas.height = this.video.height;

		this.canvas.style.position = "absolute";
		this.canvas.style.zIndex = 2;
		this.photoParent.appendChild(this.canvas);
	},
	init : function(dataDC) {
		this.video = this.initVideo();
		if(dataDC!="undefined"){
			this.initDataCenter(dataDC);
		}else{
			this.dataDC = null;
		}
		this.photoParent = $("#" + this.photoParentID)[0];

		this.photoList = new Array();
		this.photoCount = 0;
		this.photoAbsCount = 0;

		this.temp_canvas = document.createElement("canvas");
		this.temp_context = this.temp_canvas.getContext("2d");

		this.small_temp_canvas = document.createElement("canvas");// 缩略图的临时画布
		this.small_temp_context = this.small_temp_canvas.getContext("2d");
		
		this.countHolder=null;
	},

	initVideo : function() {
		if (typeof ($("#" + this.videoID)[0]) != "undefined") {
			return $("#" + this.videoID)[0];
		} else {
			console.log("************视频没有找到************");
		}
	},
	initCover : function() {
		this.cover = document.createElement("canvas");
		this.cover.width = this.video.width * this.previewRate;// 设置缩略图蒙版
		this.cover.height = this.video.height * this.previewRate;
		this.cover.style.backgroundColor = "red";
		this.cover.style.opacity = 0.3;
		this.cover.style.marginLeft =-(this.video.width * this.previewRate)+ "px";
		this.cover.style.marginTop = 0+ "px";
		this.cover.style.position = "absolute";
		this.cover.style.zIndex = 1;
	},
	addCover : function(preview) {
		preview.appendChild(this.cover);
	},
	removeCover : function(preview) {
		preview.removeChild(this.cover);
	},
	loadVideo : function(pictures) {
		var timer;

		wait_load(this, document.body.scrollWidth,pictures);
		function initsize(size, bodyWidth) {
			var ratio = size.video.videoHeight / size.video.videoWidth;
			size.video.width = bodyWidth * size.shotRatio;// 设置视频画板的宽高
			size.video.height = size.video.width * ratio;

			size.viewRatio = size.video.width / size.video.videoWidth;

			size.temp_canvas.width = size.video.width;// 设置截图画布的宽高
			size.temp_canvas.height = size.video.height;

			size.small_temp_canvas.width = size.video.width * size.previewRate;// 缩略图的临时画布
			size.small_temp_canvas.height = size.video.height
					* size.previewRate;

			size.photoParent.style.width = size.video.width + "px";
			size.photoParent.style.height = size.video.height + "px";

			size.initCanvas();// 必须在上面的视频加载后才能初始化这两项
			size.initCover();
			size.wid_height_ratio = ratio;
		}
		function wait_load(parent, bodyWidth,pictures) {
			if (parent.video.videoWidth == 0) {
				console.log("视频要等待加载");
				timer = setTimeout(function() {
					wait_load(parent, bodyWidth,pictures);
				}, 100);

			} else {
				clearTimeout(timer);
				initsize(parent, bodyWidth);
				if(pictures!=null){//
					parent.preparePictures(pictures);
				}
			}
		}
	},
	preparePictures:function(photos){//加载截图图片组，页面显示第一张，为了减少干扰所调用的方法，最后重复设置属性
		if(photos.length==0){
			return null;
		}
		
		function resetPhotosInfo(i,photoInfo,viewRatio){
			if(photoInfo==null){
				return null;
			}
			var str=photoInfo.photoID.split("_");
			var temp;
			photoInfo.photoID=str[0]+"_"+str[1]+"_"+str[2]+"_"+i+"_";
			photoInfo.url=photoInfo.url.substring(photoInfo.url.indexOf("store",0));
			for(var i=0;i<photoInfo.masker.length;i++){
				temp=photoInfo.masker[i].maskerID.split("_");
				photoInfo.masker[i].maskerID=photoInfo.photoID+temp[4]+"_"+temp[5]+"_";
				for(var j=0;j<photoInfo.masker[i].mark.length;j++){
					temp=photoInfo.masker[i].mark[j].markID.split("_");
					photoInfo.masker[i].mark[j].markID=photoInfo.masker[i].maskerID+temp[6]+"_"+temp[7]+"_";
					
					temp=photoInfo.masker[i].mark[j].coords;
					temp.xmin=(parseInt(temp.xmin)*viewRatio).toFixed(0);
					temp.ymin=(parseInt(temp.ymin)*viewRatio).toFixed(0);
					temp.xmax=(parseInt(temp.xmax)*viewRatio).toFixed(0);
					temp.ymax=(parseInt(temp.ymax)*viewRatio).toFixed(0);
				}
			}
		}
		for(var i=0;i<photos.length;i++){
			console.log("预加载图片数组");
			resetPhotosInfo(i,photos[i],this.viewRatio);
			this.temp_shot=this.loadPicture(photos[i]);
			if(i==0){//页面显示第一张图片
				this.currentPhoto = this.temp_shot;
				this.setCurrentPhotoIndex();
				this.appendToparent(this.currentPhoto);// 在页面出现
				this.drawPhoto(this.currentPhoto.id,photos[i]);// 添加蒙版
			}
			this.addPreview(this.photoCount,photos[i]);// 画缩略图
			this.photoCount++;
			this.photoAbsCount++;
			if(this.countHolder!=null){
				this.showShotCount();
			}
		}
		this.removeCover(this.photoList[this.photoList.length-1].preview);//去掉最后一个截图的蒙版
		this.addCover(this.photoList[0].preview)//把蒙版添加到第一个截图上
		this.currentPreview = this.photoList[0].preview;//设置第一个截图为当前截图
	},
	loadPicture:function(pic){
		this.temp_shot = this.createPicture(pic.photoID,pic.url);
		console.log("************加载图片****** " + this.temp_shot.id);

		this.packToList(this.temp_shot);// 将结果打包进数组
		
		return this.temp_shot;
	},
	shotPicture : function(timeStamp) {
		if (this.currentPhoto != undefined) {
			this.currentMarkPhoto.unBindButton();
			this.currentMarkPhoto.unBindCanvas();
			this.photoParent.removeChild(this.currentPhoto);
			if (this.currentMarkPhoto.currentMasker != undefined) {
				this.photoParent.removeChild(this.currentMarkPhoto.currentMasker);// 移除当前蒙版
			}
		}

		this.temp_shot = this.createPicture();
		console.log("************截了一张图****** " + this.temp_shot.id);
		this.currentPhoto = this.temp_shot;

		if (this.dataDC != null) {
			this.dataDC.insertPhoto(this.currentPhoto.id, timeStamp);// 发送数据中心
		}
		this.packToList(this.currentPhoto);// 将结果打包进数组

		this.setCurrentPhotoIndex();
		this.appendToparent(this.currentPhoto);// 在页面出现

		this.drawPhoto(this.currentPhoto.id);// 添加蒙版
		this.photoCount++;
	},
	initButton : function() {
		var self = this;
		var timeStamp;
		if (typeof ($("#" + this.shotButtonID)[0]) != "undefined") {
			$("#" + this.shotButtonID)[0].onclick = function() {// 点击进行截图
				timeStamp = self.video.currentTime;
				self.shotPicture(timeStamp);
				self.addPreview(self.currentPhotoCount);// 画缩略图
				self.photoAbsCount++;
				if(self.countHolder!=null){
					self.showShotCount();
				}
			};
		} else {
			console.log("************截图按钮没找到************");
		}

	},
	setCurrentPhotoIndex : function() {//当前图片在list中的位置
		for (var i = 0; i < this.photoList.length; i++) {
			if (this.photoList[i].photoID == this.currentPhoto.id) {
				this.currentPhotoCount = i;
			}
		}
	},
	getCurrentPhotoIndex : function(previewID) {// 
		for (var i = 0; i < this.photoList.length; i++) {
			if (this.photoList[i].preview.id == previewID) {
				return i;
			}
		}
	},
	drawPhoto : function(photoID,photoInfo) {//对于单个图片，只有id不画蒙版，如果有info要画蒙版
		var markphoto = new markPhoto(photoID, "masker", "mark",
				this.moveBtnClassName, this.canvas);
		if(photoInfo!=null){
			var maskers=photoInfo.masker;
			
			if(maskers.length>0){
				var bnt;
				var backColor;
				for(var i=0;i<maskers.length;i++){//将所有的蒙版先全部加载一遍
					bnt=$("."+this.maskerClassName+"[name='"+maskers[i].name+"']")[0];
					backColor = markphoto.getColor(maskers[i].name);
					bnt.style.backgroundColor = backColor;
					markphoto.changeMasker(bnt, backColor,maskers[i],this.dataDC);
				}//进行完上面的循环，页面进行到最后一个蒙版，下面是切换回第一个蒙版
				bnt=$("."+this.maskerClassName+"[name='"+maskers[0].name+"']")[0];
				markphoto.changeMasker(bnt, backColor,maskers[0],this.dataDC);
			}
		}
		if (this.dataDC != null) {
			markphoto.initDataCenter(this.dataDC);
		}
		this.photoList[this.currentPhotoCount].markPhoto = markphoto;
		this.currentMarkPhoto = this.photoList[this.currentPhotoCount].markPhoto;
	},

	appendToparent : function(photo) {
		this.photoParent.appendChild(photo);
	},
	packToList : function(photo) {
		this.photoList[this.photoList.length] = {
			photoID : photo.id,
			photo : photo,
			markPhoto : null,
			preview : null
		}
	},
	createPicture : function(photoID,url) {//给一个序数，返回一个截图，如果有参数，根据url生成图片
		var ipic = document.createElement("img");
		if(photoID==null){
			ipic.id = this.videoID + "photo_" + this.photoAbsCount + "_";
		}else{
			ipic.id = photoID;
		}
		ipic.style.overflow = "hidden";
		ipic.width = this.video.width;
		ipic.height = this.video.height;
		ipic.style.zIndex = 0;
		if(url==null){
			this.temp_context.drawImage(this.video, 0, 0, this.video.width,
					this.video.height);// 画小型预览图
			ipic.src = this.temp_canvas.toDataURL();
		}else{
			ipic.src =url;
		}
		return ipic;
	},
	addDelete : function(deletePhotoID, cleanMaskerID) {
		var self = this;
		if (deletePhotoID != null) {
			$("#" + deletePhotoID).click(function() {
				self.deleteShotPhoto();
				if(self.countHolder!=null){
					self.showShotCount();
				}
			});
		}
		if (cleanMaskerID != null) {
			$("#" + cleanMaskerID).click(
					function() {
						self.photoList[self.currentPhotoCount].markPhoto
								.deleteCurrentMaskerMarks();
					});
		}
	},
	deleteShotPhoto : function() {
		if (this.currentPhoto != null) {

			if (this.currentPhoto != null) {
				this.dataDC.deletePhoto(this.currentPhoto.id);
			}
			if (this.currentPreview != null) {
				this.previewArea.removeChild(this.currentPreview);
			}
			if (this.currentPhotoCount < this.photoList.length - 1) {// 当前不是最后一个
				this.changePhoto(this.currentPhotoCount + 1,this.picturesInfo[this.currentPhotoCount]);// 将要替换的图片的序数
				// 上面的change内部设定了一次currentCount，设置为了改变后的值
				this.currentPreview = this.photoList[this.currentPhotoCount].preview;
				this.addCover(this.currentPreview);
				this.photoList.splice(this.currentPhotoCount - 1, 1);
				this.currentPhotoCount = this.currentPhotoCount - 1;
				console.log("向后删除" + this.currentPhotoCount);
			} else if (this.currentPhotoCount == 0) {// 当前是唯一的一个
				this.currentMarkPhoto.unBindButton();
				this.currentMarkPhoto.unBindCanvas();
				if (this.currentMarkPhoto.currentMasker != undefined) {
					this.photoParent.removeChild(this.currentMarkPhoto.currentMasker);// 移除当前蒙版
				}
				this.photoParent.removeChild(this.currentPhoto);// 移除当前图片
				this.photoList.splice(this.currentPhotoCount, 1);
				this.currentPreview = null;
				this.currentPhoto = null;
			} else {// 当前是最后一个，而且前面有同志
				this.changePhoto(this.currentPhotoCount - 1,this.picturesInfo[this.currentPhotoCount - 1]);// 将要替换的图片的序数
				this.currentPreview = this.photoList[this.currentPhotoCount].preview;
				this.addCover(this.currentPreview);
				this.photoList.splice(this.currentPhotoCount + 1, 1);
				console.log("向前删除" + this.currentPhotoCount);
			}
			this.photoCount--;
				
		}
	},
	initCountHolder:function(hindID){
		if (typeof $("#" + hindID) != "undefined") {
			this.countHolder=$("#"+hindID)
		} else {
			console.log("************计数器没找到************");
		}
	},
	showShotCount:function(){
		this.countHolder.html(this.photoCount);
	},
	// 页面需要初始化一块截图区域，传入截图区域，截图比例，截图区域高度
	initPreview : function(previewboard, previewRate, previewHeight) {
		this.previewArea = previewboard;
		this.previewRate = previewRate;
		this.previewArea.style.height = previewHeight + "px";
	},
	addPreview : function(currentPhotoCount,one_pic) {// 添加缩略图主方法，在截图点击时触发，只需要初始化截图区域
		var preview;
		if(one_pic!=null){
			preview= this.createPreview(one_pic.url);
		}else{
			preview= this.createPreview();
		}
		this.previewArea.appendChild(preview);
		if (this.currentPreview != undefined) {
			this.removeCover(this.currentPreview);
		}
		this.currentPreview = preview;
		this.addCover(preview);
		this.photoList[currentPhotoCount].preview = preview;
		this.addPreviewListener(preview,one_pic);
	},
	createPreview : function(picUrl) {
		var iprePanel=document.createElement("div");
		iprePanel.id = this.videoID + "prePanel_" + this.photoAbsCount + "_";
		iprePanel.style.width=this.video.width * this.previewRate+"px";
		iprePanel.style.height=this.video.height * this.previewRate+"px";
		
		var ipreImg = document.createElement("img");
		ipreImg.id = this.videoID + "preview_" + this.photoAbsCount + "_";
		
		ipreImg.width=this.video.width * this.previewRate;
		ipreImg.height=this.video.height * this.previewRate;
		
		if(picUrl==null){
			this.small_temp_context.drawImage(this.video, 0, 0,
					this.small_temp_canvas.width, this.small_temp_canvas.height);// 画小型预览图
			ipreImg.src=this.small_temp_canvas.toDataURL() ;
		}else{
			ipreImg.src=picUrl ;
		}
		
		iprePanel.appendChild(ipreImg);
		iprePanel.style.marginLeft = 50 + "px";
		iprePanel.style.marginTop = 30 + "px";
		iprePanel.style.cssFloat = "left";
		iprePanel.style.position = "relative";
		iprePanel.style.opacity = "1";
		iprePanel.style.zIndex = 0;
		return iprePanel;
	},
	addPreviewListener : function(preview,picInfo) {// 给缩略图添加监听事件
		var self = this;
		preview.onclick = function() {
			console.log("点击缩略图，准备切换");
			if (preview.id != self.photoList[self.currentPhotoCount].preview.id) {
				self.removeCover(self.currentPreview);
				self.currentPreview = this;
				self.addCover(this);
				self.changePhoto(self.getCurrentPhotoIndex(this.id),picInfo);
			}
		};
	},
	changePhoto : function(count,picInfo) {// 将要替换的图片的序数,和缩略图无关,如果有photos参数，表明需要加载蒙版
		this.temp_change = this.photoList[count].photo;
		if (this.temp_change != null) {// 切换蒙版
			this.currentMarkPhoto.unBindButton();
			this.currentMarkPhoto.unBindCanvas();
			if (this.currentMarkPhoto.currentMasker != undefined) {
				this.photoParent.removeChild(this.currentMarkPhoto.currentMasker);// 移除当前蒙版
			}
			this.photoParent.removeChild(this.currentPhoto);// 移除当前图片

			this.photoParent.appendChild(this.temp_change);// 切换图片
			this.currentPhoto = this.temp_change;// 设置为当前操作图像
			this.setCurrentPhotoIndex();
			if(picInfo!=null&&this.photoList[count].markPhoto==null){//页面加载一次之后就不要在添加了
				console.log("添加一次蒙版工具---------------------哈哈哈");
				this.drawPhoto(this.currentPhoto.id,picInfo);// 添加蒙版
			}
			this.currentMarkPhoto = this.photoList[count].markPhoto;
			this.currentMarkPhoto.addfisrtMasker();
			this.currentMarkPhoto.bindButton();
			if(this.currentMarkPhoto.canvasBinded==false&&this.currentMarkPhoto.maskerList.length>0){
				this.currentMarkPhoto.bindCanvas();
			}
			this.currentMarkPhoto.setButtonColorByButton();
		}
	},
};

// 给一个图片id，在图片上形成若干可以区分的蒙版，并在蒙版上标注，切换蒙版的按键需要id
// 分别是图片id，蒙版的类名（masker），标注形式的类名，平移拖动的类名
markPhoto = function(photoID, maskerClassName, markClassName, moveBtnClassName,
		canvas) {
	this.photoID = photoID;
	this.maskerClassName = maskerClassName;
	this.markClassName = markClassName;
	this.moveBtnClassName = moveBtnClassName;
	this.dataDC;
	this.canvas = canvas;
	this.canvasBinded=false;
	this.ctx;
	this.utils;
	this.photo;
	this.photoParent;
	this.bigBro;
	this.photodata;// 关于图片的位置数据

	this.moveClassName;

	this.maskerList;// 属于本图片的蒙版列表
	this.maskerCount;
	this.currentMasker;// 当前展现的蒙版
	this.currentMaskerCount;
	this.currentPen;

	this.temp_change;
	this.drawData;
	this.penColor;
	this.ctx;

	this.init();

	this.bindButton();
};

markPhoto.prototype = {
	initDataCenter : function(dataDC) {
		this.dataDC = dataDC;
	},
	addphoto : function(id) {
		this.dataDC.insertPhoto(id);
	},
	drawMasker : function(photoInfo,viewRatio) {//对于单个图片，只有id不画蒙版，如果有info要画蒙版
		
		function resetPhotosInfo(photoInfo,viewRatio){
			if(photoInfo==null){
				return null;
			}
			for(var i=0;i<photoInfo.masker.length;i++){
				for(var j=0;j<photoInfo.masker[i].mark.length;j++){
					temp=photoInfo.masker[i].mark[j].coords;
					temp.xmin=(temp.xmin*viewRatio).toFixed(0);
					temp.ymin=(temp.ymin*viewRatio).toFixed(0);
					temp.xmax=(temp.xmax*viewRatio).toFixed(0);
					temp.ymax=(temp.ymax*viewRatio).toFixed(0);
				}
			}
		}
		if(photoInfo!=null){
			resetPhotosInfo(photoInfo,viewRatio);
			var maskers=photoInfo.masker;
			var maskerCount=photoInfo.masker.length
			if(maskers.length>0){
				var bnt;
				var backColor;
				var No_masker=true;
				//对于需要计数的元素来说，如果长度改变，最好在前面把count拿出来
				for(var i=0;i<maskerCount;i++){//将所有的蒙版先全部加载一遍
					bnt=$("."+this.maskerClassName+"[name='"+maskers[i].name+"']")[0];
					backColor = this.getColor(maskers[i].name);
					bnt.style.backgroundColor = backColor;
					this.changeMasker(bnt,backColor,maskers[i],this.dataDC,No_masker);
				}//进行完上面的循环，页面进行到最后一个蒙版，下面是切换回第一个蒙版
				bnt=$("."+this.maskerClassName+"[name='"+maskers[0].name+"']")[0];
				this.changeMasker(bnt,backColor,maskers[0],this.dataDC);
			}
		}
	},
	createPhotoCanvas : function() {// 专门为了标注图片的方法，为了构建图片要用的canvas
		var icanvas = document.createElement("canvas");
		icanvas.width = this.photo.width;
		icanvas.height = this.photo.height;

		icanvas.style.left = this.photodata.rela_left + "px";
		icanvas.style.top = this.photodata.rela_top + "px";
		icanvas.style.position = "absolute";
		icanvas.style.zIndex = 2;
		this.canvas = icanvas;
		this.photoParent.appendChild(this.canvas);
	},
	processInputDate : function() {
		this.drawData = {
			type : "REC",
			data : []
		};
		for(var i=0;i<50;i++){
			this.drawData.data[i]={
					xmin : 20+i,// left
					ymin : 20+i,// top
					xmax : 100+i,// right
					ymax : 120+i// bottom	
			}
		}
	},
	initCanvasPen : function() {
		this.penColor = "red";
		
	},
	drawOnCanvas : function() {
		if (this.drawData.type == "REC") {
			this.drawRecOnCanvas(this.drawData.data);
		}
	},
	drawRecOnCanvas : function(datalist) {
		var self=this;
		var timer;
		
		setTimer(0,datalist.length);
		
		function setTimer(i,length){
			timer = setTimeout(function() {
				drawREC(self.canvas,self.ctx,self.penColor,datalist[i]);
				if(i<length-1){
					setTimer(i+1,length);
				}
			}, 10);
		}
		function drawREC(canvas,ctx,penColor,data){
			ctx = canvas.getContext("2d");
			canvas.height = canvas.height;
			ctx.rect(data.xmin,data.ymin,data.xmax,data.ymax);
			ctx.strokeStyle = penColor;
			ctx.stroke();
		}
	},
	judgeClickPoint:function(markList){
		var left=event.pageX-this.photodata.abs_left;
		var top=event.pageY-this.photodata.abs_top;
		for (var i = 0; i < markList.length; i++) {
			if(left>markList[i].coords.left
				&&left<markList[i].coords.right
				&&top>markList[i].coords.top
				&&top<markList[i].coords.bottom){
				return true;//落在框内，应该点击移动
			}
		}
		return false;//不在任何框内，应该画图
	},
	init : function() {
		this.dataDC = null;
		this.utils = new Utils;
		this.photo = this.initPhoto();
		if (this.photo.style.zIndex == "") {
			this.photo.style.zIndex = 0;
		}
		this.photoParent = $(this.photo).parent()[0];
		this.bigBro = $("body")[0];
		this.maskerList = [];
		this.maskerCount = 0;

		this.photodata = {
			abs_left : 0,
			abs_top : 0,
			rela_left : 0,
			rela_top : 0,
			width : 0,
			height : 0,
			abs_right : 0,
			abs_bottom : 0,
		};

		this.moveClassName = "move";

		var photo_postion = this.utils.getAbsolutePosition(this.photo);
		this.photodata.abs_left = photo_postion.offsetLeft;
		this.photodata.abs_top = photo_postion.offsetTop;

		this.temp_change = $(this.photo).position();
		this.photodata.rela_left = this.temp_change.left;
		this.photodata.rela_top = this.temp_change.top;

		this.photodata.width = photo_postion.offsetWidth;
		this.photodata.height = photo_postion.offsetHeight;

		this.photodata.abs_right = photo_postion.offsetLeft
				+ photo_postion.offsetWidth;
		this.photodata.abs_bottom = photo_postion.offsetTop
				+ photo_postion.offsetHeight;

		if (typeof (this.canvas) == "undefined" || this.canvas == null) {
			this.createPhotoCanvas();
		}
		this.ctx = this.canvas.getContext("2d");

	},
	maskerInfo : {
		Names : [ "man", "car", "sign", "light", "line" ],
		Colors : [ "LawnGreen", "Fuchsia", "Gold", "Aqua", "Red" ],
		defaultColor : "#337ab7"// "#286090";
	},
	bindButton : function() {
		console.log("鼠标绑定");
		var self = this;
		$("." + this.moveBtnClassName).on("click", function() {// 如果选择器只选择了一个，则默认第一个，不用加[0]
			console.log("准备平移");
			self.changeToSlide();
		});
		$("." + this.maskerClassName).each(function() {// this=蒙版按钮元素
			$(this).on("click", function() {
				var backColor = self.getColor(this.name);
				this.style.backgroundColor = backColor;
				self.changeMasker(this, backColor);
			});
		});
	},
	unBindButton : function() {
		console.log("解绑鼠标绑定");
		var self = this;
		$("." + this.moveBtnClassName).off("click");
		$("." + this.maskerClassName).each(function() {
			this.style.backgroundColor = "transparent";
			$(this).off("click");
		});
	},
	getColor : function(btnName) {
		for (var i = 0; i < this.maskerInfo.Names.length; i++) {
			if (this.maskerInfo.Names[i] == btnName) {
				return this.maskerInfo.Colors[i];
			}
		}
	},
	setButtonColorByButton : function() {
		console.log("*********根据点击蒙版设置按键颜色*********");
		var btn;
		for (var i = 0; i < this.maskerList.length; i++) {
			btn = this.maskerList[i].button;
			btn.style.backgroundColor = this.getColor(btn.name);
		}
	},
	setButtonColorByMasker : function() {
		console.log("*********根据当前蒙版设置按键颜色*********");
		var btn;
		for (var i = 0; i < this.maskerList.length; i++) {
			if(this.currentMasker.id==this.maskerList[i].masker.id){
				btn=this.maskerList[i].button;
				btn.style.backgroundColor =this.getColor(btn.name);
			}
		}
	},
	clearButtColor : function(button) {
		button.style.backgroundColor ="transparent";
	},
/*	clearAllButtColor : function() {
		for (var i = 0; i < this.maskerList.length; i++) {
			this.maskerList[i].button.style.backgroundColor = "transparent";
		}
	},*/
	changeToDraw : function() {
		this.canvas.style.zIndex = 2;
		this.currentMasker.style.zIndex = 1;
	},
	changeToSlide : function() {
		this.canvas.style.zIndex = 1;
		this.currentMasker.style.zIndex = 2;
	},
	removeMasker : function(masker) {
		this.photoParent.removeChild(masker);
	},
	addfisrtMasker : function() {
		if (this.maskerCount > 0) {
			this.photoParent.appendChild(this.maskerList[0].masker);
			this.currentMasker = this.maskerList[0].masker;
		}
	},
	bindMaskerListener:function(masker){
		var self=this;
		$(masker).mousedown(function(){
			console.log("蒙版响应");
			self.changeToDraw();
		});
	},
	changeMasker : function(Btn, backColor,maskerInfo,dataDC,No_Masker) {//
		this.temp_change = this.isMaskerExist(Btn.id);
		if (this.temp_change != null) {// 切换蒙版
			this.unBindCanvas();// 定义在currentMasker和currentPen定义之前
			this.photoParent.removeChild(this.currentMasker);
			this.photoParent.appendChild(this.temp_change);
			this.currentMasker = this.temp_change;
			this.setCurrentMaskerIndex();
			this.currentPen = this.maskerList[this.currentMaskerCount].markPen;
			this.bindCanvas();// 定义在currentMasker和currentPen定义之后
			console.log("切换蒙版********");
		} else {// 新建蒙版
			this.temp_change = this.createMasker(Btn.name, backColor,
					this.maskerCount);
			if (this.currentMasker != undefined) {
				this.unBindCanvas();
				this.photoParent.removeChild(this.currentMasker);
			}
			this.currentMasker = this.temp_change;
			this.bindMaskerListener(this.currentMasker);
			if (this.dataDC != null&&No_Masker==undefined) {
				console.log("----------新画蒙版----------");
				this.dataDC.insertMasker(this.currentMasker.id, Btn.name);
			}
			this.photoParent.appendChild(this.currentMasker);
			this.addToMaskerList(Btn, this.currentMasker);
			this.setCurrentMaskerIndex();
			this.currentPen = new paintMark(this, Btn.id, backColor);
			if(maskerInfo!=null){//把原有的选框画上去
				var x;
				for(var i=0;i<maskerInfo.mark.length;i++){
					
					x =this.currentPen.finishMark(maskerInfo.mark[i]);
					this.currentMasker.appendChild(x.shape);
					act.move(x.id, this.moveClassName, 
							this.currentPen.getMarkDataFromList(x.id),
							dataDC);
					$("#"+x.id).find(":radio").each(function(){
						if(maskerInfo.mark[i].pose!=null){
							 $("input:radio[name="+this.name+"][value="+maskerInfo.mark[i].pose+"]").attr('checked','true');
						}
						if(maskerInfo.mark[i].truncated!=null){
							$("input:radio[name="+this.name+"][value="+maskerInfo.mark[i].truncated+"]").attr('checked','true');
						}
						if(maskerInfo.mark[i].difficult!=null){
							$("input:radio[name="+this.name+"][value="+maskerInfo.mark[i].difficult+"]").attr('checked','true');
						}
				  });

				}	
			}	
			this.bindCanvas();
			
			this.maskerList[this.currentMaskerCount].markPen = this.currentPen;
			this.maskerCount++;
			console.log("新建蒙版********");
		}
	},

	unBindCanvas : function() {
		this.canvasBinded=false;
		$(this.canvas).off("mouseover");
		$(this.canvas).off("mouseout");
		$(this.canvas).off("mousedown");
		$(this.canvas).off("mousemove");
		$("body").off("mouseup");
	},
	bindCanvas : function() {// 设置在currentPen和currentMasker重新定义之后\
		console.log("蒙版绑定");
		this.canvasBinded=true;
		var self = this;
		var multi = false;
		var canDraw = false;
		$(this.canvas).on("mouseover", function() {
			this.style.cursor = "crosshair";
		});
		$(this.canvas).on("mouseout", function() {
			this.style.cursor = "default";
		});
		$(this.canvas).on("mousedown", function() {
			console.log("-------开始框选  -----");
			if(self.judgeClickPoint(self.currentPen.markList)){
				console.log("准备平移");
				self.changeToSlide();
			}else{//不落在框内，开始画图
				if (self.currentPen.currentType == self.currentPen.markType.Multi) {
					if (self.currentPen.judgeMultiLineClose()) {
						canDraw = false;
						multi = false;
						var x = self.currentPen.finishMultiPath();
						self.currentMasker.appendChild(x.shape);
						
						act.move(x.id, self.moveClassName, self.dataDC);
					} else {
						self.currentPen.drawMultiLine();
						multi = true;
						canDraw = true;
					}
				} else {
					self.currentPen.beginDraw();
					canDraw = true;
				}
			}
			
			
		});
		$(this.canvas).on("mousemove", function() {
			if (self.currentPen.currentType == self.currentPen.markType.Multi) {
				if (multi == true) {
					self.currentPen.trackMultiPath();
				}

			} else {
				if (canDraw) {
					self.currentPen.resizeMark();
				}
			}

		});
		$("body").on("mouseup", function() {
			if (self.currentPen.currentType != self.currentPen.markType.Multi) {
				if (canDraw) {
					console.log("----框选结束");
					var x = self.currentPen.finishMark();
					self.currentMasker.appendChild(x.shape);
					act.move(x.id, self.moveClassName, 
							self.currentPen.getMarkDataFromList(x.id),
							self.dataDC);
					self.setButtonColorByMasker();//设置当前蒙版的按键颜色
					canDraw = false;
				}
			}	
		});
	},
	createMasker : function(marskerButtonName, maskerColor, maskerCount) {
		var masker = document.createElement("div");
		masker.id = this.photoID + marskerButtonName + "_" + maskerCount + "_";
		masker.name = marskerButtonName;
		masker.style.width = this.photodata.width + "px";
		masker.style.height = this.photodata.height + "px";
		masker.style.left = this.photodata.rela_left + "px";
		masker.style.top = this.photodata.rela_top + "px";
		masker.style.position = "absolute";
		masker.style.opacity = "1";
		masker.style.zIndex = 1;
		masker.className = "imgmasker";

		return masker;
	},
	addToMaskerList : function(button, newmasker) {
		this.maskerList[this.maskerList.length] = {
			button : button,
			masker : newmasker,
			markdata : [],
			markPen : null
		};
	},
	isMaskerExist : function(buttonID) {// 判断蒙版是否存在
		for (var i = 0; i < this.maskerList.length; i++) {
			if (this.maskerList[i].button.id == buttonID) {
				return this.maskerList[i].masker;
			}
		}
		return null;
	},
	setCurrentMaskerIndex : function() {// 设置当前蒙版在maskerlist中的序号
		for (var i = 0; i < this.maskerList.length; i++) {
			if (this.maskerList[i].masker.id == this.currentMasker.id) {
				this.currentMaskerCount = i;
			}
		}
	},

	initPhoto : function() {
		if (typeof ($("#" + this.photoID)[0]) != "undefined") {
			console.log("************找到需要标注的图片************");
			return $("#" + this.photoID)[0];
		} else {
			console.log("************该图片没有找到************");
		}
	},
	addDelete : function(cleanMaskerID) {
		var self = this;
		$("#" + cleanMaskerID).click(function() {
			self.deleteCurrentMaskerMarks();
		});
	},
	deleteCurrentMaskerMarks : function() {
		if (typeof (this.currentMasker) != "undefined") {
			var nodeList = this.currentMasker.childNodes;
			this.clearButtColor(this.maskerList[this.currentMaskerCount].button);

			if (nodeList.length > 0) {
				for (var i = nodeList.length - 1; i >= 0; i--) {
					this.currentMasker.removeChild(nodeList[i]);
					this.currentPen.markList.splice(this.currentPen.markList[i],1);
				}
				if (this.dataDC != null) {
					this.dataDC.deleteMarksByMasker(this.currentMasker.id);
				}
			}
		}
	},
	/*deletePhotoMarks : function() {
		if (typeof (this.currentMasker) != "undefined") {
			this.clearAllButtColor();
			var nodeList;
			for (var i = 0; i < this.maskerList.length; i++) {
				nodeList = this.maskerList[i].masker.childNodes;
				if (nodeList.length > 0) {
					for (var j = nodeList.length - 1; j >= 0; j--) {
						this.maskerList[i].masker.removeChild(nodeList[j]);
						this.currentPen.markList.splice(this.currentPen.markList[i],1);
					}
					if (this.dataDC != null) {
						this.dataDC.deleteMarksByMasker(this.maskerList[i].masker.id);
					}
				}
			}
		}
	},*/
};

paintMark = function(maskerUtil, maskerBtnID, penColor) {
	this.maskerUtil = maskerUtil;
	this.masker = maskerUtil.currentMasker;// 当前标注的蒙版
	this.canvas = maskerUtil.canvas;// 用来画框的临时画布
	this.ctx = maskerUtil.canvas.getContext("2d");

	this.maskerName = $("#" + maskerBtnID)[0].name;
	this.penColor = penColor;

	this.utils = new Utils();
	this.currentType;// 当前的标注类型
	this.markClassName = maskerUtil.markClassName;
	this.markBorderWidth=5;
	this.markBorder =this.markBorderWidth+"px dashed " + penColor;
	this.markList;
	this.absMarkCount;//用来做绝对计数，用于id增长

	this.draw_abs_startleft;// 画图的相对、绝对起点坐标
	this.draw_abs_starttop;
	this.draw_rela_left;
	this.draw_rela_top;
	this.draw_width;
	this.draw_height;

	this.abs_position;
	this.masker_width;// 当前蒙版的宽度，高度，绝对坐标的左上右下
	this.masker_height;
	this.masker_left;
	this.masker_top;
	this.masker_right;
	this.masker_bottom;

	this.max_radious;// 在画圆时用来确定宽高的更大值
	this.min_left;// 这两个实在画直线时用来比较大小
	this.min_top;
	this.multi_list = [];// 存储多边形标注的点
	this.multi_count = 0;

	this.init();
	this.initMarkTypeButton();
	this.setDefaultType();

	return this;
};

paintMark.prototype = {
	init : function() {
		this.markList=[];
		this.absMarkCount=0;
		this.abs_position = this.utils.getAbsolutePosition(this.masker);// 得到蒙版的绝对坐标
		this.masker_width = this.abs_position.offsetWidth;
		this.masker_height = this.abs_position.offsetHeight;
		this.masker_left = this.abs_position.offsetLeft;
		this.masker_top = this.abs_position.offsetTop;
		this.masker_right = this.abs_position.offsetLeft
				+ this.abs_position.offsetWidth;
		this.masker_bottom = this.abs_position.offsetTop
				+ this.abs_position.offsetHeight;
	},
	initMarkTypeButton : function() {
		var self = this;
		var type = this.markType;
		$("." + this.markClassName).each(function() {// this=蒙版按钮元素
			this.onclick = function() {
				self.maskerUtil.changeToDraw();
				if (this.classList.contains(type.REC)) {
					self.currentType = self.markType.REC;
				} else if (this.classList.contains(type.Circle)) {
					self.currentType = self.markType.Circle;
				} else if (this.classList.contains(type.Line)) {
					self.currentType = self.markType.Line;
				} else if (this.classList.contains(type.Multi)) {
					self.currentType = self.markType.Multi;
				}
			};
		});
	},
	markType : {
		REC : "REC",
		Circle : "CIRCLE",
		Line : "LINE",
		Multi : "MULTILINE"
	},

	setDefaultType : function() {// 设置默认的标注形式为框
		this.currentType = this.markType.REC;
	},

	initPen : function() {
		this.draw_abs_startleft = event.pageX;
		this.draw_abs_starttop = event.pageY;
		this.draw_rela_left = this.draw_abs_startleft - this.masker_left;
		this.draw_rela_top = this.draw_abs_starttop - this.masker_top;
	},
	cleanCanvas : function() {
		this.ctx = this.canvas.getContext("2d");
		this.canvas.height = this.canvas.height;
	},
	beginDraw : function() {
		this.initPen();
		if (this.currentType == this.markType.REC) {
			this.ctx.rect(this.draw_rela_left, this.draw_rela_top, 0, 0);// 对于框
		} else if (this.currentType == this.markType.Circle) {
			this.ctx.arc(this.draw_rela_left, this.draw_rela_top, 0, 0,
					2 * Math.PI);// 对于圆
		} else if (this.currentType == this.markType.Line) {
			this.ctx.beginPath();// 对于直线
		} else if (this.currentType == this.markType.Multi) {
			this.drawMultiLine();// 对于直线
		}
	},
	drawMultiLine : function() {
		if (this.multi_count == 0) {
			this.initPen();
			this.multi_list[this.multi_count] = {
				left : this.draw_rela_left,
				top : this.draw_rela_top,
				abs_left : this.draw_abs_startleft,
				abs_top : this.draw_abs_starttop
			};
		} else {
			this.multi_list[this.multi_count] = {// 这是最新的点
				left : this.multi_list[this.multi_count - 1].left
						+ this.draw_width,
				top : this.multi_list[this.multi_count - 1].top
						+ this.draw_height,
				abs_left : event.pageX,
				abs_top : event.pageY
			};
		}
		this.multi_count++;
	},
	resizeMark : function() {
		if (this.currentType == this.markType.REC) {
			this.resizeRec();
		} else if (this.currentType == this.markType.Circle) {
			this.resizeCircle();
		} else if (this.currentType == this.markType.Line) {
			this.resizeLine();
		} else if (this.currentType == this.markType.Multi) {
			this.resizeRec();
		}
	},

	judgeMultiLineClose : function() {// 判断缝合的距离的大小为20像素
		if (this.multi_count <= 1) {
			return false;
		}
		var distance = Math.pow(Math.pow(event.pageY
				- this.multi_list[0].abs_top, 2)
				+ Math.pow(event.pageX - this.multi_list[0].abs_left, 2), 0.5);
		if (distance < 20) {
			this.ctx.lineTo(this.multi_list[0].left, this.multi_list[0].top);
			this.ctx.strokeStyle = this.penColor;
			this.ctx.stroke();
			return true;
		} else {
			return false;
		}
	},

	resizeRec : function() {// 拖动时改变mark尺寸
		if (event.pageX > this.draw_abs_startleft) {// 如果超右方走
			if (event.pageX < this.masker_right) {
				this.draw_width = event.pageX - this.draw_abs_startleft;
			} else {// 如果超出边界
				this.draw_width = this.masker_right - this.draw_abs_startleft;
			}
			this.draw_rela_left = this.draw_abs_startleft - this.masker_left;
		} else {// 如果超左方走
			if (event.pageX > this.masker_left) {
				this.draw_width = this.draw_abs_startleft - event.pageX;
				this.draw_rela_left = event.pageX - this.masker_left;
			} else {// 如果超出边界
				this.draw_width = this.draw_abs_startleft - this.masker_left;
				this.draw_rela_left = 0;
			}
		}
		if (event.pageY > this.draw_abs_starttop) {// 如果超下方走
			if (event.pageY < this.masker_bottom) {
				this.draw_height = event.pageY - this.draw_abs_starttop;
			} else {// 如果超出边界
				this.draw_height = this.masker_bottom - this.draw_abs_starttop;
			}
			this.draw_rela_top = this.draw_abs_starttop - this.masker_top;
		} else {// 如果超上方走
			if (event.pageY > this.masker_top) {
				this.draw_rela_top = event.pageY - this.masker_top;
				this.draw_height = this.draw_abs_starttop - event.pageY;
			} else {// 如果超出边界
				this.draw_rela_top = 0;
				this.draw_height = this.draw_abs_starttop - this.masker_top;
			}

		}
		this.ctx = this.canvas.getContext("2d");
		this.canvas.height = this.canvas.height;
		this.ctx.rect(this.draw_rela_left, this.draw_rela_top, this.draw_width,
				this.draw_height);
		this.ctx.strokeStyle = this.penColor;
		this.ctx.stroke();
	},
	getMarkDataFromList:function(markID){
		for(var i=0;i<this.markList.length;i++){
			if(this.markList[i].id==markID){
				return this.markList[i];
			}
		}
	},
	loadRec:function(mark,data,markInfo){
		mark.id = markInfo.markID;
		var left;
		var top;
		var right;
		var bottom;
		left=parseInt(markInfo.coords.xmin);	
		mark.style.left = left + "px";
		
		top=parseInt(markInfo.coords.ymin);	
		mark.style.top = top + "px";
		
		right=parseInt(markInfo.coords.xmax);		
		bottom=parseInt(markInfo.coords.ymax);	
		mark.style.width = (right-left)+ "px";
		mark.style.height = (bottom-top)+ "px";
		data = {
				id : mark.id,
				type:"REC",
				coords : {
					left : left,// 标注框相对于爸爸的相对坐标
					top : top,
					right :right,
					bottom : bottom,
					width : (right-left),
					height : (bottom-top)
				},
				shape : mark
			// 标注框本身
		};
		return data
	},
	finishMark : function(markInfo) {
		var mark = document.createElement("div");
		mark.style.position = "absolute";
		//此处很关键，css3之前默认属性为content-box,之后为了区分加入boxSizing属性,此处用border
		mark.style.boxSizing="border-box";
		mark.style.border = this.markBorder;

		mark.classList.add(this.maskerUtil.moveClassName);
		mark.style.opacity = 1;
		mark.style.zIndex = 1;
		
		if (this.currentType == this.markType.REC) {
			var data;
			if(markInfo!=null){
				data=this.loadRec(mark,data,markInfo);
			}else{
				mark.id = this.masker.id + "rec_" + this.absMarkCount;
				mark.style.left = this.draw_rela_left + "px";// 这是相对于蒙版的相对坐标
				mark.style.top = this.draw_rela_top + "px";
				mark.style.width = this.draw_width+ "px";
				mark.style.height = this.draw_height+ "px"; 
				data = {
					id : mark.id,
					type:"REC",
					coords : {
						left : this.draw_rela_left,// 标注框相对于爸爸的相对坐标
						top : this.draw_rela_top,
						right : this.draw_rela_left + this.draw_width,
						bottom : this.draw_rela_top + this.draw_height,
						width : this.draw_width,
						height : this.draw_height
					},
					shape : mark
				// 标注框本身
				};
			}
			this.addPlugins(mark);
			this.cleanCanvas(data.coords.right);
			this.markList[this.markList.length]=data;
			if (this.maskerUtil.dataDC != null&&markInfo ==null) {
				this.maskerUtil.dataDC.insertCoords(mark.id, data.coords.left,
						data.coords.top, data.coords.right, data.coords.bottom);// 插入数据
			}
			this.absMarkCount++;
			return data;

		} else if (this.currentType == this.markType.Circle) {
			mark.id = this.masker.id + "circle_" + this.absMarkCount;
			mark.style.left = (this.draw_rela_left - this.max_radious) + "px";// 这是相对于蒙版的相对坐标
			mark.style.top = (this.draw_rela_top - this.max_radious) + "px";
			mark.style.width = this.max_radious * 2 + "px";
			mark.style.height = this.max_radious * 2 + "px";
			mark.style.borderRadius = this.max_radious + "px";
			var data = {
				id : mark.id,
				data : {
					left : (this.draw_rela_left - this.max_radious),// 标注框相对于爸爸的相对坐标
					top : (this.draw_rela_top - this.max_radious),
					right : (this.draw_rela_left + this.max_radious),
					bottom : (this.draw_rela_top + this.max_radious),
					width : this.max_radious * 2,
					height : this.max_radious * 2
				},
				shape : mark
			// 标注框本身
			};
			this.addPlugins(mark);
			this.cleanCanvas();
			
			this.absMarkCount++;
			return data;

		} else if (this.currentType == this.markType.Line) {
			return this.finishLine();
		}
	},
	lineOFTwoPoints : function(x1, y1, x2, y2, line_height) {

		var width = x1 - x2;
		var height = y1 - y2;
		var min_left;
		var min_top;
		if (x1 > x2) {
			min_left = x2;
		} else {
			min_left = x1;
		}
		if (y1 > y2) {
			min_top = y2;
		} else {
			min_top = y1;
		}
		var line_length = Math.pow((Math.pow(width, 2) + Math.pow(height, 2)),
				0.5);
		var degree = Math.acos(Math.abs(width) / line_length) * 180 / Math.PI;
		if ((width > 0 && height < 0) || (width < 0 && height > 0)) {// 代表\
			degree = -degree;
		}
		width = Math.abs(width);
		height = Math.abs(height);
		return data = {// data可以删除
			left : min_left,
			top : min_top,
			right : min_left + width,
			bottom : min_top + height,
			width : width,
			height : height,
			styleLeft : (min_left + (width - line_length) / 2),
			styleTop : (min_top + (height - line_height) / 2),
			styleWidth : line_length,
			styleHeight : line_height,
			degree : degree
		};
	},
	finishLine : function() {
		var mark = document.createElement("div");
		mark.style.position = "absolute";
		mark.style.border = this.markBorder;
		mark.style.visibility = "visible";
		mark.style.opacity = 1;
		mark.style.zIndex = 1;

		var linedata = this.lineOFTwoPoints(this.draw_rela_left,
				this.draw_rela_top, this.draw_rela_left + this.draw_width,
				this.draw_rela_top + this.draw_height, 10);
		mark.id = this.masker.id + "line_" + this.absMarkCount;
		if (this.draw_width < 0) {// 从右向左滑动
			mark.style.left = (linedata.styleLeft - this.draw_rela_left + linedata.width)
					+ "px";// 这是相对于蒙版的相对坐标
		} else {
			mark.style.left = (linedata.styleLeft - this.draw_rela_left) + "px";// 这是相对于蒙版的相对坐标
		}
		if (this.draw_height < 0) {// 从右向左滑动
			mark.style.top = (linedata.styleTop - this.draw_rela_top + linedata.height)
					+ "px";// 这是相对于蒙版的相对坐标
		} else {
			mark.style.top = (linedata.styleTop - this.draw_rela_top) + "px";
		}

		mark.style.width = linedata.styleWidth + "px";
		mark.style.height = linedata.styleHeight + "px";
		mark.style.transform = "rotate(" + linedata.degree + "deg)";

		var controlMark = document.createElement("div");
		controlMark.style.position = "absolute";
		controlMark.classList.add(this.maskerUtil.moveClassName);
		controlMark.style.border = this.markBorder;
		controlMark.style.visibility = "visible";
		controlMark.style.opacity = 1;
		controlMark.style.zIndex = 1;
		controlMark.id = "line_control_" + this.absMarkCount;
		controlMark.style.left = linedata.left + "px";// 这是相对于蒙版的相对坐标
		controlMark.style.top = linedata.top + "px";
		controlMark.style.width = linedata.width + "px";
		controlMark.style.height = linedata.height + "px";

		controlMark.appendChild(mark);
		this.addPlugins(controlMark);
		this.cleanCanvas();
		var data = {
			id : mark.id,
			data : {
				left : this.draw_rela_left,// 标注框相对于爸爸的相对坐标
				top : this.draw_rela_top,
				right : this.draw_rela_left + this.draw_width,
				bottom : this.draw_rela_top + this.draw_height,
				width : this.draw_width,
				height : this.draw_height
			},
			shape : controlMark,// 标注框本身
		};
		this.absMarkCount++;
		return data;
	},
	resizeCircle : function() {// 拖动时改变mark尺寸
		this.draw_rela_left = this.draw_abs_startleft - this.masker_left;
		this.draw_rela_top = this.draw_abs_starttop - this.masker_top;
		this.draw_width = Math.abs(event.pageX - this.draw_abs_startleft);
		this.draw_height = Math.abs(event.pageY - this.draw_abs_starttop);

		if (this.draw_width > this.draw_height) {
			this.max_radious = this.draw_width;
		} else {
			this.max_radious = this.draw_height;
		}

		if (this.draw_abs_startleft - this.masker_left <= this.max_radious) {
			this.max_radious = this.draw_abs_startleft - this.masker_left;
		} else if (this.masker_right - this.draw_abs_startleft <= this.max_radious) {
			this.max_radious = this.masker_right - this.draw_abs_startleft;
		}
		if (this.draw_abs_starttop - this.masker_top <= this.max_radious) {
			this.max_radious = this.draw_abs_starttop - this.masker_top;
		} else if (this.masker_bottom - this.draw_abs_starttop <= this.max_radious) {
			this.max_radious = this.masker_bottom - this.draw_abs_starttop;
		}
		this.ctx = this.canvas.getContext("2d");
		this.canvas.height = this.canvas.height;

		this.ctx.arc(this.draw_rela_left, this.draw_rela_top, this.max_radious,
				0, 2 * Math.PI);
		this.ctx.strokeStyle = this.penColor;
		this.ctx.stroke();

	},
	resizeLine : function() {// 拖动时改变mark尺寸

		this.draw_width = event.pageX - this.draw_abs_startleft;
		this.draw_height = event.pageY - this.draw_abs_starttop;
		this.ctx = this.canvas.getContext("2d");
		this.canvas.height = this.canvas.height;
		this.ctx.moveTo(this.draw_rela_left, this.draw_rela_top);
		this.ctx.lineTo(this.draw_rela_left + this.draw_width,
				this.draw_rela_top + this.draw_height);
		this.ctx.strokeStyle = this.penColor;
		this.ctx.stroke();

	},
	closePath : function() {//
		this.ctx.closePath();
		this.ctx.stroke();
	},
	trackMultiPath : function() {
		this.draw_width = event.pageX
				- this.multi_list[this.multi_count - 1].abs_left;
		this.draw_height = event.pageY
				- this.multi_list[this.multi_count - 1].abs_top;
		this.ctx = this.canvas.getContext("2d");
		this.canvas.height = this.canvas.height;
		this.ctx.moveTo(this.multi_list[0].left, this.multi_list[0].top);
		for (var i = 1; i < this.multi_count; i++) {
			this.ctx.lineTo(this.multi_list[i].left, this.multi_list[i].top);
		}
		this.ctx.lineTo(this.multi_list[this.multi_count - 1].left
				+ this.draw_width, this.multi_list[this.multi_count - 1].top
				+ this.draw_height);
		this.ctx.strokeStyle = this.penColor;
		this.ctx.stroke();
	},
	finishMultiPath : function() {
		var lineMarkList = [];
		var linemark;
		var linedata;
		var mark = document.createElement("div");
		mark.style.position = "absolute";
		mark.style.border = this.markBorder;
		// mark.style.visibility = "hidden";
		mark.classList.add(this.maskerUtil.moveClassName);
		mark.style.opacity = 1;
		mark.style.zIndex = 1;

		mark.id = "multi_line" + this.absMarkCount;
		var max_left = this.multi_list[0].left;// 找到最小值作为左边界
		var max_top = this.multi_list[0].top;
		var max_right = this.multi_list[0].left;
		var max_bottom = this.multi_list[0].top;
		for (var i = 0; i < this.multi_count; i++) {
			if (this.multi_list[i].left < max_left) {
				max_left = this.multi_list[i].left;
			}
			if (this.multi_list[i].left > max_right) {
				max_right = this.multi_list[i].left;
			}
			if (this.multi_list[i].top < max_top) {
				max_top = this.multi_list[i].top;
			}
			if (this.multi_list[i].top > max_bottom) {
				max_bottom = this.multi_list[i].top;
			}
		}
		for (var i = 0; i < this.multi_count; i++) {
			linemark = document.createElement("div");
			linemark.style.position = "absolute";
			linemark.style.border = this.markBorder;
			linemark.style.visibility = "visible";
			linemark.style.opacity = 1;
			linemark.style.zIndex = 1;

			if (i == this.multi_count - 1) {
				linedata = this.lineOFTwoPoints(this.multi_list[i].left
						- max_left, this.multi_list[i].top - max_top,
						this.multi_list[0].left - max_left,
						this.multi_list[0].top - max_top, 0.5);// 0点和1点连接，1和2连接，2和0连接
			} else {
				linedata = this.lineOFTwoPoints(this.multi_list[i].left
						- max_left, this.multi_list[i].top - max_top,
						this.multi_list[i + 1].left - max_left,
						this.multi_list[i + 1].top - max_top, 0.5);// 0点和1点连接，1和2连接，2和0连接
			}

			linemark.id = mark.id + "_line_" + i;
			linemark.style.left = linedata.styleLeft + "px";// 这是相对于蒙版的相对坐标
			linemark.style.top = linedata.styleTop + "px";
			linemark.style.width = linedata.styleWidth + "px";
			linemark.style.height = linedata.styleHeight + "px";
			linemark.style.transform = "rotate(" + linedata.degree + "deg)";

			lineMarkList[i] = linemark;
			mark.appendChild(linemark);
		}
		mark.style.left = max_left + "px";// 这是相对于蒙版的相对坐标
		mark.style.top = max_top + "px";
		mark.style.width = (max_right - max_left) + "px";
		mark.style.height = (max_bottom - max_top) + "px";
		this.addPlugins(mark);
		this.cleanCanvas();

		var data = {
			id : mark.id,
			data : {
				left : max_left,// 标注框相对于爸爸的相对坐标
				top : max_top,
				right : max_right,
				bottom : max_bottom,
				width : (max_right - max_left),
				height : (max_bottom - max_top)
			},
			shape : mark,// 标注框本身
			shapeList : lineMarkList
		};
		this.multi_list = [];
		this.multi_count = 0;
		this.absMarkCount++;
		return data;
	},
	addPlugins : function(mark) {
		var iplus = document.createElement('img');
		iplus.style.position = "absolute";
		iplus.src = "assets/dangyuchao/icon/plus.png";
		iplus.style.right = 0 + "px";
		iplus.style.top = 0 + "px";
		iplus.style.zIndex = 2;

		var toolbox = document.createElement('div');
		toolbox.style.position = "absolute";
		toolbox.style.right = "0px";
		toolbox.style.top = "25px";
		toolbox.style.width = "25px";
		toolbox.style.height = "50px";
		toolbox.style.opacity = 1;
		toolbox.style.zIndex = 2;
		toolbox.hidden = "hidden";

		var editbox = document.createElement('div');
		editbox.style.position = "absolute";
		editbox.style.backgroundColor = "white";
		editbox.style.right = "-250px";
		editbox.style.top = "50px";
		editbox.style.width = "250px";
		editbox.style.height = "60px";
		editbox.style.opacity = 1;
		editbox.style.border = "1px black solid";
		editbox.style.zIndex = 2;
		editbox.hidden = "hidden";

		this.initdelete(mark, toolbox);
		this.initEdit(toolbox, editbox);
		this.initEditbox(mark.id, editbox);

		$(iplus).click(function() {
			if (toolbox.hasAttribute("hidden")) {
				toolbox.removeAttribute("hidden");
				editbox.removeAttribute("hidden");
			} else {
				toolbox.hidden = "hidden";
				editbox.hidden = "hidden";
			}
			;
		});

		mark.appendChild(iplus);
		mark.appendChild(toolbox);
		mark.appendChild(editbox);
		
		
	},
	initdelete : function(mark, toolbox) {
		var self = this;
		var idelete = document.createElement('img');
		idelete.style.position = "absolute";
		idelete.src = "assets/dangyuchao/icon/delete.png";
		idelete.style.left = 0 + "px";
		idelete.style.top = 0 + "px";
		idelete.style.zIndex = 2;
		$(idelete).click(function() {
			if (self.maskerUtil.dataDC != null) {
				self.maskerUtil.dataDC.deleteCoords(mark.id);
			}
			for(var i=0;i<self.markList.length;i++){
				if(self.markList[i].id==mark.id){
					self.markList.splice(self.markList[i],1);
				}
			}
			$(mark).parent()[0].removeChild(mark);
		});
		toolbox.appendChild(idelete);
	},
	initEdit : function(toolbox, editbox) {
		var iedit = document.createElement('img');
		iedit.style.position = "absolute";
		iedit.src = "assets/dangyuchao/icon/edit.png";
		iedit.style.left = 0 + "px";
		iedit.style.top = 25 + "px";
		iedit.style.zIndex = 2;
		$(iedit).click(function() {
			if (editbox.hasAttribute("hidden")) {
				editbox.removeAttribute("hidden");
			} else {
				editbox.hidden = "hidden";
			}
			;
		});
		toolbox.appendChild(iedit);
	},
	checkInput : function(editName, count) {
		var x = document.getElementsByName(editName + count);
		for (var i = 0; i < x.length; i++) {
			if (x[i].checked) {
				return x[i].value;
			}
		}
	},
	initEditbox : function(markid, editbox) {
		var self = this;
		editbox.style.textAlign = "left";
		// *********************************************************拍摄角度***************************
		var angle_div = document.createElement('div');// 类别行
		var angle_name = document.createTextNode("拍摄角度: ");
		angle_div.appendChild(angle_name);

		// 角度项--左
		var angle_left_label = document.createElement('label');
		angle_left_label.classList.add("radio-inline");
		var angle_left_input = document.createElement('input');
		angle_left_input.type = "radio";
		angle_left_input.name = "angle" + markid;
		angle_left_input.value = "left";
		var angle_left_p = document.createTextNode("左");

		angle_left_label.appendChild(angle_left_input);
		angle_left_label.appendChild(angle_left_p);
		angle_div.appendChild(angle_left_label);

		// 角度项--中
		var angle_middle_label = document.createElement('label');
		angle_middle_label.classList.add("radio-inline");
		var angle_middle_input = document.createElement('input');
		angle_middle_input.type = "radio";
		angle_middle_input.name = "angle" + markid;
		angle_middle_input.value = "middle";
		var angle_middle_p = document.createTextNode("中");

		angle_middle_label.appendChild(angle_middle_input);
		angle_middle_label.appendChild(angle_middle_p);
		angle_div.appendChild(angle_middle_label);

		// 角度项--右
		var angle_right_label = document.createElement('label');
		angle_right_label.classList.add("radio-inline");
		var angle_right_input = document.createElement('input');
		angle_right_input.type = "radio";
		angle_right_input.name = "angle" + markid;
		angle_right_input.value = "right";
		var angle_right_p = document.createTextNode("右");

		angle_right_label.appendChild(angle_right_input);
		angle_right_label.appendChild(angle_right_p);
		angle_div.appendChild(angle_right_label);

		// 角度项--后
		var angle_back_label = document.createElement('label');
		angle_back_label.classList.add("radio-inline");
		var angle_back_input = document.createElement('input');
		angle_back_input.type = "radio";
		angle_back_input.name = "angle" + markid;
		angle_back_input.value = "back";
		var angle_back_p = document.createTextNode("后");

		angle_back_label.appendChild(angle_back_input);
		angle_back_label.appendChild(angle_back_p);
		angle_div.appendChild(angle_back_label);
		
		
		angle_div.onclick = function() {
			var x = $("input[name='" + angle_left_input.name + "']:checked")
					.val();
			if (self.maskerUtil.dataDC != null) {
				self.maskerUtil.dataDC.updatePose(markid, x);
			}
		}

		// *********************************************************是否被截断***************************
		var cut_div = document.createElement('div');// 类别行
		var cut_name = document.createTextNode("被截断: ");
		cut_div.appendChild(cut_name);

		// 截断项--是
		var cut_yes_label = document.createElement('label');
		cut_yes_label.classList.add("radio-inline");
		var cut_yes_input = document.createElement('input');
		cut_yes_input.type = "radio";
		cut_yes_input.name = "truncated" + markid;
		cut_yes_input.value = "0";
		var cut_yes_p = document.createTextNode("是");
		// cut_yes_input.checked = true;
		cut_yes_label.appendChild(cut_yes_input);
		cut_yes_label.appendChild(cut_yes_p);
		cut_div.appendChild(cut_yes_label);

		// 截断项--否
		var cut_no_label = document.createElement('label');
		cut_no_label.classList.add("radio-inline");
		var cut_no_input = document.createElement('input');
		cut_no_input.type = "radio";
		cut_no_input.name = "truncated" + markid;
		cut_no_input.value = "1";
		var cut_no_p = document.createTextNode("否");

		cut_div.onclick = function() {
			var x = $("input[name='" + cut_no_input.name + "']:checked").val();
			if (self.maskerUtil.dataDC != null) {
				self.maskerUtil.dataDC.updateTrun(markid, x);
			}
		}

		cut_no_label.appendChild(cut_no_input);
		cut_no_label.appendChild(cut_no_p);
		cut_div.appendChild(cut_no_label);

		// *********************************************************是否清晰***************************

		var recog_div = document.createElement('div');// 类别行
		var recog_name = document.createTextNode("难以识别: ");
		recog_div.appendChild(recog_name);

		// 清晰项--是
		var recog_yes_label = document.createElement('label');
		recog_yes_label.classList.add("radio-inline");
		var recog_yes_input = document.createElement('input');
		recog_yes_input.type = "radio";
		recog_yes_input.name = "clear" + markid;
		;
		recog_yes_input.value = "0";
		var recog_yes_p = document.createTextNode("是");
		// recog_yes_input.checked = true;
		recog_yes_label.appendChild(recog_yes_input);
		recog_yes_label.appendChild(recog_yes_p);
		recog_div.appendChild(recog_yes_label);

		// 清晰项--否
		var recog_no_label = document.createElement('label');
		recog_no_label.classList.add("radio-inline");
		var recog_no_input = document.createElement('input');
		recog_no_input.type = "radio";
		recog_no_input.name = "clear" + markid;
		;
		recog_no_input.value = "1";
		var recog_no_p = document.createTextNode("否");

		recog_div.onclick = function() {
			var x = $("input[name='" + recog_no_input.name + "']:checked")
					.val();
			if (self.maskerUtil.dataDC != null) {
				self.maskerUtil.dataDC.updatediff(markid, x);
			}
		}

		recog_no_label.appendChild(recog_no_input);
		recog_no_label.appendChild(recog_no_p);
		recog_div.appendChild(recog_no_label);

		editbox.appendChild(angle_div);
		editbox.appendChild(cut_div);
		editbox.appendChild(recog_div);
		
	},
};
