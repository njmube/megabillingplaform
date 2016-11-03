(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_used_vehicleDeleteController',Com_used_vehicleDeleteController);

    Com_used_vehicleDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_used_vehicle'];

    function Com_used_vehicleDeleteController($uibModalInstance, entity, Com_used_vehicle) {
        var vm = this;
        vm.com_used_vehicle = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_used_vehicle.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
