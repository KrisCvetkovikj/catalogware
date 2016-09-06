
service.factory('Product', Product);

function Product($resource) {

	return $resource("/api/products/:id/:path/:param", {id: "@id"}, {
		authUserLike: {
			method: "GET",			
			params: {
				path: "likes",
				param: "me"
			}
		},
		addLike: {
			method: "POST",
			params: {
				path: "likes"
			}
		},
		updateLike: {
			method: "PUT",
			params: {
				path: "likes"
			}
		},
		removeLike: {
			method: "DELETE",
			params: {
				path: "likes"
			}
		},
		authUserBasket: {
			method: "GET",
			params: {
				path: "baskets",
				param: "me"
			}
		},
		addToBasket: {
			method: "POST",
			params: {
				path: "baskets"
			}
		},
		removeFromBasket: {
			method: "DELETE",
			params: {
				path: "baskets"
			}
		}
	});
}