'use strict';

module.exports = function (ngModule) {

    ngModule.factory('NewsHelperService', [function () {

        var factory = {
            setSelectedAuthorsIdToNewsAuthors: setSelectedAuthorsIdToNewsAuthors,
            setSelectedTagsIdToNewsTags: setSelectedTagsIdToNewsTags,
            formListWithAuthorsName: formListWithAuthorsName,
            formListWithTagsName: formListWithTagsName,
            isAuthorExist: isAuthorExist,
            isTagExist: isTagExist
        };

        return factory;

        function setSelectedAuthorsIdToNewsAuthors(listWithSelectedAuthors, authors) {
            for (var i = 0; i < listWithSelectedAuthors.length; i++) {
                var authorId = {
                    id: listWithSelectedAuthors[i].id
                };
                authors.push(authorId);
            }
        }

        function setSelectedTagsIdToNewsTags(listWithSelectedTags, tags) {
            for (var i = 0; i < listWithSelectedTags.length; i++) {
                var tagId = {
                    id: listWithSelectedTags[i].id
                };
                tags.push(tagId);
            }
        }

        function formListWithAuthorsName(listWithAuthorsName, listOfAuthors) {
            for (var i = 0; i < listOfAuthors.length; i++) {
                listWithAuthorsName.push(listOfAuthors[i].name);
            }
        }

        function formListWithTagsName(listWithTagsName, listOfTags) {
            for (var i = 0; i < listOfTags.length; i++) {
                listWithTagsName.push(listOfTags[i].name);
            }
        }

        function isAuthorExist(listWithSelectedAuthors, selectedAuthor) {
            for (var i = 0; i < listWithSelectedAuthors.length; i++) {
                if (listWithSelectedAuthors[i].name == selectedAuthor) {
                    return true;
                }
            }
            return false;
        }

        function isTagExist(listWithSelectedTags, selectedTag) {
            for (var i = 0; i < listWithSelectedTags.length; i++) {
                if (listWithSelectedTags[i].name == selectedTag) {
                    return true;
                }
            }
            return false;
        }

    }]);
};