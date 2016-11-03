(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_apaw', Com_apaw);

    Com_apaw.$inject = ['$resource', 'DateUtils'];

    function Com_apaw ($resource, DateUtils) {
        var resourceUrl =  'api/com-apaws/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date_acquisition = DateUtils.convertDateTimeFromServer(data.date_acquisition);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
