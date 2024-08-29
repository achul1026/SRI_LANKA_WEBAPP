let sideBg, sideWrap;
window.addEventListener('load', () => {

	sideBg = document.getElementById('sideBackground');
	sideWrap = document.getElementById('sideWrap');

	// sidebar background remove
	if (sideBg) {
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
	weatherButton();
})

isNull = (value) => {
	return (value === undefined || value === null || value === '') ? true : false;
}
// view resize
let viewSize = () => {
	const viewWidth = window.innerWidth;
	if (viewWidth < 768) {
		document.body.classList.remove('tablet');
		document.body.classList.add('mobile');
	} else {
		document.body.classList.remove('mobile');
		document.body.classList.add('tablet');
	}
}
// selectTime
function selectMinuteSet(target) {
	const hourSelect = target ? target.querySelectorAll('.select-hour') : document.querySelectorAll('.select-hour');
	const minuteSelect = target ? target.querySelectorAll('.select-minute') : document.querySelectorAll('.select-minute');
	let hourOption = "<option value=''>"+message.common.select+"</option>";
	let minuteOption = "<option value=''>"+message.common.select+"</option>";

	for (i = 0; i < 24; i++) {
		if (i < 10) {
			hourOption += "<option value=0" + i + ">0" + i + message.common.hour + "</option>";
		} else {
			hourOption += "<option value=" + i + ">" + i + message.common.hour + "</option>";
		}
	}
	for (i = 0; i <= 59; i++) {
		if (i < 10) {
			minuteOption += "<option value=0" + i + ">0" + i + message.common.minute + "</option>";
		} else {
			minuteOption += "<option value=" + i + ">" + i + message.common.minute + "</option>";
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
function dateToggleButton() {
	//const monthPickerButton = document.getElementById('monthpickerButton'); 
	const dayPickerButton = document.getElementById('dayPickerButton');
	const monthPicker = document.getElementById('monthPicker');
	const dayPicker = document.getElementById('dayPicker');
	const textDay = document.querySelector('.startPicker');
	const textMonth = document.querySelector('.monthPicker');

	let today = new Date();
	let year = today.getFullYear();
	let month = today.getMonth() + 1;
	let date = today.getDate();
	if (month <= 9 && date <= 9) {
		textDay.value = year + '-0' + month + '-0' + date;
	} else if (month <= 9) {
		textDay.value = year + '-0' + month + '-' + date;
	} else if (date <= 9) {
		textDay.value = year + '-' + month + '-0' + date;
	} else {
		textDay.value = year + '-' + month + '-' + date;
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
function weatherButton() {
	const weatherButton = document.querySelectorAll('.survey-weather');
	weatherButton.forEach(button => {
		button.addEventListener('change', function(_this) {
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

function setNumberDigit(value, digit) {
	const strValue = value.toString();
	if (strValue.length >= digit) {
		return strValue;
	} else {
		const zeroPadding = '0'.repeat(digit - strValue.length);
		return zeroPadding + strValue;
	}
}

//Login cookie start
let setLangCookie = () => {
	let selectLang = document.getElementById('selectLang');
	const langValue = document.getElementsByName('lang');
	let selectedValue;
	for (const lang of langValue) {
		if (lang.selected) {
			selectedValue = lang.value;
			break;
		}
	}
	setCookie("lang", selectedValue, 1);
	window.location.href='/login';
}

function setCookie(cookieName, value, exdays) {
	var exdate = new Date();
	exdate.setDate(exdate.getDate() + exdays);
	var cookieValue = escape(value) + ((exdays == null) ? "" : "; expires=" + exdate.toGMTString());
	document.cookie = cookieName + "=" + cookieValue;
}

function getCookie(cookieName) {
	cookieName = cookieName + '=';
	var cookieData = document.cookie;
	var start = cookieData.indexOf(cookieName);
	var cookieValue = '';
	if (start != -1) {
		start += cookieName.length;
		var end = cookieData.indexOf(';', start);
		if (end == -1) end = cookieData.length;
		cookieValue = cookieData.substring(start, end);
	}
	return unescape(cookieValue);
}

function getToday() {
	let today = new Date()
	let year = today.getFullYear();
	let month = today.getMonth() + 1;
	month = month >= 10 ? month : '0' + month;
	let day = today.getDate();
	day = day >= 10 ? day : '0' + day;
	let totalToday = year + '-' + month + '-' + day;
	return totalToday;
}
//Login cookie end


//number
function inputOnlyNumber(_this, location = '') {
	const element = _this;
	if (location === '') {
		element.value = element.value.replace(/[^0-9]/gi, "");
	} else if (location == location) {
		element.value = element.value.replace(/[^0-9.]+/g, "");
	}
};

function parseDateString(stringDate) {
	return new Date(stringDate);
}

// 두 시간 문자열 간의 차이를 계산하는 함수
function getTimeDifference(strBeforDate, strCurrentDate) {
	const beforDate = parseDateString(strBeforDate);
	const currentDate = parseDateString(strCurrentDate);

	const differenceInMilliseconds = Math.abs(beforDate - currentDate);

	// 밀리초를 시, 분, 초로 변환
	const hours = Math.floor(differenceInMilliseconds / (1000 * 60 * 60)).toString().padStart(2,"0");
	const minutes = Math.floor((differenceInMilliseconds % (1000 * 60 * 60)) / (1000 * 60)).toString().padStart(2,"0");
	//const seconds = Math.floor((differenceInMilliseconds % (1000 * 60)) / 1000).toString().padStart(2,"0");

	return hours + ':' + minutes;
}

(function() {
  let lastScroll = 0;
	document.addEventListener('scroll', function() {
		let currentScroll = window.pageYOffset;
		const footer = document.getElementById('footerContainer');
		if (currentScroll > lastScroll) {
			footer.classList.add('on');
		} else {
			footer.classList.remove('on');
		}
		lastScroll = currentScroll;
	});
})();


/*loading*/
class setLoading {
    constructor() {
        let loading_cover = null;
    }
    
    start = function () {
        const loading_html = `<div id="loadingContainer">
                                <div id="loadingWrap">
                                   <div id="loadingCircle">
                                   		<div></div>
                                   		<div></div>
                                   		<div></div>
                                   		<div></div>
                                   		<div></div>
                                   		<div></div>
                                   		<div></div>
                                   		<div></div>
                                   		<div></div>
                                   		<div></div>
                                   		<div></div>
                                   		<div></div>
                                   	</div>
                                </div>
                              </div>`;
        this.loading_cover = document.createElement('div');
        this.loading_cover.innerHTML = loading_html;
        document.body.appendChild(this.loading_cover);
        
        return this;
    }
    
    end = function () {
        this.loading_cover.remove();
        this.loading_cover = null;
        return this;
    }
}

function isValidJSON(str) {
	try {
		JSON.parse(str);
		return true;
	} catch (e) {
		return false;
	}
}
