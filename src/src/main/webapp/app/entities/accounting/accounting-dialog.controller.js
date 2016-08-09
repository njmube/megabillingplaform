(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('AccountingDialogController', AccountingDialogController);

    AccountingDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Accounting', 'Freecom_ine_entity'];

    function AccountingDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Accounting, Freecom_ine_entity) {
        var vm = this;
        vm.accounting = entity;
        vm.freecom_ine_entities = Freecom_ine_entity.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

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
