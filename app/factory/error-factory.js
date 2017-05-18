'use strict';

module.exports = function (ngModule) {

    ngModule.factory('ErrorFactory', ['$filter', function ($filter) {

        var factory = {
            formServerError: formServerError
        };

        return factory;

        function formServerError($error) {
            var $translate = $filter('translate');
            var commonUriError = $translate('uri_error');
            var emptyResultError = $translate('empty_result_error');
            var $status = $error.status;
            if ($status !== undefined) {
                return emptyResultError;
            } else {
                return commonUriError;
            }
        }

    }])
};