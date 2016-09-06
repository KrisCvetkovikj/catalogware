
directive.directive('star', function($popover) {
	return {
		restrict: 'A',
		link: function(scope, element, attrs) {
			
			element.on("mouseover", function() {
				addStars();
			});

			element.on("mouseleave", function() {				
				removeStars();
			});			

			var addStars = function() {
				element.removeClass('fa-star-o').addClass('fa-star');				
				element.prevAll().removeClass('fa-star-o').addClass('fa-star');				
				// element.nextAll().removeClass('fa-star').addClass('fa-star-o');		
			}

			var removeStars = function() {				
				element.not('.selected').removeClass('fa-star').addClass('fa-star-o');
				element.prevAll('i').not('.selected').removeClass('fa-star').addClass('fa-star-o');				
			}
		}
	}
});