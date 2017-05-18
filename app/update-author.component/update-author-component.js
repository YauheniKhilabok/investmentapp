'use strict';

module.exports = function (ngModule) {

    ngModule.component('nhUpdateAuthor', {
        template: require('../update-author.component/update-author-template.html'),
        bindings: {
            resolve: '<',
            close: '&',
            dismiss: '&'
        },
        controller: ['$filter', 'AuthorService', 'ErrorFactory', function ($filter, AuthorService, ErrorFactory) {
            var $ctrl = this;

            $ctrl.$onInit = function () {
                $ctrl.error = null;
                $ctrl.author = {
                    id: null,
                    name: ''
                };

                $ctrl.author.id = $ctrl.resolve.id;

                AuthorService.getConcreteAuthor($ctrl.author.id)
                    .then(
                        function (d) {
                            $ctrl.author = d;
                        },
                        function (errResponse) {
                            $ctrl.error = ErrorFactory.formServerError(errResponse);
                            console.error('Error while getting concrete author: ' + errResponse);
                        }
                    );
            };

            $ctrl.update = function () {
                var $translate = $filter('translate');
                AuthorService.updateAuthor($ctrl.author.id, $ctrl.author)
                    .then(function () {
                            $ctrl.close({$value: 'close'});
                        },
                        function (errResponse) {
                            $ctrl.error = ErrorFactory.formServerError(errResponse);
                            $ctrl.error = $translate('create_author_error');
                            console.error('Error while updating author: ' + errResponse);
                        }
                    );
            };

            $ctrl.reset = function () {
                $ctrl.author = {id: $ctrl.author.id, name: ''};
            };

            $ctrl.cancel = function () {
                $ctrl.dismiss({$value: 'cancel'});
            };

        }]
    });
};