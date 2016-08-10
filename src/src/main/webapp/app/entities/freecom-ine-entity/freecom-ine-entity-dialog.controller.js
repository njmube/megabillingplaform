(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_ine_entityDialogController', Freecom_ine_entityDialogController);

    Freecom_ine_entityDialogController.$inject = ['$uibModal', '$uibModalInstance', 'entity', 'Key_entity', 'C_scope_type'];

    function Freecom_ine_entityDialogController ( $uibModal, $uibModalInstance, entity, Key_entity, C_scope_type) {
        var vm = this;
        vm.freecom_ine_entity = entity;
        vm.key_entities = Key_entity.query({pg: -1});
        vm.c_scope_types = C_scope_type.query();

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
