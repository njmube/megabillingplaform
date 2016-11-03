(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_paybill_concept', Com_paybill_concept);

    Com_paybill_concept.$inject = ['$resource', 'DateUtils'];

    function Com_paybill_concept ($resource, DateUtils) {
        var resourceUrl =  'api/com-paybill-concepts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date_expedition = DateUtils.convertDateTimeFromServer(data.date_expedition);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
