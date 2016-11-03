(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_ecc_11_concept', Com_ecc_11_concept);

    Com_ecc_11_concept.$inject = ['$resource', 'DateUtils'];

    function Com_ecc_11_concept ($resource, DateUtils) {
        var resourceUrl =  'api/com-ecc-11-concepts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date = DateUtils.convertDateTimeFromServer(data.date);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
