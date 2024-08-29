const message = {
    main_test : 'test_kor',
    
    common : {
	    button_confirm : '확인',
	    button_cancel : '취소',
	    button_save : '저장',
	    button_update : '수정',
	    select : '선택',
	    button_search : '찾기',
	    button_regist : '등록',
	    hour : '\u0020시',
	    minute : '\u0020분'
    },
    
    main : {
    	respondent : '응답자',
    	car_type : '차종',
    	investigator : '조사원 이름',
    	survey_direction : '조사 방향',
    	survey_lcchgRsn : '조사 불가 사유',
    },
    
    date : {
    	nextText :'다음 달',
		prevText :'이전 달',
    	dayNames : ['월요일', '화요일', '수요일', '목요일', '금요일', '토요일', '일요일'],
	    dayNamesMin : ['일', '월', '화', '수', '목', '금', '토'],
	    monthNames : ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
	    yearSuffix : '년',
	    monthNamesWithAbbrev : ['1월(JAN)', '2월(FEB)', '3월(MAR)', '4월(APR)', '5월(MAY)', '6월(JUN)',
                           '7월(JUL)', '8월(AUG)', '9월(SEP)', '10월(OCT)', '11월(NOV)', '12월(DEC)']
    },
    
    invest : {
    	invstQuestionSave_gps_search : 'GPS 검색',
    	invstQuestionSave_location_select : '주소를 선택해주세요.',
    	invstQuestionSave_gps_matchFound : 'GPS 주소와 TAZ 주소가 일치하지 않습니다.',
    	invstQuestionSave_gps_map_select : '지도에서 GPS를 등록해주세요.',
    	invstQuestionSave_back_up : '설문을 작성하시던 데이터가 있습니다. 불러오시겠습니까?',
    	invstQuestionSave_first_transfer_arrival_time : '첫번째 환승 도착 시간',
    	invstQuestionSave_second_transfer_arrival_time : '두번째 환승 도착 시간',
    	invstQuestionSave_third_transfer_arrival_time : '세번째 환승 도착 시간',
    	invstQuestionSave_fourth_transfer_arrival_time : '네번째 환승 도착 시간',
    	invstQuestionSave_fifth_transfer_arrival_time : '다섯번째 환승 도착 시간',
    	invstQuestionSave_final_arrival_time : '최종 목적지 도착 시간',
    	invstQuestionSave_departure_time : '출발 시간',
    	invstQuestionSave_cant_faster : (start,end) => `${start}보다 ${end}가(이) 더 빠를 수 없습니다.`,
    	pollsterSave_location_point : '조사 지점',
    	pollsterSave_cordon_line : '경계선',
    	pollsterSave_toll_booth : '요금소'
    },
    
    survey : {
    	survey_js_circlecUserinOutCheck : '반경 내에서 등록해주세요. 반경 내 진입 불가 시 사유를 입력하세요.',
    	survey_js_selectedGnCd : '조사 위치를 Grama Niladhari 까지 선택해주세요.',
    	survey_js_validationCheck : '조사 문항을 모두 입력해주세요.',
    	survey_js_confirm_answer : '질문에 답변을 알맞게 작성했는지 확인하세요.',
    	survey_js_purpose_trip_and_final_destination : 'Purpose of Trip & Final Destination Type',
    	survey_js_confirm_purpose_and_destination : (purpose, destination) => `통행목적은 ${purpose} 입니다. 도착지가 ${destination} 맞는지 확인해주세요.`,
    	survey_js_confirm_motorcycle : 'Motorcycle을 보유 하지 않고 있습니다. 이동수단으로 Motorcycle을 이용하신게 맞는지 확인해주세요.',
    	survey_js_confirm_tuktuk : 'Three Wheeler(Tuk Tuk) 보유 하지 않고 있습니다. 이동수단으로 Three Wheeler(Tuk Tuk) 이용하신게 맞는지 확인해주세요.',
    	survey_js_confirm_car : 'Car 보유 하지 않고 있습니다. 이동수단으로 Car 이용하신게 맞는지 확인해주세요.',
    	survey_js_transfer_before : '상위 환승 질문을 먼저 입력해주세요.',
    	survey_js_transfer_after : '하위 환승 질문이 활성화 되어있습니다.'
    },
    
    location : {
    	location_js_search_location : '주소 찾기',
    	location_js_research_location : '주소를 다시 확인해주세요.'
    },
    
    validation : {
    	validation_js_null : '입력하지 않은 문항이 있습니다.',
    	validation_js_check : '총 가구원 수를 확인해주세요.',
    	validation_js_check_compare : '가구원 수와 미취학 아동수가 같거나 많을 수 없습니다.'
    },
    
    qusetsurvey : {
    	qusetsurvey_js_transfer_address : '환승지 주소',
    	qusetsurvey_js_departure_time : '출발 시간',
    	qusetsurvey_js_transportation : '이동수단',
    	qusetsurvey_js_passengers_number : '승객 수(버스 제외)',
    	qusetsurvey_js_transfer_remove : '환승 제거',
    	qusetsurvey_js_transfer_add : '환승 추가',
    }
}