(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_cfdiNewController', Free_cfdiNewController);

    Free_cfdiNewController.$inject = ['$scope', '$stateParams', 'entity', 'Free_cfdi', 'Cfdi_types', 'Cfdi_states', 'free_emitter_entity', 'Payment_method', 'Way_payment', 'C_money', 'Cfdi_type_doc', 'Tax_regime', 'DataUtils', 'free_receiver_entity', 'Free_receiver', 'Type_taxpayer', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code', '$uibModal','Free_concept', 'Free_customs_info', 'Free_part_concept', 'Free_tax_transfered', 'Free_tax_retentions', 'Tax_types', '$timeout', '$state', '$q', 'freecom_taxregistration_entity','Freecom_taxregistration', 'freecom_pfic_entity', 'Freecom_pfic', 'freecom_accreditation_ieps_entity', 'C_tar', 'Freecom_accreditation_ieps'];

    function Free_cfdiNewController ($scope, $stateParams, entity, Free_cfdi, Cfdi_types, Cfdi_states, free_emitter_entity, Payment_method, Way_payment, C_money, Cfdi_type_doc, Tax_regime, DataUtils, free_receiver_entity, Free_receiver, Type_taxpayer, C_country, C_state, C_municipality, C_colony, C_zip_code, $uibModal, Free_concept, Free_customs_info, Free_part_concept, Free_tax_transfered, Free_tax_retentions, Tax_types, $timeout, $state, $q, freecom_taxregistration_entity, Freecom_taxregistration, freecom_pfic_entity, Freecom_pfic, freecom_accreditation_ieps_entity, C_tar, Freecom_accreditation_ieps) {

		var vm = this;

        vm.free_cfdi = entity;
        vm.free_cfdi.free_emitter = free_emitter_entity;
		vm.free_cfdi.tax_regime = vm.free_cfdi.free_emitter.tax_regime;
		vm.free_cfdi.cfdi_states = {id: 1, name: "Creado  ", description: "CFDI creado en el sistema"};
		vm.free_cfdi.c_money = {id: 100, name: "MXN", description: "Peso Mexicano"};
		vm.free_concepts = [];
		vm.current_free_concept = null;
		vm.way_payment = null;
		vm.way_payment_x = 0;
		vm.way_payment_y = 0;
		vm.free_saved_success = false;
        vm.accuracy = null;

		vm.show_iva = (0).toFixed(2);
		vm.calc_iva = (0).toFixed(2);
		vm.ieps = (0).toFixed(2);
		vm.ret_iva = (0).toFixed(2);
		vm.ret_isr = (0).toFixed(2);
		vm.subtotal_discount = (0).toFixed(2);

		vm.disabled_iva_value = -1;

        vm.free_receiver = free_receiver_entity;
        vm.type_taxpayers = Type_taxpayer.query();
		vm.c_countrys = C_country.query({pg:1, filtername:" "});
        vm.c_states = C_state.query({countryId:151, filtername:" "});
        vm.c_municipalitys = null;
		vm.c_colonys = null;

        vm.cfdi_typess = Cfdi_types.query({filtername:" "});
        vm.cfdi_statess = Cfdi_states.query({filtername:" "});
        vm.payment_methods =  Payment_method.query({filtername:" ",filtercode:" "})
        vm.way_payments = Way_payment.query({filtername:" "})
        vm.c_moneys = C_money.query({pg: -1});
        vm.cfdi_type_docs = Cfdi_type_doc.query({filtername:" "});
        vm.tax_regimes = Tax_regime.query({filtername:" "});

		vm.tax_typess = Tax_types.query({filtername: " "});

		$q.all([vm.free_cfdi.$promise, vm.free_cfdi.free_emitter.$promise]).then(function() {
            vm.accuracy = vm.free_cfdi.free_emitter.accuracy;
			if(vm.free_cfdi.free_emitter.path_certificate == null || vm.free_cfdi.free_emitter.path_key == null){
				$uibModal.open({
					templateUrl: 'app/entities/free-cfdi/free-cfdi-not-free-emitter-certificate-dialog.html',
					controller: 'Free_cfdiNotFreeEmitterCertificateDialogController',
					controllerAs: 'vm',
					backdrop: true,
					size: ''
				}).result.then(function(result) {
					$state.go('free-emitter.new');
				}, function() {
					$state.go('free-emitter.new');
				});
			}
        });

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
			vm.free_receiver.business_name = "Contribuyente Público General";
        }

		function onClickForeignResident(){
            vm.free_receiver.rfc = "XEXX010101000";
			vm.free_receiver.type_taxpayer = vm.type_taxpayers[1];
			vm.free_receiver.business_name = "Residente Extranjero";
        }

		function onChangeC_country () {
			var countryId = vm.free_receiver.c_country.id;
            C_state.query({countryId: countryId, filtername:" "}, function(result){
                vm.c_states = result;
            });
        }

        function onChangeC_state () {
            var stateId = vm.free_receiver.c_state.id;
            C_municipality.query({stateId: stateId, filtername:" "}, function(result){
                vm.c_municipalitys = result;
            });
        }

        function onChangeC_municipality () {
            var municipalityId = vm.free_receiver.c_municipality.id;
            C_colony.query({municipalityId: municipalityId, filtername:" "}, function(result){
                vm.c_colonys = result;
            });
        }

        function onChangeC_colony(){
            C_zip_code.get({id : vm.free_receiver.c_colony.c_zip_code.id, filtername:" "}, function(result) {
                vm.free_receiver.c_zip_code = result;
            });
        }

        var onSaveError = function () {
            vm.isSaving = false;
        };

		var onSaveConceptSuccess = function (result) {
			var free_concept = result;

			//saving IVA in free_tax_transferred...
			var free_tax_transfered_iva = vm.free_concepts[vm.current_free_concept].free_concept_iva;
			if(free_tax_transfered_iva.amount > 0){
				free_tax_transfered_iva.free_concept = free_concept;
                Free_tax_transfered.save(free_tax_transfered_iva);
			}

			//saving IEPS in free_tax_transferred...
			var free_tax_transfered_ieps = vm.free_concepts[vm.current_free_concept].free_concept_ieps;
			if(free_tax_transfered_ieps.amount > 0){
				free_tax_transfered_ieps.free_concept = free_concept;
				Free_tax_transfered.save(free_tax_transfered_ieps);
			}

			//saving IVA in free_tax_retentions
			var amount_iva_retentions = 0;
			//calculating free cfdi ret iva...
			if((vm.free_cfdi.free_emitter.rfc.length == 13 && vm.free_receiver.rfc != undefined && vm.free_receiver.rfc.length == 12) && (vm.free_cfdi.cfdi_type_doc != undefined && (vm.free_cfdi.cfdi_type_doc.id == 2 || vm.free_cfdi.cfdi_type_doc.id == 3 || vm.free_cfdi.cfdi_type_doc.id == 5))){
				amount_iva_retentions = 2/3 * free_concept.quantity * free_concept.unit_value;
			}
			if(vm.free_cfdi.cfdi_type_doc != undefined && vm.free_cfdi.cfdi_type_doc.id == 4){
				amount_iva_retentions = 0.04 * free_concept.quantity * free_concept.unit_value * (1 - free_concept.discount/100);
			}

			if(amount_iva_retentions > 0){
				var free_tax_retentions_iva = {
					amount: floorFigure(amount_iva_retentions, vm.accuracy),
					free_concept: free_concept,
					tax_types: vm.tax_typess[0],
					id: null
				};
                Free_tax_retentions.save(free_tax_retentions_iva);
			}

			//saving ISR in free_tax_retentions
			var amount_isr_retentions = 0;
			//calculating free cfdi ret isr...
			if((vm.free_cfdi.free_emitter.rfc.length == 13 && vm.free_receiver.rfc != undefined && vm.free_receiver.rfc.length == 12) || (vm.free_cfdi.cfdi_type_doc != undefined && (vm.free_cfdi.cfdi_type_doc.id == 2 || vm.free_cfdi.cfdi_type_doc.id == 5))){
				amount_isr_retentions = 1/10 * free_concept.quantity * free_concept.unit_value * (1 - free_concept.discount/100);
			}

			if(amount_isr_retentions > 0){
				var free_tax_retentions_isr = {
					amount: floorFigure(amount_isr_retentions, vm.accuracy),
					free_concept: free_concept,
					tax_types: vm.tax_typess[1],
					id: null
				};
                Free_tax_retentions.save(free_tax_retentions_isr);
			}

			//saving free_customs_infos
			var free_customs_infos = vm.free_concepts[vm.current_free_concept].free_customs_infos;
			var i;
			for(i=0; i < free_customs_infos.length; i++){
				var free_customs_info = free_customs_infos[i];
				free_customs_info.free_concept = free_concept;
                Free_customs_info.save(free_customs_info);
			}

			//saving free_part_concept
			var free_part_concepts = vm.free_concepts[vm.current_free_concept].free_part_concepts;
			var j;
			for(j=0; j < free_part_concepts.length; j++){
				var free_part_concept = free_part_concepts[j];
				free_part_concept.free_concept = free_concept;
				free_part_concept.amount = floorFigure(free_part_concept.quantity * free_part_concept.unit_value, vm.accuracy);
				Free_part_concept.save(free_part_concept);
			}
			var next_index = vm.current_free_concept + 1;
			if(next_index < vm.free_concepts.length){
				vm.current_free_concept++;
				saveConcept();
			}
			else{
				resetView();

				vm.free_saved_success = true;
				$timeout(function() {
					vm.free_saved_success = false;
				}, 2000);
			}
		};

		function saveConcept(){
			var free_concept = vm.free_concepts[vm.current_free_concept].free_concept;
			free_concept.free_cfdi = vm.free_cfdi;
			var amount = free_concept.quantity * free_concept.unit_value;
            if(free_concept.discount == 0){
                free_concept.discount = null;
            }
			free_concept.amount = floorFigure(amount, vm.accuracy);
			if (free_concept.id !== null) {
				Free_concept.update(free_concept, onSaveConceptSuccess, onSaveError);
			} else {
				Free_concept.save(free_concept, onSaveConceptSuccess, onSaveError);
			}
		}

		var onSaveSuccess = function (result) {
			vm.free_cfdi = result;

            //Save complements
            if(vm.current_complement != null && vm.current_complement.id != null){
                switch (vm.current_complement.id){
                    case "taxregistration":
                        vm.freecom_taxregistration.version = "3.2";
                        vm.freecom_taxregistration.free_cfdi = vm.free_cfdi;
                        Freecom_taxregistration.save(vm.freecom_taxregistration);
                        break;
                    case "pfic":
                        vm.freecom_pfic.version = "3.2";
                        vm.freecom_pfic.free_cfdi = vm.free_cfdi;
                        Freecom_pfic.save(vm.freecom_pfic);
                        break;
                    case "accreditation_ieps":
                        vm.freecom_accreditation_ieps.version = "3.2";
                        vm.freecom_accreditation_ieps.free_cfdi = vm.free_cfdi;
                        Freecom_accreditation_ieps.save(vm.freecom_accreditation_ieps);
                        break;
                }
            }

            vm.current_free_concept = 0;
			saveConcept();
		};

		var onSaveFreeReceiverSuccess = function (result) {
			vm.free_receiver = result;
			vm.free_cfdi.free_receiver = vm.free_receiver;
            if(vm.free_cfdi.mont_folio_fiscal_orig != null && vm.free_cfdi.mont_folio_fiscal_orig > 0){
                vm.free_cfdi.mont_folio_fiscal_orig = floorFigure(vm.free_cfdi.mont_folio_fiscal_orig, vm.accuracy);
            }
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
			vm.free_receiver.c_country = {id: 151, name: "México", abrev: "MEX"};
			vm.free_receiver.c_state = null;
			vm.free_receiver.c_municipality = null;
			vm.free_receiver.c_colony = null;
			vm.free_receiver.c_zip_code = null;
			vm.free_receiver.street = null;
			vm.free_receiver.reference = null;


			vm.free_cfdi.free_receiver = vm.free_receiver;
			vm.free_cfdi.cfdi_states = {id: 1, name: "Creado  ", description: "CFDI creado en el sistema"};
			vm.free_cfdi.version= null;
			vm.free_cfdi.serial= null;
			vm.free_cfdi.folio= null;
			vm.free_cfdi.date_expedition= null;
			vm.free_cfdi.payment_conditions= null;
			vm.free_cfdi.change_type= (1).toFixed(2);
			vm.free_cfdi.place_expedition= null;
			vm.free_cfdi.account_number= null;
			vm.free_cfdi.folio_fiscal_orig= null;
			vm.free_cfdi.serial_folio_fiscal_orig= null;
			vm.free_cfdi.date_folio_fiscal_orig= null;
			vm.free_cfdi.mont_folio_fiscal_orig= (0).toFixed(vm.accuracy);
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
            vm.path_cfdi= null;
			vm.free_cfdi.c_money = {id: 100, name: "MXN", description: "Peso Mexicano"};

			vm.way_payment = null;
			vm.way_payment_x = 0;
			vm.way_payment_y = 0;

			vm.free_cfdi.subtotal = floorFigure(0, vm.accuracy);
			vm.show_iva = (0).toFixed(2);
			vm.calc_iva = floorFigure(0, vm.accuracy);
			vm.ieps = floorFigure(0, 2);
			vm.ret_iva = floorFigure(0, 2);
			vm.ret_isr = floorFigure(0, 2);
			vm.free_cfdi.discount = floorFigure(0, vm.accuracy);
			vm.subtotal_discount = floorFigure(0, vm.accuracy);
			vm.free_cfdi.total = floorFigure(0, vm.accuracy);

			vm.free_concepts = [];
			vm.current_free_concept = null;

            vm.current_complement = null;
            resetComplementView();
		}

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
							quantity: (0).toFixed(2),
							description: null,
							unit_value: (0).toFixed(vm.accuracy),
							predial_number: null,
							discount: (0).toFixed(2),
							amount: (0).toFixed(vm.accuracy),
							id: null
						};
					},
					disabled_iva_value: function () {
						return vm.disabled_iva_value;
					},
					accuracy: function () {
						return vm.accuracy;
					}
				}
			}).result.then(function(result) {
				vm.free_concepts.push(result);
				vm.updateCFDITotals();
			}, function() {
			});
		};

		vm.removeConcept = function(index){
			vm.free_concepts.splice(index,1);
			vm.updateCFDITotals();
		};

		function floorFigure(figure, decimals){
			if (!decimals) decimals = 2;
			var d = Math.pow(10,decimals);
			return (parseInt(figure*d)/d).toFixed(decimals);
		}

		vm.updateCFDITotals = function(){
			var subtotal = 0;

			var show_iva = 0;
			var show_iva_val16 = 16.00;
			var show_iva_val15 = 15.00;
			var calc_iva = 0;

			var ieps = 0;
			var ret_iva = 0;
			var ret_isr = 0;
			var discount = 0;
			var subtotal_discount = 0;
			var total = 0;

			var disabled_iva_value = -1;

            var total_tax_transfered = 0;
            var total_tax_retention = 0;


			var i;
			for(i=0; i < vm.free_concepts.length; i++){
				//calculating free cfdi subtotal...
				subtotal = subtotal + vm.free_concepts[i].free_concept.quantity * vm.free_concepts[i].free_concept.unit_value;
                total_tax_transfered = parseFloat(total_tax_transfered) + parseFloat(vm.free_concepts[i].free_concept_iva.amount) + parseFloat(vm.free_concepts[i].free_concept_ieps.amount);

				//getting iva to show to user...
				if(vm.free_concepts[i].free_concept_iva.rate ==  show_iva_val16 || vm.free_concepts[i].free_concept_iva.rate == show_iva_val15){
					show_iva = vm.free_concepts[i].free_concept_iva.rate;

					if(vm.free_concepts[i].free_concept_iva.rate ==  show_iva_val16){
						disabled_iva_value = 15;
					}

					if(vm.free_concepts[i].free_concept_iva.rate ==  show_iva_val15){
						disabled_iva_value = 16;
					}
				}

				//calculating free cfdi iva...
				if(vm.free_cfdi.cfdi_type_doc != undefined && vm.free_cfdi.cfdi_type_doc.id == 6){
					calc_iva = 0;
				}
				else if(vm.free_cfdi.cfdi_type_doc != undefined && vm.free_cfdi.cfdi_type_doc.id == 1){
					var iva_calc_val = 0;
					if(vm.free_concepts[i].free_concept_iva.rate == show_iva_val16){
						iva_calc_val = 16/100;
					}
					if(vm.free_concepts[i].free_concept_iva.rate == show_iva_val15){
						iva_calc_val = 15/100;
					}

					calc_iva = calc_iva + iva_calc_val * vm.free_concepts[i].free_concept.amount * (1 + vm.free_concepts[i].free_concept_ieps.rate/100);
				}
				else {
					var iva_calc_val = 0;
					if(vm.free_concepts[i].free_concept_iva.rate == show_iva_val16){
						iva_calc_val = 16/100;
					}
					if(vm.free_concepts[i].free_concept_iva.rate == show_iva_val15){
						iva_calc_val = 15/100;
					}

					calc_iva = calc_iva + vm.free_concepts[i].free_concept.amount * iva_calc_val;
				}

				//calculating free cfdi ieps...
				if(vm.free_cfdi.cfdi_type_doc != undefined && (vm.free_cfdi.cfdi_type_doc.id == 1 || vm.free_cfdi.cfdi_type_doc.id == 8)){
					ieps = ieps + vm.free_concepts[i].free_concept_ieps.rate/100 * vm.free_concepts[i].free_concept.amount;
				}

				//calculationg subtotal - discount
				subtotal_discount = subtotal_discount + vm.free_concepts[i].free_concept.amount * 1;
			}

			//calculating free cfdi ret iva...
			if((vm.free_cfdi.free_emitter.rfc.length == 13 && vm.free_receiver.rfc != undefined && vm.free_receiver.rfc.length == 12) && (vm.free_cfdi.cfdi_type_doc != undefined && (vm.free_cfdi.cfdi_type_doc.id == 2 || vm.free_cfdi.cfdi_type_doc.id == 3 || vm.free_cfdi.cfdi_type_doc.id == 5))){
				ret_iva = 2/3 * subtotal;
			}

			if(vm.free_cfdi.cfdi_type_doc != undefined && vm.free_cfdi.cfdi_type_doc.id == 4){
				ret_iva = 0.04 * subtotal_discount;
			}

			//calculating free cfdi ret isr...
			if((vm.free_cfdi.free_emitter.rfc.length == 13 && vm.free_receiver.rfc != undefined && vm.free_receiver.rfc.length == 12) || (vm.free_cfdi.cfdi_type_doc != undefined && (vm.free_cfdi.cfdi_type_doc.id == 2 || vm.free_cfdi.cfdi_type_doc.id == 5))){
				ret_isr = 1/10 * subtotal_discount;
			}

			//showing all...
			vm.free_cfdi.subtotal = floorFigure(subtotal, vm.accuracy);

			if(show_iva != 0){
				vm.show_iva = show_iva;
			}

			vm.calc_iva = floorFigure(calc_iva, vm.accuracy);

			vm.ieps = floorFigure(ieps, vm.accuracy);

			vm.ret_iva = floorFigure(ret_iva, vm.accuracy);

			vm.ret_isr = floorFigure(ret_isr, vm.accuracy);

			discount = subtotal - subtotal_discount;
			vm.free_cfdi.discount = floorFigure(discount, vm.accuracy);

			vm.subtotal_discount = floorFigure(subtotal_discount, vm.accuracy);

			total = (subtotal_discount + calc_iva) - (ret_iva +  ret_isr) + ieps;
			vm.free_cfdi.total = floorFigure(total, vm.accuracy);

			vm.disabled_iva_value = disabled_iva_value;

			vm.free_cfdi.total_tax_transfered = floorFigure(total_tax_transfered, vm.accuracy);

            total_tax_retention = ret_iva + ret_isr;
			vm.free_cfdi.total_tax_retention = floorFigure(total_tax_retention, vm.accuracy);
		};

		vm.enableWithByPartiality = function(){
			if(vm.way_payment != undefined && vm.way_payment.id == 2){
				return false;
			}
			return true;
		};

		vm.partialityChange = function(){
			vm.free_cfdi.way_payment =  vm.way_payment.name;
		};

		vm.wayPaymentXYChange = function(){
			vm.free_cfdi.way_payment = vm.way_payment.name + " " + vm.way_payment_x + " de " + vm.way_payment_y;
		};

		vm.failWayPaymentXYValidation = function(){
			if(vm.way_payment != undefined && vm.way_payment.id == 2 && (vm.way_payment_x > vm.way_payment_y || vm.way_payment_x == 0 || vm.way_payment_y == 0)){
				return true;
			}
			return false;
		};

		vm.enableAccountNumber = function(){
			if(vm.free_cfdi.payment_method != undefined && vm.free_cfdi.payment_method.id >= 2 && vm.free_cfdi.payment_method.id <= 17){
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

		vm.checkMoneyType = function(){
			if(vm.free_cfdi.c_money != undefined && vm.free_cfdi.c_money.id == 100){
				vm.free_cfdi.change_type = (1).toFixed(2);
			}
		};

        //Complements
        vm.complements = [
            {id: "taxregistration", name:"CFDI Registro Fiscal"},
            {id:"pfic", name: "Persona Física Integrante de Coordinado"},
            {id:"accreditation_ieps", name: "Concepto - Acreditación del IEPS"}
        ];

        vm.current_complement = null;

        vm.onChangeComplemennt = function(){
            if(vm.current_complement != null && vm.current_complement.id != null){
                showComplemnt();
            }
            else{
                resetComplementView();
            }
        };

        function showComplemnt(){
            resetComplementView();

            switch(vm.current_complement.id){
                case "taxregistration":
                    vm.show_taxregistration = true;
                    break;
                case "pfic":
                    vm.show_pfic = true;
                    break;
                case "accreditation_ieps":
                    vm.show_accreditation_ieps = true;
                    break;
            }
        }

        function resetComplementView(){
            vm.show_taxregistration = false;
            vm.show_pfic = false;
            vm.show_accreditation_ieps = false;
        }

        //Tax Registration
        vm.show_taxregistration = false;
        vm.freecom_taxregistration = freecom_taxregistration_entity


        vm.show_pfic = false;
        vm.freecom_pfic = freecom_pfic_entity;

        vm.show_accreditation_ieps = false;
        vm.freecom_accreditation_ieps = freecom_accreditation_ieps_entity;
        vm.c_tars = C_tar.query();

    }
})();
