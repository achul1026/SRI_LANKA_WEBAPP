let mvmnmeanTypeCd; //카운팅 조사 로컬 스토리 변수
let paramCountingInfo = new Object;
let paramQuestionInfo = new Object;

let surveyMap = (lng, lat, meters) => {
	(async () => {
		document.body.classList.add('hidden');
		let startY = '';
		
	    const targetElements = document.getElementById('surveyContent');
	    const mapElements = document.getElementById('surveyMapContainer');
	    
	    const contentHeight = targetElements.offsetHeight;
	    const headerHeight = document.getElementById('headerContainer').offsetHeight;
	    const footerHeight = document.getElementById('footerContainer').offsetHeight;
	    const windowHeight = window.innerHeight;
		
		
		//처음 화면 진입시 view size 	    
		const totalHeight = windowHeight - contentHeight - headerHeight -  footerHeight;
		const firstViewHeight = totalHeight; //150
        if(document.body.classList.contains('mobile')){
			mapElements.style.height = `${firstViewHeight + 150}px`;	        
		} else {
			mapElements.style.height = `${firstViewHeight}px`;
		}
		
	    
		const style = document.createElement('style');
		document.head.appendChild(style);
		style.appendChild(document.createTextNode(`#surveyMapContainer.map-remove { height: ${totalHeight}px !important}`));	    
		
		//MAP SETTING
		let mapgl = await mapboxGl(lng, lat);
		mapTrafficType(mapgl, lng, lat, meters);
		setMapZoomControl(mapgl);
		myLocation(mapgl);
		
	    targetElements.addEventListener('touchstart', (event) => {
	    	startY = event.touches[0].clientY;
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
		    const windowMobileBreakHeight = windowHeight - 176;
		    const windowTabletBreakHeight = windowHeight - 200;
        	
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
	        
	        /*setTimeout(() => {
	        }, 200);*/
	        
        	mapgl.resize();
	        
	        //조사정보 touchScroll max,min height
	        if(document.body.classList.contains('mobile')){
		        mapElements.style.maxHeight = windowMobileBreakHeight + 'px';
		        mapElements.style.minHeight = totalHeight + 'px';
			} else {
				mapElements.style.maxHeight = windowTabletBreakHeight + 'px';
		        mapElements.style.minHeight = totalHeight + 'px';
			}
	        
	    })
	    
	    //touchend 지점에 따른 클래스 추가
	    targetElements.addEventListener('touchend', (event) => {
			const endY = event.changedTouches[0].clientY;
			const screenHeight = windowHeight * 0.7;
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

let suryerCounting = (url,exmnmngId, paramSaveDt) => {
	document.body.classList.add('hidden');
	
	const countingBox = document.querySelectorAll('.box-counting');
	let longTouchTime, countingInput, countingValue
	
	countingBox.forEach((counting) => {
		counting.addEventListener('click', (event) => {
			countingInput = event.currentTarget.querySelector('.counting-value');
			countingType  = countingInput.dataset.type;
			countingValue = parseInt(countingInput.value)
			countingInput.value = countingValue + 1;
			setLocalStorageData(countingType,countingInput.value,paramSaveDt);
		})
		counting.addEventListener('touchstart', (event) => {
			const boxTitle = event.currentTarget.querySelector('span');
			const titleName = boxTitle.innerText;
			countingInput = event.currentTarget.querySelector('.counting-value');
			countingType  = countingInput.dataset.type;
			countingValue = parseInt(countingInput.value)
			
			longTouchTime = setTimeout(() => {
				const modalContainer = document.querySelector('.modal-container');
				if(!modalContainer) {
					new MsgModalBuilder().init(titleName).ajaxBody(url).footer(3,message.common.button_confirm,function(button, modal){
							const modalInputTotal = document.getElementById('modalCountingInput').value;
							countingInput.value = modalInputTotal;
							setLocalStorageData(countingType,modalInputTotal,paramSaveDt);
							modal.close();
						}, message.common.button_cancel, function(button, modal){}).open();
						
					const modalInput = document.getElementById('modalCountingInput');
					modalInput.value = countingValue;
					
					const modalInputMinus = document.getElementById('modalCountingMinus');
					const modalInputPlus = document.getElementById('modalCountingPlus');
					
					modalInputMinus.addEventListener('click', () => {
						countingValue = parseInt(modalInput.value);
						if(countingValue >= 1 ) {
							modalInput.value = countingValue - 1;
						}
					})
					modalInputPlus.addEventListener('click', () => {
						countingValue = parseInt(modalInput.value);
						modalInput.value = countingValue + 1;
					})
				}
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
	locationChangeBox.classList.add('flex-center');
}
function impossibleCancel(){
	const locationSave = document.getElementById('impossibleButton');
	const locationChangeBox = document.getElementById('impossibleBox');
	locationChangeBox.classList.add('none');
	locationChangeBox.classList.remove('flex-center');
	locationSave.classList.remove('none');
	document.getElementById('lcchgRsn').value = '';
}

//조사화면에서 다음 버튼
function saveInvstTrafficRslt(){
	
	let tlRsltSaveDTO = new Object();
	const pageName = document.getElementById('pageName');
	//조사 타입 traffic(MCC,TM)/survey(OD,AXLELOAD,ROADSIDE)
	const exmnType = document.getElementById('exmnType').value;
	const exmnstartDt = document.getElementById('exmnstartDt').value;
	const exmnendDt = document.getElementById('exmnendDt').value;
	const exmnLc = document.getElementById('exmnLc').dataset.exmnLc;
	const exmnmngId = pageName.dataset.exmnmngId;
	const exmnrsltId = pageName.dataset.exmnrsltId;
	const selectPollsterTel = document.getElementById('pollsterTel');
	const pollsterTel = selectPollsterTel.options[selectPollsterTel.selectedIndex].value;
	
	tlRsltSaveDTO.pollsterLc 	= exmnLc;
	tlRsltSaveDTO.exmnmngId 	= exmnmngId;
	tlRsltSaveDTO.exmnrsltId 	= exmnrsltId;
	tlRsltSaveDTO.exmnstartDt 	= exmnstartDt;
	tlRsltSaveDTO.exmnendDt 	= exmnendDt;
	tlRsltSaveDTO.type 			= exmnType;
	tlRsltSaveDTO.pollsterTel 	= pollsterTel;
	
	//현재위치
	const myLocation = [lng, lat];
	
	//현재위치와 조사반경비교
	const circlecUserinOutCheck = turf.booleanPointInPolygon(myLocation, turfJs);
	
	//조사불가능 사유
	const lcchgRsn = document.getElementById('lcchgRsn').value;

	if(!circlecUserinOutCheck){
		//조사 불가능 사유가 없거나, 위치반경안에 없을때
		if(lcchgRsn == ''){
			new MsgModalBuilder().init().alertBody(message.survey.survey_js_circlecUserinOutCheck).footer(4,message.common.button_confirm,function(button, modal){
				impossible();
				modal.close();
			}).open();
			return false;
		}
	}

	//조사방향
	const exmndrctId = document.querySelector('#exmndrctId');
	const startlcNm = exmndrctId.options[exmndrctId.selectedIndex].dataset.startlcNm;
	const endlcNm = exmndrctId.options[exmndrctId.selectedIndex].dataset.endlcNm;
	tlRsltSaveDTO.pollsterLat 	= lat;
	tlRsltSaveDTO.pollsterLon 	= lng;
	tlRsltSaveDTO.startlcNm 	= startlcNm;
	tlRsltSaveDTO.endlcNm 		= endlcNm;

	tlRsltSaveDTO.lcchgRsn 		= lcchgRsn;

	const loading = new setLoading().start();
	fetch(__contextPath__+"/invstmng",{
		method: "POST",
		headers: {
			'Content-Type': 'application/json;charset=utf-8',
			'X-Requested-With': 'XMLHttpRequest'
		},
		body: JSON.stringify(tlRsltSaveDTO)
	})
	.then(response => response.json())
	.then(result => {
		if(result.code == '200'){
			const trfvlOrSrvyRsltId = result.data.trfvlOrSrvyRsltId;
			window.location.href = __contextPath__+"/invstmng/"+trfvlOrSrvyRsltId+"/counting";
		}else if(result.code == '401'){ //조사시간 만료
			new MsgModalBuilder().init().successBody(result.message).footer(4,message.common.button_confirm,function(button, modal){
				localStorage.removeItem(paramCountingInfo.exmnmngId);
				window.location.href = __contextPath__+"/login";
				modal.close();
			}).open();
		}else if(result.code == '2002'){
			//2002 등록된 조사 불가 사유가 존재
			new MsgModalBuilder().init().alertBody(result.message).footer(4,message.common.button_confirm,function(button, modal){
				window.location.href = __contextPath__+"/main";
				modal.close();
			}).open();
		}
	})
	.catch(error => {
		console.error('Error:', error);
		return;
	}).finally(() => {
		loading.end();
	});	
}


function saveInvstSurveyRslt(){
	const loading = new setLoading().start();
	
	const pageName = document.getElementById('pageName');
	const exmnrsltId = pageName.dataset.exmnrsltId;
	//조사방향
	const exmndrctId = document.querySelector('#exmndrctId').value;
	
	window.location.href = __contextPath__+"/invstmng/"+exmnrsltId+"/question?exmndrctId="+exmndrctId;
	
	loading.end();	
}

function savePollsterInfo(){
	
	let tlSrvyRsltPollsterInfoSaveDTO = new Object();
	const pageName = document.getElementById('pageName');
	const exmnmngId = pageName.dataset.exmnmngId;

	const pollsterId = document.getElementById('pollsterId');
	const pollsterTel = document.getElementById('pollsterTel');
	const regionType = document.getElementById('regionType');
	const surveyRegion = document.getElementById('surveyRegion');

	//조사 위치 (GN CODE) 수정 필요
	const dsdCd = document.getElementById('dsdCd').value;
	let gnCd = document.getElementById('gnCd').value;
	let pollsterLc = document.getElementById('exmnLc').value;
	if(isNull(gnCd)){
		const selectGnCd 	= document.querySelector('#selectGnCd');
		const selectedGnCd 	= selectGnCd.options[selectGnCd.selectedIndex].value;
		if(isNull(selectedGnCd)){
			new MsgModalBuilder().init().alertBody(message.survey.survey_js_selectedGnCd).footer(4,message.common.button_confirm,function(button, modal){
				modal.close();
			}).open();
			return;
		}
		const selectedGnNm 	= selectGnCd.options[selectGnCd.selectedIndex].innerText;
		gnCd 				= dsdCd + setNumberDigit(selectedGnCd,3);
		pollsterLc 			= pollsterLc +", "+ selectedGnNm;
	}

	tlSrvyRsltPollsterInfoSaveDTO.pollsterId 	= pollsterId.value;
	tlSrvyRsltPollsterInfoSaveDTO.pollsterTel 	= pollsterTel.value;
	tlSrvyRsltPollsterInfoSaveDTO.pollsterLc 	= pollsterLc;
	tlSrvyRsltPollsterInfoSaveDTO.gnCd 			= gnCd;
	
	if(regionType && surveyRegion){
		if(regionType.value == "RTC001"){
			tlSrvyRsltPollsterInfoSaveDTO.cordonLine 	= surveyRegion.value;
		} else if(regionType.value == "RTC002"){
			tlSrvyRsltPollsterInfoSaveDTO.tollBooth 	= surveyRegion.value;
		}
	}
	
	if(validation('surveyInner')){
		const loading = new setLoading().start();
		fetch(__contextPath__+"/invstmng/pollster",{
			method: "POST",
			headers: {
				'Content-Type': 'application/json;charset=utf-8',
				'X-Requested-With': 'XMLHttpRequest'
			},
			body: JSON.stringify(tlSrvyRsltPollsterInfoSaveDTO)
		})
		.then(response => {
			/*if (!response.ok) {

				return response.text().then(text => { throw new Error(text) });
			}*/
			return response.json();
		})
		.then(result => {
			if(result.code == '200'){
				window.location.href = __contextPath__+"/invstmng/"+exmnmngId+"/survey";
			}else if(result.code == '401'){ //조사시간 만료
				new MsgModalBuilder().init().successBody(result.message).footer(4,message.common.button_confirm,function(button, modal){
					window.location.href = __contextPath__+"/login";
					modal.close();
				}).open();
			}else{
				new MsgModalBuilder().init().alertBody(result.message).footer(4,message.common.button_confirm,function(button, modal){
					window.location.reload();
					modal.close();
				}).open();
			}
		})
		.catch(error => {
			console.error('Error:', error);
			return;
		}).finally(() => {
			loading.end();
		});
	}
}

//카운팅 조사 저장
function saveTrafficCounting(trfvlmexmnId){
	const savePushEl = document.getElementById('countingSaveBox');
	
	//data
	const checkMinutes = [00, 15, 30, 45];
	const now = new Date();
	let nextTime = getNextCheckTime(now,checkMinutes);
	let delay = nextTime - now;
	
	//확인 버튼을 누르면 새로운 URL로 이동되므로
	//타이머 자동 취소
	setTimeout(() => {
		//push
		savePushEl.classList.add('active')
		setTimeout(() => {
			savePushEl.classList.remove('active');
		}, 5000)
		
		//로컬스토지 초기화
		setLocalStorage(nextTime);
				
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
				'Content-Type': 'application/json;charset=utf-8',
				'X-Requested-With': 'XMLHttpRequest'
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
			}else if(result.code == '401'){ //조사시간 만료
				new MsgModalBuilder().init().successBody(result.message).footer(4,message.common.button_confirm,function(button, modal){
					window.location.href = __contextPath__+"/login";
					modal.close();
				}).open();
			}else{
				new MsgModalBuilder().init().alertBody(result.message).footer(4,message.common.button_confirm,function(button, modal){
					window.location.href = __contextPath__+"/main";
					modal.close();
				}).open();
			}
		})
	}, delay);
}

//환승지 메타 데이타 코드
let transferCdArr = ["SMD028","SMD032","SMD036","SMD040","SMD044"];


function invstQuestionSave(exmnrsltId,srvyrsltId = ''){
	 if(validation('surveyWrap')) {
		if(setDateTimeValidCheck()) {
			const survyQuestionWrap = document.getElementsByClassName('survy-question-wrap');
			let tlSrvyAnsSaveDTO = {};
			let tlSrvyAnsList = [];
	
			for(let survyQuestionElelment of survyQuestionWrap){
				let surveyQuestionTitle = survyQuestionElelment.querySelector('.survey-question-title');
				let sectTitle 			= surveyQuestionTitle.querySelector('.surveyInTitle').innerText;
				let sectType 			= surveyQuestionTitle.dataset.sectType;
				let sectSqno			= survyQuestionElelment.dataset.sectSqno;
	
				let surveyInfoWrap = survyQuestionElelment.getElementsByClassName('survey-info-wrap');
	
				for(let surveyInfoElement of surveyInfoWrap){
					let tlSrvyAns 	= {};
					let qstnInfo 	= surveyInfoElement.querySelector('.survey-sub-question-title');
					let qstnTitle 	= qstnInfo.dataset.qstnTitle;
					let qstnType 	= surveyInfoElement.dataset.qstnType;
					let qstnSqno 	= qstnInfo.dataset.qstnSqno;
					let srvyMetadataCd 	= qstnInfo.dataset.metadata;
					let ansCnts		= null;
					let etcYn 		= "N";
	
	
	
					tlSrvyAns.srvyrsltId 	= srvyrsltId;
					tlSrvyAns.sectType 		= sectType;
					tlSrvyAns.sectTitle 	= sectTitle;
					tlSrvyAns.sectSqno 		= sectSqno;
					tlSrvyAns.qstnTitle 	= qstnTitle;
					tlSrvyAns.qstnType 		= qstnType;
					tlSrvyAns.qstnSqno 		= qstnSqno;
					
	
					if(qstnType === 'QTC003'){ //체크박스 값 여러개
						let selectedValues = surveyInfoElement.querySelectorAll('.check-img-on');
							for(selectedValue of selectedValues){
								tlSrvyAns 	= new Object();
	
	
								tlSrvyAns.srvyrsltId 	= srvyrsltId;
								tlSrvyAns.sectType 		= sectType;
								tlSrvyAns.sectSqno 		= sectSqno;
								tlSrvyAns.qstnTitle 	= qstnTitle;
								tlSrvyAns.qstnType 		= qstnType;
								tlSrvyAns.qstnSqno 		= qstnSqno;
								tlSrvyAns.srvyMetadataCd =srvyMetadataCd;
	
								ansCnts 				= selectedValue.dataset.ansCnts;
								etcYn 					= selectedValue.dataset.etcYn;
								if(etcYn === 'Y'){
									ansCnts 			= surveyInfoElement.querySelector('.survey-input-direct').value;
								}
								tlSrvyAns.ansCnts 		= ansCnts;
								tlSrvyAns.etcYn 		= etcYn;
	
								tlSrvyAnsList.push(tlSrvyAns);
							}
					}else{
						//값 1개인경우
						if(qstnType === 'QTC001' || qstnType === 'QTC005' || qstnType === 'QTC007'){
							ansCnts = surveyInfoElement.querySelector('input[name="ansCnts"]').value;
						}else if(qstnType === 'QTC002'){ //라디오
							let checkImgOn = surveyInfoElement.querySelector('.check-img-on');
							if(checkImgOn){
								ansCnts = checkImgOn.dataset.ansCnts;
								etcYn 	= checkImgOn.dataset.etcYn;
								if(etcYn === 'Y'){
									ansCnts	= surveyInfoElement.querySelector('.survey-input-direct').value;
								}
							}
						}else if(qstnType === 'QTC004'){ //시간
							let hour = surveyInfoElement.querySelector('.select-hour').value;
							let minute = surveyInfoElement.querySelector('.select-minute').value;
							
							if(!isNull(hour) && !isNull(minute)) {
								//getToday 수정해야함 selectDate들어간다면
								ansCnts = getToday() + " " + hour+":"+minute+":00";
								let reviewTimeValue = ansCnts
								tlSrvyAns.reviewTime = reviewTimeValue; 
								if(transferCdArr.includes(srvyMetadataCd)){
									const TransferTime = getTransferTime(survyQuestionElelment,srvyMetadataCd,ansCnts);
									ansCnts = ansCnts + "," + TransferTime;
								}
	
							}
						}else if(qstnType === 'QTC006'){ //드롭다운
							ansCnts = surveyInfoElement.querySelector('select[name="ansCnts"]').value;
						}else if(qstnType === 'QTC008'){ //GPS
							let gpsLng = surveyInfoElement.querySelector('.gpsLng').value;
							let gpsLat = surveyInfoElement.querySelector('.gpsLat').value;
							if(!isNull(gpsLng) && !isNull(gpsLat)) ansCnts = gpsLng +","+ gpsLat //경,위도
						}
						tlSrvyAns.srvyMetadataCd= srvyMetadataCd;
						tlSrvyAns.ansCnts 		= ansCnts;
						tlSrvyAns.etcYn 		= etcYn;
	
						tlSrvyAnsList.push(tlSrvyAns);
					}
				}
			}
	
			let tlSrvyRslt = {};
			tlSrvyRslt.exmnrsltId = exmnrsltId;
			tlSrvyRslt.srvyrsltId = srvyrsltId;
			tlSrvyRslt.pollsterLc = document.getElementById('pollsterLc').value;
	
			const startlcNm = document.getElementById('startlcNm').value;
			tlSrvyRslt.startlcNm = isNull(startlcNm) ? null : startlcNm;
			const endlcNm = document.getElementById('endlcNm').value;
			tlSrvyRslt.endlcNm = isNull(endlcNm) ? null : endlcNm;
	
			tlSrvyRslt.exmnstartDt = document.getElementById('exmnstartDt').value;
			tlSrvyRslt.exmnendDt = document.getElementById('exmnendDt').value;
			tlSrvyAnsSaveDTO.tlSrvyRslt = tlSrvyRslt;
	
			tlSrvyAnsSaveDTO.tlSrvyAnsList = tlSrvyAnsList;
			
	
	   
			const resultObj = tlSrvyAnsSaveDTO.tlSrvyAnsList;
			const groupedData = resultObj.reduce((acc, current) => {
			    if (!acc[current.sectSqno]) {
			        acc[current.sectSqno] = [];
			    }
			    acc[current.sectSqno].push(current);
			    return acc;
			}, {});
			
			
			const confirmContainer = document.getElementById('surveyConfirm');
			let confirmWrap = document.createElement('div');
			confirmWrap.classList.add('surveyConfirmWrap');
			let confirmBg = document.createElement('div');
			confirmBg.classList.add('surveyConfirmBg');
			
			let html = `
			    <div class="survey-confirm-info">
			        <div class="survey-confirm-img"><img src="/images/confirm.png" alt="confirm"/></div>
			        <div class="survey-confirm-text">${message.survey.survey_js_confirm_answer}</div>
			    </div>
			    <div class="survey-confirm-content scroll">
			`;
			
			const sectionTitleIndexes = {};
			let validHtml = '';
			for (const [sectSqno, sectAnswers] of Object.entries(groupedData)) {
				
			    let previousSectTitle = null;
			    let startPlace = "";
			    let motoCnt = 0;
			    let tuktukCnt = 0;
			    let carCnt = 0;
			    let sectTitle = '';
			    for (const answer of sectAnswers) {
//					console.log(answer)
					if(answer.srvyMetadataCd === 'SMD050') startPlace = answer.ansCnts;
					if(answer.srvyMetadataCd === 'SMD009') motoCnt = Number(answer.ansCnts);
					if(answer.srvyMetadataCd === 'SMD010') tuktukCnt = Number(answer.ansCnts);
					if(answer.srvyMetadataCd === 'SMD011') carCnt = Number(answer.ansCnts);
					
			        if (previousSectTitle !== answer.sectTitle) {
			            if (!sectionTitleIndexes[answer.sectTitle]) {
			                sectionTitleIndexes[answer.sectTitle] = 0;
			            }
			            sectionTitleIndexes[answer.sectTitle]++;
			
			            let currentSectTitle = answer.sectTitle;
			            if (sectionTitleIndexes[answer.sectTitle] > 1) {
			                currentSectTitle += ` [${sectionTitleIndexes[answer.sectTitle]}]`;
			            }
			
			            html += `<div class="survey-confirm-title"><h3>${currentSectTitle}</h3></div>`;
			            previousSectTitle = answer.sectTitle;
			        }
			        const validAnswers = sectAnswers.filter(a => a.qstnTitle === answer.qstnTitle && a.ansCnts !== null && a.ansCnts !== "");
			        
			        if (validAnswers.length > 0) {
						
			            html += `   <div class="survey-confirm-item">
			                            <div class="survey-confirm-number"><span>${validAnswers[0].qstnSqno}</span></div>
			                            <dl>
			                                <dt>${answer.qstnTitle}</dt>`;
			            for (const validAnswer of validAnswers) {
							let ansCtns = validAnswer.ansCnts;
							if(validAnswer.qstnType === "QTC004") ansCtns = validAnswer.reviewTime;
			                html += `       <dd>
			                                    <img src="/images/confirm_check.png" alt="check" class="survey-confirm-check-img"/>
			                                    <span>${ansCtns}</span>
			                                </dd>`;
			            }
			            html += `       </dl>
			                        </div>`;
			        }
			        
			        
			        if(answer.srvyMetadataCd === 'SMD048'){
						const forCheckValues = ["School", "Going home"];
						const forCheckValueObject = {
							"School" : ['School'],
							"Going home" : ["Home"]
						}
						if(forCheckValues.indexOf(startPlace) == -1 || forCheckValueObject[startPlace].indexOf(answer.ansCnts) == -1) {
							if(isNull(sectTitle)){
								sectTitle = `<div class="warning__title">${answer.sectTitle}[${answer.sectSqno}]</div>`;
								validHtml += sectTitle; 
							}
							validHtml += `	    <div class="warning__list">
											        <div class="warning__list_title">${message.survey.survey_js_purpose_trip_and_final_destination}</div>
											        <div class="warning__list_con">${message.survey.survey_js_confirm_purpose_and_destination(startPlace, answer.ansCnts)}</div>
											    </div>
										 `
						}
					}
					
					//교통 탑승 메타데이터 코드 
			        if(answer.srvyMetadataCd === 'SMD024'|| answer.srvyMetadataCd === 'SMD029' || answer.srvyMetadataCd === 'SMD033'
			        || answer.srvyMetadataCd === 'SMD037' || answer.srvyMetadataCd === 'SMD041' || answer.srvyMetadataCd === 'SMD045'){
						
						if(answer.ansCnts === 'Motorcycle' && motoCnt === 0){
							if(isNull(sectTitle)){
								sectTitle = `<div class="warning__title">${answer.sectTitle}[${answer.sectSqno}]</div>`;
								validHtml += sectTitle; 
							}
							validHtml += `	
										  <div class="warning__list">
											<div class="warning__list_title">${answer.qstnTitle}</div>
											<div class="warning__list_con">${message.survey.survey_js_confirm_motorcycle}</div>
										  </div>
										 `
						}
						if(answer.ansCnts === 'Three Wheeler(Tuk Tuk)' && tuktukCnt === 0){
							if(isNull(sectTitle)){
								sectTitle = `<div class="warning__title">${answer.sectTitle}[${answer.sectSqno}]</div>`;
								validHtml += sectTitle; 
							}
							validHtml += `
										  <div class="warning__list">
											<div class="warning__list_title">${answer.qstnTitle}</div>
											<div class="warning__list_con">${message.survey.survey_js_confirm_tuktuk}</div>
										  </div>
										 `
						}
						if(answer.ansCnts === 'Car' && carCnt === 0){
							if(isNull(sectTitle)){
								sectTitle = `<div class="warning__title">${answer.sectTitle}[${answer.sectSqno}]</div>`;
								validHtml += sectTitle; 
							}
							validHtml += `
											<div class="warning__list">
											  <div class="warning__list_title">${answer.qstnTitle}</div>
											  <div class="warning__list_con">${message.survey.survey_js_confirm_car}</div>
											</div>
										 `
						}
					}
					
			    }
			    
			    
			}
			html += `</div>`
			html += `<div id="warningWrap">`;
			html += `<div style="background: #366ccd;color: white;padding: 3px 8px;border-radius: 4px;margin-bottom: 4px;">Please recheck your questionnaire answers.</div>`;
			if(!isNull(validHtml)) html += validHtml;
			html += `</div>`
			html += `
					<div class="survey-confirm-footer">
			            <input type="button" value="${message.common.button_update}/${message.common.button_cancel}" class="survey-confirm-btn" onclick="surveyConfirmClose();"/>
			            <input type="button" value="${message.common.button_confirm}" id="surveyQusetConfirmSaveBtn" class="survey-confirm-color-btn"/>
			        </div>
					`;
			
			
			confirmWrap.insertAdjacentHTML('beforeend', html);
			
			document.body.appendChild(confirmBg);
			confirmContainer.appendChild(confirmWrap);
			
			document.body.classList.add('scroll-hidden');
			confirmContainer.classList.add('active');
			
			const warning = document.getElementById('warningWrap');
			
			if(warning.children.length > 0){
				warning.style.display ="block";
			} else {
				warning.style.display ="none";
			} 
			
			
		    document.getElementById('surveyQusetConfirmSaveBtn').addEventListener('click', () => {
		        const loading = new setLoading().start();
		        fetch(__contextPath__+"/invstmng/question",{
		            method: "POST",
		            headers: {
		                'Content-Type': 'application/json;charset=utf-8',
		                'X-Requested-With': 'XMLHttpRequest'
		            },
		            body: JSON.stringify(tlSrvyAnsSaveDTO)
		        })
		        .then(response => response.json())
		        .then(result => {
		            if(result.code == '200'){
		                surveyConfirmClose();
		                new MsgModalBuilder().init().successBody(result.message).footer(4,message.common.button_confirm,function(button, modal){
		                    localStorage.removeItem(paramQuestionInfo.pollsterTel)
		                    window.location.href = __contextPath__+"/main";
		                    modal.close();
		                }).open();
		            }else if(result.code == '401'){ //조사시간 만료
		                new MsgModalBuilder().init().successBody(result.message).footer(4,message.common.button_confirm,function(button, modal){
		                    localStorage.removeItem(paramQuestionInfo.pollsterTel)
		                    window.location.href = __contextPath__+"/login";
		                    modal.close();
		                }).open();
		            }else{
		                new MsgModalBuilder().init().alertBody(result.message).footer(4,message.common.button_confirm,function(button, modal){
		                    window.location.href = __contextPath__+"/main";
		                    modal.close();
		                }).open();
		            }
		        })
		        .catch((error) => {
		            console.error('Error:', error);
		        })
		        .finally(() => {
		            loading.end();
		        });
			})
		}
	} 
}



//confrim remove
let surveyConfirmClose = () => {
	document.body.classList.remove('scroll-hidden');
	document.getElementById('surveyConfirm').classList.remove('active');
	document.querySelector('.surveyConfirmBg').remove();
	document.querySelector('.surveyConfirmWrap').remove();
}

//조사 5분전 알림
function fiveMinutesAgo() {
	const checkMinutes = [10, 25, 40, 55];
	const pushHTML = document.getElementById('fiveMinutesAgoBox');
	const startTime = document.getElementById('fiveStartTime');
	const endTime = document.getElementById('fiveEndTime');
	let delay = getNextCheckTime(new Date(), checkMinutes) - new Date();
	
	setTimeout(() => {
		const now = new Date();
		const nowMinutes = now.getMinutes();
		
		pushHTML.classList.add('active');
		fiveMinutesAgo();
		
		if(0 <= nowMinutes && nowMinutes <= 15){
			startTime.textContent = '00';
			endTime.textContent = '15';
		} else if(16 <= nowMinutes && nowMinutes <= 30) {
			startTime.textContent = '15';
			endTime.textContent = '30';
		} else if(31 <= nowMinutes && nowMinutes <= 45) {
			startTime.textContent = '30';
			endTime.textContent = '45';
		} else if(46 <= nowMinutes && nowMinutes <= 59) {
			startTime.textContent = '45';
			endTime.textContent = '60';
		}
		
		//푸시 5초 후 제거 
		setTimeout(() => {
			pushHTML.classList.remove('active');
		}, 5000)
		
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
		
		const loading = new setLoading().start();
		fetch(__contextPath__+"/invstmng/counting",{
			method: "POST",
			headers: {
				'Content-Type': 'application/json;charset=utf-8',
				'X-Requested-With': 'XMLHttpRequest'
			},
			body: JSON.stringify(tlTrfvlList)
		})
		.then(response => response.json())
		.then(result => {
			if(result.code == '200'){
				//로컬스토지 초기화
				const checkMinutes = [00, 15, 30, 45];
				const now = new Date();
				let nextTime = getNextCheckTime(now,checkMinutes);
				setLocalStorage(nextTime);
				new MsgModalBuilder().init().successBody(result.message).footer(4,message.common.button_confirm,function(button, modal){
					window.location.href = __contextPath__+"/main";
					modal.close();
				}).open();
			}else if(result.code == '401'){ //조사시간 만료
				new MsgModalBuilder().init().alertBody(result.message).footer(4,message.common.button_confirm,function(button, modal){
					localStorage.removeItem(paramCountingInfo.exmnmngId);
					window.location.href = __contextPath__+"/login";
					modal.close();
				}).open();
			}else{
				new MsgModalBuilder().init().alertBody(result.message).footer(4,message.common.button_confirm,function(button, modal){
					window.location.href = __contextPath__+"/main";
					modal.close();
				}).open();
			}
		})
		.catch(error => {
			console.error('Error:', error);
			return;
		}).finally(() => {
			loading.end();
		});
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


/* LocalStorage */
function setLocalStorage(paramSaveDt){
	let localStorageVlaueObj = new Object();
	let mvmnmeanTypeCd = paramCountingInfo.mvmnmeanTypeCd;
	for (let key in mvmnmeanTypeCd) {
	    if (mvmnmeanTypeCd.hasOwnProperty(key)) {
	        localStorageVlaueObj[mvmnmeanTypeCd[key]] = 0;
	    }
	}
	let expireDt = new Date();
	expireDt = expireDt.setDate(expireDt.getDate()+1);
	localStorageVlaueObj.startlcNm	= paramCountingInfo.startlcNm;
	localStorageVlaueObj.endlcNm	= paramCountingInfo.endlcNm;
	localStorageVlaueObj.saveDt 	= toLocalDateTime(paramSaveDt);
	localStorageVlaueObj.expireDt 	= new Date(expireDt);
	localStorageVlaueObj 			= JSON.stringify(localStorageVlaueObj)

	window.localStorage.setItem(paramCountingInfo.exmnmngId,localStorageVlaueObj);
}

//설문형 스토리지 저장
let setQuestStorageData = (e, elType = 'input') => {
	const container = document.getElementById('surveyWrap')
	const copyHTML = document.createElement('div');
	
    if(elType == 'input') {
	    e.setAttribute('value', e.value)
	} else if(elType == 'radio') {
		const directSiblingEl = e.closest('label').querySelector('.survey-input-direct');
		const directEl = e.closest('ul').querySelector('.survey-input-direct');
		if(!e.classList.contains('added-event')) {
			e.closest('ul').querySelectorAll('input[type="radio"]').forEach(x => {
				x.removeAttribute('checked');
			})
			e.setAttribute('checked', true);
			e.closest('ul').querySelectorAll('.check-img').forEach(img => {
				img.classList.remove('check-img-on');
			})
			e.nextElementSibling.classList.add('check-img-on');
			if(directEl) {
				if(!directSiblingEl) {
					directEl.removeAttribute('value');
					directEl.classList.remove('validation-tag');
					directEl.readOnly = true;
				}
			}
		} 
	} else if(elType == 'select') {
		e.querySelectorAll('option').forEach(o => {
			o.removeAttribute('selected')
			if(e.value == o.value) o.setAttribute('selected', true);
		})
	} else if(elType == 'checkbox') {
		const checkDirectSiblingEl = e.closest('label').querySelector('.survey-input-direct');
		if(e.checked == true) {
			e.setAttribute('checked', true);
			e.nextElementSibling.classList.add('check-img-on')
		} else {
			e.removeAttribute('checked')
			e.nextElementSibling.classList.remove('check-img-on')
			if(checkDirectSiblingEl) {
				checkDirectSiblingEl.removeAttribute('value');
				checkDirectSiblingEl.classList.remove('validation-tag');
			}
		}
	} else if(elType == 'date') {
		e.setAttribute('value', e.value);
	}
	
    copyHTML.appendChild(container.cloneNode(true));
    localStorage.setItem(paramQuestionInfo.pollsterTel, copyHTML.innerHTML);
    localStorage.setItem(paramQuestionInfo.pollsterTel+"_trip_count", document.querySelectorAll(".survy-question-wrap[data-sect-type='STC003']").length);    
}


function setLocalStorageData(type, trfvlm, paramSaveDt){
	if (!checkLocalStorage(paramSaveDt)) {
		const localStorageValue = window.localStorage.getItem(paramCountingInfo.exmnmngId);
		let trfvlmInfo = JSON.parse(localStorageValue);
		for (let key in trfvlmInfo) {
		    if (key === type) {
		        trfvlmInfo[type] = parseInt(trfvlm);
		    }
		}
		window.localStorage.setItem(paramCountingInfo.exmnmngId,JSON.stringify(trfvlmInfo));
	}
}

//로컬 스토리지 체크
function checkLocalStorage(paramSaveDt) {
    let checkNullValue = false;
    const countingLoaclStorage = window.localStorage.getItem(paramCountingInfo.exmnmngId); 
    if(isNull(countingLoaclStorage)) {
		checkNullValue = true;
	}else{
		let trfvlmInfo = JSON.parse(countingLoaclStorage);
		if(trfvlmInfo.saveDt !== toLocalDateTime(paramSaveDt)
			|| trfvlmInfo.startlcNm !== paramCountingInfo.startlcNm 
			|| trfvlmInfo.endlcNm !== paramCountingInfo.endlcNm) checkNullValue = true;
	}
    return checkNullValue;
}

function setCountingData(paramSaveDt){
	
	const localStorageValue = window.localStorage.getItem(paramCountingInfo.exmnmngId);
	let trfvlmInfo = JSON.parse(localStorageValue);
	for(let trfvlmItem in trfvlmInfo){
		if(trfvlmInfo.saveDt === toLocalDateTime(paramSaveDt)
			&& trfvlmInfo.startlcNm === paramCountingInfo.startlcNm 
			&& trfvlmInfo.endlcNm === paramCountingInfo.endlcNm){
				if(typeof(trfvlmInfo[trfvlmItem]) === "number"){
					document.querySelector(".counting-value[data-type='"+trfvlmItem+"']").value = trfvlmInfo[trfvlmItem];
				}
		}
	}
}

function getTransferTime(survyQuestionElelment,srvyMetadataCd,transferDate){
	let returnTime = "00:00";
	if(!isNull(transferDate)){
		let beforTimeElement;
		let hour;
		let minute;
		switch (srvyMetadataCd){
			case "SMD028":
				beforTimeElement = survyQuestionElelment.querySelector('[data-metadata="SMD023"]').closest('.survey-info-wrap');
				break;
			case "SMD032":
				beforTimeElement = survyQuestionElelment.querySelector('[data-metadata="SMD028"]').closest('.survey-info-wrap');
				break;
			case "SMD036":
				beforTimeElement = survyQuestionElelment.querySelector('[data-metadata="SMD032"]').closest('.survey-info-wrap');
				break;
			case "SMD040":
				beforTimeElement = survyQuestionElelment.querySelector('[data-metadata="SMD036"]').closest('.survey-info-wrap');
				break;
			case "SMD044":
				beforTimeElement = survyQuestionElelment.querySelector('[data-metadata="SMD040"]').closest('.survey-info-wrap');
				break;
			default:
				break;
		}

		hour = beforTimeElement.querySelector('.select-hour').value;
		minute = beforTimeElement.querySelector('.select-minute').value;
		const beforDate = getToday() + " " + hour+":"+minute+":00";
		returnTime = getTimeDifference(beforDate,transferDate);
	}
	return returnTime;
}

function removeExpireLocalStorage(){
	const nowDate = new Date();
	for (let i = 0; i < localStorage.length; i++) {
		let removeCheck = false;
		let key = localStorage.key(i);
		let value = localStorage.getItem(key);
		if(isValidJSON(value)){
			let localStorageValue = JSON.parse(value);
			if(localStorageValue.expireDt){
				if(new Date(localStorageValue.expireDt).getTime() <= nowDate.getTime()){
					removeCheck = true;
				}
			}else{
				if(key.length == 32){
					window.localStorage.removeItem(key);
				}
			}
		}
		if(removeCheck){
			window.localStorage.removeItem(key);
		}

	}
}

//환승 내용 추가 되면 전부 필수값으로 바꾸기

