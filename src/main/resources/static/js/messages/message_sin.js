const message = {
    main_test : 'test_sin',
    
    common : {
	    button_confirm : 'චෙක් පත',
	    button_cancel : 'අවලංගු කිරීම',
	    button_save : 'සුරකින්න',
	    button_update : 'නිවැරදි කිරීම',
	    select : 'තෝරන්න',
	    button_search : 'සොයාගන්න',
	    button_regist : 'ලියාපදිංචි කිරීම',
	    hour : '\u0020පැය',
	    minute : '\u0020විනාඩි'
    },
    
    main : {
    	respondent : 'වගඋත්තරකරු',
    	car_type : 'කාර් වර්ගය',
    	investigator : 'පරීක්ෂක නම',
    	survey_direction : 'විමර්ශන දිශාව',
    	survey_lcchgRsn : 'විමර්ශනය කිරීමට නොහැකි වීමට හේතු',
    },
    
    date : {
    	nextText : 'ඊළඟ මාසය',
	    prevText : 'කලින් මාසය',
	    dayNames : ['සඳුදා', 'අඟහරුවාදා', 'බදාදා', 'බ්‍රහස්පතින්දා', 'සිකුරාදා', 'සෙනසුරාදා', 'ඉරිදා'],
	    dayNamesMin : ['සං', 'අඟ', 'බදා', 'බ්‍රහ', 'සිකු', 'සෙන', 'ඉරි'],
	    monthNames : ['ජනවාරි', 'පෙබරවාරි', 'මාර්තු', 'අප්‍රේල්', 'මැයි', 'ජූනි', 'ජූලි', 'අගෝස්තු', 'සැප්තැම්බර්', 'ඔක්තෝබර්', 'නොවැම්බර්', 'දෙසැම්බර්'],
	    yearSuffix : 'වර්ෂය',
	    monthNamesWithAbbrev : ['ජනවාරි (JAN)', 'පෙබරවාරි (FEB)', 'මාර්තු (MAR)', 'අප්‍රේල් (APR)', 'මැයි (MAY)', 'ජූනි (JUN)',
	                           'ජූලි (JUL)', 'අගෝස්තු (AUG)', 'සැප්තැම්බර් (SEP)', 'ඔක්තෝබර් (OCT)', 'නොවැම්බර් (NOV)', 'දෙසැම්බර් (DEC)']
	},
    
    invest : {
    	invstQuestionSave_gps_search : 'GPS සෙවීම',
    	invstQuestionSave_location_select : 'කරුණාකර ලිපිනයක් තෝරන්න.',
    	invstQuestionSave_gps_matchFound : 'GPS ලිපිනය සහ TAZ ලිපිනය නොගැලපේ.',
    	invstQuestionSave_gps_map_select : 'කරුණාකර ඔබගේ GPS සිතියම මත ලියාපදිංචි කරන්න.',
    	invstQuestionSave_back_up : 'ඔබ සමීක්ෂණය පිරවීමට භාවිතා කළ දත්ත මා සතුව ඇත. ඔබ එය පූරණය කිරීමට කැමතිද?',
    	invstQuestionSave_first_transfer_arrival_time : 'පළමු මාරු පැමිණීමේ වේලාව',
    	invstQuestionSave_second_transfer_arrival_time : 'දෙවන මාරු පැමිණීමේ වේලාව',
    	invstQuestionSave_third_transfer_arrival_time : 'තෙවන මාරු පැමිණීමේ වේලාව',
    	invstQuestionSave_fourth_transfer_arrival_time : 'හතරවන මාරු පැමිණීමේ වේලාව',
    	invstQuestionSave_fifth_transfer_arrival_time : 'පස්වන මාරු පැමිණීමේ වේලාව',
    	invstQuestionSave_final_arrival_time : 'අවසාන ගමනාන්තය පැමිණීමේ වේලාව',
    	invstQuestionSave_departure_time : 'පිටත්වීමේ වේලාව',
    	invstQuestionSave_cant_faster : (start,end) => `${end} ${start} ට වඩා වේගවත් විය නොහැක`,
    	pollsterSave_location_point : 'පරීක්ෂණ ස්ථානය',
    	pollsterSave_cordon_line : 'මායිම',
    	pollsterSave_toll_booth : 'ගාස්තු කුටිය'
    },
    
    survey : {
    	survey_js_circlecUserinOutCheck : 'කරුණාකර අරය තුළ ලියාපදිංචි වන්න. අරය තුළට ඇතුළු වීමට නොහැකි නම්, හේතුවක් ඇතුළත් කරන්න.',
    	survey_js_selectedGnCd : 'කරුණාකර ග්‍රාම නිලධාරී දක්වා මැනුම් ස්ථානය තෝරන්න.',
    	survey_js_validationCheck : 'කරුණාකර සියලුම සමීක්ෂණ ප්‍රශ්න ඇතුළත් කරන්න.',
    	survey_js_confirm_answer : 'ප්‍රශ්නවලට නිසි පිළිතුරු සැපයීමට වග බලා ගන්න.',
    	survey_js_purpose_trip_and_final_destination : 'Purpose of Trip & Final Destination Type',
    	survey_js_confirm_purpose_and_destination : (purpose, destination) => `ඡේදයේ අරමුණ ${purpose} වේ. කරුණාකර ගමනාන්තය ${destination} බව සහතික කර ගන්න.`,
    	survey_js_confirm_motorcycle : 'මට යතුරුපැදියක් නැහැ. ඔබ ඔබේ ප්‍රවාහන මාධ්‍ය ලෙස යතුරුපැදියක් භාවිතා කරන බව කරුණාකර තහවුරු කරන්න.',
    	survey_js_confirm_tuktuk : 'අපිට ත්‍රීවීල් (Tuk Tuk) අයිති නැහැ. ඔබ ඔබේ ප්‍රවාහන මාධ්‍ය ලෙස ත්‍රිරෝද රථය (Tuk Tuk) භාවිතා කරන බව කරුණාකර තහවුරු කරන්න.',
    	survey_js_confirm_car : 'මට වාහනයක් නැහැ. කරුණාකර ඔබ ඔබේ ප්‍රවාහන මාධ්‍ය ලෙස මෝටර් රථයක් භාවිතා කරන බවට වග බලා ගන්න.',
    	survey_js_transfer_before : 'කරුණාකර ඔබේ ඉහළම මාරු කිරීමේ ප්‍රශ්නය පළමුව ඇතුළත් කරන්න.',
    	survey_js_transfer_after : 'උපමාරු ප්‍රශ්න සක්‍රිය කර ඇත.'
    },
    
    location : {
    	location_js_search_location : 'ලිපිනය සොයන්න',
    	location_js_research_location : 'කරුණාකර ඔබගේ ලිපිනය නැවත පරීක්ෂා කරන්න.'
    },
    
    validation : {
    	validation_js_null : 'ඇතුළත් නොකළ ප්රශ්න තිබේ.',
    	validation_js_check : 'කරුණාකර නිවසේ මුළු සාමාජිකයින් සංඛ්‍යාව පරීක්ෂා කරන්න.',
    	validation_js_check_compare : 'ගෘහස්ථ සාමාජිකයින් සහ පෙර පාසල් ළමුන් සංඛ්යාව සමාන හෝ වැඩි විය නොහැක.'
    },
    
    qusetsurvey : {
    	qusetsurvey_js_transfer_address : 'මාරු ලිපිනය',
    	qusetsurvey_js_departure_time : 'පිටත්වීමේ වේලාව',
    	qusetsurvey_js_transportation : 'ප්රවාහනය',
    	qusetsurvey_js_passengers_number : 'මගීන් සංඛ්‍යාව (බස් රථ හැර)',
    	qusetsurvey_js_transfer_remove : 'මාරුවීම් ඉවත් කිරීම',
    	qusetsurvey_js_transfer_add : 'මාරු එකතු කරන්න',
    }
}