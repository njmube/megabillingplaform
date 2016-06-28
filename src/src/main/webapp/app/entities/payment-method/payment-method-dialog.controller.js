(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Payment_methodDialogController', Payment_methodDialogController);

    Payment_methodDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Payment_method'];

    function Payment_methodDialogController ($scope, $stateParams, $uibModalInstance, entity, Payment_method) {
        var vm = this;
        vm.payment_method = entity;
        vm.load = function(id) {
            Payment_method.get({id : id}, function(result) {
                vm.payment_method = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:payment_methodUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.payment_method.id !== null) {
                Payment_method.update(vm.payment_method, onSaveSuccess, onSaveError);
            } else {
                Payment_method.save(vm.payment_method, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
