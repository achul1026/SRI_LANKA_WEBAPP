function TransferSurvey(locationClassName){
	const surveyWrap = document.getElementById('surveyWrap');
	const boxHtml = `
					<div class="transfer-wrap">
						<div class="is-box">
							<h3 class="survey-sub-question-title"><span>Q1-2.</span>환승지 주소</h3>
							<div class="survey-loaction-search-box ${locationClassName}"></div>
							<div><input type="text" class="div-input wd100 locationSave" readonly/></div>
						</div>			
						<div class="is-box">
							<h3 class="survey-sub-question-title"><span>Q2-2.</span>출발시간</h3>
							<div class="select-time-box">
								<select class="select-list-box select-hour"></select>
								<select class="select-list-box select-minute"></select>
							</div>
						</div>
						<div class="is-box">
							<h3 class="survey-sub-question-title"><span>Q3-2.</span>이동수단</h3>
							<select class="select-list-box wd100 scroll">
								<option>TEST</option>
								<option>TEST</option>
							</select>
						</div>
						<div class="is-box">
							<h3 class="survey-sub-question-title"><span>Q4-2.</span>승객 수(버스 제외)</h3>
							<div><input type="text" class="is-input wd100"/></div>
						</div>
						<div class="transfer-add-wrap newTransfer">
							<label class="is-key-button transfer-add-box transferAddButton">
								<input type="checkbox" class="transferCheckBox">
								<span class="transfer-add">환승 제거</span>
							</label>
						</div>											
					</div>						
					`;
	surveyWrap.insertAdjacentHTML('beforeend', boxHtml);
	const transferWrap = document.querySelectorAll('.transfer-wrap');
	transferWrap.forEach((transferWrap, index) => {
		transferWrap.classList.add(`transferBox${index+1}`);
	})
	
	locationSearch(locationClassName);
	selectMinuteSet();
	
	// 환승wrapRemove, 환승buttonAdd&Remove
	const transferNewButton = document.querySelectorAll('.transfer-add-wrap');
	const transferDefalutButton = document.querySelector('.defaultTransfer');
	const transferDefalutCheckBox = document.querySelector('.defaultTransferCheckbox');
	transferNewButton.forEach(targetElement => {
		if(targetElement.classList.contains('newTransfer')){
			targetElement.addEventListener('change', (_this) => {
				_this.target.closest('.transfer-wrap').remove();
				transferDefalutButton.classList.remove('none');
				transferDefalutCheckBox.checked = false;
			})
		}
	})
}
/* 
환승 추가 버튼
<div class="transfer-add-wrap">
	<label class="is-key-button transfer-add-box" id="transferAddButton">
		<input type="checkbox" id="transferCheckBox">
		<span class="transfer-add">환승 추가</span>
	</label>
</div>
*/