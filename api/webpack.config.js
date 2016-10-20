/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */
'use strict';

// Prefix url of website (leave empty if site is hosted directly eg. 'http://localhost:8080/mypage.jsp')
const contextPath = '/logiweb';
const NODE_ENV = process.env.NODE_ENV || 'development';
const webpack = require('webpack');
const path = require('path');
const ExtractTextPlugin = require("extract-text-webpack-plugin");
const CopyWebpackPlugin = require("copy-webpack-plugin");
const nodeDir = __dirname + '/node_modules/';
const rimraf = require('rimraf');

module.exports = {
    context: __dirname + '/src/main/webapp/frontend',
    entry: {
        vendor: ['jquery',
            'admin-lte',
            'bootstrap',
            'bootstrap-select',
            'bootstrap-table',
            'easy-autocomplete',
            'parsleyjs',
            'underscore',
            'backbone',
            'bootstrap-select',
            'slimscroll',
            'icheck'
        ],
        main: './js/main',
        styles: './styles/main.scss',
    },
    output: {
        path: __dirname + '/src/main/webapp/dist',
        publicPath: contextPath + '/dist/',
        filename: "app/[name].bundle.js",
        library: 'logiweb',
    },
    resolve: {
        // base path for requires
        root: __dirname + '/src/main/webapp/frontend/js',
        alias: {
            'bootstrap-table': 'bootstrap-table/dist/bootstrap-table.min.js',
            'jquery': "jquery/src/jquery"
        },
        extensions: ["", ".js", ".hbs"],
    },


    // sourcemaps only for development
    devtool: NODE_ENV == 'development' ? "source-map" : null,

    // watch: NODE_ENV == 'development',
    watch: process.env.WEBPACK_WATCH == 'yes',

    watchOptions: {
        aggregateTimeout: 100
    },

    module: {
        loaders: [
            {
                test:   /\.js$/,
                include: [
                    path.resolve(__dirname, 'src/main/webapp/frontend/js'),
                ],
                loader: "babel?presets[]=es2015"
            },
            {
                test: /\.css$/,
                loader: 'style!css'
            },
            {
                test: /\.scss$/,
                loader: ExtractTextPlugin.extract('style', '!css!sass')
            },
            {
                test: /\.hbs$/,
                loader: "handlebars-loader?helperDirs[]=" + __dirname + "/src/main/webapp/frontend/js/helpers"
            },
            {
                test:   /\.(png|jpg|svg|ttf|eot|woff|woff2)$/,
                // loader: 'file'
                loader: 'file?name=img/[name].[ext]'
            }
        ]
    },

    plugins: [
        {
            apply: (compiler) => {
                rimraf.sync(compiler.options.output.path);
            }
        },
        new webpack.NoErrorsPlugin(),
        new ExtractTextPlugin('style/main.css', {allChunks: true}),
        // copy dependencies for manual loading in html
        new CopyWebpackPlugin([
            { from: nodeDir + '/jquery/dist', to: 'vendor/jquery' },
            { from: nodeDir + '/admin-lte/dist', to: 'vendor/admin-lte' },
            { from: nodeDir + '/bootstrap/dist', to: 'vendor/bootstrap' },
            { from: nodeDir + '/bootstrap-select/dist', to: 'vendor/bootstrap-select' },
            { from: nodeDir + '/bootstrap-table/dist', to: 'vendor/bootstrap-table' },
            { from: nodeDir + '/easy-autocomplete/dist', to: 'vendor/easy-autocomplete' },
            { from: nodeDir + '/parsleyjs/dist', to: 'vendor/parsleyjs' },
            { from: nodeDir + '/underscore', to: 'vendor/underscore' },
            { from: nodeDir + '/backbone', to: 'vendor/backbone' },
            { from: nodeDir + '/bootstrap-select/dist', to: 'vendor/bootstrap-select' },
            { from: nodeDir + '/slimscroll/lib', to: 'vendor/slimscroll' },
            { from: nodeDir + '/icheck', to: 'vendor/icheck' },

            // { from: 'img', to: 'img' },
        ]),
        // new webpack.optimize.CommonsChunkPlugin({
        //     name: "common"
        // }),
        new webpack.DefinePlugin({
            CONTEXT_PATH: JSON.stringify(contextPath)
        }),
        // insert $ = requre('jquery') everytime it encounters $
        new webpack.ProvidePlugin({
            $: "jquery",
            jQuery: "jquery",
            _: "underscore"
        })
    ],

    // externals: {
    //     'jquery': '$',
    //     'underscore': '_',
    // },
};

if (NODE_ENV == 'production') {
    module.exports.plugins.push(
        new webpack.optimize.UglifyJsPlugin({
            compress: {
                drop_console: true,
                // uncreachable variables
                warnings: false,
            }
        })
    )
}
