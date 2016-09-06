var app = angular.module('app', [
	'ngResource',
	'ngAnimate',
	'ngMessages',
	'ui.router',
	'angular-loading-bar',
	'mgcrea.ngStrap',
	'toastr',
	'angularMoment',
	'ui.select',
	'ngSanitize',
	'xeditable',
	'smart-table',	
	'satellizer',

	'app.controller',
	'app.service',
	'app.component',
	'app.directive',
	'app.filter'
]);

var controller = angular.module('app.controller', []);
var service = angular.module('app.service', []);
var component = angular.module('app.component', []);
var directive = angular.module('app.directive', []);
var filter = angular.module('app.filter', []);