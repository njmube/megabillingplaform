(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_donees', Freecom_donees);

    Freecom_donees.$inject = ['$resource', 'DateUtils'];

    function Freecom_donees ($resource, DateUtils) {
        var resourceUrl =  'api/freecom-donees/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.date_authorization = DateUtils.convertDateTimeFromServer(data.date_authorization);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
