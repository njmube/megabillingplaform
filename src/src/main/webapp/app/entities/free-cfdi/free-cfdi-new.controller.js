(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_cfdiNewController', Free_cfdiNewController);

    Free_cfdiNewController.$inject = ['$scope', '$stateParams', 'entity', 'Free_cfdi', 'Cfdi_types', 'Cfdi_states', 'free_emitter_entity', 'user', 'Payment_method', 'Way_payment', 'C_money', 'Cfdi_type_doc', 'Tax_regime', 'DataUtils', 'free_receiver_entity', 'Free_receiver', 'Type_taxpayer', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code', '$uibModal','Free_concept', 'Free_customs_info', 'Free_part_concept', 'Free_tax_transfered'];

    function Free_cfdiNewController ($scope, $stateParams, entity, Free_cfdi, Cfdi_types, Cfdi_states, free_emitter_entity, user, Payment_method, Way_payment, C_money, Cfdi_type_doc, Tax_regime, DataUtils, free_receiver_entity, Free_receiver, Type_taxpayer, C_country, C_state, C_municipality, C_colony, C_zip_code, $uibModal, Free_concept, Free_customs_info, Free_part_concept, Free_tax_transfered) {
        
		var vm = this;
		
		vm.account = user;
		
        vm.free_cfdi = entity;		
        vm.free_cfdi.free_emitter = free_emitter_entity;		
		vm.free_concepts = [];
		vm.current_free_concept = null;
		
        vm.free_receiver = free_receiver_entity;
		vm.free_receiver.c_country = {id: 151, name: "México", abrev: "MEX"};
        vm.taxpayer_gp = false;
		vm.type_taxpayers = Type_taxpayer.query();
		vm.c_countrys = C_country.query({pg:1});
        vm.c_states = C_state.query({countryId:-1});
        vm.c_municipalitys = C_municipality.query({stateId:-1});
		vm.c_colonys = null;		
		
        vm.cfdi_typess = Cfdi_types.query({filtername:" "});
        vm.cfdi_statess = Cfdi_states.query({filtername:" "});
        vm.payment_methods = Payment_method.query();
        vm.way_payments = Way_payment.query();
        vm.c_moneys = C_money.query();
        vm.cfdi_type_docs = Cfdi_type_doc.query({filtername:" "});
        vm.tax_regimes = Tax_regime.query();
		
        vm.load = function(id) {
            Free_cfdi.get({id : id}, function(result) {
                vm.free_cfdi = result;
            });
        };
		
		vm.onChangeReceiverRFC = onChangeReceiverRFC;
		vm.onClickTaxpayerGP = onClickTaxpayerGP;
		vm.onClickForeignResident = onClickForeignResident;
		
		vm.onChangeC_country = onChangeC_country;
        vm.onChangeC_state = onChangeC_state;
        vm.onChangeC_municipality = onChangeC_municipality;
        vm.onChangeC_colony = onChangeC_colony;
		
		function onChangeReceiverRFC(){
            if(vm.free_receiver.rfc.length == 12){
                vm.free_receiver.type_taxpayer = vm.type_taxpayers[0];
            }else if(vm.free_receiver.rfc.length == 13){
                vm.free_receiver.type_taxpayer = vm.type_taxpayers[1];
            }
			else{
				vm.free_receiver.type_taxpayer = null;
			}
        }
		
		function onClickTaxpayerGP(){
            vm.free_receiver.rfc = "XAXX010101000";
			vm.free_receiver.type_taxpayer = vm.type_taxpayers[1];
        }
		
		function onClickForeignResident(){
            vm.free_receiver.rfc = "XEXX010101000";
			vm.free_receiver.type_taxpayer = vm.type_taxpayers[1];
        }
		
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
		
		var onSaveConceptSuccess = function (result) {
			var free_concept = result;
			
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
			
			//saving free_customs_info
			var free_customs_info = vm.free_concepts[vm.current_free_concept].free_customs_info;			
			free_customs_info.free_concept = free_concept;					
			if (free_customs_info.id !== null) {
				Free_customs_info.update(free_customs_info);
			} else {
				Free_customs_info.save(free_customs_info);
			}
			
			//saving free_part_concept
			var free_part_concepts = vm.free_concepts[vm.current_free_concept].free_part_concepts;
			var i;
			vm.current_part_concept = 0;
			for(i=0; i < free_part_concepts.length; i++){
				var free_part_concept = free_part_concepts[i];
				free_part_concept.free_concept = free_concept;
				if (free_part_concept.id !== null) {
					Free_part_concept.update(free_part_concept);
				} else {
					Free_part_concept.save(free_part_concept);
				}				
			}
			var next_index = vm.current_free_concept + 1;
			if(next_index < vm.free_concepts.length){
				vm.current_free_concept++;
				saveConcept();
			}
			else{
				resetView();
			}
		};
		
		function resetView(){
			
			vm.free_receiver.rfc= null;
			vm.free_receiver.business_name= null;
			vm.free_receiver.email= null;
			vm.free_receiver.activated= false;
			vm.free_receiver.create_date= null;
			vm.free_receiver.street= null;
			vm.free_receiver.no_ext= null;
			vm.free_receiver.no_int= null;
			vm.free_receiver.reference= null;
			vm.free_receiver.id= null;
			vm.free_receiver.c_country = null;
			vm.free_receiver.c_state = null;
			vm.free_receiver.c_municipality = null;
			vm.free_receiver.c_colony = null;
			vm.free_receiver.c_zip_code = null;
			vm.free_receiver.street = null;
			vm.free_receiver.reference = null;
			
			vm.free_cfdi.free_receiver = vm.free_receiver;			
			vm.free_cfdi.version= null;
			vm.free_cfdi.serial= null;
			vm.free_cfdi.folio= null;
			vm.free_cfdi.date_expedition= null;
			vm.free_cfdi.payment_conditions= null;
			vm.free_cfdi.change_type= null;
			vm.free_cfdi.place_expedition= null;
			vm.free_cfdi.account_number= null;
			vm.free_cfdi.folio_fiscal_orig= null;
			vm.free_cfdi.serial_folio_fiscal_orig= null;
			vm.free_cfdi.date_folio_fiscal_orig= null;
			vm.free_cfdi.mont_folio_fiscal_orig= null;
			vm.free_cfdi.total_tax_retention= null;
			vm.free_cfdi.total_tax_transfered= null;
			vm.free_cfdi.discount= null;
			vm.free_cfdi.discount_reason= null;
			vm.free_cfdi.subtotal= null;
			vm.free_cfdi.total= null;
			vm.free_cfdi.addenda= null;
			vm.free_cfdi.stamp= null;
			vm.free_cfdi.no_certificate= null;
			vm.free_cfdi.certificate= null;
			vm.free_cfdi.id= null;
			vm.free_cfdi.cfdi_type_doc= null;
			vm.free_cfdi.cfdi_types= null;
			vm.free_cfdi.way_payment= null;
			vm.free_cfdi.payment_method= null;
			vm.free_cfdi.c_money= null;
			
			vm.free_concepts = [];
			vm.current_free_concept = null;
		}
		
		function saveConcept(){
			var free_concept = vm.free_concepts[vm.current_free_concept].free_concept;
			free_concept.free_cfdi = vm.free_cfdi;
			if (free_concept.id !== null) {
				Free_concept.update(free_concept, onSaveConceptSuccess, onSaveError);
			} else {
				Free_concept.save(free_concept, onSaveConceptSuccess, onSaveError);
			}
		}
		
		var onSaveSuccess = function (result) {            
			vm.free_cfdi = result;
			vm.current_free_concept = 0;			
			saveConcept();
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
				vm.free_concepts.push(result);
				vm.updateCFDITotals();				
			}, function() {
				console.log('no result');
			});
		};
		
		vm.updateCFDITotals = function(){
			var isr = 0;
			var ieps = 0;
			var discount = 0;
			var subtotal = 0;
			
			var i;
			for(i=0; i < vm.free_concepts.length; i++){
				if(vm.free_cfdi.cfdi_type_doc != undefined && (vm.free_cfdi.cfdi_type_doc.id == 1 || vm.free_cfdi.cfdi_type_doc.id == 8)){					
					ieps = ieps + vm.free_concepts[i].free_concept_ieps.amount/100 * vm.free_concepts[i].free_concept.amount;
				}
				discount = discount + vm.free_concepts[i].free_concept.amount * vm.free_concepts[i].free_concept.discount/100;	
				subtotal = subtotal + vm.free_concepts[i].free_concept.amount * 1;
			}		
			
			if((vm.free_cfdi.free_emitter.rfc.length == 13 && vm.free_receiver.rfc != undefined && vm.free_receiver.rfc.length == 12) || (vm.free_cfdi.cfdi_type_doc != undefined && (vm.free_cfdi.cfdi_type_doc.id == 2 || vm.free_cfdi.cfdi_type_doc.id == 5))){
				isr = 1/10 * subtotal;
			}
			
			vm.free_cfdi.total_tax_retention = isr.toFixed(2);
			vm.free_cfdi.total_tax_transfered = ieps.toFixed(2);
			vm.free_cfdi.discount = discount.toFixed(2);
			vm.free_cfdi.subtotal = subtotal.toFixed(2);
			
			vm.free_cfdi.total = (subtotal + 16/100 + ieps - isr).toFixed(2);
		};
		
		vm.enableSelect = function(){
			if(vm.free_cfdi.way_payment != undefined && vm.free_cfdi.way_payment.id == 2){
				return false;
			}
			return true;
		};
		
		vm.enableAccountNumber = function(){
			if(vm.free_cfdi.payment_method != undefined && vm.free_cfdi.payment_method.id >= 2 && vm.free_cfdi.payment_method.id <= 16 ){
				return false;
			}
			return true;
		};
		
		vm.enableWayPayment = function(){
			if(vm.free_cfdi.payment_method != undefined && vm.free_cfdi.payment_method.id == 17){
				return true;
			}
			return false;
		};
    }
})();
