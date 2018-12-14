/**
 * 
 * @returns
 */


$(document).ready(function() {
	
	
	
	
	$("#posForm").submit(function(event){
		event.preventDefault();
		ajaxPost();
	});
	
	
	function ajaxPost(){
		var formData = {
				id : $(".seForm #pid").val(),
				value : $(".seForm #value").val()
		}
		
		var devid =  $('.seForm #did').val()
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url :  "jpa/device/"+devid+"/post",
			data : JSON.stringify(formData),
			dataType : 'json',
			success: function(result){
				if (result.status == "success"){
					$(".seForm #PostResultDiv").html(
					"device successfully  "+result.data.description );
				}else{
					$("#seForm #PostResultDiv").html("<strong>DONE!</strong>");
					
				}
				console.log(result);
				
			},
		//	error : function(e) {
			//	alert("ERROR: ", e);
				//console.log("ERROR: ", e);
		//	}
		});	
	}
})
