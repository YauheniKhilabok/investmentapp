'use strict';

module.exports = function (ngModule) {

    ngModule.filter('dateFilter', ['$filter', function ($filter) {

        function dateFilter(date) {
            var resultDate = null;
            var now = new Date();
            var currentDay = now.getDate();
            var dateTimeOfCreation = new Date(date);
            var creationDay = dateTimeOfCreation.getDate();
            var dayBeforeCreation = creationDay - 1;
            var $translate = $filter('translate');
            if (currentDay === creationDay) {
                resultDate = $translate('today_message') + dateTimeOfCreation.getUTCHours() + ':' + convertMinutes(dateTimeOfCreation.getMinutes());
            } else if (currentDay === dayBeforeCreation) {
                resultDate = $translate('yesterday_message') + dateTimeOfCreation.getUTCHours() + ':' + convertMinutes(dateTimeOfCreation.getMinutes());
            } else {
                resultDate = dateTimeOfCreation.getDate() + '/' + convertMonth(dateTimeOfCreation.getMonth()) + '/' + dateTimeOfCreation.getFullYear() +
                    ' ' + dateTimeOfCreation.getUTCHours() + ':' + convertMinutes(dateTimeOfCreation.getMinutes());
            }
            return resultDate;
        }

        function convertMinutes(minutes) {
            var nineMinutes = 9;
            var transformedMinutes = '0' + minutes;
            if (minutes <= nineMinutes) {
                return transformedMinutes;
            } else {
                return minutes;
            }
        }

        function convertMonth(month) {
            var tenthMonth = 9;
            var transformingMonthBeforeTenth = '0' + (month + 1);
            var transformingMonthAfterTenth = month + 1;
            if (month <= tenthMonth) {
                return transformingMonthBeforeTenth;
            } else {
                return transformingMonthAfterTenth;
            }
        }

        dateFilter.$stateful = true;
        return dateFilter;

    }]);
};