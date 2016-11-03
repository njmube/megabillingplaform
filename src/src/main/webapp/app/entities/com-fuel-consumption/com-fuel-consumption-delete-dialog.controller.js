(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_fuel_consumptionDeleteController',Com_fuel_consumptionDeleteController);

    Com_fuel_consumptionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_fuel_consumption'];

    function Com_fuel_consumptionDeleteController($uibModalInstance, entity, Com_fuel_consumption) {
        var vm = this;
        vm.com_fuel_consumption = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_fuel_consumption.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
