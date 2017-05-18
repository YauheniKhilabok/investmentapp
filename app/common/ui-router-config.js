'use strict';

module.exports = function (ngModule) {

    ngModule.config(function ($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise('/');

        $stateProvider
            .state('getAll', {
                url: '/?page&limit',
                component: 'nhListNews',
                params: {
                    page: {
                        value: '1',
                        squash: true
                    },
                    limit: {
                        value: '5',
                        squash: true
                    }
                }
            })

            .state('filter', {
                    url: '/news/?page&limit&tags&authors',
                    component: 'nhFilterNewsByType',
                    params: {
                        page: {
                            value: '1',
                            squash: true
                        },
                        limit: {
                            value: '5',
                            squash: true
                        },
                        tags: {
                            value: '0',
                            squash: true
                        },
                        authors: {
                            value: '0',
                            squash: true
                        }
                    }
                }
            )

            .state('get', {
                url: '/news/:id',
                component: 'nhConcreteNews'
            })

    });
};
