(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_municipality', C_municipality);

    C_municipality.$inject = ['$resource'];

    function C_municipality ($resource) {
        var resourceUrl =  'api/c-municipalities/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true, params: {stateId: null,filtername:null}},
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
