(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_kind_paymentDialogController', Com_kind_paymentDialogController);

    Com_kind_paymentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Com_kind_payment', 'Cfdi'];

    function Com_kind_paymentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Com_kind_payment, Cfdi) {
        var vm = this;
        vm.com_kind_payment = entity;
        vm.cfdis = Cfdi.query({filter: 'com_kind_payment-is-null'});
        $q.all([vm.com_kind_payment.$promise, vm.cfdis.$promise]).then(function() {
            if (!vm.com_kind_payment.cfdi || !vm.com_kind_payment.cfdi.id) {
                return $q.reject();
            }
            return Cfdi.get({id : vm.com_kind_payment.cfdi.id}).$promise;
        }).then(function(cfdi) {
            vm.cfdis.push(cfdi);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_kind_paymentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_kind_payment.id !== null) {
                Com_kind_payment.update(vm.com_kind_payment, onSaveSuccess, onSaveError);
            } else {
                Com_kind_payment.save(vm.com_kind_payment, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
