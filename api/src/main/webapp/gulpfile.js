'use strict'

const gulp = require('gulp');
const sourcemaps = require('gulp-sourcemaps');
const debug = require('gulp-debug');
const sass = require('gulp-sass');
const gulpIf = require('gulp-if');
const del = require('del');

const isDevelopment = !process.env.NODE_ENV || process.env.NODE_ENV == 'development';

gulp.task('styles', function () {
    return gulp.src("resources/style/main.scss", {base: 'resources'})
        .pipe(gulpIf(isDevelopment, sourcemaps.init()))
        .pipe(debug({title: 'src'}))
        .pipe(sass().on('error', sass.logError))
        .pipe(gulpIf(isDevelopment, sourcemaps.write()))
        .pipe(gulp.dest("dist"));
});

gulp.task('assets', function () {
    return gulp.src("resources/{img,js}/**")
        .pipe(gulp.dest('dist'));
});

gulp.task('clean', function () {
    return del('dist');
});

gulp.task('build', gulp.series('clean',  gulp.parallel('styles', 'assets')));