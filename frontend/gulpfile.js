var gulp = require('gulp');
var webserver = require('gulp-webserver');
var plumber = require('gulp-plumber');
var opn = require('opn');

var server = {
	host: 'localhost',
	port: '9090'
}

var sourcePaths = {
	styles: ['app/style/**/*.css'],
    scripts: ['app/**/*.js'],
	partials: ['app/**/*.html']
};

gulp.task('webserver', function() {
	gulp.src('app/.')
		.pipe(webserver({
			host: server.host,
			port: server.port,
			livereload: true,
			directoryListing: false
		}));
});

gulp.task('openbrowser', function() {
	opn('http://' + server.host + ':' + server.port);
});

gulp.task('watch', function() {
	gulp.watch(sourcePaths.styles);
    gulp.watch(sourcePaths.scripts);
	gulp.watch(sourcePaths.partials);
});

gulp.task('default', ['webserver', 'watch', 'openbrowser']);