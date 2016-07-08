(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_cfdiNewController', Free_cfdiNewController);

    Free_cfdiNewController.$inject = ['$scope', '$stateParams', 'entity', 'Free_cfdi', 'Cfdi_types', 'Cfdi_states', 'free_emitter_entity', 'user', 'Payment_method', 'Way_payment', 'C_money', 'Cfdi_type_doc', 'Tax_regime', 'DataUtils', 'free_receiver_entity', 'Free_receiver', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code', '$uibModal','Free_concept', 'Free_customs_info', 'Free_part_concept', 'Free_tax_transfered'];

    function Free_cfdiNewController ($scope, $stateParams, entity, Free_cfdi, Cfdi_types, Cfdi_states, free_emitter_entity, user, Payment_method, Way_payment, C_money, Cfdi_type_doc, Tax_regime, DataUtils, free_receiver_entity, Free_receiver, C_country, C_state, C_municipality, C_colony, C_zip_code, $uibModal, Free_concept, Free_customs_info, Free_part_concept, Free_tax_transfered) {
        
		var vm = this;
		
		vm.account = user;
		
        vm.free_cfdi = entity;		
        vm.free_cfdi.free_emitter = free_emitter_entity;		
		vm.free_concepts = [];
		vm.current_free_concept = null;
		vm.current_part_concept = null;
		
        vm.free_receiver = free_receiver_entity;		
		vm.c_countrys = C_country.query({pg:1});
        vm.c_states = C_state.query({countryId:-1});
        vm.c_municipalitys = C_municipality.query({stateId:-1});
		vm.c_colonys = null;		
		
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
		
		vm.onChangeC_country = onChangeC_country;
        vm.onChangeC_state = onChangeC_state;
        vm.onChangeC_municipality = onChangeC_municipality;
        vm.onChangeC_colony = onChangeC_colony;
		
		function onChangeC_country () {
			var countryId = vm.free_receiver.c_country.id;
            C_state.query({countryId: countryId}, function(result){
                vm.c_states = result;
            });
        }

        function onChangeC_state () {
            var stateId = vm.free_receiver.c_state.id;
            C_municipality.query({stateId: stateId}, function(result){
                vm.c_municipalitys = result;
            });
        }

        function onChangeC_municipality () {
            var municipalityId = vm.free_receiver.c_municipality.id;
            C_colony.query({municipalityId: municipalityId}, function(result){
                vm.c_colonys = result;
            });
        }

        function onChangeC_colony(){

            C_zip_code.get({id : vm.free_receiver.c_colony.c_zip_code.id}, function(result) {
                vm.free_receiver.c_zip_code = result;
            });
        }

        var onSaveError = function () {
            vm.isSaving = false;
        };
		
		var onSaveIVASuccess = function (result) {
			vm.free_concepts[vm.current_free_concept].free_concept_iva = result;
		};
		
		var onSaveIEPSSuccess = function (result) {
			vm.free_concepts[vm.current_free_concept].free_concept_ieps = result;
		};
		
		var onSaveCustomInfoSuccess = function (result) {
			vm.free_concepts[vm.current_free_concept].free_custom_info = result;
		};
		
		var onSavePartConceptSuccess = function (result) {
			vm.free_concepts[vm.current_free_concept].free_part_concepts[vm.current_part_concept] = result;
			vm.current_part_concept++;
		};
		
		var onSaveConceptSuccess = function (result) {
			var free_concept_saved = result;
			var c_cpt = vm.current_free_concept;
			vm.free_concepts[c_cpt].free_concept = free_concept_saved;
			
			//saving IVA in tax_transferred...
			/*var free_concept_iva = vm.free_concepts[c_cpt].free_concept_iva;			
			free_concept_iva.free_concept = vm.free_concepts[c_cpt].free_concept;					
			if (free_concept_iva.id !== null) {
				Free_tax_transfered.update(free_concept_iva, onSaveIVASuccess, onSaveError);
			} else {
				Free_tax_transfered.save(free_concept_iva, onSaveIVASuccess, onSaveError);
			}
			
			//saving IEPS in tax_transferred...
			var free_concept_ieps = vm.free_concepts[c_cpt].free_concept_ieps;
			if(free_concept_ieps.amount != null){
				free_concept_ieps.free_concept = vm.free_concepts[c_cpt].free_concept;					
				if (free_concept_iva.id !== null) {
					Free_tax_transfered.update(free_concept_iva, onSaveIEPSSuccess, onSaveError);
				} else {
					Free_tax_transfered.save(free_concept_iva, onSaveIEPSSuccess, onSaveError);
				}
			}*/
			
			//saving free_custom_info
			var free_custom_info = vm.free_concepts[c_cpt].free_custom_info;			
			free_custom_info.free_concept = vm.free_concepts[c_cpt].free_concept;					
			if (free_custom_info.id !== null) {
				Free_customs_info.update(free_custom_info, onSaveCustomInfoSuccess, onSaveError);
			} else {
				Free_customs_info.save(free_custom_info, onSaveCustomInfoSuccess, onSaveError);
			}
			
			//saving free_part_concept
			var free_part_concepts = vm.free_concepts[c_cpt].free_part_concepts;
			var i;
			vm.current_part_concept = 0;
			for(i=0; i < free_part_concepts.length; i++){
				var p_concpt = free_part_concepts[i];
				p_concpt.free_concept = vm.free_concepts[c_cpt].free_concept;
				if (p_concpt.id !== null) {
					Free_part_concept.update(p_concpt, onSavePartConceptSuccess, onSaveError);
				} else {
					Free_part_concept.save(p_concpt, onSavePartConceptSuccess, onSaveError);
				}				
			}
			
			vm.current_free_concept++;
		};
		
		var onSaveSuccess = function (result) {            
			vm.free_cfdi = result;
			
			//saving concepts...
			var i;
			vm.current_free_concept = 0;
			for(i=0; i < vm.free_concepts.length; i++){
				var concpt = vm.free_concepts[i].free_concept;
				if (concpt.id !== null) {
					Free_concept.update(concpt, onSaveConceptSuccess, onSaveError);
				} else {
					Free_concept.save(concpt, onSaveConceptSuccess, onSaveError);
				}				
			}
			
			vm.isSaving = false;
		};
		
		var onSaveFreeReceiverSuccess = function (result) {            
			vm.free_receiver = result;
			vm.free_cfdi.free_receiver = vm.free_receiver;
			
			vm.free_cfdi.version = "3.2";
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
							quantity: 0,
							description: null,
							unit_value: 0,
							predial_number: null,							
							discount: 0,
							amount: 0,
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
				console.log(result);
				vm.free_concepts.push(result);
			}, function() {
				console.log('no result');
			});
		};
    }
})();
