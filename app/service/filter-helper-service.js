'use strict';

module.exports = function (ngModule) {

    ngModule.factory('FilterHelperService', [function () {

        var factory = {
            formPartOfUri: formPartOfUri,
            formTagsFilterParam: formTagsFilterParam,
            formAuthorsFilterParam: formAuthorsFilterParam
        };

        return factory;

        function formPartOfUri($stateParams) {
            var partOfUri = "?page=" + $stateParams.page.toString() + "&limit=" + $stateParams.limit.toString();
            if ($stateParams.tags !== '0') {
                var tags = $stateParams.tags.toString();
                var tagsParams = tags.split(',');
                for (var i = 0; i < tagsParams.length; i++) {
                    partOfUri += "&tags=" + tagsParams[i];
                }
            }
            if ($stateParams.authors !== '0') {
                var authors = $stateParams.authors.toString();
                var authorsParams = authors.split(',');
                for (i = 0; i < authorsParams.length; i++) {
                    partOfUri += "&authors=" + authorsParams[i];
                }
            }
            return partOfUri;
        }

        function formTagsFilterParam(tagId, $ctrl) {
            var name = '';
            for (var i = 0; i < $ctrl.listOfTags.length; i++) {
                if (tagId == $ctrl.listOfTags[i].id) {
                    name = $ctrl.listOfTags[i].name;
                    break;
                }
            }
            var filterObj = {
                id: tagId,
                name: name
            };
            $ctrl.tagsFilterParam.push(filterObj);
        }

        function formAuthorsFilterParam(authorId, $ctrl) {
            var name = '';
            for (var i = 0; i < $ctrl.listOfAuthors.length; i++) {
                if (authorId == $ctrl.listOfAuthors[i].id) {
                    name = $ctrl.listOfAuthors[i].name;
                    break;
                }
            }
            var filterObj = {
                id: authorId,
                name: name
            };
            $ctrl.authorsFilterParam.push(filterObj);
        }

    }]);
};