(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Cfdi_states', Cfdi_states);

    Cfdi_states.$inject = ['$resource'];

    function Cfdi_states ($resource) {
        var resourceUrl =  'api/cfdi-states/:id';

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
