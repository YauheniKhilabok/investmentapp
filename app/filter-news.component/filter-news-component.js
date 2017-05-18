'use strict';

module.exports = function (ngModule) {

    ngModule.component('nhFilterNews', {
        template: require('../filter-news.component/filter-news-template.html'),
        bindings: {
            listOfTags: '<',
            listOfAuthors: '<'
        },
        controller: ['$state', '$stateParams', 'TagService', 'AuthorService', 'NewsHelperService', 'FilterHelperService', 'ErrorFactory',
            function ($state, $stateParams, TagService, AuthorService, NewsHelperService, FilterHelperService, ErrorFactory) {
                var $ctrl = this;

                $ctrl.$onInit = function () {
                    $ctrl.error = null;
                    $ctrl.listOfFilteredNews = [];
                    $ctrl.listOfTagsId = '';
                    $ctrl.listOfAuthorsId = '';
                    $ctrl.tagsFilterParam = [];
                    $ctrl.authorsFilterParam = [];
                    $ctrl.selectedTag = undefined;
                    $ctrl.selectedAuthor = undefined;
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
                    var tagId = null;
                    var flag;
                    for (var i = 0; i < $ctrl.listOfTags.length; i++) {
                        if ($ctrl.selectedTag.name == $ctrl.listOfTags[i].name) {
                            tagId = $ctrl.listOfTags[i].id;
                            break;
                        }
                    }

                    function isTagIdExist(element) {
                        return element == tagId;
                    }

                    var tagsIdArray = $ctrl.listOfTagsId.split(',');
                    flag = tagsIdArray.some(isTagIdExist);
                    if (!flag) {
                        if ($ctrl.listOfTagsId === '') {
                            $ctrl.listOfTagsId += tagId;
                        } else {
                            $ctrl.listOfTagsId += ',' + tagId;
                        }
                        FilterHelperService.formTagsFilterParam(tagId, $ctrl);
                    }
                    $state.go('filter', {
                        page: $stateParams.page,
                        limit: $stateParams.limit,
                        tags: $ctrl.listOfTagsId.length == 0 ? null : $ctrl.listOfTagsId
                    }, {notify: false});
                    $ctrl.selectedTag = '';
                };

                $ctrl.updateTagsFilterParam = function (tagId) {
                    for (var i = 0; i < $ctrl.tagsFilterParam.length; i++) {
                        if ($ctrl.tagsFilterParam[i].id === tagId) {
                            $ctrl.tagsFilterParam.splice(i, 1);
                        }
                    }
                    var arrayOfTags = $ctrl.listOfTagsId.split(',');
                    for (i = 0; i < arrayOfTags.length; i++) {
                        if (arrayOfTags[i] == tagId) {
                            arrayOfTags.splice(i, 1);
                        }
                    }
                    $ctrl.listOfTagsId = '';
                    for (i = 0; i < arrayOfTags.length; i++) {
                        console.log(arrayOfTags.length);
                        if (arrayOfTags.length == 1) {
                            $ctrl.listOfTagsId += arrayOfTags[i];
                        } else if (arrayOfTags.length > 1) {
                            if (i == 0) {
                                $ctrl.listOfTagsId += arrayOfTags[i];
                            } else {
                                $ctrl.listOfTagsId += ',' + arrayOfTags[i];
                            }
                        } else {
                            $ctrl.listOfTagsId = '';
                        }
                    }
                    $state.go('filter', {
                        page: $stateParams.page,
                        limit: $stateParams.limit,
                        tags: $ctrl.listOfTagsId.length == 0 ? null : $ctrl.listOfTagsId
                    }, {notify: false});
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
                    var authorId = null;
                    var flag;
                    for (var i = 0; i < $ctrl.listOfAuthors.length; i++) {
                        if ($ctrl.selectedAuthor.name == $ctrl.listOfAuthors[i].name) {
                            authorId = $ctrl.listOfAuthors[i].id;
                            break;
                        }
                    }

                    function isAuthorIdExist(element) {
                        return element == authorId;
                    }

                    var authorsIdArray = $ctrl.listOfAuthorsId.split(',');
                    flag = authorsIdArray.some(isAuthorIdExist);
                    if (!flag) {
                        if ($ctrl.listOfAuthorsId === '') {
                            $ctrl.listOfAuthorsId += authorId;
                        } else {
                            $ctrl.listOfAuthorsId += ',' + authorId;
                        }
                        FilterHelperService.formAuthorsFilterParam(authorId, $ctrl);
                    }
                    $state.go('filter', {
                        page: $stateParams.page,
                        limit: $stateParams.limit,
                        authors: $ctrl.listOfAuthorsId.length == 0 ? null : $ctrl.listOfAuthorsId
                    }, {notify: false});
                    $ctrl.selectedAuthor = '';
                };

                $ctrl.updateAuthorsFilterParam = function (authorId) {
                    for (var i = 0; i < $ctrl.authorsFilterParam.length; i++) {
                        if ($ctrl.authorsFilterParam[i].id === authorId) {
                            $ctrl.authorsFilterParam.splice(i, 1);
                        }
                    }
                    var arrayOfAuthors = $ctrl.listOfAuthorsId.split(',');
                    for (i = 0; i < arrayOfAuthors.length; i++) {
                        if (arrayOfAuthors[i] == authorId) {
                            arrayOfAuthors.splice(i, 1);
                        }
                    }
                    $ctrl.listOfAuthorsId = '';
                    for (i = 0; i < arrayOfAuthors.length; i++) {
                        if (arrayOfAuthors.length == 1) {
                            $ctrl.listOfAuthorsId += arrayOfAuthors[i];
                        } else if (arrayOfAuthors.length > 1) {
                            if (i == 0) {
                                $ctrl.listOfAuthorsId += arrayOfAuthors[i];
                            } else {
                                $ctrl.listOfAuthorsId += ',' + arrayOfAuthors[i];
                            }
                        } else {
                            $ctrl.listOfAuthorsId = '';
                        }
                    }
                    $state.go('filter', {
                        page: $stateParams.page,
                        limit: $stateParams.limit,
                        authors: $ctrl.listOfAuthorsId.length == 0 ? null : $ctrl.listOfAuthorsId
                    }, {notify: false});
                };

                $ctrl.resetFilterParams = function () {
                    $ctrl.listOfTagsId = '';
                    $ctrl.listOfAuthorsId = '';
                    $ctrl.tagsFilterParam = [];
                    $ctrl.authorsFilterParam = [];
                    $state.go('getAll');
                };

            }]
    });
};