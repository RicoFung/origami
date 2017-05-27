<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/inc_header.jsp"%>
<!-- 主内容面板 -->
<div class="content-wrapper">
	<section class="content-header">
		<h1>${param.menuName}<small>明细</small></h1>
		<ol class="breadcrumb">
			<li><a href="${ctx}/index.jsp"><i class="fa fa-dashboard"></i> 首页</a></li>
			<li><a href="query.action?menuId=${param.menuId}&menuName=${param.menuName}">${param.menuName}</a></li>
			<li class="active">明细</li>
		</ol>
	</section>
	<section class="content">
		<div class="box box-default">
			<div class="box-header with-border">
				<h3 class="box-title"><small><i class="glyphicon glyphicon-info-sign"></i></small></h3>
				<div class="box-tools pull-right">
					<button type="button" class="btn btn-box-tool" id="back"><i class="glyphicon glyphicon-arrow-left"></i></button>
				</div>
			</div>
			<div class="box-body">
				<form class="dataForm" id="dataForm" role="form">
					<div class="form-group">
						<label for="category_id">所属分类：</label>
					 	<select class="form-control input-sm" id="category_id" name="m['category_id']" disabled="disabled">
							<option value="">请选择</option>
							<c:forEach var="c" items="${categorys}">
							<option value="${c.id}">${c.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group"><label class="control-label" for="name">名称：</label><input type="text" class="form-control input-sm" id="name" name="m['name']" value="${po.m.name}" readonly="readonly"/></div>
					<div class="form-group"><label class="control-label" for="sort">排序：</label><input type="text" class="form-control input-sm" id="sort" name="m['sort']" value="${po.m.sort}" readonly="readonly"/></div>
					<input type="hidden" name="m['id']" value="${po.m.id}" readonly="readonly">
				</form>
			</div>
			<div class="box-footer">
			&nbsp;
			</div>
		</div>
	</section>
</div>
<%@ include file="/common/inc_footer.jsp"%>
<script type="text/javascript" src="/static/res/chok/js/chok.view.get.js"></script>
<script type="text/javascript">
/**********************************************************/
/* 全局函数 */
/**********************************************************/
$(function(){
	$chok.view.fn.selectSidebarMenu("${param.menuId}","${param.menuPermitId}","${param.menuName}");
	// 返回列表页
	$("#back").click(function(){
		location.href = "query.action?"+$chok.view.fn.getUrlParams("${queryParams}");
	});
	 // 所属类别赋值
	$("#category_id").val("${po.m.category_id}");
});
</script>