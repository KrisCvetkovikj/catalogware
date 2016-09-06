
directive.directive('uiSelectRequired', function() {
	return {
		restrict: 'A',
		require: 'ngModel',
		link: function(scope, element, attrs, ctrl) {
			ctrl.$validators.uiSelectRequired = function(modelValue) {				
				return modelValue && modelValue.length > 0;				
			}
		}
	}
});