(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Part_conceptDialogController', Part_conceptDialogController);

    Part_conceptDialogController.$inject = ['$uibModalInstance', 'entity', 'acurracy', 'Measure_unit'];

    function Part_conceptDialogController ($uibModalInstance, entity, acurracy, Measure_unit) {
        var vm = this;

        vm.part_concept = entity;
        vm.acurracy = acurracy;
        vm.clear = clear;
        vm.save = save;
        vm.measure_units = Measure_unit.query({pg: -1, filtername:" "});

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        vm.calcAmount = calcAmount;

        function calcAmount(){
            var regexp = /^\d{1,24}\.?\d{1,6}$/;
            var part_concept_unit_value = vm.part_concept.unit_value + '';
            if(vm.part_concept.quanitty > 0 && vm.part_concept.unit_value > 0 && part_concept_unit_value.match(regexp)){
                var bln = new BigLargeNumberOperations();
                var amount = bln.multiply(vm.part_concept.quanitty, vm.part_concept.unit_value, vm.acurracy);
                vm.part_concept.amount = amount;
            }
            else {
                vm.part_concept.amount = null;
            }
        }

        function save () {
            vm.isSaving = true;
            $uibModalInstance.close(vm.part_concept);
            vm.isSaving = false;
        }
    }
})();
