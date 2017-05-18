'use strict';

module.exports = function (ngModule) {

    ngModule.factory('CheckParamsService', [function () {

        var factory = {
            checkInputParams: checkInputParams
        };

        return factory;

        function checkInputParams($filter, $ctrl) {
            var min = 1;
            var isValid = false;
            var $translate = $filter('translate');
            if (!isNumeric($ctrl.currentPage)) {
                $ctrl.currentPageNotValid = $translate('current_page_not_number')
            } else if (!isNumeric($ctrl.itemsPerPage)) {
                $ctrl.itemsPerPageNotValid = $translate('items_per_page_not_number')
            }
            else if ($ctrl.currentPage < min || $ctrl.currentPage > $ctrl.numberOfPages) {
                $ctrl.currentPageNotValid = $translate('current_page_not_in_the_range') + " " + $ctrl.numberOfPages;
            } else if ($ctrl.itemsPerPage < min) {
                $ctrl.itemsPerPageNotValid = $translate('items_per_page_not_in_the_range')
            } else {
                isValid = true;
            }
            return isValid;
        }

        function isNumeric(n) {
            return !isNaN(parseFloat(n)) && isFinite(n);
        }

    }]);
};