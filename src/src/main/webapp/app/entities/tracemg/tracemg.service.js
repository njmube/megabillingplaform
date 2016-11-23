(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Tracemg', Tracemg);

    Tracemg.$inject = ['$resource', 'DateUtils'];

    function Tracemg ($resource, DateUtils) {
        var resourceUrl =  'api/tracemgs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true,
                params: {fromDate: null, toDate: null, principal: null, auditEventType: null, ip:null}},
            'queryAccount': { method: 'GET', isArray: true,
                params: {principal: null}},
            'getTimeLastFailureLogin':{method: 'GET', params: {time: null, delay: null}},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.timestamp = DateUtils.convertDateTimeFromServer(data.timestamp);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
