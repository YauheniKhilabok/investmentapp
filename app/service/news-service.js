'use strict';

module.exports = function (ngModule) {

    ngModule.factory('NewsService', ['$http', function ($http) {

        var factory = {
            getPartOfNews: getPartOfNews,
            getFilteredNews: getFilteredNews,
            getConcreteNews: getConcreteNews,
            createNews: createNews,
            deleteNews: deleteNews,
            updateNews: updateNews
        };

        return factory;

        function getPartOfNews(pageNumber, limit) {
            return $http({
                method: "GET",
                url: NEWS_SERVICE_URI + '?page=' + pageNumber + '&limit=' + limit
            }).then(function (response) {
                return {
                    data: response.data,
                    size: response.headers("Total-Size")
                };
            })
        }

        function getFilteredNews(partOfUri) {
            return $http({
                method: "GET",
                url: NEWS_SERVICE_URI + partOfUri
            }).then(function (response) {
                return {
                    data: response.data,
                    size: response.headers("Total-Size")
                }
            });
        }

        function getConcreteNews(id) {
            return $http({
                method: "GET",
                url: NEWS_SERVICE_URI + id
            }).then(function (response) {
                return response.data;
            });
        }

        function createNews(news) {
            return $http({
                method: 'POST',
                url: NEWS_SERVICE_URI,
                data: news
            }).then(function (response) {
                return response.data;
            });
        }

        function deleteNews(id) {
            return $http({
                method: "DELETE",
                url: NEWS_SERVICE_URI + id
            }).then(function (response) {
                return response.data;
            });
        }

        function updateNews(id, news) {
            return $http({
                method: 'PUT',
                url: NEWS_SERVICE_URI + id,
                data: news
            }).then(function (response) {
                return response.data;
            });
        }

    }]);
};