(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_conceptDialogController', Free_conceptDialogController);

    Free_conceptDialogController.$inject = ['$uibModalInstance', '$uibModal', 'Measure_unit', 'free_concept_entity', 'rate_typess', 'Tax_types', 'free_concept_ids', 'accuracy','disable_ieps'];

    function Free_conceptDialogController ($uibModalInstance, $uibModal, Measure_unit, free_concept_entity, rate_typess, Tax_types, free_concept_ids, accuracy, disable_ieps) {
        var vm = this;

        vm.free_concept = free_concept_entity;
		vm.iva = null;
		vm.ieps = (0).toFixed(2);
        vm.measure_units = Measure_unit.query({pg: -1, filtername:" "});
        vm.rate_typess = rate_typess;
        vm.tax_typess = Tax_types.query({filtername: " "});
		vm.free_part_concepts = [];
		vm.free_customs_infos = [];

		vm.free_concept_ids = free_concept_ids;
		vm.free_concept_id_valid = true;
		vm.accuracy = accuracy;
        vm.disable_ieps = disable_ieps;

		vm.calcAmount = function(){
            var regexp = /^\d{1,24}\.?\d{1,6}$/;
            var free_concept_quantity = vm.free_concept.quantity + '';
            var free_concept_unit_value = vm.free_concept.unit_value + '';
			if(vm.free_concept.quantity > 0 && free_concept_quantity.match(regexp) && vm.free_concept.unit_value > 0 && free_concept_unit_value.match(regexp)){
                var bln = new BigLargeNumberOperations();
				var amount = bln.multiply(vm.free_concept.quantity, vm.free_concept.unit_value, 6);
                var discount = 1 - vm.free_concept.discount/100;
				vm.free_concept.amount = bln.multiply(amount, discount, vm.accuracy);
			}
            else{
                vm.free_concept.amount = null;
            }
		};

        vm.onNoIdentificationChange = function(){
            var i;
            var exist = false;
            for(i = 0; i < vm.free_concept_ids.length; i++){
                if(vm.free_concept_ids[i] == vm.free_concept.no_identification){
                    vm.free_concept_id_valid = false;
                    exist = true;
                    break;
                }
            }

            if(!exist){
                vm.free_concept_id_valid = true;
            }

        };

        function floorFigure(figure, decimals){
            if (!decimals) decimals = 2;
            var d = Math.pow(10,decimals);
            return (parseInt(figure*d)/d).toFixed(decimals);
        }

        vm.save = function () {
            vm.isSaving = true;

            var bln = new BigLargeNumberOperations();

            var free_concept_iva_amount_p1 = bln.multiply(vm.free_concept.quantity, vm.free_concept.unit_value, 6);
            var free_concept_iva_amount_p2 = (1 - vm.free_concept.discount/100) * vm.iva.value/100;
            var free_concept_iva_amount = bln.multiply(free_concept_iva_amount_p1, free_concept_iva_amount_p2, vm.accuracy);

            var free_concept_ieps_amount_p1 = bln.multiply(vm.free_concept.quantity, vm.free_concept.unit_value, 6);
            var free_concept_ieps_amount_p2 = (1 - vm.free_concept.discount/100) * vm.ieps/100;
            var free_concept_ieps_amount = bln.multiply(free_concept_ieps_amount_p1, free_concept_ieps_amount_p2, vm.accuracy);

            $uibModalInstance.close({
				free_concept: vm.free_concept,
				free_concept_iva: {
							rate: floorFigure(vm.iva.value, 2),
                            amount: free_concept_iva_amount,
							tax_types: vm.tax_typess[0],
                            id: null
						},
				free_concept_ieps: {
							rate: floorFigure(vm.ieps, 2),
                            amount: free_concept_ieps_amount,
							tax_types: vm.tax_typess[2],
                            id: null
						},
				free_customs_infos: vm.free_customs_infos,
				free_part_concepts: vm.free_part_concepts

			});
			vm.isSaving = false;
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

		/*vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.date = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };*/

		vm.addFreeCustomInfo = function(){
			$uibModal.open({
				templateUrl: 'app/entities/free-customs-info/free-customs-info-dialog.html',
                controller: 'Free_customs_infoDialogController',
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
				vm.free_customs_infos.push(result);
			}, function() {

			});
		};

		vm.removeFreeCustomInfo = function(index){
			vm.free_customs_infos.splice(index,1);
		};

		vm.addFreePartConcept = function(){
			$uibModal.open({
				templateUrl: 'app/entities/free-part-concept/free-part-concept-dialog.html',
				controller: 'Free_part_conceptDialogController',
				controllerAs: 'vm',
				backdrop: 'static',
				size: '',
				resolve: {
					entity: function () {
						return {
							no_identification: null,
							quantity: 0,
							description: null,
							unit_value: (0).toFixed(vm.accuracy),
							amount: (0).toFixed(vm.accuracy),
							id: null
						};
					},
					accuracy: function(){
						return vm.accuracy;
					}
				}
			}).result.then(function(result) {
				vm.free_part_concepts.push(result);
			}, function() {

			});
		};

		vm.removeFreePartConcept = function(index){
			vm.free_part_concepts.splice(index,1);
		};
    }
})();
