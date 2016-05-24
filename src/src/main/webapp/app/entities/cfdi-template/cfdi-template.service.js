(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Cfdi_template', Cfdi_template);

    Cfdi_template.$inject = ['$resource'];

    function Cfdi_template ($resource) {
        var resourceUrl =  'api/cfdi-templates/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
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
