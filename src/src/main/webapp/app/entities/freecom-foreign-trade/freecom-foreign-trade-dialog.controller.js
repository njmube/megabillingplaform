(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_foreign_tradeDialogController', Freecom_foreign_tradeDialogController);

    Freecom_foreign_tradeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Freecom_foreign_trade', 'Free_cfdi', 'Freecom_incoterm', 'C_type_operation_ce', 'C_key_pediment', 'Freecom_addressee'];

    function Freecom_foreign_tradeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Freecom_foreign_trade, Free_cfdi, Freecom_incoterm, C_type_operation_ce, C_key_pediment, Freecom_addressee) {
        var vm = this;

        vm.freecom_foreign_trade = entity;
        vm.clear = clear;
        vm.save = save;
        vm.free_cfdis = Free_cfdi.query();
        vm.freecom_incoterms = Freecom_incoterm.query();
        vm.c_type_operation_ces = C_type_operation_ce.query();
        vm.c_key_pediments = C_key_pediment.query();
        vm.freecom_addressees = Freecom_addressee.query({filter: 'freecom_foreign_trade-is-null'});
        $q.all([vm.freecom_foreign_trade.$promise, vm.freecom_addressees.$promise]).then(function() {
            if (!vm.freecom_foreign_trade.freecom_addressee || !vm.freecom_foreign_trade.freecom_addressee.id) {
                return $q.reject();
            }
            return Freecom_addressee.get({id : vm.freecom_foreign_trade.freecom_addressee.id}).$promise;
        }).then(function(freecom_addressee) {
            vm.freecom_addressees.push(freecom_addressee);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.freecom_foreign_trade.id !== null) {
                Freecom_foreign_trade.update(vm.freecom_foreign_trade, onSaveSuccess, onSaveError);
            } else {
                Freecom_foreign_trade.save(vm.freecom_foreign_trade, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:freecom_foreign_tradeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
