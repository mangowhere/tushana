window.onload=function(){
	//页面布局设置	



	$(".fullPage").height($(window).height());
	var fullPageHeight = $(".fullPage").height();
//	$(".content").css("padding-top",(fullPageHeight-480)/2);

	//P2注册页面切换
	$(".p2_part2 .registWay").click(
	  function(){
		  $(".p2_part").removeClass('rotateLast');
		  $(".p2_part").addClass('rotateFirst');
		  var t1=setTimeout(a,500);

		  function a(){
			 $(".p2_part").hide();
			 $(".p2_part_cover").show();
			 $(".p2_part_cover").removeClass('rotateFirst');
			 $(".p2_part_cover").addClass('rotateLast');
		  }

	});
	  $(".p2_part_cover .registWay").click(
	  function(){

		  $(".p2_part_cover").removeClass('rotateLast');
		  $(".p2_part_cover").addClass('rotateFirst');	  
		  var t2=setTimeout(b,500);		  
		  function b(){
			  $(".p2_part_cover").hide();
			 $(".p2_part").show();
			 $(".p2_part").removeClass('rotateFirst');
			 $(".p2_part").addClass('rotateLast');
		  }
	  });

	  //P2表单提交
	 $(".p2_part .regist").click(function(){
		$(".p2 #registForm").submit();
	 });

	 $(".p2_part_cover .regist").click(function(){
		$(".p2 #registForm_cover").submit();
	 });
	 //P3表单提交
	 $(".p3_part2 .login").click(function(){
		$(".p3 #loginForm").submit();
	 });


	 //P4布局
	$(".p4").height(fullPageHeight);

	 //P5图片上传
/*
	$(".p5_part1 .uploadButton").click(function(){
		 var p5_imgWidth=$(".p5_part1 .imgBackground img").width();
		 alert(p5_imgWidth);
		 var p5_imgHeight=$(".p5_part1 .imgBackground img").height();
		 alert(p5_imgHeight);
		 if(p5_imgWidth-p5_imgHeight>0){
			$(".p5_part1 .imgBackground img").width("120px");		 
		}
		else{
			$(".p5_part1 .imgBackground img").height("120px");	
		}
	});
	*/

	//P7布局
	var play_width=$(window).width();
	var play1_height=$(".p7_part1 .play1").height();
	var palyInfo_width=play_width-play1_height;
	$(".p7 .play1Info").width(palyInfo_width);
	$(".p7 .play2Info").width(palyInfo_width);

	//P7图片处理
	var  mainPic1_width=$(".mainPic1").width();
	var  mainPic1_height=$(".mainPic1").height();
	var	 mainPic1_radio=mainPic1_width/mainPic1_height;
	if(mainPic1_width>mainPic1_height)
	{
		$(".play1ImgBg img").width("100%");
	}
	else
	{
		$(".play1ImgBg img").height("100%");
	}

	var  mainPic2_width=$(".mainPic2").width();
	var  mainPic2_height=$(".mainPic2").height();
	var	 mainPic2_radio=mainPic2_width/mainPic2_height;
	if(mainPic2_width>mainPic2_height)
	{
		$(".play2ImgBg img").width("100%");
	}
	else
	{
		$(".play2ImgBg img").height("100%");
	}

}

