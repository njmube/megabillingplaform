(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Part_conceptDialogController', Part_conceptDialogController);

    Part_conceptDialogController.$inject = ['$uibModalInstance', 'entity', 'acurracy', 'Measure_unit', '$uibModal'];

    function Part_conceptDialogController ($uibModalInstance, entity, acurracy, Measure_unit, $uibModal) {
        var vm = this;

        vm.part_concept = entity;
        vm.acurracy = acurracy;
        vm.clear = clear;
        vm.save = save;
        vm.measure_units = Measure_unit.query({pg: -1, filtername:" "});
        vm.customs_info_parts = [];
        vm.addCustomInfoPart = addCustomInfoPart;
        vm.removeCustomInfoPart = removeCustomInfoPart;

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

        function addCustomInfoPart(){
            $uibModal.open({
                templateUrl: 'app/entities/customs-info-part/customs-info-part-dialog.html',
                controller: 'Customs_info_partDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            number_doc: null,
                            date: null,
                            customs: null,
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.customs_info_parts.push(result);
            });
        }

        function removeCustomInfoPart(index){
            vm.customs_info_parts.splice(index, 1);
        }

        function save () {
            vm.isSaving = true;
            $uibModalInstance.close({
                part_concept: vm.part_concept,
                customs_info_parts: vm.customs_info_parts
            });
            vm.isSaving = false;
        }
    }
})();
