'use strict';

module.exports = function (ngModule) {

    ngModule.component('nhConcreteNews', {
        template: require('../concrete-news.component/concrete-news-template.html'),
        controller: ['$window', '$state', '$stateParams', '$uibModal', 'NewsService', 'ErrorFactory', function ($window, $state, $stateParams, $uibModal, NewsService, ErrorFactory) {
            var $ctrl = this;

            $ctrl.$onInit = function () {
                $ctrl.error = null;
                $ctrl.news = {
                    id: null,
                    title: '',
                    text: '',
                    dateTimeOfCreation: '',
                    dateTimeOfUpdate: '',
                    numberOfComments: '',
                    authors: {
                        id: null,
                        name: ''
                    },
                    tags: {
                        id: null,
                        name: ''
                    }
                };
                getConcreteNews();
            };

            function getConcreteNews() {
                NewsService.getConcreteNews($stateParams.id)
                    .then(
                        function (d) {
                            $ctrl.news = d;
                        },
                        function (errResponse) {
                            $ctrl.error = ErrorFactory.formServerError(errResponse);
                            console.error('Error while getting list of news: ' + errResponse);
                        }
                    );
            }

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
                    NewsService.getConcreteNews(id)
                        .then(
                            function (d) {
                                $ctrl.news = d;
                            },
                            function (errResponse) {
                                $ctrl.error = ErrorFactory.formServerError(errResponse);
                                console.error('Error while getting list of news: ' + errResponse);
                            }
                        );
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
                    $state.go('getAll');
                });
            };

            $ctrl.goBack = function () {
                $window.history.back();
            }

        }]
    });
};
