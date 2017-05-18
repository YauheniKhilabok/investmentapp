'use strict';

module.exports = function (ngModule) {

    ngModule.component('nhDeleteAuthor', {
        template: require('../common/template/delete-template.html'),
        bindings: {
            resolve: '<',
            close: '&',
            dismiss: '&'
        },
        controller: ['$filter', 'AuthorService', function ($filter, AuthorService) {
            var $ctrl = this;

            $ctrl.$onInit = function () {
                $ctrl.error = null;
                $ctrl.id = $ctrl.resolve.id;
            };

            $ctrl.delete = function () {
                AuthorService.deleteAuthor($ctrl.id)
                    .then(function () {
                            $ctrl.close({$value: 'close'});
                        },
                        function (errResponse) {
                            var $translate = $filter('translate');
                            $ctrl.error = $translate('delete_author_error');
                            console.error('Error while deleting author: ' + errResponse);
                        }
                    );
            };

            $ctrl.cancel = function () {
                $ctrl.dismiss({$value: 'cancel'});
            };

        }]
    });
};