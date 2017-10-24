<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- CAS --%>
<header class="main-header">
	<!-- Logo -->
	<a href="javascript:void(0);return false;" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels -->
		<span class="logo-mini"><b>O</b>App</span> <!-- logo for regular state and mobile devices -->
		<span class="logo-lg"><b>Origami</b>App</span>
	</a>
	<!-- 顶部导航栏 -->
	<nav class="navbar navbar-static-top">
		<!-- 左侧栏切换按钮-->
		<a href="javascript:void(0);return false;" class="sidebar-toggle" data-toggle="offcanvas" role="button"></a>
		<!-- 导航下拉菜单 -->
		<div class="navbar-custom-menu">
			<ul class="nav navbar-nav">
				<c:choose>
					<c:when test="${LoginUser==null}">
						<li><a href="https://localhost:18443/cas/login?service=http://localhost:9090/sbframework"><i class="glyphicon glyphicon-log-in"></i>登录</a></li>
					</c:when>
					<c:otherwise>
						<li class="dropdown user user-menu notifications-menu">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">
								<img src="${statics}/res/AdminLTE/dist/img/user2-160x160.jpg" class="user-image" alt="User Image">
								<span class="hidden-xs"> <%=account%></span><span class=" fa fa-angle-down"></span>
							</a>
							<ul class="dropdown-menu" style="height: 125px;">
								<li>
									<ul id="user-dropdown-menu" class="menu">
										<li menuId="userinfo"><a href="#"><i class="fa fa-user text-aqua"></i> <span>个人资料</span></a></li>
										<li menuId="password"><a href="${ctx}/cas/password.action"><i class="glyphicon glyphicon-lock text-aqua"></i><span>修改密码</span></a></li>
										<li menuId="logout"><a href="${ctx}/cas/logout.action"><i class="glyphicon glyphicon-log-out text-red"></i><span>登出</span></a></li>
									</ul>
								</li>
							</ul>
						</li>
					</c:otherwise>
				</c:choose>
				<!-- 右侧栏切换按钮 -->
				<li><a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a></li>
			</ul>
		</div>
	</nav>
</header>

<%-- SSO
<header class="main-header">
	<!-- Logo -->
	<a href="javascript:void(0);return false;" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels -->
		<span class="logo-mini"><b>O</b>App</span> <!-- logo for regular state and mobile devices -->
		<span class="logo-lg"><b>Origami</b>App</span>
	</a>
	<!-- 顶部导航栏 -->
	<nav class="navbar navbar-static-top">
		<!-- 左侧栏切换按钮-->
		<a href="javascript:void(0);return false;" class="sidebar-toggle" data-toggle="offcanvas" role="button"></a>
		<!-- 导航下拉菜单 -->
		<div class="navbar-custom-menu">
			<ul class="nav navbar-nav">
				<c:choose>
					<c:when test="${LoginUser==null}"> 
						<li><a href="http://localhost:8585/sso/auth/login.action?service=http://localhost:9090/sbframework"><i class="glyphicon glyphicon-log-in"></i>登录</a></li>
					</c:when>
					<c:otherwise>
						<li class="dropdown user user-menu notifications-menu">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">
								<img src="${ctx}/res/AdminLTE/dist/img/user2-160x160.jpg" class="user-image" alt="User Image">
								<span class="hidden-xs"> <%=account%></span><span class=" fa fa-angle-down"></span>
							</a>
							<ul class="dropdown-menu" style="height: 125px;">
								<li>
									<ul id="user-dropdown-menu" class="menu">
										<li menuId="userinfo"><a href="#"><i class="fa fa-user text-aqua"></i> <span>个人资料</span></a></li>
										<li menuId="password"><a href="${ctx}/sso/password.action"><i class="glyphicon glyphicon-lock text-aqua"></i><span>修改密码</span></a></li>
										<li menuId="logout"><a href="${ctx}/sso/logout.action"><i class="glyphicon glyphicon-log-out text-red"></i><span>登出</span></a></li>
									</ul>
								</li>
							</ul>
						</li>
					</c:otherwise>
				</c:choose>
				<!-- 右侧栏切换按钮 -->
				<li><a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a></li>
			</ul>
		</div>
	</nav>
</header>
 --%>