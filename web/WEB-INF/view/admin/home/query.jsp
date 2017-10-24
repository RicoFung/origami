<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/view-begin.jsp"%>
<!-- 主内容面板 -->
<div class="content-wrapper">
	<section class="content-header">
		<h1>首页</h1>
		<ol class="breadcrumb">
			<li class="active"><i class="fa fa-dashboard"></i> 首页</li>
		</ol>
	</section>
	<section class="content">
	</section>
</div>
<%@ include file="/include/view-end.jsp"%>
<script type="text/javascript">
/**********************************************************/
/* 全局函数 */
/**********************************************************/
$(function() {
	$chok.view.fn.selectSidebarMenu("0","首页");
});
</script>