'use strict';

module.exports = function (ngModule) {

    ngModule.config(['$translateProvider', function ($translateProvider) {

        $translateProvider.useStaticFilesLoader({
            prefix: 'common/language/',
            suffix: '.json'
        });

        $translateProvider.preferredLanguage('ru');

    }]);
};