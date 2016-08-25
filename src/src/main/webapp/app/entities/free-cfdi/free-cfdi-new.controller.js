(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_cfdiNewController', Free_cfdiNewController);

    Free_cfdiNewController.$inject = ['entity', 'Free_cfdi', 'Cfdi_types', 'Cfdi_states', 'free_emitter_entity', 'Payment_method', 'Way_payment', 'C_money', 'Cfdi_type_doc', 'Tax_regime', 'DataUtils', 'free_receiver_entity', 'Free_receiver', 'Type_taxpayer', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code', '$uibModal','Free_concept', 'Free_customs_info', 'Free_part_concept', 'Free_tax_transfered', 'Free_tax_retentions', 'Tax_types', 'Rate_type','$timeout', '$state', '$q','Freecom_taxregistration', 'Freecom_pfic', 'C_tar', 'Freecom_accreditation_ieps', 'Freecom_taxlegends', 'Freecom_legend', 'Freecom_airline', 'Freecom_charge', 'C_well_type', 'C_acquired_title', 'C_features_work_piece','Freecom_apaw', 'Freecom_donees', 'C_school_level', 'Freecom_educational_institutions', 'C_committee_type', 'C_process_type', 'Freecom_ine', 'Freecom_ine_entity', 'Accounting', 'Freecom_kind_payment','C_transit_type','C_type_road','Freecom_foreign_tourist_passenger','C_federal_entity','Freecom_partial_construction_services','C_type_operation','Freecom_foreign_exchange','Freecom_local_taxes','Freecom_retentions_transfered','Freecom_used_vehicle','Freecom_vehicle_customs_information', 'C_type_series','Freecom_destruction_certificate','Freecom_info_customs_destruction','Freecom_fuel_consumption','Freecom_concept_fuel', 'Freecom_determined', 'Freecom_storeroom_paybill', 'Freecom_paybill_concept', 'Freecom_ecc11', 'Freecom_ecc11_concept', 'Freecom_ecc11_transfer', 'Freecom_spei', 'Freecom_payer', 'Freecom_beneficiary', 'Freecom_spei_third'];

    function Free_cfdiNewController (entity, Free_cfdi, Cfdi_types, Cfdi_states, free_emitter_entity, Payment_method, Way_payment, C_money, Cfdi_type_doc, Tax_regime, DataUtils, free_receiver_entity, Free_receiver, Type_taxpayer, C_country, C_state, C_municipality, C_colony, C_zip_code, $uibModal, Free_concept, Free_customs_info, Free_part_concept, Free_tax_transfered, Free_tax_retentions, Tax_types, Rate_type, $timeout, $state, $q, Freecom_taxregistration, Freecom_pfic, C_tar, Freecom_accreditation_ieps, Freecom_taxlegends, Freecom_legend, Freecom_airline, Freecom_charge, C_well_type, C_acquired_title, C_features_work_piece, Freecom_apaw, Freecom_donees, C_school_level, Freecom_educational_institutions, C_committee_type, C_process_type, Freecom_ine, Freecom_ine_entity, Accounting, Freecom_kind_payment, C_transit_type, C_type_road, Freecom_foreign_tourist_passenger, C_federal_entity, Freecom_partial_construction_services, C_type_operation, Freecom_foreign_exchange, Freecom_local_taxes, Freecom_retentions_transfered, Freecom_used_vehicle, Freecom_vehicle_customs_information, C_type_series, Freecom_destruction_certificate, Freecom_info_customs_destruction, Freecom_fuel_consumption, Freecom_concept_fuel, Freecom_determined, Freecom_storeroom_paybill, Freecom_paybill_concept, Freecom_ecc11, Freecom_ecc11_concept, Freecom_ecc11_transfer, Freecom_spei, Freecom_payer, Freecom_beneficiary, Freecom_spei_third) {

		var vm = this;

        vm.free_cfdi = entity;
        vm.free_cfdi.free_emitter = free_emitter_entity;
		vm.free_cfdi.tax_regime = vm.free_cfdi.free_emitter.tax_regime;
		vm.free_cfdi.cfdi_states = {id: 1, name: "Creado  ", description: "CFDI creado en el sistema"};
		vm.free_cfdi.c_money = {id: 100, name: "MXN", description: "Peso Mexicano"};
		vm.free_concepts = [];
		vm.free_concept_ids = [];
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

        vm.free_receiver = free_receiver_entity;
        vm.type_taxpayers = Type_taxpayer.query();
		vm.c_countrys = C_country.query({pg:1, filtername:" "});
        vm.c_states = C_state.query({countryId:151, filtername:" "});
        vm.c_municipalitys = null;
		vm.c_colonys = null;

        vm.cfdi_typess = Cfdi_types.query({filtername:" "});
        vm.cfdi_statess = Cfdi_states.query({filtername:" "});
        vm.payment_methods =  Payment_method.query({filtername:" ",filtercode:" "});
        vm.way_payments = Way_payment.query({filtername:" "});
        vm.c_moneys = C_money.query({pg: -1});
        vm.cfdi_type_docs = Cfdi_type_doc.query({filtername:" "});
        vm.tax_regimes = Tax_regime.query({filtername:" "});

		vm.tax_typess = Tax_types.query({filtername: " "});

        vm.rate_typess = Rate_type.query({filtername: " "});
        vm.valid_rate_typess = [];

		$q.all([vm.free_cfdi.$promise, vm.free_cfdi.free_emitter.$promise]).then(function() {
            vm.accuracy = vm.free_cfdi.free_emitter.accuracy;

            vm.free_cfdi.subtotal = (0).toFixed(vm.accuracy);
            vm.show_iva = (0).toFixed(vm.accuracy);
            vm.calc_iva = (0).toFixed(vm.accuracy);
            vm.ieps = (0).toFixed(vm.accuracy);
            vm.ret_iva = (0).toFixed(vm.accuracy);
            vm.ret_isr = (0).toFixed(vm.accuracy);
            vm.free_cfdi.discount = (0).toFixed(vm.accuracy);
            vm.subtotal_discount = (0).toFixed(vm.accuracy);
            vm.free_cfdi.total = (0).toFixed(vm.accuracy);

			if(vm.free_cfdi.free_emitter.valid_certificate == null || vm.free_cfdi.free_emitter.valid_certificate == false){
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
            if(vm.free_receiver.rfc != undefined && vm.free_receiver.rfc.length == 12){
                vm.free_receiver.type_taxpayer = vm.type_taxpayers[0];
            }else if(vm.free_receiver.rfc != undefined && vm.free_receiver.rfc.length == 13){
                vm.free_receiver.type_taxpayer = vm.type_taxpayers[1];
            }
			else{
				vm.free_receiver.type_taxpayer = null;
			}

            document.getElementById('free_receiver_special_type_taxpayer').checked = false;
            document.getElementById('free_receiver_special_type_foreign').checked = false;
            vm.free_receiver.business_name = null;
        }

		function onClickTaxpayerGP(){
            vm.free_receiver.rfc = 'XAXX010101000';
			vm.free_receiver.type_taxpayer = vm.type_taxpayers[1];
			vm.free_receiver.business_name = 'Contribuyente Público General';
        }

		function onClickForeignResident(){
            vm.free_receiver.rfc = 'XEXX010101000';
			vm.free_receiver.type_taxpayer = vm.type_taxpayers[1];
			vm.free_receiver.business_name = 'Residente Extranjero';
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
                    rate_typess: function () {
                        return vm.valid_rate_typess;
                    },
                    free_concept_ids: function () {
                        return vm.free_concept_ids;
                    },
                    accuracy: function () {
                        return vm.accuracy;
                    },
                    disable_ieps: function () {
                        var disable = true;
                        if(vm.free_cfdi.cfdi_type_doc != null && (vm.free_cfdi.cfdi_type_doc.id == 1 || vm.free_cfdi.cfdi_type_doc.id == 8)){
                            disable = false;
                        }
                        return disable;
                    }
                }
            }).result.then(function(result) {
                vm.free_concepts.push(result);
                vm.free_concept_ids.push(result.free_concept.no_identification);
                vm.updateCFDITotals();
            }, function() {
            });
        };

        vm.removeConcept = function(index){
            vm.free_concepts.splice(index,1);
            vm.free_concept_ids.splice(index,1);
            vm.updateCFDITotals();
        };

        function floorFigure(figure, decimals){
            if (!decimals) decimals = 2;
            var d = Math.pow(10,decimals);
            return (parseInt(figure*d)/d).toFixed(decimals);
        }

        function updateValidRateTypes(except){
            vm.valid_rate_typess = [];
            var i;
            for(i=0; i < vm.rate_typess.length; i++){
                if(vm.rate_typess[i].value != except){
                    vm.valid_rate_typess.push(vm.rate_typess[i])
                }
            }
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
                        updateValidRateTypes(15);
                    }

                    if(vm.free_concepts[i].free_concept_iva.rate ==  show_iva_val15){
                        updateValidRateTypes(15);
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
                subtotal_discount = parseFloat(subtotal_discount) + parseFloat(vm.free_concepts[i].free_concept.amount);
            }

            //calculating free cfdi ret iva and ret isr...
            if(vm.free_cfdi.free_emitter.rfc != undefined && vm.free_cfdi.free_emitter.rfc.length == 13 && vm.free_receiver.rfc != undefined && vm.free_receiver.rfc.length == 12 && vm.free_cfdi.cfdi_type_doc != undefined && (vm.free_cfdi.cfdi_type_doc.id == 2 || vm.free_cfdi.cfdi_type_doc.id == 5)){
                ret_iva = 2/3 * calc_iva;
                ret_isr = 1/10 * subtotal_discount;
            }
            else if(vm.free_cfdi.free_emitter.rfc != undefined && vm.free_cfdi.free_emitter.rfc.length == 13 && vm.free_receiver.rfc != undefined && vm.free_receiver.rfc.length == 12 && vm.free_cfdi.cfdi_type_doc != undefined && vm.free_cfdi.cfdi_type_doc.id == 3){
                ret_iva = 2/3 * calc_iva;
            }
            else if(vm.free_cfdi.cfdi_type_doc != undefined && vm.free_cfdi.cfdi_type_doc.id == 4){
                ret_iva = 0.04 * subtotal_discount;
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
			if((vm.free_cfdi.free_emitter.rfc != undefined && vm.free_cfdi.free_emitter.rfc.length == 13 && vm.free_receiver.rfc != undefined && vm.free_receiver.rfc.length == 12) && (vm.free_cfdi.cfdi_type_doc != undefined && (vm.free_cfdi.cfdi_type_doc.id == 2 || vm.free_cfdi.cfdi_type_doc.id == 3 || vm.free_cfdi.cfdi_type_doc.id == 5))){
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
			if((vm.free_cfdi.free_emitter.rfc != undefined && vm.free_cfdi.free_emitter.rfc.length == 13 && vm.free_receiver.rfc != undefined && vm.free_receiver.rfc.length == 12) || (vm.free_cfdi.cfdi_type_doc != undefined && (vm.free_cfdi.cfdi_type_doc.id == 2 || vm.free_cfdi.cfdi_type_doc.id == 5))){
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

        var onTaxLegendsSaveSucccess = function (result) {
            var freecom_taxlegends = result;

            var i;
            for(i=0; i < vm.legends.length; i++){
                var legend = vm.legends[i];
                legend.freecom_taxlegends = freecom_taxlegends;
                Freecom_legend.save(legend);
            }

            saveConcept();
        };

        var onAirLineSaveSucccess = function (result) {
            var freecom_airline_saved = result;

            var i;
            for(i=0; i < vm.charges.length; i++){
                var charge = vm.charges[i];
                charge.freecom_airline = freecom_airline_saved;
                Freecom_charge.save(charge);
            }

            saveConcept();
        };


        vm.freecom_ine_entity_index = 0;
        vm.freecom_ine_saved = null;

        var onIneSaveSucsess = function(result){
            vm.freecom_ine_saved = result;
            vm.freecom_ine_entity_index = -1;
            saveIneEntity();
        };

        function saveIneEntity(){
            vm.freecom_ine_entity_index++;
            if(vm.freecom_ine_entity_index < vm.freecom_ine_entities.length){
                var freecom_ine_entity = vm.freecom_ine_entities[vm.freecom_ine_entity_index].freecom_ine_entity;
                freecom_ine_entity.freecom_ine = vm.freecom_ine_saved;
                Freecom_ine_entity.save(freecom_ine_entity, onIneEntitySaveSuccess, onSaveError);
            }
            else{
                vm.current_free_concept = 0;
                saveConcept();
            }
        }

        var onIneEntitySaveSuccess = function(result){
            var freecom_ine_entity_saved = result;

            var accounting;
            var accountings = vm.freecom_ine_entities[vm.freecom_ine_entity_index].accountings;

            var i;
            for(i=0; i < accountings.length; i++){
                accounting = accountings[i];
                accounting.freecom_ine_entity = freecom_ine_entity_saved;
                Accounting.save(accounting);
            }

            saveIneEntity();
        };

        var onLocalTaxesSaveSucccess = function(result){
            var freecom_local_taxes_saved = result;

            var i;
            for(i=0; i < vm.freecom_ret_transfs.length; i++){
                var freecom_ret_transf = vm.freecom_ret_transfs[i];
                freecom_ret_transf.freecom_local_taxes = freecom_local_taxes_saved;
                Freecom_retentions_transfered.save(freecom_ret_transf);
            }

            saveConcept();
        };

        var onUsedVehicleSaveSucccess = function(result){
            var freecom_used_vehicle_saved = result;
            vm.freecom_vehicle_customs_information.freecom_used_vehicle = freecom_used_vehicle_saved;
            Freecom_vehicle_customs_information.save(vm.freecom_vehicle_customs_information);

            saveConcept();
        };

        var onDestructionCertificateSaveSucccess = function(result){
            var freecom_destruction_certificate_saved = result;
            vm.freecom_info_customs_destruction.freecom_destruction_certificate = freecom_destruction_certificate_saved;
            Freecom_info_customs_destruction.save(vm.freecom_info_customs_destruction);

            saveConcept();
        };

        vm.freecom_fuel_consumption_saved = null;
        vm.freecom_concept_fuel_index = null;

        var onFuelConsumptionSaveSucccess = function(result){
            vm.freecom_fuel_consumption_saved = result;
            vm.freecom_concept_fuel_index = -1;
            saveFuelConcept();
        };

        function saveFuelConcept(){
            vm.freecom_concept_fuel_index++;
            if(vm.freecom_concept_fuel_index < vm.freecom_concept_fuels.length){
                var freecom_concept_fuel = vm.freecom_concept_fuels[vm.freecom_concept_fuel_index].concept_fuel;
                freecom_concept_fuel.freecom_fuel_consumption = vm.freecom_fuel_consumption_saved;
                Freecom_concept_fuel.save(freecom_concept_fuel, onConceptFuelSaveSuccess, onSaveError);
            }
            else{
                saveConcept();
            }
        }

        var onConceptFuelSaveSuccess = function(result){
            var freecom_concept_fuel_saved = result;
            var determinates = vm.freecom_concept_fuels[vm.freecom_concept_fuel_index].determinates;
            var i;
            for(i=0; i < determinates.length; i++){
                var determinate = determinates[i];
                determinate.freecom_concept_fuel = freecom_concept_fuel_saved;
                Freecom_determined.save(determinate);
            }

            saveFuelConcept();
        };

        var onStoreroomPaybillSaveSucccess = function(result){
            var freecom_storeroom_paybill_saved = result;
            var i;
            for(i=0; i < vm.freecom_paybill_concepts.length; i++){
                var freecom_paybill_concept = vm.freecom_paybill_concepts[i];
                freecom_paybill_concept.freecom_storeroom_paybill = freecom_storeroom_paybill_saved;
                Freecom_paybill_concept.save(freecom_paybill_concept);
            }

            saveConcept();
        };

        vm.freecom_ecc11_saved = null;
        vm.freecom_ecc11_concept_index = null;

        var onEcc11SaveSucccess = function(result){
            vm.freecom_ecc11_saved = result;
            vm.freecom_ecc11_concept_index = -1;
            saveEcc11Concept();
        };

        function saveEcc11Concept(){
            vm.freecom_ecc11_concept_index++;
            if(vm.freecom_ecc11_concept_index < vm.freecom_ecc11_concepts.length){
                var freecom_ecc11_concept = vm.freecom_ecc11_concepts[vm.freecom_ecc11_concept_index].concept;
                freecom_ecc11_concept.freecom_ecc_11 = vm.freecom_ecc11_saved;
                Freecom_ecc11_concept.save(freecom_ecc11_concept, onEcc11ConceptSaveSuccess, onSaveError);
            }
            else{
                saveConcept();
            }
        }

        var onEcc11ConceptSaveSuccess = function(result){
            var freecom_ecc11_concept_saved = result;
            var transfers = vm.freecom_ecc11_concepts[vm.freecom_ecc11_concept_index].transfers;
            var i;
            for(i=0; i < transfers.length; i++){
                var freecom_ecc11_transfer = transfers[i];
                freecom_ecc11_transfer.freecom_ecc_11_concept = freecom_ecc11_concept_saved;
                Freecom_ecc11_transfer.save(freecom_ecc11_transfer);
            }

            saveEcc11Concept();
        };

        vm.freecom_spei_saved = null;
        vm.freecom_payer_saved = null;
        vm.freecom_beneficiary_saved = null;
        vm.freecom_spei_third_index = null;
        var onSpeiSaveSucccess = function(result){
            vm.freecom_spei_saved = result;
            vm.freecom_spei_third_index = 0;
            savePayer();
        };

        function savePayer(){
            var payer = vm.freecom_spei_thirds[vm.freecom_spei_third_index].payer;
            Freecom_payer.save(payer, onFreecomPayerSaveSuccess, onSaveError);
        }

        var onFreecomPayerSaveSuccess = function(result){
            vm.freecom_payer_saved = result;
            var beneficiary = vm.freecom_spei_thirds[vm.freecom_spei_third_index].beneficiary;
            Freecom_beneficiary.save(beneficiary, onFreecomBeneficiarySaveSuccess, onSaveError);
        };

        var onFreecomBeneficiarySaveSuccess = function(result){
            vm.freecom_beneficiary_saved = result;
            var spei_third = vm.freecom_spei_thirds[vm.freecom_spei_third_index].spei_third;
            spei_third.freecom_spei = vm.freecom_spei_saved;
            spei_third.freecom_payer = vm.freecom_payer_saved;
            spei_third.freecom_beneficiary = vm.freecom_beneficiary_saved;
            Freecom_spei_third.save(spei_third, onFreecomSpeiThirdSaveSuccess, onSaveError);
        };

        var onFreecomSpeiThirdSaveSuccess = function(result){
            vm.freecom_spei_third_index++;
            if(vm.freecom_spei_third_index < vm.freecom_spei_thirds.length){
                savePayer();
            }
            else{
                saveConcept();
            }
        };

		var onSaveSuccess = function (result) {
			vm.free_cfdi = result;
            vm.current_free_concept = 0;

            //Save complements
            if(vm.current_complement != null && vm.current_complement.id != null){
                switch (vm.current_complement.id){
                    case "taxregistration":
                        vm.freecom_taxregistration.version = "1.0";
                        vm.freecom_taxregistration.free_cfdi = vm.free_cfdi;
                        Freecom_taxregistration.save(vm.freecom_taxregistration);
                        saveConcept();
                        break;
                    case "pfic":
                        vm.freecom_pfic.version = "1.0";
                        vm.freecom_pfic.free_cfdi = vm.free_cfdi;
                        Freecom_pfic.save(vm.freecom_pfic);
                        saveConcept();
                        break;
                    case "accreditation_ieps":
                        vm.freecom_accreditation_ieps.version = "1.0";
                        vm.freecom_accreditation_ieps.free_cfdi = vm.free_cfdi;
                        Freecom_accreditation_ieps.save(vm.freecom_accreditation_ieps);
                        saveConcept();
                        break;
                    case "taxlegends":
                        vm.freecom_taxlegends.version = "1.0";
                        vm.freecom_taxlegends.free_cfdi = vm.free_cfdi;
                        Freecom_taxlegends.save(vm.freecom_taxlegends, onTaxLegendsSaveSucccess, onSaveError);
                        break;
                    case "airline":
                        vm.freecom_airline.version = "1.0";
                        vm.freecom_airline.free_cfdi = vm.free_cfdi;
                        Freecom_airline.save(vm.freecom_airline, onAirLineSaveSucccess, onSaveError);
                        break;
                    case "apaw":
                        vm.freecom_apaw.version = "1.0";
                        vm.freecom_apaw.free_cfdi = vm.free_cfdi;
                        Freecom_apaw.save(vm.freecom_apaw);
                        saveConcept();
                        break;
                    case "donees":
                        vm.freecom_donees.version = "1.0";
                        vm.freecom_donees.free_cfdi = vm.free_cfdi;
                        Freecom_donees.save(vm.freecom_donees);
                        saveConcept();
                        break;
                    case "educational_institutions":
                        vm.freecom_educational_institutions.version = "1.0";
                        vm.freecom_educational_institutions.free_cfdi = vm.free_cfdi;
                        Freecom_educational_institutions.save(vm.freecom_educational_institutions);
                        saveConcept();
                        break;
                    case "ine":
                        vm.freecom_ine.version = "1.0";
                        vm.freecom_ine.free_cfdi = vm.free_cfdi;
                        Freecom_ine.save(vm.freecom_ine, onIneSaveSucsess, onSaveError);
                        break;
                    case "kind_payment":
                        vm.freecom_kind_payment.version = "1.0";
                        vm.freecom_kind_payment.free_cfdi = vm.free_cfdi;
                        Freecom_kind_payment.save(vm.freecom_kind_payment);
                        saveConcept();
                        break;
                    case "foreign_tourist_passenger":
                        vm.freecom_foreign_tourist_passenger.version = "1.0";
                        vm.freecom_foreign_tourist_passenger.free_cfdi = vm.free_cfdi;
                        Freecom_foreign_tourist_passenger.save(vm.freecom_foreign_tourist_passenger);
                        saveConcept();
                        break;
                    case "partial_construction_services":
                        vm.freecom_partial_construction_services.version = "1.0";
                        vm.freecom_partial_construction_services.free_cfdi = vm.free_cfdi;
                        Freecom_partial_construction_services.save(vm.freecom_partial_construction_services);
                        saveConcept();
                        break;
                    case "foreign_exchange":
                        vm.freecom_foreign_exchange.version = "1.0";
                        vm.freecom_foreign_exchange.free_cfdi = vm.free_cfdi;
                        Freecom_foreign_exchange.save(vm.freecom_foreign_exchange);
                        saveConcept();
                        break;
                    case "local_taxes":
                        vm.freecom_local_taxes.version = "1.0";
                        vm.freecom_local_taxes.free_cfdi = vm.free_cfdi;
                        Freecom_local_taxes.save(vm.freecom_local_taxes, onLocalTaxesSaveSucccess, onSaveError);
                        break;
                    case "used_vehicle":
                        vm.freecom_used_vehicle.version = "1.0";
                        vm.freecom_used_vehicle.free_cfdi = vm.free_cfdi;
                        Freecom_used_vehicle.save(vm.freecom_used_vehicle, onUsedVehicleSaveSucccess, onSaveError);
                        break;
                    case "destruction_certificate":
                        vm.freecom_destruction_certificate.version = "1.0";
                        vm.freecom_destruction_certificate.free_cfdi = vm.free_cfdi;
                        Freecom_destruction_certificate.save(vm.freecom_destruction_certificate, onDestructionCertificateSaveSucccess, onSaveError);
                        break;
                    case "fuel_consumption":
                        vm.freecom_fuel_consumption.version = "1.0";
                        vm.freecom_fuel_consumption.free_cfdi = vm.free_cfdi;
                        Freecom_fuel_consumption.save(vm.freecom_fuel_consumption, onFuelConsumptionSaveSucccess, onSaveError);
                        break;
                    case "storeroom_paybill":
                        vm.freecom_storeroom_paybill.version = "1.0";
                        vm.freecom_storeroom_paybill.free_cfdi = vm.free_cfdi;
                        Freecom_storeroom_paybill.save(vm.freecom_storeroom_paybill, onStoreroomPaybillSaveSucccess, onSaveError);
                        break;
                    case "ecc11":
                        vm.freecom_ecc11.version = "1.0";
                        vm.freecom_ecc11.free_cfdi = vm.free_cfdi;
                        Freecom_ecc11.save(vm.freecom_ecc11, onEcc11SaveSucccess, onSaveError);
                        break;
                    case "spei":
                        vm.freecom_spei.free_cfdi = vm.free_cfdi;
                        Freecom_spei.save(vm.freecom_spei, onSpeiSaveSucccess, onSaveError);
                        break;
                }
            }
            else {
                saveConcept();
            }
		};

		var onSaveFreeReceiverSuccess = function (result) {
			vm.free_receiver = result;
            vm.free_cfdi.free_receiver = vm.free_receiver;
            vm.free_cfdi.freecom_tfd = { version: "1.0", uuid: null, stamp_date: null, stamp_cfd: null, sat_number_certificate: null, stamp_sat: null, id: null};

            if(vm.free_cfdi.mont_folio_fiscal_orig != null && vm.free_cfdi.mont_folio_fiscal_orig > 0){
                vm.free_cfdi.mont_folio_fiscal_orig = floorFigure(vm.free_cfdi.mont_folio_fiscal_orig, vm.accuracy);
            }

            var free_concept;
            var free_concepts = [];

            var free_tax_transfereds = [];
            var free_tax_transfered_iva;
            var free_tax_transfered_ieps;

            var free_tax_retentions = [];
            var amount_iva_retentions;
            var free_tax_retentions_iva;
            var amount_isr_retentions;
            var free_tax_retentions_isr;

            var i;
            for(i=0; i < vm.free_concepts.length; i++){
                free_concept = vm.free_concepts[i].free_concept;
                free_concepts.push(free_concept);

                //getting IVA for free_tax_transferred...
                free_tax_transfered_iva = vm.free_concepts[i].free_concept_iva;
                if(free_tax_transfered_iva.amount > 0){
                    free_tax_transfereds.push(free_tax_transfered_iva);
                }

                //getting IEPS for free_tax_transferred...
                free_tax_transfered_ieps = vm.free_concepts[i].free_concept_ieps;
                if(free_tax_transfered_ieps.amount > 0){
                    free_tax_transfereds.push(free_tax_transfered_ieps);
                }

                //getting IVA for free_tax_retentions
                amount_iva_retentions = 0;
                //calculating free cfdi ret iva...
                if((vm.free_cfdi.free_emitter.rfc != undefined && vm.free_cfdi.free_emitter.rfc.length == 13 && vm.free_receiver.rfc != undefined && vm.free_receiver.rfc.length == 12) && (vm.free_cfdi.cfdi_type_doc != undefined && (vm.free_cfdi.cfdi_type_doc.id == 2 || vm.free_cfdi.cfdi_type_doc.id == 3 || vm.free_cfdi.cfdi_type_doc.id == 5))){
                    amount_iva_retentions = 2/3 * free_concept.quantity * free_concept.unit_value;
                }
                if(vm.free_cfdi.cfdi_type_doc != undefined && vm.free_cfdi.cfdi_type_doc.id == 4){
                    amount_iva_retentions = 0.04 * free_concept.quantity * free_concept.unit_value * (1 - free_concept.discount/100);
                }

                if(amount_iva_retentions > 0){
                    free_tax_retentions_iva = {
                        amount: floorFigure(amount_iva_retentions, vm.accuracy),
                        id: null
                    };
                    free_tax_retentions.push(free_tax_retentions_iva);
                }

                //getting ISR for free_tax_retentions
                amount_isr_retentions = 0;
                //calculating free cfdi ret isr...
                if((vm.free_cfdi.free_emitter.rfc != undefined && vm.free_cfdi.free_emitter.rfc.length == 13 && vm.free_receiver.rfc != undefined && vm.free_receiver.rfc.length == 12) || (vm.free_cfdi.cfdi_type_doc != undefined && (vm.free_cfdi.cfdi_type_doc.id == 2 || vm.free_cfdi.cfdi_type_doc.id == 5))){
                    amount_isr_retentions = 1/10 * free_concept.quantity * free_concept.unit_value * (1 - free_concept.discount/100);
                }

                if(amount_isr_retentions > 0){
                    free_tax_retentions_isr = {
                        amount: floorFigure(amount_isr_retentions, vm.accuracy),
                        id: null
                    };
                    free_tax_retentions.push(free_tax_retentions_isr);
                }
            }

            var free_cfdi_dto = {
                freeCFDI: vm.free_cfdi,
                concepts: free_concepts,
                freeTaxTransfereds: free_tax_transfereds,
                freeTaxRetentions: free_tax_retentions
            };

            if(vm.current_complement != null) {

                switch (vm.current_complement.id) {
                    case "taxregistration":
                        free_cfdi_dto.freecom_taxregistration = vm.freecom_taxregistration;
                        break;
                    case "pfic":
                        free_cfdi_dto.freecom_pfic = vm.freecom_pfic;
                        break;
                    case "accreditation_ieps":
                        free_cfdi_dto.freecom_accreditation_ieps = vm.freecom_accreditation_ieps;
                        break;
                    case "taxlegends":
                        free_cfdi_dto.freecom_taxlegends = vm.freecom_taxlegends;
                        break;
                    case "airline":
                        free_cfdi_dto.freecom_airline = vm.freecom_airline;
                        break;
                    case "apaw":
                        free_cfdi_dto.freecom_apaw = vm.freecom_apaw;
                        break;
                    case "donees":
                        free_cfdi_dto.freecom_donees = vm.freecom_donees;
                        break;
                    case "educational_institutions":
                        free_cfdi_dto.freecom_educational_institutions = vm.freecom_educational_institution;
                        break;
                    case "ine":
                        free_cfdi_dto.freecom_ine = vm.freecom_ine;
                        break;
                    case "kind_payment":
                        free_cfdi_dto.freecom_kind = vm.freecom_kind;
                        break;
                    case "foreign_tourist_passenger":
                        free_cfdi_dto.freecom_foreign_tourist_passenger = vm.freecom_foreign_tourist_passenger;
                        break;
                    case "partial_construction_services":
                        free_cfdi_dto.freecom_partial_construction_services = vm.freecom_partial_construction_services;
                        break;
                    case "foreign_exchange":
                        free_cfdi_dto.freecom_foreign_exchange = vm.freecom_foreign_exchange;
                        break;
                    case "local_taxes":
                        free_cfdi_dto.freecom_local_taxes = vm.freecom_local_taxes;
                        break;
                    case "used_vehicle":
                        free_cfdi_dto.freecom_used_vehicle = vm.freecom_used_vehicle;
                        break;
                    case "destruction_certificate":
                        free_cfdi_dto.freecom_destruction_certificate = vm.freecom_destruction_certificate;
                        break;
                    case "fuel_consumption":
                        free_cfdi_dto.freecom_fuel_consumption = vm.freecom_fuel_consumption;
                        break;
                    case "storeroom_paybill":
                        free_cfdi_dto.freecom_storeroom_paybill = vm.freecom_storeroom_paybill;
                        break;
                    case "ecc11":
                        free_cfdi_dto.freecom_ecc11 = vm.freecom_ecc11;
                        break;

                }
            }

			if (vm.free_cfdi.id !== null) {
                Free_cfdi.update(free_cfdi_dto, onSaveSuccess, onSaveError);
            } else {
                Free_cfdi.save(free_cfdi_dto, onSaveSuccess, onSaveError);
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
            vm.free_receiver.type_taxpayer = null;
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

            document.getElementById('free_receiver_special_type_taxpayer').checked = false;
            document.getElementById('free_receiver_special_type_foreign').checked = false;

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
            vm.free_concept_ids = [];
			vm.current_free_concept = null;

            //Reset complements
            vm.current_complement = null;
            resetComplements();
        }

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.date_folio_fiscal_orig = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };

		vm.openFile = DataUtils.openFile;

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

        vm.onChangeCFDITypeDoc = function(){
            if(vm.free_cfdi.cfdi_type_doc != undefined && vm.free_cfdi.cfdi_type_doc.id >= 1 && vm.free_cfdi.cfdi_type_doc.id <= 7){
                vm.free_cfdi.cfdi_types = vm.cfdi_typess[0];

                if(vm.current_complement != null && vm.current_complement.id == "ecc11"){
                    vm.show_ecc11 = true;
                    vm.show_ecc11_invalid = false;
                }
            }
            else if(vm.free_cfdi.cfdi_type_doc != undefined && (vm.free_cfdi.cfdi_type_doc.id == 8 || vm.free_cfdi.cfdi_type_doc.id == 9)){
                vm.free_cfdi.cfdi_types = vm.cfdi_typess[1];

                if(vm.current_complement != null && vm.current_complement.id == "ecc11"){
                    vm.show_ecc11 = false;
                    vm.show_ecc11_invalid = true;
                }
            }
            else if(vm.free_cfdi.cfdi_type_doc != undefined && vm.free_cfdi.cfdi_type_doc.id == 10 ){
                vm.free_cfdi.cfdi_types = vm.cfdi_typess[2];

                if(vm.current_complement != null && vm.current_complement.id == "ecc11"){
                    vm.show_ecc11 = false;
                    vm.show_ecc11_invalid = true;
                }
            }

            if(vm.free_cfdi.cfdi_type_doc != undefined && vm.free_cfdi.cfdi_type_doc.id == 6 ){
                vm.valid_rate_typess = [];
                vm.valid_rate_typess.push(vm.rate_typess[1]);
            }
            else {
                vm.valid_rate_typess = vm.rate_typess;
            }

            vm.updateCFDITotals();

        };

        //Complements
        vm.complements = [
            {id: "taxregistration", name:"CFDI Registro Fiscal"},
            {id:"pfic", name: "Persona Física Integrante de Coordinado"},
            {id:"accreditation_ieps", name: "Concepto - Acreditación del IEPS"},
            {id:"taxlegends", name: "Leyendas Fiscales"},
            {id:"airline", name: "Aerolíneas"},
            {id:"apaw", name: "Obras de Artes Plásticas y Antigüedades"},
            {id:"donees", name: "Donatarias"},
            {id:"educational_institutions", name: "Concepto - Instituciones Educativas Privadas"},
            {id:"ine", name: "INE"},
            {id:"kind_payment", name: "Pago en Especie"},
            {id:"foreign_tourist_passenger", name: "Turista Pasajero Extranjero"},
            {id:"partial_construction_services", name: "Servicios Parciales de Construcción"},
            {id:"foreign_exchange", name: "Divisas"},
            {id:"local_taxes", name: "Otros Derechos e Impuestos"},
            {id:"used_vehicle", name: "Vehículo Usado"},
            {id:"destruction_certificate", name: "Certificado de Destrucción"},
            {id:"fuel_consumption", name: "Consumo de Combustibles"},
            {id:"storeroom_paybill", name: "Vales de Despensa"},
            {id:"ecc11", name: "Estado de Cuenta de Combustibles de Monederos Electrónicos Autorizados"},
            {id:"spei", name: "SPEI Tercero a Tercero"}
        ];

        vm.current_complement = null;

        vm.onChangeComplemennt = function(){
            resetComplements();
            if(vm.current_complement != null && vm.current_complement.id != null){
                showComplemnt();
            }
        };

        function showComplemnt(){
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
                case "taxlegends":
                    vm.show_taxlegends = true;
                    break;
                case "airline":
                    vm.show_airline = true;
                    break;
                case "apaw":
                    vm.show_apaw = true;
                    break;
                case "donees":
                    vm.show_donees = true;
                    break;
                case "educational_institutions":
                    vm.show_educational_institutions = true;
                    break;
                case "ine":
                    vm.show_ine = true;
                    break;
                case "kind_payment":
                    vm.show_kind_payment = true;
                    break;
                case "foreign_tourist_passenger":
                    vm.show_foreign_tourist_passenger = true;
                    break;
                case "partial_construction_services":
                    vm.show_partial_construction_services = true;
                    break;
                case "foreign_exchange":
                    vm.show_foreign_exchange = true;
                    break;
                case "local_taxes":
                    vm.show_local_taxes = true;
                    break;
                case "used_vehicle":
                    vm.show_used_vehicle = true;
                    break;
                case "destruction_certificate":
                    vm.show_destruction_certificate = true;
                    break;
                case "fuel_consumption":
                    vm.show_fuel_consumption = true;
                    break;
                case "storeroom_paybill":
                    vm.show_storeroom_paybill = true;
                    break;
                case "ecc11":
                    if(vm.free_cfdi.cfdi_types != null && vm.free_cfdi.cfdi_types.id == 1) {
                        vm.show_ecc11 = true;
                        vm.show_ecc11_invalid = false;
                    }
                    else{
                        vm.show_ecc11 = false;
                        vm.show_ecc11_invalid = true;
                    }
                    break;
                case "spei":
                    vm.show_spei = true;
                    break;
            }
        }

        function resetComplements(){

            vm.show_taxregistration = false;
            vm.freecom_taxregistration = { version: null, folio: null, id: null };

            vm.show_pfic = false;
            vm.freecom_pfic = { version: null, key_vehicle: null, license_plate: null, rfcpf: null, id: null };

            vm.show_accreditation_ieps = false;
            vm.freecom_accreditation_ieps = { version: null, id: null };

            vm.show_taxlegends = false;
            vm.freecom_taxlegends = { version: null, id: null };
            vm.legends = [];

            vm.show_airline = false;
            vm.freecom_airline = {version: null, tua: null, total_charge: null, id: null};
            vm.charges = [];

            vm.show_apaw = false;
            vm.freecom_apaw = { version: null, others_well_type: null, others_acquired_title: null, subtotal: null, iva: null, date_acquisition: null, c_well_type: null, c_acquired_title: null, c_features_work_piece: null, id: null };

            vm.show_donees = false;
            vm.freecom_donees = { version: null, no_authorization: null, date_authorization: null, legend: null,  id: null };

            vm.show_educational_institutions = false;
            vm.freecom_educational_institutions = { version: null, name_student: null,  curp: null, autrvoe: null, rfcpayment: null, c_school_level: null, id: null };

            vm.show_ine = false;
            vm.freecom_ine = { version: null, ident: null, c_committee_type: null, c_process_type: null, id: null };
            vm.freecom_ine_entities = [];
            vm.c_committee_type_required = false;
            vm.c_committee_type_disabled = false;
            vm.ident_disabled = false;
            vm.add_button_ine_entity_disabled = true;
            vm.ine_entity_scope_required = false;
            vm.ine_entity_scope_disabled = false;

            vm.show_kind_payment = false;
            vm.freecom_kind = { version: null, cvepic: null, foliosoldon: null, art_piece_name: null, technical_art_piece: null, year_art_piece: null, dimensional_art_piece: null, id: null };

            vm.show_foreign_tourist_passenger = false;
            vm.freecom_foreign_tourist_passenger = { version: null, date_traffic: null, typeid: null, numerid: null, nationality: null, transportcompany: null, idtransport: null, c_transit_type: null, c_type_road: null, id: null };

            vm.show_partial_construction_services = false;
            vm.freecom_partial_construction_services = { version: null, street: null, noext: null, noint: null, colony: null, location: null, reference: null, municipality: null, zipcode: null, numperlicoaut: null, c_federal_entity: null, id: null};

            vm.show_foreign_exchange = false;
            vm.freecom_foreign_exchange = { c_type_operation: null, id: null};

            vm.show_local_taxes = false;
            vm.freecom_local_taxes = { version: null, total_retentions: null, total_transfered: null, id: null};
            vm.freecom_ret_transfs = [];

            vm.show_used_vehicle = false;
            vm.freecom_used_vehicle = { version: null, acquisition_amount: null, monto_enajenacion: null, key_vehicle: null, brand: null, type: null, model: null, number_engine: null, no_serie: null, niv: null, value: null, id: null };
            vm.freecom_vehicle_customs_information = { number: null, date_expedition: null, customs: null, freecom_used_vehicle: null, id: null };

            vm.show_destruction_certificate = false;
            vm.freecom_destruction_certificate = { version: null, numfoldesveh: null, brand: null, class_dc: null, year: null, model: null, niv: null, no_serie: null, number_plates: null, number_engine: null, numfoltarjcir: null, id: null };
            vm.freecom_info_customs_destruction = { numpedimp: null, date_expedition: null, customs: null, freecom_destruction_certificate: null, id: null};

            vm.show_fuel_consumption = false;
            vm.freecom_fuel_consumption = { version: null, type_operation: null, account_number: null, subtotal: null, total: null, id: null };
            vm.freecom_concept_fuels = [];

            vm.show_storeroom_paybill = false;
            vm.freecom_storeroom_paybill = { version: null, type_operation: null, employer_registration: null, account_number: null, total: null, id: null};
            vm.freecom_paybill_concepts = [];

            vm.show_ecc11 = false;
            vm.show_ecc11_invalid = false;
            vm.freecom_ecc11 = {version: null, type_operation: "Tarjeta", number_account: null, subtotal: null, total: null, id: null };
            vm.freecom_ecc11_concepts = [];

            vm.show_spei = false;
            vm.freecom_spei = { id: null };
            vm.freecom_spei_thirds = [];
        }

        //Tax Registration
        vm.show_taxregistration = false;
        vm.freecom_taxregistration = null;


        //PIFC
        vm.show_pfic = false;
        vm.freecom_pfic = null;

        //Accreditation IEPS
        vm.show_accreditation_ieps = false;
        vm.freecom_accreditation_ieps = null;
        vm.c_tars = C_tar.query();

        //Tax Legends
        vm.show_taxlegends = false;
        vm.freecom_taxlegends = null;
        vm.legends = [];

        vm.addLegend = function(){
            $uibModal.open({
                templateUrl: 'app/entities/freecom-legend/freecom-legend-dialog.html',
                controller: 'Freecom_legendDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            tax_provision: null,
                            norm: null,
                            text_legend: null,
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.legends.push(result);
            }, function() {

            });
        };

        vm.removeLegend = function(index){
            vm.legends.splice(index,1);
        };

        //Airline
        vm.show_airline = false;
        vm.freecom_airline = null;
        vm.charges = [];

        vm.addCharge = function(){
            $uibModal.open({
                templateUrl: 'app/entities/freecom-charge/freecom-charge-dialog.html',
                controller: 'Freecom_chargeDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            codecharge: null,
                            amount: null,
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.charges.push(result);
                updateAirLineTotalCharge();
            }, function() {});
        };

        vm.removeCharge = function(index){
            vm.charges.splice(index,1);
            updateAirLineTotalCharge();
        };

        function updateAirLineTotalCharge(){
            var total_charge = 0;
            var i;
            for(i = 0; i < vm.charges.length; i++){
                total_charge = parseFloat(total_charge) + parseFloat(vm.charges[i].amount);
            }
            vm.freecom_airline.total_charge = floorFigure(total_charge, 2);
        }

        //Apaw
        vm.show_apaw = false;
        vm.freecom_apaw = null;
        vm.c_well_types = C_well_type.query();
        vm.c_acquired_titles = C_acquired_title.query();
        vm.c_features_work_pieces = C_features_work_piece.query();

        vm.dateApawPickerOpenStatus = {};
        vm.dateApawPickerOpenStatus.date_acquisition = false;

        vm.openApawCalendar = function(date) {
            vm.dateApawPickerOpenStatus[date] = true;
        };

        //Donees
        vm.show_donees = false;
        vm.freecom_donees = null;

        vm.dateDoneesPickerOpenStatus = {};
        vm.dateDoneesPickerOpenStatus.date_authorization = false;

        vm.openDoneesCalendar = function(date) {
            vm.dateDoneesPickerOpenStatus[date] = true;
        };

        //Educational Institutions
        vm.show_educational_institutions = false;
        vm.freecom_educational_institutions = null;
        vm.c_school_levels = C_school_level.query();

        //Ine
        vm.show_ine = false;
        vm.freecom_ine = null;
        vm.c_process_types = C_process_type.query();
        vm.c_committee_types = C_committee_type.query();

        vm.c_committee_type_required = false;
        vm.c_committee_type_disabled = false;
        vm.ident_disabled = false;
        vm.add_button_ine_entity_disabled = true;
        vm.ine_entity_scope_required = false;
        vm.ine_entity_scope_disabled = false;

        vm.onChangeCprocessType = function(){
            if(vm.freecom_ine.c_process_type != null && vm.freecom_ine.c_process_type.id == 1) {
                vm.c_committee_type_required = true;
                vm.c_committee_type_disabled = false;
                vm.ident_disabled = false;

                vm.freecom_ine_entities = [];
                vm.add_button_ine_entity_disabled = true;

                vm.ine_entity_scope_required = false;
                vm.ine_entity_scope_disabled = false;
            }
            else if(vm.freecom_ine.c_process_type != null && vm.freecom_ine.c_process_type.id != 1){
                vm.c_committee_type_required = false;
                vm.c_committee_type_disabled = true;
                vm.freecom_ine.c_committee_type = null;

                vm.freecom_ine.ident = null;
                vm.ident_disabled = true;

                vm.freecom_ine_entities = [];
                vm.add_button_ine_entity_disabled = false;

                vm.ine_entity_scope_required = true;
                vm.ine_entity_scope_disabled = false;
            }
        };

        vm.onChangeCcommitteeType = function(){
            if(vm.freecom_ine.c_committee_type != null && vm.freecom_ine.c_committee_type.id == 1) {
                vm.ident_disabled = false;

                vm.freecom_ine_entities = [];
                vm.add_button_ine_entity_disabled = true;
            }
            else if(vm.freecom_ine.c_committee_type != null && vm.freecom_ine.c_committee_type.id == 2){
                vm.ident_disabled = true;

                vm.freecom_ine_entities = [];
                vm.add_button_ine_entity_disabled = false;

                vm.ine_entity_scope_required = false;
                vm.ine_entity_scope_disabled = true;
            }
        };

        vm.freecom_ine_entities = [];

        vm.addIneEntity = function(){
            $uibModal.open({
                templateUrl: 'app/entities/freecom-ine-entity/freecom-ine-entity-dialog.html',
                controller: 'Freecom_ine_entityDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            id: null
                        };
                    },
                    entity_req: function() {
                        return {
                            ine_entity_scope_required: vm.ine_entity_scope_required,
                            ine_entity_scope_disabled: vm.ine_entity_scope_disabled
                        };
                    }
                }
            }).result.then(function(result) {
                vm.freecom_ine_entities.push(result);
            }, function() {
                //do not nothing
            });
        };

        vm.removeIneEntity = function(index){
            vm.freecom_ine_entities.splice(index,1);
        };

        //Kind Payment
        vm.show_kind_payment = false;
        vm.freecom_kind_payment = null;

        //Foreign Tourist Passenger
        vm.freecom_foreign_tourist_passenger = null;
        vm.show_foreign_tourist_passenger = false;

        vm.dateForeignTouristPassengerPickerOpenStatus = {};
        vm.dateForeignTouristPassengerPickerOpenStatus.date_traffic = false;

        vm.openForeignTouristPassengerCalendar = function(date) {
            vm.dateForeignTouristPassengerPickerOpenStatus[date] = true;
        };

        vm.c_transit_types = C_transit_type.query();
        vm.c_type_roads = C_type_road.query();

        //Partial Construction Services
        vm.freecom_partial_construction_services = null;
        vm.show_partial_construction_services = false;

        vm.c_federal_entitys = C_federal_entity.query();

        //Foreign Exchange
        vm.freecom_foreign_exchange = null;
        vm.show_foreign_exchange = false;

        vm.c_type_operations = C_type_operation.query();

        //Local Taxes
        vm.freecom_local_taxes = null;
        vm.show_local_taxes = false;

        vm.freecom_ret_transfs = null;

        vm.addFreecomRetTransf = function(){
            $uibModal.open({
                templateUrl: 'app/entities/freecom-retentions-transfered/freecom-retentions-transfered-dialog.html',
                controller: 'Freecom_retentions_transferedDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            implocretentions: null,
                            retentionrate: null,
                            amountretentions: null,
                            imploctransfered: null,
                            transferedrate: null,
                            amounttransfered: null,
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.freecom_ret_transfs.push(result);
                vm.updateFreecomRetTransfTotals();
            }, function() {
                //do not nothing
            });
        };

        vm.removeFreecomRetTransf = function(index){
            vm.freecom_ret_transfs.splice(index,1);
            vm.updateFreecomRetTransfTotals();
        };

        vm.updateFreecomRetTransfTotals = function(){
            var total_retentions = 0;
            var total_transfered = 0;
            var i;
            for(i = 0; i < vm.freecom_ret_transfs.length; i++){
                total_retentions = parseFloat(total_retentions) + parseFloat(vm.freecom_ret_transfs[i].amountretentions);
                total_transfered = parseFloat(total_transfered) + parseFloat(vm.freecom_ret_transfs[i].amounttransfered);
            }

            vm.freecom_local_taxes.total_retentions = floorFigure(total_retentions, 2);
            vm.freecom_local_taxes.total_transfered = floorFigure(total_transfered, 2);
        };

        //Used Vehicle
        vm.show_used_vehicle = false;
        vm.freecom_used_vehicle = null;
        vm.freecom_vehicle_customs_information = null;

        vm.dateVehicleCustomsInfoPickerOpenStatus = {};
        vm.dateVehicleCustomsInfoPickerOpenStatus.date_expedition = false;

        vm.openVehicleCustomsInfoCalendar = function(date) {
            vm.dateVehicleCustomsInfoPickerOpenStatus[date] = true;
        };

        //Destruction Certificate
        vm.show_destruction_certificate = false;
        vm.freecom_destruction_certificate = null;
        vm.freecom_info_customs_destruction = null;

        vm.c_type_series = C_type_series.query();

        vm.dateInfoCustomsDestructionPickerOpenStatus = {};
        vm.dateInfoCustomsDestructionPickerOpenStatus.date_expedition = false;

        vm.openInfoCustomsDestructionCalendar = function(date) {
            vm.dateInfoCustomsDestructionPickerOpenStatus[date] = true;
        };

        //Fuel Consumption
        vm.show_fuel_consumption = false;
        vm.freecom_fuel_consumption = null;
        vm.freecom_concept_fuels = [];

        vm.addFuelConcept = function(){
            $uibModal.open({
                templateUrl: 'app/entities/freecom-concept-fuel/freecom-concept-fuel-dialog.html',
                controller: 'Freecom_concept_fuelDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            identifier: null,
                            date_expedition: null,
                            rfc: null,
                            key_station: null,
                            quantity: null,
                            fuel_name: null,
                            folio_operation: null,
                            unit_value: null,
                            amount: null,
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.freecom_concept_fuels.push(result);
            }, function() {
                //do not nothing
            });
        };

        vm.removeFuelConcept = function(index){
            vm.freecom_concept_fuels.splice(index,1);
        };

        //Storeroom Paybill
        vm.show_storeroom_paybill = false;
        vm.freecom_storeroom_paybill = null;
        vm.freecom_paybill_concepts = [];

        vm.addPaybillConcept = function(){
            $uibModal.open({
                templateUrl: 'app/entities/freecom-paybill-concept/freecom-paybill-concept-dialog.html',
                controller: 'Freecom_paybill_conceptDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            identification_number: null,
                            date_expedition: null,
                            rfc: null,
                            curp: null,
                            name: null,
                            social_security_number: null,
                            amount: null,
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.freecom_paybill_concepts.push(result);
            }, function() {
                //do not nothing
            });
        };

        vm.removePaybillConcept = function(index){
            vm.freecom_paybill_concepts.splice(index,1);
        };

        //Ecc11
        vm.show_ecc11 = false;
        vm.show_ecc11_invalid = false;
        vm.freecom_ecc11 = null;
        vm.freecom_ecc11_concepts = [];

        vm.addEcc11Concept = function(){
            $uibModal.open({
                templateUrl: 'app/entities/freecom-ecc-11-concept/freecom-ecc-11-concept-dialog.html',
                controller: 'Freecom_ecc11_conceptDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            identifier: null,
                            date: new Date(),
                            rfc: null,
                            key_station: null,
                            quantity: null,
                            unit: null,
                            fuel_name: null,
                            folio_operation: null,
                            unit_value: null,
                            amount: null,
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.freecom_ecc11_concepts.push(result);
                calcEcc11Totals();
            }, function() {
                //do not nothing
            });
        };

        vm.removeEcc11Concept = function(index){
            vm.freecom_ecc11_concepts.splice(index,1);
            calcEcc11Totals();
        };

        function calcEcc11Totals(){
            var subtotal = 0;
            var total = 0;

            var i;
            for(i=0; i < vm.freecom_ecc11_concepts.length; i++){
                var freecom_ecc11_concept = vm.freecom_ecc11_concepts[i].concept;

                subtotal = parseFloat(subtotal) + parseFloat(freecom_ecc11_concept.amount);
                total =  parseFloat(total) + parseFloat(freecom_ecc11_concept.amount);

                var freecom_ecc11_tranfers = vm.freecom_ecc11_concepts[i].transfers;
                var j;
                for(j=0; j < freecom_ecc11_tranfers.length; j++){
                    total =  parseFloat(total) + parseFloat(freecom_ecc11_tranfers[j].amount);
                }
            }

            vm.freecom_ecc11.total =  floorFigure(total, 2);
            vm.freecom_ecc11.subtotal = floorFigure(subtotal, 2);
        }

        //Spei Third
        vm.show_spei = false;
        vm.freecom_spei = null;
        vm.freecom_spei_thirds = [];

        vm.addSpeiThird = function(){
            $uibModal.open({
                templateUrl: 'app/entities/freecom-spei-third/freecom-spei-third-dialog.html',
                controller: 'Freecom_spei_thirdDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return {
                            date_operation: null,
                            hour: "00:00:00",
                            key_spei: null,
                            stamp: null,
                            number_certificate: null,
                            cda: null,
                            id: null
                        };
                    },
                    payer_entity: function () {
                        return {
                            emitter_bank: null,
                            name: null,
                            type_account: null,
                            account: null,
                            rfc: null,
                            id: null
                        };
                    },
                    beneficiary_entity: function () {
                        return {
                            receiver_bank: null,
                            name: null,
                            type_account: null,
                            account: null,
                            rfc: null,
                            concept: null,
                            iva: (1).toFixed(2),
                            payment_amount: (1).toFixed(2),
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.freecom_spei_thirds.push(result);
            }, function() {
                //do not nothing
            });
        };

        vm.removeSpeiThird = function(index){
            vm.freecom_spei_thirds.splice(index,1);
        };
    }
})();
