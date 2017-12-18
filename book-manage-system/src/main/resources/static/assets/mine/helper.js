function myAjax(url, data, callback) {
	$.ajax({
		url : url,
		type : "POST",
		data : data,
		success : function(re) {
			if (re.code == 0) {
				callback(re);
			} else {
				alert(re.msg)
			}
		},
		error : function() {
			alert("error");
		}
	});
}

// 获取当前时间，格式YYYY-MM-DD
function getNowFormatDate() {
	var date = new Date();
	var seperator1 = "-";
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var currentdate = year + seperator1 + month + seperator1 + strDate;
	return currentdate;
}