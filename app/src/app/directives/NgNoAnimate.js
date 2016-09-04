
directive.directive('noAnimate', function($animate) {
	return {
		restrict: 'A',
		link: function(scope, element) {
			$animate.enabled(element, false);
		}
	}
});