/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

// Prefix url of website (leave empty if site is hosted directly eg. 'http://localhost:8080/mypage.jsp')
const contextPath = '/logiweb';

'use strict';

const NODE_ENV = process.env.NODE_ENV || 'development';
const webpack = require('webpack');
const path = require('path');
const ExtractTextPlugin = require("extract-text-webpack-plugin");
const CopyWebpackPlugin = require("copy-webpack-plugin");
const nodeDir = __dirname + '/node_modules/';


module.exports = {
    context: __dirname + '/src/main/webapp/frontend',
    entry: {
        driver: './app/drivers/driver_list',
        styles: './styles/main.scss',
    },
    output: {
        path: __dirname + '/src/main/webapp/dist',
        publicPath: contextPath + '/dist/',
        filename: "js/[name].js",
        library: "app"
    },
    resolve: {
        root: [
            __dirname + '/src/main/webapp/frontend',
            // __dirname + '/node_modules',
        ],
        alias: {
            'bootstrap-table': 'bootstrap-table/dist/bootstrap-table.min.js'
        }
    },

    // devtool: NODE_ENV == 'development' ? "source-map" : null,
    watch: NODE_ENV == 'development',

    watchOptions: {
        aggregateTimeout: 100
    },

    module: {
        loaders: [
            {
                test:   /\.js$/,
                include: path.resolve(__dirname, 'src/main/webapp/frontend/app'),
                loader: "babel?presets[]=es2015"
            },
            {
                test: /\.css$/,
                loader: 'styles!css'
            },
            {
                test: /\.scss$/,
                loader: ExtractTextPlugin.extract('style', '!css!sass')
            },
            {
                test:   /\.(png|jpg|svg|ttf|eot|woff|woff2)$/,
                loader: 'file?name=[path][name].[ext]'
            }
        ]
    },

    plugins: [
        new webpack.NoErrorsPlugin(),
        new ExtractTextPlugin('style/main.css', {allChunks: true}),
        new CopyWebpackPlugin([
            { from: nodeDir + '/jquery/dist', to: 'vendor/jquery' },
            { from: nodeDir + '/admin-lte/dist', to: 'vendor/admin-lte' },
            { from: nodeDir + '/bootstrap/dist', to: 'vendor/bootstrap' },
            { from: nodeDir + '/bootstrap-select/dist', to: 'vendor/bootstrap-select' },
            { from: nodeDir + '/bootstrap-table/dist', to: 'vendor/bootstrap-table' },
            { from: nodeDir + '/easy-autocomplete/dist', to: 'vendor/easy-autocomplete' },

            { from: 'img', to: 'img' },
        ]),
        // new webpack.optimize.CommonsChunkPlugin({
        //     name: "common"
        // }),
        new webpack.DefinePlugin({
            CONTEXT_PATH: JSON.stringify(contextPath)
        }),
        // new webpack.ProvidePlugin({
        //     $: 'jquery'
        // }),
    ],

    externals: {
        'jquery': '$'
    }
};

if (NODE_ENV == 'production') {
    module.exports.plugins.push(
        new webpack.optimize.UglifyJsPlugin({
            compress: {
                drop_console: true,
                // uncreachable variables
                // warnings: false,
            }
        })
    )
}
