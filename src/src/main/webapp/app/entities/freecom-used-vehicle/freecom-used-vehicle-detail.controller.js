(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_used_vehicleDetailController', Freecom_used_vehicleDetailController);

    Freecom_used_vehicleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_used_vehicle', 'Free_cfdi'];

    function Freecom_used_vehicleDetailController($scope, $rootScope, $stateParams, entity, Freecom_used_vehicle, Free_cfdi) {
        var vm = this;
        vm.freecom_used_vehicle = entity;
        vm.load = function (id) {
            Freecom_used_vehicle.get({id: id}, function(result) {
                vm.freecom_used_vehicle = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_used_vehicleUpdate', function(event, result) {
            vm.freecom_used_vehicle = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
