(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_taxlegends', Freecom_taxlegends);

    Freecom_taxlegends.$inject = ['$resource'];

    function Freecom_taxlegends ($resource) {
        var resourceUrl =  'api/freecom-taxlegends/:id';

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
