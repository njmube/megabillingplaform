(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_state_eventDetailController', C_state_eventDetailController);

    C_state_eventDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_state_event'];

    function C_state_eventDetailController($scope, $rootScope, $stateParams, entity, C_state_event) {
        var vm = this;
        vm.c_state_event = entity;
        vm.load = function (id) {
            C_state_event.get({id: id}, function(result) {
                vm.c_state_event = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_state_eventUpdate', function(event, result) {
            vm.c_state_event = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
