function getIp(){
	var localIp = "http://gamgyul.ga:9989/gams/";
	var awsIp = "http://gams.ga/";
	
	return localIp;
}

hello.init({
	google: '397205866796-hant6ua349hu2cok1n835rgo2h6ra7fa.apps.googleusercontent.com'
}, {redirect_uri: getIp()+'google/callback'});

function login(){
	hello('google').login({scope: 'email'}).then(function(auth) {
		hello(auth.network).api('/me').then(function(r) {
			console.log(JSON.stringify(auth));
			accessToken = auth.authResponse.access_token;
			console.log(accessToken);
			getGoogleMe(); // 로그인 하자마자 바로 사용자 정보 호출한다.
		});
	});
}

function getGoogleMe(){
	hello('google').api('me').then(
			function(json) {
				console.log(JSON.stringify(json));
				name = json.name;
	    		email = json.email;
	    		domain = json.domain;
	    		thumbnail = json.thumbnail;
				console.log('name   : ' + name);
	    		console.log('email  : ' + email);
	    		console.log('domain : ' + domain);
	    		console.log('thumbnail : ' + thumbnail);
	    		loginComplete();// JSNI에 정의 되어있다.
			}, 
			function(e) {
	    		console.log('me error : ' + e.error.message);
	    	});
}

function logout(){
	hello('google').logout().then(
			function() {
				console.log('logout');
			},
			function(e) {
				console.log('Signed out error: ' + e.error.message);
	    	});
}