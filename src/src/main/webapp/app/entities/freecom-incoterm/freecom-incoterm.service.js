(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_incoterm', Freecom_incoterm);

    Freecom_incoterm.$inject = ['$resource'];

    function Freecom_incoterm ($resource) {
        var resourceUrl =  'api/freecom-incoterms/:id';

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
