
service.factory('Basket', Basket);

function Basket($resource) {

	return $resource("/api/basket/:path", {}, {
		checkout: {
			method: "POST",
			params: {
				path: "checkout"
			}
		}
	});
}