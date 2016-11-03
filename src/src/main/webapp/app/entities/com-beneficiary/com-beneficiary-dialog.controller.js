(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_beneficiaryDialogController', Com_beneficiaryDialogController);

    Com_beneficiaryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Com_beneficiary'];

    function Com_beneficiaryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Com_beneficiary) {
        var vm = this;
        vm.com_beneficiary = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_beneficiaryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_beneficiary.id !== null) {
                Com_beneficiary.update(vm.com_beneficiary, onSaveSuccess, onSaveError);
            } else {
                Com_beneficiary.save(vm.com_beneficiary, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
