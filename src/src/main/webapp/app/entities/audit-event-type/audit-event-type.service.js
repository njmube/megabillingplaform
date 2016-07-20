(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Audit_event_type', Audit_event_type);

    Audit_event_type.$inject = ['$resource'];

    function Audit_event_type ($resource) {
        var resourceUrl =  'api/audit-event-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true, params: {pg: null}},
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
