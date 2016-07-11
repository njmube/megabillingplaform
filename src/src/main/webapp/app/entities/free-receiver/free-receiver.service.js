(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Free_receiver', Free_receiver);

    Free_receiver.$inject = ['$resource', 'DateUtils'];

    function Free_receiver ($resource, DateUtils) {
        var resourceUrl =  'api/free-receivers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.create_date = DateUtils.convertDateTimeFromServer(data.create_date);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
