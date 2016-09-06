
service.factory('Product', Product);

function Product($resource) {

	var transformRequest = function(data) {
		var formData = new FormData();
        formData.append('product', new Blob([angular.toJson(data.product)], {
            type: "application/json"
        }));			        
        formData.append("image", data.image);
        return formData;
	}

	return $resource("/api/products/:id/:path/:param", {id: "@id"}, {
		save: {
			method: 'POST',
			headers: {'Content-Type': undefined },
			transformRequest: transformRequest
		},
		update: {
			method: 'POST',
			headers: {'Content-Type': undefined },
			transformRequest: transformRequest
		},
		authUserLike: {
			method: "GET",			
			params: {
				path: "likes",
				param: "me"
			},
			ignore: [401, 403]
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
			},
			ignore: [401, 403]
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