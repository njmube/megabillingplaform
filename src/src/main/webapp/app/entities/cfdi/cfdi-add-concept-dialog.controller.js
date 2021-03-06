(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('CfdiAddConceptDialogController', CfdiAddConceptDialogController);

    CfdiAddConceptDialogController.$inject = ['$scope', '$uibModalInstance', 'entity', 'taxpayer_account_entity', 'Tax_types', 'Taxpayer_concept', 'Price_concept', 'Discount_concept', 'Measure_unit_concept', 'Tax_concept', 'AlertService'];

    function CfdiAddConceptDialogController ($scope, $uibModalInstance, entity, taxpayer_account_entity, Tax_types, Taxpayer_concept, Price_concept, Discount_concept, Measure_unit_concept, Tax_concept, AlertService) {
        var vm = this;
        vm.concept = entity;
        vm.conceptDTOs = [];
        vm.taxpayer_account = taxpayer_account_entity;
        vm.tax_typess = Tax_types.query({filtername: " "});
        vm.other_ivas = [{id: 101, rate: 0}, {id: 102, rate: "No aplica"} , {id: 103, rate: "Excento"}];

        vm.price_concepts = [];
        vm.measure_unit_concepts = [];
        vm.iva_concepts = [];
        vm.ieps_concepts = [];
        vm.discount_concepts = [];

        vm.unit_value = [];
        vm.measure_unit = [];
        vm.iva = [];
        vm.ieps = [];
        vm.discount = [];
        vm.quantity = [];
        vm.amount = [];
        vm.choosen = [];

        vm.no_one = true;

        vm.unit_value_error = false;
        vm.measure_unit_value_error = false;
        vm.iva_error = false;
        vm.quantity_error = false;

        loadAll();

        function loadAll () {
            var no_identification = " ";
            var description = " ";
            var measure_unit = " ";
            var unit_value = -1;

            Taxpayer_concept.query({
                page: 0,
                size: 100,
                taxpayeraccount: vm.taxpayer_account.id,
                no_identification: no_identification,
                description: description,
                measure_unit: measure_unit,
                unit_value: unit_value
            }, onSuccess, onError);
            function onSuccess(data) {
                vm.taxpayer_concepts = data;

                var i;
                for(i = 0; i < vm.taxpayer_concepts.length; i++){
                    var prices = Price_concept.query({taxpayerconcept: vm.taxpayer_concepts[i].id});
                    var measure_units = Measure_unit_concept.query({taxpayerconcept: vm.taxpayer_concepts[i].id});
                    var ivas = Tax_concept.query({taxpayeraccount: vm.taxpayer_account.id, tax_type: "IVA", rate: -1, concept: " ", conceptid: vm.taxpayer_concepts[i].id});
                    var ieps = Tax_concept.query({taxpayeraccount: vm.taxpayer_account.id, tax_type: "IEPS", rate: -1, concept: " ", conceptid: vm.taxpayer_concepts[i].id});
                    var discounts = Discount_concept.query({taxpayerconcept: vm.taxpayer_concepts[i].id});

                    vm.price_concepts.push(prices);
                    vm.measure_unit_concepts.push(measure_units);
                    vm.iva_concepts.push(ivas);
                    vm.ieps_concepts.push(ieps);
                    vm.discount_concepts.push(discounts);

                    vm.unit_value.push(null);
                    vm.measure_unit.push(null);
                    vm.iva.push(null);
                    vm.ieps.push(null);
                    vm.discount.push(null);
                    vm.quantity.push((0).toFixed(vm.taxpayer_account.accuracy));
                    vm.amount.push((0).toFixed(vm.taxpayer_account.accuracy));
                    vm.choosen.push(false);
                }
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        vm.onChooseConcept = function(){
            vm.no_one = true;
            var i;
            for(i = 0; i < vm.choosen.length; i++) {
                if (vm.choosen[i]){
                    vm.no_one = false;
                }}
        };

        vm.calcConceptAmount = function (index){
            var regexp = /^\d{1,24}\.?\d{1,6}$/;
            var quantity = vm.quantity[index] + '';
            if(vm.unit_value[index] && vm.quantity[index] != 0 && vm.quantity[index] != "" && quantity.match(regexp)) {
                var unit_value = vm.unit_value[index].value + '';
                if (vm.quantity[index] > 0 && quantity.match(regexp) && vm.unit_value[index].value > 0) {
                    var bln = new BigLargeNumberOperations();
                    var amount = bln.multiply(vm.quantity[index], vm.unit_value[index].value, 6);
                    var discount = 1;
                    if (vm.discount[index]) {
                        discount = 1 - vm.discount[index].value / 100;
                    }
                    vm.amount[index] = bln.multiply(amount, discount, vm.taxpayer_account.accuracy);
                }
                else{
                    vm.amount[index] = (0).toFixed(vm.taxpayer_account.accuracy);
                }
            }
            else{
                vm.amount[index] = (0).toFixed(vm.taxpayer_account.accuracy);
            }
        };

        function floorFigure(figure, decimals){
            if (!decimals) decimals = 2;
            var d = Math.pow(10,decimals);
            return (parseInt(figure*d)/d).toFixed(decimals);
        }

        vm.save = function () {
            vm.isSaving = true;

            vm.unit_value_error = false;
            vm.measure_unit_error = false;
            vm.iva_error = false;
            vm.quantity_error = false;

            var valid_unit_value = true;
            var valid_measure_unit = true;
            var valid_iva = true;
            var valid_quantity = true;

            var regexp = /^\d{1,24}\.?\d{1,6}$/;

            var index;
            //validating all choosen concepts
            for(index = 0; index < vm.choosen.length; index++) {
                if (vm.choosen[index]) {

                    if (!vm.unit_value[index]) {
                        vm.unit_value_error = true;
                        valid_unit_value = false;
                    }

                    if (!vm.measure_unit[index]) {
                        vm.measure_unit_error = true;
                        valid_measure_unit = false;
                    }

                    if (!vm.iva[index]) {
                        vm.iva_error = true;
                        valid_iva = false;
                    }

                    var quantity = vm.quantity[index] + '';

                    if (!vm.quantity[index] || vm.quantity[index] == "" || vm.quantity[index] == 0 || !quantity.match(regexp)) {
                        vm.quantity_error = true;
                        valid_quantity = false;
                    }
                }
            }

            //Adding concepts
            if (valid_unit_value && valid_measure_unit && valid_iva && valid_quantity) {

                for (index = 0; index < vm.choosen.length; index++) {
                    if (vm.choosen[index]) {

                        var bln = new BigLargeNumberOperations();

                        if (vm.iva[index].rate == "0.00" || vm.iva[index].rate == "No aplica" || vm.iva[index].rate == "Excento") {
                            vm.iva[index] = {rate: 0};
                        }

                        if (!vm.ieps[index]) {
                            vm.ieps[index] = {rate: 0};
                        }

                        if (!vm.discount[index]) {
                            vm.discount[index] = {value: 0};
                        }

                        var free_concept_iva_amount_p1 = bln.multiply(vm.quantity[index], vm.unit_value[index].value, 6);
                        var free_concept_iva_amount_p2 = (1 - vm.discount[index].value / 100) * vm.iva[index].rate / 100;
                        var free_concept_iva_amount = bln.multiply(free_concept_iva_amount_p1, free_concept_iva_amount_p2, vm.taxpayer_account.accuracy);

                        var free_concept_ieps_amount_p1 = bln.multiply(vm.quantity[index], vm.unit_value[index].value, 6);
                        var free_concept_ieps_amount_p2 = (1 - vm.discount[index].value / 100) * vm.ieps[index].rate / 100;
                        var free_concept_ieps_amount = bln.multiply(free_concept_ieps_amount_p1, free_concept_ieps_amount_p2, vm.taxpayer_account.accuracy);

                        vm.concept = {
                            no_identification: vm.taxpayer_concepts[index].no_identification,
                            quantity: vm.quantity[index],
                            description: vm.taxpayer_concepts[index].description,
                            unit_value: vm.unit_value[index].value,
                            predial_number: vm.taxpayer_concepts[index].predial_number,
                            discount: floorFigure(vm.discount[index].value, 2),
                            amount: vm.amount[index],
                            measure_unit: vm.measure_unit[index].measure_unit,
                            id: null
                        };

                        var conceptDTO = {
                            concept: vm.concept,
                            concept_iva: {
                                rate: floorFigure(vm.iva[index].rate, 2),
                                amount: free_concept_iva_amount,
                                tax_types: vm.tax_typess[0],
                                id: null
                            },
                            concept_ieps: {
                                rate: floorFigure(vm.ieps[index].rate, 2),
                                amount: free_concept_ieps_amount,
                                tax_types: vm.tax_typess[2],
                                id: null
                            }
                        };

                        vm.conceptDTOs.push(conceptDTO);
                    }
                }

                $uibModalInstance.close(vm.conceptDTOs);
            }

            vm.isSaving = false;
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
