'use strict';

module.exports = function (ngModule) {

    ngModule.component('nhSidebar', {
        template: require('../sidebar.component/sidebar-template.html'),
        controller: ['$filter', '$uibModal', 'TagService', 'AuthorService', 'ErrorFactory', function ($filter, $uibModal, TagService, AuthorService, ErrorFactory) {
            var $ctrl = this;

            var page = 1;
            var tagsLimit = 50;
            var authorsLimit = 50;

            $ctrl.$onInit = function () {
                $ctrl.error = null;
                $ctrl.listOfTags = [];
                $ctrl.listOfAuthors = [];
                $ctrl.tag = {
                    name: ''
                };
                $ctrl.author = {
                    name: ''
                };
                $ctrl.createTagMessage = '';
                $ctrl.createAuthorMessage = '';
                $ctrl.isShowAllTags = false;
                $ctrl.isShowAllAuthors = false;
                getListOfTags();
                getListOfAuthors();
            };

            function getListOfTags() {
                TagService.getListOfTags(page, tagsLimit)
                    .then(
                        function (d) {
                            $ctrl.listOfTags = d;
                        },
                        function (errResponse) {
                            $ctrl.error = ErrorFactory.formServerError(errResponse);
                            console.error('Error while getting list of tags:' + errResponse);
                        }
                    );
            }

            $ctrl.loadMoreTags = function () {
                tagsLimit += 50;
                TagService.getListOfTags(page, tagsLimit)
                    .then(
                        function (d) {
                            $ctrl.listOfTags = d;
                        },
                        function (errResponse) {
                            $ctrl.error = ErrorFactory.formServerError(errResponse);
                            console.error('Error while getting list of tags:' + errResponse);
                        }
                    );
            };

            $ctrl.saveTag = function () {
                var $translate = $filter('translate');
                TagService.createTag($ctrl.tag)
                    .then(
                        function () {
                            $ctrl.isShowAllTags = true;
                            $ctrl.tag.name = '';
                            getListOfTags(page, tagsLimit);
                        },
                        function (errResponse) {
                            $ctrl.error = ErrorFactory.formServerError(errResponse);
                            $ctrl.createTagMessage = $translate('create_tag_error');
                            console.error('Error while creating tag: ' + errResponse);
                        }
                    );
            };

            $ctrl.resetTag = function () {
                $ctrl.createTagMessage = '';
            };

            $ctrl.openDeleteTagModal = function (id) {
                var modalInstance = $uibModal.open({
                    component: 'nhDeleteTag',
                    resolve: {
                        id: function () {
                            $ctrl.tagId = id;
                            return $ctrl.tagId;
                        }
                    }
                });
                modalInstance.result.then(function () {
                    $ctrl.isShowAllTags = true;
                    getListOfTags(page, tagsLimit);
                });
            };

            $ctrl.showAllTags = function () {
                $ctrl.isShowAllTags = true;
            };

            $ctrl.showTopTags = function () {
                $ctrl.isShowAllTags = false;
            };

            function getListOfAuthors() {
                AuthorService.getListOfAuthors(page, authorsLimit)
                    .then(
                        function (d) {
                            $ctrl.listOfAuthors = d;
                        },
                        function (errResponse) {
                            $ctrl.error = ErrorFactory.formServerError(errResponse);
                            console.error('Error while getting list of authors:' + errResponse);
                        }
                    );
            }

            $ctrl.loadMoreAuthors = function () {
                authorsLimit += 50;
                AuthorService.getListOfAuthors(page, authorsLimit)
                    .then(
                        function (d) {
                            $ctrl.listOfAuthors = d;
                        },
                        function (errResponse) {
                            $ctrl.error = ErrorFactory.formServerError(errResponse);
                            console.error('Error while getting list of authors:' + errResponse);
                        }
                    );
            };

            $ctrl.saveAuthor = function () {
                var $translate = $filter('translate');
                AuthorService.createAuthor($ctrl.author)
                    .then(
                        function () {
                            $ctrl.isShowAllAuthors = true;
                            $ctrl.author.name = '';
                            getListOfAuthors(page, authorsLimit);
                        },
                        function (errResponse) {
                            $ctrl.error = ErrorFactory.formServerError(errResponse);
                            $ctrl.createAuthorMessage = $translate('create_author_error');
                            console.error('Error while creating author: ' + errResponse);
                        }
                    );
            };

            $ctrl.resetAuthor = function () {
                $ctrl.createAuthorMessage = '';
            };

            $ctrl.openDeleteAuthorModal = function (id) {
                var modalInstance = $uibModal.open({
                    component: 'nhDeleteAuthor',
                    resolve: {
                        id: function () {
                            $ctrl.authorId = id;
                            return $ctrl.authorId;
                        }
                    }
                });
                modalInstance.result.then(function () {
                    $ctrl.isShowAllAuthors = true;
                    getListOfAuthors(page, authorsLimit);
                });
            };

            $ctrl.openUpdateAuthorModal = function (id) {
                var modalInstance = $uibModal.open({
                    component: 'nhUpdateAuthor',
                    resolve: {
                        id: function () {
                            $ctrl.authorId = id;
                            return $ctrl.authorId;
                        }
                    }
                });
                modalInstance.result.then(function () {
                    $ctrl.isShowAllAuthors = true;
                    getListOfAuthors(page, authorsLimit);
                });
            };

            $ctrl.showAllAuthors = function () {
                $ctrl.isShowAllAuthors = true;
            };

            $ctrl.showTopAuthors = function () {
                $ctrl.isShowAllAuthors = false;
            }

        }]
    });
};