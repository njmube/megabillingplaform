(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Free_tax_retentions', Free_tax_retentions);

    Free_tax_retentions.$inject = ['$resource'];

    function Free_tax_retentions ($resource) {
        var resourceUrl =  'api/free-tax-retentions/:id';

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
