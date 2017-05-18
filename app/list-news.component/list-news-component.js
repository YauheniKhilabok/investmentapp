'use strict';

module.exports = function (ngModule) {

    ngModule.component('nhListNews', {
        template: require('../common/template/list-news-template.html'),
        controller: ['$filter', '$state', '$stateParams', '$uibModal', 'NewsService', 'CheckParamsService', 'ErrorFactory', function ($filter, $state, $stateParams, $uibModal, NewsService, CheckParamsService, ErrorFactory) {
            var $ctrl = this;

            $ctrl.$onInit = function () {
                $ctrl.error = null;
                $ctrl.currentPageNotValid = null;
                $ctrl.itemsPerPageNotValid = null;
                $ctrl.listOfNews = [];
                getListOfNews();
            };

            function getListOfNews() {
                var pageNumber = $stateParams.page;
                var limit = checkLimitValue($stateParams.limit);
                NewsService.getPartOfNews(pageNumber, limit)
                    .then(
                        function (response) {
                            $ctrl.listOfNews = response.data;
                            $ctrl.listOfNewsSize = $ctrl.listOfNews.length;
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
            }

            function checkLimitValue(limit) {
                function isLimitAvailable(element) {
                    return element == limit;
                }

                var defaultLimitValue = 5;
                var limitArray = [5, 10, 15, 20, 25];
                var flag = limitArray.some(isLimitAvailable);
                if (!flag) {
                    limit = defaultLimitValue;
                    $state.go('getAll', {page: $ctrl.currentPage, limit: limit}, {notify: false});
                }
                return limit;
            }

            $ctrl.pageChanged = function () {
                if (CheckParamsService.checkInputParams($filter, $ctrl)) {
                    $state.go('getAll', {page: $ctrl.currentPage, limit: $ctrl.itemsPerPage}, {notify: false});
                }
            };

            $ctrl.limitChanged = function () {
                if (CheckParamsService.checkInputParams($filter, $ctrl)) {
                    $state.go('getAll', {page: 1, limit: $ctrl.itemsPerPage}, {notify: false});
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
                    getListOfNews();
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
                    getListOfNews();
                });
            };

        }]
    });
};

