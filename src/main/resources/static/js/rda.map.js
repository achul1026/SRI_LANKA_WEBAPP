let lon,lat;
let marker, userTurfJs, userMarker, turfJs = null;
let lastPosition = {lon:null, lat:null};

const RDA_ENV = {
	"MARKERS" : [{"locationUrl" : "/images/map_location_marker.png", 
				  "userUrl" : "/images/map_user_marker.png"}]
}

function mapboxGl(mapLon = 0, mapLat = 0, meters = 0){
	return new Promise((resolve, reject) => {
		mapboxgl.accessToken = 'pk.eyJ1IjoidGFleXUiLCJhIjoiY2xpbzVzcWphMDVlZzNlbndxbzQ4a20zMCJ9.Zy8tFFyQruKS8zQKTh3wKA';
	    const map = window.map = new mapboxgl.Map({
	        container: 'map',
	        style:  'mapbox://styles/mapbox/streets-v12',
	        minZoom:12,
	        maxZoom:18,
	        center: [mapLon, mapLat],
	        pitch: 0,
	    });
	    
		//조사해야할곳 위치
		const el = document.createElement('img')
		el.src = RDA_ENV.MARKERS[0].locationUrl
		el.style.width = '26px'
		el.style.height = '36px'
		new mapboxgl.Marker(el)
		.setLngLat([mapLon, mapLat])
	    .addTo(map);
	    
	    map.on('load', () => {
			resolve(map);
			
			// 조사할위치 반경
			turfJs = turf.circle([mapLon, mapLat], meters, {units:'meters'});
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
		})
		
		//최초 위치 getCurrentPosition		
		navigator.geolocation.getCurrentPosition(function(position) {
			lon = position.coords.longitude;
	        lat = position.coords.latitude;
	        
	        const el = document.createElement('img')
			el.src = RDA_ENV.MARKERS[0].userUrl
			el.style.width = '20px'
			el.style.height = '20px'
			userMarker = new mapboxgl.Marker(el)
			.setLngLat([lon, lat])
			.addTo(map);
			
			if(mapLon == 0 && mapLat == 0){
		        map.jumpTo({
		            center: [lon, lat],
		            zoom: 17
		        });
			} else {
		        map.flyTo({
		            center: [lon, lat],
		            zoom: 17
		        });				
			}			
			
		}, (error) => {
			console.error(error);
		}, {
		    enableHighAccuracy: true,
		    maximumAge: 0,
		    timeout: 27000			
		});
		
		//실시간 위치 감지 watchPosition, 
		navigator.geolocation.watchPosition(function(position) {
	        lon = position.coords.longitude;
	        lat = position.coords.latitude;
			
	        map.jumpTo({
	            center: [lon, lat],
	            zoom: 17
	        });
			/*if(mapLon == 0 && mapLat == 0){
			} else {
		        map.flyTo({
		            center: [lon, lat],
		            zoom: 17
		        });				
			}*/
			if(userMarker) userMarker.setLngLat([lon, lat]);
			
		}, (error) => {
			console.error(error)
		}, {
		    enableHighAccuracy: true,
		    maximumAge: 0,
		    timeout: 27000
		});
		
		//zoomControl
		const zoomIn = document.getElementById('zoomIn');
		const zoomOut = document.getElementById('zoomOut');
		zoomIn.addEventListener('click', () => {
			map.zoomIn();
		})
		zoomOut.addEventListener('click', () => {
			map.zoomOut();
		})
		
		//현재 위치로
		const currentLocation = document.getElementById('currentLocation');
		currentLocation.addEventListener('click', () => {
			map.flyTo({
	            center: [lon, lat],
	            zoom: 17
        	});
		})		
	});
}

/*function markerSet(el, lon, lat){
	if(lastPosition.lon !== lon || lastPosition.lat !== lat) {
		if(marker) marker.remove();
		marker = new mapboxgl.Marker(el)
		.setLngLat([lon, lat])
	    .addTo(map);
	    lastPosition = {lon, lat};
	}
}*/
