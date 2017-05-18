'use strict';

module.exports = function (ngModule) {

    ngModule.factory('CommentService', ['$http', function ($http) {

        var factory = {
            getCommentsForNews: getCommentsForNews,
            getConcreteComment: getConcreteComment,
            createComment: createComment,
            deleteComment: deleteComment,
            updateComment: updateComment
        };

        return factory;

        function getCommentsForNews(newsId, pageNumber, limit) {
            return $http({
                method: "GET",
                url: NEWS_SERVICE_URI + newsId + '/comments/' + '?page=' + pageNumber + '&limit=' + limit
            }).then(function (response) {
                return response.data;
            });
        }

        function getConcreteComment(id) {
            return $http({
                method: "GET",
                url: COMMENT_SERVICE_URI + id
            }).then(function (response) {
                return response.data;
            });
        }

        function createComment(comment, newsId) {
            return $http({
                method: 'POST',
                url: NEWS_SERVICE_URI + newsId + '/comments',
                data: comment
            }).then(function (response) {
                return response.data;
            });
        }

        function deleteComment(id) {
            return $http({
                method: "DELETE",
                url: COMMENT_SERVICE_URI + id
            }).then(function (response) {
                return response.data;
            });
        }

        function updateComment(id, comment) {
            return $http({
                method: 'PUT',
                url: COMMENT_SERVICE_URI + id,
                data: comment
            }).then(function (response) {
                return response.data;
            });
        }

    }]);
};