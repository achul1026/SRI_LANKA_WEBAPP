let lng,lat;
let marker, userTurfJs, userMarker, turfJs = null;
let watchId;
let lastPosition = {lon:null, lat:null};
let gpsLocation, gpsLng, gpsLat, gpsTaz = null;
let mapboxglToken = 'pk.eyJ1IjoidGFleXUiLCJhIjoiY2xpbzVzcWphMDVlZzNlbndxbzQ4a20zMCJ9.Zy8tFFyQruKS8zQKTh3wKA';
const defalutLng = 79.94049913408065;
const defalutLat = 6.926852160270101;
const RDA_ENV = {
	"MARKERS" : [{"locationUrl" : "/images/map_location_marker.png", 
				  "userUrl" : "/images/map_user_marker.png"}]
}

function mapboxGl(mapLng = defalutLng, mapLat = defalutLat){
	return new Promise((resolve, reject) => {
		mapboxgl.accessToken = mapboxglToken;
	    const map = window.map = new mapboxgl.Map({
	        container: 'map',
	        style:  'mapbox://styles/mapbox/streets-v12',
	        minZoom:10,
	        maxZoom:18,
	        zoom:15,
	        center: [mapLng, mapLat],
        	/* 테스트 기간 바운드X
        	maxBounds: [
				[79.5, 5.8],
    			[81.9, 9.9]
    		]*/
	    });
		const scale = new mapboxgl.ScaleControl({
			maxWidth: 80,
			unit: 'metric'
		});
		map.addControl(scale, "bottom-right");
	    map.on('load', () => resolve(map));
	    map.on('error', error => reject(error));
	});
}


let mapSurveyType = (map, tazCode) => {
   
   
/*    fetch('/js/rda.tazs.geojson')
        .then(response => response.json())
        .then(data => {
            const filteredData = {
                ...data,
                features: data.features.filter(feature => feature.properties.RDA_New_1.toString().startsWith(tazCode))
            };
            
            map.addSource('rda-location', {
                'type': 'geojson',
                'data': filteredData
            });

        });*/
        
        console.log(tazCode)
        
        
        map.addSource("rda-location", {
	        "type": "vector",
	        "url": "mapbox://taeyu.aqs1v0r0",
	        "promoteId" : {"rdatazs":"RDA_New_1"}
    	});
    	
   
        // 조사해야 할 행정구역
        map.addLayer({
            'id': 'location',
            'type': 'fill',
            'source': 'rda-location',
            'source-layer': "rdatazs",
            'paint': {
                'fill-color': 'rgba(255, 134, 54, 0.3)',
                'fill-outline-color': '#FF8636'
            }
        });

        // 조사해야 할 행정구역 윤곽선
        map.addLayer({
            'id': 'location-outline',
            'type': 'line',
            'source': 'rda-location',
            'source-layer': "rdatazs",
            'paint': {
                'line-color': '#FF8636',
                'line-width': 3
            }
        });
		
		map.setFilter('location', ['==',['slice',['to-string',['get','RDA_New_1']],0,new String(tazCode).length], ['to-string',tazCode]]);
		map.setFilter('location-outline', ['==',['slice',['to-string',['get','RDA_New_1']],0,new String(tazCode).length], ['to-string',tazCode]]);
}

//설문조사 GPS
let surveyGps = (map) => {
    map.addSource("rda-location", {
        "type": "vector",
        "url": "mapbox://taeyu.aqs1v0r0",
        "promoteId" : {"rdatazs":"RDA_New_1"}
    });
	
    map.addLayer({
        'id': 'rda-location',
        'type': 'fill',
        'source': 'rda-location',
        'source-layer': "rdatazs",
        'paint': {
            'fill-color': 'rgba(0, 0, 0, 0)',
        }
    });
    
    map.on('click', function(e){
		gpsLng = e.lngLat.lng;
		gpsLat = e.lngLat.lat;
		
	    const features = map.queryRenderedFeatures(e.point, { layers: ['rda-location'] });
	    if (!features.length) return;
	    const feature = features[0];
	    gpsTaz = feature.properties.RDA_New_1;
	    
		//GPS 좌표등록
	    const el = document.createElement('img')
		el.src = RDA_ENV.MARKERS[0].locationUrl
		el.style.width = '26px'
		el.style.height = '36px'
		if(marker) marker.remove();
		marker = new mapboxgl.Marker(el, {offset : [0,-18]})
		.setLngLat([gpsLng, gpsLat])
	    .addTo(map);
	    
		//GPS 좌표등록한곳 주소 받아오기
		fetch(`https://api.mapbox.com/geocoding/v5/mapbox.places/${gpsLng},${gpsLat}.json?access_token=${mapboxgl.accessToken}`)
	    .then(response => response.json())
	    .then(data => {
			const mapLocation = data.features[0].place_name;
			gpsLocation = mapLocation.replace(', Sri Lanka', '');
	      	document.getElementById('modalGpsLocation').value = gpsLocation;
	    });					    
	})	    	
}
//맵 주소 검색
let setMapSearch = (map) => {
	const geocoder = new MapboxGeocoder({
	    accessToken: mapboxglToken,
	    mapboxgl: mapboxgl,
	    placeholder:'Please search the address',
	    language:'en',
	    countries: 'LK',
	    autocomplete: true,
	    /*types: 'address,poi,place,locality,neighborhood,region,district',*/
	    
	});
	
	geocoder.on('result', function(e){
		map.jumpTo({
			center:e.result.geometry.coordinates,
			zoom:13
		})
	})
		
	map.addControl(geocoder);
}

//zoom
let setMapZoomControl = (map) => {
	document.getElementById('zoomIn').addEventListener('click', () => map.zoomIn());
	document.getElementById('zoomOut').addEventListener('click', () => map.zoomOut());	
}

let mapTrafficType = (map, lng, lat, meters) => {
	// 조사해야할곳위치
	const el = document.createElement('img')
	el.src = RDA_ENV.MARKERS[0].locationUrl
	el.style.width = '26px'
	el.style.height = '36px'
	new mapboxgl.Marker(el, {offset : [0,-18]})
	.setLngLat([lng, lat])
    .addTo(map);
    
	// 조사할위치 반경
	turfJs = turf.circle([lng, lat], meters, {units:'meters'});
	map.addLayer({
		id:'circle',
		type: 'fill',
		source: {
			type:'geojson',
			data: turfJs
		},
		paint: {
			'fill-color':'#B1B1B1',
			'fill-opacity': 0.5
		}
	})
}

//내위치
let myLocation = (map) => {
	//최초 위치 getCurrentPosition
	navigator.geolocation.getCurrentPosition(function(position) {
		lng = position.coords.longitude;
        lat = position.coords.latitude;
        const el = document.createElement('img')
		el.src = RDA_ENV.MARKERS[0].userUrl
		el.style.width = '20px'
		el.style.height = '20px'
		userMarker = new mapboxgl.Marker(el)
		.setLngLat([lng, lat])
		.addTo(map);
		
        map.jumpTo({
            center: [lng, lat],
            zoom: 17
        }); 

	}, (error) => {
		console.error(error);
	}, {
	    enableHighAccuracy: true,
	    maximumAge: 0,
	    timeout: 27000		
	});
	
	//실시간 위치 감지 watchPosition
	navigator.geolocation.watchPosition(function(position) {
        lng = position.coords.longitude;
        lat = position.coords.latitude;
		if(userMarker) userMarker.setLngLat([lng, lat]);
		
	}, (error) => {
		console.error(error)
	}, {
	    enableHighAccuracy: true,
	    maximumAge: 0,
	    timeout: 27000
	});
	
	//현재 위치로
	const currentLocation = document.getElementById('currentLocation');
	currentLocation.addEventListener('click', () => {
		map.flyTo({
            center: [lng, lat],
            zoom: 17
    	});
	})
}

//맵제거
function mapRemove(map){
	if(map){
		map.remove();
		map = null;
	}
}