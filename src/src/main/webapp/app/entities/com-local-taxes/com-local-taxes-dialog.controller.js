(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_local_taxesDialogController', Com_local_taxesDialogController);

    Com_local_taxesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Com_local_taxes', 'Cfdi'];

    function Com_local_taxesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Com_local_taxes, Cfdi) {
        var vm = this;
        vm.com_local_taxes = entity;
        vm.cfdis = Cfdi.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_local_taxesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_local_taxes.id !== null) {
                Com_local_taxes.update(vm.com_local_taxes, onSaveSuccess, onSaveError);
            } else {
                Com_local_taxes.save(vm.com_local_taxes, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
