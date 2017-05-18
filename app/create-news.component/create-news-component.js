'use strict';

module.exports = function (ngModule) {

    ngModule.component('nhCreateNews', {
        template: require('../common/template/form-template.html'),
        bindings: {
            resolve: '<',
            close: '&',
            dismiss: '&'
        },
        controller: ['$filter', 'NewsService', 'AuthorService', 'TagService', 'NewsHelperService', 'ErrorFactory', function ($filter, NewsService, AuthorService, TagService, NewsHelperService, ErrorFactory) {
            var $ctrl = this;

            $ctrl.$onInit = function () {
                $ctrl.error = null;
                $ctrl.news = {
                    id: null,
                    title: '',
                    briefContent: '',
                    text: '',
                    authors: [],
                    tags: []
                };
                $ctrl.selectedAuthor = undefined;
                $ctrl.selectedTag = undefined;
                $ctrl.listOfAuthors = [];
                $ctrl.listOfTags = [];
                $ctrl.listWithSelectedAuthors = [];
                $ctrl.listWithSelectedTags = [];
                $ctrl.createNewsErrorMessage = null;
            };

            $ctrl.findAuthorsByName = function (name) {
                return AuthorService.findAuthorByName(name)
                    .then(
                        function (d) {
                            $ctrl.listOfAuthors = d;
                            return d;
                        },
                        function (errResponse) {
                            $ctrl.error = ErrorFactory.formServerError(errResponse);
                            console.error('Error while getting list of authors:' + errResponse);
                        }
                    )
            };

            $ctrl.formListWithSelectedAuthors = function ($item) {
                $ctrl.selectedAuthor = $item;
                var selectedAuthor;
                if (!NewsHelperService.isAuthorExist($ctrl.listWithSelectedAuthors, $ctrl.selectedAuthor)) {
                    for (var i = 0; i < $ctrl.listOfAuthors.length; i++) {
                        if ($ctrl.selectedAuthor.name == $ctrl.listOfAuthors[i].name) {
                            selectedAuthor = {
                                id: $ctrl.listOfAuthors[i].id,
                                name: $ctrl.listOfAuthors[i].name
                            };
                            break;
                        }
                    }
                    $ctrl.listWithSelectedAuthors.push(selectedAuthor);
                }
                $ctrl.selectedAuthor = null;
                $ctrl.createNewsErrorMessage = null
            };

            $ctrl.deleteAuthorsFromList = function (authorId) {
                for (var i = 0; i < $ctrl.listWithSelectedAuthors.length; i++) {
                    if ($ctrl.listWithSelectedAuthors[i].id === authorId) {
                        $ctrl.listWithSelectedAuthors.splice(i, 1);
                    }
                }
            };

            $ctrl.findTagsByName = function (name) {
                return TagService.findTagByName(name)
                    .then(
                        function (d) {
                            $ctrl.listOfTags = d;
                            return d;
                        },
                        function (errResponse) {
                            $ctrl.error = ErrorFactory.formServerError(errResponse);
                            console.error('Error while getting list of tags:' + errResponse);
                        }
                    )
            };

            $ctrl.formListWithSelectedTags = function ($item) {
                $ctrl.selectedTag = $item;
                var selectedTag;
                if (!NewsHelperService.isTagExist($ctrl.listWithSelectedTags, $ctrl.selectedTag)) {
                    for (var i = 0; i < $ctrl.listOfTags.length; i++) {
                        if ($ctrl.selectedTag.name == $ctrl.listOfTags[i].name) {
                            selectedTag = {
                                id: $ctrl.listOfTags[i].id,
                                name: $ctrl.listOfTags[i].name
                            };
                            break;
                        }
                    }
                    $ctrl.listWithSelectedTags.push(selectedTag);
                }
                $ctrl.selectedTag = null;
            };

            $ctrl.deleteTagsFromList = function (tagId) {
                for (var i = 0; i < $ctrl.listWithSelectedTags.length; i++) {
                    if ($ctrl.listWithSelectedTags[i].id === tagId) {
                        $ctrl.listWithSelectedTags.splice(i, 1);
                    }
                }
            };

            $ctrl.save = function () {
                NewsHelperService.setSelectedAuthorsIdToNewsAuthors($ctrl.listWithSelectedAuthors, $ctrl.news.authors);
                NewsHelperService.setSelectedTagsIdToNewsTags($ctrl.listWithSelectedTags, $ctrl.news.tags);
                if ($ctrl.listWithSelectedAuthors.length != 0) {
                    NewsService.createNews($ctrl.news)
                        .then(
                            function (d) {
                                $ctrl.news = d;
                                $ctrl.close({$value: $ctrl.news.id});
                            },
                            function (errResponse) {
                                $ctrl.error = ErrorFactory.formServerError(errResponse);
                                console.error('Error while creating news: ' + errResponse);
                            }
                        );
                } else {
                    var $translate = $filter('translate');
                    $ctrl.createNewsErrorMessage = $translate('author_required_message');
                }
            };

            $ctrl.reset = function () {
                $ctrl.news = {title: '', briefContent: '', text: ''};
            };

            $ctrl.cancel = function () {
                $ctrl.dismiss({$value: 'cancel'});
            };

        }]
    });
};
