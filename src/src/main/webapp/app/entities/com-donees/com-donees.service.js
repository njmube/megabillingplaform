(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_donees', Com_donees);

    Com_donees.$inject = ['$resource', 'DateUtils'];

    function Com_donees ($resource, DateUtils) {
        var resourceUrl =  'api/com-donees/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date_authorization = DateUtils.convertDateTimeFromServer(data.date_authorization);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
