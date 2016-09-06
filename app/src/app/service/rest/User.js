
service.factory('User', User);

function User($resource) {

	return $resource("/api/users/:id/:path/:param", {id: "@id"}, {
		authUserLikes: {
			method: "GET",
			isArray: true,
			params: {
				id: "me",
				path: "likes"
			}
		},
		authUserOrders: {
			method: "GET",
			isArray: true,
			params: {
				id: "me",
				path: "orders"
			}
		}
	});
}