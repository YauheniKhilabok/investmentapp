'use strict';

module.exports = function (ngModule) {

    ngModule.component('nhListComments', {
        template: require('../list-comments.component/list-comments-template.html'),
        bindings: {
            newsId: '<',
            numberOfComments: '<'
        },
        controller: ['$uibModal', 'CommentService', 'ErrorFactory', function ($uibModal, CommentService, ErrorFactory) {
            var $ctrl = this;

            $ctrl.$onInit = function () {
                $ctrl.error = null;
                $ctrl.isShowComments = false;
                $ctrl.pageNumber = 1;
                $ctrl.limit = 5;
                $ctrl.listOfComments = [];
                $ctrl.comment = {
                    text: ''
                }
            };

            $ctrl.getListOfComments = function (newsId) {
                CommentService.getCommentsForNews(newsId, $ctrl.pageNumber, $ctrl.limit)
                    .then(
                        function (d) {
                            $ctrl.listOfComments = d;
                        },
                        function (errResponse) {
                            $ctrl.error = ErrorFactory.formServerError(errResponse);
                            console.error('Error while getting list of comments:' + errResponse);
                        }
                    );
                $ctrl.isShowComments = true;
            };

            $ctrl.pageChanged = function (newsId) {
                $ctrl.getListOfComments(newsId);
            };

            $ctrl.save = function (newsId) {
                CommentService.createComment($ctrl.comment, newsId)
                    .then(
                        function () {
                            $ctrl.getListOfComments(newsId);
                            $ctrl.numberOfComments++;
                            $ctrl.comment.text = '';
                        },
                        function (errResponse) {
                            $ctrl.error = ErrorFactory.formServerError(errResponse);
                            console.error('Error while creating comment: ' + errResponse);
                        }
                    );
            };

            $ctrl.reset = function () {
                $ctrl.comment = {text: ''};
            };

            $ctrl.openDeleteModal = function (id) {
                var modalInstance = $uibModal.open({
                    component: 'nhDeleteComment',
                    resolve: {
                        id: function () {
                            $ctrl.id = id;
                            return $ctrl.id;
                        }
                    }
                });
                modalInstance.result.then(function () {
                    $ctrl.getListOfComments($ctrl.newsId);
                    $ctrl.numberOfComments--;
                });
            };

            $ctrl.openUpdateModal = function (id) {
                var modalInstance = $uibModal.open({
                    component: 'nhUpdateComment',
                    resolve: {
                        id: function () {
                            $ctrl.id = id;
                            return $ctrl.id;
                        }
                    }
                });
                modalInstance.result.then(function () {
                    $ctrl.getListOfComments($ctrl.newsId);
                });
            };

        }]
    });
};