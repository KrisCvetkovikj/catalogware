
service.factory('Category', Category);

function Category($resource) {

	return $resource("/api/categories", {});
}