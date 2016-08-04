(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_used_vehicleDeleteController',Freecom_used_vehicleDeleteController);

    Freecom_used_vehicleDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_used_vehicle'];

    function Freecom_used_vehicleDeleteController($uibModalInstance, entity, Freecom_used_vehicle) {
        var vm = this;
        vm.freecom_used_vehicle = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_used_vehicle.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
