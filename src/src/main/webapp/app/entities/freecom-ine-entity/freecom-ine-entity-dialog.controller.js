(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_ine_entityDialogController', Freecom_ine_entityDialogController);

    Freecom_ine_entityDialogController.$inject = ['$uibModal', '$uibModalInstance', 'entity', 'entity_req', 'key_scope_combinations', 'Key_entity', 'C_scope_type'];

    function Freecom_ine_entityDialogController ( $uibModal, $uibModalInstance, entity, entity_req, key_scope_combinations, Key_entity, C_scope_type) {
        var vm = this;
        vm.freecom_ine_entity = entity;
        vm.freecom_ine_entity_req = entity_req;
        vm.key_scope_combinations = key_scope_combinations;
        vm.key_entities = Key_entity.query({pg: -1});
        vm.c_scope_types = C_scope_type.query();

        vm.key_scope_combination_repeated = false;

        vm.onScopeChange = function(){
            vm.key_scope_combination_repeated = false;
            var i;
            for(i = 0; i < vm.key_scope_combinations.length; i++){
                if(vm.key_scope_combinations[i].key != null && vm.key_scope_combinations[i].scope != null){
                    if(vm.freecom_ine_entity.key_entity != null && vm.freecom_ine_entity.c_scope_type != null && vm.freecom_ine_entity.key_entity.id == vm.key_scope_combinations[i].key.id && vm.freecom_ine_entity.c_scope_type.id == vm.key_scope_combinations[i].scope.id){
                        vm.key_scope_combination_repeated = true;
                        break;
                    }
                }
            }
        };

        vm.accountings = [];

        vm.addAccounting = function(){
            $uibModal.open({
                templateUrl: 'app/entities/accounting/accounting-dialog.html',
                controller: 'AccountingDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            keyaccounting: null,
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.accountings.push(result);
            }, function() {
               //do not nothing...
            });
        };

        vm.removeAccounting = function(index){
            vm.accountings.splice(index,1);
        };

        vm.save = function () {
            vm.isSaving = true;

            $uibModalInstance.close({
                freecom_ine_entity: {
                    key_entity: vm.freecom_ine_entity.key_entity,
                    c_scope_type: vm.freecom_ine_entity.c_scope_type
                },
                accountings: vm.accountings
            });

            vm.isSaving = false;
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
