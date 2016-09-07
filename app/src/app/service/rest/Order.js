
service.factory('Order', Order);

function Order($resource) {

	return $resource("/api/orders/:id/products", {id: "@id"});
}