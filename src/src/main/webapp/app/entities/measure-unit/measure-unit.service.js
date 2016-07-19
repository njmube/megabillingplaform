(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Measure_unit', Measure_unit);

    Measure_unit.$inject = ['$resource'];

    function Measure_unit ($resource) {
        var resourceUrl =  'api/measure-units/:id';

        return $resource(resourceUrl, {}, {
            'query': {
                method: 'GET',
                isArray: true,
                params: {filtername: null}},
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
