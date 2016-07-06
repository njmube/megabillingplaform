(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Free_emitter', Free_emitter);

    Free_emitter.$inject = ['$resource', 'DateUtils'];

    function Free_emitter ($resource, DateUtils) {
        var resourceUrl =  'api/free-emitters/:login';

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
