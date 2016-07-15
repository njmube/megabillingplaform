(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_conceptDialogController', Free_conceptDialogController);

    Free_conceptDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$uibModal', 'free_concept_entity', 'Free_cfdi', 'Measure_unit', 'Rate_type', 'Tax_types', 'disabled_iva_value', 'accuracy'];

    function Free_conceptDialogController ($scope, $stateParams, $uibModalInstance, $uibModal, free_concept_entity, Free_cfdi, Measure_unit, Rate_type, Tax_types, disabled_iva_value, accuracy) {
        var vm = this;
		
        vm.free_concept = free_concept_entity;
		vm.iva = (0).toFixed(2);
		vm.ieps = (0).toFixed(2);	
        vm.measure_units = Measure_unit.query();
        vm.rate_typess = Rate_type.query({filtername: " "});
        vm.tax_typess = Tax_types.query({filtername: " "});
		vm.free_part_concepts = [];
		vm.free_customs_infos = [];
		
		vm.disabled_iva_value = disabled_iva_value;
		vm.accuracy = accuracy;
		
		vm.calcAmount = function(){
			/*SubTotal = (Cantidad * Precio unitario)*(1-Descuento/100)*/			
			if(vm.free_concept.quantity > 0 && vm.free_concept.unit_value > 0){
				var amount = vm.free_concept.quantity * vm.free_concept.unit_value * (1 - vm.free_concept.discount/100)
				vm.free_concept.amount = floorFigure(amount, vm.accuracy);
			}
		};
		
        vm.load = function(id) {
            Free_concept.get({id : id}, function(result) {
                vm.free_concept = result;
            });
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };
		
		function floorFigure(figure, decimals){
			if (!decimals) decimals = 2;
			var d = Math.pow(10,decimals);
			return (parseInt(figure*d)/d).toFixed(decimals);
		}

        vm.save = function () {
            vm.isSaving = true;
            $uibModalInstance.close({
				free_concept: {
							no_identification: vm.free_concept.no_identification,
							quantity: vm.free_concept.quantity,
							measure_unit: vm.free_concept.measure_unit,
							description: vm.free_concept.description,
							unit_value: floorFigure(vm.free_concept.unit_value, vm.accuracy),
							predial_number: vm.free_concept.predial_number,
							discount: vm.free_concept.discount,
							amount: vm.free_concept.amount,
							id: null
						},
				free_concept_iva: {
							rate: floorFigure(vm.iva.value, 2),
                            amount: floorFigure(vm.free_concept.quantity * vm.free_concept.unit_value * (1 - vm.free_concept.discount/100) * vm.iva.value/100,vm.accuracy),
							tax_types: vm.tax_typess[0],
                            id: null
						},
				free_concept_ieps: {
							rate: floorFigure(vm.ieps, 2),
                            amount: floorFigure(vm.free_concept.quantity * vm.free_concept.unit_value * (1 - vm.free_concept.discount/100) * vm.ieps/100, vm.accuracy),
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
							amount: (0).toFixed(2),
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
