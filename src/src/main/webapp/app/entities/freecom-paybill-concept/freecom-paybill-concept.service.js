(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_paybill_concept', Freecom_paybill_concept);

    Freecom_paybill_concept.$inject = ['$resource', 'DateUtils'];

    function Freecom_paybill_concept ($resource, DateUtils) {
        var resourceUrl =  'api/freecom-paybill-concepts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.date_expedition = DateUtils.convertDateTimeFromServer(data.date_expedition);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
