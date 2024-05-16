let sideBg, sideWrap;
window.addEventListener('load', () => {
	sideBg = document.getElementById('sideBackground');
	sideWrap = document.getElementById('sideWrap');
	
	// sidebar background remove
	if(sideBg) {
		sideBg.addEventListener('click', () => {
			sideWrap.classList.remove('active');
			sideBg.classList.add('none');
			document.body.classList.remove('scroll-hidden');
		});
	}
	
    window.addEventListener('resize', () => {
		viewSize();
	})
	viewSize();
	selectMinuteSet();
	weatherButton();
	
	$('.radio-checked').on('change', function(){
        $(this).closest('li').siblings().find('.check-img').removeClass('check-img-on');
        $(this).siblings('.check-img').addClass('check-img-on');
    })
    
    $('.checkbox-checked').on('change', function(){
        $(this).siblings('.check-img').toggleClass('check-img-on');
    })
})
isNull = function(value) {
    return (value === undefined || value === null || value === '') ? true : false;
}
// view resize
viewSize = () => {
	const viewWidth = window.innerWidth;
	if(viewWidth < 768) {
		document.body.classList.remove('tablet');
		document.body.classList.add('mobile');
	} else {
		document.body.classList.remove('mobile');
		document.body.classList.add('tablet');
	}
}
// selectTime
function selectMinuteSet(){
	const hourSelect = document.querySelectorAll('.select-hour');
	const minuteSelect = document.querySelectorAll('.select-minute');
	let hourOption = "";
	let minuteOption = "";
	
	for(i = 0; i < 24; i++){
		if(i < 10){
			hourOption += "<option value=0"+i+">0"+i+"시</option>";
		}else{
			hourOption += "<option value="+i+">"+i+"시</option>";
		}
	}
	for(i = 0; i <= 59; i++){
		if(i < 10){
			minuteOption += "<option value=0"+i+">0"+i+"분</option>";
		} else {
			minuteOption += "<option value="+i+">"+i+"분</option>";
		}
	}
	
    hourSelect.forEach((select) => {
        select.innerHTML = hourOption;
        select.classList.add('scroll');
    });
    minuteSelect.forEach((select) => {
        select.innerHTML = minuteOption;
        select.classList.add('scroll');
    });
}

//survey date toggle button
function dateToggleButton(){
	//const monthPickerButton = document.getElementById('monthpickerButton'); 
	const dayPickerButton = document.getElementById('dayPickerButton');
	const monthPicker = document.getElementById('monthPicker');
	const dayPicker = document.getElementById('dayPicker');
	const textDay = document.querySelector('.startPicker');
	const textMonth = document.querySelector('.monthPicker');
	
	let today = new Date();   
	let year = today.getFullYear(); 
	let month = today.getMonth()+1;
	let date = today.getDate();
	if(month <= 9 && date <= 9) {
		textDay.value = year+'-0'+month+'-0'+date;
	} else if(month <= 9) {
		textDay.value = year+'-0'+month+'-'+date;
	} else if(date <= 9){
		textDay.value = year+'-'+month+'-0'+date;
	} else {
		textDay.value = year+'-'+month+'-'+date;
	}
	
	/*if(month <= 9){
		textMonth.value = year+'-0'+month;	
	} else {
		textMonth.value = year+'-'+month;
	}
	
	monthPickerButton.addEventListener('click', (event) => {
		dayPickerButton.classList.remove('is-key-button');
		event.target.classList.add('is-key-button');
		dayPicker.classList.add('none');
		monthPicker.classList.remove('none');
	})
	
	dayPickerButton.addEventListener('click', (event) => {
		monthPickerButton.classList.remove('is-key-button');
		event.target.classList.add('is-key-button');
		monthPicker.classList.add('none');
		dayPicker.classList.remove('none');
	})*/
}

//날씨 버튼 toggle
function weatherButton(){
	const weatherButton = document.querySelectorAll('.survey-weather');
	weatherButton.forEach(button => {
		button.addEventListener('change', function(_this){
			const parentBox = _this.target.closest('.survey-text').querySelectorAll('.survey-weather');
			parentBox.forEach(active => {
				active.classList.remove('is-primary-button');
			})
			button.classList.add('is-primary-button');
		})
	})
}

//date to localdatetime
function toLocalDateTime(date) {
    let normalizedDate = new Date(date.getTime() - (date.getTimezoneOffset() * 60000));
    return normalizedDate.toISOString().replace('Z', '').substring(0, 19);
}