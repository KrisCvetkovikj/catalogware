/**
* @Author
* Borce Petrovski
*/

var gulp = require('gulp');

//Used for concatenating files.
var concat = require('gulp-concat');

/**
* Used for adding revesion on .html files to 
* avoid caching the resources.The revision is addedd
* on ?rev=@@hash attributes.
*/
var rev = require('gulp-rev-append');

//Used for uglifing .js files.
var jsUglify = require('gulp-uglify');

//Used for uglifing .css files.
var cssNano = require('gulp-cssnano');

//User to start a lcoal server
var connect = require('gulp-connect');


//The locations of the libaries css files.
var CSS_LIB = [
	'src/libs/font-awesome/css/font-awesome.css',
	'src/libs/bootstrap/dist/css/bootstrap.css',	
	'src/libs/angular-loading-bar/build/loading-bar.css',
	'src/libs/angular-toastr/dist/angular-toastr.css',
	'src/libs/angular-motion/dist/angular-motion.css',
	'src/libs/angular-ui-select/dist/select.css',
	'src/libs/angular-xeditable/dist/css/xeditable.css'
];

//The locations of the libaries js files.
var JS_LIB = [
	'src/libs/jquery/dist/jquery.js',
	'src/libs/bootstrap/dist/js/bootstrap.js',
	'src/libs/angular/angular.js',
	'src/libs/angular-resource/angular-resource.js',
	'src/libs/angular-animate/angular-animate.js',	
	'src/libs/angular-messages/angular-messages.js',
	'src/libs/angular-ui-router/release/angular-ui-router.js',
	'src/libs/angular-loading-bar/build/loading-bar.js',	
	'src/libs/angular-toastr/dist/angular-toastr.tpls.js',
	'src/libs/angular-strap/dist/angular-strap.js',
	'src/libs/angular-strap/dist/angular-strap.tpl.js',
	'src/libs/moment/moment.js',
	'src/libs/angular-moment/angular-moment.js',
	'src/libs/angular-sanitize/angular-sanitize.js',
	'src/libs/angular-ui-select/dist/select.js',
	'src/libs/angular-xeditable/dist/js/xeditable.js',
	'src/libs/angular-smart-table/dist/smart-table.js',
	'src/libs/satellizer/dist/satellizer.js',
];

//The locations of the custom css files.
var CSS_CUSTOM = [
	'src/css/**/*.css'
];

//The locations of the app js files.
var JS_APP = [
	'src/app/**/*.js'
];

//The locations of the libaries fints files.
var FONTS_LIB = [
	'src/libs/bootstrap/dist/fonts/*.{ttf,woff,eot,svg,woff2}',
	'src/libs/font-awesome/fonts/*.{ttf,woff,eot,svg,woff2}',
];

//The html files used for revision.
var INDEX_FILE = 'index.html';

var INDEX_FILE_PATH = './';

//The location where the js files will be saved.
var JS_DESTINATION = 'dest/app/';

//The location where the css files will be saved.
var CSS_DESTINATION = 'dest/css/';

//The location where the font files will be saved.
var FONTS_DESTINATION = 'dest/fonts/';

//Reverse Proxy route
var API_URL = "http://localhost:8080/api";
var API_ROUTE = "/api";


/**
* Gulp tasks
*/


//Task for concatenating all app js files.
gulp.task('concat_lib_js', function() {
	return gulp.src(JS_LIB)		
		.pipe(concat('lib.js'))				
		//.pipe(jsUglify()) ugligy the files
		.pipe(gulp.dest(JS_DESTINATION));
});

//Task for concatenating all custom css files.
gulp.task('concat_lib_css', function() {
	return gulp.src(CSS_LIB)
		.pipe(concat('lib.css'))
		//.pipe(cssNano()) uglify the files
		.pipe(gulp.dest(CSS_DESTINATION));
});

//Task for concatenating all custom css files.
gulp.task('concat_custom_css', function() {
	return gulp.src(CSS_CUSTOM)
		.pipe(concat('custom.css'))
		//.pipe(cssNano()) uglify the files
		.pipe(gulp.dest(CSS_DESTINATION));
});

//Task for concatenating all app js files.
gulp.task('concat_app_js', function() {
	return gulp.src(JS_APP)		
		.pipe(concat('app.js'))				
		//.pipe(jsUglify()) ugligy the files
		.pipe(gulp.dest(JS_DESTINATION));
});

//Task for copying all fonts.
gulp.task('copy_fonts', function() {
	return gulp.src(FONTS_LIB)
	        .pipe(gulp.dest(FONTS_DESTINATION));	
});

//Task for adding revesion on html files to prevent caching the resources.
gulp.task('cache-break', function() {
	return gulp.src(INDEX_FILE)
		.pipe(rev())
		.pipe(gulp.dest(INDEX_FILE_PATH));
});

//All the task to be executed on building the gulp file.
var tasks = [
	'concat_lib_js',
	'concat_lib_css',
	'concat_app_js',
	'concat_custom_css',
	'copy_fonts',
	'cache-break'
];

//The build task.
gulp.task('build', tasks);

//Task for watching changes on files.
gulp.task('watch', function() {
	/**
	* Specify where to find the files and which tasks
	* to execute on those files.
	*/
	gulp.watch(JS_LIB, ['concat_lib_js', 'cache-break']);
	gulp.watch(CSS_LIB, ['concat_lib_css', 'cache-break']);
	gulp.watch(JS_APP, ['concat_app_js', 'cache-break']);
	gulp.watch(CSS_CUSTOM, ['concat_custom_css', 'cache-break']);
});

gulp.task('serve', function() {
	connect.server({
		port: 3000,
		livereload: true,
		middleware: function (connect, opt) {
            return [
                (function () {
                    var url = require('url');
                    var proxy = require('proxy-middleware');
                    var options = url.parse(API_URL);
                    options.route = API_ROUTE;
                    return proxy(options);
                })()                
            ];
        }
	});
});

//The default task.
gulp.task('default', ['build', 'serve', 'watch']);