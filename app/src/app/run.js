
app.run(function(AuthService, editableOptions) {
	AuthService.authenticate();
	editableOptions.theme = "bs3";
});