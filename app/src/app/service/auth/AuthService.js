
service.factory('AuthService', AuthService);

function AuthService($rootScope, $q, $http, $auth, AuthUser, EVENTS) {
	var _this = this;

	this.login = function(credentials) {
		return $auth.login(credentials)
			.then(function(response) {
				var data = response.data;
				AuthUser.create(data.name, data.role);
				return true;
			})
			.catch(function(response) {				
				return $q.reject(response);
			});
	}

	this.register = function(credentials) {
		return $auth.signup(credentials)
			.then(function(response) {
				var data = response.data;
				AuthUser.create(data.name, data.role);				
				$auth.setToken(data.token);
				return true;
			})
			.catch(function(response) {				
				return $q.reject(response);
			});
	}

	this.logout = function() {
		$auth.logout();
		AuthUser.destroy();
		var deferred = $q.defer();
		deferred.resolve(true);
		return deferred.promise;
	}

	this.authenticate = function() {
		return $http.get("/api/auth/me", {ignore: 401})
			.then(function(response) {
				var user = response.data;
				AuthUser.create(user.name, user.role);				
				$rootScope.$broadcast(EVENTS.authChecked);
			}, function() {				
				$rootScope.$broadcast(EVENTS.authChecked);
				return $q.reject(false);
			});
	}

	return _this;
}