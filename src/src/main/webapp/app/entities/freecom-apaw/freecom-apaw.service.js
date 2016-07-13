(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_apaw', Freecom_apaw);

    Freecom_apaw.$inject = ['$resource', 'DateUtils'];

    function Freecom_apaw ($resource, DateUtils) {
        var resourceUrl =  'api/freecom-apaws/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.date_acquisition = DateUtils.convertDateTimeFromServer(data.date_acquisition);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
