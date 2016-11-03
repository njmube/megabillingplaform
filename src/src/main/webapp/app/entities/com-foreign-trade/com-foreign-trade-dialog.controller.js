(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_foreign_tradeDialogController', Com_foreign_tradeDialogController);

    Com_foreign_tradeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Com_foreign_trade', 'Cfdi', 'Com_incoterm', 'C_type_operation_ce', 'C_key_pediment', 'Com_addressee'];

    function Com_foreign_tradeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Com_foreign_trade, Cfdi, Com_incoterm, C_type_operation_ce, C_key_pediment, Com_addressee) {
        var vm = this;
        vm.com_foreign_trade = entity;
        vm.cfdis = Cfdi.query();
        vm.com_incoterms = Com_incoterm.query();
        vm.c_type_operation_ces = C_type_operation_ce.query();
        vm.c_key_pediments = C_key_pediment.query();
        vm.com_addressees = Com_addressee.query({filter: 'com_foreign_trade-is-null'});
        $q.all([vm.com_foreign_trade.$promise, vm.com_addressees.$promise]).then(function() {
            if (!vm.com_foreign_trade.com_addressee || !vm.com_foreign_trade.com_addressee.id) {
                return $q.reject();
            }
            return Com_addressee.get({id : vm.com_foreign_trade.com_addressee.id}).$promise;
        }).then(function(com_addressee) {
            vm.com_addressees.push(com_addressee);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_foreign_tradeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_foreign_trade.id !== null) {
                Com_foreign_trade.update(vm.com_foreign_trade, onSaveSuccess, onSaveError);
            } else {
                Com_foreign_trade.save(vm.com_foreign_trade, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
