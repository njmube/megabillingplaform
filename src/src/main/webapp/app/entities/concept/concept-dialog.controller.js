(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('ConceptDialogController', ConceptDialogController);

    ConceptDialogController.$inject = ['$timeout', '$scope', '$uibModalInstance', 'entity', 'taxpayer_account_entity', 'Measure_unit', '$uibModal', 'Concept', 'Part_concept', 'Customs_info'];

    function ConceptDialogController ($timeout, $scope, $uibModalInstance, entity, taxpayer_account_entity, Measure_unit, $uibModal, Concept, Part_concept, Customs_info) {
        var vm = this;

        vm.concept = entity;
        vm.taxpayer_account = taxpayer_account_entity;

        vm.clear = clear;
        vm.save = save;

        vm.measure_units = Measure_unit.query({pg: -1, filtername:" "});

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        vm.calcAmount = calcAmount;
        vm.addPartConcept = addPartConcept;
        vm.removePartConcept = removePartConcept;
        vm.addCustomInfo = addCustomInfo;
        vm.removeCustomInfo = removeCustomInfo;

        vm.part_concepts = [];
        vm.customs_infos = [];

        if(vm.concept.id != null){
            vm.part_concepts = Part_concept.query({conceptid: vm.concept.id});
            vm.customs_infos = Customs_info.query({conceptid: vm.concept.id});
        }

        function calcAmount(){
            var regexp = /^\d{1,24}\.?\d{1,6}$/;
            var concept_quantity = vm.concept.quantity + '';
            var concept_unit_value = vm.concept.unit_value + '';
            if(vm.concept.quantity > 0 && concept_quantity.match(regexp) && vm.concept.unit_value > 0 && concept_unit_value.match(regexp)){
                var bln = new BigLargeNumberOperations();
                var amount = bln.multiply(vm.concept.quantity, vm.concept.unit_value, 6);
                var discount = 1 - vm.concept.discount/100;
                vm.concept.amount = bln.multiply(amount, discount, 6);
            }
            else{
                vm.concept.amount = null;
            }
        }

        function addPartConcept(){
            $uibModal.open({
                templateUrl: 'app/entities/part-concept/part-concept-dialog.html',
                controller: 'Part_conceptDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            no_identification: null,
                            quanitty: 0,
                            description: null,
                            unit_value: (0).toFixed(6),
                            amount: null,
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.part_concepts.push(result);
            });
        }

        function removePartConcept(index){
            var part_concept = vm.part_concepts[index];
            if(part_concept.id != null){
                Part_concept.delete({id: part_concept.id});
            }
            vm.part_concepts.splice(index,1);
        }

        function addCustomInfo(){
            $uibModal.open({
                templateUrl: 'app/entities/customs-info/customs-info-dialog.html',
                controller: 'Customs_infoDialogController',
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
                vm.customs_infos.push(result);
            });
        }

        function removeCustomInfo(index){
            var customs_info = vm.customs_infos[index];
            if(customs_info.id != null){
                Customs_info.delete({id: customs_info.id});
            }
            vm.customs_infos.splice(index,1);
        }

        function save () {
            vm.isSaving = true;
            vm.concept.taxpayer_account = vm.taxpayer_account;

            var conceptDTO = {
                concept: vm.concept,
                part_concepts: vm.part_concepts,
                customs_infos: vm.customs_infos
            };

            if (vm.concept.id !== null) {
                Concept.update(conceptDTO, onSaveSuccess, onSaveError);
            } else {
                Concept.save(conceptDTO, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:conceptUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();
