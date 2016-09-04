
service.service('ErrorUtils', ErrorUtils);

function ErrorUtils() {
	
	var getErrors = function(errors, result) {
		angular.forEach(errors, function(error) {
			if(angular.isArray(error) || angular.isObject(error))
				getErrors(error, result);
			else
				result.push(error);
		});
	}

	/*
	 * Extracts the error messages from the response.
	 */
	this.getErrorsFromResponse = function(data) {
		result = [];
		data.errors && getErrors(data.errors, result);		
		return result;
	}

	/*
	 * Checks if the form field has error.
	 */
	this.fieldError = function(form, field) {		
		return (form.$submitted || field.$touched) && field.$invalid;
	}

	/*
	 * Checks if the form field is ok.
	 */
	this.fieldOk = function(form, field) {		
		return (form.$submitted || field.$touched) && field.$valid;
	}
}