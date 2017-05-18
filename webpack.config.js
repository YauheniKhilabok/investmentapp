'use strict';

const NODE_ENV = process.env.NODE_ENV || 'development';
const webpack = require('webpack');

module.exports = {
    context: __dirname + "/app",
    entry: "./app.js",
    output: {
        path: __dirname + "/app/dist",
        filename: "bundle.js",
        library: "app"
    },

    watch: NODE_ENV == 'development',

    watchOptions: {
        aggregateTimeout: 100
    },

    devtool: NODE_ENV == 'development' ? "source-map" : null,

    plugins: [
        new webpack.DefinePlugin({
            NODE_ENV: JSON.stringify(NODE_ENV),
            NEWS_SERVICE_URI: JSON.stringify("http://localhost:8085/newshub/rest/news/"),
            COMMENT_SERVICE_URI: JSON.stringify("http://localhost:8085/newshub/rest/comments/"),
            TAG_SERVICE_URI: JSON.stringify("http://localhost:8085/newshub/rest/tags/"),
            AUTHOR_SERVICE_URI: JSON.stringify("http://localhost:8085/newshub/rest/authors/")
        })
    ],

    module: {
        loaders: [
            {
                test: /\.html$/,
                loader: "html-loader"
            }
        ]
    }

};

