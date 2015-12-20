/*
	About: Reverse Geocode Location
	Uses: Google Maps API
	<script src="https://maps.googleapis.com/maps/api/js?key=API_KEY"></script>
*/
function getPrimaryCityByCounty (_results) {
	var county;
	var resultsLength = _results.length;
	for (var i = 0; i < resultsLength; i++) {
		county = _results[i].address_components[0].long_name.toLowerCase();
		if (county === 'maricopa county' || county === 'pinal county' || county === 'gila county' || county === 'yavapai county') {
			return 'phoenix';
		} else if (county === 'pima county' || county === 'santa cruz county' || county === 'cochise county') {
			return 'tucson';
		} else if (i === resultsLength) {
			return 'no match';
		}
	}
}

//Reports Location to the callback function
function reportLocation (callback) {
	function error (err) {
		console.warn('ERROR(' + err.code + '): ' + err.message);
	};
	var options = {
		enableHighAccuracy: true,
		timeout: 5000,
		maximumAge: 0
	};
	navigator.geolocation.getCurrentPosition(function(pos){
		var lat = pos.coords.latitude;
		var lng = pos.coords.longitude;

		callback( {lat:lat, lng:lng} );

	}, error, options);
}

//Gets the return value of reportLocation
reportLocation (function(coords) {
	var geocoder = new google.maps.Geocoder;
	var latlng = {
		lat: parseFloat(coords.lat),
		lng: parseFloat(coords.lng)
	};

	//Google Geocoder
	geocoder.geocode(
		{'location': latlng},
		function (results, status) {
			if (status === 'OK')
			{
				primaryCity = getPrimaryCityByCounty(results);

				//Dynamically change the web content
				var phoneNumber = document.querySelector('#phoneNumber');
				var phone = document.querySelector('#phone');
				if (primaryCity === 'phoenix')
				{
					phoneNumber.innerHTML = '602-269-7246';
					phoneNumber.style.visibility = 'visible';
					phone.setAttribute('href', 'tel:1-602-269-7246');
				}
				else
				{
					phoneNumber.style.visibility = 'visible';
				}
			}
			else
			{
				console.log('Google Geocoder failed due to: ' + status);
			}
		}
	);//geocoder.geocode
});//reportLocation

