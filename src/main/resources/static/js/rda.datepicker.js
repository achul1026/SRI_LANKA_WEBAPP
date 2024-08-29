function datePickerSet(){
	$('.startPicker').datepicker({
		dateFormat:'yy-mm-dd',
		changMonth:true,
		changYear:true,
		nextText:message.date.nextText,
		prevText:message.date.prevText,
		dayNames:message.date.dayNames,
		dayNamesMin:message.date.dayNamesMin,
		monthNamesShort:['1','2','3','4','5','6','7','8','9','10','11','12'],
		monthNames:message.date.monthNames,
		showMonthAfterYear:true,
		yearSuffix:message.date.yearSuffix,
	    format: 'YYYY',
		minViewMode: 'years',
	    viewMode: "years",
		maxDate:'0D',
	})
	
	$('.endPicker').datepicker({
		dateFormat:'yy-mm-dd',
		changMonth:true,
		changYear:true,
		nextText:message.date.nextText,
		prevText:message.date.prevText,
		dayNames:message.date.dayNames,
		dayNamesMin:message.date.dayNamesMin,
		monthNamesShort:['1','2','3','4','5','6','7','8','9','10','11','12'],
		monthNames:message.date.monthNames,
		showMonthAfterYear:true,
		yearSuffix:message.date.yearSuffix,
	    format: 'YYYY',
		minViewMode: 'years',
	    viewMode: "years",
		maxDate:'',
	})
	
	$('.startPicker').datepicker();
    $('.startPicker').datepicker("option", "onClose", function ( selectedDate ) {
		$('.startPicker').attr("data-start-date",selectedDate + " 00:00:00");
        $(".endPicker").datepicker( "option", "minDate", selectedDate );
    });

    $('.endPicker').datepicker();
    $('.endPicker').datepicker("option", "minDate", $(".startPicker").val());
    $('.endPicker').datepicker("option", "onClose", function ( selectedDate ) {
		$('.endPicker').attr("data-end-date",selectedDate + " 00:00:00");
        $(".startPicker").datepicker( "option", "maxDate", selectedDate );
    });
    
    $('.startPicker, .endPicker').on('change', function(){
		$('.startMinute').find('option').prop("disabled", false);
	})
	
	$('.datePicker.hasDatepicker').removeClass("hasDatepicker");
	$('.datePicker').datepicker({
		dateFormat:'yy-mm-dd',
		changMonth:true,
		changYear:true,
		nextText:message.date.nextText,
		prevText:message.date.prevText,
		dayNames:message.date.dayNames,
		dayNamesMin:message.date.dayNamesMin,
		monthNamesShort:['1','2','3','4','5','6','7','8','9','10','11','12'],
		monthNames:message.date.monthNames,
		showMonthAfterYear:true,
		yearSuffix:message.date.yearSuffix,
	    format: 'YYYY',
		minViewMode: 'years',
	    viewMode: "years"
	})
}

$(function(){
	datePickerSet();
})