(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Free_emitter', Free_emitter);

    Free_emitter.$inject = ['$resource'];

    function Free_emitter ($resource) {
        var resourceUrl =  'api/free-emitters/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
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
