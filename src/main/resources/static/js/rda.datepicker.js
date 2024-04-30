function datePickerSet(){
	$('.startPicker').datepicker({
		dateFormat:'yy-mm-dd',
		changMonth:true,
		changYear:true,
		nextText:'다음 달',
		prevText:'이전 달',
		dayNames:['월요일','화요일','수요일','목요일','금요일','토요일','일요일'],
		dayNamesMin:['일','월','화','수','목','금','토'],
		monthNamesShort:['1','2','3','4','5','6','7','8','9','10','11','12'],
		monthNames:['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		showMonthAfterYear:true,
		yearSuffix:'년',
	    format: 'YYYY',
		minViewMode: 'years',
	    viewMode: "years",
		maxDate:'0D',
	})
	
	$('.endPicker').datepicker({
		dateFormat:'yy-mm-dd',
		changMonth:true,
		changYear:true,
		nextText:'다음 달',
		prevText:'이전 달',
		dayNames:['월요일','화요일','수요일','목요일','금요일','토요일','일요일'],
		dayNamesMin:['일','월','화','수','목','금','토'],
		monthNamesShort:['1','2','3','4','5','6','7','8','9','10','11','12'],
		monthNames:['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		showMonthAfterYear:true,
		yearSuffix:'년',
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
	
}

function monthPickerSet(){
	$(".monthPicker").monthpicker({
        monthNames: ['1월(JAN)', '2월(FEB)', '3월(MAR)', '4월(APR)', '5월(MAY)', '6월(JUN)',
            '7월(JUL)', '8월(AUG)', '9월(SEP)', '10월(OCT)', '11월(NOV)', '12월(DEC)'],
        monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
        /*showOn: "button",*/
        /*buttonImage: "../../Images/Goods/calendar.jpg",*/
        /*buttonImageOnly: true,*/
        changeYear: false,
        yearRange: 'c-2:c+2',
        dateFormat: 'yy-mm'
    });
}

$(function(){
	datePickerSet();
	monthPickerSet();
})