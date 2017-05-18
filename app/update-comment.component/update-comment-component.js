'use strict';

module.exports = function (ngModule) {

    ngModule.component('nhUpdateComment', {
        template: require('../update-comment.component/update-comment-template.html'),
        bindings: {
            resolve: '<',
            close: '&',
            dismiss: '&'
        },
        controller: ['$state', 'CommentService', 'ErrorFactory', function ($state, CommentService, ErrorFactory) {
            var $ctrl = this;

            $ctrl.$onInit = function () {
                $ctrl.error = null;
                $ctrl.comment = {
                    id: null,
                    text: ''
                };

                $ctrl.comment.id = $ctrl.resolve.id;

                CommentService.getConcreteComment($ctrl.comment.id)
                    .then(
                        function (d) {
                            $ctrl.comment = d;
                        },
                        function (errResponse) {
                            $ctrl.error = ErrorFactory.formServerError(errResponse);
                            console.error('Error while getting concrete comment: ' + errResponse);
                            $state.reload();
                        }
                    );
            };

            $ctrl.update = function () {
                CommentService.updateComment($ctrl.comment.id, $ctrl.comment)
                    .then(function () {
                            $ctrl.close({$value: 'close'});
                        },
                        function (errResponse) {
                            $ctrl.error = ErrorFactory.formServerError(errResponse);
                            console.error('Error while updating comment: ' + errResponse);
                        }
                    );
            };

            $ctrl.reset = function () {
                $ctrl.comment = {id: $ctrl.comment.id, text: ''};
            };

            $ctrl.cancel = function () {
                $ctrl.dismiss({$value: 'cancel'});
            };

        }]
    });
};