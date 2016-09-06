
service.service('AuthUser', AuthUser);

function AuthUser(ROLES) {

	this.name = null;
	this.role = null;
	this.isAuthenticated = false;

	this.create = function(name, role) {
		this.name = name;
		this.isAuthenticated = true;
		if (role && /admin/i.test(role)) {
			this.role = ROLES.admin;
		} else {
			this.role = ROLES.customer;
		}
	}

	this.destroy = function() {
		this.name = null;
		this.role = null;
		this.isAuthenticated = false;
	}

	this.isAdmin = function() {		
		return this.isAuthenticated && this.role == ROLES.admin;
	}
}