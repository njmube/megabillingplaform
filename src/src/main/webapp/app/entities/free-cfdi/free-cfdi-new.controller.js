(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_cfdiNewController', Free_cfdiNewController);

    Free_cfdiNewController.$inject = ['entity', 'Principal', 'Free_cfdi', 'Cfdi_types', 'Cfdi_states', 'free_emitter_entity', 'Payment_method', 'Way_payment', 'C_money', 'Cfdi_type_doc', 'Tax_regime', 'DataUtils', 'free_receiver_entity', 'Free_receiver', 'Type_taxpayer', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code', '$uibModal', 'Tax_types', 'Rate_type','$timeout', '$state', '$q', 'C_tar', 'C_well_type', 'C_acquired_title', 'C_features_work_piece', 'C_school_level', 'C_committee_type', 'C_process_type', 'C_transit_type', 'C_type_road', 'C_federal_entity', 'C_type_operation', 'C_type_series', 'C_type_operation_ce','C_key_pediment','Freecom_incoterm'];

    function Free_cfdiNewController (entity, Principal, Free_cfdi, Cfdi_types, Cfdi_states, free_emitter_entity, Payment_method, Way_payment, C_money, Cfdi_type_doc, Tax_regime, DataUtils, free_receiver_entity, Free_receiver, Type_taxpayer, C_country, C_state, C_municipality, C_colony, C_zip_code, $uibModal, Tax_types, Rate_type, $timeout, $state, $q, C_tar, C_well_type, C_acquired_title, C_features_work_piece, C_school_level, C_committee_type, C_process_type, C_transit_type, C_type_road, C_federal_entity, C_type_operation, C_type_series, C_type_operation_ce, C_key_pediment, Freecom_incoterm) {

		var vm = this;

        vm.free_cfdi = entity;
        vm.free_emitter = free_emitter_entity;
        vm.free_cfdi.free_emitter = vm.free_emitter;
        vm.messegeUser = messegeUser;
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

        vm.messegeUser();

        vm.load = function(id) {
            Free_cfdi.get({id : id}, function(result) {
                vm.free_cfdi = result;
            });
        };

        function messegeUser(){
            Principal.identity().then(function(account) {
                if(account != null){
                    vm.isUser = account.authorities.indexOf('ROLE_USER') != -1;
                    vm.isNoAdmin = account.authorities.indexOf('ROLE_ADMIN') == -1;

                    if(vm.isUser && vm.isNoAdmin){
                        $uibModal.open({
                            templateUrl: 'app/home/messegeUser.html',
                            controller: 'MessegeUserController',
                            controllerAs: 'vm',
                            backdrop: true,
                            size: '',
                            resolve: {
                                entity: function () {
                                    return 0;
                                }
                            }
                        }).result.then(function () {

                        }, function () {
                        });
                    }
                }

            });
        }

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
                vm.updateCFDITotals();
            }else if(vm.free_receiver.rfc != undefined && vm.free_receiver.rfc.length == 13){
                vm.free_receiver.type_taxpayer = vm.type_taxpayers[1];
                vm.updateCFDITotals();
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

            vm.updateCFDITotals();
        }

		function onClickForeignResident(){
            vm.free_receiver.rfc = 'XEXX010101000';
			vm.free_receiver.type_taxpayer = vm.type_taxpayers[1];
			vm.free_receiver.business_name = 'Residente Extranjero';

            vm.updateCFDITotals();
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
                            quantity: (0).toFixed(vm.accuracy),
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
            });
        };

        vm.removeConcept = function(index){
            vm.free_concepts.splice(index,1);
            vm.free_concept_ids.splice(index,1);

            if(vm.free_concepts.length == 0 && vm.free_cfdi.cfdi_type_doc != undefined && vm.free_cfdi.cfdi_type_doc.id != 6){
                updateValidRateTypes(20);
            }

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

            var bln = new BigLargeNumberOperations();
            var i;
            for(i=0; i < vm.free_concepts.length; i++){
                //calculating free cfdi subtotal...
                //subtotal = subtotal + vm.free_concepts[i].free_concept.quantity * vm.free_concepts[i].free_concept.unit_value;
                subtotal = bln.add(subtotal, bln.multiply(vm.free_concepts[i].free_concept.quantity, vm.free_concepts[i].free_concept.unit_value, 6), 6);

                //total_tax_transfered = parseFloat(total_tax_transfered) + parseFloat(vm.free_concepts[i].free_concept_iva.amount) + parseFloat(vm.free_concepts[i].free_concept_ieps.amount);
                total_tax_transfered = bln.add(total_tax_transfered, bln.add(vm.free_concepts[i].free_concept_iva.amount, vm.free_concepts[i].free_concept_ieps.amount, 6), 6);

                //getting iva to show to user...
                if(vm.free_concepts[i].free_concept_iva.rate ==  show_iva_val16 || vm.free_concepts[i].free_concept_iva.rate == show_iva_val15){
                    show_iva = vm.free_concepts[i].free_concept_iva.rate;

                    if(vm.free_concepts[i].free_concept_iva.rate ==  show_iva_val16){
                        updateValidRateTypes(15);
                    }

                    if(vm.free_concepts[i].free_concept_iva.rate ==  show_iva_val15){
                        updateValidRateTypes(16);
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

                    //calc_iva = calc_iva + iva_calc_val * vm.free_concepts[i].free_concept.amount * (1 + vm.free_concepts[i].free_concept_ieps.rate/100);
                    calc_iva = bln.add(calc_iva, bln.multiply(bln.multiply(iva_calc_val, vm.free_concepts[i].free_concept.amount, 6), (1 + vm.free_concepts[i].free_concept_ieps.rate/100), 6), 6);
                    //calc_iva = bln.add(calc_iva, bln.multiply(bln.multiply(iva_calc_val, bln.multiply(vm.free_concepts[i].free_concept.quantity, vm.free_concepts[i].free_concept.unit_value, 6), 6), (1 + vm.free_concepts[i].free_concept_ieps.rate/100), 6), 6);
                }
                else {
                    var iva_calc_val = 0;
                    if(vm.free_concepts[i].free_concept_iva.rate == show_iva_val16){
                        iva_calc_val = 16/100;
                    }
                    if(vm.free_concepts[i].free_concept_iva.rate == show_iva_val15){
                        iva_calc_val = 15/100;
                    }

                    //calc_iva = calc_iva + vm.free_concepts[i].free_concept.amount * iva_calc_val;
                    calc_iva = bln.add(calc_iva, bln.multiply(vm.free_concepts[i].free_concept.amount, iva_calc_val, 6), 6);
                }

                //calculating free cfdi ieps...
                if(vm.free_cfdi.cfdi_type_doc != undefined && (vm.free_cfdi.cfdi_type_doc.id == 1 || vm.free_cfdi.cfdi_type_doc.id == 8)){
                    //ieps = ieps + vm.free_concepts[i].free_concept_ieps.rate/100 * vm.free_concepts[i].free_concept.amount;
                    ieps = bln.add(ieps, bln.multiply(vm.free_concepts[i].free_concept_ieps.rate/100, vm.free_concepts[i].free_concept.amount, 6), 6);
                }

                //calculationg subtotal - discount
                //subtotal_discount = parseFloat(subtotal_discount) + parseFloat(vm.free_concepts[i].free_concept.amount);
                subtotal_discount = bln.add(subtotal_discount, vm.free_concepts[i].free_concept.amount, 6);
            }

            //calculating free cfdi ret iva and ret isr...
            if(vm.free_cfdi.free_emitter.rfc != undefined && vm.free_cfdi.free_emitter.rfc.length == 13 && vm.free_receiver.rfc != undefined && vm.free_receiver.rfc.length == 12 && vm.free_cfdi.cfdi_type_doc != undefined) {

                if (vm.free_cfdi.cfdi_type_doc.id == 2 || vm.free_cfdi.cfdi_type_doc.id == 5) {
                    //ret_iva = 2/3 * calc_iva;
                    ret_iva = bln.multiply(2 / 3, calc_iva, 6);

                    //ret_isr = 1/10 * subtotal_discount;
                    ret_isr = bln.multiply(1 / 10, subtotal_discount, 6);
                }
                else if (vm.free_cfdi.cfdi_type_doc.id == 3) {
                    //ret_iva = 2/3 * calc_iva;
                    ret_iva = bln.multiply(2 / 3, calc_iva, 6);
                }

            }
            else if(vm.free_cfdi.cfdi_type_doc != undefined && vm.free_cfdi.cfdi_type_doc.id == 4){
                //ret_iva = 0.04 * subtotal_discount;
                ret_iva = bln.multiply(0.04, subtotal_discount, 6);
            }

            //showing all...
            vm.free_cfdi.subtotal =  bln.add(subtotal, 0, vm.accuracy);

            if(show_iva != 0){
                vm.show_iva = show_iva;
            }

            vm.calc_iva = bln.add(calc_iva, 0, vm.accuracy);

            vm.ieps = bln.add(ieps, 0, vm.accuracy);

            vm.ret_iva = bln.add(ret_iva, 0, vm.accuracy);

            vm.ret_isr = bln.add(ret_isr, 0, vm.accuracy);

            //discount = subtotal - subtotal_discount;
            discount = bln.minus(subtotal, subtotal_discount, vm.accuracy);
            vm.free_cfdi.discount = discount;

            vm.subtotal_discount = bln.add(subtotal_discount, 0, vm.accuracy);

            //total = (subtotal_discount + calc_iva) - (ret_iva +  ret_isr) + ieps;
            total = bln.add(bln.minus(bln.add(subtotal_discount, calc_iva, 6), bln.add(ret_iva, ret_isr, 6), 6), ieps, vm.accuracy);
            vm.free_cfdi.total = total;

            vm.disabled_iva_value = disabled_iva_value;

            vm.free_cfdi.total_tax_transfered = bln.add(total_tax_transfered, 0, vm.accuracy);

            total_tax_retention = bln.add(ret_iva, ret_isr, vm.accuracy);
            vm.free_cfdi.total_tax_retention = total_tax_retention;
        };

		vm.save = function () {

			var bln = new BigLargeNumberOperations();

            vm.free_cfdi.cfdi_states = {id: 1, name: "Creado  ", description: "CFDI creado en el sistema"};
            vm.free_cfdi.tax_regime = vm.free_emitter.tax_regime;

            if(vm.free_cfdi.mont_folio_fiscal_orig != null && vm.free_cfdi.mont_folio_fiscal_orig > 0){
                vm.free_cfdi.mont_folio_fiscal_orig = bln.add(vm.free_cfdi.mont_folio_fiscal_orig, 0, vm.accuracy);
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
                    //amount_iva_retentions = 2/3 * free_concept.quantity * free_concept.unit_value;
                    amount_iva_retentions = bln.multiply(bln.multiply(2/3, free_concept.quantity, 6), free_concept.unit_value, vm.accuracy);
                }
                if(vm.free_cfdi.cfdi_type_doc != undefined && vm.free_cfdi.cfdi_type_doc.id == 4){
                    //amount_iva_retentions = 0.04 * free_concept.quantity * free_concept.unit_value * (1 - free_concept.discount/100);
                    amount_iva_retentions = bln.multiply(bln.multiply(bln.multiply(0.04, free_concept.quantity, 6), free_concept.unit_value, 6), (1 - free_concept.discount/100), vm.accuracy);
                }

                if(amount_iva_retentions > 0){
                    free_tax_retentions_iva = {
                        amount: amount_iva_retentions,
                        tax_types: vm.tax_typess[0],
                        id: null
                    };

                    free_tax_retentions.push(free_tax_retentions_iva);

                    vm.free_concepts[i].free_tax_retentions_iva = free_tax_retentions_iva;
                }

                //getting ISR for free_tax_retentions
                amount_isr_retentions = 0;
                //calculating free cfdi ret isr...
                if((vm.free_cfdi.free_emitter.rfc != undefined && vm.free_cfdi.free_emitter.rfc.length == 13 && vm.free_receiver.rfc != undefined && vm.free_receiver.rfc.length == 12) || (vm.free_cfdi.cfdi_type_doc != undefined && (vm.free_cfdi.cfdi_type_doc.id == 2 || vm.free_cfdi.cfdi_type_doc.id == 5))){
                    //amount_isr_retentions = 1/10 * free_concept.quantity * free_concept.unit_value * (1 - free_concept.discount/100);
                    amount_isr_retentions = bln.multiply(bln.multiply(bln.multiply(1/10, free_concept.quantity, 6), free_concept.unit_value, 6), (1 - free_concept.discount/100), vm.accuracy);
                }

                if(amount_isr_retentions > 0){
                    free_tax_retentions_isr = {
                        amount: amount_isr_retentions,
                        tax_types: vm.tax_typess[1],
                        id: null
                    };

                    free_tax_retentions.push(free_tax_retentions_isr);

                    vm.free_concepts[i].free_tax_retentions_isr = free_tax_retentions_isr;
                }
            }

            var freeCfdiDTO = {
                freeReceiver: vm.free_receiver,
                freeCFDI: vm.free_cfdi,
                conceptDTOs: vm.free_concepts,
                concepts: free_concepts,
                freeTaxTransfereds: free_tax_transfereds,
                freeTaxRetentions: free_tax_retentions
            };

            if(vm.current_complement != null) {

                switch (vm.current_complement.id) {
                    case "taxregistration":
                        vm.freecom_taxregistration.version = "1.0";
                        freeCfdiDTO.freecom_taxregistration = vm.freecom_taxregistration;
                        break;
                    case "pfic":
                        vm.freecom_pfic.version = "1.0";
                        freeCfdiDTO.freecom_pfic = vm.freecom_pfic;
                        break;
                    case "accreditation_ieps":
                        vm.freecom_accreditation_ieps.version = "1.0";
                        freeCfdiDTO.freecom_accreditation_ieps = vm.freecom_accreditation_ieps;
                        break;
                    case "taxlegends":
                        vm.freecom_taxlegends.version = "1.0";
                        freeCfdiDTO.freecom_taxlegends = vm.freecom_taxlegends;
                        freeCfdiDTO.freecom_legends = vm.legends;
                        break;
                    case "airline":
                        vm.freecom_airline.version = "1.0";
                        freeCfdiDTO.freecom_airline = vm.freecom_airline;
                        freeCfdiDTO.freecom_charges = vm.charges;
                        break;
                    case "apaw":
                        vm.freecom_apaw.version = "1.0";
                        freeCfdiDTO.freecom_apaw = vm.freecom_apaw;
                        break;
                    case "donees":
                        vm.freecom_donees.version = "1.0";
                        freeCfdiDTO.freecom_donees = vm.freecom_donees;
                        break;
                    case "educational_institutions":
                        vm.freecom_educational_institutions.version = "1.0";
                        freeCfdiDTO.freecom_educational_institutions = vm.freecom_educational_institutions;
                        break;
                    case "ine":
                        vm.freecom_ine.version = "1.0";
                        freeCfdiDTO.freecom_ine = vm.freecom_ine;
                        freeCfdiDTO.freecom_ine_entities = vm.freecom_ine_entities;
                        break;
                    case "kind_payment":
                        vm.freecom_kind_payment.version = "1.0";
                        freeCfdiDTO.freecom_kind_payment = vm.freecom_kind_payment;
                        break;
                    case "foreign_tourist_passenger":
                        vm.freecom_foreign_tourist_passenger.version = "1.0";
                        freeCfdiDTO.freecom_foreign_tourist_passenger = vm.freecom_foreign_tourist_passenger;
                        break;
                    case "partial_construction_services":
                        vm.freecom_partial_construction_services.version = "1.0";
                        freeCfdiDTO.freecom_partial_construction_services = vm.freecom_partial_construction_services;
                        break;
                    case "foreign_exchange":
                        vm.freecom_foreign_exchange.version = "1.0";
                        freeCfdiDTO.freecom_foreign_exchange = vm.freecom_foreign_exchange;
                        break;
                    case "local_taxes":
                        vm.freecom_local_taxes.version = "1.0";
                        freeCfdiDTO.freecom_local_taxes = vm.freecom_local_taxes;
                        freeCfdiDTO.freecom_ret_transfs = vm.freecom_ret_transfs;
                        break;
                    case "used_vehicle":
                        vm.freecom_used_vehicle.version = "1.0";
                        freeCfdiDTO.freecom_used_vehicle = vm.freecom_used_vehicle;
                        freeCfdiDTO.vehicle_customs_informations = vm.vehicle_customs_informations;
                        break;
                    case "destruction_certificate":
                        vm.freecom_destruction_certificate.version = "1.0";
                        freeCfdiDTO.freecom_destruction_certificate = vm.freecom_destruction_certificate;
                        if(vm.use_info_customs_destruction) {
                            freeCfdiDTO.freecom_info_customs_destruction = vm.freecom_info_customs_destruction;
                        }
                        break;
                    case "fuel_consumption":
                        vm.freecom_fuel_consumption.version = "1.0";
                        freeCfdiDTO.freecom_fuel_consumption = vm.freecom_fuel_consumption;
                        freeCfdiDTO.freecom_concept_fuels = vm.freecom_concept_fuels;
                        break;
                    case "storeroom_paybill":
                        vm.freecom_storeroom_paybill.version = "1.0";
                        freeCfdiDTO.freecom_storeroom_paybill = vm.freecom_storeroom_paybill;
                        freeCfdiDTO.freecom_paybill_concepts = vm.freecom_paybill_concepts;
                        break;
                    case "ecc11":
                        vm.freecom_ecc11.version = "1.0";
                        freeCfdiDTO.freecom_ecc11 = vm.freecom_ecc11;
                        freeCfdiDTO.freecom_ecc11_concepts = vm.freecom_ecc11_concepts;
                        break;
                    case "spei":
                        freeCfdiDTO.freecom_spei_thirds = vm.freecom_spei_thirds;
                        break;
                    case "foreign_trade":
                        vm.freecom_foreign_trade.version = "1.0";
                        freeCfdiDTO.freecom_foreign_trade = vm.freecom_foreign_trade;
                        freeCfdiDTO.freecom_addressee = vm.freecom_addressee;
                        freeCfdiDTO.commodities = vm.f_add_commodities;
                        break;
                }
            }

            vm.isSaving = true;

            Free_cfdi.save(freeCfdiDTO, onSaveSuccess, onSaveError);
        };

        var onSaveSuccess = function () {
            resetView();
            vm.free_saved_success = true;
            $timeout(function () {
                vm.free_saved_success = false;
            }, 2000);

            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

		function resetView(){

			vm.free_receiver.rfc= null;
            vm.free_receiver.type_taxpayer = null;
			vm.free_receiver.business_name= null;
			vm.free_receiver.email= null;
			vm.free_receiver.activated= false;
			vm.free_receiver.create_date= null;
			vm.free_receiver.location= null;
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

            vm.free_cfdi.free_emitter = vm.free_emitter;
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
            else
                vm.free_cfdi.change_type = null;
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
            {id:"spei", name: "SPEI Tercero a Tercero"},
            {id:"foreign_trade", name: "Comercio Exterior"}
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
                case "foreign_trade":
                    vm.show_foreign_trade = true;
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
            vm.freecom_apaw = { version: null, others_well_type: null, others_acquired_title: null, subtotal: (0).toFixed(2), iva: (0).toFixed(2), date_acquisition: new Date(), c_well_type: null, c_acquired_title: null, c_features_work_piece: null, id: null };
            vm.disable_others_well_type = true;
            vm.disable_others_acquired_title = true;

            vm.show_donees = false;
            vm.freecom_donees = { version: null, no_authorization: null, date_authorization: new Date(), legend: null,  id: null };

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
            vm.freecom_kind_payment = { version: null, cvepic: null, foliosoldon: null, art_piece_name: null, technical_art_piece: null, year_art_piece: null, dimensional_art_piece: null, id: null };

            vm.show_foreign_tourist_passenger = false;
            vm.freecom_foreign_tourist_passenger = { version: null, date_traffic: new Date(), typeid: null, numerid: null, nationality: null, transportcompany: null, idtransport: null, c_transit_type: null, c_type_road: null, id: null };

            vm.show_partial_construction_services = false;
            vm.freecom_partial_construction_services = { version: null, street: null, noext: null, noint: null, colony: null, location: null, reference: null, municipality: null, zipcode: null, numperlicoaut: null, c_federal_entity: null, id: null};

            vm.show_foreign_exchange = false;
            vm.freecom_foreign_exchange = { c_type_operation: null, id: null};

            vm.show_local_taxes = false;
            vm.freecom_local_taxes = { version: null, total_local_retentions: null, total_local_transfered: null, id: null };
            vm.freecom_ret_transfs = [];

            vm.show_used_vehicle = false;
            vm.freecom_used_vehicle = { version: null, acquisition_amount: null, monto_enajenacion: null, key_vehicle: null, brand: null, type: null, model: null, number_engine: null, no_serie: null, niv: null, value: null, id: null };
            vm.vehicle_customs_informations = [];

            vm.show_destruction_certificate = false;
            vm.freecom_destruction_certificate = { version: null, numfoldesveh: null, brand: null, class_dc: null, year: null, model: null, niv: null, no_serie: null, number_plates: null, number_engine: null, numfoltarjcir: null, id: null };
            vm.freecom_info_customs_destruction = { numpedimp: null, date_expedition: null, customs: null, freecom_destruction_certificate: null, id: null};
            vm.use_info_customs_destruction = false;

            vm.show_fuel_consumption = false;
            vm.freecom_fuel_consumption = { version: null, type_operation: "Monedero Electrónico", account_number: null, subtotal: (0).toFixed(2), total: (0).toFixed(2), id: null };
            vm.freecom_concept_fuels = [];

            vm.show_storeroom_paybill = false;
            vm.freecom_storeroom_paybill = { version: null, type_operation: "Monedero Electrónico", employer_registration: null, account_number: null, total: null, id: null};
            vm.freecom_paybill_concepts = [];

            vm.show_ecc11 = false;
            vm.show_ecc11_invalid = false;
            vm.freecom_ecc11 = {version: null, type_operation: "Tarjeta", number_account: null, subtotal: null, total: null, id: null };
            vm.freecom_ecc11_concepts = [];

            vm.show_spei = false;
            vm.freecom_spei = { id: null };
            vm.freecom_spei_thirds = [];

            vm.show_foreign_trade = false;
            vm.freecom_foreign_trade = { version: null, emitter_curp: null, receiver_curp: null, receiver_numregidtrib: null, origin_certificate: null, number_origin_certificate: null, number_reliable_exporter: null, subdivision: null, observations: null, typechangeusd: null, totalusd: null, id: null };
            vm.freecom_addressee = { street: null, no_ext: null, no_int: null, locate: null, reference: null, numregidtrib: null, rfc: null, curp: null, name: null, id: null };
            vm.f_add_commodities = [];
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
        vm.disable_others_well_type = true;
        vm.disable_others_acquired_title = true;
        vm.c_well_types = C_well_type.query();
        vm.c_acquired_titles = C_acquired_title.query();
        vm.c_features_work_pieces = C_features_work_piece.query();

        vm.onApawWellTypeChange = function(){
            if(vm.freecom_apaw.c_well_type != null && vm.freecom_apaw.c_well_type.id == 4){
                vm.disable_others_well_type = false;
            }
            else{
                vm.disable_others_well_type = true;
            }
        };

        vm.onApawAcquiredTitleChange = function(){
            if(vm.freecom_apaw.c_acquired_title != null && vm.freecom_apaw.c_acquired_title.id == 5){
                vm.disable_others_acquired_title = false;
            }
            else{
                vm.disable_others_acquired_title = true;
            }
        };

        vm.maxApawDate = new Date();
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
                vm.freecom_ine.ident = null;
                vm.ident_disabled = true;

                vm.freecom_ine_entities = [];
                vm.add_button_ine_entity_disabled = false;

                vm.ine_entity_scope_required = false;
                vm.ine_entity_scope_disabled = true;
            }
            else if(vm.freecom_ine.c_committee_type != null && vm.freecom_ine.c_committee_type.id == 3){
                vm.ident_disabled = false;

                vm.freecom_ine_entities = [];
                vm.add_button_ine_entity_disabled = false;

                vm.ine_entity_scope_required = false;
                vm.ine_entity_scope_disabled = true;
            }
        };

        vm.freecom_ine_entities = [];
        vm.key_scope_combinations = [];

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
                    },
                    key_scope_combinations: function(){
                        return vm.key_scope_combinations;
                    }
                }
            }).result.then(function(result) {
                vm.freecom_ine_entities.push(result);
                updateKeyScopeCombinations();
            }, function() {
                //do not nothing
            });
        };

        vm.removeIneEntity = function(index){
            vm.freecom_ine_entities.splice(index,1);
            updateKeyScopeCombinations();
        };

        function updateKeyScopeCombinations(){
            vm.key_scope_combinations = [];
            var i;
            for(i = 0; i < vm.freecom_ine_entities.length; i++){
                vm.key_scope_combinations.push({
                    key: vm.freecom_ine_entities[i].freecom_ine_entity.key_entity,
                    scope: vm.freecom_ine_entities[i].freecom_ine_entity.c_scope_type
                });
            }
        }

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

        vm.pcs_c_municipalities = C_municipality.query({stateId: 0, filtername:" "});
        vm.pcs_c_colonies = null;

         vm.onChangePCS_C_municipality = function() {
            var municipalityId = vm.freecom_partial_construction_services.c_municipality.id;
            C_colony.query({municipalityId: municipalityId, filtername:" "}, function(result){
                vm.pcs_c_colonies = result;
                vm.freecom_partial_construction_services.c_zip_code = null;
            });
        };

         vm.onChangePCS_C_colony = function() {
             var colonyId = vm.freecom_partial_construction_services.c_colony.id;
             C_zip_code.get({id: colonyId, filtername: " "}, function (result) {
                 vm.freecom_partial_construction_services.c_zip_code = result;
             });
         };

        vm.c_federal_entitys = C_federal_entity.query();

        //Foreign Exchange
        vm.freecom_foreign_exchange = null;
        vm.show_foreign_exchange = false;

        vm.c_type_operations = C_type_operation.query();

        //Local Taxes
        vm.show_local_taxes = false;
        vm.freecom_local_taxes = null;

        vm.freecom_ret_transfs = [];

        vm.addFreecomRetTransf = function(){
            $uibModal.open({
                templateUrl: 'app/entities/freecom-local-retentions/freecom-local-retentions-dialog.html',
                controller: 'Freecom_local_retentionsDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return {
                            implocretentions: null,
                            retentionrate: (0).toFixed(2),
                            amountretentions: (0).toFixed(2),
                            id: null
                        };
                    },
                    transfered_entity: function () {
                        return {
                            imploctransfered: null,
                            transferedrate: (0).toFixed(2),
                            amounttransfered: (0).toFixed(2),
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
                if(vm.freecom_ret_transfs[i].retentions != null) {
                    total_retentions = parseFloat(total_retentions) + parseFloat(vm.freecom_ret_transfs[i].retentions.amountretentions);
                }
                if(vm.freecom_ret_transfs[i].transfered != null) {
                    total_transfered = parseFloat(total_transfered) + parseFloat(vm.freecom_ret_transfs[i].transfered.amounttransfered);
                }
            }

            vm.freecom_local_taxes.total_local_retentions = floorFigure(total_retentions, 2);
            vm.freecom_local_taxes.total_local_transfered = floorFigure(total_transfered, 2);
        };

        //Used Vehicle
        vm.show_used_vehicle = false;
        vm.freecom_used_vehicle = null;
        vm.vehicle_customs_informations = [];

        vm.addVehicleCustomsInformation = function(){
            $uibModal.open({
                templateUrl: 'app/entities/freecom-vehicle-customs-information/freecom-vehicle-customs-information-dialog.html',
                controller: 'Freecom_vehicle_customs_informationDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            number: null,
                            date_expedition: null,
                            customs: null,
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.vehicle_customs_informations.push(result);
            }, function() {
                //do not nothing
            });
        };

        vm.removeVehicleCustomsInformation = function(index){
            vm.vehicle_customs_informations.splice(index,1);
        };

        //Destruction Certificate
        vm.show_destruction_certificate = false;
        vm.freecom_destruction_certificate = null;
        vm.freecom_info_customs_destruction = null;
        vm.use_info_customs_destruction = false;

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
                            date_expedition: new Date(),
                            rfc: null,
                            key_station: null,
                            quantity: (0).toFixed(2),
                            fuel_name: null,
                            folio_operation: null,
                            unit_value: (0).toFixed(2),
                            amount: null,
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.freecom_concept_fuels.push(result);
                calcFuelTotals();
            }, function() {
                //do not nothing
            });
        };

        vm.removeFuelConcept = function(index){
            vm.freecom_concept_fuels.splice(index,1);
            calcFuelTotals();
        };

        function calcFuelTotals(){
            var fuel_subtotal = 0;
            var fuel_total = 0;

            var i;
            for(i=0; i < vm.freecom_concept_fuels.length; i++){
                var concept_fuel = vm.freecom_concept_fuels[i].concept_fuel;

                fuel_subtotal = parseFloat(fuel_subtotal) + parseFloat(concept_fuel.amount);
                fuel_total =  parseFloat(fuel_total) + parseFloat(concept_fuel.amount);

                var fuel_determinates = vm.freecom_concept_fuels[i].determinates;
                var j;
                for(j=0; j < fuel_determinates.length; j++){
                    fuel_total =  parseFloat(fuel_total) + parseFloat(fuel_determinates[j].amount);
                }
            }

            vm.freecom_fuel_consumption.subtotal = floorFigure(fuel_subtotal, 2);
            vm.freecom_fuel_consumption.total =  floorFigure(fuel_total, 2);
        }

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
                            date_expedition: new Date(),
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
                            quantity: "0.001",
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

        //Foreign Trade
        vm.show_foreign_trade = false;
        vm.freecom_foreign_trade = null;
        vm.freecom_addressee = null;
        vm.f_add_commodities = [];

        vm.c_type_operation_ces = C_type_operation_ce.query();
        vm.c_key_pediments = C_key_pediment.query();
        vm.freecom_incoterms = Freecom_incoterm.query();

        vm.f_add_c_countries = C_country.query({pg:1, filtername:" "});
        vm.f_add_c_states = null;
        vm.f_add_c_municipalities = null;
        vm.f_add_c_colonies = null;

        vm.onFreecomAddresseeChangeC_country = function() {
            var countryId = vm.freecom_addressee.c_country.id;
            C_state.query({countryId: countryId, filtername:" "}, function(result){
                vm.f_add_c_states = result;
            });
        };

        vm.onFreecomAddresseeChangeC_state = function () {
            var stateId = vm.freecom_addressee.c_state.id;
            C_municipality.query({stateId: stateId, filtername:" "}, function(result){
                vm.f_add_c_municipalities = result;
            });
        };

        vm.onFreecomAddresseeChangeC_municipality = function () {
            var municipalityId = vm.freecom_addressee.c_municipality.id;
            C_colony.query({municipalityId: municipalityId, filtername:" "}, function(result){
                vm.f_add_c_colonies = result;
            });
        };

        vm.onFreecomAddresseeChangeC_colony = function(){
            C_zip_code.get({id : vm.freecom_addressee.c_colony.c_zip_code.id, filtername:" "}, function(result) {
                vm.freecom_addressee.c_zip_code = result;
            });
        };

        vm.addFreecomCommodity = function(){
            $uibModal.open({
                templateUrl: 'app/entities/freecom-commodity/freecom-commodity-dialog.html',
                controller: 'Freecom_commodityDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            noidentification: null,
                            customs_quantity: (0).toFixed(2),
                            customs_unit_value: (0).toFixed(2),
                            dollar_value: (0).toFixed(2),
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.f_add_commodities.push(result);
            }, function() {
                //do not nothing
            });
        };

        vm.removeFreecomCommodity = function(index){
            vm.f_add_commodities.splice(index,1);
        };
    }
})();
