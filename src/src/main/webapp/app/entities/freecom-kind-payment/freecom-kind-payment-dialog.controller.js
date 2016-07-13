(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_kind_paymentDialogController', Freecom_kind_paymentDialogController);

    Freecom_kind_paymentDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Freecom_kind_payment', 'Free_cfdi'];

    function Freecom_kind_paymentDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, Freecom_kind_payment, Free_cfdi) {
        var vm = this;
        vm.freecom_kind_payment = entity;
        vm.free_cfdis = Free_cfdi.query({filter: 'freecom_kind_payment-is-null'});
        $q.all([vm.freecom_kind_payment.$promise, vm.free_cfdis.$promise]).then(function() {
            if (!vm.freecom_kind_payment.free_cfdi || !vm.freecom_kind_payment.free_cfdi.id) {
                return $q.reject();
            }
            return Free_cfdi.get({id : vm.freecom_kind_payment.free_cfdi.id}).$promise;
        }).then(function(free_cfdi) {
            vm.free_cfdis.push(free_cfdi);
        });
        vm.load = function(id) {
            Freecom_kind_payment.get({id : id}, function(result) {
                vm.freecom_kind_payment = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_kind_paymentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_kind_payment.id !== null) {
                Freecom_kind_payment.update(vm.freecom_kind_payment, onSaveSuccess, onSaveError);
            } else {
                Freecom_kind_payment.save(vm.freecom_kind_payment, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
