<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<!DOCTYPE html>  
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">  
<title>人脸注册</title>  
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>  
<script type="text/javascript" src="js/index.js"></script>  
<script type="text/javascript">  
            function CatchCode() {  
          var canvans = document.getElementById("canvas");  
          var video = document.getElementById("video");  
          var context = canvas.getContext("2d");  
   
          canvas.width = video.videoWidth;  
          canvas.height = video.videoHeight;  
          context.drawImage(video,0,0);  
            
          var imgData = canvans.toDataURL();  
          var imgData1 = imgData.substring(22);  
              
          var username = $("#username").val();  
          $.ajax({  
              type: "post",  
              url: "Main?tag=reg",  
              data: {"img":imgData1,"username":username,"password":password},  
              success: function(data){  
                  alert(data);  
              },error:function(msg){  
                  alert("错误");  
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
    <h2>注册</h2>  
          用户名：  
    <input type="text" name="username" id="username" /> 
    <br> 
          密码：  
    <input type="password" name="password" id="password" />  
    <br />  
    <div id="support"></div>  
    <div id="contentHolder">  
        <video id="video" width="300" height="225"  
            style="border-radius:16px" autoplay></video>  
  		<br>
        <canvas style="border-radius:16px;width:300px;height:225px;"  
            id="canvas"></canvas>  
    </div>  
    <br />  
    <input type="button" value="确认" id="snap" />  
    <br />  
    <a href="login.jsp">点击登陆</a>  
</body>  
</html>  