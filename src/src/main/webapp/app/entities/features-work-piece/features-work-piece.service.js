(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Features_work_piece', Features_work_piece);

    Features_work_piece.$inject = ['$resource'];

    function Features_work_piece ($resource) {
        var resourceUrl =  'api/features-work-pieces/:id';

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
