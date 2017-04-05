<%@ page import="chok.util.PropertiesUtil" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String ctx = request.getContextPath();
String imagePath = PropertiesUtil.getImagePath();
request.setAttribute("ctx", ctx);
request.setAttribute("imagePath", imagePath);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<!-- 
 -->
<!-- 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
 -->