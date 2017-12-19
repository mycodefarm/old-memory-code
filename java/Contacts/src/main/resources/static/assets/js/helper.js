function myAjax(url, data, callback) {
	$.ajax({
		url : url,
		type : "POST",
		data : data,
		success : function(re) {
			//console.log(re)
			if (re.code == 1) {
				callback(re);
			} else {
				alert(re.msg)
			}
		},
		error : function(e) {
			console.log(e)
			alert("error");
		}
	});
}