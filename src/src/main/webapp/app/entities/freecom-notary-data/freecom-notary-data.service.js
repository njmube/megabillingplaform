(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_notary_data', Freecom_notary_data);

    Freecom_notary_data.$inject = ['$resource'];

    function Freecom_notary_data ($resource) {
        var resourceUrl =  'api/freecom-notary-data/:id';

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
