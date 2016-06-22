(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Payment_methodDialogController', Payment_methodDialogController);

    Payment_methodDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Payment_method'];

    function Payment_methodDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Payment_method) {
        var vm = this;

        vm.payment_method = entity;
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
            if (vm.payment_method.id !== null) {
                Payment_method.update(vm.payment_method, onSaveSuccess, onSaveError);
            } else {
                Payment_method.save(vm.payment_method, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:payment_methodUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
