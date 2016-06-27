(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_municipality', C_municipality);

    C_municipality.$inject = ['$resource'];

    function C_municipality ($resource) {
        var resourceUrl =  'api/c-municipalities/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true, params: {stateId: null}},
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

    /*function C_municipality ($resource) {
        var service = $resource('api/c-municipalitiesbystate', {}, {
            'queryByState': {
                method: 'GET',
                isArray: true,
                params: {stateId: null}
            }
        });

        return service;
    }*/
})();
