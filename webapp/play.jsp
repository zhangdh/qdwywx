<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>录音播放</title>
</head>	
<% 
 String url = request.getParameter("url");
%>
<body> 
    <object id="Player" width=500 height=96 classid="CLSID:6BF52A52-394A-11D3-B153-00C04F79FAA6">
                    <param name="URL" value="">
                    <param name="autoStart" value="1">
                    <param name="balance" value="0">
                    <param name="baseURL" value>
                    <param name="captioningID" value>
                    <param name="currentPosition" value="0">
                    <param name="currentMarker" value="0">
                    <param name="defaultFrame" value="0">    
                    <param name="enabled" value="1">
                    <param name="enableErrorDialogs" value="0">
                    <param name="enableContextMenu" value="1">              
                    <param name="fullScreen" value="0">     
                    <param name="invokeURLs" value="1">
                    <param name="mute" value="0">
                    <param name="playCount" value="1">  
                    <param name="rate" value="1">
                    <param name="SAMIStyle" value>
                    <param name="SAMILang" value>
                    <param name="SAMIFilename" value>
                    <param name="stretchToFit" value="0">
                    <param name="uiMode" value="full">
                    <param name="volume" value="100">
                    <param name="windowlessVideo" value="0">
</object>
<script>
	var url = '<%=url %>' ;
	var hostIp = window.location.host;
	Player.URL="http://"+hostIp+url;
</script>                                                       
</body>                                                                   
                                                                          
</html>                                                                   
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          
                                                                          