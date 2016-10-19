(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_conceptDialogController', Taxpayer_conceptDialogController);

    Taxpayer_conceptDialogController.$inject = ['$scope', '$uibModalInstance', 'entity', 'taxpayer_account_entity', 'Taxpayer_concept', 'Measure_unit', 'Measure_unit_concept', 'Price_concept', 'Discount_concept'];

    function Taxpayer_conceptDialogController ($scope, $uibModalInstance, entity, taxpayer_account_entity, Taxpayer_concept, Measure_unit, Measure_unit_concept, Price_concept, Discount_concept) {
        var vm = this;
        vm.taxpayer_concept = entity;
        vm.taxpayer_account = taxpayer_account_entity;

        console.log(vm.taxpayer_concept);

        vm.measure_unit = null;
        vm.measure_units = Measure_unit.query({pg: -1, filtername:" "});
        vm.measure_unit_concepts = [];
        if(vm.taxpayer_concept.id != null){
            vm.measure_unit_concepts = Measure_unit_concept.query({taxpayerconcept: vm.taxpayer_concept.id});
            console.log(vm.measure_unit_concepts);
        }
        vm.measure_unit_concept_add_error = false;
        vm.onMeasureUnitChange = onMeasureUnitChange;
        vm.addMeasureUnitConcept = addMeasureUnitConcept;
        vm.removeMeasureUnitConcept = removeMeasureUnitConcept;

        vm.price_concept = (0).toFixed(vm.taxpayer_account.accuracy);
        vm.price_concepts = [];
        if(vm.taxpayer_concept.id != null){
            vm.price_concepts = Price_concept.query({taxpayerconcept: vm.taxpayer_concept.id});
        }
        vm.price_concept_add_error = false;
        vm.addPriceConcept = addPriceConcept;
        vm.removePriceConcept = removePriceConcept;

        vm.discount_concept = (0).toFixed(2);
        vm.discount_concepts = [];
        if(vm.taxpayer_concept.id != null){
            vm.discount_concepts = Discount_concept.query({taxpayerconcept: vm.taxpayer_concept.id});
        }
        vm.discount_concept_add_error = false;
        vm.addDiscountConcept = addDiscountConcept;
        vm.removeDiscountConcept = removeDiscountConcept;

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:taxpayer_conceptUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            vm.taxpayer_concept.taxpayer_account = vm.taxpayer_account;
            var taxpayer_conceptDTO = {
                taxpayer_concept: vm.taxpayer_concept,
                measure_units: vm.measure_unit_concepts,
                prices: vm.price_concepts,
                discounts: vm.discount_concepts
            };

            if (vm.taxpayer_concept.id !== null) {
                Taxpayer_concept.update(taxpayer_conceptDTO, onSaveSuccess, onSaveError);
            } else {
                Taxpayer_concept.save(taxpayer_conceptDTO, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        function onMeasureUnitChange(){
            if(vm.measure_unit != null) {
                var i;
                var error_found = false;
                for (i = 0; i < vm.measure_unit_concepts.length; i++) {
                    if (vm.measure_unit_concepts[i].measure_unit.name == vm.measure_unit.name) {
                        error_found = true;
                        break;
                    }
                }

                vm.measure_unit_concept_add_error = error_found;
            }

        }

        function addMeasureUnitConcept() {
            if (!vm.measure_unit_concept_add_error) {
                vm.measure_unit_concepts.push({
                        id: null,
                        measure_unit: vm.measure_unit
                    }
                );
                vm.measure_unit = null;
            }
        }

        function removeMeasureUnitConcept(index){
            var measure_unit_concept = vm.measure_unit_concepts[index];
            if(measure_unit_concept.id != null){
                Measure_unit_concept.delete({id: measure_unit_concept.id});
            }
            vm.measure_unit_concepts.splice(index,1);
        }

        function addPriceConcept(){
            var i;
            var error_found = false;
            for (i = 0; i < vm.price_concepts.length; i++) {
                if (vm.price_concepts[i].value == vm.price_concept) {
                    error_found = true;
                    break;
                }
            }

            vm.price_concept_add_error = error_found;

            if(!vm.price_concept_add_error) {

                vm.price_concepts.push({
                    id: null,
                    value: vm.price_concept
                });

                vm.price_concept = (0).toFixed(vm.taxpayer_account.accuracy);
            }
        }

        function removePriceConcept(index){
            var price_concept = vm.price_concepts[index];
            if(price_concept.id != null){
                Price_concept.delete({id: price_concept.id});
            }

            vm.price_concepts.splice(index,1);
        }

        function addDiscountConcept(){
            var i;
            var error_found = false;
            for (i = 0; i < vm.discount_concepts.length; i++) {
                if (vm.discount_concepts[i].value == vm.discount_concept) {
                    error_found = true;
                    break;
                }
            }

            vm.discount_concept_add_error = error_found;

            if(!vm.discount_concept_add_error) {
                vm.discount_concepts.push({
                    id: null,
                    value: vm.discount_concept
                });

                vm.discount_concept = (0).toFixed(2);
            }
        }

        function removeDiscountConcept(index){
            var discount_concept = vm.discount_concepts[index];
            if(discount_concept.id != null){
                Discount_concept.delete({id: discount_concept.id});
            }

            vm.discount_concepts.splice(index,1);
        }
    }
})();
