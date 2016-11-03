(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_local_transferedDialogController', Com_local_transferedDialogController);

    Com_local_transferedDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Com_local_transfered', 'Com_local_taxes'];

    function Com_local_transferedDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Com_local_transfered, Com_local_taxes) {
        var vm = this;
        vm.com_local_transfered = entity;
        vm.com_local_taxes = Com_local_taxes.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_local_transferedUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_local_transfered.id !== null) {
                Com_local_transfered.update(vm.com_local_transfered, onSaveSuccess, onSaveError);
            } else {
                Com_local_transfered.save(vm.com_local_transfered, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
