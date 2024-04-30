surveyMap = (maplon, maplat, mapMeters) => {
	(async () => {
		document.body.classList.add('hidden');
		
		let mapgl = await mapboxGl(maplon, maplat, mapMeters);
		let startY = '';
		let startHeight = '';
	    const targetElements = document.getElementById('surveyContent');
	    const mapElements = document.getElementById('surveyMapContainer');
	    
	    targetElements.addEventListener('touchstart', (event) => {
	    	startY = event.touches[0].clientY;
	    	startHeight = targetElements.offsetHeight;
	    })
        
	    targetElements.addEventListener('touchmove', (event) => {
	    	const currentY = event.touches[0].clientY;
	    	const deltaY = currentY - startY;
	    	const mapEleHeight = mapElements.clientHeight;
	    	const currentResult = Math.floor(deltaY);
	    	const mapEleSize = mapEleHeight + currentResult + 'px';
	        
		    const mapEleBounding = mapElements.getBoundingClientRect();
		    const mapEleBottom = mapEleBounding.bottom;
		    const mapEleBottomResult = Math.floor(mapEleBottom);
		    const windowHeight = window.innerHeight; //176
		    const windowMobileBreakHeight = window.innerHeight - 176;
		    const windowTabletBreakHeight = window.innerHeight - 200;
        	
	        if(currentY > startY) {
	            if(mapEleBottomResult < windowHeight) {
	            	mapElements.classList.remove('map-active');
	            	mapElements.classList.remove('map-remove');
	            	mapElements.style.height = mapEleSize;
	            }
	        } else if(currentY < startY) {
	        	mapElements.classList.remove('map-active');
	        	mapElements.classList.remove('map-remove');
				mapElements.style.height = mapEleSize;
	        }
	        
	        setTimeout(() => {
	        	mapgl.resize();
	        }, 200);
	        
	        //조사정보 touchScroll max,min height
		    let windowMobileBreakMinHeight = window.innerHeight - 524;
		    let windowTabletBreakMinHeight = window.innerHeight - 556;
	        const surveyItem = document.querySelectorAll('.survey-item');
	        surveyItem.forEach(item => {
		        if(item.classList.contains('survey-direction')){
					windowMobileBreakMinHeight = window.innerHeight - 586;
			    	windowTabletBreakMinHeight = window.innerHeight - 609;
				}
			})
		    
	        if(document.body.classList.contains('mobile')){
		        mapElements.style.maxHeight = windowMobileBreakHeight + 'px';
		        mapElements.style.minHeight = windowMobileBreakMinHeight + 'px';
			} else {
				mapElements.style.maxHeight = windowTabletBreakHeight + 'px';
		        mapElements.style.minHeight = windowTabletBreakMinHeight + 'px';
			}
	        
	    })
	    
	    //touchend 지점에 따른 클래스 추가
	    targetElements.addEventListener('touchend', (event) => {
			const endY = event.changedTouches[0].clientY;
			const screenHeight = window.innerHeight * 0.7;
			if(endY >= screenHeight) {
				mapElements.classList.remove('map-down');
				mapElements.classList.add('map-up');
			} else if(endY <= screenHeight) {
				mapElements.classList.remove('map-up');
				mapElements.classList.add('map-down');
			}
	    })	    
	    
	    const topLineButton = document.getElementById('top-line');
		topLineButton.addEventListener('click', () => {
			if(mapElements.classList.contains('map-active') || mapElements.classList.contains('map-up')){
				mapElements.classList.remove('map-active');
				mapElements.classList.remove('map-up');
				mapElements.classList.add('map-remove');
			}else if(mapElements.classList.contains('map-remove') || mapElements.classList.contains('map-down')) {
				mapElements.classList.remove('map-remove');
				mapElements.classList.remove('map-down');
				mapElements.classList.add('map-active');				
			}
			
	        setTimeout(() => {
	        	mapgl.resize();
	        }, 200)
		})
	})()
	const headerNextButton = document.getElementById('headerNextButton');
	headerNextButton.classList.remove('none');
}

suryerCounting = (url) => {
	document.body.classList.add('hidden');
	
	const countingBox = document.querySelectorAll('.box-counting');
	let longTouchTime, countingInput, countingValue
	
	countingBox.forEach((counting) => {
		counting.addEventListener('click', (event) => {
			countingInput = event.currentTarget.querySelector('.counting-value');
			countingValue = parseInt(countingInput.value)
			countingInput.value = countingValue + 1;
		})
		counting.addEventListener('touchstart', (event) => {
			const boxTitle = event.currentTarget.querySelector('span');
			const titleName = boxTitle.innerText;
			countingInput = event.currentTarget.querySelector('.counting-value');
			countingValue = parseInt(countingInput.value)
			
			longTouchTime = setTimeout(() => {
				new ModalBuilder().init(titleName).ajaxBody(url).footer(3,'확인',function(button, modal){
						const modalInputTotal = document.getElementById('modalCountingInput').value;
						countingInput.value = modalInputTotal;
						modal.close();
					}, '취소', function(button, modal){}).open();
				
				const modalInput = document.getElementById('modalCountingInput');
				modalInput.value = countingValue;
				
				const modalInputMinus = document.getElementById('modalCountingMinus');
				const modalInputPlus = document.getElementById('modalCountingPlus');
				
				modalInputMinus.addEventListener('click', () => {
					countingValue = parseInt(modalInput.value);
					modalInput.value = countingValue - 1;
				})
				modalInputPlus.addEventListener('click', () => {
					countingValue = parseInt(modalInput.value);
					modalInput.value = countingValue + 1;
				})
			}, 800);
		})
		
		counting.addEventListener('touchend', () => {
			clearTimeout(longTouchTime);
		})
	})
}

//조사불가능버튼
function impossible(){
	const locationSave = document.getElementById('impossibleButton');
	const locationChangeBox = document.getElementById('impossibleBox');
	locationSave.classList.add('none');
	locationChangeBox.classList.remove('none');
}

//조사화면에서 다음 버튼
window.addEventListener('load', () => {
	const nextButton = document.getElementById('headerNextButton')
	const header = document.getElementById('headerContainer');
	const pageName = document.getElementById('pageName');
	 
	if(header){
		nextButton.addEventListener('click', () => {
			
			
			let tlRsltSaveDTO = new Object();
			
			//조사 타입 traffic(MCC,TM)/survey(OD,AXLELOAD,LABORSIDE)
			const exmnType = document.getElementById('exmnType').value;
			const exmnstartDt = document.getElementById('exmnstartDt').value;
			const exmnendDt = document.getElementById('exmnendDt').value;
			const exmnLc = document.getElementById('exmnLc').dataset.exmnLc;
			const exmnmngId = pageName.dataset.exmnmngId;
			const exmnrsltId = pageName.dataset.exmnrsltId;
			
			tlRsltSaveDTO.pollsterLc 	= exmnLc;
			tlRsltSaveDTO.exmnmngId 	= exmnmngId;
			tlRsltSaveDTO.exmnrsltId 	= exmnrsltId;
			tlRsltSaveDTO.exmnstartDt 	= exmnstartDt;
			tlRsltSaveDTO.exmnendDt 	= exmnendDt;
			tlRsltSaveDTO.type 			= exmnType;
			
			if(exmnType === 'traffic'){
				
				//현재위치
				const myLocation = [lon, lat];
				//현재위치와 조사반경비교
				const circlecUserinOutCheck = turf.booleanPointInPolygon(myLocation, turfJs);
				
				//조사불가능 사유
				const lcchgRsn = document.getElementById('lcchgRsn').value;

				if(!circlecUserinOutCheck){
					//조사 불가능 사유가 없거나, 위치반경안에 없을때
					if(lcchgRsn == ''){
						new ModalBuilder().init().alertBody("반경 내에서 등록해주세요.<br>반경 내 진입 불가 시 사유를 입력하세요.").footer(4,'확인',function(button, modal){
							modal.close();
							impossible();
						}).open();
						return false;
					}

				}

				//조사방향
				const exmndrctId = document.querySelector('#exmndrctId');
				const startlcNm = exmndrctId.options[exmndrctId.selectedIndex].dataset.startlcNm;
				const endlcNm = exmndrctId.options[exmndrctId.selectedIndex].dataset.endlcNm;
				
				tlRsltSaveDTO.pollsterLat 	= lat;
				tlRsltSaveDTO.pollsterLon 	= lon;
				tlRsltSaveDTO.startlcNm 	= startlcNm;
				tlRsltSaveDTO.endlcNm 		= endlcNm;

				tlRsltSaveDTO.lcchgRsn 		= lcchgRsn;

			}
			
			fetch(__contextPath__+"/invstmng",{
				method: "POST",
				headers: {
					'Content-Type': 'application/json;charset=utf-8'
				},
				body: JSON.stringify(tlRsltSaveDTO)
			})
			.then(response => response.json())
			.then(result => {
				if(result.code == '200'){
					const trfvlOrSrvyRsltId = result.data.trfvlOrSrvyRsltId;
					if(exmnType === 'traffic'){
						window.location.href = __contextPath__+"/invstmng/"+trfvlOrSrvyRsltId+"/counting";
					}else{
						window.location.href = __contextPath__+"/invstmng/"+trfvlOrSrvyRsltId+"/question";
					}
				}else if(result.code == '2002'){
					//2002 등록된 조사 불가 사유가 존재
					new ModalBuilder().init().alertBody(result.message).footer(4,'확인',function(button, modal){
						window.location.href = __contextPath__+"/main";
						modal.close();
					}).open();
				}
			})
		})
	}
})

//카운팅 조사 저장
function saveTrafficCounting(trfvlmexmnId){
	
	//data
	const checkMinutes = [00, 15, 30, 45];
	const now = new Date();
	let nextTime = getNextCheckTime(now,checkMinutes);
	let delay = nextTime - now;
	
	//확인 버튼을 누르면 새로운 URL로 이동되므로
	//타이머 자동 취소
	setTimeout(() => {
		
		let tlTrfvlList = new Array();

		let countingInfo = document.getElementsByName('countingInfo');
		for(let item of countingInfo){
			let tlTrfvlInfo 	= new Object();
			tlTrfvlInfo.trfvlmexmnId 	= trfvlmexmnId;
			tlTrfvlInfo.mvmnmeanType 	= item.dataset.type;
			tlTrfvlInfo.trfvlm 			= item.value;
			tlTrfvlInfo.ftnminunitTime 	= toLocalDateTime(nextTime);
			tlTrfvlList.push(tlTrfvlInfo);
		}
		
		fetch(__contextPath__+"/invstmng/counting",{
			method: "POST",
			headers: {
				'Content-Type': 'application/json;charset=utf-8'
			},
			body: JSON.stringify(tlTrfvlList)
		})
		.then(response => response.json())
		.then(result => {
			if(result.code == '200'){
				for (const item of countingInfo) {
				    item.value = 0;
				}
				saveTrafficCounting(trfvlmexmnId);
			}else{
				new ModalBuilder().init().alertBody(result.message).footer(4,'확인',function(button, modal){
					window.location.href = __contextPath__+"/main";
					modal.close();
				}).open();
			}
		})
	}, delay);

}

function invstQuestionSave(trfvlmexmnId){
	alert(trfvlmexmnId)
}
//조사 5분전 알림
function fiveMinutesAgo() {
	const checkMinutes = [10, 25, 40, 55];
	const now = new Date();
	
	let delay = getNextCheckTime(now,checkMinutes) - now;
	
	setTimeout(() => {
		new ModalBuilder().init().alertBody('조사 종료 5분전 입니다.').footer(4,'확인',function(button, modal){modal.close();}).open();
		fiveMinutesAgo();
	}, delay);
}


function getNextCheckTime(currentTime,checkMinutes) {
	
	let nextCheck = new Date(currentTime.getTime());
	nextCheck.setSeconds(0);
	nextCheck.setMilliseconds(0);

	let nextMinute = checkMinutes.find(minute => minute > currentTime.getMinutes());
	if (nextMinute !== undefined) {
		nextCheck.setMinutes(nextMinute);
	} else {
		nextCheck.setHours(currentTime.getHours() + 1);
		nextCheck.setMinutes(checkMinutes[0]);
	}
	
	return nextCheck;
}

//카운티 화면에서 확인 버튼
function invstCountingSave(exmnmngId){
	let tlTrfvlList = new Array();

		let countingInfo = document.getElementsByName('countingInfo');
		
		for(let item of countingInfo){
			let tlTrfvlInfo 	= new Object();
			tlTrfvlInfo.trfvlmexmnId 	= trfvlmexmnId;
			tlTrfvlInfo.mvmnmeanType 	= item.dataset.type;
			tlTrfvlInfo.trfvlm 			= item.value;
			tlTrfvlInfo.ftnminunitTime 	= toLocalDateTime(roundUpToNearestInterval(15));
			tlTrfvlList.push(tlTrfvlInfo);
		}
		
		fetch(__contextPath__+"/invstmng/counting",{
			method: "POST",
			headers: {
				'Content-Type': 'application/json;charset=utf-8'
			},
			body: JSON.stringify(tlTrfvlList)
		})
		.then(response => response.json())
		.then(result => {
			if(result.code == '200'){
				window.location.href = __contextPath__+"/main";
			}else{
				new ModalBuilder().init().alertBody(result.message).footer(4,'확인',function(button, modal){
					window.location.href = __contextPath__+"/main";
					modal.close();
				}).open();
			}
		})
}

function roundUpToNearestInterval(interval) {
	
    const date = new Date();  // 현재 시간
    let minutes = date.getMinutes();  // 현재 분

    // interval로 나눈 몫을 계산하고 1을 더해 다음 interval을 구함
    minutes = Math.ceil((minutes + 1) / interval) * interval;

    if (minutes >= 60) {  // 분이 60 이상이면 시간을 1 증가
        date.setHours(date.getHours() + 1);
        minutes -= 60;  // 분을 60 감소
    }

    date.setMinutes(minutes);
    date.setSeconds(0);  // 초를 0으로 설정
    date.setMilliseconds(0);  // 밀리초를 0으로 설정

    return date;
}