(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Cfdi_types', Cfdi_types);

    Cfdi_types.$inject = ['$resource'];

    function Cfdi_types ($resource) {
        var resourceUrl =  'api/cfdi-types/:id';

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
