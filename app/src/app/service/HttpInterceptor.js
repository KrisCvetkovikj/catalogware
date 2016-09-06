
service.factory('HttpInterceptor', HttpInterceptor);

function HttpInterceptor($rootScope, $q, EVENTS) {

	/*
	 * Checks if the request error should be ignored.
     */
	var shouldIgnore = function(status, ignores) {
		if(angular.isArray(ignores)) {
			for (var i = 0; i < ignores.length; i++) {
				if(status === ignores[i])
					return true;
			}
		} else if(status === ignores) {
			return true;
		}		

		return false;
	}

	return {
		responseError: function(response) {			
			if(!shouldIgnore(response.status, response.config.ignore)) {				
				$rootScope.$broadcast({
					404: EVENTS.notFound,
					403: EVENTS.notAuthorized,
					401: EVENTS.notAuthenticated,
					500: EVENTS.internalServerError,
					405: EVENTS.methodNotAllowed				
				}[response.status]);
			}		

			return $q.reject(response);
		}
	}
}