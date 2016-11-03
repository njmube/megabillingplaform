(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_payerDialogController', Com_payerDialogController);

    Com_payerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Com_payer'];

    function Com_payerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Com_payer) {
        var vm = this;
        vm.com_payer = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_payerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_payer.id !== null) {
                Com_payer.update(vm.com_payer, onSaveSuccess, onSaveError);
            } else {
                Com_payer.save(vm.com_payer, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
