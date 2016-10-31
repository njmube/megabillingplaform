(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('CfdiAddConceptInfoDialogController', CfdiAddConceptInfoDialogController);

    CfdiAddConceptInfoDialogController.$inject = ['customs_infos', 'part_concepts', 'taxpayer_account_entity', '$uibModal', '$uibModalInstance'];

    function CfdiAddConceptInfoDialogController (customs_infos, part_concepts, taxpayer_account_entity, $uibModal, $uibModalInstance) {
        var vm = this;

        vm.customs_infos = customs_infos;
        vm.part_concepts = part_concepts;
        vm.taxpayer_account = taxpayer_account_entity;

        vm.addCustomInfo = function(){
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
        };

        vm.removeCustomInfo = function(index){
            vm.customs_infos.splice(index,1);
        };

        vm.addPartConcept = function(){
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
                            unit_value: (0).toFixed(vm.taxpayer_account.accuracy),
                            amount: null,
                            id: null
                        };
                    },
                    acurracy: vm.taxpayer_account.accuracy
                }
            }).result.then(function(result) {
                vm.part_concepts.push(result);
            });
        };

        vm.removePartConcept = function(index){
            vm.part_concepts.splice(index,1);
        };

        vm.save = function () {
            $uibModalInstance.close({
                customs_infos: vm.customs_infos,
                part_concepts: vm.part_concepts
            });
            vm.isSaving = false;
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
