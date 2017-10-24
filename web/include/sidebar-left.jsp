<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 左侧栏  -->
<aside class="main-sidebar">
	<section class="sidebar">
		<!-- 菜单搜索form -->
		<form id="navSearchForm" action="${ctx}/admin/home/searchMenu.action" method="get" class="sidebar-form">
			<div class="input-group">
				<input type="text" id="menuName" name="q" class="form-control" placeholder="Search..."/>
				<span class="input-group-btn">
					<button type="submit" name="search" id="search-btn" class="btn btn-flat">
						<i class="fa fa-search"></i>
					</button>
				</span>
			</div>
		</form>
		<!-- 树状导航菜单，后台数据动态生成 
		...
		-->
	</section>
</aside>