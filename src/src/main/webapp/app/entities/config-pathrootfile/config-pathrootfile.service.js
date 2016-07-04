(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Config_pathrootfile', Config_pathrootfile);

    Config_pathrootfile.$inject = ['$resource'];

    function Config_pathrootfile ($resource) {
        var resourceUrl =  'api/config-pathrootfiles/:id';

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
