'use strict';

module.exports = function (ngModule) {

    ngModule.component('nhFilterNewsByType', {
        template: require('../common/template/list-news-template.html'),
        controller: ['$filter', '$state', '$stateParams', '$uibModal', 'NewsService', 'FilterHelperService', 'CheckParamsService', 'ErrorFactory',
            function ($filter, $state, $stateParams, $uibModal, NewsService, FilterHelperService, CheckParamsService, ErrorFactory) {
                var $ctrl = this;

                $ctrl.$onInit = function () {
                    $ctrl.error = null;
                    $ctrl.currentPageNotValid = null;
                    $ctrl.itemsPerPageNotValid = null;
                    $ctrl.listOfNews = [];
                    getFilteredListOfNews();
                };

                function getFilteredListOfNews() {
                    var pageNumber = $stateParams.page;
                    var limit = $stateParams.limit;
                    var resultStr = $stateParams.authors.toString() + $stateParams.tags.toString();
                    var maxUriGetLength = 2048;
                    if (resultStr.length <= maxUriGetLength) {
                        var partOfUri = FilterHelperService.formPartOfUri($stateParams);
                        NewsService.getFilteredNews(partOfUri)
                            .then(
                                function (response) {
                                    $ctrl.listOfNews = response.data;
                                    $ctrl.listOfNewsSize = $ctrl.listOfNews.length;
                                    $ctrl.totalItems = 0;
                                    if ($ctrl.listOfNewsSize > 0) {
                                        $ctrl.totalItems = parseInt(response.size);
                                    }
                                    $ctrl.currentPage = pageNumber;
                                    $ctrl.itemsPerPage = limit;
                                    $ctrl.numberOfPages = Math.ceil($ctrl.totalItems / $ctrl.itemsPerPage);
                                },
                                function (errResponse) {
                                    $ctrl.error = ErrorFactory.formServerError(errResponse);
                                    console.error('Error while getting list of news:' + errResponse);
                                }
                            );
                    } else {
                        var $translate = $filter('translate');
                        $ctrl.error = $translate('uri_max_error');
                    }
                }

                $ctrl.pageChanged = function () {
                    if (CheckParamsService.checkInputParams($filter, $ctrl)) {
                        $state.go('filter', {
                            page: $ctrl.currentPage,
                            limit: $ctrl.itemsPerPage,
                            tags: $stateParams.tags,
                            authors: $stateParams.authors
                        }, {notify: false});
                    }
                };

                $ctrl.limitChanged = function () {
                    if (CheckParamsService.checkInputParams($filter, $ctrl)) {
                        $state.go('filter', {
                            page: 1,
                            limit: $ctrl.itemsPerPage,
                            tags: $stateParams.tags,
                            authors: $stateParams.authors
                        }, {notify: false});
                    }
                };

                $ctrl.openCreateModal = function () {
                    var modalInstance = $uibModal.open({
                        component: 'nhCreateNews'
                    });
                    modalInstance.result.then(function (id) {
                        $state.go('get', {id: id});
                    });
                };

                $ctrl.openDeleteModal = function (id) {
                    var modalInstance = $uibModal.open({
                        component: 'nhDeleteNews',
                        resolve: {
                            id: function () {
                                $ctrl.id = id;
                                return $ctrl.id;
                            }
                        }
                    });
                    modalInstance.result.then(function () {
                        getFilteredListOfNews();
                    });
                };

                $ctrl.openUpdateModal = function (id) {
                    var modalInstance = $uibModal.open({
                        component: 'nhUpdateNews',
                        resolve: {
                            id: function () {
                                $ctrl.id = id;
                                return $ctrl.id;
                            }
                        }
                    });
                    modalInstance.result.then(function () {
                        getFilteredListOfNews();
                    });
                };

            }]
    });
};