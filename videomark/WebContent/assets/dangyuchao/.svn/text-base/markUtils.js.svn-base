photoInput=function(photoID,getdata, maskerClassName, 
		markClassName, moveBtnClassName,cleanMaskerID,viewRatio){
	this.photoID=photoID;
	this.getdata=getdata;
	this.maskerClassName=maskerClassName;
	this.markClassName=markClassName;
	this.moveBtnClassName=moveBtnClassName;
	this.cleanMaskerID=cleanMaskerID;
	this.viewRatio=viewRatio;
	
	this.markPhoto;
	this.currentPhoto;
	this.PhotoDC;
	
	
	this.init();
};
photoInput.prototype = {
		init:function(){
			this.PhotoDC=new PhotoDC(this.getdata);
		},	
		
		processPhoto:function(index){
			if(this.getdata==null){//接受到值不为空
				return null;
			}
			if(this.getdata.taskid!=null){//接受到值不为空
				if(this.getdata.photolist.length==0){
					return null;
				}
				console.log("加载数据集");
				
				this.markPhoto= new markPhoto(this.photoID, 
						this.maskerClassName,
						this.markClassName, 
						this.moveBtnClassName);
				this.markPhoto.initDataCenter(this.PhotoDC);
				this.markPhoto.addDelete(this.cleanMaskerID);
				
				var photoInfo=this.getdata.photolist[index];
				
				this.markPhoto.drawMasker(photoInfo,this.viewRatio);
			}else{
				console.log("不加载数据集");
				this.markPhoto= new markPhoto(this.photoID, 
						this.maskerClassName,
						this.markClassName, 
						this.moveBtnClassName);
				this.markPhoto.initDataCenter(this.PhotoDC);
				this.markPhoto.addphoto(this.photoID);
				this.markPhoto.addDelete(this.cleanMaskerID);
			}	
		},
};

VideoInput=function(subformID,getdata,shotbuttonID,
		photoParentID,maskerClassName,markClassName,
		moveBtnClassName,videoID,videoShotCount,
		deleteimgID,cleanMaskerID,previewboard,previewboardHeight){
	this.formID=subformID;
	this.getdata=getdata;
	this.shotbuttonID=shotbuttonID;
	this.photoParentID=photoParentID;
	this.maskerClassName=maskerClassName;
	this.markClassName=markClassName;
	this.moveBtnClassName=moveBtnClassName;
	this.videoID=videoID;
	this.videoShotCount=videoShotCount;
	this.deleteimgID=deleteimgID;
	this.cleanMaskerID=cleanMaskerID;
	this.previewboard=previewboard;
	this.previewboardHeight=previewboardHeight;
	
	this.currentVideo;
	this.videoDC;
	this.scalerate;//视频缩放比例
	this.previewRate;//缩略图比例
	
	this.shotVideo;

	this.init();
};
VideoInput.prototype = {
		init:function(){
			this.videoDC= new VideoDC(this.getdata);
			this.scalerate =0.5;//视频缩放比例
			this.previewRate=0.3;//缩略图比例
			
		},
		processVideo:function(index){//制定videolist中的一个video
			if(this.getdata==null){//接受到值不为空
				return null;
			}

			if(this.getdata.taskid!=null){//接受到值不为空
				if(this.getdata.videolist.length==0){
					return null;
				}
				console.log("加载数据集");
				var process_video=this.getdata.videolist[index];//取到要处理的视频
				
				//暂时用页面的video标签的id，后面要改为视频的容器
				this.currentVideo=$("#"+this.videoID)[0];
				//暂时用页面的video标签的id，后面要改为视频的容器
				this.currentVideo.src=process_video.url;			
	
				this.shotVideo= new ShotVideo(this.videoID,
						this.scalerate,
						this.shotbuttonID,
						this.photoParentID,
						this.maskerClassName,
						this.markClassName,
						this.moveBtnClassName,
						process_video.photos,
						this.videoDC);
				this.writeForm(process_video.formdata);//处理表单数据
			}else{
				console.log("不加载数据集");
				this.currentVideo=$("#"+this.videoID)[0];
				this.shotVideo= new ShotVideo(this.videoID,
						this.scalerate,
						this.shotbuttonID,
						this.photoParentID,
						this.maskerClassName,
						this.markClassName,
						this.moveBtnClassName);
				
				this.shotVideo.initDataCenter(this.videoDC);
				this.shotVideo.addVideo();
			}

			this.shotVideo.initCountHolder(this.videoShotCount);
			this.shotVideo.initPreview(this.previewboard,this.previewRate,this.previewboardHeight);
			this.shotVideo.addDelete(this.deleteimgID,this.cleanMaskerID); 
				
			
		},
		writeForm:function(formdata){
			$('#'+this.formID).find('input, textarea').each(function () {
				if (this.type === 'hidden') {
					return;
				}
				if (! formdata.hasOwnProperty(this.name)) return;
					
				if (this.type == 'radio' && this.value == formdata[this.name]) {
					this.checked = true;
				}
				if (this.type === 'checkbox') {
					if (typeof formdata[this.name] === 'string' && this.value === formdata[this.name]) {
						this.checked = true;
					} else if ($.inArray(this.value, formdata[this.name]) >= 0) {
						this.checked = true;
					}
				}
				if (this.tagName === 'TEXTAREA') {
					this.innerText = formdata[this.name];
				}
			});
		}
};

Utils=function(){};
Utils.prototype = {//工具类
		offset:{
			offsetTop:null,
			offsetLeft:null,
			offsetWidth:null,
			offsetHeight:null	
		},
		getAbsolutePosition:function(obj){
			var offset=this.offset;
			 
			offset.offsetTop= obj.offsetTop;  
			offset.offsetLeft= obj.offsetLeft;  
			offset.offsetWidth= obj.offsetWidth;  
			offset.offsetHeight= obj.offsetHeight;  
	        while (obj = obj.offsetParent) {
	        	offset.offsetTop += obj.offsetTop;  
	        	offset.offsetLeft += obj.offsetLeft;  
	        }  
	          
	        return offset;
	            
		},
		getOffSet:function(obj){
			var offset=this.offset;
			 
			offset.offsetTop= obj.offsetTop;  
			offset.offsetLeft= obj.offsetLeft;  
			offset.offsetWidth= obj.offsetWidth;  
			offset.offsetHeight= obj.offsetHeight;  
	          
	        return offset;
		},		
};
PhotoDC=function(getdata){//数据中心
	this.subdata=getdata;
	
	this.now_photo;
	this.now_masker;
	this.now_mark;
	this.newdata={
			id:null,
			count:{
				photoscount:0,//photoscount
				maskercount:0,//maskercount
				shapecount:0//shapecount
			},
			mark:{
				pose : null,
				truncated : 0,
				difficult : 0,
				coords:{
					xmin : 0,//left
					ymin : 0,//top
					xmax : 0,//right
					ymax : 0//bottom
				}
			},
			values:{
				ID : null,
				mark : null,//视频是否标注过
				name : null//蒙版名字	
			}	
	}
};
PhotoDC.prototype={//shot for DataCenter
		data:{
			id:{
				p:"photolist",
				m:"masker",
				r:"shape",
			},
			count:{
				photoscount:0,//photoscount
				maskercount:0,//maskercount
				shapecount:0//shapecount
			},
			mark:{
				pose : null,
				truncated : 0,
				difficult : 0,
				coords:{
					xmin : 0,//left
					ymin : 0,//top
					xmax : 0,//right
					ymax : 0//bottom
				}
			},
			values:{
				ID : null,
				mark : null,//视频是否标注过
				name : null//蒙版名字		
			}
		},
		formcount:function(photoscount,maskercount,shapecount){
			var count={
					photoscount:photoscount,
					maskercount:maskercount,
					shapecount:shapecount
			};
			return count;
		},
		formcoords:function(left,top,right,bottom){
			var coords={
						xmin : left,//left
						ymin : top,//top
						xmax : right,//right
						ymax : bottom//bottom
			};
			return coords;
		},
		formmark:function(pose,truncated,difficult,coords){
			var mark={ 
					pose : pose,
					truncated : truncated,
					difficult : difficult,
					coords:coords
			};
			return mark;
		},
		formvalues:function(ID,mark,name){
			var values={
					ID : ID,
					mark : mark,//视频是否标注过
					name : name//蒙版名字	
			};
			return values;
		},
		formData:function(ID,count,mark,values){
			var data={
					ID:ID,
					count:count,
					mark:mark,
					values:values	
			};
			return data;
		},
		
		getMaxIndex:function(input){
			switch (input.id)
			{
			case this.data.id.p:
				return this.subdata.photolist.length;
			case this.data.id.m:
				return this.subdata.photolist[input.count.photoscount].masker.length;
			case this.data.id.r:
				return this.subdata.photolist[input.count.photoscount].masker[input.count.maskercount].mark.length;
			default:
				return null;
			}			
		},
		getNowMarkIndex:function(ID){
			this.str=ID.split("_");
			var photoCount=parseInt(this.str[1]);
			var maskerCount=parseInt(this.str[3]);
			for(var i=0;i<this.subdata.photolist[photoCount].masker[maskerCount].mark.length;i++){
				if(this.subdata.photolist[photoCount].masker[maskerCount].mark[i].markID==ID){
					return i;
				}
			}
		},
		getNowIndex:function(ID){
			this.str=ID.split("_");
			return this.formcount(parseInt(this.str[1]),parseInt(this.str[3]),parseInt(this.str[5]));
			
		},
		writeData:function(input){
			switch (input.id)
			{
			case this.data.id.p:
				this.subdata.photolist[input.count.photoscount]={
					photoID : null,
					mark:"yes",
					url:"",
					original_file_id:null,
					masker :[]
				};
				this.subdata.photolist[input.count.photoscount].photoID=input.values.ID;
				break;
			case this.data.id.m:
				this.subdata.photolist[input.count.photoscount].masker[input.count.maskercount]={
					maskerID : null,
					name : null,//蒙版名字
					mark :[]//标记信息
				};
				this.subdata.photolist[input.count.photoscount].masker[input.count.maskercount].maskerID=input.values.ID;
				this.subdata.photolist[input.count.photoscount].masker[input.count.maskercount].name=input.values.name;
				break;
			case this.data.id.r:
				this.subdata.photolist[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount]={
					markID : null,
					pose : null,
					truncated : null,
					difficult : null,
					coords:{
						xmin : 0,
						ymin : 0,
						xmax : 0,
						ymax : 0,	
					}
				};
				this.subdata.photolist[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount].markID=input.values.ID;
				this.subdata.photolist[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount].pose=input.mark.pose;
				this.subdata.photolist[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount].truncated=input.mark.truncated;
				this.subdata.photolist[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount].difficult=input.mark.difficult;
				this.subdata.photolist[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount].coords=input.mark.coords;
				break;
			default:
				return null;
			}
		},
		updateData:function(input){
			switch (input.id)
			{
			case this.data.id.p:
				if(input.values.timestamp!=null){
					this.subdata.photolist[input.count.photoscount].timestamp=input.values.timestamp;
				}			
				break;
			case this.data.id.m:
				if(input.values.name!=null){
					this.subdata.photolist[input.count.photoscount].masker[input.count.maskercount].name=input.values.name;
				}				
				break;
			case this.data.id.r:
				if(input.mark.pose!=null){
					this.subdata.photolist[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount].pose=input.mark.pose;
				}		
				if(input.mark.truncated!=null){
					this.subdata.photolist[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount].truncated=input.mark.truncated;
				}		
				if(input.mark.difficult!=null){
					this.subdata.photolist[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount].difficult=input.mark.difficult;
				}		
				if(input.mark.coords!=null){
					this.subdata.photolist[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount].coords=input.mark.coords;
				}		
				break;
			default:
				return null;
			}
		},
		getData:function(input){
			switch (input.id)
			{
			case this.data.id.v:
				data.values.mark=this.subdata.videolist[input.count.videocount].mark;
				data.values.formdata= this.subdata.videolist[input.count.videocount].formdata;
				return data;
			case this.data.id.p:
				data.values.timestamp=this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].timestamp;
				return data;
			case this.data.id.m:
				data.values.name=this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker[input.count.maskercount].name;
				return data;
			case this.data.id.r:
				data.mark.type=this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount].name;
				data.mark.pose=this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount].pose;
				data.mark.truncated=this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount].truncated;
				data.mark.diffult=this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount].difficult;			
				data.mark.coords=this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount].coords;
				return data;
			default:
				return null;
			}
		},
		deleteData:function(input){		
			switch (input.id)
			{
			case this.data.id.m:
				this.subdata.photolist[input.count.photoscount].masker.splice(input.count.maskercount,1);
				break;
			case this.data.id.r:
				this.subdata.photolist[input.count.photoscount].masker[input.count.maskercount].mark.splice(input.count.shapecount,1);
				break;
			default:
				return null;
			}
		},
		deleteMarksByMasker:function(maskerID){
			this.newdata.count=this.getNowIndex(maskerID);
			this.now_photo=this.newdata.count.photoscount;
			this.now_masker=this.newdata.count.maskercount;

			var marks=this.subdata.photolist[this.now_photo].masker[this.now_masker];
			
			marks.mark.splice(0,marks.mark.length);
		},
		insertCoords:function(id,v2,v3,v4,v5){
			this.newdata.id=this.data.id.r;//修改data
			
			this.newdata.count=this.getNowIndex(id);//通过图片id找到图片所属的视频位置
			this.now_photo=this.newdata.count.photoscount;
			this.now_masker=this.newdata.count.maskercount;
			this.now_mark=this.getMaxIndex(this.newdata);
			
			this.newdata.count=this.formcount(this.now_photo,this.now_masker,this.now_mark);//修改计数
			this.newdata.mark.coords=this.formcoords(v2,v3,v4,v5);
			this.newdata.mark=this.formmark(null,null,null,this.newdata.mark.coords);//修改坐标
			this.newdata.values=this.formvalues(id,null,null);
			this.writeData(this.newdata);
		},
		
		getCoordsUpdateCount:function(id){//更新坐标时的通用代码，下面在更新坐标，角度时用到
			this.newdata.id=this.data.id.r;//修改data
			this.newdata.count=this.getNowIndex(id);//通过图片id找到图片所属的视频位置
			this.now_photo=this.newdata.count.photoscount;
			this.now_masker=this.newdata.count.maskercount;
			this.now_mark=this.getNowMarkIndex(id);
			
			this.newdata.count=this.formcount(this.now_photo,this.now_masker,this.now_mark);//修改计数
		},
		updateCoords:function(id,p1,p2,p3,p4){
			this.getCoordsUpdateCount(id);			
			this.newdata.mark.coords=this.formcoords(p1,p2,p3,p4);
			this.newdata.mark=this.formmark(null,null,null,this.newdata.mark.coords);//修改坐标
			this.updateData(this.newdata);
		},
		deleteCoords:function(id){
			this.getCoordsUpdateCount(id);
			this.deleteData(this.newdata);
		},
		updatePose:function (id,pose){
			this.getCoordsUpdateCount(id);			
			this.newdata.mark=this.formmark(pose,null,null,null);//修改坐标
			this.updateData(this.newdata);
		},
		updateTrun:function (id,truncated){//更新是否截断
			this.getCoordsUpdateCount(id);			
			this.newdata.mark=this.formmark(null,truncated,null,null);
			this.updateData(this.newdata);
		},
		updatediff:function (id,difficult){//更新是否难以识别
			this.getCoordsUpdateCount(id);			
			this.newdata.mark=this.formmark(null,null,difficult,null);
			this.updateData(this.newdata);
		},		
		insertMasker:function(id,maskerName){//修改蒙版名字
			this.newdata.id=this.data.id.m;

			this.newdata.count=this.getNowIndex(id);//通过图片id找到图片所属的视频位置
			this.now_photo=this.newdata.count.photoscount;
			this.now_masker=this.getMaxIndex(this.newdata);
			
			this.newdata.count=this.formcount(this.now_photo,this.now_masker);//修改计数
			this.newdata.values=this.formvalues(id,null,maskerName);
			this.writeData(this.newdata);
		},
		insertPhoto:function(id){
			this.newdata.id=this.data.id.p;
			
			this.now_photo=this.getMaxIndex(this.newdata);
			
			this.newdata.count=this.formcount(this.now_photo);//修改计数
			this.newdata.values=this.formvalues(id,null,null);
			this.writeData(this.newdata);
		},
		prepareCoords:function(viewRatio){
			var temp;
			for(var i=0;i<this.subdata.photolist.length;i++){

				for(var j=0;j<this.subdata.photolist[i].masker.length;j++){

					for(var k=0;k<this.subdata.photolist[i].masker[j].mark.length;k++){
						temp=this.subdata.photolist[i].masker[j].mark[k].coords;
						temp.xmin=(temp.xmin/viewRatio).toFixed(0);
						temp.ymin=(temp.ymin/viewRatio).toFixed(0);
						temp.xmax=(temp.xmax/viewRatio).toFixed(0);
						temp.ymax=(temp.ymax/viewRatio).toFixed(0);
					}
				}
			}
		}
}
VideoDC=function(getdata){//数据中心
	this.subdata=getdata;
	this.now_video;
	this.now_photo;
	this.now_masker;
	this.now_mark;
	this.newdata={
			id:null,
			count:{
				videocount:0,//videocount
				photoscount:0,//photoscount
				maskercount:0,//maskercount
				shapecount:0//shapecount
			},
			mark:{
				pose : null,
				truncated : 0,
				difficult : 0,
				coords:{
					xmin : 0,//left
					ymin : 0,//top
					xmax : 0,//right
					ymax : 0//bottom
				}
			},
			values:{
				ID : null,
				mark : null,//视频是否标注过
				formdata : null,//视频的信息
				timestamp : null,//截图时间点
				name : null//蒙版名字	
			}	
	}
};
VideoDC.prototype={//shot for DataCenter
		data:{
			id:{
				v:"videolist",
				p:"photos",
				m:"masker",
				r:"shape",
			},
			count:{
				videocount:0,//videocount
				photoscount:0,//photoscount
				maskercount:0,//maskercount
				shapecount:0//shapecount
			},
			mark:{
				pose : null,
				truncated : 0,
				difficult : 0,
				coords:{
					xmin : 0,//left
					ymin : 0,//top
					xmax : 0,//right
					ymax : 0//bottom
				}
			},
			values:{
				ID : null,
				mark : null,//视频是否标注过
				formdata : null,//视频的信息
				timestamp : null,//截图时间点
				name : null//蒙版名字		
			}
		},
		
		formcount:function(videocount,photoscount,maskercount,shapecount){
			var count={
					videocount:videocount,
					photoscount:photoscount,
					maskercount:maskercount,
					shapecount:shapecount
			};
			return count;
		},
		formcoords:function(left,top,right,bottom){
			var coords={
						xmin : left,//left
						ymin : top,//top
						xmax : right,//right
						ymax : bottom//bottom
			};
			return coords;
		},
		formmark:function(pose,truncated,difficult,coords){
			var mark={ 
					pose : pose,
					truncated : truncated,
					difficult : difficult,
					coords:coords
			};
			return mark;
		},
		formvalues:function(ID,mark,formdata,timestamp,name){
			var values={
					ID : ID,
					mark : mark,//视频是否标注过
					formdata : formdata,//视频的信息
					timestamp : timestamp,//截图时间点
					name : name//蒙版名字	
			};
			return values;
		},
		formData:function(ID,count,mark,values){
			var data={
					ID:ID,
					count:count,
					mark:mark,
					values:values	
			};
			return data;
		},
		
		getMaxIndex:function(input){
			switch (input.id)
			{
			case this.data.id.v:
				return this.subdata.videolist.length;
			case this.data.id.p:
				return this.subdata.videolist[input.count.videocount].photos.length;
			case this.data.id.m:
				return this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker.length;
			case this.data.id.r:
				return this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker[input.count.maskercount].mark.length;
			default:
				return null;
			}			
		},
		getNowPhotoIndex:function(ID){
			this.str=ID.split("_");
			var videoIndex=parseInt(this.str[1]);
			var photoID=this.str[0]+"_"+this.str[1]+"_"+this.str[2]+"_"+this.str[3]+"_";
			for(var i=0;i<this.subdata.videolist[videoIndex].photos.length;i++){
				if(this.subdata.videolist[videoIndex].photos[i].photoID==photoID){
					return i;
				}
			}
		},
		getNowMarkIndex:function(ID){
			this.str=ID.split("_");
			var videoCount=parseInt(this.str[1]);
			var photoIndex=this.getNowPhotoIndex(ID);
			var maskerCount=parseInt(this.str[5]);
			for(var i=0;i<this.subdata.videolist[videoCount].photos[photoIndex].masker[maskerCount].mark.length;i++){
				if(this.subdata.videolist[videoCount].photos[photoIndex].masker[maskerCount].mark[i].markID==ID){
					return i;
				}
			}
		},
		getNowIndex:function(ID){
			this.str=ID.split("_");
			return this.formcount(parseInt(this.str[1]),parseInt(this.str[3]),parseInt(this.str[5]),parseInt(this.str[7]));
			
		},
		writeData:function(input){
			switch (input.id)
			{
			case this.data.id.v:
				this.subdata.videolist[input.count.videocount]={
					videoID : null,
					url:"",
					mark : "yes",
					formdata : null,
					original_file_id:null,
					photos : []
				};
				this.subdata.videolist[input.count.videocount].videoID=input.values.ID;
				this.subdata.videolist[input.count.videocount].formdata=input.values.formdata;
				break;
			case this.data.id.p:
				this.subdata.videolist[input.count.videocount].photos[input.count.photoscount]={
					photoID : null,
					mark:"yes",
					url:"",
					timestamp : null,	
					masker :[]
				};
				this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].photoID=input.values.ID;
				this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].timestamp=input.values.timestamp;
				break;
			case this.data.id.m:
				this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker[input.count.maskercount]={
					maskerID : null,
					name : null,//蒙版名字
					mark :[]//标记信息
				};
				this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker[input.count.maskercount].maskerID=input.values.ID;
				this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker[input.count.maskercount].name=input.values.name;
				break;
			case this.data.id.r:
				this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount]={
					markID : null,
					pose : null,
					truncated : null,
					difficult : null,
					coords:{
						xmin : 0,
						ymin : 0,
						xmax : 0,
						ymax : 0,	
					}
				};
				this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount].markID=input.values.ID;
				this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount].pose=input.mark.pose;
				this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount].truncated=input.mark.truncated;
				this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount].difficult=input.mark.difficult;
				this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount].coords=input.mark.coords;
				break;
			default:
				return null;
			}
		},
		updateData:function(input){
			switch (input.id)
			{
			case this.data.id.v:
				if(input.values.mark!=null){
					this.subdata.videolist[input.count.videocount].mark=input.values.mark;
				}
				if(input.values.formdata!=null){
					this.subdata.videolist[input.count.videocount].formdata=input.values.formdata;
				}		
				break;
			case this.data.id.p:
				if(input.values.timestamp!=null){
					this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].timestamp=input.values.timestamp;
				}		
				
				break;
			case this.data.id.m:
				if(input.values.name!=null){
					this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker[input.count.maskercount].name=input.values.name;
				}				
				break;
			case this.data.id.r:
				if(input.mark.pose!=null){
					this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount].pose=input.mark.pose;
				}		
				if(input.mark.truncated!=null){
					this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount].truncated=input.mark.truncated;
				}		
				if(input.mark.difficult!=null){
					this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount].difficult=input.mark.difficult;
				}		
				if(input.mark.coords!=null){
					this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount].coords=input.mark.coords;
				}		
				break;
			default:
				return null;
			}
		},
		getData:function(input){
			switch (input.id)
			{
			case this.data.id.v:
				data.values.mark=this.subdata.videolist[input.count.videocount].mark;
				data.values.formdata= this.subdata.videolist[input.count.videocount].formdata;
				return data;
			case this.data.id.p:
				data.values.timestamp=this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].timestamp;
				return data;
			case this.data.id.m:
				data.values.name=this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker[input.count.maskercount].name;
				return data;
			case this.data.id.r:
				data.mark.type=this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount].name;
				data.mark.pose=this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount].pose;
				data.mark.truncated=this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount].truncated;
				data.mark.diffult=this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount].difficult;			
				data.mark.coords=this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker[input.count.maskercount].mark[input.count.shapecount].coords;
				return data;
			default:
				return null;
			}
		},
		deleteData:function(input){		
			switch (input.id)
			{
			case this.data.id.v:
				this.subdata.videolist[input.count.videocount]=null;
				break;
			case this.data.id.p:
				this.subdata.videolist[input.count.videocount].photos.splice(input.count.photoscount,1);
				break;
			case this.data.id.m:
				this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker.splice(input.count.maskercount,1);
				break;
			case this.data.id.r:
				this.subdata.videolist[input.count.videocount].photos[input.count.photoscount].masker[input.count.maskercount].mark.splice(input.count.shapecount,1);
				break;
			default:
				return null;
			}
		},
		deleteMarksByMasker:function(maskerID){
			this.newdata.count=this.getNowIndex(maskerID);
			this.now_video=this.newdata.count.videocount;
			this.now_photo=this.getNowPhotoIndex(maskerID);
			this.now_masker=this.newdata.count.maskercount;

			var marks=this.subdata.videolist[this.now_video].photos[this.now_photo].masker[this.now_masker];
			
			marks.mark.splice(0,marks.mark.length);
			console.log(this.subdata);
		},
		insertCoords:function(id,v2,v3,v4,v5){
			this.newdata.id=this.data.id.r;//修改data
			
			this.newdata.count=this.getNowIndex(id);//通过图片id找到图片所属的视频位置
			this.now_video=this.newdata.count.videocount;
			this.now_photo=this.getNowPhotoIndex(id);
			this.now_masker=this.newdata.count.maskercount;
			this.newdata.count.photoscount=this.now_photo;
			this.newdata.count.maskercount=this.now_masker;
			this.now_mark=this.getMaxIndex(this.newdata);
			
			this.newdata.count=this.formcount(this.now_video,this.now_photo,this.now_masker,this.now_mark);//修改计数
			this.newdata.mark.coords=this.formcoords(v2,v3,v4,v5);
			this.newdata.mark=this.formmark(null,null,null,this.newdata.mark.coords);//修改坐标
			this.newdata.values=this.formvalues(id);
			this.writeData(this.newdata);
		},
		
		getCoordsUpdateCount:function(id){//更新坐标时的通用代码，下面在更新坐标，角度时用到
			this.newdata.id=this.data.id.r;//修改data
			this.newdata.count=this.getNowIndex(id);//通过图片id找到图片所属的视频位置
			this.now_video=this.newdata.count.videocount;
			this.now_photo=this.getNowPhotoIndex(id);
			this.now_masker=this.newdata.count.maskercount;
			this.now_mark=this.getNowMarkIndex(id);
			
			this.newdata.count=this.formcount(this.now_video,this.now_photo,this.now_masker,this.now_mark);//修改计数
		},
		updateCoords:function(id,p1,p2,p3,p4){
			this.getCoordsUpdateCount(id);			
			this.newdata.mark.coords=this.formcoords(p1,p2,p3,p4);
			this.newdata.mark=this.formmark(null,null,null,this.newdata.mark.coords);//修改坐标
			this.updateData(this.newdata);
			console.log("update Coords");
		},
		deleteCoords:function(id){
			this.getCoordsUpdateCount(id);
			this.deleteData(this.newdata);
		},
		updatePose:function (id,pose){
			this.getCoordsUpdateCount(id);			
			this.newdata.mark=this.formmark(pose,null,null,null);//修改坐标
			this.updateData(this.newdata);
		},
		updateTrun:function (id,truncated){//更新是否截断
			this.getCoordsUpdateCount(id);			
			this.newdata.mark=this.formmark(null,truncated,null,null);
			this.updateData(this.newdata);
		},
		updatediff:function (id,difficult){//更新是否难以识别
			this.getCoordsUpdateCount(id);			
			this.newdata.mark=this.formmark(null,null,difficult,null);
			this.updateData(this.newdata);
		},		
		insertMasker:function(id,maskerName){//修改蒙版名字
			this.newdata.id=this.data.id.m;

			this.newdata.count=this.getNowIndex(id);//通过图片id找到图片所属的视频位置
			this.now_video=this.newdata.count.videocount;
			this.now_photo=this.getNowPhotoIndex(id);
			this.newdata.count.photoscount=this.now_photo;//通过图片id找到图片所属的视频位置
			this.now_masker=this.getMaxIndex(this.newdata);
			
			this.newdata.count=this.formcount(this.now_video,this.now_photo,this.now_masker);//修改计数
			this.newdata.values=this.formvalues(id,null,null,null,maskerName);
			this.writeData(this.newdata);
		},
		insertPhoto:function(id,timeStamp){//修改时间戳
			this.newdata.id=this.data.id.p;
			
			this.newdata.count=this.getNowIndex(id);//通过图片id找到图片所属的视频位置
			this.now_video=this.newdata.count.videocount;
			this.now_photo=this.getMaxIndex(this.newdata);
			
			this.newdata.count=this.formcount(this.now_video,this.now_photo);//修改计数
			this.newdata.values=this.formvalues(id,null,null,timeStamp);
			this.writeData(this.newdata);
		},
		deletePhoto:function(id){
			this.newdata.id=this.data.id.p;
			
			this.newdata.count=this.getNowIndex(id);//通过图片id找到图片所属的视频位置
			this.now_video=this.newdata.count.videocount;
			this.now_photo=this.getNowPhotoIndex(id);
			
			this.newdata.count=this.formcount(this.now_video,this.now_photo);//修改计数
			this.deleteData(this.newdata);
		},
		insertVideo:function(id){
			this.newdata.id=this.data.id.v;
			this.now_video=this.getMaxIndex(this.newdata);
			this.newdata.count=this.formcount(this.now_video);//修改计数
			this.newdata.values=this.formvalues(id,null,null);
			this.writeData(this.newdata);
		},
		updateVideo:function(id,formdata){//修改视频信息
			this.newdata.id=this.data.id.v;
			
			this.newdata.count=this.getNowIndex(id);//通过图片id找到图片所属的视频位置
			this.now_video=this.newdata.count.videocount;
			
			this.newdata.count=this.formcount(this.now_video);//修改计数
			this.newdata.values=this.formvalues(id,null,formdata);
			this.updateData(this.newdata);
		},
		prepareOriginalFileID:function(OriginalFileID){
			this.subdata.videolist[0].original_file_id=OriginalFileID;
		},
		prepareCoords:function(viewRatio){
			var temp;
			console.log(viewRatio);
			for(var i=0;i<this.subdata.videolist.length;i++){

				for(var j=0;j<this.subdata.videolist[i].photos.length;j++){

					for(var k=0;k<this.subdata.videolist[i].photos[j].masker.length;k++){

						for(var l=0;l<this.subdata.videolist[i].photos[j].masker[k].mark.length;l++){
							
							temp=this.subdata.videolist[i].photos[j].masker[k].mark[l].coords;
							temp.xmin=(temp.xmin/viewRatio).toFixed(0);
							temp.ymin=(temp.ymin/viewRatio).toFixed(0);
							temp.xmax=(temp.xmax/viewRatio).toFixed(0);
							temp.ymax=(temp.ymax/viewRatio).toFixed(0);
							console.log(temp);
						}
					}
				}
			}
		}
};
