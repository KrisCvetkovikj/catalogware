
directive.directive('match', function($parse) {
	return {
		restrict: 'A',
		require: 'ngModel',
		scope: {
			otherModelValue: "=match"
		},
		link: function(scope, element, attr, ctrl) {
			if(ctrl) {
				ctrl.$validators.match = function(viewValue) {
					return !ctrl.$isEmpty(viewValue) && viewValue === scope.otherModelValue;
				}

				scope.$watch('otherModelValue', function(value) {
					ctrl.$validate();
				});
			}
		}
	}	
});