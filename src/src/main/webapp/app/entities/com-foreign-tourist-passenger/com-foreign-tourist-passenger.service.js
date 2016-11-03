(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_foreign_tourist_passenger', Com_foreign_tourist_passenger);

    Com_foreign_tourist_passenger.$inject = ['$resource', 'DateUtils'];

    function Com_foreign_tourist_passenger ($resource, DateUtils) {
        var resourceUrl =  'api/com-foreign-tourist-passengers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date_traffic = DateUtils.convertDateTimeFromServer(data.date_traffic);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
