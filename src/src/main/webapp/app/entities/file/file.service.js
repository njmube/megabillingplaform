(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('File', File);

    File.$inject = ['$resource'];

    function File ($resource) {
        var resourceUrl =  'api/files/:id';

        return $resource(resourceUrl, {}, {
            'query': {
                method: 'GET',
                isArray: true,
                params: {filtername: null}},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
