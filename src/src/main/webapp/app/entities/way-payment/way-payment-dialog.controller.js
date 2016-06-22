(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Way_paymentDialogController', Way_paymentDialogController);

    Way_paymentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Way_payment'];

    function Way_paymentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Way_payment) {
        var vm = this;

        vm.way_payment = entity;
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
            if (vm.way_payment.id !== null) {
                Way_payment.update(vm.way_payment, onSaveSuccess, onSaveError);
            } else {
                Way_payment.save(vm.way_payment, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:way_paymentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
