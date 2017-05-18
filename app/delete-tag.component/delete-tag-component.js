'use strict';

module.exports = function (ngModule) {

    ngModule.component('nhDeleteTag', {
        template: require('../common/template/delete-template.html'),
        bindings: {
            resolve: '<',
            close: '&',
            dismiss: '&'
        },
        controller: ['$filter', 'TagService', function ($filter, TagService) {
            var $ctrl = this;

            $ctrl.$onInit = function () {
                $ctrl.error = null;
                $ctrl.id = $ctrl.resolve.id;
            };

            $ctrl.delete = function () {
                TagService.deleteTag($ctrl.id)
                    .then(function () {
                            $ctrl.close({$value: 'close'});
                        },
                        function (errResponse) {
                            var $translate = $filter('translate');
                            $ctrl.error = $translate('delete_tag_error');
                            console.error('Error while deleting tag: ' + errResponse);
                        }
                    );
            };

            $ctrl.cancel = function () {
                $ctrl.dismiss({$value: 'cancel'});
            };

        }]
    });
};