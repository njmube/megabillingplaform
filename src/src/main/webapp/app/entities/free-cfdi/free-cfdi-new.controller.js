(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_cfdiNewController', Free_cfdiNewController);

    Free_cfdiNewController.$inject = ['$scope', '$stateParams', 'entity', 'Free_cfdi', 'Cfdi_types', 'Cfdi_states', 'free_emitter_entity', 'user', 'Payment_method', 'Way_payment', 'C_money', 'Cfdi_type_doc', 'Tax_regime', 'DataUtils', 'free_receiver_entity', 'Free_receiver', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code', '$uibModal'];

    function Free_cfdiNewController ($scope, $stateParams, entity, Free_cfdi, Cfdi_types, Cfdi_states, free_emitter_entity, user, Payment_method, Way_payment, C_money, Cfdi_type_doc, Tax_regime, DataUtils, free_receiver_entity, Free_receiver, C_country, C_state, C_municipality, C_colony, C_zip_code, $uibModal) {
        
		var vm = this;
		
		vm.account = user;
		
        vm.free_cfdi = entity;
		
        vm.free_cfdi.free_emitter = free_emitter_entity;
		
		vm.free_concepts = [];
		
        vm.free_receiver = free_receiver_entity;
		vm.c_countrys = C_country.query({pg:1});
        vm.c_states = C_state.query({countryId:-1});
        vm.onChangeC_country = onChangeC_country;
        vm.c_municipalitys = C_municipality.query({stateId:-1});
        vm.onChangeC_state = onChangeC_state;
        vm.c_colonys = C_colony.query();
        vm.c_zip_codes = C_zip_code.query();
		
		
        vm.cfdi_typess = Cfdi_types.query();
        vm.cfdi_statess = Cfdi_states.query();
        vm.payment_methods = Payment_method.query();
        vm.way_payments = Way_payment.query();
        vm.c_moneys = C_money.query();
        vm.cfdi_type_docs = Cfdi_type_doc.query();
        vm.tax_regimes = Tax_regime.query();
		
        vm.load = function(id) {
            Free_cfdi.get({id : id}, function(result) {
                vm.free_cfdi = result;
            });
        };
		
		function onChangeC_country () {
			var countryId = vm.free_emitter.c_country.id;
            C_state.query({countryId: countryId}, function(result){
                vm.c_states = result;
            });
        }

        function onChangeC_state () {
            var stateId = vm.free_emitter.c_state.id;
            C_municipality.query({stateId: stateId}, function(result){
                vm.c_municipalitys = result;
            });
        }
		
		
		var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:free_cfdiUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };
		
		var onSaveFreeReceiverSuccess = function (result) {
            
			vm.free_receiver = result;
			vm.free_cfdi.free_receiver = vm.free_receiver;
			
			vm.free_cfdi.version = '3.2';
			vm.free_cfdi.date_expedition = Date.now();
			vm.free_cfdi.place_expedition = vm.free_cfdi.free_emitter.c_country.name + ", " + vm.free_cfdi.free_emitter.c_state.name + ", " + vm.free_cfdi.free_emitter.c_municipality.name + ", " + vm.free_cfdi.free_emitter.c_colony.name + ", " + vm.free_cfdi.free_emitter.c_zip_code.postcode;
			
			if (vm.free_cfdi.id !== null) {
                Free_cfdi.update(vm.free_cfdi, onSaveSuccess, onSaveError);
            } else {
                Free_cfdi.save(vm.free_cfdi, onSaveSuccess, onSaveError);
            }
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.free_receiver.id !== null) {
                Free_receiver.update(vm.free_receiver, onSaveFreeReceiverSuccess, onSaveError);
            } else {
                Free_receiver.save(vm.free_receiver, onSaveFreeReceiverSuccess, onSaveError);
            }
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.date_expedition = false;
        vm.datePickerOpenStatus.date_folio_fiscal_orig = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
		
		vm.openFile = DataUtils.openFile;
		
		vm.addConcept = function(){
			$uibModal.open({
				templateUrl: 'app/entities/free-concept/free-concept-dialog.html',
				controller: 'Free_conceptDialogController',
				controllerAs: 'vm',
				backdrop: true,
				size: '',
				resolve: {
					free_concept_entity: function () {
						return {
							no_identification: null,
							quantity: null,
							description: null,
							unit_value: null,
							predial_number: null,
							discount: null,
							amount: null,
							id: null
						};
					},
					free_custom_info_entity: function () {
						return {
							number_doc: null,
							date: null,
							customs: null,
							id: null
						};			
					},
				}
			}).result.then(function(result) {
				vm.free_concepts.push(result);
			}, function() {
				console.log('no result');
			});
		};
    }
})();
