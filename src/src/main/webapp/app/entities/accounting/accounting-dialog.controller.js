(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('AccountingDialogController', AccountingDialogController);

    AccountingDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Accounting', 'Entity_cfdi'];

    function AccountingDialogController ($scope, $stateParams, $uibModalInstance, entity, Accounting, Entity_cfdi) {
        var vm = this;
        vm.accounting = entity;
        vm.entity_cfdis = Entity_cfdi.query();
        vm.load = function(id) {
            Accounting.get({id : id}, function(result) {
                vm.accounting = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:accountingUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.accounting.id !== null) {
                Accounting.update(vm.accounting, onSaveSuccess, onSaveError);
            } else {
                Accounting.save(vm.accounting, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
