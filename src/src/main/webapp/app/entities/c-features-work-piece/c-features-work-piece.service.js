(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_features_work_piece', C_features_work_piece);

    C_features_work_piece.$inject = ['$resource'];

    function C_features_work_piece ($resource) {
        var resourceUrl =  'api/c-features-work-pieces/:id';

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
