(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Free_emitter_file', Free_emitter_file);

    Free_emitter_file.$inject = ['$resource', 'DateUtils'];

    function Free_emitter_file ($resource, DateUtils) {
        var resourceUrl =  '/free-emitters/loadfile';

        return $resource(resourceUrl, {}, {
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.create_date = DateUtils.convertDateTimeFromServer(data.create_date);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
