(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_concept_fuelDeleteController',Com_concept_fuelDeleteController);

    Com_concept_fuelDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_concept_fuel'];

    function Com_concept_fuelDeleteController($uibModalInstance, entity, Com_concept_fuel) {
        var vm = this;
        vm.com_concept_fuel = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_concept_fuel.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
