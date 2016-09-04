
app.service('AuthResolver', AuthResolver);

function AuthResolver($rootScope, $q, EVENTS) {

	var checked = false;

	this.resolve = function() {
		var deferred = $q.defer();
		if (checked) {
			deferred.resolve(true);
		}
		var unwatch = $rootScope.$on(EVENTS.authChecked, function(event, data) {
			checked = true;
			deferred.resolve(true);
			unwatch();
		});
		return deferred.promise;
	}
}