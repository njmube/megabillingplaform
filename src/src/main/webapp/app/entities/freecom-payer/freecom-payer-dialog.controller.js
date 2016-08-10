(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_payerDialogController', Freecom_payerDialogController);

    Freecom_payerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_payer'];

    function Freecom_payerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Freecom_payer) {
        var vm = this;

        vm.freecom_payer = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.freecom_payer.id !== null) {
                Freecom_payer.update(vm.freecom_payer, onSaveSuccess, onSaveError);
            } else {
                Freecom_payer.save(vm.freecom_payer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:freecom_payerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
