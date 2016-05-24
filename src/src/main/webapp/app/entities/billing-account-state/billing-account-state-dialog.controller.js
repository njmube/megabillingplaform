(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Billing_account_stateDialogController', Billing_account_stateDialogController);

    Billing_account_stateDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Billing_account_state'];

    function Billing_account_stateDialogController ($scope, $stateParams, $uibModalInstance, entity, Billing_account_state) {
        var vm = this;
        vm.billing_account_state = entity;
        vm.load = function(id) {
            Billing_account_state.get({id : id}, function(result) {
                vm.billing_account_state = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:billing_account_stateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.billing_account_state.id !== null) {
                Billing_account_state.update(vm.billing_account_state, onSaveSuccess, onSaveError);
            } else {
                Billing_account_state.save(vm.billing_account_state, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
