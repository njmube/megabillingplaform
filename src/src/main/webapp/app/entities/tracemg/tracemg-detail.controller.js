(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('TracemgDetailController', TracemgDetailController);

    TracemgDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Tracemg', 'Audit_event_type', 'C_state_event'];

    function TracemgDetailController($scope, $rootScope, $stateParams, entity, Tracemg, Audit_event_type, C_state_event) {
        var vm = this;
        vm.tracemg = entity;
        vm.load = function (id) {
            Tracemg.get({id: id}, function(result) {
                vm.tracemg = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:tracemgUpdate', function(event, result) {
            vm.tracemg = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
