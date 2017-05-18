'use strict';

module.exports = function (ngModule) {

    ngModule.component('nhDeleteNews', {
        template: require('../common/template/delete-template.html'),
        bindings: {
            resolve: '<',
            close: '&',
            dismiss: '&'
        },
        controller: ['NewsService', 'ErrorFactory', function (NewsService, ErrorFactory) {
            var $ctrl = this;

            $ctrl.$onInit = function () {
                $ctrl.error = null;
                $ctrl.id = $ctrl.resolve.id;
            };

            $ctrl.delete = function () {
                NewsService.deleteNews($ctrl.id)
                    .then(function () {
                            $ctrl.close({$value: 'close'});
                        },
                        function (errResponse) {
                            $ctrl.error = ErrorFactory.formServerError(errResponse);
                            console.error('Error while deleting news: ' + errResponse);
                        }
                    );
            };

            $ctrl.cancel = function () {
                $ctrl.dismiss({$value: 'cancel'});
            };

        }]
    });
};