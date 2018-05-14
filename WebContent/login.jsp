<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  
<!DOCTYPE html>   
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">   
<title>登陆</title>  
   
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>  
<script type="text/javascript" src="js/index.js"></script>  
<script type="text/javascript">  
          function CatchCode() {  
          //实际运用可不写，测试代 ， 为单击拍照按钮就获取了当前图像，有其他用途  
          var canvans = document.getElementById("canvas");  
          var video = document.getElementById("video");  
          var context = canvas.getContext("2d");  
   
          canvas.width = video.videoWidth;  
          canvas.height = video.videoHeight;  
          context.drawImage(video,0,0);  
            
          var imgData = canvans.toDataURL();  
            //获取图像在前端截取22位以后的字符串作为图像数据  
            var imgData1 = imgData.substring(22);  
              
            var username = $("#username").val();  
            $.ajax({  
                    type: "post",  
                    url: "Main?tag=login",  
                    data: {"img":imgData1,"username":username},  
                    success: function(data){  
                        alert(data);  
                    },error:function(msg){  
                        alert("检测到不是你的脸");  
                    }  
            });               
      }          
</script>  
<style>
	body{
		text-align:center;
	}
</style>
</head>  
  
<body>  
    <h2>登陆</h2>  
	 输入用户名:  
    <input type="text" name="username" id="username" />  
    <br /> 把脸靠过来：  
    <div id="support"></div>
    <div id="contentHolder">  
    	<video id="video" width="300" height="225"  
            style="border-radius:16px;" autoplay></video>    
            <br>         
        <canvas style="border-radius:16px;width:300px;height:225px;"  
            id="canvas"></canvas>  
    </div>  
    <br>  
    <input type="button" value="准备好了就确认" id="snap" />  
</body>  
</html>  