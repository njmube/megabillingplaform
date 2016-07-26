(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_part_conceptDialogController', Free_part_conceptDialogController);

    Free_part_conceptDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Free_part_concept', 'Free_concept', 'Measure_unit', 'accuracy'];

    function Free_part_conceptDialogController ($scope, $stateParams, $uibModalInstance, entity, Free_part_concept, Free_concept, Measure_unit, accuracy) {
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
			$uibModalInstance.close({
				no_identification: vm.free_part_concept.no_identification,
				measure_unit: vm.free_part_concept.measure_unit,
				quantity: vm.free_part_concept.quantity,
				description: vm.free_part_concept.description,
				unit_value: floorFigure(vm.free_part_concept.unit_value, vm.accuracy),
				amount: vm.free_part_concept.amount,
				id: null
			});
			vm.isSaving = false;
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

		vm.calcAmount = function(){
			if(vm.free_part_concept.quantity > 0 && vm.free_part_concept.unit_value){
				var amount = vm.free_part_concept.quantity * vm.free_part_concept.unit_value;
				vm.free_part_concept.amount = floorFigure(amount, vm.accuracy);
			}
        };

		function floorFigure(figure, decimals){
			if (!decimals) decimals = 2;
			var d = Math.pow(10,decimals);
			return (parseInt(figure*d)/d).toFixed(decimals);
		}
    }
})();
