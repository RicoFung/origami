<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/inc_header.jsp"%>
<!-- 主内容面板 -->
<div class="content-wrapper">
	<section class="content-header">
		<h1>${param.menuName}</h1>
		<ol class="breadcrumb">
			<li><a href="${ctx}/index.jsp"><i class="fa fa-dashboard"></i> 首页</a></li>
			<li class="active">${param.menuName}</li>
		</ol>
	</section>
	<section class="content">
		<div class="row">
		<div class="col-md-12">
		<div class="box box-default">
		<div class="box-header with-border">
			<h3 class="box-title"><small><i class="glyphicon glyphicon-th-list"></i></small></h3>
		</div>
		<div class="box-body">
			<!-- toolbar
			======================================================================================================= -->
			<div id="toolbar">
			<button type="button" class="btn btn-default" id="bar_btn_add" pbtnId="pbtn_add"><i class="glyphicon glyphicon-plus"></i></button>
			<button type="button" class="btn btn-default" id="bar_btn_del" pbtnId="pbtn_del"><i class="glyphicon glyphicon-remove"></i></button>
			<button type="button" class="btn btn-default" id="bar_btn_query" pbtnId="pbtn_query" data-toggle="modal" data-target="#modal_form_query"><i class="glyphicon glyphicon-search"></i></button>
			</div>
			<!-- data list
			======================================================================================================= -->
			<table id="tb_list"></table>
			<!-- context menu
			======================================================================================================= -->
			<ul id="tb_ctx_menu" class="dropdown-menu">
			    <li data-item="getById" class="getById" pbtnId="pbtn_getById"><a><i class="glyphicon glyphicon-info-sign"></i></a></li>
			</ul>
		</div>
		</div>
		</div>
		</div>
	</section>
</div>
<!-- query form modal
======================================================================================================= -->
<form id="form_query">
<div id="modal_form_query" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="modal_label" aria-hidden="true">
<div class="modal-dialog">
<div class="modal-content">
	<div class="modal-header">
	   <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	   <h4 class="modal-title" id="modal_label">筛选条件</h4>
	</div>
	<div class="modal-body form-body">
		<div class="form-group">
			<label for="category_id">所属类别：</label>
		 	<select class="form-control input-sm" id="category_id"></select>
		</div>
		<div class="form-group">
			<label for="model_id">所属模型：</label>
		 	<select class="form-control input-sm" id="model_id" cascadeid="category_id"></select>
		</div>
	</div>
	<div class="modal-footer">
		<button type="reset" class="btn btn-default"><i class="glyphicon glyphicon-repeat"></i></button>
		<button type="button" class="btn btn-primary" id="form_query_btn"><i class="glyphicon glyphicon-ok"></i></button>
	</div>
</div><!-- /.modal-content -->
</div><!-- /.modal -->
</div>
</form>
<%@ include file="/common/inc_footer.jsp"%>
<!-- ======================================================================================================= -->
<script type="text/javascript" src="/static/res/chok/js/chok.auth.js"></script>
<script type="text/javascript" src="/static/res/chok/js/chok.view.get.js"></script>
<script type="text/javascript">
/**********************************************************/
/* 全局函数 */
/**********************************************************/
$(function() {
	$chok.view.fn.selectSidebarMenu("${param.menuId}","${param.menuPermitId}","${param.menuName}");
	$chok.view.get.init.toolbar();
	$chok.view.get.init.modalFormQuery();
	$chok.view.get.init.table("${queryParams.f_page}","${queryParams.f_pageSize}");
	$chok.auth.btn($chok.view.menuPermitId,$g_btnJson);
});
/**********************************************************/
/* 初始化配置 */
/**********************************************************/
$chok.view.get.config.setPreFormParams = function(){
	$("#f_category_id").val(typeof("${queryParams.f_category_id}")=="undefined"?"":"${queryParams.f_category_id}");
	$("#f_model_id").val(typeof("${queryParams.f_model_id}")=="undefined"?"":"${queryParams.f_model_id}");
};
$chok.view.get.config.formParams = function(p){
	p.category_id = $("#f_category_id").val();
	p.model_id = $("#f_model_id").val();
    return p;
};
$chok.view.get.config.urlParams = function(){
	return {f_category_id : $("#f_category_id").val(),
			f_model_id : $("#f_model_id").val()};
};
$chok.view.get.config.tableColumns = 
[
    {title:'ID', field:'m.id', align:'center', valign:'middle', sortable:true},
    {title:'图片路径', field:'m.url', align:'center', valign:'middle', sortable:true},
    {title:'所属模型_ID', field:'m.model_id', align:'center', valign:'middle', sortable:true},
    {title:'所属模型', field:'m.model_name', align:'center', valign:'middle', sortable:true},
    {title:'所属分类_ID', field:'m.category_id', align:'center', valign:'middle', sortable:true},
    {title:'所属分类', field:'m.category_name', align:'center', valign:'middle', sortable:true},
    {title:'排序', field:'m.sort', align:'center', valign:'middle', sortable:true, 
    	editable:
    	{
	    	type:'text',
	    	title:'排序',
	    	validate: function(value){
	            return $chok.validator.checkEditable("number", null, value, null);
	    	}
    	}
    }
];
$chok.view.get.callback.delRows = function(){
};
$chok.view.get.callback.onLoadSuccess = function(){
	$chok.auth.btn($chok.view.menuPermitId,$g_btnJson);
};
/**********************************************************/
/* 自定义配置 */
/**********************************************************/
$chok.view.fn.customize = function(){
	var category_select = 
		$("#category_id").DropDownSelect({
			url:$ctx+"/dict/getCategorys.action",
			callback:{
				afterload:function(){
					model_select.reload();
				}
			}
		});
	var model_select = 
		$("#model_id").DropDownSelect({
			url:$ctx+"/dict/getModels.action",
			cascadeid:"category_id",
			fk:"model_id"
		});
};
</script>