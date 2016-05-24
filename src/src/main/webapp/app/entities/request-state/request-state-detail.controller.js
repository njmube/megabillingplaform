(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Request_stateDetailController', Request_stateDetailController);

    Request_stateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Request_state'];

    function Request_stateDetailController($scope, $rootScope, $stateParams, entity, Request_state) {
        var vm = this;
        vm.request_state = entity;
        vm.load = function (id) {
            Request_state.get({id: id}, function(result) {
                vm.request_state = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:request_stateUpdate', function(event, result) {
            vm.request_state = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
