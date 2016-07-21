(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_school_level', C_school_level);

    C_school_level.$inject = ['$resource'];

    function C_school_level ($resource) {
        var resourceUrl =  'api/c-school-levels/:id';

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
