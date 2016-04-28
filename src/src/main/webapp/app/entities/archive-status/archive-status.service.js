(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Archive_status', Archive_status);

    Archive_status.$inject = ['$resource'];

    function Archive_status ($resource) {
        var resourceUrl =  'api/archive-statuses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
