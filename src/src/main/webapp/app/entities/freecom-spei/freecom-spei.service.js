(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_spei', Freecom_spei);

    Freecom_spei.$inject = ['$resource'];

    function Freecom_spei ($resource) {
        var resourceUrl =  'api/freecom-speis/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
