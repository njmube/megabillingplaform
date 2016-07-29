(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_retentions_transfered', Freecom_retentions_transfered);

    Freecom_retentions_transfered.$inject = ['$resource'];

    function Freecom_retentions_transfered ($resource) {
        var resourceUrl =  'api/freecom-retentions-transfereds/:id';

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
