(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Free_reciver', Free_reciver);

    Free_reciver.$inject = ['$resource'];

    function Free_reciver ($resource) {
        var resourceUrl =  'api/free-recivers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
