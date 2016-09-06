
directive.directive('validFile', function(EVENTS) {
	return {
		restrict: 'A',
		require: 'ngModel',
		link: function(scope, element, attrs, ctrl) {
			element.bind('change', function(event){				
				var file = event.target.files[0];
				scope.$emit(EVENTS.imageSelected, {file: file});
				scope.$apply(function(){
					ctrl.$setViewValue(element.val());					
					ctrl.$render();					
				});
			});
		}
	}
});