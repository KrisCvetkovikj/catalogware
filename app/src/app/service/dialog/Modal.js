
service.service('Modal', Modal);

function Modal($modal) {

	this.create = function(title, templateUrl, controller, controllerAs) {
		return $modal({
			animation: 'am-fade-and-scale',
			title: title,
			contentTemplate: templateUrl,
			html: true,
			show: true,
			controller: controller,
			controllerAs: controllerAs			
		});
	}
}