//없어질소스
window.addEventListener('load', () => {
	
	let location = window.location.href;
	const title = document.getElementById('pageName');
	if(!isNull(title)){
		const pageType = title.dataset.pageType.toLowerCase();
		const exmmngId = title.dataset.exmnmngId;
		const prevButton = document.getElementById('headerPrevButton');
		
		switch(pageType) {
			case 'counting':
			case 'question':
				const headerButton = document.getElementById('headerNextButton');
				let onclickName = "invstCountingSave(\'"+exmmngId+"'\)";
				if(pageType === 'question'){
					onclickName = "invstQuestionSave(\'"+exmmngId+"'\)";
				}
				headerButton.setAttribute('onclick', onclickName);
				prevButton.href="javascript:history.back()";
			break;
			case 'detail':
				prevButton.href="javascript:history.back()";
			break;
			case 'inquiry':
				title.textContent = titleLocation[5].toUpperCase();
			break;
		}
	}
})


