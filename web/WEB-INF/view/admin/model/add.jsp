<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/inc_header.jsp"%>
<!-- 主内容面板 -->
<div class="content-wrapper">
	<section class="content-header">
		<h1>${param.menuName}<small>新增</small></h1>
		<ol class="breadcrumb">
			<li><a href="${ctx}/index.jsp"><i class="fa fa-dashboard"></i> 首页</a></li>
			<li><a href="query.action?menuId=${param.menuId}&menuName=${param.menuName}">${param.menuName}</a></li>
			<li class="active">新增</li>
		</ol>
	</section>
	<section class="content">
		<div class="box box-default">
			<div class="box-header with-border">
				<h3 class="box-title"><small><i class="glyphicon glyphicon-plus"></i></small></h3>
				<div class="box-tools pull-right">
					<button type="button" class="btn btn-box-tool" id="back"><i class="glyphicon glyphicon-arrow-left"></i></button>
				</div>
			</div>
			<div class="box-body">
				<form class="dataForm" id="dataForm" action="add2.action" method="post">
					<div class="form-group">
						<label for="category_id">所属分类：</label>
					 	<select class="form-control input-sm" id="category_id" name="m['category_id']" validate validate-rule-required>
							<option value="">请选择</option>
							<c:forEach var="c" items="${categorys}">
							<option value="${c.id}">${c.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group"><label class="control-label" for="name">名称：</label><input type="text" class="form-control input-sm" id="name" name="m['name']" value="" validate validate-rule-required/></div>
					<div class="form-group"><label class="control-label" for="sort">排序：</label><input type="text" class="form-control input-sm" id="sort" name="m['sort']" value="0" validate validate-rule-inputType="integer"/></div>
				</form>
			</div>
			<div class="box-footer">
				<button type="submit" class="btn btn-block btn-success btn-flat pull-right" id="dataFormSave"><i class="glyphicon glyphicon-floppy-save"></i></button>
			</div>
		</div>
	</section>
</div>
<%@ include file="/common/inc_footer.jsp"%>
<!-- ======================================================================================================= -->
<script type="text/javascript" src="/static/res/chok/js/chok.auth.js"></script>
<script type="text/javascript" src="/static/res/chok/js/chok.view.add.js"></script>
<script type="text/javascript">
/**********************************************************/
/* 保存后回调函数 */
/**********************************************************/
$chok.form.callback = function(){
	if($chok.result.type == 1){
 		location.href = "query.action?"+$chok.view.fn.getUrlParams("${queryParams}");
	}
};
/**********************************************************/
/* 全局函数 */
/**********************************************************/
$(function(){
	$chok.view.fn.selectSidebarMenu("${param.menuId}","${param.menuPermitId}","${param.menuName}");
	$chok.auth.btn($chok.view.menuPermitId,$g_btnJson);
	// 返回列表页
	$("#back").click(function(){
		location.href = "query.action?"+$chok.view.fn.getUrlParams("${queryParams}");
	});
});
</script>