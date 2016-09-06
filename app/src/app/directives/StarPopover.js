
directive.directive('starPopover', function($popover) {
	return {
		restrict: 'A',
		scope: {
			rating: '=',
			rate: '&',
			popoverTitle: '@'
		},
		link: function(scope, element, attrs) {						
			$popover(element, {
                title: scope.popoverTitle,
	            contentTemplate: 'src/views/products/popover-like.html',
	            placement: 'auto',
	            html: true,	            
	            autoClose: true,
	            scope: scope
	        });        	   
		}
	}
});