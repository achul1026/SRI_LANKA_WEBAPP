const message = {
    main_test : 'test_eng',
    
    common : {
	    button_confirm : 'Confirm',
	    button_cancel : 'Cancel',
	    button_save : 'Save',
	    button_update : 'Modify',
	    select : 'Select',
	    button_search : 'Search',
	    button_regist : 'Regist',
	    hour : '\u0020hour',
	    minute : '\u0020minute'
    },
    
    main : {
    	respondent : 'Respondent',
    	car_type : 'Car Type',
    	investigator : 'Surveyor Name',
    	survey_direction : 'Road Direction',
    	survey_lcchgRsn : 'Reasons for not being able to survey',
    },
    
    date : {
    	nextText : 'Next Month',
	    prevText : 'Previous Month',
	    dayNames : ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'],
	    dayNamesMin : ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
	    monthNames : ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
	    yearSuffix : '',
	    monthNamesWithAbbrev : ['January (JAN)', 'February (FEB)', 'March (MAR)', 'April (APR)', 'May (MAY)', 'June (JUN)',
	                           'July (JUL)', 'August (AUG)', 'September (SEP)', 'October (OCT)', 'November (NOV)', 'December (DEC)']
	},
    
    invest : {
    	invstQuestionSave_gps_search : 'GPS Search',
    	invstQuestionSave_location_select : 'Please select an address.',
    	invstQuestionSave_gps_matchFound : 'GPS address and TAZ address do not match.',
    	invstQuestionSave_gps_map_select : 'Please register your GPS on the map.',
    	invstQuestionSave_back_up : 'I have the data you used to fill out the survey. Would you like to load it?',
    	invstQuestionSave_first_transfer_arrival_time : 'First transfer arrival time',
    	invstQuestionSave_second_transfer_arrival_time : 'Second transfer arrival time',
    	invstQuestionSave_third_transfer_arrival_time : 'Third transfer arrival time',
    	invstQuestionSave_fourth_transfer_arrival_time : 'Fourth transfer arrival time',
    	invstQuestionSave_fifth_transfer_arrival_time : 'Fifth transfer arrival time',
    	invstQuestionSave_final_arrival_time : 'Final destination arrival time',
    	invstQuestionSave_departure_time : 'Departure time',
    	invstQuestionSave_cant_faster : (start,end) => `${start} cannot be faster than ${end}.`,
    	pollsterSave_location_point : 'Location Point',
    	pollsterSave_cordon_line : 'Cordon Line',
    	pollsterSave_toll_booth : 'Toll Booth'
    },
    
    survey : {
    	survey_js_circlecUserinOutCheck : 'Please register within the radius. If entry within the radius is not possible, enter a reason.',
    	survey_js_selectedGnCd : 'Please select the survey location up to Grama Niladhari.',
    	survey_js_validationCheck : 'Please enter all survey questions.',
    	survey_js_confirm_answer : 'Make sure you answer the questions appropriately.',
    	survey_js_purpose_trip_and_final_destination : 'Purpose of Trip & Final Destination Type',
    	survey_js_confirm_purpose_and_destination : (purpose, destination) => `The purpose of travel is ${purpose}. Please check if the destination is ${destination}.`,
    	survey_js_confirm_motorcycle : 'I do not own a motorcycle. Please check if you are using a motorcycle as a means of transportation.',
    	survey_js_confirm_tuktuk : 'I do not own a Three Wheeler (Tuk Tuk). Please check if you are using a Three Wheeler (Tuk Tuk) as a means of transportation.',
    	survey_js_confirm_car : 'I do not own a car. Please check if you are using a car as a means of transportation.',
    	survey_js_transfer_before : 'Please enter your top transfer question first.',
    	survey_js_transfer_after : 'Subtransfer questions are activated.'
    },
    
    location : {
    	location_js_search_location : 'Find address',
    	location_js_research_location : 'Please check your address again.',
    },
    
    validation : {
    	validation_js_null : 'There are questions that have not been entered.',
    	validation_js_check : 'Please check the total number of household members.',
    	validation_js_check_compare : 'The number of household members and preschool children cannot be the same or more.'
    },
    
    qusetsurvey : {
    	qusetsurvey_js_transfer_address : 'Transfer address',
    	qusetsurvey_js_departure_time : 'Departure time',
    	qusetsurvey_js_transportation : 'Transportation',
    	qusetsurvey_js_passengers_number : 'Number of passengers (excluding buses)',
    	qusetsurvey_js_transfer_remove : 'Remove of transfers',
    	qusetsurvey_js_transfer_add : 'Add transfer',
    }
}