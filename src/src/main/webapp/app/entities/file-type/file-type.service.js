(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('File_type', File_type);

    File_type.$inject = ['$resource'];

    function File_type ($resource) {
        var resourceUrl =  'api/file-types/:id';

        return $resource(resourceUrl, {}, {
            'query': {
                method: 'GET',
                isArray: true,
                params: {filtername: null}},
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
