<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="chok.util.PropertiesUtil" %>
<script type="text/javascript" src="/static/res/jquery/jquery.js"></script>
<script type="text/javascript" src="/static/res/jquery/jquery.form.js"></script>
<script type="text/javascript" src="/static/res/jquery/jquery-ui.min.js"></script>
<script type="text/javascript" src="/static/res/jquery/jquery.customize.resize.js"></script>
<script type="text/javascript" src="/static/res/bs/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/static/res/bs/js/bootstrap-table.js"></script>
<script type="text/javascript" src="/static/res/bs/js/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="/static/res/bs/js/bootstrap-table-fixed-columns.js"></script>
<script type="text/javascript" src="/static/res/bs/js/bootstrap-editable.min.js"></script>
<script type="text/javascript" src="/static/res/bs/js/bootstrap-table-editable.min.js"></script>
<script type="text/javascript" src="/static/res/bs/js/bootstrap-table-contextmenu.min.js"></script>
<script type="text/javascript" src="/static/res/chok/js/chok.base.js"></script>
<script type="text/javascript" src="/static/res/chok/js/chok.validator.js"></script>
<script type="text/javascript" src="/static/res/chok/js/chok.form.js"></script>
<script type="text/javascript" src="/static/res/chok/js/chok.view.js"></script>
<script type="text/javascript" src="/static/res/chok/js/chok.nav.js"></script>
<script type="text/javascript" src="/static/res/chok/js/ztree-selector-modal.js"></script>
<script type="text/javascript">
function getGlobalHeight(type) {
	if (type=='table') {
		var minH = 300;
		var newH = $(window).height() - 373;
	   	return newH<minH?minH:newH;
	}
	else if (type=='tree') {
		return $(window).height() - 35;
	}
}
</script>