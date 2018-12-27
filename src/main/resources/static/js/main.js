/**
 * 
 */

$(document).ready(function(){
	
	$('.nBtn, .rBtn, .table .eBtn, .table .viBtn, .table .seBtn ').on('click',function(event){
		event.preventDefault();
		var href = $(this).attr('href');
		var text = $(this).text();
		
	
		if(text=='Edit'){
			
		$.get(href,function(device,status){
			$('.myForm #id').val(device.id);
			$('.myForm #name').val(device.name);
			$('.myForm #location').val(device.location);
			$('.myForm #type').val(device.type);
			$('.myForm #connector').val(device.connector);
		});
		
	$('.myForm #exampleModal').modal();
		}else if(text=='Open'){
			$.get(href,function(device,status){
				$.each(device, function(i, field){
					$('.tblForm #poid').text(field.id);
					$('.tblForm #podes').text(field.value);
					$('.table #op').text(field.value);
					
		        });
			});
			
			$('.tblForm #tblmodal').modal();
		}
		else if(text=='Send'){
			$('.seForm #did').val(href);
			$('.seForm #semodal').modal();
		}
		else if(text=='Scenarios'){
			
			$('.relForm #relmodal').modal();
		}
		
		else{
			
			$('.myForm #id').val('');
			$('.myForm #name').val('');
			$('.myForm #location').val('');
			$('.myForm #type').val('');
			$('.myForm #connector').val('');
			$('.myForm #degree').val('');
			$('.myForm #exampleModal').modal();
			
		}
	});
	
	$('.table .delBtn').on('click',function(event){
		event.preventDefault();
		var href = $(this).attr('href');
		$('#myModal #delRef').attr('href' ,href);
		$('#myModal').modal();
		
	});
});
