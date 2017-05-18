'use strict';

module.exports = function (ngModule) {

    ngModule.factory('AuthorService', ['$http', function ($http) {

        var factory = {
            getListOfAuthors: getListOfAuthors,
            getConcreteAuthor: getConcreteAuthor,
            findAuthorByName: findAuthorByName,
            createAuthor: createAuthor,
            deleteAuthor: deleteAuthor,
            updateAuthor: updateAuthor
        };

        return factory;

        function getListOfAuthors(page, limit) {
            return $http({
                method: "GET",
                url: AUTHOR_SERVICE_URI + '/?page=' + page + '&limit=' + limit
            }).then(function (response) {
                return response.data;
            });
        }

        function getConcreteAuthor(id) {
            return $http({
                method: "GET",
                url: AUTHOR_SERVICE_URI + id
            }).then(function (response) {
                return response.data;
            });
        }

        function findAuthorByName(name) {
            return $http({
                method: "GET",
                url: AUTHOR_SERVICE_URI + '/?name=' + name
            }).then(function (response) {
                return response.data;
            });
        }

        function createAuthor(author) {
            return $http({
                method: 'POST',
                url: AUTHOR_SERVICE_URI,
                data: author
            }).then(function (response) {
                return response.data;
            });
        }

        function deleteAuthor(id) {
            return $http({
                method: "DELETE",
                url: AUTHOR_SERVICE_URI + id
            }).then(function (response) {
                return response.data;
            });
        }

        function updateAuthor(id, author) {
            return $http({
                method: 'PUT',
                url: AUTHOR_SERVICE_URI + id,
                data: author
            }).then(function (response) {
                return response.data;
            });
        }

    }]);
};