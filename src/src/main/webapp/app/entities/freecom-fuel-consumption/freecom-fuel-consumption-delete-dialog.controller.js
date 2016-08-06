(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_fuel_consumptionDeleteController',Freecom_fuel_consumptionDeleteController);

    Freecom_fuel_consumptionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_fuel_consumption'];

    function Freecom_fuel_consumptionDeleteController($uibModalInstance, entity, Freecom_fuel_consumption) {
        var vm = this;
        vm.freecom_fuel_consumption = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_fuel_consumption.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
