(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_conceptDialogController', Free_conceptDialogController);

    Free_conceptDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'free_concept_entity', 'free_custom_info_entity', 'Free_concept', 'Free_cfdi', 'Measure_unit', 'Tax_transfered', '$uibModal'];

    function Free_conceptDialogController ($scope, $stateParams, $uibModalInstance, free_concept_entity, free_custom_info_entity, Free_concept, Free_cfdi, Measure_unit, Tax_transfered, $uibModal) {
        var vm = this;
		
        vm.free_concept = free_concept_entity;
        vm.iva = null;
        vm.ieps = null;
		
        vm.free_customs_info = free_custom_info_entity;
		
        vm.free_cfdis = Free_cfdi.query();
        vm.measure_units = Measure_unit.query();
        vm.tax_transfereds = Tax_transfered.query();
		
		vm.free_part_concepts = [];
		
        vm.load = function(id) {
            Free_concept.get({id : id}, function(result) {
                vm.free_concept = result;
            });
        };

        var onSaveSuccess = function (result) {            
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            $uibModalInstance.close({
				free_concept: {
							no_identification: vm.free_concept.no_identification,
							quantity: vm.free_concept.quantity,
							measure_unit: vm.free_concept.measure_unit,
							description: vm.free_concept.description,
							unit_value: vm.free_concept.unit_value,
							predial_number: vm.free_concept.predial_number,
							amount: vm.free_concept.amount,
							id: null
						},
				free_custom_info: {
							number_doc: vm.free_customs_info.number_doc,
							date: vm.free_customs_info.date,
							customs: vm.free_customs_info.customs,
							id: null
						},
				free_part_concepts: vm.free_part_concepts				
					
			});
			vm.isSaving = false;
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
		
		vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.date = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
		
		vm.openFreePartConcept = function(){
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
							unit_value: 0,
							amount: 0.00,
							id: null
						};
					}
				}
			}).result.then(function(result) {
				vm.free_part_concepts.push(result);
			}, function() {
				
			});
		};
    }
})();
