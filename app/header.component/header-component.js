'use strict';

module.exports = function (ngModule) {

    ngModule.component('nhHeader', {
        template: require('../header.component/header-template.html'),
        controller: HeaderController
    });

};

function HeaderController($translate, $scope) {

    $scope.changeLanguage = function (langKey) {
        $translate.use(langKey);
    };

}