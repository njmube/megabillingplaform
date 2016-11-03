(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_used_vehicleDetailController', Com_used_vehicleDetailController);

    Com_used_vehicleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_used_vehicle', 'Cfdi'];

    function Com_used_vehicleDetailController($scope, $rootScope, $stateParams, entity, Com_used_vehicle, Cfdi) {
        var vm = this;
        vm.com_used_vehicle = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_used_vehicleUpdate', function(event, result) {
            vm.com_used_vehicle = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
