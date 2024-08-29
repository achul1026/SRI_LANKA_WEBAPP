function validation(_this){
   	let container = document.getElementById(`${_this}`)
	let success = true;
	
   	const elements = container.querySelectorAll('.validation-tag');
   	for(const element of elements){
    	let elementValue = element.value;
        let dataValidation = element.dataset.validation;
        let specialValidation = element.dataset.typeValidation
        
        if(isNull(elementValue)){
			if(dataValidation != 'radioCheckbox' && dataValidation != 'select') {
				new ModalBuilder().init().alertBody(message.validation.validation_js_null).footer(4,message.common.button_confirm,function(button, modal){
					element.classList.add('validation');
					element.focus();
					modal.close();
				}).open();
	            success = false;
	            break;
			}
		}
		if(dataValidation === 'select') {
			if(isNull(elementValue)){
				new ModalBuilder().init().alertBody(message.validation.validation_js_null).footer(4,message.common.button_confirm,function(button, modal){
					/*element.classList.add('validation');*/
					element.focus();
					modal.close();
				}).open();				
				success = false;
				break;
			}
		}
		if(dataValidation === 'radioCheckbox'){
			const checkedItem  = element.querySelectorAll('.survey-check');
			const result = Array.from(checkedItem).some(c => c.checked);
			if(!result) {
				new ModalBuilder().init().alertBody(message.validation.validation_js_null).footer(4,message.common.button_confirm,function(button, modal){
					const scrollValue = element.offsetTop - 120;
					window.scrollTo({top:scrollValue, behavior:'smooth'});
					modal.close();
				}).open();				
				success = false;
				break;
			}
		}
		if(specialValidation === 'SMD004') {
			if(element.value == 0) {
				new ModalBuilder().init().alertBody(message.validation.validation_js_check).footer(4,message.common.button_confirm,function(button, modal){
					element.classList.add('validation');
					element.focus();
					modal.close();
				}).open();
				success = false;
				break;				
			}
		}
		if(specialValidation === 'SMD005') {
			const container = element.closest('.invst-slide-wrap');
			const householdMember = container.querySelector("[data-type-validation='SMD004']");
			if(element.value >= householdMember.value) {
				new ModalBuilder().init().alertBody(message.validation.validation_js_check_compare).footer(4,message.common.button_confirm,function(button, modal){
					element.classList.add('validation');
					element.focus();
					modal.close();
				}).open();
				success = false;
				break;	
			}
		}
    }
    return success;
}


let valid = (_this) => {
	_this.classList.remove('validation')
}
