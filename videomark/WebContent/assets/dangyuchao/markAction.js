action=function(){};
action.prototype={
		ScaleTrans:function(){
			var self=this;
			$(".scale_trans").each(function(){
				var son=this;
				var ipic;
				var src=this.src;
				var id=this.id;
				var startWidth=this.width;
				var startHeight=this.height;
				var parent=$(this).parent()[0];//以上都是为了记录每个图片的信息，用于更新
				parent.style.overflow="hidden";
				
				var currentScaleRate=1;//当前缩放比例
				var lastRate=1;//在放大中需要上一次缩放比例
				var n=0;//为了在下面的数组指明序数
				var lastPageX=[];//记录在放大中所有的缩放点击点，为了在缩小中还原，相当于下面的lastoffsetX的集合
				var lastPageY=[];
				var changeScaleRate=0.3;//放大缩小每次变化的速率，（加上或减去这个数）
				
				var lastoffsetX=0;//为了达到连续的缩放效果，记录上一次的点击点
				var lastoffsetY=0;
				
				var abs_position=self.getAbsolutePosition(this);
				var offsetX=abs_position.offsetLeft;//图片模块绝对坐标
				var offsetY=abs_position.offsetTop;
				var pageX;
				var pageY;

				var lastX=0;
				var lastY=0;
				var startX=0;
				var startY=0;
				var lastoffsetX=0;//为了达到连续的缩放效果，记录上一次的点击点
				var lastoffsetY=0;
				
				var transMouseDown=false;
				$(this).bind({
					mousedown:function(){
				    	event.preventDefault();
				    	
			    		startX=event.pageX;
				    	startY=event.pageY;
				    	this.style.marginLeft =lastX+"px";
				    	this.style.marginTop =lastY+"px";
				    	this.style.cursor="move";
				    	transMouseDown=true;
				    	isFisrtDrag=false;
				    	$("body").bind({		
						    mousemove:function(){
						    	if(transMouseDown){
						    		son.style.marginLeft =(event.pageX-startX+lastX)+"px";
						    		son.style.marginTop =(event.pageY-startY+lastY)+"px";
						    	}	
						    },  
						    mouseup:function(){
						    	if(transMouseDown){
						    		var tempx=(event.pageX-startX+lastX);
							    	var tempy=(event.pageY-startY+lastY);					    						    	
							    	lastX=tempx;
							    	lastY=tempy;				    	
							    	son.style.cursor="default";
							    	transMouseDown=false;
						    	}
						   }
				    	});
					}
				});
				
				parent.onmousewheel=function(){
					event.preventDefault();//先阻止页面滚动的默认动作，不让父层页面上下滚动
					pageX=event.pageX-offsetX;
					pageY=event.pageY-offsetY;
					lastRate=currentScaleRate;	
					if(event.wheelDelta<0){//向下滚，缩小
						if(currentScaleRate>1){
							currentScaleRate=parseFloat(currentScaleRate);
							currentScaleRate=(currentScaleRate-changeScaleRate).toFixed(1);
							ipic=$("#"+id)[0];
							parent.removeChild($("#"+id)[0]);
							ipic.style.overflow="hidden";
							ipic.width = (startWidth*currentScaleRate).toFixed(0);
							ipic.height = (startHeight*currentScaleRate).toFixed(0);			
							lastoffsetX=((currentScaleRate*lastoffsetX-changeScaleRate*lastPageX[n-1])/lastRate).toFixed(0);
							lastoffsetY=((currentScaleRate*lastoffsetY-changeScaleRate*lastPageY[n-1])/lastRate).toFixed(0);
							lastX=(-lastoffsetX);
							lastY=(-lastoffsetY);
							ipic.style.marginLeft =(-lastoffsetX)+"px";
							ipic.style.marginTop =(-lastoffsetY)+"px";
							lastRate=currentScaleRate;
							parent.appendChild(ipic);
							n--;
						}
						
					}else{//向上滚，放大
						currentScaleRate=parseFloat(currentScaleRate);
						currentScaleRate=(currentScaleRate+changeScaleRate).toFixed(1);
						ipic=$("#"+id)[0];
						parent.removeChild($("#"+id)[0]);
						ipic.style.overflow="hidden";
						ipic.width = (startWidth*currentScaleRate).toFixed(0);
						ipic.height = (startHeight*currentScaleRate).toFixed(0);
						lastoffsetX=((changeScaleRate*pageX+currentScaleRate*lastoffsetX)/lastRate).toFixed(0);
						lastoffsetY=((changeScaleRate*pageY+currentScaleRate*lastoffsetY)/lastRate).toFixed(0);
						lastX=(-lastoffsetX);
						lastY=(-lastoffsetY);	
						ipic.style.marginLeft =(-lastoffsetX)+"px";
						ipic.style.marginTop =(-lastoffsetY)+"px";
						lastRate=currentScaleRate;
						parent.appendChild(ipic);			
						lastPageX[n]=pageX;
						lastPageY[n]=pageY;
						n++;		
					}
				};
			});		
		},
		getAbsolutePosition:function(obj){
			var offset={
					top:null,
					left:null,
					width:null,
					height:null	
				};
				offset.top= obj.offsetTop;  
				offset.left= obj.offsetLeft;  
				offset.width= obj.offsetWidth; 
				offset.height= obj.offsetHeight;  
		        while (obj = obj.offsetParent) {
		        	offset.top += obj.offsetTop;  
		        	offset.left += obj.offsetLeft;  
		        }  
		          
		        return offset;
		},
		move:function(markID,moveClassName,mark,dataDC){//dataDC可以传入null
			var self=this;
			$("."+moveClassName).each(function(){//zindex=1
				if(markID==this.id){
					var son_position;
					var son=this;
					var son_width=this.offsetWidth;//自身的宽度
					var son_height=this.offsetHeight;
					var son_left;
					var son_right;
					var son_top;
					var son_bottom;
					
					var parent=$(this).parent()[0];//一层parent
					var parent_position;
					var border_left;//爸爸的限制
					var border_width;
					var border_top;
					var border_height;

					var drag_startx=0;
					var drag_starty=0;
					var drag_startLeft=0;
					var drag_startTop=0;
					var drag_left;
					var drag_top;
					var canDrag=false;
					
					$(this).bind({
					mousedown:function(){//默认先监听儿子，再监听爸爸
						console.log("按下去");
						event.preventDefault();
						event.stopPropagation();//阻止按下去的时候蒙版切换为画图模式
						son_position=self.getAbsolutePosition(this);
						son_left=son_position.left;//爸爸的限制
						son_right=son_position.left+son_position.width;
						son_top=son_position.top;
						son_bottom=son_position.top+son_position.height;
						
						parent_position=self.getAbsolutePosition(parent);
						border_left=parent_position.left;//爸爸的限制
						border_width=parent_position.width;
						border_top=parent_position.top;
						border_height=parent_position.height;
						$("."+moveClassName).each(function(){
							this.style.zIndex=1;
						});
			    		this.style.zIndex=2;
						drag_startx=event.pageX;
						drag_starty=event.pageY;
						drag_startLeft=son_left-border_left;
						drag_startTop=son_top-border_top;
						drag_left=son.offsetLeft;
						drag_top=son.offsetTop;
						canDrag=true;
						$("body").bind({		
						    mousemove:function(){
						    	if(canDrag){  		
						    		drag_left=event.pageX-drag_startx+drag_startLeft;
									if(drag_left<=0){
										drag_left=0;
									}else if(drag_left+son_width>border_width){
										drag_left=border_width-son_width;	
									}
									
									drag_top=event.pageY-drag_starty+drag_startTop;
									if(drag_top<=0){
										drag_top=0;
									}else if(drag_top+son_height>border_height){
										drag_top=border_height-son_height;
									}
									son.style.left=(drag_left)+"px";
									son.style.top=(drag_top)+"px";  		
						    	}
						    },  
						    mouseup:function(){
						    	if(canDrag){
						    		if(mark!=null){
						    			mark.coords.left=drag_left;
							    		mark.coords.top=drag_top;
							    		mark.coords.right=drag_left+son_width;
							    		mark.coords.bottom=drag_top+son_height;	
						    		}
						    		if(dataDC!=null){
						    			dataDC.updateCoords(markID,drag_left,drag_top,drag_left+son_width,drag_top+son_height);//插入数据
						    		}					    		
									canDrag=false;
						    	}
						    },
						});
				    	}			    
					});
				}    
			});
		},
		dragCorner:function(){},
		scale:function(){},
		roate:function(){}
		
};

