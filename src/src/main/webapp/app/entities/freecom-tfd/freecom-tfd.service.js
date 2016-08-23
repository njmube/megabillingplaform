(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_tfd', Freecom_tfd);

    Freecom_tfd.$inject = ['$resource', 'DateUtils'];

    function Freecom_tfd ($resource, DateUtils) {
        var resourceUrl =  'api/freecom-tfds/:id';

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
