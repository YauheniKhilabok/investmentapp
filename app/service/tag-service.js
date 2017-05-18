'use strict';

module.exports = function (ngModule) {

    ngModule.factory('TagService', ['$http', function ($http) {

        var factory = {
            getListOfTags: getListOfTags,
            findTagByName: findTagByName,
            createTag: createTag,
            deleteTag: deleteTag
        };

        return factory;

        function getListOfTags(page, limit) {
            return $http({
                method: "GET",
                url: TAG_SERVICE_URI + '/?page=' + page + '&limit=' + limit
            }).then(function (response) {
                return response.data;
            });
        }

        function findTagByName(name) {
            return $http({
                method: "GET",
                url: TAG_SERVICE_URI + '/?name=' + name
            }).then(function (response) {
                return response.data;
            });
        }

        function createTag(tag) {
            return $http({
                method: 'POST',
                url: TAG_SERVICE_URI,
                data: tag
            }).then(function (response) {
                return response.data;
            });
        }

        function deleteTag(id) {
            return $http({
                method: "DELETE",
                url: TAG_SERVICE_URI + id
            }).then(function (response) {
                return response.data;
            });
        }

    }]);
};