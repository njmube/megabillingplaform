(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Tax_retentions', Tax_retentions);

    Tax_retentions.$inject = ['$resource'];

    function Tax_retentions ($resource) {
        var resourceUrl =  'api/tax-retentions/:id';

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
