(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_part_conceptDialogController', Free_part_conceptDialogController);

    Free_part_conceptDialogController.$inject = ['$uibModalInstance', 'entity', 'Free_part_concept', 'Free_concept', 'Measure_unit', 'accuracy'];

    function Free_part_conceptDialogController ($uibModalInstance, entity, Free_part_concept, Free_concept, Measure_unit, accuracy) {
        var vm = this;
        vm.free_part_concept = entity;
        vm.free_concepts = Free_concept.query();
        vm.measure_units = Measure_unit.query({pg: -1, filtername:" "});
		vm.accuracy = accuracy;

        vm.load = function(id) {
            Free_part_concept.get({id : id}, function(result) {
                vm.free_part_concept = result;
            });
        };

        vm.save = function () {
            vm.isSaving = true;
			$uibModalInstance.close(vm.free_part_concept);
			vm.isSaving = false;
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

		vm.calcAmount = function(){
            var regexp = /^\d{1,24}\.?\d{1,6}$/;
            var free_part_concept_quantity = vm.free_part_concept.quantity + '';
            var free_part_concept_unit_value = vm.free_part_concept.unit_value + '';
			if(vm.free_part_concept.quantity > 0 && free_part_concept_quantity.match(regexp) && vm.free_part_concept.unit_value > 0 && free_part_concept_unit_value.match(regexp)){
                var bln = new BigLargeNumberOperations();
                var amount = bln.multiply(vm.free_part_concept.quantity, vm.free_part_concept.unit_value, vm.accuracy);
				vm.free_part_concept.amount = amount;
			}
        };
    }
})();
