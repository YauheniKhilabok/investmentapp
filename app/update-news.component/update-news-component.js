'use strict';

module.exports = function (ngModule) {

    ngModule.component('nhUpdateNews', {
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
                    authors: [
                        {
                            id: null,
                            name: ''
                        }
                    ],
                    tags: [
                        {
                            id: null,
                            name: ''
                        }
                    ]
                };
                $ctrl.selectedAuthor = undefined;
                $ctrl.selectedTag = undefined;
                $ctrl.listOfAuthors = [];
                $ctrl.listOfTags = [];
                $ctrl.listWithSelectedAuthors = [];
                $ctrl.listWithSelectedTags = [];
                $ctrl.news.id = $ctrl.resolve.id;

                NewsService.getConcreteNews($ctrl.news.id)
                    .then(
                        function (d) {
                            $ctrl.news = d;
                            initializeSelectedAuthors($ctrl.news.authors);
                            initializeSelectedTags($ctrl.news.tags);
                        },
                        function (errResponse) {
                            $ctrl.error = ErrorFactory.formServerError(errResponse);
                            console.error('Error while getting concrete news: ' + errResponse);
                        }
                    );
            };

            function initializeSelectedAuthors(authors) {
                for (var i = 0; i < authors.length; i++) {
                    var authorObj = {
                        id: authors[i].id,
                        name: authors[i].name
                    };
                    $ctrl.listWithSelectedAuthors.push(authorObj);
                }
                $ctrl.news.authors = [];
            }

            function initializeSelectedTags(tags) {
                for (var i = 0; i < tags.length; i++) {
                    var tagObj = {
                        id: tags[i].id,
                        name: tags[i].name
                    };
                    $ctrl.listWithSelectedTags.push(tagObj);
                }
                $ctrl.news.tags = [];
            }

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
                $ctrl.selectedAuthor = '';
                $ctrl.createNewsErrorMessage = ''
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

            $ctrl.update = function () {
                NewsHelperService.setSelectedAuthorsIdToNewsAuthors($ctrl.listWithSelectedAuthors, $ctrl.news.authors);
                NewsHelperService.setSelectedTagsIdToNewsTags($ctrl.listWithSelectedTags, $ctrl.news.tags);
                if ($ctrl.listWithSelectedAuthors.length != 0) {
                    NewsService.updateNews($ctrl.news.id, $ctrl.news)
                        .then(function () {
                                $ctrl.close({$value: 'close'});
                            },
                            function (errResponse) {
                                $ctrl.error = ErrorFactory.formServerError(errResponse);
                                console.error('Error while updating news: ' + errResponse);
                            }
                        )
                }
                else {
                    var $translate = $filter('translate');
                    $ctrl.createNewsErrorMessage = $translate('author_required_message');
                }
            };

            $ctrl.reset = function () {
                $ctrl.news = {id: $ctrl.news.id, title: '', briefContent: '', text: ''};
            };

            $ctrl.cancel = function () {
                $ctrl.dismiss({$value: 'cancel'});
            };

        }]
    });
};