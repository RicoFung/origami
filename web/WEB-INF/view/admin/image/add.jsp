<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/inc_header.jsp"%>
<!-- 主内容面板 -->
<div class="content-wrapper">
	<section class="content-header">
		<h1>${param.menuName}<small>新增</small></h1>
		<ol class="breadcrumb">
			<li><a href="${ctx}/index.jsp"><i class="fa fa-dashboard"></i> 首页</a></li>
			<li><a href="get.action?menuId=${param.menuId}&menuName=${param.menuName}">${param.menuName}</a></li>
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
				<form class="dataForm" enctype="multipart/form-data">
					<div class="form-group">
						<label for="category_id">所属类别：</label>
					 	<select class="form-control input-sm" id="category_id"></select>
					</div>
					<div class="form-group">
						<label for="model_id">所属模型：</label>
					 	<select class="form-control input-sm" id="model_id" cascadeid="category_id" validate validate-rule-required></select>
					</div>
					<input type="hidden" id="sort" value="0"/>
					<input id="myFile" name="myFile" type="file" multiple class="file-loading"/>
					<div id="kv-error-2" style="margin-top:10px;display:none"></div>
					<div id="kv-success-2" class="alert alert-success fade in" style="margin-top:10p;display:none"></div>
				</form>
			</div>
			<div class="box-footer">&nbsp;</div>
		</div>
	</section>
</div>
<%@ include file="/common/inc_footer.jsp"%>
<!-- ======================================================================================================= -->
<script type="text/javascript" src="/static/res/chok/js/chok.auth.js"></script>
<link rel="stylesheet" href="/static/res/bs/plugin/fileinput/css/fileinput.min.css"/>
<script type="text/javascript" src="/static/res/bs/plugin/fileinput/js/fileinput.min.js"></script>
<script type="text/javascript" src="/static/res/chok/js/chok.view.add.js"></script>
<script type="text/javascript">
$(function(){
	$chok.view.fn.selectSidebarMenu("${param.menuId}","${param.menuPermitId}","${param.menuName}");
	// 返回列表页
	$("#back").click(function(){
		location.href = "get.action?"+$chok.view.fn.getUrlParams("${queryParams}");
	});
	//
	$("#myFile").fileinput({
	    allowedFileExtensions : ['jpg','png','gif'],
	    uploadUrl: "add2.action", // server upload action
	    uploadExtraData:function()
	    {
	    	return {model_id:$("#model_id").val(), sort:$("#sort").val()};
	    },
	    uploadAsync: true,
	    minFileCount: 1,
	    maxFileCount: 10
	}).on('filepreupload', function(event, data, previewId, index, jqXHR) {
       // 进行自定义验证并返回如下所示的错误
       if (!$chok.validator.check()) {
           return {
               message: '表单信息不合法'//,
               //data: {key1: 'Key 1', detail1: 'Detail 1'}
           };
       }
	}).on('fileuploaderror', function(event, data, msg) {
 	    var form = data.form, files = data.files, extra = data.extra,
        response = data.response, reader = data.reader;
	    var out = '';
	    $.each(data.files, function(key, file) {
	        var fname = file.name;
	        out = out + '<li>' + '上传 # ' + (key + 1) + ' - '  +  fname + ' 失败.' + '</li>';
	    });
	    $('#kv-error-2 ul').append(out);
	    $('#kv-error-2').show(); 
	}).on('fileuploaded', function(event, data, previewId, index) {
        var form = data.form, files = data.files, extra = data.extra,
        response = data.response, reader = data.reader;
	    if(!response.success){
	    	alert("上传失败，"+response.msg);
	    	return;
	    }
	    alert("上传图片成功！");
		location.href = "get.action?"+$chok.view.fn.getUrlParams("${queryParams}");
	});
   /*  同步请求
   }).on('filebatchpreupload', function(event, data, id, index) {
	    $('#kv-success-2').html('<h4>Upload Status</h4><ul></ul>').hide();
	}).on('filebatchuploaderror', function(event, data, msg) {
 	    var form = data.form, files = data.files, extra = data.extra,
	        response = data.response, reader = data.reader;
	    var out = '';
	    $.each(data.files, function(key, file) {
	        var fname = file.name;
	        out = out + '<li>' + '上传 # ' + (key + 1) + ' - '  +  fname + ' 失败.' + '</li>';
	    });
	    $('#kv-error-2 ul').append(out);
	    $('#kv-error-2').show(); 
	}).on('filebatchuploadsuccess', function(event, data, previewId, index) {
 	   var form = data.form, files = data.files, extra = data.extra,
	        response = data.response, reader = data.reader;
	    var out = '';
	    $.each(data.files, function(key, file) {
	        var fname = file.name;
	        out = out + '<li>' + '上传 # ' + (key + 1) + ' - '  +  fname + ' 成功.' + '</li>';
	    });
	    $('#kv-success-2 ul').append(out);
	    $('#kv-success-2').show();
	    alert("上传图片成功！");
		location.href = "get.action?"+$chok.view.fn.getUrlParams("${queryParams}");
	}); */
});

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
			fk:"category_id"
		});
};
</script>
