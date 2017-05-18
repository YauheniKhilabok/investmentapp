'use strict';

module.exports = function (ngModule) {

    ngModule.component('nhDeleteComment', {
        template: require('../common/template/delete-template.html'),
        bindings: {
            resolve: '<',
            close: '&',
            dismiss: '&'
        },
        controller: ['$state', 'CommentService', 'ErrorFactory', function ($state, CommentService, ErrorFactory) {
            var $ctrl = this;

            $ctrl.$onInit = function () {
                $ctrl.error = null;
                $ctrl.id = $ctrl.resolve.id;
            };

            $ctrl.delete = function () {
                CommentService.deleteComment($ctrl.id)
                    .then(function () {
                            $ctrl.close({$value: 'close'});
                        },
                        function (errResponse) {
                            $ctrl.error = ErrorFactory.formServerError(errResponse);
                            console.error('Error while deleting comment: ' + errResponse);
                            $state.reload();
                        }
                    );
            };

            $ctrl.cancel = function () {
                $ctrl.dismiss({$value: 'cancel'});
            };

        }]
    });
};