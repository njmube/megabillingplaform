(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_addressee', Freecom_addressee);

    Freecom_addressee.$inject = ['$resource'];

    function Freecom_addressee ($resource) {
        var resourceUrl =  'api/freecom-addressees/:id';

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
