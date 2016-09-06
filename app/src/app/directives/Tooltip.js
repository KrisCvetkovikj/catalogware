
directive.directive('tooltip', function() {
	return {
		restrict: 'A',
		link: function(scope, element) {
			$(element).tooltip();
		}
	}
})