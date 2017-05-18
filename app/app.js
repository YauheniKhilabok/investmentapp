'use strict';

var angular = require('angular');
var ngModule = angular.module('newsHubApp', ['pascalprecht.translate', 'ui.router', 'ui.bootstrap']);

require('angular-translate');
require('angular-translate-loader-static-files');
require('angular-ui-router');
require('angular-ui-bootstrap');

require('./common/translate-config')(ngModule);
require('./common/ui-router-config')(ngModule);
require('./common/filter/date-filter')(ngModule);
require('./factory/error-factory')(ngModule);
require('./service/news-service')(ngModule);
require('./service/comment-service')(ngModule);
require('./service/tag-service')(ngModule);
require('./service/author-service')(ngModule);
require('./service/news-helper-service')(ngModule);
require('./service/filter-helper-service')(ngModule);
require('./service/check-params-service')(ngModule);
require('./header.component/header-component')(ngModule);
require('./sidebar.component/sidebar-component')(ngModule);
require('./list-news.component/list-news-component')(ngModule);
require('./concrete-news.component/concrete-news-component')(ngModule);
require('./create-news.component/create-news-component')(ngModule);
require('./delete-news.component/delete-news-component')(ngModule);
require('./update-news.component/update-news-component')(ngModule);
require('./filter-news.component/filter-news-component')(ngModule);
require('./filter-news.component/filter-news-by-type-component')(ngModule);
require('./list-comments.component/list-comments-component')(ngModule);
require('./delete-comment.component/delete-comment-component')(ngModule);
require('./update-comment.component/update-comment-component')(ngModule);
require('./delete-author.component/delete-author-component')(ngModule);
require('./update-author.component/update-author-component')(ngModule);
require('./delete-tag.component/delete-tag-component')(ngModule);