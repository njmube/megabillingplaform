(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_tfd', Com_tfd);

    Com_tfd.$inject = ['$resource', 'DateUtils'];

    function Com_tfd ($resource, DateUtils) {
        var resourceUrl =  'api/com-tfds/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.stamp_date = DateUtils.convertDateTimeFromServer(data.stamp_date);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
