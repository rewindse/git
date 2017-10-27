/**
 * Write by Liwei
 */
var WEB_CONTEXT_PATH = '';

$.ajax({
	type : "post",
	url : "common/context_path",
	async : false,
	success : function (contextPath) {
    	WEB_CONTEXT_PATH = contextPath;
	}
});