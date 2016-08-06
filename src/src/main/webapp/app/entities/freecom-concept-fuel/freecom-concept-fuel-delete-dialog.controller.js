(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_concept_fuelDeleteController',Freecom_concept_fuelDeleteController);

    Freecom_concept_fuelDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_concept_fuel'];

    function Freecom_concept_fuelDeleteController($uibModalInstance, entity, Freecom_concept_fuel) {
        var vm = this;
        vm.freecom_concept_fuel = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_concept_fuel.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
