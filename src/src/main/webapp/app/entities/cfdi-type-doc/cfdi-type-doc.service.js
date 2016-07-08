(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Cfdi_type_doc', Cfdi_type_doc);

    Cfdi_type_doc.$inject = ['$resource'];

    function Cfdi_type_doc ($resource) {
        var resourceUrl =  'api/cfdi-type-docs/:id';

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
