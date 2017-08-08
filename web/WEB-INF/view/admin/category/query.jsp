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
		<button type="button" class="btn btn-default" id="bar_btn_query" pbtnId="pbtn_query2" data-toggle="modal" data-target="#modal_form_query"><i class="glyphicon glyphicon-search"></i></button>
		<button type="button" class="btn btn-default" id="bar_btn_imp" ><i class="glyphicon glyphicon-upload"></i></button>
		<button type="button" class="btn btn-default" id="bar_btn_exp" ><i class="glyphicon glyphicon-download"></i></button>
		</div>
		<!-- data list
		======================================================================================================= -->
		<table id="tb_list"></table>
		<!-- context menu
		======================================================================================================= -->
		<ul id="tb_ctx_menu" class="dropdown-menu">
		    <li data-item="upd" class="upd" pbtnId="pbtn_upd"><a><i class="glyphicon glyphicon-edit"></i></a></li>
		    <li data-item="get" class="get" pbtnId="pbtn_get"><a><i class="glyphicon glyphicon-info-sign"></i></a></li>
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
	<div class="modal-body">
		<div class="form-group">
			 <label for="f_name">名称：</label><input type="text" class="form-control input-sm" id="f_name"/>
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
<script type="text/javascript" src="/static/res/chok/js/chok.view.query.js"></script>
<script type="text/javascript">
/**********************************************************/
/* 全局函数 */
/**********************************************************/
$(function() {
	$chok.view.fn.selectSidebarMenu("${param.menuId}","${param.menuPermitId}","${param.menuName}");
	$chok.view.query.init.toolbar();
	$chok.view.query.init.modalFormQuery();
	$chok.view.query.init.table("${queryParams.f_page}","${queryParams.f_pageSize}");
	$chok.auth.btn($chok.view.menuPermitId,$g_btnJson);
});
/**********************************************************/
/* 初始化配置 */
/**********************************************************/
$chok.view.query.config.setPreFormParams = function(){
	$("#f_name").val(typeof("${queryParams.f_name}")=="undefined"?"":"${queryParams.f_name}");
};
$chok.view.query.config.formParams = function(p){
	p.name = $("#f_name").val();
    return p;
};
$chok.view.query.config.urlParams = function(){
	return {f_name : $("#f_name").val()};
};
$chok.view.query.config.tableColumns = 
[
    {title:'ID', field:'m.id', align:'center', valign:'middle', sortable:true},
    {title:'名称', field:'m.name', align:'center', valign:'middle', sortable:true, 
    	editable:
    	{
    		type:'text',
   			title:'名称',
	    	validate: function(value){
	            return $chok.validator.checkEditable("required", null, value, null);
	    	}
    	}
    },
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
$chok.view.query.config.showMultiSort = true;
$chok.view.query.config.sortPriority = [{"sortName":"m.sort", "sortOrder":"asc"}];
$chok.view.query.callback.delRows = function(){
};
$chok.view.query.callback.onLoadSuccess = function(){
	$chok.auth.btn($chok.view.menuPermitId,$g_btnJson);
};
/* OVERWRITE-初始化工具栏 */
$chok.view.query.init.toolbar = function(){
	$("#bar_btn_imp").click(function(){
		location.href = "imp.action?"+$chok.view.query.fn.getUrlParams();
	});
	$("#bar_btn_exp").click(function(){
		$chok.view.query.fn.exp('exp.action', 'test','test_title', 'ID,名称,排序', 'id,name,sort');
	});
	$("#bar_btn_add").click(function(){
		location.href = "add.action?"+$chok.view.query.fn.getUrlParams();
	});
	$("#bar_btn_del").click(function(){
		if($chok.view.query.fn.getIdSelections().length<1) {
			alert("没选择");
			return;
		}
		if(!confirm("确认删除？")) return;
		$.post("del.action",{id:$chok.view.query.fn.getIdSelections()},function(result){
	        $chok.view.query.callback.delRows(result); // 删除行回调
	        if(!result.success) {
	        	alert(result.msg);
	        	return;
	        }
	        $("#tb_list").bootstrapTable('refresh'); // 刷新table
		});
	});
};
</script>